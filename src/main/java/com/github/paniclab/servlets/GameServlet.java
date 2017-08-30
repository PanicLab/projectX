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
        GameSession game = (GameSession) request.getSession().getAttribute("game");

        System.out.println("GameServlet пробрасывает на game.jsp");
        request.getRequestDispatcher("/game.jsp").forward(request, response);
        System.out.println("Выход из GameServlet");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
