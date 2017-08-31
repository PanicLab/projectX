package com.github.paniclab.servlets;

import com.github.paniclab.models.GameSession;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

public class GameLauncherServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getAnonymousLogger();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Поток управления входит в GameLauncherServlet");

        GameSession gameSession = (GameSession)request.getSession().getAttribute("game");
        if(gameSession == null) {
            gameSession = GameSession.newInstance();
        }
        if (gameSession.isOver()) {
            gameSession = GameSession.newInstance();
        }
        request.getSession().setAttribute("game", gameSession);
        LOGGER.info("Создан новый объект GameSession. Загадано число: " + gameSession.getNumber());

        setInitialDigits(request, gameSession);

        request.getRequestDispatcher("game.jsp").forward(request, response);
        System.out.println("Выход из GameLauncherServlet");
    }

    private void setInitialDigits(HttpServletRequest request, GameSession game) {
        List<String> digits = new ArrayList<>(4);
        Random rndGenerator = new Random();
        while (digits.size() < 4){
            String digit = String.valueOf(rndGenerator.nextInt(9 + 1));
            if(!(digits.contains(digit))) digits.add(digit);
        }

        request.setAttribute("digit_1", Integer.valueOf(digits.get(0)));
        request.setAttribute("digit_2", Integer.valueOf(digits.get(1)));
        request.setAttribute("digit_3", Integer.valueOf(digits.get(2)));
        request.setAttribute("digit_4", Integer.valueOf(digits.get(3)));
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
