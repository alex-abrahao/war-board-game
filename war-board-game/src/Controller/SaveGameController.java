package Controller;

import Model.Match;
import Model.SaveGame;
import Model.SaveMatch;
import Model.SavePlayer;

public class SaveGameController {
    
    
    private Match match = Match.getInstance();
   
    private void saveGame(String fileName){
        SaveMatch save = new SaveMatch(match.saveTerritories(), match.savePlayers(), match.saveMatchInfo());
        SaveGame.saveBinary(save, fileName);
    }

    
}