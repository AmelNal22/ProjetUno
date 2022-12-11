package fr.pantheonsorbonne.miage;

import java.util.Deque;
import java.util.Set;

import fr.pantheonsorbonne.miage.game.Card;
import fr.pantheonsorbonne.miage.game.Pile;

public abstract class UnoEngine {
    private String winner;

    protected void setWinner(String winner) {
        this.winner = winner;
    }

    protected void play() {
        setPlayerOnTheRound();
        do {
            getCardsToRemove();
            for (String playerName : getPlayerOnTheRound()) {
                giveCardsToPlayer(playerName);
            }
            playRound();
        } while (!isGameOver());
        declareWinner(getWinner());
    }

    protected boolean playRound() {
        boolean clockWiseDirection = true;
        while (true) {
            String player = nextPlayerToPlay(clockWiseDirection);
            Card cardToPut = getCardFromPlayer(player);
            if (cardToPut != null) {
                playCard(cardToPut, player);
                if (verifIfWinner((player))) {
                    winner = player;
                    return true;
                }
                if (Card.isInverse(cardToPut)) {
                    clockWiseDirection = !clockWiseDirection;
                    nextPlayerToPlay(clockWiseDirection);
                    System.out.println("Le jeu a été inversé !");
                } else if (Card.isInterdit(cardToPut) || Card.isTake2(cardToPut) || Card.isTake4(cardToPut)) {
                    specialCard(nextPlayerToPlay(clockWiseDirection));
                }
            }
        }
    }

    protected String nextPlayerToPlay(boolean clockWiseDirection) {
        String currentPlayer;
        if (clockWiseDirection) {
            currentPlayer = getPlayerOnTheRound().getFirst();
            getPlayerOnTheRound().offerLast(getPlayerOnTheRound().pollFirst());
        } else {
            currentPlayer = getPlayerOnTheRound().getLast();
            getPlayerOnTheRound().offerFirst(getPlayerOnTheRound().pollLast());
        }
        return currentPlayer;
    }

    protected void specialCard(String player) {

        if (Card.isTake4(Pile.getCardTopDeck()) || Card.isTake2(Pile.getCardTopDeck())) {
            searchSpecialCardAdding(Pile.getCardTopDeck(), player);
        }
        interditToPlay(player);
    }

    protected String getWinner() {
        return this.winner;
    }

    protected abstract void setPlayerOnTheRound();

    protected abstract void playCard(Card cardToPplay, String player);

    protected abstract void declareWinner(String name);

    protected abstract boolean verifIfWinner(String player);

    protected abstract boolean isGameOver();

    protected abstract void getCardsToRemove();

    protected abstract Deque<String> getPlayerOnTheRound();

    protected abstract Set<String> getInitialPlayers();

    protected abstract void giveCardsToPlayer(String player);

    protected abstract Card getCardFromPlayer(String player);

    protected abstract void searchSpecialCardAdding(Card card, String player);

    protected abstract void interditToPlay(String player);

}
