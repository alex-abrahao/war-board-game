package Model;
import java.util.Random;
import java.util.List;

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

	public int getrandomCard(List<Card> cards){
		Random generator = new Random();
		return generator.nextInt(cards.size());
	}

	public void setPlayersCards(Player[] players, List<Card> cards){
		int numberOfCardsPerPlayer = players.length/cards.length;
        for(int i=0; i<players.length; i++){
			for(int numberOfCards = 0; numberOfCards<numberOfCardsPerPlayer; numberOfCards++) {
				int index = getrandomCard(cards);
				players[i].addCard(cards.get(index));
				cards.remove(index);//removing the selected card from the list so anyone take the same one.
            	
            }
		}
    }
 }

}
