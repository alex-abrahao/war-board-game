package Model;

import java.util.ArrayList;
import java.util.List;

import Common.Observable;
import Model.observer.UnitNumberObserver;

class Territory implements Observable<UnitNumberObserver> {

	final String name;
	private Continent continent;
	private Player owner;
	private int armyCount = 1;
	private List<Territory> neighbors = new ArrayList<>();
	private List<UnitNumberObserver> observers = new ArrayList<>();
	
	Territory(String name) {
		this.name = name;
	}

	Territory(Territories t) {
		name = t.getName();
	}

	void setOwner(Player player) {
		owner = player;
		notifyObservers();
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

	void addContinentNeighbors(Territories[] territories) {
		for (Territories territory : territories) {
			addNeighbor(continent.getTerritory(territory.getName()));
		}
	}

	void addArmy(int quantity) {
		armyCount += quantity;
		notifyObservers();
	}

	// To be used if there's not a change of owner situation
	void removeArmy(int quantity) {
		// Verify if quantity is more than 
		armyCount -= quantity >= armyCount ? armyCount - 1: quantity;
		notifyObservers();
	}

	// To be used when there's a change of ownership
	void removeAllArmies() {
		armyCount = 0;
		notifyObservers();
	}

	boolean isNeighbor(Territory territory) {
		return neighbors.contains(territory);
	}
	
	boolean canAttack() {
		return armyCount > 1;
	}

	boolean canTransferUnits() {
		return armyCount > 1;
	}

	boolean isTransferArmyValid(Territory destinationTerritory, int armyCount) {
		if (destinationTerritory.getOwner() != this.owner) {
			return false;
		}
		if (this.armyCount - armyCount <= 0) {
			return false;
		}
		if (!this.isNeighbor(destinationTerritory)) {
			return false;
		}
		return true;
	}

	void transferArmy(Territory destinationTerritory, int armyCount) {
		removeArmy(armyCount);
		destinationTerritory.addArmy(armyCount);
	}

	private void notifyObservers() {
		for (UnitNumberObserver observer : observers) {
			observer.notify(armyCount, owner.getColor());
		}
	}

	@Override
	public void addObserver(UnitNumberObserver observer) {
		observers.add(observer);
		if (owner == null) return;
		observer.notify(armyCount, owner.getColor());
	}

	@Override
	public void removeObserver(UnitNumberObserver observer) {
		observers.remove(observer);
	}
}
