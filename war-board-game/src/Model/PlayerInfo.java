package Model;

public class PlayerInfo {
    public final String name;
    public final PlayerColor color;

    public PlayerInfo(String name, PlayerColor color) {
        this.name = name;
        this.color = color;
    }

    PlayerInfo(Player player) {
        this.name = player.getName();
        this.color = player.getColor();
    }
}