package Model.observer;

import Model.PlayerColor;

public interface UnitNumberObserver {
    void notify(int units, PlayerColor color);
}