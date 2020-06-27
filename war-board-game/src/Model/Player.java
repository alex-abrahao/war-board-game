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
	private int availableUnits = 0;
	private int numberOfCardExchanges = 0;
	
	Player(String name, PlayerColor color) {
		this.name = name;
		this.color = color;
	}

	Player(PlayerInfo info) {
		this.name = info.name;
		this.color = info.color;
	}

	void setObjective(Objective objective) {
		if (objective instanceof DefeatPlayerObjective) {
			DefeatPlayerObjective playerObjective = (DefeatPlayerObjective) objective;
			if (playerObjective.colorToEliminate == this.color) {
				this.objective = new TerritoriesObjective(30);
				return;
			}
		}
		this.objective = objective;
	}

	Objective getObjective() {
		return objective;
	}

	void addCard(Card card) {
		cards.add(card);
	}

	void resetPlayer() {
		cards.clear();
		objective = null;
		territories.clear();
		availableUnits = 0;
		numberOfCardExchanges = 0;
	}

	void addTerritory(Territory territory) {
		territory.setOwner(this);
		territories.add(territory);
	}

	void removeTerritory(Territory territory) {
		territories.remove(territory);
	}

	int getConqueredTerritoriesNumber() {
		return territories.size();
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

	void addRoundStartUnits(){
		int newArmyAmount = territories.size() /2;
		availableUnits += newArmyAmount > 3 ? newArmyAmount : 3;
	}

	void addContinentBonusUnits(Continent continent) {
		availableUnits += continent.getContinentBonus();
	}

	void addCardExchangeBonus(List<Card> cardList) {

		numberOfCardExchanges++;
		availableUnits += getNumberOfCardExchangeNewUnits();

		for (Card card : cardList) {
			// Adds card territory owner units
			if (card.getTerritory() != null) {
				for (Territory territory : territories) {
					if (territory == card.getTerritory()) {
						territory.addArmy(2);
					}
				}
			}
		}
	}

	private int getNumberOfCardExchangeNewUnits() {
		if (numberOfCardExchanges < 6) {
			return 2 + (numberOfCardExchanges) * 2;
		} else {
			return 10 + (numberOfCardExchanges - 5) * 5;
		}
	}

	int getAvailableUnits() {
		return availableUnits;
	}

	String getName() {
		return name;
	}

	void putAvailableUnits(int units, Territory territory) {
		if (territory.getOwner() != this) return;
		if (units > availableUnits) units = availableUnits;
		territory.addArmy(units);
		availableUnits -= units;
	}
}
