package Model;

enum GameState {
    firstRoundDistribute("Distribuição Primeira Rodada"),
    unitDistributing("Distribuindo Exércitos"),
    attacking("Atacando"),
    movingUnits("Remanejando Exércitos");

    final String name;

    private GameState(String name) {
        this.name = name;
    }
}