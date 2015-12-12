package com.brianstempin.vindiniumclient.bot.simple;

import com.brianstempin.vindiniumclient.bot.BotMove;
import com.brianstempin.vindiniumclient.bot.BotUtils;
import com.brianstempin.vindiniumclient.dto.GameState;
import java.util.*;
import java.util.logging.Logger;

public class MyBot2 implements SimpleBot {
	private Logger logger;
	public MyBot2() {
		logger = Logger.getLogger("murderbot");
	}

	public TileNode closestMine(GameGraph2 graph){
		TileNode closestMine = null;
		for (Mine m : graph.getMines().values()) {
			TileNode mine = graph.getBoardGraph().get(m.getPosition());
			if((m.getOwner() == null || m.getOwner().getId() != graph.getMe().getId()) && 
					(closestMine == null || closestMine.getDistance() > mine.getDistance())){
				closestMine = mine;
			}
		}
		return closestMine;
	}

	public TileNode closestTavern(GameGraph2 graph){
		TileNode closestTavern = null;
		for (Pub tavern : graph.getPubs().values()) {
			TileNode pub = graph.getBoardGraph().get(tavern.getPosition());
			if((closestTavern == null || closestTavern.getDistance() > pub.getDistance())){
				closestTavern = pub;
			}
		}
		return closestTavern;
	}

	public TileNode closestPlayer(GameGraph2 graph){
		TileNode player = null;
		for(GameState.Hero hero: graph.getHeroesByPosition().values()){
			TileNode closest= graph.getBoardGraph().get(hero.getPos());
			if(!hero.getPos().equals(graph.getMe().getPos()) && 
					(player == null || player.getDistance() > closest.getDistance())){
				player = closest;
			}
		}

		return player;
	}

	public TileNode lowestHealth(GameGraph2 graph){
		TileNode lowestPlayer = null;
		GameState.Hero heroLowest = null;
		for(GameState.Hero hero: graph.getHeroesByPosition().values()){
			if(!hero.getPos().equals(graph.getMe().getPos()) && hero.getLife() < graph.getMe().getLife() 
					&& (heroLowest == null || heroLowest.getLife() > hero.getLife())){
				heroLowest = hero;
			}
		}

		if(heroLowest != null){
			lowestPlayer = graph.getBoardGraph().get(heroLowest.getPos());
		}
		return lowestPlayer;
	}

	public TileNode highestGold(GameGraph2 graph){
		TileNode player = null;
		GameState.Hero gold = null;
		for(GameState.Hero hero: graph.getHeroesByPosition().values()){
			if(gold == null || gold.getGold() < hero.getGold()){
				gold = hero;
			}
		}
		if(gold != null){
			player = graph.getBoardGraph().get(gold.getPos());
		}
		return player;
	}

	@Override
	public BotMove move(GameState gameState) {
		logger.info("Starting move");
		GameGraph2 graph2 = new GameGraph2(gameState);
		AStar astar = new AStar();
		Dijkstra dijkstra = new Dijkstra();

		dijkstra.doDijkstra2(graph2);
		TileNode me = graph2.getBoardGraph().get((graph2.getMe().getPos()));

		TileNode mine = closestMine(graph2);
		TileNode tavern = closestTavern(graph2);
		TileNode player = lowestHealth(graph2);
		TileNode closest = closestPlayer(graph2);
		TileNode highest = highestGold(graph2);

		//If I have the highest Gold
		if(highest.equals(me)){
			if(tavern.getDistance() > 1){
				astar.doAStar2(graph2, tavern);
				TileNode move = getPath(tavern).get(0);
				logger.info("Moving: " + BotUtils.directionTowards(gameState.getHero().getPos(), move.getPosition()));
				return BotUtils.directionTowards(gameState.getHero().getPos(), move.getPosition());
			}
			else if(gameState.getHero().getLife() < 15){
				astar.doAStar2(graph2, tavern);
				TileNode move = getPath(tavern).get(0);
				logger.info("Moving: " + BotUtils.directionTowards(gameState.getHero().getPos(), move.getPosition()));
				return BotUtils.directionTowards(gameState.getHero().getPos(), move.getPosition());
			}
			else{
				return BotMove.STAY;
			}
		}
		//If Low on Health
		else if(gameState.getHero().getLife() <= 20 && gameState.getHero().getGold() >= 2){
			astar.doAStar2(graph2, tavern);
			TileNode move = getPath(tavern).get(0);
			logger.info("Moving: " + BotUtils.directionTowards(gameState.getHero().getPos(), move.getPosition()));
			return BotUtils.directionTowards(gameState.getHero().getPos(), move.getPosition());
		}
		else if(tavern.getDistance() < 2 && gameState.getHero().getLife() <= 80){
			astar.doAStar2(graph2, tavern);
			TileNode move = getPath(tavern).get(0);
			logger.info("Moving: " + BotUtils.directionTowards(gameState.getHero().getPos(), move.getPosition()));
			return BotUtils.directionTowards(gameState.getHero().getPos(), move.getPosition());
		}
		//If enemy is Close
		else if(closest.getDistance() == 1){
			GameState.Hero close = graph2.getHeroesByPosition().get(closest.getPosition());
			if(close.getLife() >= graph2.getMe().getLife()){
				astar.doAStar2(graph2, tavern);
				TileNode move = getPath(tavern).get(0);
				logger.info("Moving: " + BotUtils.directionTowards(gameState.getHero().getPos(), move.getPosition()));
				return BotUtils.directionTowards(gameState.getHero().getPos(), move.getPosition());
			}else{
				//astar.doAStar2(graph2, player);
				TileNode move = getPath(player).get(0);
				logger.info("Moving: " + BotUtils.directionTowards(gameState.getHero().getPos(), move.getPosition()));
				return BotUtils.directionTowards(gameState.getHero().getPos(), move.getPosition());
			}
		}
		//Capture Mines
		else if(mine != null){
			astar.doAStar2(graph2, mine);
			TileNode move = getPath(mine).get(0);
			logger.info("Moving: " + BotUtils.directionTowards(gameState.getHero().getPos(), move.getPosition()));
			return BotUtils.directionTowards(gameState.getHero().getPos(), move.getPosition());
		}
		//Attack Enemy with low health
		else if(player != null){
			astar.doAStar2(graph2, player);
			TileNode move = getPath(player).get(0);
			logger.info("Moving: " + BotUtils.directionTowards(gameState.getHero().getPos(), move.getPosition()));
			return BotUtils.directionTowards(gameState.getHero().getPos(), move.getPosition());
		}
		else{
			return BotMove.STAY;
		}
	}

	public static List<TileNode> getPath(TileNode target) {
		List<TileNode> path = new ArrayList<TileNode>();
		path.add(target);
		TileNode next = target;
		while(next.getPrevious().getDistance() != 0){
			path.add(next.getPrevious());
			next = next.getPrevious();
		}

		Collections.reverse(path);
		return path;
	}

	@Override
	public void setup() {
		// No-op
	}

	@Override
	public void shutdown() {
		// No-op
	}
}
