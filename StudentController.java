package ufl.cs1.controllers;

import game.controllers.DefenderController;
import game.models.Attacker;
import game.models.Defender;
import game.models.Game;
import game.models.Node;

import java.util.List;

public final class StudentController implements DefenderController {
	public void init(Game game) {
	}

	public void shutdown(Game game) {
	}


	public int[] update(Game game, long timeDue) {
		int[] actions = new int[Game.NUM_DEFENDER];
		List<Defender> enemies = game.getDefenders();


		actions[0] = ghost1(game, enemies.get(0));
		actions[1] = ghost1(game, enemies.get(1));
		actions[2] = chasePac1(game, enemies.get(2));
		actions[3] = defendPowerpill(game, enemies.get(3));


		return actions;
	}

	public int chasePac1(Game game, Defender ghost) {

		int action;
		Attacker pacman = game.getAttacker();
		List<Node> powerPills = game.getPowerPillList();
		List<Node> pacNeighbors = pacman.getLocation().getNeighbors();

		if (ghost.isVulnerable()) {

			Node closestPowerPillToPac = pacman.getTargetNode(powerPills, true);
			boolean thereORNot = game.checkPowerPill(closestPowerPillToPac);

			if (thereORNot) {


				List<Node> pathtoPillNextToPac = ghost.getPathTo(closestPowerPillToPac);
				Node farthestNodeToPath = ghost.getTargetNode(pathtoPillNextToPac, false);


				for (int j = 0; j < pacNeighbors.size(); j++) {
					if (pacNeighbors.get(j) == closestPowerPillToPac) {

						action = ghost.getNextDir(farthestNodeToPath, false);

						return action;
					}
				}
			} else {

			action = ghost.getNextDir(pacman.getLocation(), false);
			return action;
			}
		} else {

			action = ghost.getNextDir(pacman.getLocation(), true);
			return action;
		}
		return 0;
	}


	public int defendPowerpill(Game game, Defender ghost) {

		int action;
		Attacker pacman= game.getAttacker();
		List<Node> powerPillList=game.getPowerPillList();
		List<Node> normPills =  game.getPillList();									// returns the list of normals pills
		List<Node> pacmanPath = ghost.getPathTo(pacman.getLocation());				// returns the list of nodes to wherever pacman is
		Node closestPowerPillToPac = pacman.getTargetNode(powerPillList, true);	// returns the closest powerpill node to pacman
		List <Node> ghostNeighbors = ghost.getLocation().getNeighbors();			// returns the list of nodes that neighbor a ghosts current location


		if(ghost.isVulnerable()){

			for (int i = 0; i < pacmanPath.size(); i++){
				if (ghost.getLocation() == pacmanPath.get(i)){
					action = ghost.getReverse();
					return action;
				}
			}
		}

		else {
			for (int g = 0; g < ghostNeighbors.size(); g++) {
				boolean thereORNot = game.checkPowerPill(closestPowerPillToPac);
				if (thereORNot) {
					if (closestPowerPillToPac == ghostNeighbors.get(g)) {
						ghost.isVulnerable();

					} else {
						List<Node> pacmanPathToPP = pacman.getPathTo(closestPowerPillToPac);
						action = ghost.getNextDir(powerPillList.get(0), true);
						for (int j = 0; j < pacmanPathToPP.size(); j++) {
							if (ghost.getLocation() == pacmanPathToPP.get(j)) {
								action = ghost.getReverse();
								return action;
							}
						}
						return action;
					}
				}
				else {
					action = ghost.getNextDir(normPills.get(0), true);
					return action;
				}
			}
		}
		return 0;
	}

	public int ghost1(Game game, Defender ghost) {
		List<Node> powerPills = game.getPowerPillList();
		Attacker pacman = game.getAttacker();
		int action;

		if (powerPills == null){

			action = ghost.getNextDir(pacman.getLocation(), true);
			return action;
		}
		if (powerPills.size() >= 4) {

			boolean thereORNot0 = game.checkPowerPill(powerPills.get(0));
			boolean thereORNot1 = game.checkPowerPill(powerPills.get(1));
			boolean thereORNot2 = game.checkPowerPill(powerPills.get(2));
			boolean thereORNot3 = game.checkPowerPill(powerPills.get(3));

			if (thereORNot3) {

				action = ghost.getNextDir(powerPills.get(3), true);
				return action;

			}
			if (powerPills.size() >= 3) {

				if (thereORNot2) {

					action = ghost.getNextDir(powerPills.get(2), true);
					return action;

				}
				if (powerPills.size() >= 2) {
					if (thereORNot1) {

						action = ghost.getNextDir(powerPills.get(1), true);
						return action;

					}
					if (powerPills.size() >= 1) {
						if (thereORNot0) {


							action = ghost.getNextDir(powerPills.get(0), true);
							return action;

						} else {

							action = ghost.getNextDir(pacman.getLocation(), true);
							return action;

						}
					}
				}
			}
		}

		action = ghost.getNextDir(pacman.getLocation(), true);
		return action;
	}

}
