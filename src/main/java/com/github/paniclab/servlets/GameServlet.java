package com.github.paniclab.servlets;

import com.github.paniclab.models.GameSession;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GameServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final String attempt = extractAttemptFrom(request);
        GameSession game = (GameSession) request.getSession().getAttribute("game");
        game.makeNewAttempt(attempt);

        setAttemptDigitsAsRequestAttributes(request);
        request.getRequestDispatcher("/WEB-INF/templates/game.jsp").forward(request, response);
    }

    private String extractAttemptFrom(HttpServletRequest request) {
        String digit_1 = request.getParameter("digit_1");
        String digit_2 = request.getParameter("digit_2");
        String digit_3 = request.getParameter("digit_3");
        String digit_4 = request.getParameter("digit_4");
        return digit_1 + digit_2 + digit_3 + digit_4;
    }

    private void setAttemptDigitsAsRequestAttributes(HttpServletRequest request) {
        String digit_1 = request.getParameter("digit_1");
        request.setAttribute("digit_1", Integer.valueOf(digit_1));

        String digit_2 = request.getParameter("digit_2");
        request.setAttribute("digit_2", Integer.valueOf(digit_2));

        String digit_3 = request.getParameter("digit_3");
        request.setAttribute("digit_3", Integer.valueOf(digit_3));

        String digit_4 = request.getParameter("digit_4");
        request.setAttribute("digit_4", Integer.valueOf(digit_4));
    }



    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
