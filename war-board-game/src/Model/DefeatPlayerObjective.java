package Model;

import java.util.List;

class DefeatPlayerObjective extends Objective {

    final PlayerColor colorToEliminate;

    DefeatPlayerObjective(PlayerColor colorToEliminate) {
        super(ObjectiveType.defeatPlayer);
        this.colorToEliminate = colorToEliminate;
    }

    @Override
    public String getDescription() {
        return "Eliminar todos os exércitos do jogador da cor " + colorToEliminate.getName();
    }

    @Override
    public boolean isCompleted(Player player, List<Player> allPlayers) {
        // Searchs other players to find the target
        for (Player otherPlayer : allPlayers) {
            if (otherPlayer.getColor() == colorToEliminate) {
                return otherPlayer.getConqueredTerritoriesNumber() == 0;
            }
        }
        // If it gets here, the target player has been defeated by other and the objective should have been changed
        return false;
    }
}