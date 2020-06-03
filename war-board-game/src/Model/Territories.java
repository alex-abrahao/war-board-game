package Model;

public enum Territories {

    France("França"),
    Spain("Espanha"),
    Italy("Itália"),
    Poland("Polônia"),
    Romenia("Romênia"),
    UnitedKingdom("Reino Unido"),
    Sweden("Suécia"),
    Ukraine("Ucrânia"),

    Mexico("México"),
    California("Califórnia"),
    Calgary("Calgary"),
    NewYork("Nova York"),
    Quebec("Québec"),
    Vancouver("Vancouver"),
    Alaska("Alasca"),
    Greenland("Groenlândia"),
    Texas("Texas"),

    Brazil("Brasil"),
    Argentina("Argentina"),
    Venezuela("Venezuela"),
    Peru("Peru"),

    SaudiArabia("Arábia Saudita"),
    Bangladesh("Bangladesh"),
    Kazakhstan("Cazaquistão"),
    NorthCorea("Coreia do Norte"),
    SouthCorea("Coreia do Sul"),
    Siberia("Sibéria"),
    Mongolia("Mongólia"),
    Estonia("Estônia"),
    Iran("Irã"),
    Iraq("Iraque"),
    China("China"),
    India("Índia"),
    Japan("Japão"),
    Jordan("Jordânia"),
    Letonia("Letônia"),
    Pakistan("Paquistão"),
    Russia("Rússia"),
    Siria("Síria"),
    Thailand("Tailândia"),
    Turkey("Turquia"),

    Australia("Austrália"),
    Indonesia("Indonésia"),
    Perth("Perth"),
    NewZealand("Nova Zelândia"),

    Algeria("Argélia"),
    Angola("Angola"),
    SouthAfrica("África do Sul"),
    Nigeria("Nigéria"),
    Somalia("Somalia"),
    Egypt("Egito");
    
    private final String name;

    Territories(String name) {
        this.name = name;
    }

    String getName() {
        return name;
    }
}