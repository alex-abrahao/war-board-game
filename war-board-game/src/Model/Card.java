package Model;

class Card {

	// triangle, square, circle or joker
	private final CardType type;
	private final Territory territory;
	
	Card(Territory territory, CardType type) {
		this.territory = territory;
		this.type = type;
	}

	CardType getType() {
		return type;
	}
	
	Territory getTerritory() {
		return territory;
	}
}
