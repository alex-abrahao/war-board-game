package Model;

import java.util.*;
import Model.observer.*;

public class Match {

    private Player[] players;
    private int currentRound = 0;
    private Board board = new Board();
    private int currentPlayerIndex = 0;
    private boolean objectiveComplete = false;
    private GameState currentState = GameState.firstRoundDistribute;
    private int roundConqueredTerritoriesCount = 0;

    private List<StringObserver> currentPlayerObservers = new ArrayList<StringObserver>();
    private List<StringObserver> currentStateObservers = new ArrayList<StringObserver>();

    private final static Match instance = new Match();

    private Match() {}

    public static Match getInstance() {
        return instance;
    }

    public void setPlayers(PlayerInfo[] players) {

        // Cria Players novos baseados nas infos
        this.players = new Player[players.length];
        for (int i = 0; i < players.length; i++) {
            this.players[i] = new Player(players[i]);
        }
        shufflePlayers();
    }

    public PlayerInfo[] getOrderedPlayers() {
        PlayerInfo[] players = new PlayerInfo[this.players.length];
        for (int i = 0; i < players.length; i++) {
            players[i] = new PlayerInfo(this.players[i]);
        }
        return players;
    }

    public boolean getObjectiveComplete() {
        return objectiveComplete;
    }

    private void shufflePlayers() {
        // Embaralha o array de players
        List<Player> playerList = Arrays.asList(this.players);
        Collections.shuffle(playerList);
        playerList.toArray(this.players);
        // O array embaralhado agora representa a ordem dos jogadores
        for (int i = 0; i < players.length; i++) {
            this.players[i].orderToPlay = i + 1;
        }
    }

    public void start() {
        //distribuir objetivo de cada jogador 
        distributeObjectives();
        // distribuir territorios (ou cartas) aleatoriamente
        distributePlayerTerritories();
        // distribuir exercitos extras pra cada player
        distributeFirstRoundUnits();
        
    }

    public void clear(boolean keepPlayers) {

        currentState = GameState.firstRoundDistribute;
        board = new Board();
        currentPlayerIndex = 0;
        roundConqueredTerritoriesCount = 0;
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

    private void advanceToNextPlayer() {
        if (++currentPlayerIndex >= players.length) {
            currentPlayerIndex = 0;
            if (currentState != GameState.firstRoundDistribute) {
                currentRound++;
            }   
        }
        for (StringObserver playerObserver : currentPlayerObservers) {
            playerObserver.notify(players[currentPlayerIndex].getName());
        }
    }

    // Returns error message or null
    private String advanceToNextState() {
        Player currentPlayer = players[currentPlayerIndex];

        switch (currentState) {
            case firstRoundDistribute:
                if (currentPlayer.getAvailableUnits() > 0) return "Distribua todos os exércitos";
                if (currentPlayer == players[players.length - 1]) {
                    currentState = GameState.unitDistributing;
                }
                advanceToNextPlayer();
                return null;
            case unitDistributing:
                if (currentPlayer.getAvailableUnits() > 0) return "Distribua todos os exércitos";
                currentState = GameState.attacking;
                return null;
            case attacking:
                // TODO: Implement
                currentState = GameState.movingUnits;
                return null;
            case movingUnits:
                if (roundConqueredTerritoriesCount > 1) {
                    newCardForConqueredTerritory();
                }
                roundConqueredTerritoriesCount = 0;
                currentState = GameState.unitDistributing;
                return null;
            default:
                // Shoud never execute
                return "Erro: Estados demais";
        }
    }

    private int getRandomListIndex(List<?> list) {
		Random generator = new Random();
		return generator.nextInt(list.size());
	}

	private void distributePlayerTerritories() {

        List<Card> cardsCopy = new ArrayList<>(board.cards);
        // There are 2 jokers at end which should not be distributed
        cardsCopy.remove(cardsCopy.size() - 1);
        cardsCopy.remove(cardsCopy.size() - 1);
        int totalCards = cardsCopy.size();
		// Circular distribution for the players
		for (int distributedCards = 0; distributedCards < totalCards; distributedCards++) {
			int playerIndex = distributedCards % players.length;
			int cardIndex = getRandomListIndex(cardsCopy);
			players[playerIndex].addTerritory(cardsCopy.get(cardIndex).getTerritory());
			// removes the selected card from the list so there's no duplicates.
			cardsCopy.remove(cardIndex);
		}
    }

    private void distributeObjectives(){
        List<Objective> objectivesCopy = new ArrayList<>(board.objectives);

        for (int distributedObjectives = 0; distributedObjectives < players.length; distributedObjectives++) {
            int objectiveIndex = getRandomListIndex(objectivesCopy);
            while(objectivesCopy.get(objectiveIndex).type == ObjectiveType.defeatPlayer && !isObjectiveValid(players[distributedObjectives], 
                    (DefeatPlayerObjective) objectivesCopy.get(objectiveIndex))) {
                        objectivesCopy.remove(objectivesCopy.get(objectiveIndex));
                        objectiveIndex = getRandomListIndex(objectivesCopy);
            }
            players[distributedObjectives].setObjective(objectivesCopy.get(objectiveIndex));
            // removes the selected card from the list so there's no duplicates.
            objectivesCopy.remove(objectiveIndex);
        }
    }

    private boolean isObjectiveValid(Player player, DefeatPlayerObjective objective){
        if (player.getColor() == objective.colorToEliminate){
            return false;
        }
        for(int i = 0; i < players.length; i++){
            if(players[i].getColor() == objective.colorToEliminate){
                return true;
            }
        }
        return false;
    }

    // private void makeDefeatPlayersObjectives() {
    //     for(int i = 0; i < players.length; i++){
    //         board.objectives.add(new DefeatPlayerObjective(players[i].getColor()));
    //     }
    // }
    
    private void distributeFirstRoundUnits() {
        for (Player player : players) {
            player.addRoundStartUnits();
        }
    }

    public int getCurrentRound() {
        return currentRound;
    }

    /**
     * Attack territories.
     * @param originTerritoryName
     * @param destinationTerritoryName
     * @return true if the attack was successfull, false if it is not valid.
     */
    public boolean attack(String originTerritoryName, String destinationTerritoryName) {
        Territory originTerritory = board.getTerritory(originTerritoryName),
                  destinationTerritory = board.getTerritory(destinationTerritoryName);
        int numberOfAttackDice = getNumberOfAttackDice(originTerritoryName, destinationTerritoryName);

        if(originTerritory.getOwner() != players[currentPlayerIndex] || numberOfAttackDice == 0) {
            return false;
        }
        
        int numberOfAttackWin = 0;
        int numberOfDefendDice = getNumberOfDefendDice(destinationTerritoryName);
        Integer []attackDices = new Integer[numberOfAttackDice];
        Integer []defendDices = new Integer[numberOfDefendDice];
        attackDices = rollDices(numberOfAttackDice);
        defendDices = rollDices(numberOfDefendDice);
        Arrays.sort(attackDices, Collections.reverseOrder());
        Arrays.sort(defendDices, Collections.reverseOrder());
        for(int i = 0; i < numberOfDefendDice; i++){
            if(attackDices[i] > defendDices[i]){
                numberOfAttackWin++;
            }
        }

        /* Move armies */
        if(numberOfDefendDice - numberOfAttackWin <= 0){ /*if player conquered territory*/
            handleConqueredTerritory(originTerritory, destinationTerritory);
        } else {
            destinationTerritory.removeArmy(numberOfAttackWin);
            originTerritory.removeArmy(numberOfDefendDice-numberOfAttackWin);
        }
        return true;
    }

    /**
     * Number of available dice to attack.
     * @param originTerritoryName
     * @param destinationTerritoryName
     * @return Number of dice available to attack. Returns zero if the attack is invalid. Maximum of 3.
     */
    public int getNumberOfAttackDice(String originTerritoryName, String destinationTerritoryName) {

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

    public Integer[] rollDices(int quantity) {
        Integer[] dices = new Integer[quantity];
        for (int i = 0; i < quantity; i++) {
            // TODO: Verificar essa conta
            dices[i] = (int) (Math.random() * ((6 - 1) + 1)) + 1;
        }
        return dices;
    }

    public void handleConqueredTerritory(Territory originTerritory, Territory newTerritory) {
        int unitsToMove = originTerritory.getArmyCount() > 3 ? 3 : originTerritory.getArmyCount() - 1;
        players[currentPlayerIndex].addTerritory(newTerritory);
        newTerritory.removeAllArmies();
        newTerritory.addArmy(unitsToMove);
        roundConqueredTerritoriesCount++;
    }

    private void newCardForConqueredTerritory() {
        players[currentPlayerIndex].addCard(board.getRandomCard(board.cards));
    }

    public void addTerritoryObserver(Territories territory, UnitNumberObserver observer) {
        board.getTerritory(territory).addObserver(observer);
    }

    public void addCurrentPlayerObserver(StringObserver observer) {
        currentPlayerObservers.add(observer);
        observer.notify(players[currentPlayerIndex].getName());
    }

    public void addCurrentStateObserver(StringObserver observer) {
        currentStateObservers.add(observer);
        // TODO: Notify state
        // observer.notify(value);
        observer.notify("Estado");
    }

    public String getCurrentPlayerObjective() {
        return players[currentPlayerIndex].getObjective().getDescription();
    }
}
