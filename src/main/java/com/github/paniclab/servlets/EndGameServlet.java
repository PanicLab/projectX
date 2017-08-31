package com.github.paniclab.servlets;

import com.github.paniclab.models.GameSession;
import com.github.paniclab.models.User;
import com.github.paniclab.services.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

public class EndGameServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getAnonymousLogger();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processGameResult(request);
        request.getRequestDispatcher("win.jsp").forward(request, response);
    }

    private void processGameResult(HttpServletRequest request) {
        GameSession game = (GameSession)request.getSession().getAttribute("game");
        User user = (User)request.getSession().getAttribute("user");
        int gameResult = game.getStageCount();

        user.setLastResult(gameResult);
        if(user.getBestResult() > gameResult) user.setBestResult(gameResult);
        float newAverageResult = (user.getAverageResult() * user.getAttemptsCount() + gameResult) /
                (user.getAttemptsCount() + 1);
        user.setAverageResult(newAverageResult);
        user.setAttemptsCount(user.getAttemptsCount() + 1);

        UserService service = UserService.newInstance(request);
        service.persist(user);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
