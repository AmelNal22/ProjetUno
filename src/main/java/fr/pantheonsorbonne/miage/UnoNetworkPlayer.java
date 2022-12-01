package fr.pantheonsorbonne.miage;

import fr.pantheonsorbonne.miage.game.Card;
import fr.pantheonsorbonne.miage.game.Deck;
import fr.pantheonsorbonne.miage.game.Pile;
import fr.pantheonsorbonne.miage.model.Game;
import fr.pantheonsorbonne.miage.model.GameCommand;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Random;


public class UnoNetworkPlayer{

   
    static final String PLAYER_ID = "Player-" + new Random().nextInt();
    static final Deque<Card> hand = new LinkedList<>();
    static final PlayerFacade playerFacade = Facade.getFacade();
    static Game uno;
    private static Strategie playerStrategie=new Strategie();
    private static int point=0;

    public static void main(String[] args) {

        playerFacade.waitReady();
        playerFacade.createNewPlayer(PLAYER_ID);
        uno = playerFacade.autoJoinGame("uno");
        boolean endGame=false;
        while (!endGame) {

            GameCommand command = playerFacade.receiveGameCommand(uno);
            switch (command.name()) {
                case "cardsForYou":
                System.out.println("Itenez");
                    handleCardsForYou();
                    break;
                case "playACard":
                    handlePlayACard(command,Pile.getCardTopDeck());
                    Pile.showCardTopDeck();
                    break;
                case "cardPlay":
                    System.out.println(command.body());
                    break;
                case "piochePlus2":
                    pioche2();
                    System.out.println("                        pioche 2 cartes");
                    break;
                case "piochePlus4":
                    pioche4();
                    System.out.println("                    pioche 4 cartes");
                    break;
                case "interdit":
                    System.out.println("                      cest  Interdit");
                    break;
                case "inverse":
                    System.out.println("                        cest  inverse");
                    break;
                case "count":
                    System.out.println("                            count");
                    break;
                case "gameOver":
                    handleGameOverCommand(command);
                    endGame=true;
                    break;
                case "verifWinner":
                    System.out.println("verifWiner");
                    verifiWinner();
                    break;
                case "getCardsToRemove":
                    System.out.println("removing card");
                    removeCard();
                    break;
                case "countNbrOfPoint":
                    System.out.println("couting card");
                    coutingCard();
                    break;
                case "yourPoints":
                    System.out.println("your ptn");
                    addPoints(Integer.parseInt(command.body()));
                    break;    
            }
           
        }
    }


    private static void verifiWinner() {

        if (hand.isEmpty()){
            playerFacade.sendGameCommandToAll(uno, new GameCommand("winner"));
            System.out.println("il na plus de cartes");
        }else{
            playerFacade.sendGameCommandToAll(uno, new GameCommand("pasWinner"));
            System.out.println("a des cartes");
        }
    }


    private static void addPoints(int pointsToAdd) {
        int pointBefore = point;
             int newPoint = pointBefore + pointsToAdd;
             point=newPoint;
             System.out.println(point);
             playerFacade.sendGameCommandToAll(uno, new GameCommand("finalPoints", ""+point));
    }


    private static void coutingCard() {
        int point = 0;
        for (Card currCard : hand) {
            point = point + currCard.getPoint();
        }
        playerFacade.sendGameCommandToAll(uno, new GameCommand("getPoints", ""+point));
 
    }

    


    public static Card[] getHandToString(){
        Card c[]=new Card[hand.size()];
        int i=0;
        for (Card currCard: hand){
            c[i]=currCard;
            i++;
        }
        return c;
    }

    public static void setHand(){
        Card[] deck=Deck.getRandomCards(7);
        if (Deck.getPiocheSize()<7){
            Deck.createNewDeck();
        }
        for (int i=0; i<deck.length; i++){
            hand.addFirst(deck[i]);
            System.out.println(hand.getFirst());
        }
    }

    private static void handleCardsForYou() {
        setHand();
    }

    private static void handlePlayACard(GameCommand command,Card carfToFind) {
        if (command.params().get("playerId").equals(PLAYER_ID)) {
            if (!hand.isEmpty()) {
                Pile.showCardTopDeck();
                readyToPlay(carfToFind);
                Pile.showCardTopDeck();
                playerFacade.sendGameCommandToAll(uno, new GameCommand("card", Pile.getCardTopDeckToString()));
            }
            else{
                playerFacade.sendGameCommandToAll(uno, new GameCommand("verifWiner", Pile.getCardTopDeckToString()));
            }
        }
    }

    private static void handleGameOverCommand(GameCommand command) {
        if (command.body().equals("win")) {
            System.out.println("I've won!");
        } else {
            System.out.println("I've lost :-(");
        }
        System.exit(0);
    }

    public static boolean readyToPlay(Card cardToFind){
        if (playerStrategie.searchCardToPut(cardToFind, hand)){
            return true;
        }
        else{
            return drawCard(cardToFind);
        }
    }

    public static boolean drawCard(Card cardToFind){
        addHandCard();
        return playerStrategie.searchCardToPutBetweenValueAndColor(cardToFind, hand);
    }

    //ajout d'une carte dans la main  (pioche ou +2 ou +4)
    public static void addHandCard(){
        Deck.getPiocheSize();
        if (Deck.getPiocheSize()<=0){
            System.out.println("ttxt");
            Deck.createNewDeck();
        }
        Card[] deck=Deck.getRandomCards(1);
        hand.addFirst(deck[0]);
    }


    public static void setPoint(int newPoint){
        point=newPoint;
    }
    private static int getPoint(){
        return point;
    }

    private static void removeCard(){
        if (!hand.isEmpty()) {
            System.out.println(getPoint() + "a gagehr");
        }
        else{
            while (!hand.isEmpty()) {
            Deck.setPioche(Deck.getPiocheSize(), hand.poll());
            Deck.setPiocheSize(Deck.getPiocheSize() + 1);
            }
        }

        Deck.mixCards();
    }

    private static void pioche4(){
        Strategie.add4Cards(hand);
    }

    private static void pioche2(){
        Strategie.add2Cards(hand);
    }
    
}