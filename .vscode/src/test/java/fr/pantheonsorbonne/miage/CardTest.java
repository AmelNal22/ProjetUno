package fr.pantheonsorbonne.miage;

import org.junit.jupiter.api.Test;

import fr.pantheonsorbonne.miage.enums.CardColor;
import fr.pantheonsorbonne.miage.enums.CardValue;
import fr.pantheonsorbonne.miage.game.Card;

import static org.junit.jupiter.api.Assertions.*;

public class CardTest {

    @Test
    public void testGetStringToCards() {

        String carte = "\nB 7\nR interdit\nB joker\n";
        Card[] cardToFind = new Card[3];
        cardToFind[0] = new Card(CardColor.BLUE, CardValue.SEVEN, 7);
        cardToFind[1] = new Card(CardColor.RED, CardValue.INTERDIT, 20);
        cardToFind[2] = new Card(CardColor.BLUE, CardValue.JOKER, 50);
        assertEquals(Card.cardsToString(cardToFind), Card.cardsToString(Card.getStringToCards(carte)));
    }

    @Test
    public void testValueOfCard() {
        Card card = new Card(CardColor.BLUE, CardValue.TWO, 2);
        assertEquals("B 2", Card.valueOfCard(card));
    }

    @Test
    public void testCardsToString() {
        Card carte1 = new Card(CardColor.BLUE, CardValue.TWO, 2);
        Card carte2 = new Card(CardColor.GREEN, CardValue.PLUSFOUR, 50);
        Card carte3 = new Card(CardColor.RED, CardValue.NINE, 9);
        Card[] cards = { carte1, carte2, carte3 };
        assertEquals("\nB 2\nG +4\nR 9\n", Card.cardsToString(cards));
    }

    @Test
    public void testIfIsInverse() {
        Card card = new Card(CardColor.BLUE, CardValue.INVERSE, 20);
        assertTrue(Card.isInverse(card));
        Card card2 = new Card(CardColor.BLUE, CardValue.TWO, 2);
        assertFalse(Card.isInverse(card2));
    }

    @Test
    public void testIfIsInterdit() {
        Card card = new Card(CardColor.BLUE, CardValue.INTERDIT, 20);
        assertTrue(Card.isInterdit(card));
        Card card2 = new Card(CardColor.BLUE, CardValue.TWO, 2);
        assertFalse(Card.isInterdit(card2));
    }

    @Test
    public void testIfIsTake2() {
        Card card = new Card(CardColor.BLUE, CardValue.PLUSTWO, 20);
        assertTrue(Card.isTake2(card));
        Card card2 = new Card(CardColor.BLUE, CardValue.TWO, 2);
        assertFalse(Card.isTake2(card2));
    }

    @Test
    public void testIfIsChangingColor() {
        CardValue value = CardValue.JOKER;
        assertTrue(Card.isChangingColor(value));
        CardValue value2 = CardValue.PLUSFOUR;
        assertTrue(Card.isChangingColor(value2));
        CardValue value3 = CardValue.THREE;
        assertFalse(Card.isChangingColor(value3));
    }
}
