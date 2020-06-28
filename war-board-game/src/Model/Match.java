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
        int playersCounter = 0;
        do {
            if (++currentPlayerIndex >= players.length) {
                currentPlayerIndex = 0;
            }
            playersCounter++;
            // Skip defeated players, infinite loop safe
        } while (players[currentPlayerIndex].isDefeated() && playersCounter < players.length);
        // Check if the current player has to change objective
        players[currentPlayerIndex].changeObjectiveIfNeeded(players);
        for (StringObserver playerObserver : currentPlayerObservers) {
            playerObserver.notify(players[currentPlayerIndex].getName());
        }
    }

    // Returns error message or null
    private String advanceToNextState() {
        Player currentPlayer = players[currentPlayerIndex];
        notifyResultObservers("");
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
                System.out.println(String.format("Player %s Num Territorios: %d", currentPlayer.getName(), currentPlayer.getConqueredTerritoriesNumber()));
                if (currentPlayer.getObjective().isCompleted(currentPlayer, players)) {
                    notifyMessageObservers("Parabéns! Você ganhou o jogo!");
                    setState(GameState.victory);
                    return null;
                }
                if (currentPlayerHasConqueredTerritories) {
                    newCardForConqueredTerritory();
                }
                currentPlayerHasConqueredTerritories = false;
                selectedOriginTerritory = null;
                selectedDestinationTerritory = null;
                setState(GameState.movingUnits);
                notifyMessageObservers("Selecione o território de origem");
                return null;
            case movingUnits:
                advanceToNextPlayer();
                setPlayerContinentBonusUnits();
                setRemainingUnitsMessage(players[currentPlayerIndex].getAvailableUnits());
                setState(GameState.unitDistributing);
                selectedOriginTerritory = null;
                selectedDestinationTerritory = null;
                return null;
            default:
                // Someone has won
                return "Inicie um novo jogo";
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
        Player currentPlayer = players[currentPlayerIndex];
        if (bonusContinentsToDistribute.isEmpty()) {
            currentContinentToDistribute = null;
            currentPlayer.addRoundStartUnits();
            List<Card> exchangeCards = currentPlayer.addCardExchangeBonus();
            if (exchangeCards != null) {
                board.returnCardsToDeck(exchangeCards);
                notifyExchangeResult(currentPlayer, exchangeCards);
            }
            return;
        }
        currentContinentToDistribute = bonusContinentsToDistribute.get(0);
        bonusContinentsToDistribute.remove(0);
        currentPlayer.addContinentBonusUnits(currentContinentToDistribute);
    }

    private void notifyExchangeResult(Player player, List<Card> exchangeCards) {
        String result = String.format("%dª troca: ", player.getNumberOfCardExchanges());
        for (int i = 0; i < exchangeCards.size(); i++) {
            Territory cardTerritory = exchangeCards.get(i).getTerritory();
            result += String.format("%s %s", cardTerritory != null ? cardTerritory.name : "Coringa", exchangeCards.get(i).getType().toString());
            if (i < exchangeCards.size() - 1) {
                result += ", ";
            }
        }
        System.out.println(result);
        notifyResultObservers(result);
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

    private boolean isObjectiveValid(Player player, DefeatPlayerObjective objective) {
        for (int i = 0; i < players.length; i++) {
            if (players[i].getColor() == objective.colorToEliminate) {
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
        if(destinationTerritory.getArmyCount() - numberOfAttackWin <= 0){ /*if player conquered territory*/
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
        System.out.println(result);
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
        newTerritory.getOwner().removeTerritory(newTerritory);
        players[currentPlayerIndex].addTerritory(newTerritory);
        newTerritory.removeAllArmies();
        newTerritory.addArmy(unitsToMove);
        originTerritory.removeArmy(unitsToMove);
        currentPlayerHasConqueredTerritories = true;
    }

    private void newCardForConqueredTerritory() {
        Card card = board.getRandomCard(board.cards);
        players[currentPlayerIndex].addCard(card);
        Territory cardTerritory = card.getTerritory();
        notifyResultObservers(String.format("Recebeu carta: %s tipo %s", cardTerritory != null ? cardTerritory.name : "Coringa", card.getType().toString()));
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
        notifyResultObservers("");
        currentPlayer.putAvailableUnits(1, territory);
        moveToNextDistributionIfNeeded(currentPlayer);
        setRemainingUnitsMessage(currentPlayer.getAvailableUnits());
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
            notifyResultObservers("Origem do ataque: " + territory.name);
        } else {
            if (selectedOriginTerritory == territory) {
                notifyMessageObservers("Selecione um território de origem");
                notifyResultObservers("");
                selectedOriginTerritory = null;
                return;
            }
            if (territory.getOwner() == currentPlayer) {
                notifyResultObservers("Selecione um território de destino de um oponente");
                return;
            }
            if (selectedOriginTerritory.isNeighbor(territory) == false) {
                notifyResultObservers("Ataque não é valido, selecione um território de destino vizinho");
                return;
            }
            selectedDestinationTerritory = territory;
            notifyMessageObservers("Ataque válido, jogue os dados");
            notifyResultObservers(selectedOriginTerritory.name + " atacará " + selectedDestinationTerritory.name);
        }
    }

    private void handleSelectMovingUnits(Territory territory) {
        Player currentPlayer = players[currentPlayerIndex];
        if (selectedOriginTerritory == null) {
            if (territory.getOwner() != currentPlayer) {
                notifyResultObservers("Selecione um território de origem conquistado");
                return;
            }
            if (!territory.canTransferUnits()) {
                notifyResultObservers("Selecione um território de origem conquistado com mais de um exército");
                return;
            }
            selectedOriginTerritory = territory;
            notifyMessageObservers("Selecione um território de destino");
            notifyResultObservers("Origem da transferência: " + territory.name);
        } else {
            if (selectedOriginTerritory == territory) {
                notifyMessageObservers("Selecione um território de origem");
                notifyResultObservers("");
                selectedOriginTerritory = null;
                return;
            }
            if (!selectedOriginTerritory.isTransferArmyValid(territory, 1)) {
                notifyResultObservers("Selecione um território de destino vizinho conquistado");
                return;
            }
            selectedOriginTerritory.transferArmy(territory, 1);
            notifyMessageObservers("Selecione um território de origem");
            notifyResultObservers("Exército movido de " + selectedOriginTerritory.name + " para " + territory.name);
            selectedOriginTerritory = null;
        }
    }

    public void goToNextPlay() {
        String errorMessage = advanceToNextState();
        if (errorMessage != null) {
            notifyResultObservers(errorMessage);
            return;
        }
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

    // Returns null if the game state does not allow saving
    public SaveMatch getMatchSaveData() {
        switch (currentState) {
            case attacking:
            case movingUnits:
                return new SaveMatch(getSaveTerritoriesData(), getSavePlayersData(), getSaveMatchInfoData());
            default:
                return null;
        }
    }

    private SavePlayer[] getSavePlayersData() {
        SavePlayer[] savingPlayers = new SavePlayer[players.length];
        for(int i=0; i < players.length; i++){
            String[] cards = new String[players[i].getCards().size()];
            for(int numberOfCards = 0; numberOfCards<players[i].getCards().size(); numberOfCards++){
                List<Card> playerCards =  players[i].getCards();
                cards[numberOfCards] = playerCards.get(numberOfCards).getType().toString();

            }
            savingPlayers[i] = new SavePlayer(players[i].getName(), players[i].getColor().getName(), players[i].getObjective().getDescription(),cards,players[i].getAvailableUnits(), players[i].getNumberOfCardExchanges());
        }
        return savingPlayers;
    }

    private SaveTerritory[] getSaveTerritoriesData() {
        SaveTerritory[] savingTerritories = new SaveTerritory[51];
        int index = 0;
        Collection<Continent> continents = board.continents.values();
        for (Continent continent : continents) {
            for (Territory territory : continent.getTerritories()) {
                SaveTerritory saveTerritory = new SaveTerritory(territory.name, territory.getArmyCount(), territory.getOwner().getName());
                savingTerritories[index] = saveTerritory;
                index++;
            }
        }
        return savingTerritories;
    }

    private SaveMatchInfo getSaveMatchInfoData() {
        String[] bonusStrings = new String[bonusContinentsToDistribute.size()];
        for(int i=0; i < bonusContinentsToDistribute.size(); i++){
            bonusStrings[i] = bonusContinentsToDistribute.get(i).name;
        }
        SaveMatchInfo savingMatch = new SaveMatchInfo(currentPlayerIndex, currentState.name, currentPlayerHasConqueredTerritories, bonusStrings);
        return savingMatch;
    }

    public void loadedPlayers(SavePlayer[] players){
        Player[] loadedPlayers = new Player[players.length];
        List<Card> cards = new ArrayList<>();
        for (int i = 0; i < players.length; i++) {
            loadedPlayers[i] = new Player(players[i].name, translateToPlayerColor(players[i].color));
            loadedPlayers[i].setAvailableUnits(players[i].getAvailableUnits());
            loadedPlayers[i].setNumberOfCardExchanges(players[i].getNumberOfCardExchanges());
            for (Objective objective : board.objectives) {
                if(objective.getDescription().compareTo(players[i].objective) == 0){
                    loadedPlayers[i].setObjective(objective);
                    break;
                }
            }
            for (Card card : board.cards) {
                for(int numberOfCards = 0; numberOfCards < players[i].cards.length; numberOfCards++){
                    if(card.getType().toString().compareTo(players[i].cards[numberOfCards]) == 0){
                        cards.add(card);
                    }
                }
            }
            
        }
        this.players = loadedPlayers;
    }

    PlayerColor translateToPlayerColor(String color){
        switch (color) {
            case "Vermelha": return PlayerColor.red;
            case "Verde": return PlayerColor.green;
            case "Azul": return PlayerColor.blue;
            case "Amarela": return PlayerColor.yellow;
            case "Preta": return PlayerColor.black;
            case "Branca": return PlayerColor.white;
        }
        return null;
    }

    public void loadMatchInfo(SaveMatchInfo matchInfo){
        this.currentPlayerHasConqueredTerritories = matchInfo.currentPlayerHasConqueredTerritories;
        this.currentPlayerIndex = matchInfo.currentPlayerIndex;
        this.setState(traslateState(matchInfo.gameState));
        for (Continent continent : board.continents.values()) {
            for(int i=0; i<matchInfo.bonusContinentsToDistribute.length; i++ ){
                if(continent.toString().compareTo(matchInfo.bonusContinentsToDistribute[i]) == 0){
                }
            }
        }
    }

    GameState traslateState(String state){
        switch(state){
            case "Distribuição Primeira Rodada": return GameState.firstRoundDistribute;
            case "Distribuindo Exércitos": return GameState.unitDistributing;
            case "Atacando": return GameState.attacking;
            case "Remanejando Exércitos": return GameState.movingUnits;
            case "Vitória": return GameState.victory;
        }
        return null;
    }

    private void loadTerritories(SaveTerritory[] territoriesInfo, Player[] possibleOwnePlayers){
        for(int i=0; i<territoriesInfo.length; i++){
            Territory territory = board.getTerritory(territoriesInfo[i].name);
            territory.addArmy(territoriesInfo[i].units-1);
            for(int playerIndex = 0; playerIndex<possibleOwnePlayers.length; playerIndex++){
                if(territoriesInfo[i].owner.compareTo(possibleOwnePlayers[playerIndex].getName()) == 0){
                    possibleOwnePlayers[playerIndex].addTerritory(territory);
                }
            }
        }
    }

    public void loadFromSaveMatch(SaveMatch match){
        loadedPlayers(match.players);
        loadTerritories(match.territories, players);
        loadMatchInfo(match.matchInfo);
        
    }
}
