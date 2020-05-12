package Model;

public class Territory {

	private final String name;
	private Continent continent;
	private Player owner;
	private int armyCount;
	private Territory[] neighbors;
	
	Territory(String name) {
		this.name = name;
	}

	public void setOwner(Player player) {
		this.owner = player;
	}

	public Player getOwner() {
		return owner;
	}

	void setContinent(Continent continent) {
		this.continent = continent;
	}
	
	public boolean isAttackValid(Territory territory) {
		
		// if (territory.getOwner().equals(this.getOwnerName())) {
		// 	return false;
		// }

		if (territory.getOwner() == this.owner) {
			return false;
		}
		
		if (this.armyCount <= 1) {
			return false;
		}

		//TO DO: checar vizinhos
		
		return true;
	}
}
