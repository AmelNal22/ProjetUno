package fr.pantheonsorbonne.miage;

import fr.pantheonsorbonne.miage.exception.NoMoreCardException;
import fr.pantheonsorbonne.miage.game.Card;
import fr.pantheonsorbonne.miage.model.Game;
import fr.pantheonsorbonne.miage.model.GameCommand;

import java.util.*;

public class UnoNetworkEngine extends UnoEngine {

    static final String playerId = "Player-" + new Random().nextInt();
    private LinkedList<Player> playerOnTheRound=new LinkedList<>();
    private final Set<String> players;
    private static final int nbrOfplayerOnTheRound = 4;

    private final HostFacade hostFacade;
    private final Game uno;

    public UnoNetworkEngine(HostFacade hostFacade, Set<String> players, fr.pantheonsorbonne.miage.model.Game uno) {
        this.hostFacade = hostFacade;
        this.players = players;
        this.uno = uno;
    }

    public static void main(String[] args) {

        HostFacade hostFacade = Facade.getFacade();
        hostFacade.waitReady();

        hostFacade.createNewPlayer("Host");

        fr.pantheonsorbonne.miage.model.Game uno = hostFacade.createNewGame("UNO");

        hostFacade.waitForExtraPlayerCount(nbrOfplayerOnTheRound);
        
        UnoEngine host = new UnoNetworkEngine(hostFacade, uno.getPlayers(), uno);
        host.play();
    }

    @Override
    protected LinkedList<Player> getPlayerOnTheRound(){
        return this.playerOnTheRound;
    }

    @Override
    protected void giveCardsToPlayer() {
        for (int i=0; i<nbrOfplayerOnTheRound; i++){
            playerOnTheRound.get(i).setHand();
            hostFacade.sendGameCommandToPlayer(uno, playerId, new GameCommand("cardsForYou"));
        }
    }

    @Override
    protected LinkedList<Player> inverseGame(int posi) {
        hostFacade.sendGameCommandToAll(uno, new GameCommand("inverse"));
        return inverseGame(posi);
    }

    @Override
    protected boolean countNbrOfPoint() {
        if (countNbrOfPoint()) {
            hostFacade.sendGameCommandToAll(uno, new GameCommand("inverse"));
        }
    }

    @Override
    protected Card getCardOrGameOver(Collection<Card> leftOverCard, String cardProviderPlayer, String cardProviderPlayerOpponent) {

        try {
            return getCardFromPlayer(cardProviderPlayer);
        } catch (NoMoreCardException nmc) {
            hostFacade.sendGameCommandToPlayer(uno, cardProviderPlayer, new GameCommand("gameOver"));
            players.remove(cardProviderPlayer);
            hostFacade.sendGameCommandToPlayer(uno, cardProviderPlayerOpponent, new GameCommand("cardsForYou", Card.cardsToString(leftOverCard.toArray(new Card[leftOverCard.size()]))));
            return null;
        }

    }

    /**
     * give this stack of card to the winner player
     *
     * @param roundStack a stack of card at stake
     * @param winner     the winner
     */
    @Override
    protected void giveCardsToPlayer(Collection<Card> roundStack, String winner) {
        List<Card> cards = new ArrayList<>();
        cards.addAll(roundStack);
        //shuffle the round deck so we are not stuck
        Collections.shuffle(cards);
        hostFacade.sendGameCommandToPlayer(uno, winner, new GameCommand("cardsForYou", Card.cardsToString(cards.toArray(new Card[cards.size()]))));
    }

    /**
     * we get a card from a player, if possible.
     * <p>
     * If the player has no more card, throw an exception
     *
     * @param player the name of the player
     * @return a card from a player
     * @throws NoMoreCardException if player has no more card.
     */
    @Override
    protected Card getCardFromPlayer(String player) throws NoMoreCardException {
        hostFacade.sendGameCommandToPlayer(uno, player, new GameCommand("playACard"));
        GameCommand expectedCard = hostFacade.receiveGameCommand(uno);
        if (expectedCard.name().equals("card")) {
            return Card.valueOf(expectedCard.body());
        }
        if (expectedCard.name().equals("outOfCard")) {
            throw new NoMoreCardException();
        }
        //should not happen!
        throw new RuntimeException("invalid state");

    }

}
