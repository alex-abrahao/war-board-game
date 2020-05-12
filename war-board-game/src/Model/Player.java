package Model;


public class Player {
	
	private final String name;
	private final PlayerColor color;
	int orderToPlay;
	private Card[] cards;
	private Territory[] territories;
	private Objective objective;
	
	Player(String name, PlayerColor color) {
		this.name = name;
		this.color = color;
	}

	public void setObjective (Objective objective){
		this.objective = objective;
	}

	public Objective getObjective(){
		return objective;
	}

	public Card[] getCards(){
		return cards;
	}

	public PlayerColor getColor(){
		return color;
	}
}
