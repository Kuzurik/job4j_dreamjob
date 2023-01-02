package ru.job4j.dreamjob.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@Component
public class AuthFilter implements Filter {

    private final static String LOGIN_PAGE = "loginPage";
    private final static String LOGIN = "login";
    private final static String REG = "registration";
    private final static String REG_FORM = "formRegistration";

    private final Set<String> ends = Set.of(LOGIN_PAGE, LOGIN, REG, REG_FORM);

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String uri = req.getRequestURI();
        if (findEnd(uri)) {
            chain.doFilter(req, res);
            return;
        }
        if (req.getSession().getAttribute("user") == null) {
            res.sendRedirect(req.getContextPath() + "/loginPage");
            return;
        }
        chain.doFilter(req, res);
    }

    public boolean findEnd(String uri) {
        for (String end : ends) {
            if (uri.endsWith(end)) {
                return true;
            }
        }
        return false;
    }
}
