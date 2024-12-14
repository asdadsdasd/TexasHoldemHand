package entities.enums;

public enum Suit {
    SPADES('S'), HEARTS('H'), DIAMONDS('D'), CLUBS('C');

    private final char symbol;

    Suit(char symbol) {
        this.symbol = symbol;
    }

    public char getSymbol() {
        return symbol;
    }

    public static Suit fromChar(char c) {
        for (Suit suit : values()) {
            if (suit.symbol == c) {
                return suit;
            }
        }
        throw new IllegalArgumentException("Неверная масть карты: " + c);
    }
}
