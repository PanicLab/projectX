package com.github.paniclab.servlets;

import com.github.paniclab.models.GameSession;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GameServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Поток управления входит в GameServlet");

        final String attempt = extractAttempt(request);
        GameSession game = (GameSession) request.getSession().getAttribute("game");
        game.makeNewAttempt(attempt);

        System.out.println("GameServlet пробрасывает на game.jsp");
        request.getRequestDispatcher("/game.jsp").forward(request, response);
        System.out.println("Выход из GameServlet");
    }


    private String extractAttempt(HttpServletRequest request) {
        String digit_1 = request.getParameter("digit_1");
        String digit_2 = request.getParameter("digit_2");
        String digit_3 = request.getParameter("digit_3");
        String digit_4 = request.getParameter("digit_4");
        return digit_1 + digit_2 + digit_3 + digit_4;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
