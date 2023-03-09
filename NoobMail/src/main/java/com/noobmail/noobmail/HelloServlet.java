package com.noobmail.noobmail;

import com.noobmail.noobmail.repository.CrudRepository;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.json.JSONException;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
       String s = "hello";
       System.out.println("hello".equals(s));
    }

    public void destroy() {
    }
}