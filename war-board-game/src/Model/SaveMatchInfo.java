package Model;

import java.io.Serializable;

public class SaveMatchInfo implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = 3296264785554985676L;
    
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