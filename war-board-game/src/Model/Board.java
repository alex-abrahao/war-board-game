package Model;

import java.util.HashMap;
import java.util.Map;

class Board {

    private final Map<String, Continent> continents = makeContinents();

    Continent getContinent(String name) {
        return continents.get(name);
    }

    Territory getTerritory(String name) {
        for (Continent continent : continents.values()) {
            Territory territory = continent.getTerritory(name);
            if (territory != null) {
                return territory;
            }
        }
        return null;
    }

    private Map<String, Continent> makeContinentsMap(final Continent[] continentsArray) {

        final Map<String, Continent> continentsMap = new HashMap<String, Continent>();
        for (final Continent continent : continentsArray) {
            continentsMap.put(continent.name, continent);
        }
        return continentsMap;
    }

    private Map<String, Continent> makeContinents() {

        final Territory[] europeTerritories = {
            new Territory("Alemanha"),
            new Territory("Espanha, Portugal, França, Itália"),
            new Territory("Moscou"),
            new Territory("Polônia, Iugoslávia"),
            new Territory("Islândia"),
            new Territory("Inglaterra"),
            new Territory("Suécia")
        };

        final Territory[] northAmericaTerritories = {
            new Territory("México"),
            new Territory("Califórnia"),
            new Territory("Labrador"),
            new Territory("Nova York"),
            new Territory("Ottawa"),
            new Territory("Vancouver"),
            new Territory("Alasca"),
            new Territory("Groenlândia"),
            new Territory("Mackenzie")
        };

        final Territory[] southAmericaTerritories = {
            new Territory("Brasil"),
            new Territory("Argentina, Uruguai"),
            new Territory("Colômbia, Venezuela"),
            new Territory("Peru, Bolívia, Chile")
        };

        final Territory[] asiaTerritories = {
            new Territory("Oriente Médio"),
            new Territory("Aral"),
            new Territory("Omsk"),
            new Territory("Dudinka"),
            new Territory("Sibéria"),
            new Territory("Mongólia"),
            new Territory("Tchita"),
            new Territory("Vladivostok"),
            new Territory("China"),
            new Territory("Índia"),
            new Territory("Japão"),
            new Territory("Vietnã")
        };

        final Territory[] oceaniaTerritories = {
            new Territory("Austrália"),
            new Territory("Bornéu"),
            new Territory("Sumatra"),
            new Territory("Nova Guiné")
        };

        final Territory[] africaTerritories = {
            new Territory("Argélia, Nigéria"),
            new Territory("Madagascar"),
            new Territory("África do Sul"),
            new Territory("Congo"),
            new Territory("Sudão"),
            new Territory("Egito")
        };

        final Continent[] continentsArray = {
            new Continent("Europa", europeTerritories, 5),
            new Continent("América do Norte", northAmericaTerritories, 5),
            new Continent("América do Sul", southAmericaTerritories, 2),
            new Continent("Ásia", asiaTerritories, 7),
            new Continent("Oceania", oceaniaTerritories, 2),
            new Continent("África'", africaTerritories, 3),
        };

        return makeContinentsMap(continentsArray);
    }
}