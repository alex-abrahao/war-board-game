package Model;

import java.util.*;
import Model.observer.*;

public class Match {

    private Player[] players;
    private Board board = new Board();
    private int currentPlayerIndex = 0;
    private GameState currentState = GameState.firstRoundDistribute;
    private boolean currentPlayerHasConqueredTerritories = false;
    private Territory selectedOriginTerritory;
    private Territory selectedDestinationTerritory;
    private List<Continent> bonusContinentsToDistribute = new ArrayList<>();
    private Continent currentContinentToDistribute;

    private List<StringObserver> currentPlayerObservers = new ArrayList<StringObserver>();
    private List<StringObserver> currentStateObservers = new ArrayList<StringObserver>();
    private List<StringObserver> messageObservers = new ArrayList<StringObserver>();
    private List<StringObserver> resultObservers = new ArrayList<StringObserver>();

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
        setRemainingUnitsMessage(players[currentPlayerIndex].getAvailableUnits());
    }

    public void clear(boolean keepPlayers) {

        currentState = GameState.firstRoundDistribute;
        board = new Board();
        currentPlayerIndex = 0;
        currentPlayerHasConqueredTerritories = false;
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
                advanceToNextPlayer();
                if (currentPlayerIndex == 0) { // If after advancing the player is back to the first
                    setState(GameState.unitDistributing);
                    setPlayerContinentBonusUnits();
                }
                setRemainingUnitsMessage(players[currentPlayerIndex].getAvailableUnits());
                return null;
            case unitDistributing:
                if (currentPlayer.getAvailableUnits() > 0) return "Distribua todos os exércitos";
                setState(GameState.attacking);
                notifyMessageObservers("Selecione o território de origem");
                return null;
            case attacking:
                selectedOriginTerritory = null;
                selectedDestinationTerritory = null;
                setState(GameState.movingUnits);
                return null;
            case movingUnits:
                if (currentPlayerHasConqueredTerritories) {
                    newCardForConqueredTerritory();
                }
                currentPlayerHasConqueredTerritories = false;
                advanceToNextPlayer();
                setPlayerContinentBonusUnits();
                setRemainingUnitsMessage(players[currentPlayerIndex].getAvailableUnits());
                setState(GameState.unitDistributing);
                return null;
            default:
                // Shoud never execute
                return "Erro: Estados demais";
        }
    }

    private void setState(GameState state) {
        currentState = state;
        for (StringObserver observer : currentStateObservers) {
            observer.notify(currentState.name);
        }
    }

    private void setPlayerContinentBonusUnits() {
        Player currentPlayer = players[currentPlayerIndex];
        fillContinentBonusesList(currentPlayer);
        handleCurrentPlayerNextBonusUnits();
    }

    private void handleCurrentPlayerNextBonusUnits() {

        if (bonusContinentsToDistribute.isEmpty()) {
            currentContinentToDistribute = null;
            players[currentPlayerIndex].addRoundStartUnits();
            return;
        }
        currentContinentToDistribute = bonusContinentsToDistribute.get(0);
        bonusContinentsToDistribute.remove(0);
        players[currentPlayerIndex].addContinentBonusUnits(currentContinentToDistribute);
    }

    private void fillContinentBonusesList(Player player) {
        bonusContinentsToDistribute.clear();
        for (Continent continent : board.continents.values()) {
            if (player == continent.getConqueror()) {
                bonusContinentsToDistribute.add(continent);
            }
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
        List<Objective> objectives = board.objectives;

        for (int distributedObjectives = 0; distributedObjectives < players.length; distributedObjectives++) {
            int objectiveIndex = getRandomListIndex(objectives);
            Objective objective = objectives.get(objectiveIndex);
            Player player = players[distributedObjectives];
            while(objective.type == ObjectiveType.defeatPlayer &&
                  !isObjectiveValid(player, (DefeatPlayerObjective) objective)) {
                        objectives.remove(objective);
                        objectiveIndex = getRandomListIndex(objectives);
                        objective = objectives.get(objectiveIndex);
            }
            player.setObjective(objective);
            System.out.println(String.format("Player: %s %s, Objetivo %s", player.getName(), player.getColor().getName(), objective.getDescription()));
            // removes the selected card from the list so there's no duplicates.
            objectives.remove(objectiveIndex);
        }
    }

    private boolean isObjectiveValid(Player player, DefeatPlayerObjective objective){
        for(int i = 0; i < players.length; i++) {
            if(players[i].getColor() == objective.colorToEliminate){
                return true;
            }
        }
        return false;
    }
    
    private void distributeFirstRoundUnits() {
        for (Player player : players) {
            player.addRoundStartUnits();
        }
    }

    /**
     * Attack territories.
     * @param originTerritory
     * @param destinationTerritory
     */
    private void attack(Territory originTerritory, Territory destinationTerritory) {
        int numberOfAttackDice = getNumberOfAttackDice(originTerritory);

        int numberOfAttackWin = 0;
        int numberOfDefendDice = getNumberOfDefendDice(destinationTerritory);
        Integer []attackDices = new Integer[numberOfAttackDice];
        Integer []defendDices = new Integer[numberOfDefendDice];
        attackDices = rollDices(numberOfAttackDice);
        defendDices = rollDices(numberOfDefendDice);
        Arrays.sort(attackDices, Collections.reverseOrder());
        Arrays.sort(defendDices, Collections.reverseOrder());
        notifyDiceResults(attackDices, defendDices);
        for(int i = 0; i < numberOfDefendDice && i < numberOfAttackDice; i++){
            if (attackDices[i] > defendDices[i]){
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
    }

    private void notifyDiceResults(Integer[] attack, Integer[] defense) {
        String result = "Ataque: ";
        for (int i = 0; i < attack.length; i++) {
            result += String.format("%d", attack[i]);
            if (i < attack.length - 1) {
                result += ", ";
            }
        }
        result += " | Defesa: ";
        for (int i = 0; i < defense.length; i++) {
            result += String.format("%d", defense[i]);
            if (i < defense.length - 1) {
                result += ", ";
            }
        }
        notifyResultObservers(result);
    }

    /**
     * Number of available dice to attack.
     * @param originTerritoryName
     * @param destinationTerritoryName
     * @return Number of dice available to attack. Returns zero if the attack is invalid. Maximum of 3.
     */
    private int getNumberOfAttackDice(Territory originTerritory) {
        
        int availableUnits = originTerritory.getArmyCount() - 1;

        return availableUnits > 3 ? 3 : availableUnits;
    }

    /**
     * Number of available dice to defend.
     * @param defenderTerritory
     * @return Number of dice available to defend. Maximum of 3.
     */
    private int getNumberOfDefendDice(Territory defenderTerritory) {

        int availableUnits = defenderTerritory.getArmyCount();

        return availableUnits > 3 ? 3 : availableUnits;
    }

    public Integer[] rollDices(int quantity) {
        Integer[] dices = new Integer[quantity];
        for (int i = 0; i < quantity; i++) {
            dices[i] = (int) (Math.random() * 6) + 1;
            System.out.format("dado %d: %d\n", i, dices[i]);
        }
        return dices;
    }

    public void handleConqueredTerritory(Territory originTerritory, Territory newTerritory) {
        int unitsToMove = originTerritory.getArmyCount() > 3 ? 3 : originTerritory.getArmyCount() - 1;
        players[currentPlayerIndex].addTerritory(newTerritory);
        newTerritory.removeAllArmies();
        newTerritory.addArmy(unitsToMove);
        originTerritory.removeArmy(unitsToMove);
        currentPlayerHasConqueredTerritories = true;
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
        observer.notify(currentState.name);
    }

    public void addMessageObserver(StringObserver observer) {
        messageObservers.add(observer);
    }

    public void addResultObserver(StringObserver observer) {
        resultObservers.add(observer);
    }

    public String getCurrentPlayerObjective() {
        return players[currentPlayerIndex].getObjective().getDescription();
    }

    private void setRemainingUnitsMessage(int number) {
        if (currentContinentToDistribute != null) {
            notifyMessageObservers(String.format("Unidades para distribuir em %s: %d", currentContinentToDistribute.name, number));
        } else {
            notifyMessageObservers(String.format("Unidades para distribuir: %d", number));
        }
    }

    private void notifyMessageObservers(String message) {
        for (StringObserver observer : messageObservers) {
            observer.notify(message);
        }
    }

    private void notifyResultObservers(String result) {
        for (StringObserver observer : resultObservers) {
            observer.notify(result);
        }
    }

    public void selectTerritory(Territories territory) {
        Territory selectedTerritory = board.getTerritory(territory);
        switch (currentState) {
            case firstRoundDistribute:
            case unitDistributing:
                handleSelectDistribute(selectedTerritory);
                break;
            case attacking:
                handleSelectAttack(selectedTerritory);
                break;
            case movingUnits:
                handleSelectMovingUnits(selectedTerritory);
                break;
            default:
                break;
        }
    }

    private void handleSelectDistribute(Territory territory) {
        Player currentPlayer = players[currentPlayerIndex];
        if (currentPlayer != territory.getOwner()) {
            notifyResultObservers("Selecione um território conquistado");
            return;
        }
        if (currentPlayer.getAvailableUnits() == 0) {
            notifyResultObservers("Sem unidades disponíveis. Selecione Próxima Jogada");
            return;
        }
        if (currentContinentToDistribute != null && !currentContinentToDistribute.hasTerritory(territory)) {
            notifyResultObservers("Selecione um território do continente " + currentContinentToDistribute.name);
            return;
        }
        currentPlayer.putAvailableUnits(1, territory);
        moveToNextDistributionIfNeeded(currentPlayer);
        setRemainingUnitsMessage(currentPlayer.getAvailableUnits());
        notifyResultObservers("");
    }

    private void moveToNextDistributionIfNeeded(Player currentPlayer) {
        if (currentPlayer.getAvailableUnits() == 0 && currentContinentToDistribute != null) {
            handleCurrentPlayerNextBonusUnits();
        }
    }

    private void handleSelectAttack(Territory territory) {
        Player currentPlayer = players[currentPlayerIndex];
        if (selectedOriginTerritory == null) {
            if (territory.getOwner() != currentPlayer) {
                notifyResultObservers("Selecione um território de origem conquistado");
                return;
            }
            if (!territory.canAttack()) {
                notifyResultObservers("Selecione um território de origem conquistado com mais de um exército");
                return;
            }
            selectedOriginTerritory = territory;
            notifyMessageObservers("Selecione um território de destino");
        } else {
            if (territory.getOwner() == currentPlayer) {
                notifyResultObservers("Selecione um território de destino de um oponente");
                return;
            }
            if (selectedOriginTerritory.isNeighbor(territory) == false) {
                notifyResultObservers("Ataque não é valido, território não é vizinho, selecione um vizinho");
                return;
            }
            selectedDestinationTerritory = territory;
            notifyMessageObservers("Ataque válido, jogue os dados");
        }
        notifyResultObservers("");
    }

    private void handleSelectMovingUnits(Territory territory) {
        // TODO: Implement
    }

    public void goToNextPlay() {
        String errorMessage = advanceToNextState();
        if (errorMessage != null) {
            notifyResultObservers(errorMessage);
            return;
        }
        notifyResultObservers("");
    }

    public void playDice() {
        if (currentState != GameState.attacking) {
            notifyResultObservers("Não está atacando");
            return;
        }
        if (selectedOriginTerritory == null || selectedDestinationTerritory == null) {
            notifyResultObservers("Selecione territórios de origem e destino");
            return;
        }
        attack(selectedOriginTerritory, selectedDestinationTerritory);
        selectedDestinationTerritory = null;
        selectedOriginTerritory = null;
        notifyMessageObservers("Selecione um território de origem");
    }
}
