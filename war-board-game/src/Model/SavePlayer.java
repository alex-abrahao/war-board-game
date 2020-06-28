package Model;

import java.io.Serializable;

public class SavePlayer implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -7000344874415522575L;
    String name;
    String color;
    String objective;
    String[] cards;
    int availableUnits;
    int numberOfCardExchanges;

    SavePlayer(String name, String color, String objective, String[] cards, int availableUnits, int numberOfCardExchanges) {
		this.name = name;
        this.color = color;
        this.objective = objective;
        this.cards = cards;
        this.availableUnits = availableUnits;
        this.numberOfCardExchanges = numberOfCardExchanges;
	}
}