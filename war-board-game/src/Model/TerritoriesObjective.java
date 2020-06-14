package Model;

import java.util.List;

public class TerritoriesObjective extends Objective {

    private final int numberToConquer;

    TerritoriesObjective(int numberToConquer) {
        super(ObjectiveType.numberOfTerritories);
        this.numberToConquer = numberToConquer;
    }

    @Override
    String getDescription() {
        return String.format("Conquistar %d territórios", numberToConquer);
    }

    @Override
    boolean isCompleted(Player player, List<Player> allPlayers) {
        return player.getConqueredTerritoriesNumber() >= numberToConquer;
    }
}