package main.java.entities;

import main.java.entities.enums.CombinationRank;
import main.java.entities.enums.Rank;
import main.java.entities.enums.Suit;

import java.util.*;
import java.util.stream.IntStream;

public class PokerHand implements Comparable<PokerHand> {
    private final List<Card> cards;
    private CombinationRank combinationRank;   // Сила комбинации
    private int combinationPower;              // Сила внутри одной комбинации


    public PokerHand(String hand) {
        if (hand == null || hand.isBlank()) {
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

        Set<Card> uniqueCards = new HashSet<>(cards);
        if (uniqueCards.size() != cards.size()) {
            throw new IllegalArgumentException("Карты не должны дублироваться");
        }

        Collections.sort(cards);
        evaluateHand();
    }

    public CombinationRank getCombinationRank() {
        return combinationRank;
    }

    public int getCombinationPower() {
        return combinationPower;
    }

    private boolean checkForStraightFlush() {
        if (checkForFlush() && checkForStraight()) {
            combinationRank = CombinationRank.StraightFlush;
            return true;
        }
        return false;
    }

    private boolean checkForFourOfKind() {
        Rank fourOfKindRank = getRankByCount(4);
        Rank kickerRank = getRankByCount(1);

        if (fourOfKindRank != null && kickerRank != null) {
            combinationRank = CombinationRank.FourOfAKind;
            combinationPower = Integer.parseInt(fourOfKindRank.getValue() + String.format("%02d", kickerRank.getValue()));
            return true;
        }
        return false;
    }

    private boolean checkForFullHouse() {
        Rank threeOfKindRank = getRankByCount(3);
        Rank pairRank = getRankByCount(2);

        if (threeOfKindRank != null && pairRank != null) {
            combinationRank = CombinationRank.FullHouse;
            combinationPower = Integer.parseInt(threeOfKindRank.getValue() + String.format("%02d", pairRank.getValue()));
            return true;
        }
        return false;
    }

    private boolean checkForFlush() {
        Suit firstCardSuit = cards.get(0).getSuit();
        for (Card card : cards) {
            if (card.getSuit().getSymbol() != firstCardSuit.getSymbol()) {
                return false;
            }
        }
        combinationRank = CombinationRank.Flush;
        StringBuilder combinationPowerString = new StringBuilder();
        for (int i = cards.size() - 1; i >= 0; i--) {
            combinationPowerString.append(String.format("%02d", cards.get(i).getRank().getValue()));
        }
        combinationPower = Integer.parseInt(combinationPowerString.toString());
        return true;
    }

    private boolean checkForStraight() {
        boolean isSequential = IntStream.range(1, cards.size())
                .allMatch(i -> cards.get(i).getRank().getValue() == cards.get(i - 1).getRank().getValue() + 1);

        boolean isAceLowStraight = !isSequential &&
                cards.get(4).getRank() == Rank.ACE &&
                cards.get(0).getRank() == Rank.TWO &&
                cards.get(1).getRank() == Rank.THREE &&
                cards.get(2).getRank() == Rank.FOUR &&
                cards.get(3).getRank() == Rank.FIVE;

        if (isSequential || isAceLowStraight) {
            combinationRank = CombinationRank.Straight;
            combinationPower = isAceLowStraight
                    ? cards.get(3).getRank().getValue()
                    : cards.get(4).getRank().getValue();
            return true;
        }
        return false;
    }

    private boolean checkForThreeOfKind() {
        Rank threeOfKindRank = getRankByCount(3);

        if (threeOfKindRank != null) {
            combinationRank = CombinationRank.ThreeOfAKind;
            StringBuilder combinationPowerString = new StringBuilder(threeOfKindRank.getValue());
            combinationPowerString.append(String.format("%d", threeOfKindRank.getValue()));

            for (int i = cards.size() - 1; i >= 0; i--) {
                if (cards.get(i).getRank().getValue() != threeOfKindRank.getValue()) {
                    combinationPowerString.append(String.format("%02d", cards.get(i).getRank().getValue()));
                }
            }

            combinationPower = Integer.parseInt(combinationPowerString.toString());
            return true;
        }
        return false;
    }

    private boolean checkForTwoPair() {
        Map<Rank, Integer> rankCounts = new HashMap<>();

        for (Card card : cards) {
            rankCounts.put(card.getRank(), rankCounts.getOrDefault(card.getRank(), 0) + 1);
        }

        List<Rank> pairs = new ArrayList<>();
        Rank singlePair = null;

        for (Map.Entry<Rank, Integer> entry : rankCounts.entrySet()) {
            if (entry.getValue() == 2) {
                pairs.add(entry.getKey());
            } else {
                singlePair = entry.getKey();
            }
        }

        if (pairs.size() == 2 && singlePair != null) {
            Rank higherPair = pairs.get(0);
            Rank lowerPair = pairs.get(1);

            if (higherPair.compareTo(lowerPair) < 0) {
                higherPair = pairs.get(1);
                lowerPair = pairs.get(0);
            }

            String combinationPowerString = higherPair.getValue() +
                    String.format("%02d", lowerPair.getValue()) +
                    String.format("%02d", singlePair.getValue());

            combinationRank = CombinationRank.TwoPair;
            combinationPower = Integer.parseInt(combinationPowerString);
            return true;
        }
        return false;
    }

    private boolean checkForOnePair() {
        Rank pairRank = getRankByCount(2);

        if (pairRank != null) {
            combinationRank = CombinationRank.Pair;
            StringBuilder combinationPowerString = new StringBuilder(pairRank.getValue());

            for (int i = cards.size() - 1; i >= 0; i--) {
                if (cards.get(i).getRank().getValue() != pairRank.getValue()) {
                    combinationPowerString.append(String.format("%02d", cards.get(i).getRank().getValue()));
                }
            }

            combinationPower = Integer.parseInt(combinationPowerString.toString());
            return true;
        }
        return false;
    }

    private boolean checkForHighCards() {
        Map<Rank, Integer> rankCounts = getRankCounts();

        if (rankCounts.size() != 5) {
            return false;
        }

        StringBuilder combinationPowerString = new StringBuilder();

        for (int i = cards.size() - 1; i >= 0; i--) {
            combinationPowerString.append(String.format("%02d", cards.get(i).getRank().getValue()));
        }

        combinationRank = CombinationRank.HighCard;
        combinationPower = Integer.parseInt(combinationPowerString.toString());
        return true;
    }

    private void evaluateHand() {
        if (checkForStraightFlush()) return;
        if (checkForFourOfKind()) return;
        if (checkForFullHouse()) return;
        if (checkForFlush()) return;
        if (checkForStraight()) return;
        if (checkForThreeOfKind()) return;
        if (checkForTwoPair()) return;
        if (checkForOnePair()) return;
        checkForHighCards();
    }

    private Rank getRankByCount(int count) {
        Map<Rank, Integer> rankCounts = getRankCounts();

        return rankCounts.entrySet()
                .stream()
                .filter(entry -> entry.getValue() == count)
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);
    }

    private Map<Rank, Integer> getRankCounts() {
        Map<Rank, Integer> rankCounts = new HashMap<>();

        for (Card card : cards) {
            rankCounts.put(card.getRank(), rankCounts.getOrDefault(card.getRank(), 0) + 1);
        }

        return rankCounts;
    }

    @Override
    public int compareTo(PokerHand other) {
        int rankComparison = Integer.compare(other.combinationRank.getRank(), this.combinationRank.getRank());
        if (rankComparison != 0) {
            return rankComparison;
        }

        return Integer.compare(other.combinationPower, this.combinationPower);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Card card : cards) {
            sb.append(card).append(" ");
        }
        return sb.toString().trim();
    }
}
