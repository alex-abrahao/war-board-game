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
            view.showSaveGameError("É possível salvar apenas durante o ataque ou movimentação de unidades");
            return;
        }
        if (SaveGame.saveBinary(save, fileName) == false) {
            view.showSaveGameError("Problema ao salvar no arquivo " + fileName);
            return;
        }
        view.showSaveGameSuccess();
    }
    
}