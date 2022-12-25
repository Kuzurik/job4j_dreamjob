package ru.job4j.dreamjob;

import ru.job4j.dreamjob.model.User;

import javax.servlet.http.HttpSession;

public class ValidateUser {
    public static User validateUser(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setEmail("Гость");
        }
        return user;
    }
}
