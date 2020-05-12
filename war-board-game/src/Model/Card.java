package Model;

public class Card {

	// triangle, square, circle or joker
	private CardType type;
	private String name;
	
	Card(String name, CardType type) {
		this.name = name;
		this.type = type;
	}

	public CardType getType() {
		return type;
	}
	
	public String getName() {
		return name;
	}
}
