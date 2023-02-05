package fr.pantheonsorbonne.miage;

import org.junit.jupiter.api.Test;

import fr.pantheonsorbonne.miage.enums.CardColor;
import fr.pantheonsorbonne.miage.enums.CardValue;
import fr.pantheonsorbonne.miage.game.Card;
import fr.pantheonsorbonne.miage.game.Deck;
import fr.pantheonsorbonne.miage.strategie.Strateg;
import fr.pantheonsorbonne.miage.strategie.StrategieColor;
import fr.pantheonsorbonne.miage.strategie.StrategieValue;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Deque;
import java.util.LinkedList;

public class LocalUnoTest {

  @Test
  public void testIfIsWinner() {
    Strateg value = new StrategieColor();
    Player[] listPlayer = new Player[2];
    listPlayer[0] = new Player("meryam", value);
    listPlayer[1] = new Player("amel", value);
    LocalUno game = new LocalUno(listPlayer);
    game.setPlayerOnTheRound();
    String player = "amel";
    assertTrue(game.isWinner(player));
  }

  @Test
  public void testIfIsNotWinner() {
    Strateg value = new StrategieValue();
    Player[] listPlayer = new Player[2];
    listPlayer[0] = new Player("meryam", value);
    listPlayer[1] = new Player("amel", value);
    LocalUno game = new LocalUno(listPlayer);
    game.setPlayerOnTheRound();
    String player = "amel";
    game.findPlayerWithName(player).setHand();
    assertFalse(game.isWinner(player));
  }

  @Test
  public void testPlayerLegalNumberofPlayer() {
    Strateg color = new StrategieColor();
    Player[] listPlayer = new Player[2];
    listPlayer[0] = new Player("meryam", color);
    listPlayer[1] = new Player("amel", color);
    LocalUno game = new LocalUno(listPlayer);
    assertEquals(2, game.getInitialPlayers().size());
  }

  @Test
  public void testTooManyPlayers() {
    Strateg color = new StrategieColor();
    Player[] listPlayer = new Player[11];
    for (int i = 0; i < 11; i++) {
      listPlayer[i] = new Player("meryam" + i, color);
    }
    assertThrows(IllegalArgumentException.class, () -> {
      new LocalUno(listPlayer);
    });
  }

  @Test
  public void testTooLessPlayers() {
    Strateg color = new StrategieColor();
    Player[] listPlayer = new Player[1];
    listPlayer[0] = new Player("meryam", color);
    assertThrows(IllegalArgumentException.class, () -> {
      new LocalUno(listPlayer);
    });
  }

  @Test
  public void testFindPlayerWithName() {
    Strateg color = new StrategieColor();
    Player[] listPlayer = new Player[2];
    String name = "amel";
    listPlayer[0] = new Player("amel", color);
    listPlayer[1] = new Player("meryam", color);
    LocalUno game = new LocalUno(listPlayer);
    assertEquals("amel", game.findPlayerWithName(name).getName());
  }
  /* 

  @Test
  public void testNotFoundPlayerWithName() {
    Strateg color = new StrategieColor();
    Player[] listPlayer = new Player[2];
    String name = "lina";
    listPlayer[0] = new Player("amel", color);
    listPlayer[1] = new Player("meryam", color);
    LocalUno game = new LocalUno(listPlayer);
    assertThrows(IllegalArgumentException.class, () -> game.findPlayerWithName(name).getName());
  }
*/
  @Test
  public void testCountPointPlayer() {
    Deque<Card> handOfPlayers = new LinkedList<Card>();
    handOfPlayers.add(new Card(CardColor.BLUE, CardValue.TWO, 2));
    handOfPlayers.add(new Card(CardColor.RED, CardValue.PLUSFOUR, 50));
    handOfPlayers.add(new Card(CardColor.GREEN, CardValue.INVERSE, 20));
    handOfPlayers.add(new Card(CardColor.GREEN, CardValue.INVERSE, 20));
    Strateg color = new StrategieColor();
    Player[] listPlayer = new Player[2];
    listPlayer[0] = new Player("amel", color);
    listPlayer[1] = new Player("meryam", color);
    LocalUno game = new LocalUno(listPlayer);
    assertEquals(92, game.countPointPlayer(handOfPlayers));
  }

  @Test
  public void testCountPointAllPlayers() {
    Strateg value = new StrategieColor();
    Player[] listPlayer = new Player[3];
    listPlayer[0] = new Player("meryam", value);
    listPlayer[1] = new Player("amel", value);
    listPlayer[2] = new Player("lili", value);
    LocalUno game = new LocalUno(listPlayer);
    game.setPlayerOnTheRound();
    Card carte1 = new Card(CardColor.BLUE, CardValue.JOKER, 50);
    Card carte2 = new Card(CardColor.GREEN, CardValue.JOKER, 50);
    Card carte3 = new Card(CardColor.RED, CardValue.JOKER, 50);
    Card carte7 = new Card(CardColor.BLUE, CardValue.JOKER, 50);
    Card carte8 = new Card(CardColor.GREEN, CardValue.JOKER, 50);
    Card carte9 = new Card(CardColor.RED, CardValue.JOKER, 50);
    Card carte4 = new Card(CardColor.RED, CardValue.PLUSFOUR, 50);
    Card carte5 = new Card(CardColor.RED, CardValue.INTERDIT, 50);
    Card carte6 = new Card(CardColor.RED, CardValue.PLUSFOUR, 50);

    listPlayer[1].getHand().add(carte3);
    listPlayer[1].getHand().add(carte2);
    listPlayer[1].getHand().add(carte1);
    listPlayer[1].getHand().add(carte7);
    listPlayer[1].getHand().add(carte8);
    listPlayer[1].getHand().add(carte9);
    listPlayer[0].getHand().add(carte4);
    listPlayer[0].getHand().add(carte5);
    listPlayer[0].getHand().add(carte6);
    listPlayer[0].getHand().add(carte6);
    listPlayer[0].getHand().add(carte6);
    listPlayer[2].getHand().add(carte6);
    game.setWinner(listPlayer[2].getName());
    assertTrue(game.isGameOver());
  }

  @Test
  public void testGiveCardsToPlayer() {
    Strateg value = new StrategieColor();
    Player[] listPlayer = new Player[3];
    listPlayer[0] = new Player("meryam", value);
    listPlayer[1] = new Player("amel", value);
    listPlayer[2] = new Player("lili", value);
    LocalUno game = new LocalUno(listPlayer);
    game.setPlayerOnTheRound();
    String player = "amel";

    game.giveCardsToPlayer(player);
    assertEquals(7, game.findPlayerWithName(player).getHand().size());
  }

  @Test
  public void testCardsToRemove() {
    Strateg value = new StrategieColor();
    Player[] listPlayer = new Player[3];
    listPlayer[0] = new Player("meryam", value);
    listPlayer[1] = new Player("amel", value);
    listPlayer[2] = new Player("lili", value);
    LocalUno game = new LocalUno(listPlayer);
    game.setPlayerOnTheRound();
    int size = Deck.getPiocheSize();
    game.giveCardsToPlayer("meryam");
    game.giveCardsToPlayer("lili");
    game.giveCardsToPlayer("amel");
    game.getCardsToRemove();
    assertEquals(size, Deck.getPiocheSize());
  }

  @Test
  public void testGetArrayPlayer() {
    Player player1 = new Player("Amel", null);
    Player player2 = new Player("Meryam", null);
    Player[] players = { player1, player2 };
    LocalUno localUno = new LocalUno(players);
    assertEquals(players, localUno.getArrayPlayer());
  }
}
