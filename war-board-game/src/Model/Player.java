package Model;

import java.util.ArrayList;
import java.util.List;

class Player {
	
	private final String name;
	private final PlayerColor color;
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
				this.objective = new TerritoriesObjective(24);
				return;
			}
		}
		this.objective = objective;
	}

	Objective getObjective() {
		return objective;
	}

	public void setAvailableUnits(int availableUnits) {
		this.availableUnits = availableUnits;
	}

	public void setNumberOfCardExchanges (int numberOfCardExchanges){
		this.numberOfCardExchanges = numberOfCardExchanges;
	}

	void addCard(Card card) {
		cards.add(card);
		String message = String.format("Jogador %s, cartas: ", name);
		for (Card cardPrint : cards) {
			Territory cardTerritory = cardPrint.getTerritory();
			message += String.format("%s tipo %s, ", cardTerritory != null ? cardTerritory.name : "Coringa", cardPrint.getType().toString());
		}
		System.out.println(message);
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

	int getNumberOfCardExchanges() {
		return numberOfCardExchanges;
	}

	boolean playerHasTerritory(Territory territory){
		return (territories.contains(territory));
	}

	void addRoundStartUnits(){
		int newArmyAmount = territories.size() / 2;
		availableUnits += newArmyAmount > 3 ? newArmyAmount : 3;
	}

	void addContinentBonusUnits(Continent continent) {
		availableUnits += continent.getContinentBonus();
	}

	// Returns the exchanged cards to be placed back into the deck or null if no exchange was made
	List<Card> addCardExchangeBonus() {
		List<Card> exchangeCards = getExchangeCards();
		if (exchangeCards == null) return null;

		numberOfCardExchanges++;
		availableUnits += getNumberOfCardExchangeNewUnits();

		for (Card card : exchangeCards) {
			// Adds card territory owner units
			if (card.getTerritory() != null) {
				for (Territory territory : territories) {
					if (territory == card.getTerritory()) {
						territory.addArmy(2);
					}
				}
			}
			cards.remove(card);
		}
		return exchangeCards;
	}

	private List<Card> getExchangeCards() {
		if (cards.size() < 3) return null;

		int squareCards = 0, triangleCards = 0, circleCards = 0; // joker counts to all

		for (Card card : cards) {
			switch (card.getType()) {
				case circle:
					circleCards++;
					break;
				case square:
					squareCards++;
					break;
				case triangle:
					triangleCards++;
					break;
				default: // joker
					circleCards++; squareCards++; triangleCards++;
					break;
			}
		}
		if (squareCards >= 3) return getExchangeCardsOfType(CardType.square);
		if (circleCards >= 3) return getExchangeCardsOfType(CardType.circle);
		if (triangleCards >= 3) return getExchangeCardsOfType(CardType.triangle);
		if (squareCards > 0 && triangleCards > 0 && circleCards > 0) return getMixedExchangeCards();
		// If it gets here, no exchange can be made
		return null;
	}

	private List<Card> getExchangeCardsOfType(CardType type) {
		List<Card> exchangeCards = new ArrayList<>();
		for (Card card : cards) {
			if (card.getType() == type || card.getType() == CardType.joker) {
				exchangeCards.add(card);
			}
			if (exchangeCards.size() == 3) break;
		}
		return exchangeCards;
	}

	private List<Card> getMixedExchangeCards() {
		List<Card> exchangeCards = new ArrayList<>();
		boolean hasAddedTriangle = false, hasAddedCircle = false, hasAddedSquare = false;
		for (Card card : cards) {
			switch (card.getType()) {
				case square:
					if (!hasAddedSquare) {
						exchangeCards.add(card);
						hasAddedSquare = true;
					}
					break;
				case circle:
					if (!hasAddedCircle) {
						exchangeCards.add(card);
						hasAddedCircle = true;
					}
					break;
				case triangle:
					if (!hasAddedTriangle) {
						exchangeCards.add(card);
						hasAddedTriangle = true;
					}
					break;
				default: // joker
					exchangeCards.add(card);
					break;
			}
			if (exchangeCards.size() == 3) break;
		}
		return exchangeCards;
	}

	boolean canExchangeCards() {
		return getExchangeCards() != null;
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

	// To be called at the start of a round
	void changeObjectiveIfNeeded(Player[] players) {
		if (objective instanceof DefeatPlayerObjective) {
			DefeatPlayerObjective playerObjective = (DefeatPlayerObjective) objective;
			for (Player player : players) {
				// If the player has been eliminated by someone else
				if (playerObjective.colorToEliminate == player.color && player.isDefeated()) {
					// Has to change objective
					this.objective = new TerritoriesObjective(24);
					return;
				}
			}
		}
	}

	boolean isDefeated() {
		return getConqueredTerritoriesNumber() == 0;
	}
}
