package fr.pantheonsorbonne.miage;

import java.util.Deque;
import java.util.LinkedList;
import java.util.ListIterator;

import fr.pantheonsorbonne.miage.game.Card;
import fr.pantheonsorbonne.miage.game.Pile;

public abstract class UnoEngine {

    public void play() {
            //loop until there is a winner for this round
         
        do{
                playRound();
   
        }while(!countNbrOfPoint());
        
        System.out.println("nombre de point marquee    par le joueur "+getWinner().getPoint()+" et son nom "+getWinner().getName()+" apres tant de manche ");

    }

    protected boolean playRound(){
        while(true){

        
        for (int i=0; i<getPlayerOnTheRound().size(); i++){
            if (getCardFromPlayer(getPlayerOnTheRound().get(i))){
                if (verifIfWinner(getPlayerOnTheRound().get(i))){
                    System.out.println("hhhaaa");
                    return true;
                }
                if(Card.isInverse(Pile.cardTopDeck())){
                    inverseGame(i);
                    i=0;
                }
                int x=specialCard(i);  
                i=x;
            }
        }
    }
    }

    
    protected int countPoint(Deque<Card> cardInTheHand){
        int point=0;
        for (Card currCard : cardInTheHand){
            point=point+currCard.getPoint();
        }
        return point;
     }

    
     public boolean countNbrOfPoint(){
        int counterOfPoint=0;
        for (Player currPlayer: getPlayerOnTheRound()){
            counterOfPoint=counterOfPoint+countPoint(currPlayer.getHand());
        }
        int pointBefore=getWinner().getPoint();
        int newPoint=pointBefore+counterOfPoint;
        getWinner().setPoint(newPoint);
        return getWinner().getPoint()>=500;
    }

    protected LinkedList<Player> inverseGame(int posi) {
        int other=getPlayerOnTheRound().size()-posi;
        ListIterator<Player> iterator = getPlayerOnTheRound().listIterator(posi); 
        ListIterator<Player> list2 = getPlayerOnTheRound().listIterator(getPlayerOnTheRound().size()); 
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

    protected abstract LinkedList<Player> getPlayerOnTheRound();

    protected abstract void giveCardsToPlayer();

    protected abstract boolean verifIfWinner(Player player);

    protected abstract void getCardsToRemove();

    protected abstract int searchSpecialCardAdding(Card card,LinkedList<Player> playerOnTheRound,int currPosition);

    protected abstract int specialCard(int i);

  //  protected abstract int countPoint(Deque<Card> cardInTheHand);

  //  protected abstract boolean countNbrOfPoint();

    protected abstract boolean getCardFromPlayer(Player b);

    protected abstract Player getWinner();
    
}


