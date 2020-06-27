package Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ContinentObjective extends Objective {

    private List<Continent> continentList;

    ContinentObjective(Continent[] continents) {
        super(ObjectiveType.conquerContinents);
        continentList = new ArrayList<>(Arrays.asList(continents));
    }

    @Override
    String getDescription() {
        String continentNames = "";
        for (int i = 0; i < continentList.size(); i++) {
            continentNames += continentList.get(i).name;
            if (i != continentList.size() - 1) {
                continentNames += ", ";
            }
        }
        return "Conquistar inteiramente os continentes: " + continentNames;
    }

    @Override
    boolean isCompleted(Player player, List<Player> allPlayers) {
        for (Continent continent : continentList) {
            if (continent.getConqueror() != player) {
                return false;
            }
        }
        return true;
    }
}