package fr.pantheonsorbonne.miage;

import fr.pantheonsorbonne.miage.game.Card;
import fr.pantheonsorbonne.miage.game.Deck;
import fr.pantheonsorbonne.miage.game.Pile;
import fr.pantheonsorbonne.miage.model.Game;
import fr.pantheonsorbonne.miage.model.GameCommand;

import java.util.*;

/**
 * This class implements the uno game with the network engine
 */

public class UnoNetworkEngine extends UnoEngine {
    private static final int PLAYER_COUNT = 4;
    private Deque<String> playerOnTheRound = new LinkedList<>();
    private final HostFacade hostFacade;
    private final Set<String> players;
    private final Game uno;

    public UnoNetworkEngine(HostFacade hostFacade, Set<String> players, fr.pantheonsorbonne.miage.model.Game uno) {
        this.hostFacade = hostFacade;
        this.players = players;
        this.uno = uno;
    }

    public static void main(String[] args) {
        // create the host facade
        HostFacade hostFacade = Facade.getFacade();
        hostFacade.waitReady();

        // set the name of the player
        hostFacade.createNewPlayer("Host");

        // create a new game of uno
        fr.pantheonsorbonne.miage.model.Game uno = hostFacade.createNewGame("uno");

        // wait for enough players to join
        hostFacade.waitForExtraPlayerCount(PLAYER_COUNT);
        UnoEngine host = new UnoNetworkEngine(hostFacade, uno.getPlayers(), uno);
        host.play();
    }

    @Override
    protected Set<String> getInitialPlayers() {
        return players;
    }

    @Override
    protected Deque<String> getPlayerOnTheRound() {
        return playerOnTheRound;
    }

    protected void setPlayerOnTheRound() {
        for (String currentPlayer : getInitialPlayers()) {
            playerOnTheRound.add(currentPlayer);
            System.out.println(currentPlayer);
        }
    }

    @Override
    protected void getCardsToRemove() {
        for (String currPlayer : getPlayerOnTheRound()) {
            hostFacade.sendGameCommandToPlayer(uno, currPlayer, new GameCommand("getCardsToRemove"));
            GameCommand cardToRemove = hostFacade.receiveGameCommand(uno);
            if (cardToRemove.name().equals("cardsToRemove")) {
                Card[] cardsRemove = Card.getStringToCards(cardToRemove.body());
                for (Card currCard : cardsRemove) {
                    Deck.setPioche(Deck.getPiocheSize(), currCard);
                    Deck.setPiocheSize(Deck.getPiocheSize() + 1);
                }
            }
        }
    }

    @Override
    protected void giveCardsToPlayer(String player) {
        Card[] deck = Deck.giveCards(7);
        hostFacade.sendGameCommandToPlayer(uno, player, new GameCommand("cardsForYou", Card.cardsToString(deck)));
    }

    @Override
    protected Card getCardFromPlayer(String player) {
        System.out.println("Au tour du player: " + player);
        Pile.showCardTopDeck();
        hostFacade.sendGameCommandToPlayer(uno, player, new GameCommand("playACard", Pile.getCardTopDeckToString()));
        GameCommand expectedCard = hostFacade.receiveGameCommand(uno);
        if (expectedCard.name().equals("drawCard")) {
            Card[] cardToGive = Deck.giveCards(1);
            hostFacade.sendGameCommandToPlayer(uno, player,
                    new GameCommand("yourCard", Card.valueOfCard(cardToGive[0])));
            expectedCard = hostFacade.receiveGameCommand(uno);
        }
        if (expectedCard.name().equals("card")) {
            System.out.println("Carte reçu: " + expectedCard.body());
            return Card.getStringToCard(expectedCard.body());
        }
        System.out.println("Le joueur " + player + " n'a pas posé de cartes.");
        return null;
    }

    @Override
    protected boolean isWinner(String player) {
        hostFacade.sendGameCommandToPlayer(uno, player, new GameCommand("verifWinner"));
        GameCommand winnerOrNot = hostFacade.receiveGameCommand(uno);
        return winnerOrNot.name().equals("winner");
    }

    @Override
    protected void searchSpecialCardAdding(Card card, String player) {
        if (Card.isTake4(card)) {
            Card[] cardToAdd = Deck.giveCards(4);
            hostFacade.sendGameCommandToPlayer(uno, player,
                    new GameCommand("piochePlus4", Card.cardsToString(cardToAdd)));
            System.out.println("Pioche +4 !");

        } else {
            Card[] cardToAdd = Deck.giveCards(2);
            hostFacade.sendGameCommandToPlayer(uno, player,
                    new GameCommand("piochePlus2", Card.cardsToString(cardToAdd)));
            System.out.println("Pioche +2 !");
        }
    }

    @Override
    protected boolean isGameOver() {
        System.out.println("Compteur de points: ");
        int counterOfPoint = 0;
        for (String currPlayer : getPlayerOnTheRound()) {
            hostFacade.sendGameCommandToPlayer(uno, currPlayer, new GameCommand("countNbrOfPoint"));
            GameCommand point = hostFacade.receiveGameCommand(uno);
            counterOfPoint = counterOfPoint + Integer.parseInt(point.body());
        }
        String pl = getWinner();
        hostFacade.sendGameCommandToPlayer(uno, pl, new GameCommand("yourPoints", "" + counterOfPoint));
        GameCommand finalPoint = hostFacade.receiveGameCommand(uno);
        return Integer.parseInt(finalPoint.body()) >= 100;
    }

    @Override
    protected void declareWinner(String winner) {
        hostFacade.sendGameCommandToAll(uno, new GameCommand("gameOver"));
        System.out.println("Le gagnant de la partie est " + winner + " !");
        System.exit(0);
    }

    @Override
    protected void playCard(Card cardToPlay, String player) {
        Pile.verifCardToAdd(cardToPlay);
        Pile.addCardToGameDeck(cardToPlay);
    }

    @Override
    protected void interditToPlay(String player) {
        System.out.println("Le joueur "+player+" ne joue pas!");
        hostFacade.sendGameCommandToPlayer(uno, player, new GameCommand("interdit"));
    }
}



