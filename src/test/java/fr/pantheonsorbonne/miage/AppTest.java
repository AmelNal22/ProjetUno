package fr.pantheonsorbonne.miage;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Deque;
import java.util.LinkedList;

import org.junit.jupiter.api.Test;

import fr.pantheonsorbonne.miage.enums.CardColor;
import fr.pantheonsorbonne.miage.enums.CardValue;
import fr.pantheonsorbonne.miage.game.Card;
import fr.pantheonsorbonne.miage.game.Deck;

public class AppTest 
{

    //Classe Play



    @Test
    public void testGetRandomCards() {
        int length = 10;
        assertNotEquals(length < 0, Deck.getRandomCards(length));
    }

    @Test
    public void testPlay() {
        Play x = new Play(3);
        assertNotNull(x);
    }

    @Test
    public void testNumberOfPlayers() {
        int numberOfPlayer = 3;
        Player y = new Player(numberOfPlayer);
        assertNotEquals(numberOfPlayer < 11,y);
    }

    @Test
    public void testGetRandomCards2() {
        int length = 7;
        Card[] deck = Deck.getRandomCards(7);
        assertNotEquals(length == 7, deck);
    }

    @Test
    public void testInverseGame() {
        Play x = new Play(3);
        int posi = 3;
        assertNotNull(x.inverseGame(posi));
    }

    @Test
    public void testSetOfTheRound() {
        Play b = new Play(3);
        assertFalse(b.setOfTheRound(3));
    }

    @Test
    public void testverifIfWinner() {
        Play b = new Play(3);
        Player y = new Player(3);
        if (y.getHand().isEmpty()){
            assertTrue(b.verifIfWinner(y));
        }else{
        assertFalse(b.verifIfWinner(y));
        }
    }

    @Test
    public void testReadyToPlay(){
        Deque<Card> hand = new LinkedList<>();
        Card cardToFind = new Card(null, null, 0);
        Strategie playerStrategie = new Strategie();
        Player y = new Player(3);
        if (playerStrategie.readyToPlay(cardToFind, hand)){
            assertTrue(y.readyToPlay(cardToFind));
        } else if (y.drawCard(cardToFind)) {
            assertTrue(y.readyToPlay(cardToFind));
        }
        assertFalse(y.readyToPlay(cardToFind));

    }

    @Test
    public void testFindSameColorOfDeck() {
        Strategie s = new Strategie();
        Deque<Card> handOfPlayer = new LinkedList<>();
        Card cardToFind = new Card(null, null, 0);
        assertNull(s.findSameColorOfDeck(cardToFind, handOfPlayer));
    }

    /**@Test
    public void testGamePlay() {
        Player b = new Player(3);
        Play t = new Play(3);
        if (!b.readyToPlay(Pile.cardTopDeck())) {
            assertFalse(t.gamePlay(b));
        } else if (b.readyToPlay(Pile.cardTopDeck())) {
            assertTrue(t.gamePlay(b));
        }
    }**/

    @Test
    public void testCountPoint() {
        Play p = new Play(3);
        Deque<Card> cardInTheHand = new LinkedList<>();
        assertNotEquals(501, p.countPoint(cardInTheHand));
    }

    @Test
    public void testFindCardToChangeColor() {
        Deque<Card> handOfPlayer = new LinkedList<>();
        Strategie s = new Strategie();
        for (Card currCard : handOfPlayer){
            if (Card.isTake4(currCard) || Card.isJoker(currCard)){
                assertEquals(currCard, s.findCardToChangeColor(handOfPlayer));
            }
        }
        assertNull(s.findCardToChangeColor(handOfPlayer));
    }

    @Test
    public void testIllegalArgument(){
        assertThrows(IllegalArgumentException.class, 
        () -> Deck.getRandomCards(-8));
    }
 
    @Test
    public void testsearchMaxValueCard(){
        Strategie s = new Strategie();
        Card[] cardsInHandOfSameColor={new Card(CardColor.ROUGE,CardValue.ONE,1),new Card(CardColor.ROUGE,CardValue.NINE,9)};
        assertSame(cardsInHandOfSameColor[1],s.searchMaxValueCard(cardsInHandOfSameColor));
    }


    @Test
    public void testFindRepetitionColor(){
        Deque<Card> handOfPlayer= new LinkedList<Card>();
        handOfPlayer.add(new Card(CardColor.ROUGE,CardValue.ONE,1));
        handOfPlayer.add(new Card(CardColor.ROUGE,CardValue.NINE,9));
        handOfPlayer.add(new Card(CardColor.VERT,CardValue.NINE,9));
        CardColor rouge=CardColor.ROUGE;
        Strategie s = new Strategie();

        assertEquals(2,s.findRepetitionColor(rouge,handOfPlayer));
    }

    @Test
    public void testReadyToPlay2(){

        Player m=new Player(0);
        Card cardToFind=new Card(CardColor.ROUGE,CardValue.NINE,9);
        Deque<Card> handOfPlayer= new LinkedList<Card>();
        handOfPlayer.add(new Card(CardColor.ROUGE,CardValue.ONE,1));
        handOfPlayer.add(new Card(CardColor.ROUGE,CardValue.NINE,9));
        handOfPlayer.add(new Card(CardColor.VERT,CardValue.JOKER,50));
        handOfPlayer.add(new Card(CardColor.BLEU,CardValue.ONE,1));
        handOfPlayer.add(new Card(CardColor.JAUNE,CardValue.NINE,9));
        handOfPlayer.add(new Card(CardColor.ROUGE,CardValue.INVERSE,20));

        assertTrue(m.getPlayerStrategie().readyToPlay(cardToFind,handOfPlayer));
    }


    @Test
    public void testCountNbrOfPoint(){
        Play game1=new Play(2);
        
        game1.getPlayerOnTheRound().getLast().getHand().add(new Card(CardColor.VERT,CardValue.JOKER,50));
        game1.getPlayerOnTheRound().getLast().getHand().add(new Card(CardColor.BLEU,CardValue.INTERDIT,20));
        game1.getPlayerOnTheRound().getLast().getHand().add(new Card(CardColor.JAUNE,CardValue.NINE,9));
        game1.getPlayerOnTheRound().getLast().getHand().add(new Card(CardColor.JAUNE,CardValue.NINE,9));
        game1.getPlayerOnTheRound().getLast().getHand().add(new Card(CardColor.ROUGE,CardValue.INVERSE,20));

        game1.setWinner(game1.getPlayerOnTheRound().getFirst());

        assertTrue(game1.countNbrOfPoint());
    }

    @Test
    public void testVerifIfWinner(){
        Play game1=new Play(2);

        game1.getPlayerOnTheRound().getLast().getHand().add(new Card(CardColor.VERT,CardValue.JOKER,50));
        game1.getPlayerOnTheRound().getLast().getHand().add(new Card(CardColor.BLEU,CardValue.INTERDIT,20));
        game1.getPlayerOnTheRound().getLast().getHand().add(new Card(CardColor.JAUNE,CardValue.NINE,9));
        game1.getPlayerOnTheRound().getLast().getHand().add(new Card(CardColor.JAUNE,CardValue.NINE,9));
        game1.getPlayerOnTheRound().getLast().getHand().add(new Card(CardColor.ROUGE,CardValue.INVERSE,20));

        assertTrue(game1.verifIfWinner(game1.getPlayerOnTheRound().getFirst()));
        assertFalse(game1.verifIfWinner(game1.getPlayerOnTheRound().getLast()));
    }

    /**@Test
    public void testMeth1(){
        Player m=new Player(0);
        Card cardToFind=new Card(CardColor.ROUGE,CardValue.NINE,9);
        Deque<Card> handOfPlayer= new LinkedList<Card>();
        handOfPlayer.add(new Card(CardColor.VERT,CardValue.ONE,1));
        handOfPlayer.add(new Card(CardColor.VERT,CardValue.NINE,9));
        handOfPlayer.add(new Card(CardColor.VERT,CardValue.JOKER,50));
        handOfPlayer.add(new Card(CardColor.BLEU,CardValue.ONE,1));
        handOfPlayer.add(new Card(CardColor.ROUGE,CardValue.NINE,9));
        handOfPlayer.add(new Card(CardColor.ROUGE,CardValue.INVERSE,20));

        assertTrue(m.getPlayerStrategie().meth1(cardToFind, handOfPlayer));

    }**/

    @Test
    public void testSearchSpecialCardAdding() {
        Play game1=new Play(3);
        Player y = new Player(1);
        Player x = new Player(2);
        Player z = new Player(3);
        Card card = new Card(CardColor.VERT,CardValue.JOKER, 50);
        Card card2 = new Card(CardColor.VERT,CardValue.PLUSQAUTRE, 50);
        LinkedList<Player> playerOnTheRound = new LinkedList<>();
        playerOnTheRound.add(y);
        playerOnTheRound.add(x);
        playerOnTheRound.add(z);
        int currPosition = 1;
        assertEquals(2,game1.searchSpecialCardAdding(card, playerOnTheRound, currPosition));
        assertEquals(2,game1.searchSpecialCardAdding(card2, playerOnTheRound, currPosition));
    
    }

    /**@Test
    public void testGetCardsToRemove() {
        Play game1 = new Play(3);
        Player y = new Player(1);
        Player x = new Player(2);
        Player z = new Player(3);
        Deque<Card> hand = new LinkedList<>();
        Card card = new Card(CardColor.VERT,CardValue.JOKER, 50);
        hand.add(card);
        LinkedList<Player> playerOnTheRound = new LinkedList<>();
        playerOnTheRound.add(y);
        playerOnTheRound.add(x);
        playerOnTheRound.add(z);
        Deck d = new Deck();
        AssertNotEquals(d.mixCards(), game1.getCardsToRemove());
    }**/

    @Test
    public void testSpecialCard() {

            Play game1=new Play(3);
            Player y = new Player(1);
            Player x = new Player(2);
            Player z = new Player(3);
            Deque<Card> gameDeck = new LinkedList<>();
            gameDeck.add(new Card(CardColor.VERT,CardValue.PLUSQAUTRE, 50));
            gameDeck.add(new Card(CardColor.VERT,CardValue.PLUSQAUTRE, 50));
            LinkedList<Player> playerOnTheRound = new LinkedList<>();
            playerOnTheRound.add(y);
            playerOnTheRound.add(x);
            playerOnTheRound.add(z);

            assertEquals(3, game1.specialCard(3));
            //assertEquals(0, game1.specialCard(5));
    }
}


