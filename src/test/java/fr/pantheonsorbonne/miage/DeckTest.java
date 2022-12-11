package fr.pantheonsorbonne.miage;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import fr.pantheonsorbonne.miage.game.Deck;
import fr.pantheonsorbonne.miage.strategie.Strateg;
import fr.pantheonsorbonne.miage.strategie.StrategieColor;

public class DeckTest{

  @Test
  public void testGiveCardsToPlayerWithDeck() {
    Strateg value = new StrategieColor();
    Player player = new Player("meryam", value);
    assertThrows(IllegalArgumentException.class, () -> {
      player.addHandCard(109);
    });
  }

  @Test
  public void testCreateNewDeckWithError() {
    assertThrows(IllegalArgumentException.class, () -> {
      Deck.createNewDeck(108);
    });
  }

}
