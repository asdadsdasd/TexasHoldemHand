package entities.enums;

public enum Rank {
    TWO('2', 2), THREE('3', 3), FOUR('4', 4), FIVE('5', 5), SIX('6', 6),
    SEVEN('7', 7), EIGHT('8', 8), NINE('9', 9), TEN('T', 10),
    JACK('J', 11), QUEEN('Q', 12), KING('K', 13), ACE('A', 14);

    private final char symbol;
    private final int value;

    Rank(char symbol, int value) {
        this.symbol = symbol;
        this.value = value;
    }

    public char getSymbol() {
        return symbol;
    }

    public int getValue() {
        return value;
    }

    public static Rank fromChar(char c) {
        for (Rank rank : values()) {
            if (rank.symbol == c) {
                return rank;
            }
        }
        throw new IllegalArgumentException("Неверный зачение карты: " + c);
    }
}
