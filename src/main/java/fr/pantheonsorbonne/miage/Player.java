package fr.pantheonsorbonne.miage;

import fr.pantheonsorbonne.miage.game.*;
import fr.pantheonsorbonne.miage.strategie.Strateg;

import java.util.*;

public class Player {
    protected final String name;
    protected Deque<Card> hand = new LinkedList<>();
    protected int point = 0;
    protected Strateg strategie;

    // Constructeur
    Player(String nom, Strateg theStrategie) {
        this.name = nom;
        this.strategie = theStrategie;
    }

    public String getName() {
        return name;
    }

    public Strateg getStrategie() {
        return this.strategie;
    }

    public Deque<Card> getHand() {
        return this.hand;
    }

    public void setPoint(int newPoint) {
        this.point = newPoint;
    }

    public int getPoint() {
        return this.point;
    }

    // Distribution de sept cartes
    protected Deque<Card> setHand() {
        Card[] deck = Deck.giveCards(7);
        for (int i = 0; i < deck.length; i++) {
            this.hand.addFirst(deck[i]);
        }
        return this.hand;
    }

    // Ajout d'une carte dans la main (pioche ou +2 ou +4)
    protected void addHandCard(int length) {
        Card[] deck = Deck.giveCards(length);
        for (Card currCard : deck) {
            this.hand.addFirst(currCard);
        }
    }

    // Supprime une carte de la main
    protected void removeHandCard(Card card) {
        this.hand.remove(card);
    }

    protected Card readyToPlay(Card cardToFind) {
        if (this.strategie.searchTheBestCardToPut(cardToFind, hand) != null) {
            return strategie.searchTheBestCardToPut(cardToFind, hand);
        } else {
            return drawCard(cardToFind);
        }
    }

    protected Card drawCard(Card cardToFind) {
        addHandCard(1);
        return this.strategie.searchTheBestCardToPut(cardToFind, hand);
    }

    public void displayHandCard() {
        for (Card currentCard : hand) {
            System.out.print(Card.valueOfCard(currentCard) + "  \n");
        }
    }
}

