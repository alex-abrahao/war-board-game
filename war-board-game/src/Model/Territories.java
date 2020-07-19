package Model;

public enum Territories {

    France("Franca"),
    Spain("Espanha"),
    Italy("Italia"),
    Poland("Polonia"),
    Romenia("Romenia"),
    UnitedKingdom("Reino Unido"),
    Sweden("Suecia"),
    Ukraine("Ucrania"),

    Mexico("Mexico"),
    California("California"),
    Calgary("Calgary"),
    NewYork("Nova York"),
    Quebec("Quebec"),
    Vancouver("Vancouver"),
    Alaska("Alasca"),
    Greenland("Groenlandia"),
    Texas("Texas"),

    Brazil("Brasil"),
    Argentina("Argentina"),
    Venezuela("Venezuela"),
    Peru("Peru"),

    SaudiArabia("Arabia Saudita"),
    Bangladesh("Bangladesh"),
    Kazakhstan("Cazaquistao"),
    NorthCorea("Coreia do Norte"),
    SouthCorea("Coreia do Sul"),
    Siberia("Siberia"),
    Mongolia("Mongolia"),
    Estonia("Estonia"),
    Iran("Ira"),
    Iraq("Iraque"),
    China("China"),
    India("India"),
    Japan("Japao"),
    Jordan("Jordania"),
    Letonia("Letonia"),
    Pakistan("Paquistao"),
    Russia("Russia"),
    Siria("Siria"),
    Thailand("Tailandia"),
    Turkey("Turquia"),

    Australia("Australia"),
    Indonesia("Indonesia"),
    Perth("Perth"),
    NewZealand("Nova Zelandia"),

    Algeria("Argelia"),
    Angola("Angola"),
    SouthAfrica("Africa do Sul"),
    Nigeria("Nigeria"),
    Somalia("Somalia"),
    Egypt("Egito");
    
    private final String name;

    private Territories(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}