package fr.pantheonsorbonne.miage;
import fr.pantheonsorbonne.miage.game.*;

import java.util.*;

public class Play {
    private final int nbrOfplayerOnTheRound;
    private LinkedList<Player> playerOnTheRound=new LinkedList<>();
    private Player winner;

    public  Play(int nbr){
        this.nbrOfplayerOnTheRound=nbr;
        for (int i=0; i<nbrOfplayerOnTheRound; i++){
            playerOnTheRound.add(new Player("Player-" + new Random().nextInt()));
        }
    }

    public LinkedList<Player> getPlayerOnTheRound(){
        return this.playerOnTheRound;
    }

    public void setWinner(Player x){
        winner=x;
    }
    
    LinkedList<Player> inverseGame(int posi){
        int other=playerOnTheRound.size()-posi;
        ListIterator<Player> iterator = playerOnTheRound.listIterator(posi); 
        ListIterator<Player> list2 = playerOnTheRound.listIterator(playerOnTheRound.size()); 
        LinkedList<Player> newList=new LinkedList<>();
        while(iterator.hasPrevious()){
            newList.add(iterator.previous());
        } 
        while(list2.hasPrevious() && other>0){
            newList.add(list2.previous());
             other--;
         } 
         return newList;

    }

    boolean gamePlay(Player b){
        return b.readyToPlay(Pile.cardTopDeck());
     }

     
  
     public boolean verifIfWinner(Player player){
        if (player.getHand().isEmpty()){
            winner=player;
            return true;
        }
        return false;
     }

    boolean setOfTheRound(int i){
        playerOnTheRound=inverseGame(i);
        return false;
    }

    int specialCard(int i){

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

     private boolean oneRound(){
            for (int i=0; i<playerOnTheRound.size(); i++){
                if (gamePlay(playerOnTheRound.get(i))){
                    if (verifIfWinner(playerOnTheRound.get(i))){
                        return true;
                    }
                    if(Card.isInverse(Pile.cardTopDeck())){
                        return setOfTheRound(i);
                    }
                    int x=specialCard(i);  
                    i=x;
                }
            }
            return false;
        }

    void getCardsToRemove(){
        for (Player currPlayer : playerOnTheRound){
            while (!currPlayer.getHand().isEmpty()){
                Deck.setPioche(Deck.getPiocheSize(),currPlayer.getHand().poll());
                Deck.setPiocheSize(Deck.getPiocheSize()+1);
            }   
        }
        Deck.mixCards();
    }
     
     int countPoint(Deque<Card> cardInTheHand){
        int point=0;
        for (Card currCard : cardInTheHand){
            point=point+currCard.getPoint();
        }
        return point;
     }

     public boolean countNbrOfPoint(){
        int counterOfPoint=0;
        for (Player currPlayer: playerOnTheRound){
            counterOfPoint=counterOfPoint+countPoint(currPlayer.getHand());
        }
        int pointBefore=winner.getPoint();
        int newPoint=pointBefore+counterOfPoint;
        winner.setPoint(newPoint);
        return winner.getPoint()>=100;
    }

    public void playNow(){
        int count=0;
        do{
            getCardsToRemove();
            for (int i=0; i<nbrOfplayerOnTheRound; i++){
                playerOnTheRound.get(i).setHand();
            }
            while (!oneRound());
            count++;
        }while(!countNbrOfPoint());
        
        
        System.out.println("nombre de point marquee par le joueur "+winner.getPoint()+" et son nom "+winner.getName()+" apres tant de manche "+count);
     }

    
    int searchSpecialCardAdding(Card card,LinkedList<Player> playerOnTheRound,int currPosition) {
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
        Play x=new Play(14);
        x.playNow();
     }

}

