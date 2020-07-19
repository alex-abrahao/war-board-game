package Model;

enum GameState {
    firstRoundDistribute("Distribuicao Primeira Rodada"),
    unitDistributing("Distribuindo Exercitos"),
    attacking("Atacando"),
    movingUnits("Remanejando Exercitos"),
    victory("Vitoria");

    final String name;

    private GameState(String name) {
        this.name = name;
    }
}