package Model;

public enum Continents {
    Europe("Europa"),
    NorthAmerica("América do Norte"),
    SouthAmerica("América do Sul"),
    Asia("Ásia"),
    Oceania("Oceania"),
    Africa("África");

    private final String name;

    Continents(String name) {
        this.name = name;
    }

    String getName() {
        return name;
    }
}