package Model;

import java.util.HashMap;
import java.util.Map;

class Continent {

	final String name;
	private final Map<String, Territory> territories;
	private Player conqueror;
	private final int continentBonus;

	Continent(String name, Territory[] territories, int continentBonus) {
		this.name = name;
		this.territories = makeTerritoriesMap(territories);
		this.continentBonus = continentBonus;
	}

	private Map<String, Territory> makeTerritoriesMap(Territory[] territoriesArray) {

		Map<String, Territory> territoriesMap = new HashMap<String, Territory>();
		for (Territory territory : territoriesArray) {
			territoriesMap.put(territory.name, territory);
			territory.setContinent(this);
		}
		return territoriesMap;
	}

	int getContinentBonus(){
		return continentBonus;
	}

	Player getConqueror() {
		return conqueror;
	}

	Territory getTerritory(String name) {
		return territories.get(name);
	}
}
