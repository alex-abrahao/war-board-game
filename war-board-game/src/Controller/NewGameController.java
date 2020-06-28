package Controller;

import Model.PlayerInfo;
import Model.SaveMatch;
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

    public void didSelectLoadGame(){
        SaveGameController saveController = new SaveGameController();
        SaveMatch newMatch = saveController.loadGame("saveData.dat");
        if(newMatch != null){
            MapController mapController = new MapController(newMatch);
            MapView mapView = new MapView(mapController);
            mapView.setVisible(true);
            view.dispose();
        }
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
