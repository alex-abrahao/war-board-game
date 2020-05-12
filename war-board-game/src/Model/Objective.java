package Model;
import java.util.Random;
import java.util.Arrays; 

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

    public int getRandomIndexObjective(Objective[] objectives){
        Random generator = new Random();
		int randomIndex = generator.nextInt(objectives.length);
		return randomIndex;

    }

    public void setPlayerObjective(Player[] players, Objective[] objectives){
        for(int i=0; i<players.length; i++){
            int index = getRandomIndexObjective(objectives)
            players[i].setObjective(objectives[index]);
            //removing the selected objective from the list so anyone take the same one.
            for(int j = index; j < objectives.length -1; j++){
                objectives[j] = objectives[j + 1]; 
              }
        }
    }
}
