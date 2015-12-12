package com.brianstempin.vindiniumclient.bot.simple;

import com.brianstempin.vindiniumclient.dto.GameState;

public class Mine {
	private GameState.Position position;
	private GameState.Hero owner;

	public Mine(GameState.Position position, GameState.Hero owner) {
		this.position = position;
		this.owner = owner;
	}

	public GameState.Position getPosition() {
		return position;
	}

	public GameState.Hero getOwner() {
		return owner;
	}

	public void setOwner(GameState.Hero hero) { 
		this.owner = hero; 
	}

}
