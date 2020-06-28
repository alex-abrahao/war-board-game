package Model;

abstract class Objective {
    
    final ObjectiveType type;

    Objective(ObjectiveType type) {
        this.type = type;
    }

    abstract String getDescription();
    abstract boolean isCompleted(Player player, Player[] allPlayers);
}
