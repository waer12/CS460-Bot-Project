package com.brianstempin.vindiniumclient.bot.simple;

import java.util.*;
import com.brianstempin.vindiniumclient.dto.GameState;

public class GameGraph {
	private List<TileNode> nodes;
	private TileNode[][] gameMatrix;
	private TileNode me;
	private int boardSize;
	private Map<GameState.Position, TileNode> playersMap;
	private List<TileNode> walls;

	public GameGraph(GameState.Board board, GameState.Hero hero){
		List<TileNode> nodeList = new ArrayList<TileNode>();
		TileNode[][] boardMatrix = new TileNode[board.getSize()][board.getSize()];
		TileNode player = new TileNode();
		Map<GameState.Position, TileNode> players = new HashMap<>();
		List<TileNode> wall = new ArrayList<>();
		boardSize = board.getSize();

		for (int row = 0; row < boardSize; row++) {
			for (int col = 0; col < boardSize; col++) {
				TileNode tile = new TileNode();
				GameState.Position pos = new GameState.Position(row, col);
				int tileStart = row * board.getSize() * 2 + (col * 2);
				String tileValue = board.getTiles().substring(tileStart, tileStart + 1 + 1);
				tile.setTileType(tileValue);
				tile.setPosition(pos);

				if (tile.getPosition().getX() == hero.getPos().getX() && tile.getPosition().getY() == hero.getPos().getY()){
					player = tile;
				}

				if(tileValue.equals("##")){
					wall.add(tile);
				}
				else if(tileValue.startsWith("@")){
					players.put(tile.getPosition(), tile);
				}

				boardMatrix[row][col] = tile;
				nodeList.add(tile);
			}
		}

		playersMap = players;
		walls = wall;
		nodes = nodeList;
		gameMatrix = boardMatrix;
		me = player;
	}

	public List<TileNode> getNeighbors(TileNode tileNode){
		List<TileNode> neighbors = new ArrayList<TileNode>();

		if(tileNode.getPosition().getX() < boardSize-1){
			TileNode bottom = gameMatrix[tileNode.getPosition().getX() + 1][tileNode.getPosition().getY()];
			if(!walls.contains(bottom) || !bottom.getTileType().equals("[]") || !bottom.getTileType().startsWith("$")){
				neighbors.add(bottom);
			}
		}

		if(tileNode.getPosition().getY() < boardSize-1){
			TileNode right = gameMatrix[tileNode.getPosition().getX()][tileNode.getPosition().getY() + 1];
			if(!walls.contains(right) || !right.getTileType().equals("[]") || !right.getTileType().startsWith("$")){
				neighbors.add(right);
			}
		}

		if(tileNode.getPosition().getX() > 0){
			TileNode top = gameMatrix[tileNode.getPosition().getX() - 1][tileNode.getPosition().getY()];
			if(!walls.contains(top) || !top.getTileType().equals("[]") || !top.getTileType().startsWith("$")){
				neighbors.add(top);
			}
		}

		if(tileNode.getPosition().getY() > 0){
			TileNode left = gameMatrix[tileNode.getPosition().getX()][tileNode.getPosition().getY() - 1];
			if(!walls.contains(left) || !left.getTileType().equals("[]") || !left.getTileType().startsWith("$")){
				neighbors.add(left);
			}
		}
		return neighbors;
	}

	public List<TileNode> getNodeList(){
		return nodes;
	}

	public TileNode getMe(){
		return me;
	}

	public int getBoardSize(){
		return boardSize;
	}

	public Map<GameState.Position, TileNode> getPlayersMap(){
		return playersMap;
	}

	public List<TileNode> getWalls(){
		return walls;
	}
}
