package com.game.kalah.core;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import com.game.kalah.model.Player;
import com.game.kalah.model.Player.MoveDirection;

/**
 * Kalah game engine responsible for all the game actions, rules and players moves
 */
@Singleton
public class KalahEngine {
	
	private List<Player> players = new ArrayList<Player>();
	
	/**
	 * Responsible for:
	 * 1. Creating kalah two players
	 * 2. Reset players
	 */
	public void start() {
		
		//add two players
		players.add(new Player("Player Two", MoveDirection.BACKWARD));
		players.add(new Player("Player One", MoveDirection.FORWARD, true));
	}
	
	/**
	 * Moves the current player stones into the next pits
	 * @param pitIndex
	 */
	public void move(int pitIndex) {
		
		List<Integer> currPlayerPits = getCurrnetTurnPlayer().getPits();
		int pitValue = currPlayerPits.get(pitIndex);
		
		//set by 0
		currPlayerPits.set(pitIndex, 0);
		
		move(pitIndex, pitValue);
	}
	
	/**
	 * Moves the current player stones into the next pits
	 * @param pitIndex
	 */
	public void move(int pitIndex, int pitValue) {
		
		Player currentPlayer = getCurrnetTurnPlayer();
		Player otherPlayer = getOtherPlayer();
		
		//1. add stones to the current player pits
		int currPlayerPitsMove = getCurrentPlayerMovesCount(currentPlayer.getMoveDirection(), pitIndex);
		
		if(pitValue > currPlayerPitsMove) {
			pitValue -= currPlayerPitsMove;
		} else {
			currPlayerPitsMove = pitValue;
			pitValue = 0;
		}
		
		List<Integer> currPlayerPits = currentPlayer.getPits();
		int lastPit;
		
		if(currentPlayer.isDirectionForward()) {
			lastPit = pitIndex + currPlayerPitsMove;
			moveForward(currPlayerPits, (pitIndex + 1), lastPit);
		} else {
			lastPit = pitIndex - currPlayerPitsMove;
			moveBackward(currPlayerPits, (pitIndex - 1), lastPit);
		}
		
		//1.1 check if the last stone and last pit is empty
		if(pitValue == 0 && currPlayerPits.get(lastPit) == 1) {
			
			int oppositePitStones = otherPlayer.takePitStones(lastPit);
			currPlayerPits.set(lastPit, 0);
			currentPlayer.addToHouse(oppositePitStones + 1);
			
			//exist the method
			checkWinner(currentPlayer, otherPlayer);
			switchTurns();
			return;
		}
		
		//1.2 if stones ends in a non-empty current player pit
		if(pitValue == 0) {
			checkWinner(currentPlayer, otherPlayer);
			switchTurns();
			return;
		}
		
		//1.3 add stone in current player house
		pitValue--;
		currentPlayer.addToHouse(1);
		
		//1.4 if ends in player house, return and keep the current player turn
		if(pitValue == 0) {
			checkWinner(currentPlayer, otherPlayer);
			return;
		}
		
		//2. add stone to the other player pits
		int otherPlayerPitsMove = Player.PITS_COUNT;
		
		if(pitValue > otherPlayerPitsMove) {
			pitValue -= otherPlayerPitsMove;
		} else {
			otherPlayerPitsMove = pitValue;
			pitValue = 0;
		}
		
		List<Integer> otherPlayerPits = otherPlayer.getPits();
		
		if(otherPlayer.isDirectionForward()) {
			moveForward(otherPlayerPits, 0, (otherPlayerPitsMove - 1));
		} else {
			moveBackward(otherPlayerPits, (Player.PITS_COUNT - 1), (Player.PITS_COUNT - otherPlayerPitsMove));
		}
		
		//2.1 call game cycle again
		if(pitValue != 0) {
			
			if(currentPlayer.isDirectionForward()) {
				currPlayerPits.set(0, currPlayerPits.get(0) + 1);
				move(0, --pitValue);
			} else {
				currPlayerPits.set(5, currPlayerPits.get(5) + 1);
				move(5, --pitValue);
			}
			
		} else {
			switchTurns();
		}
		
		//2.2 end up
		return;
	}
	
	/**
	 * 
	 * @param currnetPlayer
	 */
	private void checkWinner(Player currentPlayer, Player otherPlayer) {
		
		for(int stones : currentPlayer.getPits()) {
			if(stones != 0) {
				return;
			}
		}
		
		if(currentPlayer.getHouse() > otherPlayer.getHouse()) {
			currentPlayer.setWinner(true);
		} else {
			otherPlayer.setWinner(true);
		}
	}

	/**
	 * 
	 * @param moveDirection
	 * @param pitIndex
	 * @return
	 */
	private int getCurrentPlayerMovesCount(MoveDirection moveDirection, int pitIndex) {
		
		if(MoveDirection.FORWARD == moveDirection) {
			return Player.PITS_COUNT - pitIndex - 1;
		} else {
			return pitIndex;
		}
	}

	/**
	 * 
	 * @param pits
	 * @param from
	 * @param to
	 */
	private void moveForward(List<Integer> pits, int from, int to) {
		
		for(; from <= to; from++) {
			pits.set(from, (pits.get(from) + 1));
		}
	}
	
	/**
	 * 
	 * @param pits
	 * @param from
	 * @param to
	 */
	private void moveBackward(List<Integer> pits, int from, int to) {
		
		for(; from >= to; from--) {
			pits.set(from, (pits.get(from) + 1));
		}
	}
	
	/**
	 * Switchs players turns 
	 */
	private void switchTurns() {
		for(Player player : players) {
			player.setTurn(!player.isTurn());
		}
	}
	
	/**
	 * 
	 * @return current turn player name
	 */
	public String getPlayerTurn() {
		return getCurrnetTurnPlayer().getName();
	}
	
	/**
	 * 
	 * @return
	 */
	private Player getCurrnetTurnPlayer() {
		return getPlayerByTurnStatus(true);
	}
	
	/**
	 * 
	 * @return
	 */
	private Player getOtherPlayer() {
		return getPlayerByTurnStatus(false);
	}
	
	/**
	 * 
	 * @return
	 */
	private Player getPlayerByTurnStatus(boolean turn) {
		
		for(Player player : players) {
			if(player.isTurn() == turn) {
				return player;
			}
		}
		
		return null;
	}

	/**
	 * 
	 * @return
	 */
	public List<Player> getPlayers() {
		return players;
	}
}
