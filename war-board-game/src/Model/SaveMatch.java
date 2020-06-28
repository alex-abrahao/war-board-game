package Model;

import java.io.Serializable;

public class SaveMatch implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 3004658447926335353L;
    SaveTerritory[] territories;
    SavePlayer[] players;
    SaveMatchInfo matchInfo;

    public SaveMatch(SaveTerritory[] territories, SavePlayer[] players, SaveMatchInfo matchInfo) {
		this.territories = territories;
        this.players = players;
        this.matchInfo = matchInfo;
	}

	
}
