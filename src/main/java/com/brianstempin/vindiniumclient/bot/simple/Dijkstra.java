package com.brianstempin.vindiniumclient.bot.simple;

import java.util.*;

public class Dijkstra {

	public Dijkstra() {

	}

	public void doDijkstra2(GameGraph2 graph) {
		PriorityQueue<TileNode> nodeQueue = new PriorityQueue<TileNode>();

		TileNode me = graph.getBoardGraph().get(graph.getMe().getPos());
		me.setDistance(0);
		me.setPrevious(null);
		nodeQueue.add(me);

		for(TileNode node: graph.getBoardGraph().values()){
			if(!node.equals(me)){
				node.setDistance(Double.POSITIVE_INFINITY);
				node.setPrevious(null);
				nodeQueue.add(node);
			}
		}

		//Start Loop
		while (!nodeQueue.isEmpty()) {
			TileNode u = nodeQueue.poll();
			graph.neighbors(u);
			for (TileNode v : u.getAdjacent()){
				if(nodeQueue.contains(v)){
					double alt = u.getDistance() + 1;
					if (alt < v.getDistance()) {
						v.setDistance(alt);
						v.setPrevious(u);
						nodeQueue.remove(v);
						nodeQueue.add(v);
					}
				}
			}
		}
		//End Loop
	}
}
