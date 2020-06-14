package Controller;

import View.MapView;

public class MapController implements Controller<MapView> {

    private MapView view;
    @Override
    public void setView(MapView view) {
        this.view = view;
    }
}