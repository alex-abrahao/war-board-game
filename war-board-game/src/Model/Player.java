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
}
