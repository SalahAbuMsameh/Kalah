package com.game.kalah.config;

/**
 * Kalah configurations
 */
public class Config {
	
	/**
	 * Pit size
	 * @return pit size
	 */
	public static int getPitSize() {
		//since the game can be play with two version, one with 6 pit size and the other one which with pit size 4
		//this class will helps to support two version via config file
		return 6;
	}

}
