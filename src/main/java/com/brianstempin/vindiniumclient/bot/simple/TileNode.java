package com.brianstempin.vindiniumclient.bot.simple;

import java.util.List;

import com.brianstempin.vindiniumclient.dto.GameState;

public class TileNode implements Comparable<TileNode>{
	private String tileType;
	private List<TileNode> adjacent;
	private double distance;
	private double heuristic;
	private TileNode previous;
	private GameState.Position position;

	public TileNode(){
	
	}
	
	public TileNode(GameState.Position pos, List<TileNode> list){
		this.position = pos;
		this.adjacent = list;
	}

	public String getTileType(){
		return tileType;
	}

	public void setTileType(String tileType){
		this.tileType = tileType;
	}

	public double getDistance(){
		return distance;
	}

	public void setDistance(double distance){
		this.distance = distance;
	}

	public double getHeuristic(){
		return heuristic;
	}

	public void setHeuristic(double heuristic){
		this.heuristic = heuristic;
	}

	public TileNode getPrevious(){
		return previous;
	}

	public void setPrevious(TileNode previous){
		this.previous = previous;
	}

	public GameState.Position getPosition(){
		return position;
	}

	public void setPosition(GameState.Position position){
		this.position = position;
	}
	
	public List<TileNode> getAdjacent(){
		return adjacent;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof TileNode)) return false;

		TileNode node = (TileNode) o;

		return getPosition().equals(node.getPosition());

	}

	@Override
	public int compareTo(TileNode o) {
		return Double.compare(getDistance(), o.getDistance());
	}
}
