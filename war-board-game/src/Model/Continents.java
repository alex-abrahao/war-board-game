package Model;

public enum Continents {
    Europe("Europa"),
    NorthAmerica("America do Norte"),
    SouthAmerica("America do Sul"),
    Asia("Asia"),
    Oceania("Oceania"),
    Africa("Africa");

    private final String name;

    Continents(String name) {
        this.name = name;
    }

    String getName() {
        return name;
    }
}