package fr.pantheonsorbonne.miage.game;

import java.util.*;

public class Pile {
    private static Deque<Card> gameDeck = new LinkedList<>();

    private Pile() {
    }

    static {

        // Création d'une nouvelle pioche lorsque la premiere est fini
        Card cardToTheTop;
        do {
            Card[] carte = Deck.getRandomCards(1);
            cardToTheTop = carte[0];
        } while (Card.isSpecialCard(cardToTheTop));
        gameDeck.add(cardToTheTop);
    }

    public static Deque<Card> getGameDeck() {
        return gameDeck;
    }

    public static void setCardToTheTop(Card cardTopDeck) {
        gameDeck.offerFirst(cardTopDeck);
    }

    public static void showCardTopDeck() {
        System.out.println("Carte au dessus de la pile: " + Card.valueOfCard(gameDeck.element()));
    }

    public static String getCardTopDeckToString() {
        return Card.valueOfCard(gameDeck.element());
    }

    // Récupère la carte au-dessus de la Pile
    public static Card getCardTopDeck() {
        return gameDeck.element();
    }

    // Ajout de carte sur la Pile en vérifiant si elle est similaire
    public static void addCardToGameDeck(Card card) throws IllegalArgumentException {
        if (!verifCardToAdd(card)) {
            throw new IllegalArgumentException("Erreur car ce n'est pas la bonne carte.");
        } else {
            gameDeck.offerFirst(card);
        }
    }

    public static boolean verifCardToAdd(Card card) {
        return Card.isJoker(card) || Card.isTake4(card) || card.getColor().equals(Pile.getCardTopDeck().getColor())
                || card.getValue().equals(Pile.getCardTopDeck().getValue());
    }
}