package fr.pantheonsorbonne.miage.strategie;

import java.util.Deque;

import fr.pantheonsorbonne.miage.enums.CardColor;
import fr.pantheonsorbonne.miage.game.Card;
import fr.pantheonsorbonne.miage.game.Pile;

/**
 * This class implements the Strategie
 */
public class StrategieColor implements Strateg {

    /*
     * Dans cette classe StrategieColor:
     * - Nous commençons par chercher la carte ayant une couleur similaire à la
     * couleur présentée sur le haut de la pile.
     * - Nous cherchons par la suite toutes les cartes de cette couleur là.
     * - Ensuite, on cherche la même valeur que la valeur présentée sur le haut de
     * la pile.
     * - Nous cherchons maintenant les cartes de même couleur que la carte que nous
     * venons de trouver.
     * - Nous comparons le nombre de cartes parmis les cartes trouvées précedemment
     * selon les couleurs et valeurs.
     * - Si ce sont les cartes de mêmes couleur qui l'"emporte", on regarde la plus
     * grande valeur de cette même couleur.
     * - Sinon, on pose les cartes de même valeur.
     * - Par la suite, on pose donc la carte ayant le plus grand nombre de carte de
     * même couleur.
     * - Dans le cas où l'on ne trouve pas, on pose la carte joker ou un +4 (ces
     * deux cartes sont posés qu'en cas de besoin).
     */

    /**
     * This method makes and returns an array of cards with the same colours as the
     * colour in parameter
     * 
     * @param color              : Designates the colour we want
     * @param nbrOfSameColorCard : Designates the number of cards of the same colour
     * @param handOfPlayer       : Designates the player's hand
     * @return an array of cards of the same colours
     */
    @Override
    public Card[] searchSameColorsCard(CardColor color, int nbrOfSameColorCard, Deque<Card> handOfPlayer) {
        Card[] cardsFind = new Card[nbrOfSameColorCard];
        int count = 0;
        for (Card currCard : handOfPlayer) {
            if (currCard.getColor().equals(color) && !Card.isJoker(currCard) && !Card.isTake4(currCard)) {
                cardsFind[count++] = currCard;
            }
        }
        return cardsFind;
    }

    /**
     * This method searches for the number of cards with the same colour as the
     * colour in parameter
     * 
     * @param color        : Designates the colour we want
     * @param handOfPlayer : Designates the player's hand
     * @return the number of times the colour is present in the game
     */
    @Override
    public int findRepetitionColor(CardColor color, Deque<Card> handOfPlayer) {
        int count = 0;
        for (Card currCard : handOfPlayer) {
            if (currCard.getColor().equals(color) && !Card.isJoker(currCard) && !Card.isTake4(currCard)) {
                count++;
            }
        }
        return count;
    }

    /**
     * This method counts the number of cards of the same colour in the hand for
     * each colour in the deck.
     * It then takes the highest number of cards of the same colour and returns the
     * corresponding colour
     * 
     * @param handOfPlayer : Designates the player's hand
     * @return the colour most present in the player's hand
     */
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

    /**
     * This method makes it possible to change the colour of the card (joker or +4)
     * according to the colour most present in the player's hand.
     * If it is the last card, the card does not change colour.
     * 
     * @param card         : Designates a changing color card (JOKER OR PLUSFOUR)
     * @param handOfPlayer : Designates the player's hand
     * @return the new card with a color changed
     */
    @Override
    public Card chooseColor(Card card, Deque<Card> handOfPlayer) {
        if (handOfPlayer.isEmpty()) {
            return card;
        }
        card.setColor(findMostPresentColor(handOfPlayer));
        return card;
    }

    /**
     * When the player put the cardOntheTopDeck, if it's a changing color Card, this
     * method makes it possible to change the colour of the card according to the
     * function choosColor.
     * The card will be removed of the player hand
     * 
     * @param card         : Designates the card we will play
     * @param handOfPlayer : Designates the player's hand
     */
    @Override
    public void playCard(Card card, Deque<Card> handOfPlayer) {
        Pile.addCardToGameDeck(card);
        handOfPlayer.remove(card);
        if (Card.isJoker(card) || Card.isTake4(card)) {
            chooseColor(card, handOfPlayer);
        }
        Card.valueOfCard(card);
    }

    // AUTRE METHODE PROPOSEE:

    /**
     * This method makes it possible to search special Card.
     * 
     * @param cardToFind   : Designates the card at the top of the GameDeck
     * @param handOfPlayer : Designates the player's hand
     * @return a special card except +4 and joker, or else, if there is no special
     *         card, returns null
     */
    @Override
    public Card searchisSpecialCard(Card cardToFind, Deque<Card> handOfPlayer) {
        for (Card currCard : handOfPlayer) {
            if (!Card.isTake4(currCard) && !Card.isJoker(currCard) && currCard.getColor().equals(cardToFind.getColor())
                    && Card.isSpecialCard(currCard)) {
                return currCard;

            }
        }
        return null;
    }

    /**
     * This method gives this hand (as string) to the provided player
     * 
     * @param cardToFind                                : Designates the card at the
     *                                                  top of the GameDeck
     * @param handOfPlayer                              : Designates the player's
     *                                                  hand
     * @param nbrOfCardWithSameColorOfCardFindWithColor : Designates the number of
     *                                                  cards of the same colour in
     *                                                  top GameDeck
     * @param cardFindWithColor
     * @return
     */
    public Card findTheBestCardToPutWithColor(Card cardToFind, Deque<Card> handOfPlayer,
            int nbrOfCardWithSameColorOfCardFindWithColor, Card cardFindWithColor) {
        Card specialCardWithSameColor = searchisSpecialCard(cardToFind, handOfPlayer);
        if (specialCardWithSameColor == null) {
            Card[] cardOfSameColor = searchSameColorsCard(cardFindWithColor.getColor(),
                    nbrOfCardWithSameColorOfCardFindWithColor, handOfPlayer);
            return Strateg.searchMaxValueCard(cardOfSameColor);
        }
        return specialCardWithSameColor;
    }

    /**
     * This method tries to find the best card to place between the card of the same
     * colour of the top GameDeck or ofthe same
     * value card of the top GameDeck (If there are no more cards of the same colour
     * of cardFindWithValue, this method returns the cardFindWithValue.
     * Otherwise, it will search the cards of the same colour for a special card and
     * return them.
     * In case there is no special card, this method will search for the highest
     * number and return it.
     * Finally, if there is no card of the same colour or value, it will return
     * null.)
     * 
     * @param cardToFind   : Designates the card at the top of the GameDeck
     * @param handOfPlayer : Designates the player's hand
     * @return the best card to put according to the number of the same color of
     *         card in the hand
     */
    @Override
    public Card searchTheBestCardToPutBetweenColorAndValue(Card cardToFind, Deque<Card> handOfPlayer) {
        Card cardFindWithColor = Strateg.findSameColorOfDeck(cardToFind, handOfPlayer);
        Card cardFindWithValue = Strateg.findSameValueOfDeck(cardToFind, handOfPlayer);
        if (cardFindWithColor != null && cardFindWithValue != null) {
            int nbrOfCardWithSameColorOfCardFindWithValue = findRepetitionColor(cardFindWithValue.getColor(),
                    handOfPlayer);
            int nbrOfCardWithSameColorOfCardFindWithColor = findRepetitionColor(cardFindWithColor.getColor(),
                    handOfPlayer);
            if (nbrOfCardWithSameColorOfCardFindWithValue > nbrOfCardWithSameColorOfCardFindWithColor) {
                return cardFindWithValue;
            } else {
                return findTheBestCardToPutWithColor(cardToFind, handOfPlayer,
                        nbrOfCardWithSameColorOfCardFindWithColor, cardFindWithColor);
            }
        } else if (cardFindWithColor != null) {
            int nbrOfCardWithSameColorOfCardFindWithColor = findRepetitionColor(cardFindWithColor.getColor(),
                    handOfPlayer);
            return findTheBestCardToPutWithColor(cardToFind, handOfPlayer, nbrOfCardWithSameColorOfCardFindWithColor,
                    cardFindWithColor);
        } else if (cardFindWithValue != null) {
            return cardFindWithValue;
        }
        return null;

    }

    /**
     * This method tries to find the best card to put down.
     * First it looks for cards of the same suit or value. If there are none, it
     * tries to find a colour-changing card.
     * Finally, if there is no colour-changing card, this method returns null.
     * 
     * @param cardToFind   : Designates the card at the top of the GameDeck
     * @param handOfPlayer : Designates the player's hand
     * @return return the best card to put on the GameDeck
     */
    @Override
    public Card searchTheBestCardToPut(Card cardToFind, Deque<Card> handOfPlayer) {
        Card cardWithSameColorOrSameValue = searchTheBestCardToPutBetweenColorAndValue(cardToFind, handOfPlayer);
        if (cardWithSameColorOrSameValue == null) {
            return Strateg.findCardToChangeColor(handOfPlayer);
        }
        return cardWithSameColorOrSameValue;

    }

}
