package com.game.kalah.model;

import java.util.ArrayList;
import java.util.List;

import com.game.kalah.config.Config;
import com.game.kalah.model.Player.MoveDirection;

/**
 * Player class
 */
public class Player {
	
	private String name;
	private int house;
	private List<Integer> pits;
	
	/** True, if the player currently his turn to move the stones, false otherwise */
	private boolean turn;
	private boolean winner;
	private MoveDirection moveDirection;
	
	public static final int PITS_COUNT = 6;
	
	/**
	 * Init player with the given name
	 * @param name
	 */
	public Player(String name, MoveDirection moveDirection) {
		this(name, moveDirection, false);
	}
	
	/**
	 * Init player with the given name & turn
	 * @param name
	 */
	public Player(String name, MoveDirection moveDirection, boolean turn) {
		
		this.name = name;
		this.turn = turn;
		this.moveDirection = moveDirection;
		
		pits = new ArrayList<Integer>(PITS_COUNT);
		reset();
	}
	
	/**
	 * Take the given pits index stones 
	 * @param pitIndex
	 * @return
	 */
	public int takePitStones(int pitIndex) {
		
		int pitStones = pits.get(pitIndex);
		pits.set(pitIndex, 0);
		
		return pitStones;
	}
	
	/**
	 * Adds stones to the house
	 * @param i
	 */
	public void addToHouse(int stonesCount) {
		this.house += stonesCount;
	}

	/**
	 * reset player pits and house
	 */
	private void reset() {
		
		//reset pits
		for(int i = 0; i < PITS_COUNT; i++) {
			pits.add(Config.getPitSize());
		}
		
		//reset kalah/house
		house = 0;
	}
	
	/**
	 * 
	 * @return true if the move direction is forward, false otherwise
	 */
	public boolean isDirectionForward() {
		return this.moveDirection == MoveDirection.FORWARD;
	}
	
	/**
	 * Players move direction
	 */
	public enum MoveDirection {
		FORWARD, BACKWARD
	}
	
	public String getName() {
		return name;
	}

	public int getHouse() {
		return house;
	}

	public List<Integer> getPits() {
		return pits;
	}

	public boolean isTurn() {
		return turn;
	}

	public void setTurn(boolean turn) {
		this.turn = turn;
	}

	public MoveDirection getMoveDirection() {
		return moveDirection;
	}

	public boolean isWinner() {
		return winner;
	}

	public void setWinner(boolean winner) {
		this.winner = winner;
	}
}
