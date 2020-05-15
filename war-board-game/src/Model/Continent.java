package Model;

class Continent {

	final String name;
	private final Territory[] territories;
	private Player conqueror;
	private final int continentBonus;

	Continent(String name, Territory[] territories, int continentBonus) {
		this.name = name;
		this.territories = territories;
		this.continentBonus = continentBonus;

		setupTerritories();
	}

	private void setupTerritories() {

		for (Territory territory : territories) {
			territory.setContinent(this);
		}
	}

	int getContinentBonus(){
		return continentBonus;
	}

	Player getConqueror() {
		return conqueror;
	}
}
