package net.betaengine.jettyexample;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.betaengine.jettyexample.mybatis.domain.User;
import net.betaengine.jettyexample.mybatis.service.FooService;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@SuppressWarnings("serial")
@Singleton
public class SimpleServlet extends HttpServlet {
    private final FooService fooService;
    
    @Inject
    /* default */ SimpleServlet(FooService fooService) {
        this.fooService = fooService;
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

    private void showDatabase(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            User user = fooService.doSomeBusinessStuff("u1");

            resp.getWriter().print(user);
        } catch (Exception e) {
            resp.getWriter().print("There was an error: " + e.getMessage());
        }
    }
}
