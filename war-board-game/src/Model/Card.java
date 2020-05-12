package Model;
import java.util.Random;

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

	public Card getrandomCard(Card[] cards){
		Random generator = new Random();
		int randomIndex = generator.nextInt(cards.length);
		return cards[randomIndex];
	}
}
