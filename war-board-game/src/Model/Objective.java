package Model;
import java.util.List;
import java.util.Random;

abstract class Objective {
    
    final ObjectiveType type;

    Objective(ObjectiveType type) {
        this.type = type;
    }

    abstract String getDescription();
    abstract boolean isCompleted(Player player, List<Player> allPlayers);

    static private int getRandomIndexObjective(List<Objective> objectives) {
        Random generator = new Random();
		int randomIndex = generator.nextInt(objectives.size());
		return randomIndex;
    }

    static void changeObjective(Player[] players, int playerIndex, List<Objective> objectives){
        List<Objective> objectivesCopy = List.copyOf(objectives);
        for (int i = 0; i < players.length; i++) {
            int objectiveIndex = getRandomIndexObjective(objectivesCopy);
            objectivesCopy.remove(objectiveIndex);
        }
        int newObjectiveindex = getRandomIndexObjective(objectivesCopy);
        players[playerIndex].setObjective(objectivesCopy.get(newObjectiveindex));
    }
}
