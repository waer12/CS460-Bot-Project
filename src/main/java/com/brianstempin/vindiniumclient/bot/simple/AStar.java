package com.brianstempin.vindiniumclient.bot.simple;

import java.util.*;

public class AStar {

	public AStar() {

	}

	public double heuristic(TileNode n, TileNode target){
		double nX = n.getPosition().getX();
		double nY = n.getPosition().getY();
		double goalX = target.getPosition().getX();
		double goalY = target.getPosition().getY();
		double ssX = Math.pow((nX - goalX), 2);
		double ssY = Math.pow((nY - goalY), 2);
		double result = Math.sqrt((ssX+ssY));

		return result;
	}

	public void doAStar2(GameGraph2 graph, TileNode target){
		PriorityQueue<TileNode> open = new PriorityQueue<>(new Comparator<TileNode>(){
			@Override
			public int compare(TileNode x, TileNode y){
				if(x.getHeuristic() > y.getHeuristic()){
					return 1;
				}else if(x.getHeuristic() < y.getHeuristic()){
					return -1;
				}else{
					return 0;
				}
			}
		});	

		Set<TileNode> closed = new HashSet<>();

		TileNode me = graph.getBoardGraph().get(graph.getMe().getPos());
		me.setDistance(0.0);
		me.setHeuristic(0.0);
		me.setPrevious(null);

		open.add(me);

		//Start loop
		while(!open.isEmpty()){
			TileNode u = open.remove();
			if(u.equals(target)){
				break;
			}
			graph.neighbors(u);
			closed.add(u);
			for(TileNode v: u.getAdjacent()){
				if(closed.contains(v)){
					continue;
				}
				double distanceV = u.getDistance() + 1;
				if(!open.contains(v)){
					double func = distanceV + heuristic(v, target);
					v.setPrevious(u);
					v.setDistance(distanceV);
					v.setHeuristic(func);
					open.add(v);
				}else if(distanceV >= v.getDistance()){
					continue;
				}
			}
		}
		//End Loop
	}
}
