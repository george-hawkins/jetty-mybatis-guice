package net.betaengine.jettyexample;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.betaengine.jettyexample.mybatis.domain.User;
import net.betaengine.jettyexample.mybatis.service.UserService;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@SuppressWarnings("serial")
@Singleton
public class SimpleServlet extends HttpServlet {
    private final UserService userService;
    
    @Inject
    SimpleServlet(UserService userService) {
        this.userService = userService;
    }
    

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (req.getRequestURI().endsWith("/db")) {
            showDatabase(req, resp);
        } else {
            showHome(req, resp);
        }
    }

    private void showHome(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.getWriter().print("Hello from Java!");
    }
    
    private User insertUser() {
        User user = new User();
        user.setEmailId(UUID.randomUUID() + "@x.com");
        user.setPassword("secret");
        user.setFirstName("TestFirstName");
        user.setLastName("TestLastName");

        userService.insertUser(user);

        return user;
    }

    private void showDatabase(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            User user = insertUser();

            resp.getWriter().print(userService.getUserById(user.getUserId()));
        } catch (Exception e) {
            resp.getWriter().print("There was an error: " + e.getMessage());
        }
    }
}
