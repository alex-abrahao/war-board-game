package Model;

import java.util.ArrayList;
import java.util.List;

class Territory {

	final String name;
	private Continent continent;
	private Player owner;
	private int armyCount = 1;
	private List<Territory> neighbors = new ArrayList<>();
	
	Territory(String name) {
		this.name = name;
	}

	void setOwner(Player player) {
		this.owner = player;
	}

	Player getOwner() {
		return owner;
	}

	int getArmyCount() {
		return armyCount;
	}

	void setContinent(Continent continent) {
		this.continent = continent;
	}

	/**
	 * Adds {@code territory} as a neighbor, and adds itself as a neighbor of {@code territory}
	 * @param territory Territory to add as neighbor
	 */
	void addNeighbor(Territory territory) {
		neighbors.add(territory);
		territory.neighbors.add(this);
	}

	void addArmy(int quantity) {
		this.armyCount += quantity;
	}

	void removeArmy(int quantity) {
		this.armyCount -= quantity;
	}

	boolean isNeighbor(Territory territory) {
		return neighbors.contains(territory);
	}
	
	boolean isAttackValid(Territory territory) {
		if (territory.getOwner() == this.owner) {
			return false;
		}
		
		if (this.armyCount <= 1) {
			return false;
		}
		if (isNeighbor(territory)) {
			return true;
		}
		return false;
	}
}
