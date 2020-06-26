package Controller;

import Model.Match;
import Model.PlayerInfo;
import Model.Territories;
import Model.observer.StringObserver;
import Model.observer.UnitNumberObserver;
import View.MapView;
import View.UnitsLabelDelegate;

public class MapController implements Controller<MapView>, UnitsLabelDelegate {

    private MapView view;
    private Match match = Match.getInstance();

    public MapController(PlayerInfo[] players) {
        match.setPlayers(players);
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

    public void start() {
        match.start();
    }

    @Override
    public void didSelectLabel(Territories territory) {
        // TODO: Implement
    }
}