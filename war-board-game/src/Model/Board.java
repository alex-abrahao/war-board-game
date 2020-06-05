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
        cards.remove(cards.get(rand.nextInt(cards.size())));
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
            new Territory(Territories.France),
            new Territory(Territories.Spain),
            new Territory(Territories.Italy),
            new Territory(Territories.Poland),
            new Territory(Territories.Romenia),
            new Territory(Territories.UnitedKingdom),
            new Territory(Territories.Sweden),
            new Territory(Territories.Ukraine)
        };

        final Territory[] northAmericaTerritories = {
            new Territory(Territories.Mexico),
            new Territory(Territories.California),
            new Territory(Territories.Calgary),
            new Territory(Territories.NewYork),
            new Territory(Territories.Quebec),
            new Territory(Territories.Vancouver),
            new Territory(Territories.Alaska),
            new Territory(Territories.Greenland),
            new Territory(Territories.Texas)
        };

        final Territory[] southAmericaTerritories = {
            new Territory(Territories.Brazil),
            new Territory(Territories.Argentina),
            new Territory(Territories.Venezuela),
            new Territory(Territories.Peru)
        };

        final Territory[] asiaTerritories = {
            new Territory(Territories.SaudiArabia),
            new Territory(Territories.Bangladesh),
            new Territory(Territories.Kazakhstan),
            new Territory(Territories.NorthCorea),
            new Territory(Territories.SouthCorea),
            new Territory(Territories.Siberia),
            new Territory(Territories.Mongolia),
            new Territory(Territories.Iran),
            new Territory(Territories.Iraq),
            new Territory(Territories.China),
            new Territory(Territories.India),
            new Territory(Territories.Japan),
            new Territory(Territories.Jordan),
            new Territory(Territories.Letonia),
            new Territory(Territories.Pakistan),
            new Territory(Territories.Russia),
            new Territory(Territories.Siria),
            new Territory(Territories.Thailand),
            new Territory(Territories.Turkey)
        };

        final Territory[] oceaniaTerritories = {
            new Territory(Territories.Australia),
            new Territory(Territories.Indonesia),
            new Territory(Territories.Perth),
            new Territory(Territories.NewZealand)
        };

        final Territory[] africaTerritories = {
            new Territory(Territories.Algeria),
            new Territory(Territories.Angola),
            new Territory(Territories.SouthAfrica),
            new Territory(Territories.Nigeria),
            new Territory(Territories.Somalia),
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
            Territories.Argentina,
            Territories.Venezuela,
            Territories.Peru
        });
        brazil.addNeighbor(africa.getTerritory(Territories.Nigeria));
        southAmerica.getTerritory(Territories.Peru).addContinentNeighbors(new Territories[] {
            Territories.Argentina,
            Territories.Venezuela
        });
        southAmerica.getTerritory(Territories.Venezuela).addNeighbor(northAmerica.getTerritory(Territories.Mexico));

        // North America
        northAmerica.getTerritory(Territories.Texas).addContinentNeighbors(new Territories[] {
            Territories.Mexico,
            Territories.Vancouver,
            Territories.California,
            Territories.NewYork,
            Territories.Quebec
        });
        northAmerica.getTerritory(Territories.Quebec).addContinentNeighbors(new Territories[] {
            Territories.Vancouver,
            Territories.Greenland,
            Territories.NewYork
        });
        northAmerica.getTerritory(Territories.California).addContinentNeighbors(new Territories[] {
            Territories.Vancouver,
            Territories.Mexico
        });
        northAmerica.getTerritory(Territories.Calgary).addContinentNeighbors(new Territories[] {
            Territories.Vancouver,
            Territories.Alaska,
            Territories.Greenland
        });
        final Territory greenland = northAmerica.getTerritory(Territories.Greenland);
        greenland.addNeighbor(europe.getTerritory(Territories.UnitedKingdom));
        final Territory alaska = northAmerica.getTerritory(Territories.Alaska);
        alaska.addContinentNeighbors(new Territories[] {Territories.Vancouver});
        alaska.addNeighbor(asia.getTerritory(Territories.Siberia));

        // Europe
        europe.getTerritory(Territories.France).addContinentNeighbors(new Territories[] {
            Territories.UnitedKingdom,
            Territories.Sweden,
            Territories.Spain,
            Territories.Italy
        });
        final Territory italy = europe.getTerritory(Territories.Italy);
        italy.addContinentNeighbors(new Territories[] {
            Territories.Sweden,
            Territories.Poland,
            Territories.Romenia
        });
        italy.addNeighbor(africa.getTerritory(Territories.Algeria));
        final Territory poland = europe.getTerritory(Territories.Poland);
        poland.addContinentNeighbors(new Territories[] {
            Territories.Romenia,
            Territories.Ukraine
        });
        poland.addNeighbor(asia.getTerritory(Territories.Letonia));
        final Territory ukraine = europe.getTerritory(Territories.Ukraine);
        ukraine.addContinentNeighbors(new Territories[] {Territories.Romenia });
        ukraine.addNeighbor(asia.getTerritory(Territories.Letonia));
        ukraine.addNeighbor(asia.getTerritory(Territories.Turkey));
        final Territory sweden = europe.getTerritory(Territories.Sweden);
        sweden.addNeighbor(asia.getTerritory(Territories.Letonia));
        sweden.addNeighbor(asia.getTerritory(Territories.Estonia));
        final Territory romenia = europe.getTerritory(Territories.Romenia);
        romenia.addNeighbor(africa.getTerritory(Territories.Egypt));
        final Territory spain = europe.getTerritory(Territories.Spain);
        spain.addNeighbor(asia.getTerritory(Territories.Algeria));

        // Africa
        africa.getTerritory(Territories.Nigeria).addContinentNeighbors(new Territories[] {
            Territories.Algeria,
            Territories.Angola,
            Territories.Egypt,
            Territories.Somalia
        });
        final Territory egypt = africa.getTerritory(Territories.Egypt);
        egypt.addContinentNeighbors(new Territories[] {
            Territories.Algeria,
            Territories.Somalia
        });
        egypt.addNeighbor(asia.getTerritory(Territories.Jordan));
        final Territory somalia = africa.getTerritory(Territories.Somalia);
        somalia.addContinentNeighbors(new Territories[] {
            Territories.SouthAfrica,
            Territories.Angola
        });
        somalia.addNeighbor(asia.getTerritory(Territories.SaudiArabia));
        africa.getTerritory(Territories.Angola).addContinentNeighbors(new Territories[] { Territories.SouthAfrica });

        // Oceania
        oceania.getTerritory(Territories.Australia).addContinentNeighbors(new Territories[] {
            Territories.Indonesia,
            Territories.NewZealand,
            Territories.Perth
        });
        final Territory indonesia = oceania.getTerritory(Territories.Indonesia);
        indonesia.addContinentNeighbors(new Territories[] {Territories.NewZealand});
        indonesia.addNeighbor(asia.getTerritory(Territories.India));
        indonesia.addNeighbor(asia.getTerritory(Territories.Bangladesh));

        // Asia
        asia.getTerritory(Territories.China).addContinentNeighbors(new Territories[] {
            Territories.Kazakhstan,
            Territories.NorthCorea,
            Territories.SouthCorea,
            Territories.Mongolia,
            Territories.Pakistan,
            Territories.India,
            Territories.Turkey
        });
        asia.getTerritory(Territories.Japan).addContinentNeighbors(new Territories[] {
            Territories.NorthCorea,
            Territories.Mongolia,
            Territories.Kazakhstan
        });
        asia.getTerritory(Territories.Kazakhstan).addContinentNeighbors(new Territories[] {
            Territories.Mongolia,
            Territories.Siberia,
            Territories.Russia,
            Territories.Letonia
        });
        asia.getTerritory(Territories.SouthCorea).addContinentNeighbors(new Territories[] {
            Territories.NorthCorea,
            Territories.India,
            Territories.Bangladesh,
            Territories.Thailand
        });
        asia.getTerritory(Territories.Bangladesh).addContinentNeighbors(new Territories[] {
            Territories.Thailand,
            Territories.India
        });
        asia.getTerritory(Territories.Pakistan).addContinentNeighbors(new Territories[] {
            Territories.Iran,
            Territories.India,
            Territories.Siria,
            Territories.Turkey
        });
        asia.getTerritory(Territories.Russia).addContinentNeighbors(new Territories[] {
            Territories.Siberia,
            Territories.Letonia,
            Territories.Estonia
        });
        asia.getTerritory(Territories.Letonia).addContinentNeighbors(new Territories[] {
            Territories.Turkey,
            Territories.Estonia
        });
        asia.getTerritory(Territories.Siria).addContinentNeighbors(new Territories[] {
            Territories.Turkey,
            Territories.Iran,
            Territories.Iraq,
            Territories.Jordan
        });
        asia.getTerritory(Territories.Iraq).addContinentNeighbors(new Territories[] {
            Territories.Iran,
            Territories.SaudiArabia,
            Territories.Jordan
        });
        asia.getTerritory(Territories.SaudiArabia).addContinentNeighbors(new Territories[] { Territories.Jordan });
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
        // TODO: Criar a lista de cartas de território
        cardsList.add(new Card(getContinent(Continents.Europe).getTerritory(Territories.Poland), CardType.square));
        cardsList.add(new Card(getContinent(Continents.Europe).getTerritory(Territories.UnitedKingdom), CardType.circle));
        cardsList.add(new Card(null, CardType.joker));
        return cardsList;
    }
}