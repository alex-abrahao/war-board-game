package Model;

public enum CardType {
    triangle, circle, square, joker;

    @Override
    public String toString() {
        switch (this) {
            case triangle: return "triângulo";
            case circle: return "círculo";
            case square: return "quadrado";
            default: return "coringa";
        }
    }
}