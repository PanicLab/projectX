package com.github.paniclab.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthenticationServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Внутри сервлета AuthenticationServlet");
        String action = request.getParameter("action");
        if (action == null) action = "register";

        HttpSession session = request.getSession();
        String message = "";
        if(action.equals("login")) {
            message = "Пожалуйста, введите необходимые данные";
            request.setAttribute("on_click_button_action", "Войти");
        }

        if(action.equals("register")) {
            message = "Пожалуйста, зарегистрируйтесь";
            request.setAttribute("on_click_button_action", "Зарегистрироваться");
        }

        request.setAttribute("message", message);
        getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
        System.out.println("Выход из сервлета AuthenticationServlet");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
