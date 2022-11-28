package fr.pantheonsorbonne.miage.game;
import java.util.Arrays;

import fr.pantheonsorbonne.miage.enums.*;

public class Card {
    

    private CardColor color;
    private final CardValue value;
    private final int point;


    public Card(final CardColor color,final CardValue value, int point) {
        this.color = color;
        this.value = value;
        this.point = point;
    }


    public CardColor getColor() {
        return color;
    }

    public void setColor(CardColor couleur){
        color=couleur;
    }


    public CardValue getValue() {
        return value;
    }

    public int getPoint(){
        return this.point;
    }

    
    public static String valueOfCard(Card card){
        return card.getColor().getCouleur()+" "+card.getValue().getValeur()+"\n";
    }
        
    public static String cardsToString(Card[] cards) {
        String carte="";
        for (int i=0; i<cards.length; i++){
            carte+=Card.valueOfCard(cards[i]);
        }

        return carte;
    }

    public static Card valueOf(String str){
        int point = 0;
        return new Card(CardColor.valueOfStr(str), CardValue.valueOfStr(str), point);
    }
    
    public static Card[] stringToCards(String cards) {
        if (cards.isEmpty()) {
            return new Card[0];
        }
        return (Card[]) Arrays.stream(cards.split("\n")).map(Card::valueOf).toArray(Card[]::new);
    }
    
    // SECTION CARTES SPECIALES

    public static boolean specialCard(Card card) {
        return (card.getValue().getValeur().length() != 1);
    }

    // si c'est sens inverse
    public static boolean isInverse(Card card) {
        return card.getValue().getValeur().equals("inverse");
    }

    // si c'est sens interdit
    public static boolean isInterdit(Card card) {
        return card.getValue().getValeur().equals("interdit");
    }

    // si on peut choisir couleur
    public static boolean isJoker(Card card) {
        String value = card.getValue().getValeur();
        return value.equals("joker");
    }

    // si on peut chosir couleur et +4
    public static boolean isTake4(Card card) {
        String value = card.getValue().getValeur();
        return value.equals("+4");
    }

    // si +2
    public static boolean isTake2(Card card) {
        String value = card.getValue().getValeur();
        return value.equals("+2");
    }
}
