package Model;

import java.io.Serializable;

public class SaveTerritory implements Serializable{
    /**
     *
     */
    private static final long serialVersionUID = -2402495553655675606L;
    String name;
    int units;
    String owner;

    SaveTerritory(String name, int units, String owner) {
		this.name = name;
        this.units = units;
        this.owner = owner;
	}
}