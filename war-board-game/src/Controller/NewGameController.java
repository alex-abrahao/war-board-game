package Controller;

import Model.PlayerInfo;

import View.MapView;
import View.NewGameView;
import View.PlayerOptionsDelegate;
import View.PlayerOptionsView;

public class NewGameController implements Controller<NewGameView>, PlayerOptionsDelegate {

    private NewGameView view;

    @Override
    public void setView(NewGameView view) {
        this.view = view;
    }

    public void didSelectNewGame() {
        PlayerOptionsView optionsView = new PlayerOptionsView(this);
        optionsView.setVisible(true);
    }

    private void startNewGame(PlayerInfo[] players) {
        MapController mapController = new MapController(players);
        MapView mapView = new MapView(mapController);
        mapView.setVisible(true);
        view.dispose();
    }

    @Override
    public void didSetPlayers(PlayerInfo[] players) {
        startNewGame(players);
    }
}
