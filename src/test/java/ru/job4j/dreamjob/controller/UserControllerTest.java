package ru.job4j.dreamjob.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.ui.ConcurrentModel;
import ru.job4j.dreamjob.model.User;
import ru.job4j.dreamjob.service.user.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserControllerTest {

    private UserService userService;
    private UserController userController;
    private HttpServletRequest request;
    private HttpSession session;

    @BeforeEach
    public void initService() {
        userService = mock(UserService.class);
        userController = new UserController(userService);
        request = mock(HttpServletRequest.class);
        session = mock(HttpSession.class);
    }

    @Test
    public void whenRequestRegisterThenGetPageIndex() {
        var user = new User(1, "some@mail.ru", "name", "password");
        var userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        when(userService.save(userArgumentCaptor.capture())).thenReturn(Optional.of(user));
        when(request.getSession()).thenReturn(session);

        var model = new ConcurrentModel();
        var view = userController.register(model, user, request);
        var actualUser = userArgumentCaptor.getValue();

        assertThat(view).isEqualTo("redirect:/index");
        assertThat(actualUser).isEqualTo(user);
    }

    @Test
    public void whenRequestRegisterThenGetPageError() {
        when(userService.save(any())).thenReturn(Optional.empty());

        var model = new ConcurrentModel();
        var view = userController.register(model, new User(), request);
        var actualUser = model.getAttribute("message");

        assertThat(view).isEqualTo("errors/404");
        assertThat(actualUser).isEqualTo("Пользователь с такой почтой уже существует");
    }

    @Test
    public void whenRequestLoginThenGetPageVacancies() {
        var user = new User(1, "some@mail.ru", "name", "password");

        when(userService.findByEmailAndPassword(any(String.class),
                any(String.class))).thenReturn(Optional.of(user));
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);

        var model = new ConcurrentModel();
        var view = userController.loginUser(user, model, request);
        var actualUser = session.getAttribute("user");

        assertThat(view).isEqualTo("redirect:/vacancies");
        assertThat(actualUser).isEqualTo(user);
    }

    @Test
    public void whenRequestLoginThenGetPageError() {
        when(userService.findByEmailAndPassword(anyString(),
                anyString())).thenReturn(Optional.empty());

        var model = new ConcurrentModel();
        var view = userController.loginUser(new User(), model, request);
        var actualUser = model.getAttribute("error");

        assertThat(view).isEqualTo("users/login");
        assertThat(actualUser).isEqualTo("Почта или пароль введены неверно");
    }

    @Test
    public void whenRequestLogoutThenGetPageLogin() {
        assertThat(userController.logout(session)).isEqualTo("redirect:/users/login");
    }

    @Test
    public void whenRequestRegisterThenGetPageRegister() {
        assertThat(userController.getRegistrationPage()).isEqualTo("users/register");
    }

    @Test
    public void whenRequestLoginThenGetPageLogin() {
        assertThat(userController.getLoginPage()).isEqualTo("users/login");
    }

}