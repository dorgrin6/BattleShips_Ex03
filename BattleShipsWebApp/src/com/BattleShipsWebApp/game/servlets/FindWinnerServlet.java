package com.BattleShipsWebApp.game.servlets;

import BattleShipsEngine.engine.Game;
import BattleShipsEngine.engine.Player;
import com.BattleShipsWebApp.constants.Constants;
import com.BattleShipsWebApp.mainGamesRoom.gameRecordsManager.GameRecord;
import com.BattleShipsWebApp.mainGamesRoom.gameRecordsManager.GameRecordsManager;
import com.BattleShipsWebApp.utils.ServletUtils;
import com.BattleShipsWebApp.utils.SessionUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "FindWinnerServlet", urlPatterns = {"/game/findWinner"})
public class FindWinnerServlet extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");

        Player.Type winner = null;

        // needed params
        final String gameNameParameter = SessionUtils.getSessionGameName(request);
        final GameRecordsManager gameRecordsManager = ServletUtils.getGameRecordsManager(getServletContext());

        // get game
        final GameRecord gameRecord = gameRecordsManager.getGameByName(gameNameParameter);

        // check game version
        if (gameRecord.getVersion() < SessionUtils.getGameVersion(request)){
            return;
        }

        Game game = gameRecord.getGame();
        if (gameRecord.getWinner() != null) {
            winner = gameRecord.getWinner();
        } else if (game.getWinner() != null) {
            winner = game.getWinner();
            gameRecord.setWinner(winner);
        }

        try (PrintWriter out = response.getWriter()) {
            if (winner != null) {
                String username = gameRecord.getUsernameByPlayerType(winner);
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty(Constants.GAME_WINNER_ATTRIBUTE, username);
                jsonObject.addProperty(Constants.USERNAME, SessionUtils.getSessionUsername(request));
                out.println(new Gson().toJson(jsonObject));
                out.flush();
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
