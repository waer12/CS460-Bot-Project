package com.brianstempin.vindiniumclient.bot.simple;

import java.util.*;
import com.brianstempin.vindiniumclient.dto.GameState;

public class GameGraph2 {

	private final Map<GameState.Position, Mine> mines;
	private final Map<GameState.Position, Pub> pubs;
	private final Map<GameState.Position, GameState.Hero> heroesByPosition;
	private final Map<Integer, GameState.Hero> heroesById;
	private final Map<GameState.Position, TileNode> boardGraph;
	private final GameState.Hero me;
	private final int boardSize;

	public GameGraph2(GameState gameState) {
		boardGraph = new HashMap<>();
		mines = new HashMap<>();
		pubs = new HashMap<>();
		heroesById = new HashMap<>();
		heroesByPosition = new HashMap<>();
		GameState.Board board = gameState.getGame().getBoard();
		boardSize = board.getSize();

		for(GameState.Hero currentHero : gameState.getGame().getHeroes()) {
			this.heroesByPosition.put(currentHero.getPos(), currentHero);
			this.heroesById.put(currentHero.getId(), currentHero);
		}

		this.me = gameState.getHero();

		for (int row = 0; row < board.getSize(); row++) {
			for (int col = 0; col < board.getSize(); col++) {
				GameState.Position pos = new GameState.Position(row, col);
				int tileStart = row * board.getSize() * 2 + (col * 2);
				String tileValue = board.getTiles().substring(tileStart, tileStart + 1 + 1);

				if (tileValue.equals("##"))
					continue;

				TileNode v = new TileNode(pos, new ArrayList<TileNode>());

				this.boardGraph.put(pos, v);

				if (tileValue.startsWith("$")) {
					String owner = tileValue.substring(1);
					Mine mine;
					if (owner.equals("-")) {
						mine = new Mine(pos, null);
					} else {
						int ownerId = Integer.parseInt(owner);
						mine = new Mine(pos, this.heroesById.get(ownerId));
					}

					this.mines.put(pos, mine);
				} else if (tileValue.equals("[]")) {
					Pub pub = new Pub(pos);
					this.pubs.put(pos, pub);
				}
			}
		}
	}
	
	public void neighbors(TileNode currentVertex){
		currentVertex.getAdjacent().clear();
		GameState.Position currentVertexPosition = currentVertex.getPosition();
		if(this.mines.containsKey(currentVertexPosition) || this.pubs.containsKey(currentVertexPosition)){

		}else{
			int currentX = currentVertex.getPosition().getX();
			int newX1 = currentX - 1;
			int newX2 = currentX + 1;
			
			if(newX1 >= 0 && newX1 < boardSize) {
				GameState.Position adjacentPos = new GameState.Position(newX1, currentVertex.getPosition().getY());
				TileNode adjacentVertex = this.boardGraph.get(adjacentPos);
				if(adjacentVertex != null)
					currentVertex.getAdjacent().add(adjacentVertex);
			}
			if(newX2 >= 0 && newX2 < boardSize) {
				GameState.Position adjacentPos = new GameState.Position(newX2, currentVertex.getPosition().getY());
				TileNode adjacentVertex = this.boardGraph.get(adjacentPos);
				if(adjacentVertex != null)
					currentVertex.getAdjacent().add(adjacentVertex);
			}
			
			int currentY = currentVertex.getPosition().getY();
			int newY1 = currentY - 1;
			int newY2 = currentY + 1;
			
			if(newY1 >= 0 && newY1 < boardSize) {
				GameState.Position adjacentPos = new GameState.Position(currentVertex.getPosition().getX(), newY1);
				TileNode adjacentVertex = this.boardGraph.get(adjacentPos);
				if(adjacentVertex != null)
					currentVertex.getAdjacent().add(adjacentVertex);
			}
			if(newY2 >= 0 && newY2 < boardSize) {
				GameState.Position adjacentPos = new GameState.Position(currentVertex.getPosition().getX(), newY2);
				TileNode adjacentVertex = this.boardGraph.get(adjacentPos);
				if(adjacentVertex != null)
					currentVertex.getAdjacent().add(adjacentVertex);
			}
		}
	}
	
	public Map<GameState.Position, Mine> getMines() {
		return mines;
	}

	public Map<GameState.Position, Pub> getPubs() {
		return pubs;
	}

	public Map<GameState.Position, GameState.Hero> getHeroesByPosition() {
		return heroesByPosition;
	}

	public Map<Integer, GameState.Hero> getHeroesById() {
		return heroesById;
	}

	public Map<GameState.Position, TileNode> getBoardGraph() {
		return boardGraph;
	}

	public GameState.Hero getMe() {
		return me;
	}
}
