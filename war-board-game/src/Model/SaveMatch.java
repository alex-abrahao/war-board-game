package Model;

public class SaveMatch {
    SaveTerritory[] territories;
    SavePlayer[] players;
    SaveMatchInfo matchInfo;

    SaveMatch(SaveTerritory[] territories, SavePlayer[] players, SaveMatchInfo matchInfo) {
		this.territories = territories;
        this.players = players;
        this.matchInfo = matchInfo;
	}
}
