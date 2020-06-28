package Model;

public class SaveMatchInfo {
    int currentPlayerIndex;
    String gameState;
    boolean currentPlayerHasConqueredTerritories;
    String selectedOriginTerritory;
    String selectedDestinationTerritory;
    String[] bonusContinentsToDistribute;
    String currentContinentToDistribute;
    /* ... */

    SaveMatchInfo(int currentPlayerIndex, String gameState, boolean currentPlayerHasConqueredTerritories, String selectedOriginTerritory, String selectedDestinationTerritory, String[] bonusContinentsToDistribute, String currentContinentToDistribute) {
		this.currentPlayerIndex = currentPlayerIndex;
        this.gameState = gameState;
        this.currentPlayerHasConqueredTerritories = currentPlayerHasConqueredTerritories;
        this.selectedOriginTerritory = selectedOriginTerritory;
        this.selectedDestinationTerritory = selectedDestinationTerritory;
        this.bonusContinentsToDistribute = bonusContinentsToDistribute;
        this.currentContinentToDistribute = currentContinentToDistribute;
	}

}