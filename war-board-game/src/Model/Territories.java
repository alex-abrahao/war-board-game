package Model;

public enum Territories {

    Germany("Alemanha"),
    SpainPortugalFranceItaly("Espanha, Portugal, França, Itália"),
    Moscow("Moscou"),
    PolandYugoslavia("Polônia, Iugoslávia"),
    Iceland("Islândia"),
    England("Inglaterra"),
    Sweden("Suécia"),

    Mexico("México"),
    California("Califórnia"),
    Labrador("Labrador"),
    NewYork("Nova York"),
    Ottawa("Ottawa"),
    Vancouver("Vancouver"),
    Alaska("Alasca"),
    Greenland("Groenlândia"),
    Mackenzie("Mackenzie"),

    Brazil("Brasil"),
    ArgentinaUruguay("Argentina, Uruguai"),
    ColombiaVenezuela("Colômbia, Venezuela"),
    PeruBoliviaChile("Peru, Bolívia, Chile"),

    MiddleEast("Oriente Médio"),
    Aral("Aral"),
    Omsk("Omsk"),
    Dudinka("Dudinka"),
    Siberia("Sibéria"),
    Mongolia("Mongólia"),
    Tchita("Tchita"),
    Vladivostok("Vladivostok"),
    China("China"),
    India("Índia"),
    Japan("Japão"),
    Vietnam("Vietnã"),

    Australia("Austrália"),
    Borneo("Bornéu"),
    Sumatra("Sumatra"),
    NewGuinea("Nova Guiné"),

    AlgeriaNigeria("Argélia, Nigéria"),
    Madagascar("Madagascar"),
    SouthAfrica("África do Sul"),
    Congo("Congo"),
    Sudan("Sudão"),
    Egypt("Egito");
    
    private final String name;

    Territories(String name) {
        this.name = name;
    }

    String getName() {
        return name;
    }
}