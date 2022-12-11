package main.java;


import main.java.enums.CardColor;
import main.java.enums.CardValue;
import java.util.Random;

public class Deck {
    
    private final static Random random = new Random();
    private static int deckSize = (CardValue.values().length * CardColor.values().length)*2;
    private final static Card[] deck = new Card[deckSize];
    

    static {
        int cardCount=deckSize-1;
        for (CardColor color : CardColor.values){
            for (CardValue value : CardValue.values){
                deck[cardCount--] = new Card(color, value);
            }
        }

        for (int i=0; i<deckSize; i++){
            int randomIndexToSwap = random.nextInt(deckSize);
            Card temp = deck[randomIndexToSwap];
            deck[randomIndexToSwap] = deck[i];
            deck[i] = temp;

        }

    }

    private Deck(){

    }


    public static Card[] getRandomCards(int length) {
        Card[] result = new Card[length];
        for (int i = 0; i < length; i++) {
            result[i] = newRandomCard();
        }
        return result;
    }

    private static Card newRandomCard() {
        return deck[deckSize-- - 1];
    }

    




    public static void main(String args[]){
        System.out.println(cardsToString(deck));
    }


}
