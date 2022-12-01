package fr.pantheonsorbonne.miage;
import fr.pantheonsorbonne.miage.game.*;

import java.util.*;

public class Player {
    private final String name;
    private Deque<Card> hand = new LinkedList<>();
    private int point=0;
    private Strategie playerStrategie = new Strategie();

    Player(String nom){
        this.name=nom;
    }

    public String getName(){
        return name;
    }

    public Deque<Card> getHand(){
        return this.hand;
    }

    public void setPoint(int newPoint){
        this.point=newPoint;
    }

    public int getPoint(){
        return this.point;
    }

    //distribution de 7 carte
    public Deque<Card> setHand(){
        Card[] deck=Deck.getRandomCards(7);
        if (Deck.getPiocheSize()<7){
            Deck.createNewDeck();
        }
        for (int i=0; i<deck.length; i++){
            this.hand.addFirst(deck[i]);
        }
        return this.hand;
    }

    //ajout d'une carte dans la main  (pioche ou +2 ou +4)
    private void addHandCard() {
        Card[] deck = Deck.getRandomCards(1);
        this.hand.addFirst(deck[0]);
        if (Deck.getPiocheSize()<=0){
            Deck.createNewDeck();
        }
    }

    //supprime une carte de la main
    private void removeHandCard(Card card){
        this.hand.remove(card);
    }

    //poser carte sur la Pile
    public void playCard(Card card){
        Pile.addCardToGameDeck(card);
        removeHandCard(card);
        if (Card.isJoker(card) || Card.isTake4(card)){
            Strategie.chooseColor(card, hand);
        }
    }
  
    public boolean startToPlay(Card cardToFind){
        if (playerStrategie.searchCardToPut(cardToFind, hand)){
            return true;
        }
        else{
            return drawCard(cardToFind);
        }
    }

    //piocher une carte
    private boolean drawCard(Card cardToFind){
        addHandCard();
        return playerStrategie.searchCardToPutBetweenValueAndColor(cardToFind, hand);
    }
}

