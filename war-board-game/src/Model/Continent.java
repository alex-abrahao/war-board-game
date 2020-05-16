package Model;

import java.util.HashMap;
import java.util.Map;

class Continent {

	final String name;
	private final Map<String, Territory> territories;
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

	int getContinentBonus() {
		return continentBonus;
	}

	/**
	 * To be called at the end of a round.
	 * @return Continent conquereor player, or {@code null} if no player has conquered.
	 */
	Player getConqueror() {
		Player conqueror = null;
		for (Territory territory : territories.values()) {
			if (conqueror == null) {
				// First passing
				conqueror = territory.getOwner();
			} else if (conqueror != territory.getOwner()) {
				return null;
			}
		}
		// This player has all the territories in the continent
		return conqueror;
	}

	Territory getTerritory(String name) {
		return territories.get(name);
	}
}
