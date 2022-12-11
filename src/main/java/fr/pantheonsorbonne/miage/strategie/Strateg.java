package fr.pantheonsorbonne.miage.strategie;

import java.util.Deque;

import fr.pantheonsorbonne.miage.enums.CardColor;
import fr.pantheonsorbonne.miage.game.Card;

public interface Strateg {

    public abstract Card[] searchSameColorsCard(CardColor color, int nbrOfSameColorCard, Deque<Card> handOfPlayer);

    public abstract int findRepetitionColor(CardColor color, Deque<Card> handOfPlayer);

    public abstract Card searchisSpecialCard(Card cardToFind, Deque<Card> handOfPlayer);

    public abstract Card searchTheBestCardToPutBetweenColorAndValue(Card cardToFind, Deque<Card> handOfPlayer);

    public abstract Card searchTheBestCardToPut(Card cardToFind, Deque<Card> handOfPlayer);

    public abstract Card chooseColor(Card card, Deque<Card> handOfPlayer);

    public abstract void playCard(Card card, Deque<Card> handOfPlayer);

    public abstract CardColor findMostPresentColor(Deque<Card> handOfPlayer);

    public static Card findSameColorOfDeck(Card cardToFind, Deque<Card> handOfPlayer) {
        for (Card currCard : handOfPlayer) {
            if (currCard.getColor().equals(cardToFind.getColor())) {
                return currCard;
            }
        }
        return null;
    }

    public static Card findSameValueOfDeck(Card cardToFind, Deque<Card> handOfPlayer) {
        for (Card currCard : handOfPlayer) {
            if (currCard.getValue().equals(cardToFind.getValue())) {
                return currCard;
            }
        }
        return null;
    }

    public static Card searchMaxValueCard(Card[] cardsInHandOfSameColor) {
        int max = 0;
        Card cardFind = null;
        for (int i = 0; i < cardsInHandOfSameColor.length; i++) {
            int nombre = Integer.parseInt(cardsInHandOfSameColor[i].getValue().getValeur());
            if (nombre >= max) {
                max = nombre;
                cardFind = cardsInHandOfSameColor[i];
            }
        }
        return cardFind;
    }

    public static Card findCardToChangeColor(Deque<Card> handOfPlayer) {
        for (Card currCard : handOfPlayer) {
            if (Card.isTake4(currCard) || Card.isJoker(currCard)) {
                return currCard;
            }
        }
        return null;
    }

    public static Card findPlusFour(Deque<Card> handOfPlayer) {
        for (Card currCard : handOfPlayer) {
            if (Card.isTake4(currCard)) {
                return currCard;
            }
        }
        return null;
    }
}
