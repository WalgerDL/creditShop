package loans.controller;


import emessage.service.EmailService;
import emessage.service.impl.EmailServiceImpl;
import loans.domain.entity.Client;
import loans.domain.entity.CreditOffer;
import loans.repository.OfferRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;


@Slf4j
@Controller
@RequiredArgsConstructor
public class EmailController {

    @Autowired
    public JavaMailSender emailSender;
    private EmailService emailService;

    private final OfferRepository offerRepository;

    @PostMapping(value = "/sendmail")
    public String email(CreditOffer offer) {
        log.info("=======email offer id:=" + offer.getAmount());
        log.info("=======offer full_name:=" + offer.getFullName());
        //Optional<CreditOffer> offer = offerRepository.findById(id);
        Optional<Client> client = offerRepository.findByClientIdForOffer(offer.getClientId());
        log.info("======email client id:=" + client.get().getId());
        String toEmail = client.get().getEmail();
        log.info("======email to:=" + toEmail);
        String message = "Уважаемый клиент,\n";
        message += "\n Вам предодобрен кредит на сумму\n";

        emailService = new EmailServiceImpl(emailSender);
        emailService.sendSubjectEmail("pivansm@gmail.com", "Предложение по кредиту", message);
        return "redirect:/";
    }


}
