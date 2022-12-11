package fr.pantheonsorbonne.miage;

import org.junit.jupiter.api.Test;

import fr.pantheonsorbonne.miage.enums.CardColor;
import fr.pantheonsorbonne.miage.enums.CardValue;
import fr.pantheonsorbonne.miage.game.Card;
import fr.pantheonsorbonne.miage.game.Pile;
import fr.pantheonsorbonne.miage.strategie.Strateg;
import fr.pantheonsorbonne.miage.strategie.StrategieColor;
import fr.pantheonsorbonne.miage.strategie.StrategieValue;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Deque;
import java.util.LinkedList;

public class StrategieTest {

  @Test
  public void testStrategieValue() {
    Strateg value = new StrategieValue();
    Player[] listPlayer = new Player[2];
    listPlayer[0] = new Player("meryam", value);
    listPlayer[1] = new Player("amel", value);
    LocalUno game = new LocalUno(listPlayer);

    Card carte1 = new Card(CardColor.BLUE, CardValue.TWO, 2);
    Card carte2 = new Card(CardColor.GREEN, CardValue.FIVE, 5);
    Card carte3 = new Card(CardColor.RED, CardValue.NINE, 9);
    Card carte4 = new Card(CardColor.BLUE, CardValue.PLUSFOUR, 20);
    Card carte5 = new Card(CardColor.RED, CardValue.INTERDIT, 20);
    Card carteTopDeck = new Card(CardColor.RED, CardValue.ZERO, 0);

    listPlayer[1].getHand().add(carte1);
    listPlayer[1].getHand().add(carte2);
    listPlayer[1].getHand().add(carte3);
    listPlayer[1].getHand().add(carte4);
    listPlayer[1].getHand().add(carte5);

    Pile.setCardToTheTop(carteTopDeck);

    assertEquals(carte5, game.getCardFromPlayer(listPlayer[1].getName()));

  }

  @Test
  public void testStrategieValueWithPlusFour() {
    Strateg value = new StrategieValue();
    Player[] listPlayer = new Player[2];
    listPlayer[0] = new Player("meryam", value);
    listPlayer[1] = new Player("amel", value);
    LocalUno game = new LocalUno(listPlayer);

    Card carte1 = new Card(CardColor.BLUE, CardValue.TWO, 2);
    Card carte2 = new Card(CardColor.GREEN, CardValue.FIVE, 5);
    Card carte3 = new Card(CardColor.GREEN, CardValue.NINE, 9);
    Card carte4 = new Card(CardColor.BLUE, CardValue.PLUSFOUR, 20);
    Card carte5 = new Card(CardColor.GREEN, CardValue.INTERDIT, 20);
    Card carteTopDeck = new Card(CardColor.RED, CardValue.ZERO, 0);

    listPlayer[1].getHand().add(carte1);
    listPlayer[1].getHand().add(carte2);
    listPlayer[1].getHand().add(carte3);
    listPlayer[1].getHand().add(carte4);
    listPlayer[1].getHand().add(carte5);

    Pile.setCardToTheTop(carteTopDeck);

    assertEquals(carte4, game.getCardFromPlayer(listPlayer[1].getName()));

  }

  @Test
  public void testStrategieValueWithComparaison() {
    Strateg value = new StrategieValue();
    Player[] listPlayer = new Player[2];
    listPlayer[0] = new Player("meryam", value);
    listPlayer[1] = new Player("amel", value);
    LocalUno game = new LocalUno(listPlayer);

    Card carte1 = new Card(CardColor.BLUE, CardValue.TWO, 2);
    Card carte2 = new Card(CardColor.RED, CardValue.FIVE, 5);
    Card carte3 = new Card(CardColor.GREEN, CardValue.NINE, 9);
    Card carte4 = new Card(CardColor.RED, CardValue.PLUSFOUR, 20);
    Card carte5 = new Card(CardColor.GREEN, CardValue.INTERDIT, 20);
    Card carteTopDeck = new Card(CardColor.RED, CardValue.NINE, 9);

    listPlayer[1].getHand().add(carte1);
    listPlayer[1].getHand().add(carte2);
    listPlayer[1].getHand().add(carte3);
    listPlayer[1].getHand().add(carte4);
    listPlayer[1].getHand().add(carte5);

    Pile.setCardToTheTop(carteTopDeck);

    assertEquals(carte3, game.getCardFromPlayer(listPlayer[1].getName()));

  }

  @Test
  public void testStrategieValueWithPlayCard() {
    Strateg value = new StrategieValue();
    Player[] listPlayer = new Player[2];
    listPlayer[0] = new Player("meryam", value);
    listPlayer[1] = new Player("amel", value);
    LocalUno game = new LocalUno(listPlayer);

    Card carte1 = new Card(CardColor.BLUE, CardValue.TWO, 2);
    Card carte2 = new Card(CardColor.RED, CardValue.FIVE, 5);
    Card carte3 = new Card(CardColor.RED, CardValue.NINE, 9);
    Card carte4 = new Card(CardColor.RED, CardValue.PLUSFOUR, 50);
    Card carte5 = new Card(CardColor.GREEN, CardValue.JOKER, 50);
    Card carteTopDeck = new Card(CardColor.BLUE, CardValue.NINE, 9);

    listPlayer[1].getHand().add(carte1);
    listPlayer[1].getHand().add(carte2);
    listPlayer[1].getHand().add(carte3);
    listPlayer[1].getHand().add(carte4);
    listPlayer[1].getHand().add(carte5);

    Pile.setCardToTheTop(carteTopDeck);

    game.playCard(carte5, listPlayer[1].getName());

    Card carte = new Card(CardColor.RED, CardValue.JOKER, 50);

    assertEquals(Card.valueOfCard(carte), Pile.getCardTopDeckToString());

  }

  @Test
  public void testStrategieValueWithPlayCardWithFour() {
    Strateg value = new StrategieValue();
    Player[] listPlayer = new Player[2];
    listPlayer[0] = new Player("meryam", value);
    listPlayer[1] = new Player("amel", value);
    LocalUno game = new LocalUno(listPlayer);

    Card carte1 = new Card(CardColor.BLUE, CardValue.TWO, 2);
    Card carte2 = new Card(CardColor.RED, CardValue.FIVE, 5);
    Card carte3 = new Card(CardColor.RED, CardValue.NINE, 9);
    Card carte4 = new Card(CardColor.GREEN, CardValue.PLUSFOUR, 50);
    Card carte5 = new Card(CardColor.GREEN, CardValue.FIVE, 5);
    Card carteTopDeck = new Card(CardColor.BLUE, CardValue.NINE, 9);

    listPlayer[1].getHand().add(carte1);
    listPlayer[1].getHand().add(carte2);
    listPlayer[1].getHand().add(carte3);
    listPlayer[1].getHand().add(carte4);
    listPlayer[1].getHand().add(carte5);

    Pile.setCardToTheTop(carteTopDeck);

    game.playCard(carte4, listPlayer[1].getName());

    Card carte = new Card(CardColor.RED, CardValue.PLUSFOUR, 50);

    assertEquals(Card.valueOfCard(carte), Pile.getCardTopDeckToString());

  }

  @Test
  public void testSearchMaxValueCard() {

    Card[] cardsInHandOfSameColor = {
        new Card(CardColor.BLUE, CardValue.FIVE, 5),
        new Card(CardColor.BLUE, CardValue.TWO, 2),
        new Card(CardColor.BLUE, CardValue.NINE, 9),
    };
    assertEquals(cardsInHandOfSameColor[2], Strateg.searchMaxValueCard(cardsInHandOfSameColor));
  }

  @Test
  public void testFindSameColorOfDeck() {
    Card cardToFind = new Card(CardColor.BLUE, CardValue.FIVE, 5);
    Deque<Card> handOfPlayers = new LinkedList<Card>();
    Card carte1 = new Card(CardColor.BLUE, CardValue.TWO, 2);
    Card carte2 = new Card(CardColor.GREEN, CardValue.FIVE, 5);
    handOfPlayers.add(carte1);
    handOfPlayers.add(carte2);
    assertEquals(carte1, Strateg.findSameColorOfDeck(cardToFind, handOfPlayers));
  }

  @Test
  public void testNoFoundSameColorOfDeck() {
    Card cardToFind = new Card(CardColor.BLUE, CardValue.TWO, 2);
    Deque<Card> handOfPlayers = new LinkedList<Card>();
    Card carte1 = new Card(CardColor.RED, CardValue.TWO, 2);
    Card carte2 = new Card(CardColor.RED, CardValue.TWO, 2);
    Card carte3 = new Card(CardColor.GREEN, CardValue.TWO, 2);
    handOfPlayers.add(carte1);
    handOfPlayers.add(carte2);
    handOfPlayers.add(carte3);
    assertNull(Strateg.findSameColorOfDeck(cardToFind, handOfPlayers));
  }

  @Test
  public void testFindSameValueOfDeck() {
    Card cardToFind = new Card(CardColor.BLUE, CardValue.FIVE, 5);
    Deque<Card> handOfPlayers = new LinkedList<Card>();
    Card carte1 = new Card(CardColor.BLUE, CardValue.TWO, 2);
    Card carte2 = new Card(CardColor.GREEN, CardValue.FIVE, 5);
    handOfPlayers.add(carte1);
    handOfPlayers.add(carte2);
    assertEquals(carte2, Strateg.findSameValueOfDeck(cardToFind, handOfPlayers));
  }

  @Test
  public void testNoFoundSameValueOfDeck() {
    Card cardToFind = new Card(CardColor.BLUE, CardValue.TWO, 2);
    Deque<Card> handOfPlayers = new LinkedList<Card>();
    Card carte1 = new Card(CardColor.RED, CardValue.ONE, 1);
    Card carte2 = new Card(CardColor.RED, CardValue.NINE, 9);
    Card carte3 = new Card(CardColor.GREEN, CardValue.NINE, 9);
    handOfPlayers.add(carte1);
    handOfPlayers.add(carte2);
    handOfPlayers.add(carte3);
    assertNull(Strateg.findSameValueOfDeck(cardToFind, handOfPlayers));
  }

  @Test
  public void testFindNumberOfCardSameColor() {
    Strateg value = new StrategieValue();
    CardColor color = CardColor.BLUE;
    Deque<Card> handOfPlayers = new LinkedList<Card>();
    Card carte1 = new Card(CardColor.BLUE, CardValue.TWO, 2);
    Card carte2 = new Card(CardColor.GREEN, CardValue.FIVE, 5);
    Card carte3 = new Card(CardColor.RED, CardValue.NINE, 9);
    Card carte4 = new Card(CardColor.BLUE, CardValue.NINE, 9);
    Card carte5 = new Card(CardColor.BLUE, CardValue.ONE, 1);
    handOfPlayers.add(carte1);
    handOfPlayers.add(carte2);
    handOfPlayers.add(carte3);
    handOfPlayers.add(carte4);
    handOfPlayers.add(carte5);
    assertEquals(3, value.findRepetitionColor(color, handOfPlayers));
    assertNotEquals(5, value.findRepetitionColor(color, handOfPlayers));
  }

  @Test
  public void testFindMostPresentColor() {
    Strateg value = new StrategieValue();
    Deque<Card> handOfPlayers = new LinkedList<Card>();
    Card carte1 = new Card(CardColor.BLUE, CardValue.TWO, 2);
    Card carte2 = new Card(CardColor.GREEN, CardValue.FIVE, 5);
    Card carte3 = new Card(CardColor.RED, CardValue.NINE, 9);
    Card carte4 = new Card(CardColor.BLUE, CardValue.NINE, 9);
    Card carte5 = new Card(CardColor.BLUE, CardValue.ONE, 1);
    handOfPlayers.add(carte1);
    handOfPlayers.add(carte2);
    handOfPlayers.add(carte3);
    handOfPlayers.add(carte4);
    handOfPlayers.add(carte5);
    CardColor colorFind = CardColor.BLUE;
    CardColor colorFindFalse = CardColor.RED;
    assertEquals(colorFind, value.findMostPresentColor(handOfPlayers));
    assertNotEquals(colorFindFalse, value.findMostPresentColor(handOfPlayers));
  }

  @Test
  public void testForChooseColorIfIsEmpty() {
    Strateg value = new StrategieValue();
    Deque<Card> handOfPlayer = new LinkedList<Card>();
    Card card = new Card(CardColor.YELLOW, CardValue.PLUSFOUR, 50);
    assertEquals(card, value.chooseColor(card, handOfPlayer));
  }

  @Test
  public void testForChooseColor() {
    Strateg value = new StrategieValue();
    Deque<Card> handOfPlayers = new LinkedList<Card>();
    Card carte1 = new Card(CardColor.BLUE, CardValue.TWO, 2);
    Card carte2 = new Card(CardColor.GREEN, CardValue.FIVE, 5);
    Card carte3 = new Card(CardColor.RED, CardValue.JOKER, 50);
    Card carte4 = new Card(CardColor.BLUE, CardValue.NINE, 9);
    Card carte5 = new Card(CardColor.BLUE, CardValue.ONE, 1);
    handOfPlayers.add(carte1);
    handOfPlayers.add(carte2);
    handOfPlayers.add(carte3);
    handOfPlayers.add(carte4);
    handOfPlayers.add(carte5);
    Card card = new Card(CardColor.BLUE, CardValue.JOKER, 50);
    assertEquals(card, value.chooseColor(card, handOfPlayers));
  }

  @Test
  public void testFindCardToChangeColor() {
    Deque<Card> handOfPlayers = new LinkedList<Card>();
    Card carte1 = new Card(CardColor.BLUE, CardValue.TWO, 2);
    Card carte2 = new Card(CardColor.GREEN, CardValue.PLUSFOUR, 50);
    Card carte3 = new Card(CardColor.RED, CardValue.NINE, 9);
    handOfPlayers.add(carte1);
    handOfPlayers.add(carte2);
    handOfPlayers.add(carte3);
    Deque<Card> handOfPlayers2 = new LinkedList<Card>();
    Card carte4 = new Card(CardColor.BLUE, CardValue.JOKER, 50);
    Card carte5 = new Card(CardColor.GREEN, CardValue.NINE, 9);
    handOfPlayers2.add(carte4);
    handOfPlayers2.add(carte5);
    assertEquals(carte2, Strateg.findCardToChangeColor(handOfPlayers));
    assertEquals(carte4, Strateg.findCardToChangeColor(handOfPlayers2));
    assertNotEquals(carte1, Strateg.findCardToChangeColor(handOfPlayers));
  }

  @Test
  public void testNoFoundCardToChangeColor() {
    Deque<Card> handOfPlayers = new LinkedList<Card>();
    Card carte1 = new Card(CardColor.BLUE, CardValue.TWO, 2);
    Card carte2 = new Card(CardColor.GREEN, CardValue.FIVE, 5);
    Card carte3 = new Card(CardColor.RED, CardValue.NINE, 9);
    handOfPlayers.add(carte1);
    handOfPlayers.add(carte2);
    handOfPlayers.add(carte3);
    assertNull(Strateg.findCardToChangeColor(handOfPlayers));
  }

  @Test
  public void testFindPlusFour() {
    Deque<Card> handOfPlayers = new LinkedList<Card>();
    Card carte1 = new Card(CardColor.BLUE, CardValue.TWO, 2);
    Card carte2 = new Card(CardColor.GREEN, CardValue.PLUSFOUR, 50);
    Card carte3 = new Card(CardColor.RED, CardValue.NINE, 9);
    handOfPlayers.add(carte1);
    handOfPlayers.add(carte2);
    handOfPlayers.add(carte3);
    assertEquals(carte2, Strateg.findPlusFour(handOfPlayers));
  }

  @Test
  public void testNoFoundPlusFour() {
    Deque<Card> handOfPlayers = new LinkedList<Card>();
    Card carte1 = new Card(CardColor.BLUE, CardValue.TWO, 2);
    Card carte2 = new Card(CardColor.GREEN, CardValue.FIVE, 5);
    Card carte3 = new Card(CardColor.RED, CardValue.NINE, 9);
    handOfPlayers.add(carte1);
    handOfPlayers.add(carte2);
    handOfPlayers.add(carte3);
    assertNull(Strateg.findPlusFour(handOfPlayers));
  }

  @Test
  public void testSearchTheBestCardToPutBetweenColorAndValue() {
    Strateg color = new StrategieColor();
    Deque<Card> handOfPlayers = new LinkedList<Card>();
    Card carte1 = new Card(CardColor.GREEN, CardValue.ONE, 1);
    Card carte2 = new Card(CardColor.GREEN, CardValue.TWO, 2);
    Card carte3 = new Card(CardColor.RED, CardValue.NINE, 9);
    Card carte4 = new Card(CardColor.RED, CardValue.THREE, 3);
    Card carte5 = new Card(CardColor.RED, CardValue.FOUR, 4);
    Card carte6 = new Card(CardColor.RED, CardValue.NINE, 9);
    Card cardTopDeck = new Card(CardColor.GREEN, CardValue.THREE, 3);
    handOfPlayers.add(carte1);
    handOfPlayers.add(carte2);
    handOfPlayers.add(carte3);
    handOfPlayers.add(carte4);
    handOfPlayers.add(carte5);
    handOfPlayers.add(carte6);

    assertEquals(Card.valueOfCard(carte4),
        Card.valueOfCard(color.searchTheBestCardToPutBetweenColorAndValue(cardTopDeck, handOfPlayers)));
  }

  @Test
  public void testSearchTheBestCardToPutBetweenColorAndValueForStrategValue() {
    Strateg value = new StrategieValue();
    Deque<Card> handOfPlayers = new LinkedList<Card>();
    Card carte1 = new Card(CardColor.GREEN, CardValue.ONE, 1);
    Card carte2 = new Card(CardColor.BLUE, CardValue.INTERDIT, 20);
    Card carte3 = new Card(CardColor.RED, CardValue.NINE, 9);
    Card carte4 = new Card(CardColor.RED, CardValue.THREE, 3);
    Card carte5 = new Card(CardColor.RED, CardValue.FOUR, 4);
    Card carte6 = new Card(CardColor.RED, CardValue.NINE, 9);
    Card cardTopDeck = new Card(CardColor.GREEN, CardValue.INTERDIT, 20);
    handOfPlayers.add(carte1);
    handOfPlayers.add(carte2);
    handOfPlayers.add(carte3);
    handOfPlayers.add(carte4);
    handOfPlayers.add(carte5);
    handOfPlayers.add(carte6);

    assertEquals(Card.valueOfCard(carte2),
        Card.valueOfCard(value.searchTheBestCardToPutBetweenColorAndValue(cardTopDeck, handOfPlayers)));
  }

  @Test
  public void testSearchTheBestCardToPutBetweenColorAndValueForStrategValueFalse() {
    Strateg value = new StrategieValue();
    Deque<Card> handOfPlayers = new LinkedList<Card>();
    Card carte1 = new Card(CardColor.GREEN, CardValue.ONE, 1);
    Card carte2 = new Card(CardColor.GREEN, CardValue.TWO, 2);
    Card carte3 = new Card(CardColor.RED, CardValue.NINE, 9);
    Card carte4 = new Card(CardColor.RED, CardValue.THREE, 3);
    Card carte5 = new Card(CardColor.RED, CardValue.FOUR, 4);
    Card carte6 = new Card(CardColor.RED, CardValue.NINE, 9);
    Card cardTopDeck = new Card(CardColor.BLUE, CardValue.INTERDIT, 20);
    handOfPlayers.add(carte1);
    handOfPlayers.add(carte2);
    handOfPlayers.add(carte3);
    handOfPlayers.add(carte4);
    handOfPlayers.add(carte5);
    handOfPlayers.add(carte6);

    assertNull(value.searchTheBestCardToPutBetweenColorAndValue(cardTopDeck, handOfPlayers));
  }

  @Test
  public void testSearchisSpecialCard() {
    Strateg value = new StrategieValue();
    Deque<Card> handOfPlayers = new LinkedList<Card>();
    Card carte1 = new Card(CardColor.GREEN, CardValue.ONE, 1);
    Card carte2 = new Card(CardColor.BLUE, CardValue.INTERDIT, 20);
    Card carte3 = new Card(CardColor.RED, CardValue.NINE, 9);
    Card carte4 = new Card(CardColor.RED, CardValue.THREE, 3);
    Card carte5 = new Card(CardColor.RED, CardValue.FOUR, 4);
    Card carte6 = new Card(CardColor.RED, CardValue.NINE, 9);
    Card cardTopDeck = new Card(CardColor.GREEN, CardValue.INTERDIT, 20);
    handOfPlayers.add(carte1);
    handOfPlayers.add(carte2);
    handOfPlayers.add(carte3);
    handOfPlayers.add(carte4);
    handOfPlayers.add(carte5);
    handOfPlayers.add(carte6);
    assertEquals(Card.valueOfCard(carte2), Card.valueOfCard(value.searchisSpecialCard(cardTopDeck, handOfPlayers)));
  }

  @Test
  public void testPlayCardStrategieValue() {
    Strateg value = new StrategieValue();

    Deque<Card> handOfPlayers = new LinkedList<Card>();
    Card carte1 = new Card(CardColor.GREEN, CardValue.JOKER, 50);
    Card carte2 = new Card(CardColor.BLUE, CardValue.INTERDIT, 20);
    Card carte3 = new Card(CardColor.RED, CardValue.NINE, 9);
    Card carte4 = new Card(CardColor.RED, CardValue.THREE, 3);
    Card carte5 = new Card(CardColor.RED, CardValue.FOUR, 4);
    Card carte6 = new Card(CardColor.RED, CardValue.NINE, 9);

    handOfPlayers.add(carte1);
    handOfPlayers.add(carte2);
    handOfPlayers.add(carte3);
    handOfPlayers.add(carte4);
    handOfPlayers.add(carte5);
    handOfPlayers.add(carte6);
    value.playCard(carte1, handOfPlayers);
    Card carteFound = new Card(CardColor.RED, CardValue.JOKER, 50);
    assertEquals(Card.valueOfCard(carteFound), Pile.getCardTopDeckToString());

  }

  @Test
  public void testPlayCardsStrategieValue() {
    Strateg value = new StrategieValue();

    Deque<Card> handOfPlayers = new LinkedList<Card>();
    Card carte1 = new Card(CardColor.GREEN, CardValue.JOKER, 50);
    Card carte2 = new Card(CardColor.BLUE, CardValue.INTERDIT, 20);
    Card carte3 = new Card(CardColor.RED, CardValue.NINE, 9);
    Card carte4 = new Card(CardColor.RED, CardValue.THREE, 3);
    Card carte5 = new Card(CardColor.RED, CardValue.FOUR, 4);
    Card carte6 = new Card(CardColor.RED, CardValue.NINE, 9);
    Pile.setCardToTheTop(carte6);
    handOfPlayers.add(carte1);
    handOfPlayers.add(carte2);
    handOfPlayers.add(carte3);
    handOfPlayers.add(carte4);
    handOfPlayers.add(carte5);
    handOfPlayers.add(carte6);
    value.playCard(carte6, handOfPlayers);
    Card carteFound = new Card(CardColor.RED, CardValue.NINE, 9);
    assertEquals(Card.valueOfCard(carteFound), Pile.getCardTopDeckToString());

  }

  @Test
  public void testPlayCardStrategieColor() {

    Strateg color = new StrategieColor();
    Deque<Card> handOfPlayers = new LinkedList<Card>();
    Card carte1 = new Card(CardColor.GREEN, CardValue.JOKER, 50);
    Card carte2 = new Card(CardColor.BLUE, CardValue.INTERDIT, 20);
    Card carte3 = new Card(CardColor.RED, CardValue.NINE, 9);
    Card carte4 = new Card(CardColor.RED, CardValue.THREE, 3);
    Card carte5 = new Card(CardColor.RED, CardValue.FOUR, 4);
    Card carte6 = new Card(CardColor.RED, CardValue.NINE, 9);

    handOfPlayers.add(carte1);
    handOfPlayers.add(carte2);
    handOfPlayers.add(carte3);
    handOfPlayers.add(carte4);
    handOfPlayers.add(carte5);
    handOfPlayers.add(carte6);
    color.playCard(carte1, handOfPlayers);
    Card carteFound = new Card(CardColor.RED, CardValue.JOKER, 50);
    assertEquals(Card.valueOfCard(carteFound), Pile.getCardTopDeckToString());

  }

  @Test
  public void testPlayCardStrategieColorWithPlusFour() {

    Strateg color = new StrategieColor();
    Deque<Card> handOfPlayers = new LinkedList<Card>();
    Card carte1 = new Card(CardColor.GREEN, CardValue.PLUSFOUR, 50);
    Card carte2 = new Card(CardColor.BLUE, CardValue.INTERDIT, 20);
    Card carte3 = new Card(CardColor.RED, CardValue.NINE, 9);
    Card carte4 = new Card(CardColor.RED, CardValue.THREE, 3);
    Card carte5 = new Card(CardColor.RED, CardValue.FOUR, 4);
    Card carte6 = new Card(CardColor.RED, CardValue.NINE, 9);

    handOfPlayers.add(carte1);
    handOfPlayers.add(carte2);
    handOfPlayers.add(carte3);
    handOfPlayers.add(carte4);
    handOfPlayers.add(carte5);
    handOfPlayers.add(carte6);
    color.playCard(carte1, handOfPlayers);
    Card carteFound = new Card(CardColor.RED, CardValue.PLUSFOUR, 50);
    assertEquals(Card.valueOfCard(carteFound), Pile.getCardTopDeckToString());

  }

  @Test
  public void testSearchSameColorCards() {
    Strateg colorr = new StrategieColor();
    CardColor color = CardColor.BLUE;
    int nbrOfSameColorCard = 2;
    Deque<Card> hand = new LinkedList<>();
    Card carte1 = new Card(CardColor.BLUE, CardValue.TWO, 2);
    Card carte2 = new Card(CardColor.BLUE, CardValue.FIVE, 5);
    Card carte3 = new Card(CardColor.RED, CardValue.NINE, 9);
    hand.add(carte1);
    hand.add(carte2);
    hand.add(carte3);
    Card[] cardsFind = { carte1, carte2 };
    assertEquals(cardsFind.length, colorr.searchSameColorsCard(color, nbrOfSameColorCard, hand).length);
  }

  @Test
  public void testSearchSpecialCard() {
    Strateg value = new StrategieValue();
    Card cardToFind = new Card(CardColor.BLUE, CardValue.JOKER, 50);
    Deque<Card> hand = new LinkedList<>();
    Card carte1 = new Card(CardColor.BLUE, CardValue.JOKER, 50);
    Card carte2 = new Card(CardColor.BLUE, CardValue.FIVE, 5);
    Card carte3 = new Card(CardColor.RED, CardValue.NINE, 9);
    hand.add(carte1);
    hand.add(carte2);
    hand.add(carte3);
    assertEquals(carte1, value.searchisSpecialCard(cardToFind, hand));

    Card cardToFind2 = new Card(CardColor.RED, CardValue.JOKER, 50);
    Deque<Card> hand2 = new LinkedList<>();
    Card carte4 = new Card(CardColor.BLUE, CardValue.TWO, 2);
    Card carte5 = new Card(CardColor.BLUE, CardValue.FIVE, 5);
    Card carte6 = new Card(CardColor.RED, CardValue.INVERSE, 20);
    hand2.add(carte4);
    hand2.add(carte5);
    hand2.add(carte6);
    assertEquals(carte6, value.searchisSpecialCard(cardToFind2, hand2));

    Card cardToFind3 = new Card(CardColor.RED, CardValue.INVERSE, 50);
    Deque<Card> hand3 = new LinkedList<>();
    Card carte7 = new Card(CardColor.BLUE, CardValue.TWO, 2);
    Card carte8 = new Card(CardColor.BLUE, CardValue.FIVE, 5);
    Card carte9 = new Card(CardColor.GREEN, CardValue.INVERSE, 20);
    hand3.add(carte7);
    hand3.add(carte8);
    hand3.add(carte9);
    assertEquals(carte9, value.searchisSpecialCard(cardToFind3, hand3));

    Card cardToFind4 = new Card(CardColor.RED, CardValue.INVERSE, 50);
    Deque<Card> hand4 = new LinkedList<>();
    Card carte10 = new Card(CardColor.BLUE, CardValue.TWO, 2);
    Card carte11 = new Card(CardColor.BLUE, CardValue.FIVE, 5);
    Card carte12 = new Card(CardColor.GREEN, CardValue.NINE, 9);
    hand4.add(carte10);
    hand4.add(carte11);
    hand4.add(carte12);
    assertNull(value.searchisSpecialCard(cardToFind4, hand4));

    Card cardToFind5 = new Card(CardColor.RED, CardValue.PLUSTWO, 50);
    Deque<Card> hand5 = new LinkedList<>();
    Card carte13 = new Card(CardColor.BLUE, CardValue.TWO, 2);
    Card carte14 = new Card(CardColor.BLUE, CardValue.NINE, 9);
    Card carte15 = new Card(CardColor.RED, CardValue.FOUR, 4);
    hand5.add(carte13);
    hand5.add(carte14);
    hand5.add(carte15);
    assertNull(value.searchisSpecialCard(cardToFind5, hand5));

    Card cardToFind6 = new Card(CardColor.RED, CardValue.PLUSTWO, 50);
    Deque<Card> hand6 = new LinkedList<>();
    Card carte16 = new Card(CardColor.BLUE, CardValue.TWO, 2);
    Card carte17 = new Card(CardColor.BLUE, CardValue.NINE, 9);
    Card carte18 = new Card(CardColor.RED, CardValue.PLUSFOUR, 50);
    hand6.add(carte16);
    hand6.add(carte17);
    hand6.add(carte18);
    assertNull(value.searchisSpecialCard(cardToFind6, hand6));

    Card cardToFind7 = new Card(CardColor.RED, CardValue.TWO, 50);
    Deque<Card> hand7 = new LinkedList<>();
    Card carte19 = new Card(CardColor.BLUE, CardValue.TWO, 2);
    Card carte20 = new Card(CardColor.BLUE, CardValue.NINE, 9);
    Card carte21 = new Card(CardColor.BLUE, CardValue.TWO, 4);
    hand7.add(carte19);
    hand7.add(carte20);
    hand7.add(carte21);
    assertNull(value.searchisSpecialCard(cardToFind7, hand7));

    Card cardToFind8 = new Card(CardColor.RED, CardValue.PLUSFOUR, 50);
    Deque<Card> hand8 = new LinkedList<>();
    Card carte22 = new Card(CardColor.BLUE, CardValue.TWO, 2);
    Card carte23 = new Card(CardColor.BLUE, CardValue.NINE, 9);
    Card carte24 = new Card(CardColor.BLUE, CardValue.PLUSFOUR, 50);
    hand8.add(carte22);
    hand8.add(carte23);
    hand8.add(carte24);
    assertNull(value.searchisSpecialCard(cardToFind8, hand8));
  }

  @Test
  public void testFindTheBestCardToPut() {
    Strateg value = new StrategieValue();
    Card cardToFind = new Card(CardColor.BLUE, CardValue.TWO, 2);
    Card cardToCompare = new Card(CardColor.BLUE, CardValue.NINE, 9);
    Deque<Card> hand = new LinkedList<>();
    Card carte1 = new Card(CardColor.BLUE, CardValue.JOKER, 50);
    Card carte2 = new Card(CardColor.BLUE, CardValue.FIVE, 5);
    Card carte3 = new Card(CardColor.RED, CardValue.NINE, 9);
    hand.add(carte1);
    hand.add(carte2);
    hand.add(carte3);
    assertEquals(cardToCompare, ((StrategieValue) value).findTheBestCardToPut(cardToFind, cardToCompare, hand));

    Card cardToFind2 = new Card(CardColor.BLUE, CardValue.NINE, 9);
    Card cardToCompare2 = new Card(CardColor.BLUE, CardValue.TWO, 2);
    Card cardFind = Strateg.findSameValueOfDeck(cardToFind2, hand);
    assertEquals(cardFind, ((StrategieValue) value).findTheBestCardToPut(cardToFind2, cardToCompare2, hand));

    Card cardToFind3 = new Card(CardColor.BLUE, CardValue.TWO, 2);
    Card cardToCompare3 = new Card(CardColor.BLUE, CardValue.NINE, 9);
    assertEquals(cardToCompare3, ((StrategieValue) value).findTheBestCardToPut(cardToFind3, cardToCompare3, hand));
  }

  @Test
  public void testSearchTheBestCardToPutBetweenColorAndValuee() {
    Strateg value = new StrategieValue();
    Card cardToFind = new Card(CardColor.BLUE, CardValue.TWO, 2);
    Deque<Card> hand = new LinkedList<>();
    Card carte1 = new Card(CardColor.GREEN, CardValue.ONE, 1);
    Card carte2 = new Card(CardColor.GREEN, CardValue.FIVE, 5);
    Card carte3 = new Card(CardColor.RED, CardValue.NINE, 9);
    hand.add(carte1);
    hand.add(carte2);
    hand.add(carte3);
    assertNull(value.searchTheBestCardToPutBetweenColorAndValue(cardToFind, hand));

    Card cardToFind2 = new Card(CardColor.BLUE, CardValue.ONE, 1);
    Card cardFind = Strateg.findSameValueOfDeck(cardToFind2, hand);
    assertEquals(cardFind, value.searchTheBestCardToPutBetweenColorAndValue(cardToFind2, hand));

    Card cardToFind3 = new Card(CardColor.RED, CardValue.TWO, 2);
    Card cardFind2 = Strateg.findSameColorOfDeck(cardToFind3, hand);
    assertEquals(cardFind2, value.searchTheBestCardToPutBetweenColorAndValue(cardToFind3, hand));

    Deque<Card> hand2 = new LinkedList<>();
    Card carte4 = new Card(CardColor.BLUE, CardValue.TWO, 2);
    Card carte5 = new Card(CardColor.BLUE, CardValue.FIVE, 5);
    Card carte6 = new Card(CardColor.RED, CardValue.JOKER, 20);
    hand2.add(carte4);
    hand2.add(carte5);
    hand2.add(carte6);
    Card cardToFind4 = new Card(CardColor.GREEN, CardValue.NINE, 9);
    Card cardFind3 = Strateg.findCardToChangeColor(hand2);
    assertEquals(cardFind3, value.searchTheBestCardToPutBetweenColorAndValue(cardToFind4, hand2));
  }

  @Test
  public void testSearchTheBestCardToPut() {
    Strateg value = new StrategieValue();
    Card cardToFind = new Card(CardColor.BLUE, CardValue.JOKER, 50);
    Deque<Card> hand = new LinkedList<>();
    Card carte1 = new Card(CardColor.BLUE, CardValue.JOKER, 50);
    Card carte2 = new Card(CardColor.BLUE, CardValue.FIVE, 5);
    Card carte3 = new Card(CardColor.RED, CardValue.NINE, 9);
    hand.add(carte1);
    hand.add(carte2);
    hand.add(carte3);
    Card cardFind = value.searchisSpecialCard(cardToFind, hand);
    assertEquals(cardFind, value.searchTheBestCardToPut(cardToFind, hand));

    Card cardToFind4 = new Card(CardColor.RED, CardValue.INVERSE, 50);
    Deque<Card> hand4 = new LinkedList<>();
    Card carte10 = new Card(CardColor.RED, CardValue.TWO, 2);
    Card carte11 = new Card(CardColor.RED, CardValue.FIVE, 5); // celle la
    Card carte12 = new Card(CardColor.GREEN, CardValue.NINE, 9);
    hand4.add(carte10);
    hand4.add(carte11);
    hand4.add(carte12);
    assertEquals(carte11, value.searchTheBestCardToPut(cardToFind4, hand4));

    Card cardToFind5 = new Card(CardColor.RED, CardValue.PLUSTWO, 50);
    Deque<Card> hand5 = new LinkedList<>();
    Card carte13 = new Card(CardColor.BLUE, CardValue.TWO, 2);
    Card carte14 = new Card(CardColor.BLUE, CardValue.NINE, 9);
    Card carte15 = new Card(CardColor.GREEN, CardValue.FOUR, 4);
    hand5.add(carte13);
    hand5.add(carte14);
    hand5.add(carte15);
    assertNull(value.searchTheBestCardToPut(cardToFind5, hand5));

    Deque<Card> hand7 = new LinkedList<>();

    Card carte19 = new Card(CardColor.BLUE, CardValue.TWO, 2);
    Card carte20 = new Card(CardColor.BLUE, CardValue.NINE, 9);
    Card carte21 = new Card(CardColor.GREEN, CardValue.PLUSFOUR, 4);
    hand7.add(carte19);
    hand7.add(carte20);
    hand7.add(carte21);
    assertEquals(carte21, value.searchTheBestCardToPut(cardToFind5, hand7));

  }

  @Test
  public void testStrategColorsearchTheBestCardToPutBetweenColorAndValue() {
    Strateg color = new StrategieColor();
    Deque<Card> hand = new LinkedList<>();

    Card carte1 = new Card(CardColor.BLUE, CardValue.FIVE, 5);
    Card carte2 = new Card(CardColor.RED, CardValue.FOUR, 4);
    Card carteTopDeck = new Card(CardColor.GREEN, CardValue.FIVE, 5);
    Pile.setCardToTheTop(carteTopDeck);
    hand.add(carte1);
    hand.add(carte2);

    assertEquals(carte1, color.searchTheBestCardToPutBetweenColorAndValue(carteTopDeck, hand));
  }

  @Test
  public void playCardsWithValue() {
    Strateg value = new StrategieValue();
    Deque<Card> hand = new LinkedList<>();

    Card carte1 = new Card(CardColor.BLUE, CardValue.FIVE, 5);
    Card carte2 = new Card(CardColor.RED, CardValue.SIX, 6);
    Card carteTopDeck = new Card(CardColor.RED, CardValue.FIVE, 5);
    Pile.setCardToTheTop(carteTopDeck);
    hand.add(carte1);
    hand.add(carte2);
    assertEquals(carte2, ((StrategieValue) value).findTheBestCardToPut(carteTopDeck, carte2, hand));
  }

  @Test
  public void searchTheBestCardToPutWithZero() {
    Strateg color = new StrategieColor();
    Deque<Card> hand = new LinkedList<>();

    Card carte1 = new Card(CardColor.BLUE, CardValue.ZERO, 0);
    Card carte2 = new Card(CardColor.BLUE, CardValue.SIX, 6);
    Pile.setCardToTheTop(carte2);
    hand.add(carte1);
    Card[] x = new Card[1];
    x[0] = carte1;

    assertEquals(1, color.findRepetitionColor(CardColor.BLUE, hand));
    assertEquals(Card.valueOfCard(carte1), Card.valueOfCard(Strateg.searchMaxValueCard(x)));
    assertEquals(carte1, ((StrategieColor) color).searchTheBestCardToPutBetweenColorAndValue(carte2, hand));
  }

}
