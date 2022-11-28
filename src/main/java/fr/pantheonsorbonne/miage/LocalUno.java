package fr.pantheonsorbonne.miage;
import fr.pantheonsorbonne.miage.game.*;

import java.util.*;

public class LocalUno extends UnoEngine {
    
    private final int nbrOfplayerOnTheRound;
    private LinkedList<Player> playerOnTheRound=new LinkedList<>();
    private Player winner;

    public  LocalUno(int nbr){
        this.nbrOfplayerOnTheRound=nbr;
        for (int i=0; i<nbrOfplayerOnTheRound; i++){
            playerOnTheRound.add(new Player("Player-" + new Random().nextInt()));
        }
    }

    @Override
    protected Player getWinner(){
        return this.winner;
    }


    @Override
    protected LinkedList<Player> getPlayerOnTheRound(){
        return this.playerOnTheRound;
    }

    @Override
    protected void giveCardsToPlayer(){
        for (int i=0; i<nbrOfplayerOnTheRound; i++){
            playerOnTheRound.get(i).setHand();
        }
    }

    public void setWinner(Player x){
        this.winner=x;
    }

    @Override
    protected boolean getCardFromPlayer(Player b){
        Pile.showCardTalo();
        return b.readyToPlay(Pile.cardTopDeck());
     }

     
     @Override
     protected boolean verifIfWinner(Player player){
        System.out.println("je suis la");
        if (player.getHand().isEmpty()){
            System.out.println("je suis la et la");
            this.winner=player;
            return true;
        }
        return false;
     }

    @Override
    protected int specialCard(int i){

            if (Card.isTake4(Pile.cardTopDeck()) || Card.isTake2(Pile.cardTopDeck())){
                i=searchSpecialCardAdding(Pile.cardTopDeck(),playerOnTheRound,i);
                
                    i++;
                if (i>=playerOnTheRound.size()){
                    i=0;
                }
                }   
            else if (Card.isInterdit(Pile.cardTopDeck())){
                i++;
                if (i>=playerOnTheRound.size()){
                    i=0;
                }
            }
        return i;
    }

    @Override
    public void getCardsToRemove(){
     //   System.out.println("retire carte en main car quelqun a gagner");
        for (Player currPlayer : playerOnTheRound){
            if (!currPlayer.getHand().isEmpty()){
                System.out.println(currPlayer.getPoint()+"a gagehr");
            }
            while (!currPlayer.getHand().isEmpty()){
                Deck.setPioche(Deck.getPiocheSize(),currPlayer.getHand().poll());
                Deck.setPiocheSize(Deck.getPiocheSize()+1);
              //  System.out.println("oko cest fait");
            }   
        }
        Deck.mixCards();
    }
     


    @Override
    protected boolean playRound(){
        getCardsToRemove();
        giveCardsToPlayer();
        return super.playRound(); 
     }

    @Override
    protected int searchSpecialCardAdding(Card card,LinkedList<Player> playerOnTheRound,int currPosition) {
        int nextPlayer=currPosition+1;
        if (currPosition>=playerOnTheRound.size()-1){
            nextPlayer=0;
        }
        if (Card.isTake4(card)) {
            playerOnTheRound.get(nextPlayer).getPlayerStrategie().add4Cards(playerOnTheRound.get(nextPlayer).getHand());
            return nextPlayer;
        } else {
            playerOnTheRound.get(nextPlayer).getPlayerStrategie().add2Cards(playerOnTheRound.get(nextPlayer).getHand());
            return nextPlayer;
        }
    }

     public static void main(String[] args){       
       LocalUno play=new LocalUno(10);
      play.play();
      

     }

}


