package fr.pantheonsorbonne.miage;

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Set;

import fr.pantheonsorbonne.miage.game.Card;
import fr.pantheonsorbonne.miage.game.Pile;

public abstract class UnoEngine {
    
    private String winner;

    protected abstract void setPlayerOnTheRound();

    public void play() {
        setPlayerOnTheRound();
        for (String playerName : getInitialPlayers()) {
            giveCardsToPlayer(playerName);
            
        }
        do{
            System.out.println("???");
                playRound();
            
        }while(!countPointAllPlayers());
        
        declareWinner(getWinner());
        System.out.println("nombre de point marquee    par le joueur "+getWinner()+" et son nom "+getWinner()+" apres tant de manche ");
        System.exit(0);

    }

    protected abstract void declareWinner(String name);

    
    protected abstract boolean verifIfWinner(String player);

    protected boolean playRound(){
        while(true){
        for (int i=0; i<getPlayerOnTheRound().size(); i++){
            if (getCardFromPlayer(getPlayerOnTheRound().get(i))){
                if (verifIfWinner((getPlayerOnTheRound().get(i)))){
                  winner=getPlayerOnTheRound().get(i);
                    return true;
                }
                if(Card.isInverse(Pile.getCardTopDeck())){
                    setNewPlayerOnTheRound(gameIsReversed(i));
                    i=-1;
                }else{
                    int x=isSpecialCard(i);  
                    i=x;
                }
            }
        }
    }
    }
    
    protected abstract boolean countPointAllPlayers();

    protected String getWinner(){
        return this.winner;
    }

    protected abstract LinkedList<String> getPlayerOnTheRound();
    
    protected abstract Set<String> getInitialPlayers();

    protected abstract void giveCardsToPlayer(String player);

    protected abstract boolean getCardFromPlayer(String player);

    protected void interditToPlay(int i) {
        System.out.println("Vous ne jouez pas");
    }

    protected int isSpecialCard(int i){
            if (Card.isTake4(Pile.getCardTopDeck()) || Card.isTake2(Pile.getCardTopDeck())){
                i=searchSpecialCardAdding(Pile.getCardTopDeck(),getPlayerOnTheRound(),i);
                }   
            else if (Card.isInterdit(Pile.getCardTopDeck())){
                i++;
                if (i>=getPlayerOnTheRound().size()){
                    i=0;
                }
                interditToPlay(i);
            }
        return i;
    }

    protected abstract int searchSpecialCardAdding(Card card,LinkedList<String> playerOnTheRound,int currPosition);
    
    protected LinkedList<String> gameIsReversed(int posi) {
        int other=getPlayerOnTheRound().size()-posi;
        ListIterator<String> iterator = getPlayerOnTheRound().listIterator(posi); 
        ListIterator<String> list2 = getPlayerOnTheRound().listIterator(getPlayerOnTheRound().size()); 
        LinkedList<String> newList=new LinkedList<>();
        while(iterator.hasPrevious()){
            newList.add(iterator.previous());
        } 
        while(list2.hasPrevious() && other>0){
            newList.add(list2.previous());
             other--;
        }
        return newList;
    }

    protected abstract void setNewPlayerOnTheRound(LinkedList<String> playerOnTheRound);
    
    protected abstract void getCardsToRemove();

}