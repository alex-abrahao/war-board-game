package Model;

public enum CardType {
    triangle, circle, square, joker;

    @Override
    public String toString() {
        switch (this) {
            case triangle: return "triangulo";
            case circle: return "circulo";
            case square: return "quadrado";
            default: return "coringa";
        }
    }
}