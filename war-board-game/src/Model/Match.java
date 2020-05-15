package Model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Match {

    Player[] players;
    private int currentRound = 0;
    private Board board = new Board();
    
    Match() {}

    public void setPlayers(PlayerInfo[] players) {

        // Cria Players novos baseados nas infos
        this.players = new Player[players.length];
        for (int i = 0; i < players.length; i++) {
            this.players[i] = new Player(players[i]);
        }
    }

    public void start() {

        // Embaralha o array de players
        List<Player> playerList = Arrays.asList(this.players);
        Collections.shuffle(playerList);
        playerList.toArray(this.players);
        // O array embaralhado agora representa a ordem dos jogadores
        for (int i = 0; i < players.length; i++) {
            this.players[i].orderToPlay = i + 1;
        }
        // distribuir territorios (ou cartas) aleatoriamente
        // distribuir exercitos extras pra cada player
    }

    public void reset(boolean keepPlayers) {

        board = new Board();
        // reset players dependendo do bool
    }
}
