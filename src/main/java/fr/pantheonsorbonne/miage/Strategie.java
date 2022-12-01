package fr.pantheonsorbonne.miage;
import fr.pantheonsorbonne.miage.game.*;
import fr.pantheonsorbonne.miage.enums.CardColor;
import java.util.*;

//static: pas d'objet

public class Strategie {

     //chercher valeur max parmi les memes couleur de la cartes
    private Card searchMaxValueCard(Card[] cardsInHandOfSameColor){
        int max = 0;
        Card cardFind = null;
        for (int i=0; i<cardsInHandOfSameColor.length; i++){
            int nombre=Integer.parseInt(cardsInHandOfSameColor[i].getValue().getValeur());
            if (nombre>max){
                max=nombre;
                cardFind=cardsInHandOfSameColor[i];
            }
        }
        return cardFind;
    }

    //cherche toute les cartes de la meme couleur
    private Card[] searchSameColorsCard(CardColor color, int nbrOfSameColorCard, Deque<Card> handOfPlayer){
        Card[] cardsFind=new Card[nbrOfSameColorCard];
        int count=0;
        for (Card currCard: handOfPlayer){
            if(currCard.getColor().equals(color)){
                cardsFind[count++]=currCard;
            }
        }
        return cardsFind;
    }

    //trouver meme couleur que le talon
    private Card findSameColorOfDeck(Card cardToFind, Deque<Card> handOfPlayer){
        for (Card currCard: handOfPlayer){
            if(currCard.getColor().equals(cardToFind.getColor())){
                return currCard;
            }
        }
        return null;
    }

    //recherche meme valeur de carte
    private Card findSameValueOfDeck(Card cardToFind, Deque<Card> handOfPlayer){
        for (Card currCard: handOfPlayer){
            if(currCard.getValue().equals(cardToFind.getValue())){
                return currCard;
            }
        }
        return null;

    }

    //chercher le nbr de cartes de cette couleur
    private static int findCardSameColor(CardColor color, Deque<Card> handOfPlayer){
        int count=0;
            for (Card currCard: handOfPlayer){
                if (currCard.getColor().equals(color)){
                    count++;
                }
            }
        return count;
    }

    // chercher la couleur la plus presente dans le jeux de carte
    private static CardColor findMostPresentColor(Deque<Card> handOfPlayer){
        int max = 0;
        CardColor colorFind=handOfPlayer.getFirst().getColor();
        for (CardColor color : CardColor.values()){
            int count=findCardSameColor(color,handOfPlayer);
            if (count > max){
                max = count;
                colorFind = color;
            }
        }
        return colorFind;
    }

    //choisir une couleur pour la carte du joker ou +4
    public static Card chooseColor(Card card, Deque<Card> handOfPlayer){
        if (handOfPlayer.isEmpty()){
            return card;
        }
        card.setColor(findMostPresentColor(handOfPlayer));
        return card;
    }

    Card findCardToChangeColor( Deque<Card> handOfPlayer){
        for (Card currCard : handOfPlayer){
            if (Card.isTake4(currCard) || Card.isJoker(currCard)){
                return currCard;
            }
        }
        return null;
    }

    public boolean findTheSameCardOfAdding(Card card, Deque<Card> handOfPlayer) {
        for (Card currCard : handOfPlayer){
            if (currCard.getValue().equals(card.getValue())){
                playCard(currCard,handOfPlayer);
                return true;
            }
        }
        return false;
    }

    public Card findPlusFour(Deque<Card> handOfPlayer) {
        for (Card currCard : handOfPlayer){
            if (Card.isTake4(currCard)){
                return currCard;
            }
        }
        return null;
    }

    public Card searchSpecialCard(Card cardToFind, Deque<Card> handOfPlayer){
        for (Card currCard : handOfPlayer){
            if (Card.isJoker(currCard)){
                return currCard;
            }
            if (currCard.getColor().equals(cardToFind.getColor()) && Card.isSpecialCard(currCard) && !Card.isTake4(currCard)){
                return currCard;
            }
            if (currCard.getValue().equals(cardToFind.getValue()) && Card.isSpecialCard(currCard) && !Card.isTake4(currCard)){
                return currCard;
            }
        }
        return null;
    }

    //ajouts de plusieurs cartes dans le main
    public static void addCards(int nbrOfAdding,Deque<Card> handOfPlayer){
        for (int i=0; i<nbrOfAdding; i++){
            addHandCard(handOfPlayer);
        }
    }

    public static boolean add4Cards(Deque<Card> handOfPlayer){
        addCards(4,handOfPlayer);
        return true;
    }

    public static boolean add2Cards(Deque<Card> handOfPlayer) {
        addCards(2,handOfPlayer);
        return true;
    }

    public Card findTheBestCardToPut(Card cardToFind,Card cardToCompare,Deque<Card> handOfPlayer){
        Card cardFind=findSameValueOfDeck(cardToFind,handOfPlayer);
        if (cardFind==null){
            return cardToCompare;
        }
        if (Integer.parseInt(cardFind.getValue().getValeur())>Integer.parseInt(cardToCompare.getValue().getValeur())){
            return cardFind;
        }
        return cardToCompare;
    }

    //poser carte sur la Pile
    public static void playCard(Card card,Deque<Card> handOfPlayer){
        Pile.addCardToGameDeck(card);
        removeHandCard(card,handOfPlayer);
        if (Card.isJoker(card) || Card.isTake4(card)){
            chooseColor(card,handOfPlayer);
        }
    }

    //supprime une carte de la main
    public static void removeHandCard(Card card,Deque<Card> handOfPlayer){
        handOfPlayer.remove(card);
    }

    //commence par chercher la même valeur que le deck, sinon cherche la même couleur
    public boolean searchCardToPutBetweenValueAndColor(Card cardToFind,Deque<Card> handOfPlayer){
        Card cardFind=findSameValueOfDeck(cardToFind,handOfPlayer);
        if (cardFind==null){
            cardFind=findSameColorOfDeck(cardToFind,handOfPlayer);
            if (cardFind==null){
                cardFind=findCardToChangeColor(handOfPlayer);
                if (cardFind==null){
                    return false;
                }
            }
        }
        playCard(cardFind,handOfPlayer);
        return true;
}

//ajout d'une carte dans la main  (pioche ou +2 ou +4)
public static void addHandCard(Deque<Card> handOfPlayer){
    Card[] deck=Deck.getRandomCards(1);
    handOfPlayer.addFirst(deck[0]);
    if (Deck.getPiocheSize()<=0){
        Deck.createNewDeck();
    }
}

public boolean searchCardToPut(Card cardToFind,Deque<Card> handOfPlayer){
    Card cardFind=searchSpecialCard(cardToFind,handOfPlayer);
    if (cardFind==null){
        int nbrOfSameColorCards=findCardSameColor(cardToFind.getColor(),handOfPlayer);
        Card[] sameColorCards=searchSameColorsCard(cardToFind.getColor(), nbrOfSameColorCards,handOfPlayer);
        Card cardFindWithColor=searchMaxValueCard(sameColorCards);
        if (cardFindWithColor!=null){
            playCard(findTheBestCardToPut(cardToFind,cardFindWithColor,handOfPlayer),handOfPlayer);
            return true;
        }
        else{ 
            cardFind=findPlusFour(handOfPlayer);
            if (cardFind==null){
                return false;
            }
        }
    }
    playCard(cardFind,handOfPlayer);
    return true;
}

}