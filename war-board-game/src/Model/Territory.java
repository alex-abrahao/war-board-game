package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Territory {

	private final String name;
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

	int getArmyCount(){
		return armyCount;
	}

	void setContinent(Continent continent) {
		this.continent = continent;
	}

	void addNeighbor(Territory territory) {
		neighbors.add(territory);
	}

	void addArmy(int quantity){
		this.armyCount += quantity;
	}

	void removeArmy(int quantity){
		this.armyCount -= quantity;
	}

	boolean isNeighbor(Territory territory){
		return neighbors.contains(territory);
	}
	
	boolean isAttackValid(Territory territory) {
		if (territory.getOwner() == this.owner) {
			return false;
		}
		
		if (this.armyCount <= 1) {
			return false;
		}
		if (isNeighbor(territory)){
			return true;
		}
		return false;
	}

	static int getRandomTerritoryIndex(List<Territory> territory) {
		Random generator = new Random();
		return generator.nextInt(territory.size());
	}

	static void setPlayersTerritories(Player[] players, List<Territory> territories){

		int totalCards = 42;
		List<Territory> territoriesCopy = List.copyOf(territories);
		// Circular distribution for the players
		for (int distributedCards = 0; distributedCards < totalCards; distributedCards++) {
			int playerIndex = (distributedCards/totalCards) % players.length;
			int cardIndex = getRandomTerritoryIndex(territoriesCopy);
			players[playerIndex].addTerritory(territoriesCopy.get(cardIndex));
			// removes the selected card from the list so there's no duplicates.
			territoriesCopy.remove(cardIndex);
		}
    }
}
