package fr.pantheonsorbonne.miage.game;

import java.util.Random;

import fr.pantheonsorbonne.miage.enums.*;

public class Deck {

    private static final Random random = new Random();
    private static int piocheSize = 108;
    private static Card[] pioche = new Card[piocheSize];

    private Deck() {
    }

    public static int getPiocheSize() {
        return piocheSize;
    }

    public static void setPiocheSize(int newPioche) {
        piocheSize = newPioche;
    }

    public static Card[] getPioche() {
        return pioche;
    }

    public static void setPioche(int posi, Card newCard) {
        pioche[posi] = newCard;
    }

    static {
        int cardCount = piocheSize - 1;
        for (CardColor color : CardColor.values()) {
            for (CardValue value : CardValue.values()) {
                if (value.getValeur().length() > 1) {
                    if (isOneTime(value)) {
                        pioche[cardCount--] = new Card(color, value, 50);
                    } else {
                        pioche[cardCount--] = new Card(color, value, 20);
                    }
                } else {
                    pioche[cardCount--] = new Card(color, value, Integer.parseInt(value.getValeur()));
                }
                if (!isOneTime(value) && !value.getValeur().equals("0")) {
                    if (value.getValeur().length() > 1) {
                        pioche[cardCount--] = new Card(color, value, 20);
                    } else {
                        pioche[cardCount--] = new Card(color, value, Integer.parseInt(value.getValeur()));
                    }
                }
            }
        }
        mixCards();
    }

    private static boolean isOneTime(CardValue value) {
        //you should have used directly the enum here instead of the String
        return value.getValeur().equals("joker") || value.getValeur().equals("+4");
    }

    public static Card[] getRandomCards(int length) {
        Card[] result = new Card[length];
        for (int i = 0; i < length; i++) {
            result[i] = newRandomCard();
        }
        return result;
    }

    private static Card newRandomCard() {
        return pioche[piocheSize-- - 1];
    }

    public static void mixCards() {
        for (int i = 0; i < piocheSize; i++) {
            int randomIndexToSwap = random.nextInt(piocheSize);
            Card temp = pioche[randomIndexToSwap];
            pioche[randomIndexToSwap] = pioche[i];
            pioche[i] = temp;
        }
    }

    // Refaire la pioche avec la Pile
    public static void createNewDeck(int length) {
        if (Pile.getGameDeck().size() <= length) {
            throw new IllegalArgumentException("Erreur donc fin du jeu.");
        }
        piocheSize = Pile.getGameDeck().size() - 1;
        for (int i = 0; i < piocheSize; i++) {
            pioche[i] = Pile.getGameDeck().pollLast();
        }
        Deck.mixCards();
    }

    public static Card[] giveCards(int length) {
        Deck.getPiocheSize();
        if (Deck.getPiocheSize() < length) {
            System.out.println("Pas assez de cartes dans la pioche: Nouvelle pioche.");
            Deck.createNewDeck(length);
        }
        return getRandomCards(length);
    }
}