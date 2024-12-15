import entities.PokerHand;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ArrayList<PokerHand> hands = new ArrayList<>();
        hands.add(new PokerHand("5H 6H 7H 8H 9H")); // Стрит флеш
        hands.add(new PokerHand("2H 2C 2S 2D 5C")); // Каре
        hands.add(new PokerHand("2H 2C 2S 3D 3C")); // Фулл хаус
        hands.add(new PokerHand("2H 3H 5H 7H 9H")); // Флеш
        hands.add(new PokerHand("2H 3C 4D 5S 6H")); // Стрит
        hands.add(new PokerHand("2H 2C 2S JD TD")); // Сет
        hands.add(new PokerHand("2H 2C 5C 5D TD")); // Две пары
        hands.add(new PokerHand("2H 2C 5C JD TD")); // Пара
        hands.add(new PokerHand("KS 2H 5C JD TD")); // Рука с высоким карманом

        Collections.sort(hands);

        System.out.println("Sorted Hands:");
        for (PokerHand hand : hands) {
            System.out.println(hand + "   Rank: " + hand.getCombinationRank() + "   Power: " + hand.getCombinationPower());
        }
    }
}