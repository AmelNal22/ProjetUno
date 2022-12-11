package fr.pantheonsorbonne.miage;

import org.junit.jupiter.api.Test;

import fr.pantheonsorbonne.miage.enums.CardColor;
import fr.pantheonsorbonne.miage.enums.CardValue;
import fr.pantheonsorbonne.miage.game.Card;

import fr.pantheonsorbonne.miage.game.Pile;
import fr.pantheonsorbonne.miage.strategie.Strateg;
import fr.pantheonsorbonne.miage.strategie.StrategieValue;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    @Test
    public void testCardsToRemove() {
        Strateg value = new StrategieValue();
        Player player = new Player("meryam", value);
        player.setHand();
        player.removeHandCard(player.getHand().getFirst());

        assertEquals(6, player.getHand().size());
    }

    @Test
    public void playerDrawCardTest() {
        Strateg value = new StrategieValue();
        Player player = new Player("meryam", value);
        player.setHand();
        Card cartToFind = new Card(CardColor.BLUE, CardValue.FIVE, 5);
        Card carte = new Card(CardColor.RED, CardValue.SIX, 6);
        Pile.setCardToTheTop(cartToFind);
        player.drawCard(cartToFind);
        assertNotEquals(carte, Pile.getCardTopDeck());
    }

    @Test
    public void testReadyToPlay() {
        Strateg value = new StrategieValue();
        Player player = new Player("meryam", value);
        Card cartToFind = new Card(CardColor.GREEN, CardValue.FOUR, 4);
        Card card = new Card(CardColor.BLUE, CardValue.FIVE, 5);
        Card carte = new Card(CardColor.RED, CardValue.SIX, 6);
        player.getHand().add(card);
        player.getHand().add(carte);

        Pile.setCardToTheTop(cartToFind);
        player.readyToPlay(cartToFind);

        assertNotEquals(1, player.getHand().size());
    }
}
