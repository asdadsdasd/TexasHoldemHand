package test.java;

import main.java.entities.PokerHand;
import main.java.entities.enums.CombinationRank;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PokerHandTest {
    @Test
    void testHighCard() {
        PokerHand hand = new PokerHand("2H 5D 9S JC KD");
        assertEquals(CombinationRank.HighCard, hand.getCombinationRank(), "High Card");
    }

    @Test
    void testOnePair() {
        PokerHand hand = new PokerHand("2H 2D 5S 9C KD");
        assertEquals(CombinationRank.Pair, hand.getCombinationRank(), "One Pair");
    }

    @Test
    void testTwoPair() {
        PokerHand hand = new PokerHand("2H 2D 5S 5C KD");
        assertEquals(CombinationRank.TwoPair, hand.getCombinationRank(), "Two Pair");
    }

    @Test
    void testThreeOfAKind() {
        PokerHand hand = new PokerHand("2H 2D 2S 9C KD");
        assertEquals(CombinationRank.ThreeOfAKind, hand.getCombinationRank(), "Three of a Kind");
    }

    @Test
    void testStraight() {
        PokerHand hand = new PokerHand("2H 3D 4S 5C 6H");
        assertEquals(CombinationRank.Straight, hand.getCombinationRank(), "Straight");
    }

    @Test
    void testAceLowStraight() {
        PokerHand hand = new PokerHand("AH 2D 3S 4C 5H");
        assertEquals(CombinationRank.Straight, hand.getCombinationRank(), "Ace-low Straight");
    }

    @Test
    void testFlush() {
        PokerHand hand = new PokerHand("2H 5H 8H JH KH");
        assertEquals(CombinationRank.Flush, hand.getCombinationRank(), "Flush");
    }

    @Test
    void testFullHouse() {
        PokerHand hand = new PokerHand("2H 2D 5S 5C 5H");
        assertEquals(CombinationRank.FullHouse, hand.getCombinationRank(), "Full House");
    }

    @Test
    void testFourOfAKind() {
        PokerHand hand = new PokerHand("2H 2D 2S 2C 5H");
        assertEquals(CombinationRank.FourOfAKind, hand.getCombinationRank(), "Four of a Kind");
    }

    @Test
    void testStraightFlush() {
        PokerHand hand = new PokerHand("2H 3H 4H 5H 6H");
        assertEquals(CombinationRank.StraightFlush, hand.getCombinationRank(), "Straight Flush");
    }

    @Test
    void testInvalidHand() {
        assertThrows(IllegalArgumentException.class, () -> new PokerHand("2H 3D"), "Некорректное количество карт");
    }

    @Test
    void testDuplicateCards() {
        assertThrows(IllegalArgumentException.class, () -> new PokerHand("2H 2H 3S 4D 5C"), "Дубликацияя карт");
    }

    @Test
    void testSortingPokerHands() {
        PokerHand highCard = new PokerHand("2H 5D 9S JC KD");
        PokerHand onePair = new PokerHand("2H 2D 5S 9C KD");
        PokerHand twoPair = new PokerHand("2H 2D 5S 5C KD");
        PokerHand threeOfAKind = new PokerHand("2H 2D 2S 9C KD");
        PokerHand straight = new PokerHand("2H 3D 4S 5C 6H");
        PokerHand flush = new PokerHand("2H 5H 8H JH KH");
        PokerHand fullHouse = new PokerHand("2H 2D 5S 5C 5H");
        PokerHand fourOfAKind = new PokerHand("2H 2D 2S 2C 5H");
        PokerHand straightFlush = new PokerHand("2H 3H 4H 5H 6H");

        List<PokerHand> hands = new ArrayList<>();
        hands.add(threeOfAKind);
        hands.add(straight);
        hands.add(highCard);
        hands.add(flush);
        hands.add(fourOfAKind);
        hands.add(fullHouse);
        hands.add(straightFlush);
        hands.add(twoPair);
        hands.add(onePair);

        List<PokerHand> expectedOrder = List.of(
                straightFlush,
                fourOfAKind,
                fullHouse,
                flush,
                straight,
                threeOfAKind,
                twoPair,
                onePair,
                highCard
        );

        Collections.sort(hands);

        assertIterableEquals(expectedOrder, hands, "Сортировка. Общий случай");
    }

    @Test
    void testHighCardHands() {
        PokerHand highCard1 = new PokerHand("2H 4D 6S 8C 9H");
        PokerHand highCard2 = new PokerHand("2C 5H 7D 9S QH");
        PokerHand highCard3 = new PokerHand("TH 5D 7S 8C QH");
        PokerHand highCard4 = new PokerHand("4C 6H 8D TS AH");

        PokerHand pairHand = new PokerHand("2H 2D 5S 9C KH");

        List<PokerHand> hands = new ArrayList<>();
        hands.add(pairHand);
        hands.add(highCard1);
        hands.add(highCard2);
        hands.add(highCard3);
        hands.add(highCard4);

        Collections.sort(hands);

        //Тест сортировки старшей карты
        assertEquals(pairHand, hands.get(0), "One Pair");
        assertEquals(highCard4, hands.get(1), "High Card с A");
        assertEquals(highCard3, hands.get(2), "High Card с K");
        assertEquals(highCard2, hands.get(3), "High Card с Q");
        assertEquals(highCard1, hands.get(4), "High Card с 9");
    }

    @Test
    void testOnePairHands() {
        PokerHand pair1 = new PokerHand("2H 2D 4S 5C 9H");  // Пара 2, кикер 9
        PokerHand pair2 = new PokerHand("2C 2S 6H 7D QH");  // Пара 2, кикер Q
        PokerHand pair3 = new PokerHand("4H 4D 7S 8C KH");  // Пара 4, кикер K
        PokerHand pair4 = new PokerHand("5C 5H 9D TS AH");  // Пара 5, кикер A

        PokerHand highCardHand = new PokerHand("2H 4D 6S 8C 9H"); // High Card
        PokerHand twoPairHand = new PokerHand("2H 2D 5S 5C 8H"); // Two Pair

        List<PokerHand> hands = new ArrayList<>();
        hands.add(highCardHand);
        hands.add(pair1);
        hands.add(pair2);
        hands.add(pair3);
        hands.add(pair4);
        hands.add(twoPairHand);

        Collections.sort(hands);

        // Тест сортировки пар
        assertEquals(twoPairHand, hands.get(0), "Two Pair");
        assertEquals(pair4, hands.get(1), "One Pair с 5 кикер A");
        assertEquals(pair3, hands.get(2), "One Pair с 4 кикер K ");
        assertEquals(pair2, hands.get(3), "One Pair с 2 кикер Q");
        assertEquals(pair1, hands.get(4), "One Pair с 2 кикер 9");
        assertEquals(highCardHand, hands.get(5), "High Card");
    }

    @Test
    void testTwoPairHands() {
        PokerHand twoPair1 = new PokerHand("2H 2D 5S 5C 9H"); // Пары 2 и 5
        PokerHand twoPair2 = new PokerHand("2H 2D 9S 5C 9H"); // Пары 2 и 9
        PokerHand twoPair3 = new PokerHand("8H 8D 9S 9C KH"); // Пары 8 и 9
        PokerHand twoPair4 = new PokerHand("AH 2D AS 7C 2H"); // Пары 2 и A

        PokerHand pairHand = new PokerHand("2H 2D 5S 9C KH");         // One Pair
        PokerHand threeOfAKindHand = new PokerHand("2H 2D 2S 5C 9H"); // Three of a Kind

        List<PokerHand> hands = new ArrayList<>();
        hands.add(pairHand);
        hands.add(twoPair1);
        hands.add(twoPair2);
        hands.add(twoPair3);
        hands.add(twoPair4);
        hands.add(threeOfAKindHand);

        Collections.sort(hands);

        // Тест сортировки двух пар
        assertEquals(threeOfAKindHand, hands.get(0), "Three of a Kind");
        assertEquals(twoPair4, hands.get(1), "Two Pair с A и 2");
        assertEquals(twoPair3, hands.get(2), "Two Pair с 8 и 9");
        assertEquals(twoPair2, hands.get(3), "Two Pair с 9 и 2");
        assertEquals(twoPair1, hands.get(4), "Two Pair с 5 и 2");
        assertEquals(pairHand, hands.get(5), "One Pair");
    }

    @Test
    void testThreeOfAKindHands() {
        PokerHand threeOfAKind1 = new PokerHand("2H 2D 2S 5C 9H"); // Сеты 2
        PokerHand threeOfAKind2 = new PokerHand("KH KD KS 7C 9H"); // Сеты K кикер 9
        PokerHand threeOfAKind3 = new PokerHand("KH KD KS 5C AH"); // Сеты K кикер A
        PokerHand threeOfAKind4 = new PokerHand("AH AD AS 7C 2H"); // Сеты A

        PokerHand twoPairHand = new PokerHand("8H 8D 9S 9C KH");      // Two Pair
        PokerHand straightHand = new PokerHand("2H 3D 4S 5C 6H");     // Straight

        List<PokerHand> hands = new ArrayList<>();
        hands.add(twoPairHand);
        hands.add(threeOfAKind1);
        hands.add(threeOfAKind2);
        hands.add(threeOfAKind3);
        hands.add(threeOfAKind4);
        hands.add(straightHand);

        Collections.sort(hands);

        // Тест сортировки сета
        assertEquals(straightHand, hands.get(0), "Straight");
        assertEquals(threeOfAKind4, hands.get(1), "Three of a Kind с A");
        assertEquals(threeOfAKind3, hands.get(2), "Three of a Kind с K кикер A");
        assertEquals(threeOfAKind2, hands.get(3), "Three of a Kind с K кикер 9");
        assertEquals(threeOfAKind1, hands.get(4), "Three of a Kind с 2");
        assertEquals(twoPairHand, hands.get(5), "Two Pair");
    }

    @Test
    void testStraightHands() {
        PokerHand straight1 = new PokerHand("2H 3D 4S 5C AH");  // Низкий стрит
        PokerHand straight2 = new PokerHand("4H 5D 6S 7C 8H");  // Средний стрит
        PokerHand straight3 = new PokerHand("9H TD JS QC KH");   // Высокий стрит
        PokerHand straight4 = new PokerHand("TH JH QD KS AH");   // Стрит до туза

        PokerHand threeOfAKindHand = new PokerHand("8H 8D 8S 5C KH");  // Three of a Kind
        PokerHand flushHand = new PokerHand("2H 5H 8H JH KH");         // Flush

        List<PokerHand> hands = new ArrayList<>();
        hands.add(threeOfAKindHand);
        hands.add(straight1);
        hands.add(straight2);
        hands.add(straight3);
        hands.add(straight4);
        hands.add(flushHand);

        Collections.sort(hands);

        // Тест сортировки стритов
        assertEquals(flushHand, hands.get(0), "Flush");
        assertEquals(straight4, hands.get(1), "Straight до A");
        assertEquals(straight3, hands.get(2), "Straight до K");
        assertEquals(straight2, hands.get(3), "Straight до 8");
        assertEquals(straight1, hands.get(4), "Straight до 6");
        assertEquals(threeOfAKindHand, hands.get(5), "Three of a Kind");
    }

    @Test
    void testFlushHands() {
        PokerHand flush1 = new PokerHand("5C 8C JC QC KC");  // Flush до K кикер Q
        PokerHand flush2 = new PokerHand("3D 6D 9D QD AD");  // Flush до A
        PokerHand flush3 = new PokerHand("2S 4S 7S 9S JS");  // Flush до J
        PokerHand flush4 = new PokerHand("2H 5H 8H JH KH");  // Flush до K кикр J

        PokerHand straightHand = new PokerHand("2H 3D 4S 5C 6H");      // Straight
        PokerHand fullHouseHand = new PokerHand("8H 8D 8S 5C 5H");     // Full House

        List<PokerHand> hands = new ArrayList<>();
        hands.add(straightHand);
        hands.add(flush1);
        hands.add(flush2);
        hands.add(flush3);
        hands.add(flush4);
        hands.add(fullHouseHand);

        Collections.sort(hands);

        // Тест сортировки флеша
        assertEquals(fullHouseHand, hands.get(0), "Full House");
        assertEquals(flush2, hands.get(1), "Flush до A");
        assertEquals(flush1, hands.get(2), "Flush до K кикер Q");
        assertEquals(flush4, hands.get(3), "Flush до K кикер J");
        assertEquals(flush3, hands.get(4), "Flush до J");
        assertEquals(straightHand, hands.get(5), "Straight");
    }

    @Test
    void testFullHouseHands() {
        PokerHand fullHouse1 = new PokerHand("3H 3D 3S 5C 5H");  // Тройка 3, пара 5
        PokerHand fullHouse2 = new PokerHand("5H 5D 5S 3C 3H");  // Тройка 5, пара 3
        PokerHand fullHouse3 = new PokerHand("KH KD KS 5C 5H");  // Тройка K, пара 5
        PokerHand fullHouse4 = new PokerHand("KH KD KS TH TC");  // Тройка K, пара 10

        PokerHand flushHand = new PokerHand("2H 5H 8H JH KH");        // Flush
        PokerHand fourOfAKindHand = new PokerHand("9H 9D 9S 9C 2H");  // Four of a Kind

        List<PokerHand> hands = new ArrayList<>();
        hands.add(flushHand);
        hands.add(fullHouse1);
        hands.add(fullHouse2);
        hands.add(fullHouse3);
        hands.add(fullHouse4);
        hands.add(fourOfAKindHand);

        Collections.sort(hands);

        // Тест сортировки фулл хауса
        assertEquals(fourOfAKindHand, hands.get(0), "Four of a Kind");
        assertEquals(fullHouse4, hands.get(1), "Full House (K с 10)");
        assertEquals(fullHouse3, hands.get(2), "Full House (K с 5)");
        assertEquals(fullHouse2, hands.get(3), "Full House (5 с 3)");
        assertEquals(fullHouse1, hands.get(4), "Full House (3 с 5)");
        assertEquals(flushHand, hands.get(5), "Flush");
    }

    @Test
    void testFourOfAKindHands() {
        PokerHand fourOfAKind1 = new PokerHand("2H 2D 2S 2C 5H");  // Четыре 2, кикер 5
        PokerHand fourOfAKind2 = new PokerHand("3H 3D 3S 3C KH");  // Четыре 3, кикер K
        PokerHand fourOfAKind3 = new PokerHand("7H 7D 7S 7C 9H");  // Четыре 7, кикер 9
        PokerHand fourOfAKind4 = new PokerHand("8H 8D 8S 8C AH");  // Четыре 8, кикер A

        PokerHand fullHouseHand = new PokerHand("9H 9D 9S 5C 5H");     // Full House
        PokerHand straightFlushHand = new PokerHand("2H 3H 4H 5H 6H"); // Straight Flush

        List<PokerHand> hands = new ArrayList<>();
        hands.add(fullHouseHand);
        hands.add(fourOfAKind1);
        hands.add(fourOfAKind2);
        hands.add(fourOfAKind3);
        hands.add(fourOfAKind4);
        hands.add(straightFlushHand);

        Collections.sort(hands);

        // Тест сортировки Four of a Kind
        assertEquals(straightFlushHand, hands.get(0), "Straight Flush");
        assertEquals(fourOfAKind4, hands.get(1), "Four of a Kind (8, кикер A)");
        assertEquals(fourOfAKind3, hands.get(2), "Four of a Kind (7, кикер 9)");
        assertEquals(fourOfAKind2, hands.get(3), "Four of a Kind (3, кикер K)");
        assertEquals(fourOfAKind1, hands.get(4), "Four of a Kind (2, кикер 5)");
        assertEquals(fullHouseHand, hands.get(5), "Full House");
    }

    @Test
    void testStraightFlushHands() {
        PokerHand straightFlush1 = new PokerHand("2H 3H 4H 5H AH");  // Straight Flush до 6
        PokerHand straightFlush2 = new PokerHand("3D 4D 5D 6D 7D");  // Straight Flush до 7
        PokerHand straightFlush3 = new PokerHand("TH JH QH 8H 9H");  // Straight Flush до Q
        PokerHand straightFlush4 = new PokerHand("9S TS JS QS KS");  // Straight Flush до K

        PokerHand fourOfAKindHand = new PokerHand("8H 8D 8S 8C AH");  // Four of a Kind
        PokerHand royalFlushHand = new PokerHand("TH JH QH KH AH");   // Royal Flush

        List<PokerHand> hands = new ArrayList<>();
        hands.add(fourOfAKindHand);
        hands.add(straightFlush1);
        hands.add(straightFlush2);
        hands.add(straightFlush3);
        hands.add(straightFlush4);
        hands.add(royalFlushHand);

        Collections.sort(hands);

        // Тест сортировки стрит флеша
        assertEquals(royalFlushHand, hands.get(0), "Royal Flush");
        assertEquals(straightFlush4, hands.get(1), "Straight Flush до K");
        assertEquals(straightFlush3, hands.get(2), "Straight Flush до Q");
        assertEquals(straightFlush2, hands.get(3), "Straight Flush до 7");
        assertEquals(straightFlush1, hands.get(4), "Straight Flush до 6");
        assertEquals(fourOfAKindHand, hands.get(5), "Four of a Kind");
    }

}