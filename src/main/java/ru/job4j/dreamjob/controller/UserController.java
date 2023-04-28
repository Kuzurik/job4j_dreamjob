package ru.job4j.dreamjob.controller;



import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.job4j.dreamjob.model.User;
import ru.job4j.dreamjob.service.user.UserService;

import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService simpleUserService;

    public UserController(UserService simpleUserService) {
        this.simpleUserService = simpleUserService;
    }

    @GetMapping("/register")
    public String getRegistrationPage(Model model) {
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
}
