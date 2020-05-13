package Model;

import java.util.ArrayList;
import java.util.List;

public class Player {
	
	private final String name;
	private final PlayerColor color;
	int orderToPlay;
	private List<Card> cards = new ArrayList<>();
	private List<Territory> territories = new ArrayList<>();
	private Objective objective;
	
	public Player(String name, PlayerColor color) {
		this.name = name;
		this.color = color;
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
}
