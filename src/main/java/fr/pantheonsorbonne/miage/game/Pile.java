package fr.pantheonsorbonne.miage.game;

import java.util.*;


public class Pile{
    private static Deque<Card> gameDeck = new LinkedList<>();

    static {

        // cree une nouvelle pioche quand la premiere est fini
        Card cardToTheTop;
        do {
            Card[] carte = Deck.getRandomCards(1);
            cardToTheTop = carte[0];
        } while (Card.specialCard(cardToTheTop));
        gameDeck.add(cardToTheTop);

    }   //vérifier les règles pour poser le +4

    // construct initialise First Card of gamedeck

    public Pile() {
    }

    public static Deque<Card> getGameDeck(){
        return gameDeck;
    }

    // montre la carte de la Pile au-dessus de tout
    public static void showCardTalo() {
        System.out.println("Carte au dessus de la pile : "+Card.valueOfCard(gameDeck.element()));
    }

    // recupere la carte au-dessus de la Pile
    public static Card cardTopDeck() {
        return gameDeck.element();
    }

    // ajout de carte sur la Pile en verifiant si elle est similaire
    public static void addCardToGameDeck(Card card) throws IllegalArgumentException{
        if (!verifCardToAdd(card)){
            throw new IllegalArgumentException("Erreur car ce n'est pas la bonne carte.");
        } else {
            gameDeck.offerFirst(card);
        }        
    }   

    // regarde si la derniere carte poser est la meme
    public static boolean verifCardToAdd(Card card) {
        return Card.isJoker(card) || Card.isTake4(card) || card.getColor().equals(Pile.cardTopDeck().getColor())
                || card.getValue().equals(Pile.cardTopDeck().getValue());
    }

    // regarde si toute les cartes posees en meme temps ont meme valeur

    public static boolean verifCardsToAdd(Card[] card) {
        for (int i = 0; i < card.length - 1; i++) {
            if (!card[i].getValue().equals(card[i + 1].getValue())) {
                return false;
            }
        }

        return true;
    }

    public static void displayAlldeck() {
        for (Card c : gameDeck) {
            System.out.println("leefef deck la Pile CEST ICI" + Card.valueOfCard(c));
        }
    }

    

}