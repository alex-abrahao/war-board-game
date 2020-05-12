package Model;

public class Continent {
	
	final String name;
	private final Territory[] territories;
	private Player conqueror;
	
	Continent(String name, Territory[] territories) {
		this.name = name;
		this.territories = territories;

		setupTerritories();
	}

	private void setupTerritories() {

		for (Territory territory : territories) {
			territory.setContinent(this);
		}
	}

	Player getConqueror() {
		return conqueror;
	}
}
