package Model;
import java.util.Random;
import java.util.List;

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

	int getRandomCardIndex(List<Card> cards) {
		Random generator = new Random();
		return generator.nextInt(cards.size());
	}

	void setPlayersCards(Player[] players, List<Card> cards){

		int totalCards = 42;
		for (int distributedCards = 0; distributedCards < totalCards; distributedCards++) {
			int playerIndex = (distributedCards/totalCards) % players.length;
			int cardIndex = getRandomCardIndex(cards);
			players[playerIndex].addCard(cards.get(cardIndex));
			cards.remove(cardIndex);//removing the selected card from the list so anyone take the same one.
		}
    }
}
