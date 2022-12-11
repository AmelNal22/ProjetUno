package fr.pantheonsorbonne.miage.game;

import fr.pantheonsorbonne.miage.enums.*;

public class Card {

    private CardColor color;
    private final CardValue value;
    private final int point;

    public Card(CardColor color, CardValue value, int point) {
        this.color = color;
        this.value = value;
        this.point = point;
    }

    public CardColor getColor() {
        return color;
    }

    public void setColor(CardColor couleur) {
        color = couleur;
    }

    public CardValue getValue() {
        return value;
    }

    public int getPoint() {
        return this.point;
    }

    public static String valueOfCard(Card card) {
        return card.getColor().getCouleur() + " " + card.getValue().getValeur();
    }

    public static String cardsToString(Card[] cards) {
        String carte = "\n";
        for (int i = 0; i < cards.length; i++) {
            carte += Card.valueOfCard(cards[i]) + "\n";
        }
        return carte;
    }

    // SECTION CARTES SPECIALES

    public static boolean isSpecialCard(Card card) {
        return (card.getValue().getValeur().length() != 1);
    }

    // Si c'est sens inverse
    public static boolean isInverse(Card card) {
        return card.getValue().getValeur().equals("inverse");
    }

    // Si c'est sens interdit
    public static boolean isInterdit(Card card) {
        return card.getValue().getValeur().equals("interdit");
    }

    // Si on peut choisir couleur
    public static boolean isJoker(Card card) {
        String value = card.getValue().getValeur();
        return value.equals("joker");
    }

    // Si on peut chosir couleur et +4
    public static boolean isTake4(Card card) {
        String value = card.getValue().getValeur();
        return value.equals("+4");
    }

    // Si +2
    public static boolean isTake2(Card card) {
        String value = card.getValue().getValeur();
        return value.equals("+2");
    }

    public static boolean isChangingColor(CardValue value) {
        return value.getValeur().equals("joker") || value.getValeur().equals("+4");
    }

    public static Card getStringToCard(String card) {
        for (CardColor color : CardColor.values()) {
            if (color.getCouleur().equals(card.substring(0, 1))) {
                CardValue value = CardValue.valueOfStr(card.substring(2, card.length()));
                if (value.getValeur().length() != 1) {
                    if (isChangingColor(value)) {

                        return new Card(color, value, 50);
                    } else {

                        return new Card(color, value, 20);
                    }
                } else {
                    return new Card(color, value, Integer.parseInt(value.getValeur()));

                }
            }
        }
        return null;
    }

    public static Card[] getStringToCards(String card) {
        String[] cardToString = card.split("\n");
        Card[] newCards = new Card[cardToString.length - 1];
        int count = 0;
        for (String x : cardToString) {
            if (x.length() != 0) {
                newCards[count++] = getStringToCard(x);
            }
        }
        return newCards;
    }
}
