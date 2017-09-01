package com.github.paniclab.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class WelcomeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Вход в WelcomeServlet");

        System.out.println("WelcomeServlet пробрасывает на index.jsp");
        getServletContext().getRequestDispatcher("/WEB-INF/templates/index.jsp").forward(request,response);
        System.out.println("Выход из WelcomeServlet");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
