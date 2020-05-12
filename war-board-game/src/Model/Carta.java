package Model;

public class Carta {
	private CardType type; // triangle, square, circle or joker
	private String name; //territory name
	
	Carta(String name, CardType type) {
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
