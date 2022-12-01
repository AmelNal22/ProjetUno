package fr.pantheonsorbonne.miage;
import fr.pantheonsorbonne.miage.game.Card;
import fr.pantheonsorbonne.miage.model.Game;
import fr.pantheonsorbonne.miage.model.GameCommand;

import java.util.*;
 
public class UnoNetworkEngine extends UnoEngine {
    private static final int PLAYER_COUNT = 3;
    private LinkedList<String> playerOnTheRound=new LinkedList<>();
    private final HostFacade hostFacade;
    private final Set<String> players;
    private final Game uno;

    public UnoNetworkEngine(HostFacade hostFacade, Set<String> players, fr.pantheonsorbonne.miage.model.Game uno) {
        this.hostFacade = hostFacade;
        this.players = players;
        this.uno = uno;
    }

    public static void main(String[] args) {
        //create the host facade
        HostFacade hostFacade = Facade.getFacade();
        hostFacade.waitReady();

        //set the name of the player
        hostFacade.createNewPlayer("Host");

        //create a new game of uno
        fr.pantheonsorbonne.miage.model.Game uno = hostFacade.createNewGame("uno");

        //wait for enough players to join
        hostFacade.waitForExtraPlayerCount(PLAYER_COUNT);
        System.out.println("Ca commence");
        UnoEngine host = new UnoNetworkEngine(hostFacade, uno.getPlayers(), uno);
        host.play();


    }

    @Override
    protected Set<String> getInitialPlayers(){
        return this.uno.getPlayers();
    }

    protected void setPlayerOnTheRound() {
        for (String currentPlayer : players) {
            playerOnTheRound.add(currentPlayer);
            System.out.println(currentPlayer);
        }
    }

    @Override
    protected void giveCardsToPlayer(String player) {
        hostFacade.sendGameCommandToPlayer(uno, player, new GameCommand("cardsForYou"));
        
    }

    @Override
    protected boolean getCardFromPlayer(String player) {
        hostFacade.sendGameCommandToPlayer(uno, player, new GameCommand("playACard"));
        GameCommand expectedCard = hostFacade.receiveGameCommand(uno);
        if (expectedCard.name().equals("card")) {
            return true;
        }
        else if  (expectedCard.name().equals("verifWiner")) {
           return verifIfWinner(player);
            
        }
        return false;
    }

    @Override
    protected LinkedList<String> gameIsReversed(int posi) {
        hostFacade.sendGameCommandToAll(uno, new GameCommand("inverse"));
        return gameIsReversed(posi);
    }

    @Override
    protected void declareWinner(String winner) {
        hostFacade.sendGameCommandToPlayer(uno, winner, new GameCommand("gameOver", "win"));
    }

    @Override
    protected void interditToPlay(int i) {
        hostFacade.sendGameCommandToPlayer(uno, getPlayerOnTheRound().get(i), new GameCommand("interdit"));
    }

    @Override
    protected boolean playRound(){
        getCardsToRemove();
        for (String playerName : getPlayerOnTheRound()) {
            giveCardsToPlayer(playerName);
        }
        return super.playRound(); 
    }

    @Override
    public boolean verifIfWinner(String player){
       hostFacade.sendGameCommandToPlayer(uno, player, new GameCommand("verifWinner"));
       GameCommand winnerOrNot = hostFacade.receiveGameCommand(uno);
       return winnerOrNot.name().equals("winner");
    }

    @Override
    protected boolean countPointAllPlayers() {
        int counterOfPoint = 0;
         for (String currPlayer : getPlayerOnTheRound()) {
            hostFacade.sendGameCommandToPlayer(uno, currPlayer, new GameCommand("countPointAllPlayers"));
            GameCommand point = hostFacade.receiveGameCommand(uno);
            counterOfPoint=counterOfPoint+Integer.parseInt(point.body());
         }
         String pl = getWinner();
        hostFacade.sendGameCommandToPlayer(uno, pl, new GameCommand("yourPoints",""+counterOfPoint));
        GameCommand finalPoint = hostFacade.receiveGameCommand(uno);
             
        return Integer.parseInt(finalPoint.body()) >= 100;
    }

    @Override
    protected LinkedList<String> getPlayerOnTheRound() {
        return playerOnTheRound;
    }
    
    @Override
    protected int searchSpecialCardAdding(Card card, LinkedList<String> playerOnTheRound, int currPosition) {
        int nextPlayer = currPosition + 1;
        if (currPosition >= playerOnTheRound.size() - 1) {
            nextPlayer = 0;
        }
            if (Card.isTake4(card)) {
                hostFacade.sendGameCommandToPlayer(uno, playerOnTheRound.get(nextPlayer), new GameCommand("piochePlus4"));
                return nextPlayer;
            } else {
                hostFacade.sendGameCommandToPlayer(uno, playerOnTheRound.get(nextPlayer) , new GameCommand("piochePlus2"));
                return nextPlayer;
            }
    }

    @Override
    protected void getCardsToRemove() {
        for (String currPlayer : getPlayerOnTheRound()) {
            hostFacade.sendGameCommandToPlayer(uno, currPlayer, new GameCommand("getCardsToRemove"));
        }
    }

    @Override
    protected void setNewPlayerOnTheRound(LinkedList<String> playerOnTheRound) {
        this.playerOnTheRound = playerOnTheRound;   
    }
}