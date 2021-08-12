package loans.controller;

import loans.domain.entity.Bank;
import loans.repository.BankRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

/**
 * Контроллер для Bank
 * @author Ivan
 */
@Slf4j
@Controller
public class BankController {

    private final BankRepository bankRepository;

    @Autowired
    public BankController(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    @GetMapping("/banks.html")
    public String allBank(Model model) {
        log.info("banks");
        Iterable<Bank> iterable = bankRepository.findAll();
        for (Bank b: iterable) {
            if(b.getCredits() != null) {
                System.out.println(" " + b);
            }
        }
        model.addAttribute("banks", bankRepository.findAll());
        return "banks/listBanks";
    }

    @GetMapping(value = "/banks.html", params = "id")
    private String get(Long id, Model model) {
        System.out.println("id:=" + id);
        Optional<Bank> b = bankRepository.findById(id);
        bankRepository.findById(id)
                .ifPresent(bank -> model.addAttribute("bank", bank));
        System.out.println("bank:=" + b);
        return "banks/detailsBank";
    }

    @PostMapping("/banks.html")
    public String processBank(Bank bank) {
        log.info("Bank submitted" + bank);
        bankRepository.save(bank);
        return "redirect:/banks.html";
    }

    @PostMapping("bank")
    public String processCredit(Bank bk, Model model) {
        log.info("Find bank:=" + bk);
        Optional<Bank> bank = Optional.empty();
        model.addAttribute("bank", bank);
        return "banks/detailsBank";
    }

    @PostMapping(value = "deleteBank", params = "id")
    public String deleteCredit(Long id) {
        log.info("Delete bank id:=" + id);
        Optional<Bank> bank = bankRepository.findById(id);
        System.out.println("bank:=" + bank);
        bankRepository.deleteById(id);
        return "redirect:/banks.html";
    }

}
