package Model;

public class Continent {
	private final String name;
	private final Territory[] territories;
	
	Continent(String name, Territory[] territories) {
		this.name = name;
		this.territories = territories;
	}
}
