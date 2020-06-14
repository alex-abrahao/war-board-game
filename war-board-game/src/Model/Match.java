package Model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.swing.plaf.synth.ColorType;

public class Match {

    private Player[] players;
    private int currentRound = 0;
    private Board board = new Board();
    private int currentPlayerIndex = 0;
    private boolean objectiveComplete = false;

    public void setPlayers(PlayerInfo[] players) {

        // Cria Players novos baseados nas infos
        this.players = new Player[players.length];
        for (int i = 0; i < players.length; i++) {
            this.players[i] = new Player(players[i]);
        }
    }

    public boolean getObjectiveComplete(){
        return objectiveComplete;
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
        distributeFirstRoundUnits();
    }

    public void clear(boolean keepPlayers) {

        board = new Board();
        currentPlayerIndex = 0;
        // reset players accordingly
        if (keepPlayers) {
            // resets objective, cards, territories etc
            for (Player player : players) {
                player.resetPlayer();
            }
        } else {
            players = null;
        }
    }

    public void advanceToNextPlayer() {
        if (++currentPlayerIndex >= players.length) {
            currentPlayerIndex = 0;
            currentRound++;
        }
    }

    private int getRandomListIndex(List<?> list) {
		Random generator = new Random();
		return generator.nextInt(list.size());
	}

	private void distributePlayerTerritories() {

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

    private void distributeFirstRoundUnits() {
        for (Player player : players) {
            player.addRoundStartUnits();
        }
    }

    public int getCurrentRound() {
        return currentRound;
    }

    public void attack(ColorType attackerColor, String originTerritoryName, String destinationTerritoryName){
        Territory originTerritory = board.getTerritory(originTerritoryName),
                  destinationTerritory = board.getTerritory(destinationTerritoryName);
        int numberOfAttackDice = getNumberOfAttackDice(attackerColor, originTerritoryName, destinationTerritoryName);
        int numberOfAttackWin = 0;
        if(originTerritory.getOwner() == players[currentPlayerIndex]){
            if(numberOfAttackDice > 0){
                int numberOfDefendDice = getNumberOfDefendDice(destinationTerritoryName);
                int []attackDices = new int[numberOfAttackDice];
                int []defendDices = new int[numberOfDefendDice];
                attackDices = rollDices(numberOfAttackDice);
                defendDices = rollDices(numberOfDefendDice);
                Arrays.sort(attackDices);
                Arrays.sort(defendDices);
                for(int i = 0; i<numberOfDefendDice; i++){
                    if(compareDices(attackDices[i],defendDices[i]) == true){
                        numberOfAttackWin++;
                    }
                }

                /*Move armies*/
                if(numberOfDefendDice - numberOfAttackWin <= 0){ /*if player conquered territory*/
                    conqueredTerritory(destinationTerritory, originTerritory.getArmyCount()-1);
                    newCardForConqueredTerritory();
                }
                else {
                    destinationTerritory.removeArmy(numberOfAttackWin);
                    originTerritory.removeArmy(numberOfDefendDice-numberOfAttackWin);
                }
            }
        }
    }

    /**
     * Number of available dice to attack.
     * @param attackerColor
     * @param originTerritoryName
     * @param destinationTerritoryName
     * @return Number of dice available to attack. Returns zero if the attack is invalid. Maximum of 3.
     */
    public int getNumberOfAttackDice(ColorType attackerColor, String originTerritoryName, String destinationTerritoryName) {

        Territory originTerritory = board.getTerritory(originTerritoryName),
                  destinationTerritory = board.getTerritory(destinationTerritoryName);

        if (!originTerritory.isAttackValid(destinationTerritory)) {
            return 0;
        }
        
        int availableUnits = originTerritory.getArmyCount() - 1;

        return availableUnits > 3 ? 3 : availableUnits;
    }

    /**
     * Number of available dice to defend.
     * @param defenderTerritoryName
     * @return Number of dice available to defend. Maximum of 3.
     */
    public int getNumberOfDefendDice(String defenderTerritoryName) {

        Territory defenderTerritory = board.getTerritory(defenderTerritoryName);
        int availableUnits = defenderTerritory.getArmyCount();

        return availableUnits > 3 ? 3 : availableUnits;
    }

    public int[] rollDices(int quantity) {
        int[] dices = new int[quantity];
        for (int i = 0; i<quantity; i++){
            dices[i] =  (int) (Math.random() * ((6 - 1) + 1)) + 1;
        }
        return dices;
    }

    public boolean compareDices(int attack, int defend) {
        if (attack > defend){
            return true;
        }
        return false;
    }

    public void conqueredTerritory(Territory newTerritory, int armiesToNewTerritory){
        players[currentPlayerIndex].addTerritory(newTerritory);
        newTerritory.removeAllArmies();
        newTerritory.addArmy(armiesToNewTerritory);
    }

    public void newCardForConqueredTerritory(){
        players[currentPlayerIndex].addCard(board.getRandomCard(board.cards));
    }

}
