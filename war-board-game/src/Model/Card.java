package Model;

public class Card {
	
	private CardType type; // triangle, square, circle or joker
	private String name; //territory name
	
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
