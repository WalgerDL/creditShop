package loans.controller;

import loans.domain.entity.Bank;
import loans.domain.entity.Client;
import loans.domain.entity.Credit;
import loans.domain.entity.CreditOffer;
import loans.repository.CreditRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * Контроллер для кредитов
 * @author Ivan
 *
 */
@Slf4j
@Controller
public class CreditController {

    private List<String> typeCred;
    private final CreditRepository creditRepository;

    public CreditController(CreditRepository creditRepository) {
        this.creditRepository = creditRepository;
        typeCred = new ArrayList<>();
        typeCred.add("ANNUITY");
        typeCred.add("DIFFERENTIAL");
    }

    @GetMapping("credits.html")
    public String all(Model model) {
        log.info("credits");
        Iterable<Credit> iterable = creditRepository.findAll();
        model.addAttribute("credits", creditRepository.findAll());
        return "credits/listCredit";
    }

    @GetMapping(value = "/directCredits", params = "id")
    //@GetMapping(value = "/directCredits")
    public String allDirect(Long id, Model model) {

        if(id != null) {
            log.info("=======directCredits client id:=" + id);
        }
        //
        Iterable<Credit> iterable = creditRepository.findAll();
        model.addAttribute("credits", creditRepository.findAll());

        Optional<Client> cli = creditRepository.findByClientId(id);
        log.info("=======directCredits client name:=" + cli.get().getFullName());
        model.addAttribute("client", creditRepository.findByClientId(id).get());
        //
        return "credits/directoryCredit";
    }


    @GetMapping(value = "/credits.html", params = "id")
    private String getCreditId(Long id, Model model) {
        System.out.println("id:=" + id);
        Optional<Credit> cr = creditRepository.findById(id);
        Iterable<Bank> banks =  creditRepository.findByBankAll();
        System.out.println("credit:=" + cr);
        creditRepository.findById(id)
                .ifPresent(credit -> model.addAttribute("credit", credit));
        model.addAttribute("allTypes", typeCred);
        model.addAttribute("banks", banks);
        return "credits/detailsCredit";
    }

    @GetMapping(value = "bankCredits", params = "id")
    public String allBankId(Long id, Model model) {
        log.info("======credits + bank:=" + id);
        model.addAttribute("credits", creditRepository.findByBankRef(id));
        return "credits/listCredit";
    }


    @PostMapping(value="/credits.html")
    public String addCredit(@ModelAttribute Credit newCredit, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            log.error("BINDING RESULT ERROR:=" + bindingResult.toString());
            return "/credits.html";
        } else {
            model.addAttribute("credit", newCredit);
            if (newCredit.getLimitCredit() != null) {
                creditRepository.save(newCredit);
                System.out.println("new credit added: " + newCredit);
            }
            return "redirect:/credits.html";
        }
    }

    @PostMapping("credit")
    public String processCredit(Credit cri, Model model) {
        log.info("Find credit:=" + cri);
        Optional<Credit> credit = Optional.empty();
        Iterable<Bank> banks =  creditRepository.findByBankAll();
        model.addAttribute("credit", credit);
        model.addAttribute("allTypes", typeCred);
        model.addAttribute("banks", banks);
        return "credits/detailsCredit";
    }

    @PostMapping(value = "/creditOffer")
    public String creditOffer(@RequestParam(name = "clid") Long clid, @RequestParam(name = "crid") Long crid, Model model) {
        log.info("====Find credit id:=" + crid);
        log.info("====Find client id:=" + clid);

        Optional<Client> client = creditRepository.findByClientId(clid);
        log.info("================client:=" + client);
        //
        Optional<CreditOffer> crdOffer = Optional.of(new CreditOffer(client.get()));
        log.info("================offer id:=" + crdOffer.get().getId());
        Optional<Credit> credit = creditRepository.findById(crid);
        crdOffer.get().setCredit(credit.get());

        //генерация случайной суммы кредита
        double min = 100000.0d;
        double max = 99999900.0d;
        max = credit.get().getLimitCredit();
        if(min >= max) { min = 1.0d; }
        Random random = new Random();
        crdOffer.get().setAmount(random.nextDouble() * (max - min) + max);
        crdOffer.get().setMessage("none");
        crdOffer.get().setCompleted(false);
        //
        model.addAttribute("offer", crdOffer.orElse(new CreditOffer()));
        model.addAttribute("cln_name", client.isPresent() ? client.get().getFullName() : "Клиент не найден");
        return "offers/evnOffer";
    }

    @PostMapping(value = "deleteCredit", params = "id")
    public String deleteCredit(Long id) {
        log.info("Delete credit id:=" + id);
        Optional<Credit> cr = creditRepository.findById(id);
        System.out.println("credit:=" + cr);
        creditRepository.deleteById(id);
        return "redirect:/credits.html";
    }

}
