package fr.pantheonsorbonne.miage;

import org.junit.jupiter.api.Test;

import fr.pantheonsorbonne.miage.enums.CardColor;
import fr.pantheonsorbonne.miage.enums.CardValue;
import fr.pantheonsorbonne.miage.game.Card;
import fr.pantheonsorbonne.miage.game.Pile;

import static org.junit.jupiter.api.Assertions.*;

public class PileTest {

    @Test
    public void testAddCardToGameDeck() {
        Card cardTopDeck = new Card(CardColor.BLUE, CardValue.FIVE, 5);
        Pile.setCardToTheTop(cardTopDeck);
        Card cardToPut = new Card(CardColor.GREEN, CardValue.FOUR, 4);
        assertThrows(IllegalArgumentException.class, () -> {
            Pile.addCardToGameDeck(cardToPut);
        });
    }
}
