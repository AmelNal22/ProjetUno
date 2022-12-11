package fr.pantheonsorbonne.miage;

import fr.pantheonsorbonne.miage.game.*;
import fr.pantheonsorbonne.miage.strategie.Strateg;
import fr.pantheonsorbonne.miage.strategie.StrategieColor;
import fr.pantheonsorbonne.miage.strategie.StrategieValue;

import java.util.*;

public class LocalUno extends UnoEngine {

    private Deque<String> playerOnTheRound = new LinkedList<>();
    private Set<String> players = new HashSet<>();
    private Player[] arrayPlayer;

    public LocalUno(Player[] arrayPlayer) {
        if (arrayPlayer.length <= 10 && arrayPlayer.length > 1) {
            this.arrayPlayer = arrayPlayer;

            for (Player name : arrayPlayer) {
                players.add(name.getName());
            }
        } else {
            throw new IllegalArgumentException("Too many players");
        }

    }

    public Player[] getArrayPlayer() {
        return this.arrayPlayer;
    }

    protected Deque<String> getPlayerOnTheRound() {
        return playerOnTheRound;
    }

    protected Player findPlayerWithName(String player) {
        for (Player currentPlayer : arrayPlayer) {
            if (currentPlayer.getName().equals(player)) {
                return currentPlayer;
            }
        }
        throw new IllegalArgumentException("Erreur car le player " + player + " n'existe pas.");
    }

    @Override
    protected void giveCardsToPlayer(String player) {
        Player thisPlayer = findPlayerWithName(player);
        thisPlayer.setHand();
    }

    @Override
    protected Card getCardFromPlayer(String player) {
        Pile.showCardTopDeck();
        Player thisPlayer = findPlayerWithName(player);
        System.out.println(
                "Au tour du joueur " + thisPlayer.getName() + " avec " + thisPlayer.getHand().size() + " cartes: \n");
        thisPlayer.displayHandCard();
        return thisPlayer.readyToPlay(Pile.getCardTopDeck());
    }

    @Override
    protected void declareWinner(String name) {
        Player thisPlayer = findPlayerWithName(name);
        System.out.println("Le gagnant est " + name + " avec au total " + thisPlayer.getPoint());
    }

    @Override
    protected void setPlayerOnTheRound() {
        for (Player currentPlayer : arrayPlayer) {
            playerOnTheRound.add(currentPlayer.getName());
        }
    }

    @Override
    protected boolean verifIfWinner(String player) {
        Player thisPlayer = findPlayerWithName(player);
        return thisPlayer.getHand().isEmpty();
    }

    @Override
    protected Set<String> getInitialPlayers() {
        return players;
    }

    int countPointPlayer(Deque<Card> cardInTheHand) {
        int point = 0;
        for (Card currCard : cardInTheHand) {
            point = point + currCard.getPoint();
        }
        System.out.println("J'ai donné "+point+" points à mon adversaire.");
        return point;
    }

    @Override
    protected boolean isGameOver() {
        System.out.println("Compte de points");
        int counterOfPoint = 0;
        for (String currPlayer : getPlayerOnTheRound()) {
            Player thisPlayer = findPlayerWithName(currPlayer);
            if (!thisPlayer.getHand().isEmpty()){
                counterOfPoint = counterOfPoint + countPointPlayer(thisPlayer.getHand());
            }
        }
        Player thisPlayer = findPlayerWithName(getWinner());
        int pointBefore = thisPlayer.getPoint();
        System.out.println("Avant cette manche j'avais un total de "+thisPlayer.getPoint()+" points.");
        int newPoint = pointBefore + counterOfPoint;
        thisPlayer.setPoint(newPoint);
        System.out.println("J'ai maintenant au total "+thisPlayer.getPoint()+" points.");
        return thisPlayer.getPoint() >= 250;
    }

    @Override
    protected void searchSpecialCardAdding(Card card, String player) {
        Player thisPlayer = findPlayerWithName(player);
        if (Card.isTake4(card)) {
            thisPlayer.addHandCard(4);
        } else {
            thisPlayer.addHandCard(2);
        }
    }

    @Override
    protected void getCardsToRemove() {
        for (String currPlayer : getPlayerOnTheRound()) {
            Player thisPlayer = findPlayerWithName(currPlayer);
            if (!thisPlayer.getHand().isEmpty()) {
                System.out.println(thisPlayer.getPoint() + " a gagné !");
            }
            while (!thisPlayer.getHand().isEmpty()) {
                Deck.setPioche(Deck.getPiocheSize(), thisPlayer.getHand().poll());
                Deck.setPiocheSize(Deck.getPiocheSize() + 1);
            }
        }
        Deck.mixCards();
    }

    @Override
    protected void playCard(Card cardToPlay, String player) {
        Pile.verifCardToAdd(cardToPlay);
        Pile.addCardToGameDeck(cardToPlay);
        Player thisPlayer = findPlayerWithName(player);
        thisPlayer.getHand().remove(cardToPlay);
        if (Card.isJoker(cardToPlay) || Card.isTake4(cardToPlay)) {
            thisPlayer.getStrategie().chooseColor(cardToPlay, thisPlayer.getHand());
        }
    }

    @Override
    protected void interditToPlay(String player) {
        System.out.println("Le joueur " + player + " ne joue pas !");
    }

    public static void main(String... args) {
        // STRATEGIE COLOR IS MUCH BETTER THAN STATEGIE VALUE
        Strateg color = new StrategieColor();
        Strateg value = new StrategieValue();

        Player[] listPlayer = new Player[2];
        listPlayer[0] = new Player("Meryam", color);
        listPlayer[1] = new Player("Amel", value);

        LocalUno localWarGame = new LocalUno(listPlayer);
        localWarGame.play();
    }
}