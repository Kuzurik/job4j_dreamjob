package ru.job4j.dreamjob.controller;



import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.job4j.dreamjob.model.User;
import ru.job4j.dreamjob.service.user.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@ThreadSafe
@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService simpleUserService;

    public UserController(UserService simpleUserService) {
        this.simpleUserService = simpleUserService;
    }

    @GetMapping("/register")
    public String getRegistrationPage(Model model, HttpSession session) {
        return "users/register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user, Model model) {
        try {
            var result = simpleUserService.save(user);
            if (result.isEmpty()) {
                model.addAttribute("message", "Пользователь с таким именем уже существует");
                return "errors/404";
            }
            return "users/created";
        } catch (Exception e) {
            model.addAttribute("msg", e.getMessage());
            return "errors/404";
        }
    }

    @GetMapping("/login")
    public String getLoginPage(Model model, HttpSession session) {
        return "users/login";
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute User user, Model model, HttpServletRequest request) {
        var userOptional = simpleUserService
                .findByEmailAndPassword(user.getEmail(), user.getPassword());
        if (userOptional.isEmpty()) {
            model.addAttribute("error", "Почта или пароль введены неверно");
            return "users/login";
        }
        var session = request.getSession();
        session.setAttribute("user", userOptional.get());
        return "redirect:/vacancies";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/users/login";
    }
}
