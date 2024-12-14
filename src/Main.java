import entities.PokerHand;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<PokerHand> hands = new ArrayList<PokerHand>();
        {
            hands.add(new PokerHand("KS 2H 5C JD TD"));
            hands.add(new PokerHand("2S 2H 2C 2D AD"));
            hands.add(new PokerHand("AH 2H 3H 4H 5H"));
            hands.add(new PokerHand("5H 4H 3H 2H AH"));
        }
        for(PokerHand hand : hands){
            System.out.println(hand);
        }
    }
}