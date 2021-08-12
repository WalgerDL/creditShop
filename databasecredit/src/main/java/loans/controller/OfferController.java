package loans.controller;

import domain.Payments;
import loans.domain.entity.Client;
import loans.domain.entity.CreditOffer;
import loans.kafka.service.OfferProducer;
import loans.repository.OfferRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import payment.CalculationPayment;
import toexcel.ExcelDocument;
import topdf.PdfDocument;

import java.io.ByteArrayInputStream;
import java.util.Optional;

@Slf4j
@Controller
public class OfferController {

    private final OfferRepository offerRepository;
    private Payments payments;

    @Autowired
    private OfferProducer offerProducer;

    public OfferController(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    @GetMapping(value = "/offers.html")
    private String get(Model model) {
        log.info("create offer=====================================");
        Optional<CreditOffer> offer = Optional.empty();
        model.addAttribute("offer", offer);
        return "offers/detailsOffer";
    }

    @GetMapping("/offersList")
    public String allOffer(Model model) {
        log.info("offers");
        Pageable pageable = PageRequest.of(0, 10);
        model.addAttribute("offers", offerRepository.findAll());
        return "offers/listOffer";
    }

    @GetMapping(value = "/offer", params = "id")
    private String getOfferId(Long id, Model model) {
        log.info("offer id:=" + id);
        Optional<CreditOffer> offer = offerRepository.findById(id);
        Optional<Client> client = offerRepository.findByClientIdForOffer(offer.get().getClientId());
        log.info("================client:=" + client);

        //
        model.addAttribute("cln_name", client.isPresent() ? client.get().getFullName() : "Клиент не найден");
        model.addAttribute("offer", offer.orElse(new CreditOffer()));
        return "offers/evnOffer";
    }

    @GetMapping(value = "clientOffers", params = "id")
    public String clientIdOffers(Long id, Model model) {
        log.info("======offers + client:=" + id);
        model.addAttribute("offers", offerRepository.findByOfferClientId(id));
        return "offers/listOffer";
    }



    @PostMapping("approved")
    public String processApproved(CreditOffer newOffer, Model model) {
        log.info("=======approved credit:=" + newOffer.getAmount());
        if (newOffer.getAmount() != null) {
            if(newOffer.getCredit() != null ) {
                newOffer.setFullName(newOffer.getFullName() + " тип кредита: " + newOffer.getCredit().getType_credit());
            }
            log.info("=======approved full_name:=" + newOffer.getFullName());
            offerRepository.save(newOffer);
            System.out.println("new client added: " + newOffer);
            offerProducer.produceOffer(newOffer, "k_" + newOffer.getId());
            log.info("kafka approved ok!");
        }
        return "redirect:/clients.html";
    }

    @PostMapping(value = "deleteOffer", params = "id")
    public String deleteOffer(Long id) {
        log.info("Delete offer id:=" + id);
        Optional<CreditOffer> offer = offerRepository.findById(id);
        System.out.println("offer:=" + offer);
        offerRepository.deleteById(id);
        return "redirect:/offersList";
    }

    @PostMapping(value = "paymentSchedule", params = "id")
    public String paymentSchedule(Long id, Model model) {
        log.info("Payment schedule id:=" + id);
        Optional<CreditOffer> offer = offerRepository.findById(id);
        Optional<Client> client = offerRepository.findByClientIdForOffer(offer.get().getClientId());
        model.addAttribute("offer", offer);
        CreditOffer creditOffer = offer.get();
        CalculationPayment calculationPayment = new CalculationPayment(creditOffer.getCredit().getTerm(), creditOffer.getAmount(), creditOffer.getCredit().getRate());
        if(creditOffer.getCredit().getType_credit().equals("ANNUITY")) {
            //ануитент
            log.info("Тип кредита:=ANNUITY");
            calculationPayment.payAnnuity();
            payments = calculationPayment.getPayments();
            model.addAttribute("payments", payments);
        } else {
            //диффиренциал
            log.info("Тип кредита:=DIFFERENTIAL");
            calculationPayment.payDifferential();
            payments = calculationPayment.getPayments();
            model.addAttribute("payments", payments);
        }
        model.addAttribute("cln_name", client.get().getFullName() + " тип кредита:=" + creditOffer.getCredit().getType_credit());
        return "offers/listPayment";
    }

    /**
     * "экспорт графика платежей в MS Excel
     * @author Ivan
     */
    @PostMapping(value = "/downloadExcel")
    public ModelAndView downloadExcel() {
        log.info("Экспорт в Excel количество строк:=" + payments.getCount());
        return new ModelAndView(new ExcelDocument(), "payments", payments);
    }

    /**
     * "экспорт графика платежей в pdf
     * @author Ivan
     */
    @PostMapping(value = "/exportPdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> employeeReport() {

        ByteArrayInputStream bis = PdfDocument.employeePDFReport(payments);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=employees.pdf");

        return ResponseEntity.ok().headers(headers).contentType
                (MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }


}
