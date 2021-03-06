package com.BattleShipsWebApp.mainGamesRoom.servlets.gamesManagement;

import com.BattleShipsWebApp.constants.Constants;
import com.BattleShipsWebApp.exceptions.GameRecordSizeException;
import com.BattleShipsWebApp.mainGamesRoom.gameRecordsManager.GameRecord;
import com.BattleShipsWebApp.mainGamesRoom.gameRecordsManager.GameRecordsManager;
import com.BattleShipsWebApp.utils.ServletUtils;
import com.BattleShipsWebApp.utils.SessionUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "DeleteGameRecordServlet", urlPatterns = {"/BattleShipsWebApp_war/gamesRoom/gameRecords/deleteGameRecord"})
public class DeleteGameRecordServlet extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // needed params
        final String gameNameParameter = request.getParameter(Constants.GAME_NAME_ATTRIBUTE_NAME);
        final String userName = SessionUtils.getSessionUsername(request);

        GameRecordsManager gameRecordsManager = ServletUtils.getGameRecordsManager(getServletContext());
        final GameRecord gameRecord = gameRecordsManager.getGameByName(gameNameParameter);

        try {
            if (gameRecord.getCreator().getUserName().equals(userName)){
                gameRecordsManager.removeGameRecord(gameNameParameter);
            }
            else{
                // non-creator trying to delete
                //
            }
        } catch (GameRecordSizeException e) {
            // handle
            //e.printStackTrace();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request  servlets request
     * @param response servlets response
     * @throws ServletException if a servlets-specific error occurs
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
     * @param request  servlets request
     * @param response servlets response
     * @throws ServletException if a servlets-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlets.
     *
     * @return a String containing servlets description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}