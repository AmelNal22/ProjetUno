package fr.pantheonsorbonne.miage.strategie;

import java.util.Deque;

import fr.pantheonsorbonne.miage.enums.CardColor;
import fr.pantheonsorbonne.miage.game.Card;
import fr.pantheonsorbonne.miage.game.Pile;

public class StrategieValue implements Strateg {

    // Dans cette calsse, on va chercher à chaque fois parmi toutes les cartes en
    // main celle ayant la valeur maximale.

    /*
     * Pour cela, on commence par poser les cartes speciales
     * (joker/interdit/inverse).
     * La carte +4 ne peut être posée seulement si dans la main aucune carte n'a la
     * même couleur que le haut de la pile.
     * Donc dès qu'on en a l'occasion, on pose le +4.
     * Ensuite, on regarde la couleur de la carte posée au haut de la pile.
     * On cherche toutes les cartes en main de cette couleur.
     * Parmi les cartes trouvées, on va chercher celles dont le nombre est le plus
     * élevé.
     * En même temps on regarde si on a une carte de même valeur que le haut de la
     * pile (peu importe la couleur).
     * Si c'est le cas on compare cette carte avec la carte trouvée juste avant.
     * On pose la meilleur carte.
     * Si on n'a pas de bonne carte, on pioche une carte et si c'est une bonne
     * pioche on la pose.
     */

    @Override
    public Card[] searchSameColorsCard(CardColor color, int nbrOfSameColorCard, Deque<Card> handOfPlayer) {
        Card[] cardsFind = new Card[nbrOfSameColorCard];
        int count = 0;
        for (Card currCard : handOfPlayer) {
            if (currCard.getColor().equals(color)) {
                cardsFind[count++] = currCard;
            }
        }
        return cardsFind;
    }

    @Override
    public int findRepetitionColor(CardColor color, Deque<Card> handOfPlayer) {
        int count = 0;
        for (Card currCard : handOfPlayer) {
            if (currCard.getColor().equals(color)) {
                count++;
            }
        }
        return count;
    }

    @Override
    public Card searchisSpecialCard(Card cardToFind, Deque<Card> handOfPlayer) {
        for (Card currCard : handOfPlayer) {
            if (Card.isJoker(currCard)) {
                return currCard;
            }
            if (currCard.getColor().equals(cardToFind.getColor()) && Card.isSpecialCard(currCard)
                    && !Card.isTake4(currCard)) {
                return currCard;
            }
            if (currCard.getValue().equals(cardToFind.getValue()) && Card.isSpecialCard(currCard)
                    && !Card.isTake4(currCard)) {
                return currCard;
            }
        }
        return null;
    }

    public Card findTheBestCardToPut(Card cardToFind, Card cardToCompare, Deque<Card> handOfPlayer) {
        Card cardFind = Strateg.findSameValueOfDeck(cardToFind, handOfPlayer);
        if (cardFind == null) {
            return cardToCompare;
        }
        if (Integer.parseInt(cardFind.getValue().getValeur()) > Integer
                .parseInt(cardToCompare.getValue().getValeur())) {
            return cardFind;
        }

        return cardToCompare;
    }

    @Override
    public CardColor findMostPresentColor(Deque<Card> handOfPlayer) {
        int max = 0;
        CardColor colorFind = handOfPlayer.getFirst().getColor();
        for (CardColor color : CardColor.values()) {
            int count = findRepetitionColor(color, handOfPlayer);
            if (count > max) {
                max = count;
                colorFind = color;
            }
        }
        return colorFind;
    }

    @Override
    public Card chooseColor(Card card, Deque<Card> handOfPlayer) {
        if (handOfPlayer.isEmpty()) {
            return card;
        }
        card.setColor(findMostPresentColor(handOfPlayer));
        return card;
    }

    @Override
    public void playCard(Card card, Deque<Card> handOfPlayer) {
        Pile.addCardToGameDeck(card);
        handOfPlayer.remove(card);
        if (Card.isJoker(card) || Card.isTake4(card)) {
            chooseColor(card, handOfPlayer);
        }
        Card.valueOfCard(card);
    }

    @Override
    public Card searchTheBestCardToPutBetweenColorAndValue(Card cardToFind, Deque<Card> handOfPlayer) {
        Card cardFind = Strateg.findSameValueOfDeck(cardToFind, handOfPlayer);
        if (cardFind == null) {
            cardFind = Strateg.findSameColorOfDeck(cardToFind, handOfPlayer);
            if (cardFind == null) {
                cardFind = Strateg.findCardToChangeColor(handOfPlayer);
                if (cardFind == null) {
                    return null;
                }
            }
        }
        return cardFind;
    }

    @Override
    public Card searchTheBestCardToPut(Card cardToFind, Deque<Card> handOfPlayer) {
        Card cardFind = searchisSpecialCard(cardToFind, handOfPlayer);
        if (cardFind == null) {
            int nbrOfSameColorCards = findRepetitionColor(cardToFind.getColor(), handOfPlayer);
            Card[] sameColorCards = searchSameColorsCard(cardToFind.getColor(), nbrOfSameColorCards, handOfPlayer);
            Card cardFindWithColor = Strateg.searchMaxValueCard(sameColorCards);
            if (cardFindWithColor != null) {
                return findTheBestCardToPut(cardToFind, cardFindWithColor, handOfPlayer);

            } else {
                cardFind = Strateg.findPlusFour(handOfPlayer);
                if (cardFind == null) {
                    return null;
                }
            }
        }
        return cardFind;
    }

}