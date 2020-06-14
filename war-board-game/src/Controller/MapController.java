package Controller;

import Model.Match;
import Model.PlayerInfo;
import View.MapView;

public class MapController implements Controller<MapView> {

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
}