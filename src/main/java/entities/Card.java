package main.java.entities;

import main.java.entities.enums.Rank;
import main.java.entities.enums.Suit;

import java.util.Objects;

public class Card implements Comparable<Card> {
    private final Rank rank;
    private final Suit suit;


    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public Card(String card) {
        if (card.isBlank() || card.length() != 2) {
            throw new IllegalArgumentException("Неверный формат: " + card);
        }

        this.rank = Rank.fromChar(card.charAt(0));
        this.suit = Suit.fromChar(card.charAt(1));
    }

    public Rank getRank() {
        return rank;
    }

    public Suit getSuit() {
        return suit;
    }

    @Override
    public int compareTo(Card other) {
        return this.rank.compareTo(other.rank);
    }

    @Override
    public String toString() {
        return "" + rank.getSymbol() + suit.getSymbol();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return rank == card.rank && suit == card.suit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rank, suit);
    }
}
