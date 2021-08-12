package loans.controller;

import loans.domain.entity.User;
import loans.service.SecurityService;
import loans.service.UserService;
import loans.validator.UserValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;


    @GetMapping("/registration")
    public String registration(Model model) {
        log.info("Register For user=========================");
        if (securityService.isAuthenticated()) {
            return "redirect:/";
        }
        model.addAttribute("userForm", new User());
        return "register/registration";
    }

    @GetMapping("/login")
    public String login(Model model, String error, String logout) {
        if (securityService.isAuthenticated()) {
            return "redirect:/";
        }
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");
        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");
        return "login";
    }

    @GetMapping({"/"})
    public String welcome(Model model) {
        log.info("welcome=========");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            log.info("welcome=========" + auth.getPrincipal());
        }
        return "/index";
    }

    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout";
    }

    @PostMapping("registration")
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult) {
        log.info("Register For user save=========================");
        userValidator.validate(userForm, bindingResult);
        log.info("User:=" + userForm);

        if (bindingResult.hasErrors()) {
            return "register/registration";
        }
        userService.save(userForm);
        securityService.autoLogin(userForm.getUsername(), userForm.getPasswordConfirm());
        return "redirect:/";
    }

}
