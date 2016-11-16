package com.game.kalah.control;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.game.kalah.core.KalahEngine;

/**
 * Kalah main servlet which serves all requests
 */
@WebServlet("/home")
public class HttpKalah extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private KalahEngine engine;
	
	private static final String PARAM_ACTION = "move";
	
	private static final String ATT_PLAYER_TURN_NAME = "playerTurnName";
	private static final String ATT_PLAYER_ONE = "playerOneBean";
	private static final String ATT_PLAYER_TWO = "playerTwoBean";
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		String movePitIndex = req.getParameter(PARAM_ACTION);
		
		//call player move action
		if(!StringUtils.isEmpty(movePitIndex)) {
			engine.move(Integer.parseInt(movePitIndex));
		}
		
		populateHomePageInfo(req);
		
		req.getRequestDispatcher("/layout/home.jsp").forward(req, res);
	}

	/**
	 * 
	 * @param req
	 */
	private void populateHomePageInfo(HttpServletRequest req) {
		req.setAttribute(ATT_PLAYER_TURN_NAME, engine.getPlayerTurn());
		req.setAttribute(ATT_PLAYER_TWO, engine.getPlayers().get(0));
		req.setAttribute(ATT_PLAYER_ONE, engine.getPlayers().get(1));
	}
}
