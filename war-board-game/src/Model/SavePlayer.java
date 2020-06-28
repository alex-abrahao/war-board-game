package Model;

public class SavePlayer {
    String name;
    String color;
    String objective;
    int availableUnits;
    int numberOfCardExchanges;

    SavePlayer(String name, String color, String objective, int availableUnits, int numberOfCardExchanges) {
		this.name = name;
        this.color = color;
        this.objective = objective;
        this.availableUnits = availableUnits;
        this.numberOfCardExchanges = numberOfCardExchanges;
	}
}