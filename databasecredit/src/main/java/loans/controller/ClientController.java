package loans.controller;

import loans.domain.entity.Bank;
import loans.domain.entity.Client;
import loans.domain.entity.Credit;
import loans.domain.entity.CreditOffer;
import loans.repository.ClientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Slf4j
@Controller
public class ClientController {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientController(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @GetMapping(value="clientForm")
    public String thankYou(@ModelAttribute Client client, Model model) {
        model.addAttribute("client", client);
        return "clientForm";
    }

    @GetMapping(value = "/clients.html", params = "id")
    private String getClientId(Long id, Model model) {
        System.out.println("id:=" + id);
        Optional<Client> cl = clientRepository.findById(id);
        System.out.println("client:=" + cl);
        Iterable<Bank> banks =  clientRepository.findByBankAll();
        clientRepository.findById(id)
                .ifPresent(client -> model.addAttribute("client", client));
        model.addAttribute("banks", banks);
        return "clients/detailsClient";
    }


    @PostMapping(value="/clients.html")
    public String addClient(@ModelAttribute Client newClient, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            log.error("BINDING RESULT ERROR:=" + bindingResult.toString());
            return "/clients.html";
        } else {
            model.addAttribute("client", newClient);
            if (newClient.getFullName() != null) {
                clientRepository.save(newClient);
                System.out.println("new client added: " + newClient);
            }
            return "redirect:/clients.html";
        }
    }

    @GetMapping("/clients.html")
    public String all(Model model) {
        log.info("=======clients");
        model.addAttribute("clients", clientRepository.findAll());
        return "clients/listClient";
    }

    @GetMapping(value = "bankClients", params = "id")
    public String allBankId(Long id, Model model) {
        log.info("======clients + bank:=" + id);
        model.addAttribute("clients", clientRepository.findByBankRef(id));
        return "clients/listClient";
    }

    @PostMapping("/client")
    public String processClient(Client cli, Model model) {
        log.info("Find client:=" + cli.getFullName());
        Optional<Client> client = clientRepository.findByFullName(cli.getFullName());
        System.out.println("client:=" + client);
        Iterable<Bank> banks =  clientRepository.findByBankAll();
        model.addAttribute("client", client.orElse(new Client()));
        model.addAttribute("banks", banks);
        return "clients/detailsClient";
    }

    @PostMapping(value = "deleteClient", params = "id")
    public String deleteCredit(Long id) {
        log.info("Delete client id:=" + id);
        Optional<Client> cl = clientRepository.findById(id);
        System.out.println("credit:=" + cl);
        clientRepository.deleteById(id);
        return "redirect:/clients.html";
    }


    @PostMapping(value = "offers.html")
    public String processOffer(Client cli, Model model) {

        log.info("================Offer client id:=" + cli.getId());
        Optional<Client> client = clientRepository.findById(cli.getId());
        log.info("================client:=" + client);
        //
        Optional<CreditOffer> crdOffer = Optional.of(new CreditOffer(client.get()));

        //выбор кредита случайно
        List<Long> idCredit = clientRepository.findByCreditIdAll();
        Optional<Long> crdId = idCredit.stream().skip((int) (idCredit.size() * Math.random())).findAny();
        log.info("========credit id:=" + crdId);
        Optional<Credit> credit = clientRepository.findByCreditId(crdId.get());
        crdOffer.get().setCredit(clientRepository.findByCreditId(crdId.get()).get());

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
        model.addAttribute("cln_name", client.get().getFullName());
        model.addAttribute("offer", crdOffer);
        return "offers/detailsOffer";
    }

}
