package Model;
import java.util.Random;

public class Objective {
    final String description;
    final ObjectiveType type;

    Objective(String description, ObjectiveType type) {
        this.description = description;
        this.type = type;
    }

    public String getDescription(){
        return description;
    }

    public ObjectiveType getObjectiveType(){
        return type;
    }

    public Objective getRandomObjective(Objective[] objectives){
        Random generator = new Random();
		int randomIndex = generator.nextInt(objectives.length);
		return objectives[randomIndex];

    }
}
