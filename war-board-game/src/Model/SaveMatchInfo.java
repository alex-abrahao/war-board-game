package Model;

import java.io.Serializable;

public class SaveMatchInfo implements Serializable{

    private static final long serialVersionUID = 3296264785554985676L;
    
    int currentPlayerIndex;
    String gameState;
    boolean currentPlayerHasConqueredTerritories;

    SaveMatchInfo(int currentPlayerIndex, String gameState, boolean currentPlayerHasConqueredTerritories) {
        this.currentPlayerIndex = currentPlayerIndex;
        this.gameState = gameState;
        this.currentPlayerHasConqueredTerritories = currentPlayerHasConqueredTerritories;
	}

}