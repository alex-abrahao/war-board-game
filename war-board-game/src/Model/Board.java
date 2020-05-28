package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

class Board {

    private final Map<String, Continent> continents = makeContinents();
    final List<Objective> objectives;
    final List<Card> cards;

    Board() {
        makeTerritoryConnections();
        objectives = makeObjectivesList();
        cards = makeCardsList();
    }

    Continent getContinent(String name) {
        return continents.get(name);
    }

    Continent getContinent(Continents c) {
        return getContinent(c.getName());
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

    Territory getTerritory(Territories t) {
        return getTerritory(t.getName());
    }

    public Card getRandomCard(List<Card> cards) 
    { 
        Random rand = new Random(); 
        return cards.get(rand.nextInt(cards.size())); 
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
            new Territory(Territories.Germany),
            new Territory(Territories.SpainPortugalFranceItaly),
            new Territory(Territories.Moscow),
            new Territory(Territories.PolandYugoslavia),
            new Territory(Territories.Iceland),
            new Territory(Territories.England),
            new Territory(Territories.Sweden)
        };

        final Territory[] northAmericaTerritories = {
            new Territory(Territories.Mexico),
            new Territory(Territories.California),
            new Territory(Territories.Labrador),
            new Territory(Territories.NewYork),
            new Territory(Territories.Ottawa),
            new Territory(Territories.Vancouver),
            new Territory(Territories.Alaska),
            new Territory(Territories.Greenland),
            new Territory(Territories.Mackenzie)
        };

        final Territory[] southAmericaTerritories = {
            new Territory(Territories.Brazil),
            new Territory(Territories.ArgentinaUruguay),
            new Territory(Territories.ColombiaVenezuela),
            new Territory(Territories.PeruBoliviaChile)
        };

        final Territory[] asiaTerritories = {
            new Territory(Territories.MiddleEast),
            new Territory(Territories.Aral),
            new Territory(Territories.Omsk),
            new Territory(Territories.Dudinka),
            new Territory(Territories.Siberia),
            new Territory(Territories.Mongolia),
            new Territory(Territories.Tchita),
            new Territory(Territories.Vladivostok),
            new Territory(Territories.China),
            new Territory(Territories.India),
            new Territory(Territories.Japan),
            new Territory(Territories.Vietnam)
        };

        final Territory[] oceaniaTerritories = {
            new Territory(Territories.Australia),
            new Territory(Territories.Borneo),
            new Territory(Territories.Sumatra),
            new Territory(Territories.NewGuinea)
        };

        final Territory[] africaTerritories = {
            new Territory(Territories.AlgeriaNigeria),
            new Territory(Territories.Madagascar),
            new Territory(Territories.SouthAfrica),
            new Territory(Territories.Congo),
            new Territory(Territories.Sudan),
            new Territory(Territories.Egypt)
        };

        final Continent[] continentsArray = {
            new Continent(Continents.Europe, europeTerritories, 5),
            new Continent(Continents.NorthAmerica, northAmericaTerritories, 5),
            new Continent(Continents.SouthAmerica, southAmericaTerritories, 2),
            new Continent(Continents.Asia, asiaTerritories, 7),
            new Continent(Continents.Oceania, oceaniaTerritories, 2),
            new Continent(Continents.Africa, africaTerritories, 3)
        };

        return makeContinentsMap(continentsArray);
    }

    private void makeTerritoryConnections() {
        final Continent southAmerica = getContinent(Continents.SouthAmerica),
                        northAmerica = getContinent(Continents.NorthAmerica),
                        africa = getContinent(Continents.Africa),
                        europe = getContinent(Continents.Europe),
                        oceania = getContinent(Continents.Oceania),
                        asia = getContinent(Continents.Asia);

        // South America
        final Territory brazil = southAmerica.getTerritory(Territories.Brazil);
        brazil.addContinentNeighbors(new Territories[] {
            Territories.ArgentinaUruguay,
            Territories.ColombiaVenezuela,
            Territories.PeruBoliviaChile
        });
        brazil.addNeighbor(africa.getTerritory(Territories.AlgeriaNigeria));
        southAmerica.getTerritory(Territories.PeruBoliviaChile).addContinentNeighbors(new Territories[] {
            Territories.ArgentinaUruguay,
            Territories.ColombiaVenezuela
        });
        southAmerica.getTerritory(Territories.ColombiaVenezuela).addNeighbor(northAmerica.getTerritory(Territories.Mexico));

        // North America
        northAmerica.getTerritory(Territories.Ottawa).addContinentNeighbors(new Territories[] {
            Territories.Mackenzie,
            Territories.Vancouver,
            Territories.California,
            Territories.NewYork,
            Territories.Labrador
        });
        northAmerica.getTerritory(Territories.NewYork).addContinentNeighbors(new Territories[] {
            Territories.Labrador,
            Territories.Mexico,
            Territories.California
        });
        northAmerica.getTerritory(Territories.California).addContinentNeighbors(new Territories[] {
            Territories.Vancouver,
            Territories.Mexico
        });
        northAmerica.getTerritory(Territories.Mackenzie).addContinentNeighbors(new Territories[] {
            Territories.Vancouver,
            Territories.Alaska,
            Territories.Greenland
        });
        final Territory greenland = northAmerica.getTerritory(Territories.Greenland);
        greenland.addContinentNeighbors(new Territories[] {Territories.Labrador});
        greenland.addNeighbor(europe.getTerritory(Territories.Iceland));
        final Territory alaska = northAmerica.getTerritory(Territories.Alaska);
        alaska.addContinentNeighbors(new Territories[] {Territories.Vancouver});
        alaska.addNeighbor(asia.getTerritory(Territories.Vladivostok));

        // Europe
        europe.getTerritory(Territories.England).addContinentNeighbors(new Territories[] {
            Territories.Iceland,
            Territories.Sweden,
            Territories.Germany,
            Territories.SpainPortugalFranceItaly
        });
        final Territory france = europe.getTerritory(Territories.SpainPortugalFranceItaly);
        france.addContinentNeighbors(new Territories[] {
            Territories.PolandYugoslavia,
            Territories.Germany
        });
        france.addNeighbor(africa.getTerritory(Territories.AlgeriaNigeria));
        france.addNeighbor(africa.getTerritory(Territories.Egypt));
        final Territory poland = europe.getTerritory(Territories.PolandYugoslavia);
        poland.addContinentNeighbors(new Territories[] {
            Territories.Moscow,
            Territories.Germany
        });
        poland.addNeighbor(africa.getTerritory(Territories.Egypt));
        poland.addNeighbor(asia.getTerritory(Territories.MiddleEast));
        final Territory moscow = europe.getTerritory(Territories.PolandYugoslavia);
        moscow.addContinentNeighbors(new Territories[] { Territories.Sweden });
        moscow.addNeighbor(asia.getTerritory(Territories.MiddleEast));
        moscow.addNeighbor(asia.getTerritory(Territories.Omsk));
        moscow.addNeighbor(asia.getTerritory(Territories.Aral));

        // Africa
        africa.getTerritory(Territories.Sudan).addContinentNeighbors(new Territories[] {
            Territories.Congo,
            Territories.Madagascar,
            Territories.Egypt,
            Territories.AlgeriaNigeria,
            Territories.SouthAfrica
        });
        africa.getTerritory(Territories.AlgeriaNigeria).addContinentNeighbors(new Territories[] {
            Territories.Congo,
            Territories.Egypt
        });
        africa.getTerritory(Territories.SouthAfrica).addContinentNeighbors(new Territories[] {
            Territories.Madagascar,
            Territories.Congo
        });
        africa.getTerritory(Territories.Egypt).addNeighbor(asia.getTerritory(Territories.MiddleEast));

        // Oceania
        oceania.getTerritory(Territories.Australia).addContinentNeighbors(new Territories[] {
            Territories.Borneo,
            Territories.NewGuinea,
            Territories.Sumatra
        });
        oceania.getTerritory(Territories.NewGuinea).addNeighbor(oceania.getTerritory(Territories.Borneo));
        oceania.getTerritory(Territories.Borneo).addNeighbor(asia.getTerritory(Territories.Vietnam));
        oceania.getTerritory(Territories.Sumatra).addNeighbor(asia.getTerritory(Territories.India));

        // Asia
        asia.getTerritory(Territories.Mongolia).addContinentNeighbors(new Territories[] {
            Territories.China,
            Territories.Omsk,
            Territories.Dudinka,
            Territories.Tchita
        });
        asia.getTerritory(Territories.China).addContinentNeighbors(new Territories[] {
            Territories.Omsk,
            Territories.Aral,
            Territories.Tchita,
            Territories.Vladivostok,
            Territories.Vietnam,
            Territories.India,
            Territories.Japan
        });
        asia.getTerritory(Territories.Vladivostok).addContinentNeighbors(new Territories[] {
            Territories.Japan,
            Territories.Siberia,
            Territories.Tchita
        });
        asia.getTerritory(Territories.India).addContinentNeighbors(new Territories[] {
            Territories.Vietnam,
            Territories.MiddleEast,
            Territories.Aral
        });
        asia.getTerritory(Territories.Aral).addContinentNeighbors(new Territories[] {
            Territories.Omsk,
            Territories.MiddleEast
        });
        asia.getTerritory(Territories.Dudinka).addContinentNeighbors(new Territories[] {
            Territories.Omsk,
            Territories.Siberia,
            Territories.Tchita
        });
        asia.getTerritory(Territories.Siberia).addNeighbor(asia.getTerritory(Territories.Tchita));
    }

    private List<Objective> makeObjectivesList() {
        List<Objective> objectivesList = new ArrayList<>();
        // TODO: Criar a lista de objetivos
        objectivesList.add(new Objective("Descrição de conquista de continente", ObjectiveType.conquerContinents));
        objectivesList.add(new Objective("Descrição de eliminação de jogador", ObjectiveType.defeatPlayer));
        objectivesList.add(new Objective("Descrição de conquista de territórios", ObjectiveType.numberOfTerritories));
        return objectivesList;
    }

    private List<Card> makeCardsList() {
        List<Card> cardsList = new ArrayList<>();
        // TODO: Criar a lista de objetivos
        cardsList.add(new Card(getContinent(Continents.Europe).getTerritory(Territories.England), CardType.square));
        cardsList.add(new Card(getContinent(Continents.Europe).getTerritory(Territories.Germany), CardType.circle));
        cardsList.add(new Card(null, CardType.joker));
        return cardsList;
    }
}