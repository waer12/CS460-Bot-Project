package com.brianstempin.vindiniumclient.bot.simple;

import com.brianstempin.vindiniumclient.dto.GameState;

public class Pub {

	private GameState.Position position;

	public Pub(GameState.Position position){
		this.position = position;
	}

	public GameState.Position getPosition() {
		return position;
	}

}
