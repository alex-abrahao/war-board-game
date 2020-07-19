package Controller;

import Model.Match;
import Model.SaveGame;
import Model.SaveMatch;
import View.SaveGameView;


public class SaveGameController {
    
    private final Match match = Match.getInstance();
   
    void saveGame(String fileName, SaveGameView view) {
        SaveMatch save = match.getMatchSaveData();
        if (save == null) {
            view.showSaveGameError("Eh possivel salvar apenas durante o ataque ou movimentacao de unidades");
            return;
        }
        if (SaveGame.saveBinary(save, fileName) == false) {
            view.showSaveGameError("Problema ao salvar no arquivo " + fileName);
            return;
        }
        view.showSaveGameSuccess();
    }

    SaveMatch loadGame(String fileName){
        SaveMatch loadedMatch = SaveGame.readBinary(fileName);
        return loadedMatch;
    }

}