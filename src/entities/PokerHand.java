package entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PokerHand {
    private final List<Card> cards;

    public PokerHand(String hand) {
        if (hand.isBlank() || hand.isEmpty()) {
            throw new IllegalArgumentException("Строка с картами не должна быть пустой");
        }

        String[] cardStrings = hand.split(" ");
        if (cardStrings.length != 5) {
            throw new IllegalArgumentException("Строка должна содержать 5 карт");
        }

        cards = new ArrayList<>();
        for (String cardString : cardStrings) {
            cards.add(new Card(cardString));
        }

        Collections.sort(cards);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Card card : cards) {
            sb.append(card).append(" ");
        }
        return sb.toString();
    }
}
