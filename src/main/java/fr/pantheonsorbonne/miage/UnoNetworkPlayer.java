package fr.pantheonsorbonne.miage;

import fr.pantheonsorbonne.miage.game.Card;
import fr.pantheonsorbonne.miage.game.Deck;
import fr.pantheonsorbonne.miage.model.Game;
import fr.pantheonsorbonne.miage.model.GameCommand;
import fr.pantheonsorbonne.miage.strategie.Strateg;
import fr.pantheonsorbonne.miage.strategie.StrategieColor;

/**
 * this is the player part of the network version of the uno game
 */
public class UnoNetworkPlayer extends Player {

    UnoNetworkPlayer(String nom, Strateg theStrategie) {
        super(nom, theStrategie);
    }

    private static final String PLAYER_ID = "MEME";
    static final PlayerFacade playerFacade = Facade.getFacade();
    private static Game uno;
    private static int nbrOfTurn = 0;
    private static final String ID = "playerId";
    private static Player networkPlayer = new UnoNetworkPlayer(PLAYER_ID, new StrategieColor());

    public static void main(String[] args) {

        playerFacade.waitReady();
        playerFacade.createNewPlayer(PLAYER_ID);
        uno = playerFacade.autoJoinGame("uno");
        boolean endGame = false;
        System.out.println("Je suis " + networkPlayer.name);
        while (!endGame) {

            GameCommand command = playerFacade.receiveGameCommand(uno);
            switch (command.name()) {

                case "cardsForYou":
                    System.out.println("Tenez vos cartes: ");
                    handleCardsForYou(command);
                    break;
                case "playACard":
                    nbrOfTurn++;
                    ((UnoNetworkPlayer) networkPlayer).handlePlayACard(command);
                    break;
                case "piochePlus2":
                    nbrOfTurn++;
                    handleCardsForYou(command);
                    System.out.println("Pioche de deux cartes !");
                    break;
                case "piochePlus4":
                    nbrOfTurn++;
                    handleCardsForYou(command);
                    System.out.println("Pioche de quatre cartes !");
                    break;
                case "interdit":
                    nbrOfTurn++;
                    System.out.println("Interdit !");
                    break;
                case "inverse":
                    System.out.println("Inverse !");
                    playerFacade.sendGameCommandToAll(uno, new GameCommand("INVERSE"));
                    break;
                case "gameOver":
                    handleGameOverCommand();
                    endGame = true;
                    break;
                case "verifWinner":
                    verifiWinner(command);
                    break;
                case "getCardsToRemove":
                    removeHandCard(command);
                    break;
                case "countNbrOfPoint":
                    System.out.println("couting card");
                    coutingCard(command);
                    break;
                case "yourPoints":
                    System.out.println("Your points: ");
                    addPoints(Integer.parseInt(command.body()), command);
                    break;
                default:
                    System.out.println();
                    break;
            }
        }
    }

    private static void verifiWinner(GameCommand command) {
        if (command.params().get(ID).equals(networkPlayer.name)) {
            if (networkPlayer.hand.isEmpty()) {
                playerFacade.sendGameCommandToAll(uno, new GameCommand("winner"));
            } else {
                playerFacade.sendGameCommandToAll(uno, new GameCommand("pasWinner"));
            }
        }
    }

    private static void addPoints(int pointsToAdd, GameCommand command) {
        if (command.params().get(ID).equals(networkPlayer.name)) {
            int pointBefore = networkPlayer.point;
            int newPoint = pointBefore + pointsToAdd;
            networkPlayer.point = newPoint;
            System.out.println(networkPlayer.point);
            playerFacade.sendGameCommandToAll(uno, new GameCommand("finalPoints", "" + networkPlayer.point));
        }
    }

    private static void coutingCard(GameCommand command) {
        if (command.params().get(ID).equals(networkPlayer.name)) {
            int point = 0;
            for (Card currCard : networkPlayer.hand) {
                point = point + currCard.getPoint();
            }
            playerFacade.sendGameCommandToAll(uno, new GameCommand("getPoints", "" + point));
        }
    }

    private static void handleCardsForYou(GameCommand command) {
        Card[] cardsInHand = Card.getStringToCards(command.body());
        for (int i = 0; i < cardsInHand.length; i++) {
            networkPlayer.hand.addFirst(cardsInHand[i]);
            System.out.print(Card.valueOfCard(networkPlayer.hand.getFirst()));
        }
    }

    private void handlePlayACard(GameCommand command) {
        if (command.params().get(ID).equals(networkPlayer.name)) {
            System.out.println("A mon nbrOfTurn: " + nbrOfTurn);
            System.out.println("Il me reste " + hand.size() + " cartes.");
            displayHandCard();
            if (!hand.isEmpty()) {
                System.out.println("Ancienne carte: " + command.body());
                Card cardToFind = Card.getStringToCard(command.body());
                Card cardFind = readyToPlay(cardToFind);
                if (cardFind != null) {
                    System.out.println("Nouvelle carte posée: " + Card.valueOfCard(cardFind));
                    if (Card.isJoker(cardFind) || Card.isTake4(cardFind)) {
                        System.out.println("On a changé de couleur.");
                        networkPlayer.strategie.chooseColor(readyToPlay(cardFind), hand);
                    }
                    playerFacade.sendGameCommandToAll(uno, new GameCommand("card", Card.valueOfCard(cardFind)));
                    System.out.println("La carte est supprimé: " + Card.valueOfCard(cardFind));
                    hand.remove(cardFind);
                } else {
                    System.out.println("Pas de carte à poser.");
                    playerFacade.sendGameCommandToAll(uno, new GameCommand("cardNotFound"));
                }
            }
        }
    }

    private static void handleGameOverCommand() {
        if (networkPlayer.point >= 100) {
            System.out.println("I've won after "+nbrOfTurn+" !");
        } else {
            System.out.println("I've lost after "+nbrOfTurn+"  :-(");
        }
        System.exit(0);
    }

    @Override
    public Card drawCard(Card cardToFind) {
        System.out.println("Je pioche une carte.");
        playerFacade.sendGameCommandToAll(uno, new GameCommand("drawCard"));
        GameCommand command = playerFacade.receiveGameCommand(uno);
        hand.addFirst(Card.getStringToCard(command.body()));
        return strategie.searchTheBestCardToPut(cardToFind, hand);
    }

    public String handToStringToDelete() {
        String cardsInHand = "\n";
        int handSize = networkPlayer.hand.size();
        for (int i = 0; i < handSize; i++) {
            cardsInHand += Card.valueOfCard(networkPlayer.hand.poll()) + "\n";
        }
        return cardsInHand;
    }

    private static void removeHandCard(GameCommand command) {
        if (command.params().get(ID).equals(networkPlayer.name)) {
            if (networkPlayer.hand.isEmpty()) {
                playerFacade.sendGameCommandToAll(uno, new GameCommand("noCards"));
            } else {
                playerFacade.sendGameCommandToAll(uno,
                        new GameCommand("cardsToRemove", ((UnoNetworkPlayer) networkPlayer).handToStringToDelete()));
            }
        }
        Deck.mixCards();
    }
}