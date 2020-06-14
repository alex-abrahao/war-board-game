package Model;
import java.awt.Color;

public enum PlayerColor {
    red, green, blue, yellow, black, white;

    public Color getColor() {
        switch (this) {
            case red: return Color.red;
            case green: return Color.green;
            case blue: return Color.blue;
            case yellow: return Color.yellow;
            case black: return Color.black;
            case white: return Color.white;
        }
        // Should be unreacheable
        return null;
    }

    public String getName() {
        switch (this) {
            case red: return "Vermelha";
            case green: return "Verde";
            case blue: return "Azul";
            case yellow: return "Amarela";
            case black: return "Preta";
            case white: return "Branca";
        }
        // Should be unreacheable
        return null;
    }
}