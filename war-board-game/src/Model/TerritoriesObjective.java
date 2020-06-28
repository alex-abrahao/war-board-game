package Model;

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
    boolean isCompleted(Player player, Player[] allPlayers) {
        return player.getConqueredTerritoriesNumber() >= numberToConquer;
    }
}