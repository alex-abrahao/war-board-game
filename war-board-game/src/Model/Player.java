package Model;

import java.util.ArrayList;
import java.util.List;

class Player {
	
	private final String name;
	private final PlayerColor color;
	int orderToPlay;
	private List<Card> cards = new ArrayList<>();
	private List<Territory> territories = new ArrayList<>();
	private Objective objective;
	
	Player(String name, PlayerColor color) {
		this.name = name;
		this.color = color;
	}

	Player(PlayerInfo info) {
		this.name = info.name;
		this.color = info.color;
	}

	void setObjective (Objective objective) {
		this.objective = objective;
	}

	Objective getObjective() {
		return objective;
	}

	void addCard(Card card) {
		cards.add(card);
	}

	void addTerritory(Territory territory) {
		territories.add(territory);
	}

	List<Card> getCards() {
		return cards;
	}

	PlayerColor getColor() {
		return color;
	}

	boolean playerHasTerritory(Territory territory){
		return (territories.contains(territory));
	}

	int amountOfNewArmy(){
		int newArmyAmount = 0;
		int totalArmy = 0;
		for (Territory territory : territories) {
			totalArmy += territory.getArmyCount();
		}
		newArmyAmount = totalArmy/2;
		return newArmyAmount;
	}
}
