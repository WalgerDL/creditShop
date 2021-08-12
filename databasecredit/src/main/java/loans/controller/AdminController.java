package loans.controller;

import loans.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class AdminController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/admin")
    public String userList(Model model) {
        log.info("List user=================================");
        model.addAttribute("users", userRepository.findAll());
        return "admin/listUser";
    }

    @GetMapping("/error403")
    public String error403() {
        log.info("====user permission to access this page=====");
        return "error403/err403";
    }

}
