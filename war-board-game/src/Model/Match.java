package Model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Match {

    private Player[] players;
    private int currentRound = 0;
    private Board board = new Board();
    
    Match() {}

    public void setPlayers(PlayerInfo[] players) {

        // Cria Players novos baseados nas infos
        this.players = new Player[players.length];
        for (int i = 0; i < players.length; i++) {
            this.players[i] = new Player(players[i]);
        }
    }

    public void start() {

        // Embaralha o array de players
        List<Player> playerList = Arrays.asList(this.players);
        Collections.shuffle(playerList);
        playerList.toArray(this.players);
        // O array embaralhado agora representa a ordem dos jogadores
        for (int i = 0; i < players.length; i++) {
            this.players[i].orderToPlay = i + 1;
        }
        // distribuir territorios (ou cartas) aleatoriamente
        distributePlayerTerritories();
        // distribuir exercitos extras pra cada player
        distributeUnits();
    }

    public void reset(boolean keepPlayers) {

        board = new Board();
        // reset players dependendo do bool
            // se mantiver os players
                // reseta objetivo, cartas e territórios
            // senão
                // seta players como null
    }

    private int getRandomListIndex(List<?> list) {
		Random generator = new Random();
		return generator.nextInt(list.size());
	}

	private void distributePlayerTerritories(){

		int totalCards = 42;
		List<Card> cardsCopy = List.copyOf(board.cards);
		// Circular distribution for the players
		for (int distributedCards = 0; distributedCards < totalCards; distributedCards++) {
			int playerIndex = (distributedCards/totalCards) % players.length;
			int cardIndex = getRandomListIndex(cardsCopy);
			players[playerIndex].addTerritory(cardsCopy.get(cardIndex).getTerritory());
			// removes the selected card from the list so there's no duplicates.
			cardsCopy.remove(cardIndex);
		}
    }

    private void distributeUnits() {
        for (Player player : players) {
            player.addRoundStartUnits();
        }
    }

    public int getCurrentRound() {
        return currentRound;
    }
}
