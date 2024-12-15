package entities.enums;

public enum CombinationRank {
    HighCard(0), Pair(1), TwoPair(2), ThreeOfAKind(3), Straight(4),
    Flush(5), FullHouse(6), FourOfAKind(7), StraightFlush(8);

    private final int rank;

    CombinationRank(int rank) {
        this.rank = rank;
    }

    public int getRank() {
        return rank;
    }
}
