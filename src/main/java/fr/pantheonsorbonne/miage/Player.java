package fr.pantheonsorbonne.miage;
import fr.pantheonsorbonne.miage.game.*;

import java.util.*;

public class Player {
    private final String name;
    private Deque<Card> hand = new LinkedList<>();
    private int point=0;
    private Strategie playerStrategie = new Strategie();

    //constructeur 
    Player(String nom){
        this.name=nom;
    }

    public String getName(){
        return name;
    }

    public Strategie getPlayerStrategie(){
        return playerStrategie;
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
    public void addHandCard() {
        Card[] deck = Deck.getRandomCards(1);
        this.hand.addFirst(deck[0]);
        if (Deck.getPiocheSize()<=0){
            Deck.createNewDeck();
        }
    }

    //supprime une carte de la main
    public void removeHandCard(Card card){
        this.hand.remove(card);
    }

    //poser carte sur la Pile
    public void playCard(Card card){
        Pile.addCardToGameDeck(card);
        removeHandCard(card);
        if (Card.isJoker(card) || Card.isTake4(card)){
            playerStrategie.chooseColor(card, hand);
        }
    }
  

    public boolean readyToPlay(Card cardToFind){
        if (playerStrategie.readyToPlay(cardToFind, hand)){
            return true;
        }
        else{
            return drawCard(cardToFind);
        }
    }

    public boolean drawCard(Card cardToFind){
        addHandCard();
        return playerStrategie.meth1(cardToFind, hand);
    }


    // cherche carte meme valeur       ===    indSameValueOfDeck(Card[] card,Card cardToFind)
    // cherche carte meme couleur    ===   findSameColorOfDeck(cardsInHand, cardToFind)
    // chercher parmi carte de meme couleur celle qui a la plus grande valeur    === searchMaxValueCard(Card[] cardsInHandOfSameColor)
    // cherche carte special
    // tabl de toutes les cartes de meme couleur searchSameColorsCard(Card[] cardsInHand,CardColor color, int nbrOfSameColorCard)
    //chercher nbr de carte de cette couleur === findRepetitionColor(Card[] cardsInHand,CardColor color)
    //couleur la plus presente dan le jeu de carte === findMaxColor(Card[] card)
    //chooseColor(Card card,Card [] cards)  === change couleur de la carte
    //checher carte de changement de couleur === findCardToChangeColor()
    //cherhcer carte +2 ou +4 (si meme qie le talon) findTheSameCardOfAdding(Card card)
    //cherche toute les cartes special
}

