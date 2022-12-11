package fr.pantheonsorbonne.miage;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import fr.pantheonsorbonne.miage.enums.CardColor;
import fr.pantheonsorbonne.miage.enums.CardValue;
import fr.pantheonsorbonne.miage.game.Card;
import fr.pantheonsorbonne.miage.game.Deck;
import fr.pantheonsorbonne.miage.game.Pile;
import fr.pantheonsorbonne.miage.strategie.Strateg;
import fr.pantheonsorbonne.miage.strategie.StrategieColor;
import fr.pantheonsorbonne.miage.strategie.StrategieValue;

public class UnoEngineTest {
  @Test
  public void testNextPlayerToPlayIfTrue() {
    boolean clockWiseDirectionFalse = true;
    Strateg color = new StrategieColor();
    Player[] listPlayer = new Player[4];
    listPlayer[0] = new Player("meryam", color);
    listPlayer[1] = new Player("amel", color);
    listPlayer[2] = new Player("lili", color);
    listPlayer[3] = new Player("momi", color);
    LocalUno game = new LocalUno(listPlayer);
    game.setPlayerOnTheRound();
    assertEquals("meryam", game.nextPlayerToPlay(clockWiseDirectionFalse));
  }

  @Test
  public void testNextPlayerToPlayIfFalse() {
    boolean clockWiseDirectionFalse = false;
    Strateg color = new StrategieColor();
    Player[] listPlayer = new Player[4];
    listPlayer[0] = new Player("meryam", color);
    listPlayer[1] = new Player("amel", color);
    listPlayer[2] = new Player("lili", color);
    listPlayer[3] = new Player("momi", color);
    LocalUno game = new LocalUno(listPlayer);
    game.setPlayerOnTheRound();
    assertEquals("momi", game.nextPlayerToPlay(clockWiseDirectionFalse));
  }

  @Test
  public void testGetPoint() {
    Strateg color = new StrategieColor();
    int point = 75;
    Player player = new Player("Amel", color);
    player.setPoint(point);
    assertEquals(75, player.getPoint());
  }

  @Test
  public void testPlayRoundIsTrue() {
    Strateg color = new StrategieColor();
    Player[] listPlayer = new Player[2];
    listPlayer[0] = new Player("meryam", color);
    listPlayer[1] = new Player("amel", color);
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
    game.playRound();
    assertEquals("meryam", game.getWinner());
  }

  @Test
  public void testPlayRoundIsWithWrongWinner() {
    Strateg value = new StrategieColor();
    Player[] listPlayer = new Player[2];
    listPlayer[0] = new Player("meryam", value);
    listPlayer[1] = new Player("amel", value);
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
    game.playRound();
    assertNotEquals("amel", game.getWinner());
  }

  @Test
  public void testSpecialCardToAdd() {
    Strateg value = new StrategieColor();
    Player[] listPlayer = new Player[2];
    listPlayer[0] = new Player("meryam", value);
    listPlayer[1] = new Player("amel", value);
    LocalUno game = new LocalUno(listPlayer);
    game.setPlayerOnTheRound();

    Card carte1 = new Card(CardColor.BLUE, CardValue.TWO, 2);
    Card carte2 = new Card(CardColor.GREEN, CardValue.FIVE, 5);
    Card carte3 = new Card(CardColor.RED, CardValue.NINE, 9);
    Card carte4 = new Card(CardColor.BLUE, CardValue.PLUSFOUR, 20);
    Card carte5 = new Card(CardColor.RED, CardValue.INTERDIT, 9);

    Pile.setCardToTheTop(carte2);

    listPlayer[1].getHand().add(carte1);
    listPlayer[1].getHand().add(carte2);
    listPlayer[1].getHand().add(carte3);
    listPlayer[0].getHand().add(carte4);
    listPlayer[0].getHand().add(carte5);

    game.playRound();

    assertEquals(7, listPlayer[1].getHand().size());
  }

  @Test
  public void testPlayRoundInverse() {
    Strateg value = new StrategieColor();
    Player[] listPlayer = new Player[3];
    listPlayer[0] = new Player("meryam", value);
    listPlayer[1] = new Player("amel", value);
    listPlayer[2] = new Player("lili", value);
    LocalUno game = new LocalUno(listPlayer);
    game.setPlayerOnTheRound();

    Card carte1 = new Card(CardColor.RED, CardValue.TWO, 2);
    Card carte2 = new Card(CardColor.RED, CardValue.FIVE, 5);
    Card carte3 = new Card(CardColor.RED, CardValue.NINE, 9);

    Card carte4 = new Card(CardColor.BLUE, CardValue.PLUSFOUR, 20);
    Card carte5 = new Card(CardColor.RED, CardValue.INVERSE, 20);

    Pile.setCardToTheTop(carte3);
    listPlayer[2].getHand().add(carte2);
    listPlayer[2].getHand().add(carte3);
    listPlayer[1].getHand().add(carte1);
    listPlayer[1].getHand().add(carte2);
    listPlayer[1].getHand().add(carte3);
    listPlayer[0].getHand().add(carte4);
    listPlayer[0].getHand().add(carte5);

    game.playRound();

    assertEquals(listPlayer[2].getName(), game.nextPlayerToPlay(false));
  }

  @Test
  public void test2PlayRoundInverse() {
    Strateg color = new StrategieColor();
    Player[] listPlayer = new Player[2];
    listPlayer[0] = new Player("meryam", color);
    listPlayer[1] = new Player("amel", color);

    LocalUno game = new LocalUno(listPlayer);
    game.setPlayerOnTheRound();

    
    Card carte2 = new Card(CardColor.RED, CardValue.FIVE, 5);
    Card carte3 = new Card(CardColor.RED, CardValue.NINE, 9);

    Card carte4 = new Card(CardColor.RED, CardValue.INTERDIT, 20);
    Card carte5 = new Card(CardColor.RED, CardValue.INVERSE, 20);
    Card carte6 = new Card(CardColor.RED, CardValue.PLUSFOUR, 50);
    Card carte7 = new Card(CardColor.RED, CardValue.PLUSTWO, 20);

    Pile.setCardToTheTop(carte3);
    
    listPlayer[1].getHand().add(carte2);
    listPlayer[1].getHand().add(carte3);
    listPlayer[0].getHand().add(carte4);
    listPlayer[0].getHand().add(carte5);
    listPlayer[0].getHand().add(carte6);
    listPlayer[0].getHand().add(carte7);

    game.playRound();

    assertEquals(0, listPlayer[0].getHand().size());
  }

  @Test
  public void testPlay(){
    Strateg color = new StrategieColor();
        Strateg value = new StrategieValue();

        Player[] listPlayer = new Player[2];
        listPlayer[0] = new Player("Meryam", color);
        listPlayer[1] = new Player("Amel", value);

        LocalUno localWarGame = new LocalUno(listPlayer);
        Deck.setPiocheSize(1);
        assertThrows(IllegalArgumentException.class, () -> {
          localWarGame.play();
        });
        Deck.setPiocheSize(108);

  }


}
