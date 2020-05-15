package Model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Match {

    Player[] players;
    private static final Continent[] continents = makeContinents();
    private int currentRound = 0;
    
    Match() {}

    public void setPlayers(PlayerInfo[] players) {

        // Cria Players novos baseados nas infos
        this.players = new Player[players.length];
        for (int i = 0; i < players.length; i++) {
            this.players[i] = new Player(players[i]);
        }
        // Embaralha o array de players
        List<Player> playerList = Arrays.asList(this.players);
        Collections.shuffle(playerList);
        playerList.toArray(this.players);
        // O array embaralhado agora representa a ordem dos jogadores
        for (int i = 0; i < players.length; i++) {
            this.players[i].orderToPlay = i + 1;
        }
    }

    public void start() {

        // randomizar ordem dos players
        // distribuir territorios (ou cartas) aleatoriamente
        // distribuir exercitos extras pra cada player

    }

    public void reset(boolean keepPlayers) {

        // pra cada territorio
            // reset army count pra 1
            // player owner = null
        // reset players dependendo do bool

    }

    private static Continent[] makeContinents() {

        Territory[] europeTerritories = {
            new Territory("Alemanha"),
            new Territory("Espanha, Portugal, França, Itália"),
            new Territory("Moscou"),
            new Territory("Polônia, Iugoslávia"),
            new Territory("Islândia"),
            new Territory("Inglaterra"),
            new Territory("Suécia")
        };

        Territory[] northAmericaTerritories = {
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

        Territory[] southAmericaTerritories = {
            new Territory("Brasil"),
            new Territory("Argentina, Uruguai"),
            new Territory("Colômbia, Venezuela"),
            new Territory("Peru, Bolívia, Chile")
        };

        Territory[] asiaTerritories = {
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

        Territory[] oceaniaTerritories = {
            new Territory("Austrália"),
            new Territory("Bornéu"),
            new Territory("Sumatra"),
            new Territory("Nova Guiné")
        };

        Territory[] africaTerritories = {
            new Territory("Argélia, Nigéria"),
            new Territory("Madagascar"),
            new Territory("África do Sul"),
            new Territory("Congo"),
            new Territory("Sudão"),
            new Territory("Egito")
        };

        Continent[] continents = {
            new Continent("Europa", europeTerritories, 5),
            new Continent("América do Norte", northAmericaTerritories, 5),
            new Continent("América do Sul", southAmericaTerritories, 2),
            new Continent("Ásia", asiaTerritories, 7),
            new Continent("Oceania", oceaniaTerritories, 2),
            new Continent("África'", africaTerritories, 3),
        };
        return continents;
    }
}
