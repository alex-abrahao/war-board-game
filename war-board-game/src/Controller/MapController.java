package Controller;

import Model.Match;
import Model.PlayerInfo;
import Model.SaveMatch;
import Model.Territories;
import Model.observer.StringObserver;
import Model.observer.UnitNumberObserver;
import View.MapView;
import View.UnitsLabelDelegate;

public class MapController implements Controller<MapView>, UnitsLabelDelegate {

    private MapView view;
    private Match match = Match.getInstance();
    private final String saveDataFileName = "saveData.dat";
    private final boolean cameFromLoad;

    public MapController(PlayerInfo[] players) {
        cameFromLoad = false;
        match.setPlayers(players);
    }
    
    public MapController(SaveMatch load){
        cameFromLoad = true;
        match.loadFromSaveMatch(load);
    }

    @Override
    public void setView(MapView view) {
        this.view = view;
    }

    public void fetchPlayersOrder() {
        String message = "Ordem dos jogadores: ";
        PlayerInfo[] players = match.getOrderedPlayers();
        for (int i = 0; i < players.length; i++) {
            message += String.format("%d: %s", i + 1, players[i].name);
            if (i < players.length - 1) {
                message += ", ";
            }
        }
        view.showPlayerOrder(message);
    }

    public void addTerritoryObserver(Territories territory, UnitNumberObserver observer) {
        match.addTerritoryObserver(territory, observer);
    }

    public void addCurrentPlayerObserver(StringObserver observer) {
        match.addCurrentPlayerObserver(observer);
    }

    public void addCurrentStateObserver(StringObserver observer) {
        match.addCurrentStateObserver(observer);
    }

    public void addMessageLabelObserver(StringObserver observer) {
        match.addMessageObserver(observer);
    }

    public void addResultLabelObserver(StringObserver observer) {
        match.addResultObserver(observer);
    }

    public void start() {
        if (!cameFromLoad){
            match.start();
        }
    }

    public void didSelectShowObjective() {
        view.showPlayerObjective(match.getCurrentPlayerObjective());
    }

    public void didSelectChooseDicesValue(){
        view.showChooseDicesView();
    }

    public void didSelectNextPlay() {
        match.goToNextPlay();
    }

    public void didSelectPlayDice() {
        match.playDice();
    }

    public void didSelectSaveGame() {
        new SaveGameController().saveGame(saveDataFileName, view);
    }

    @Override
    public void didSelectLabel(Territories territory) {
        match.selectTerritory(territory);
    }
}