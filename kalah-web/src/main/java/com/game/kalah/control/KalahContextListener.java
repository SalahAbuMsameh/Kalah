package com.game.kalah.control;

import javax.inject.Inject;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.game.kalah.core.KalahEngine;

/**
 * Kalah context listener
 */
@WebListener
public class KalahContextListener implements ServletContextListener {

	@Inject
	private KalahEngine engine;

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		engine.start();
		System.out.println("Kalah Game is starting ....");
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		//do nothing
	}
}
