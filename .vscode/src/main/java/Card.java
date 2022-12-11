package main.java;


import main.java.enums.CardColor;
import main.java.enums.CardValue;
import java.util.Arrays;
import java.util.stream.Collectors;



public class Card {
    

    private final CardColor color;
    private final CardValue value;


    public Card(CardColor color, CardValue value) {
        this.color = color;
        this.value = value;
    }


    public CardColor getColor() {
        return color;
    }


    public CardValue getValue() {
        return value;
    }


   // public static Card valueOf(String str) {

    


    public static String cardsToString(Card[] cards) {
        String carte="";
        for (int i=0; i<cards.length; i++){
            carte+=cards[i].getColor().getCouleur()+" ";
            carte+=cards[i].getValue().getValeur()+"\n";
        }

        return carte;
    }
        
    

  //  public static Card[] stringToCards(String cards) {
        
    


}
