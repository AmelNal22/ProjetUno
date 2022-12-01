package fr.pantheonsorbonne.miage;

import fr.pantheonsorbonne.miage.game.*;

import java.util.*;

public class LocalUno extends UnoEngine {

    private LinkedList<String> playerOnTheRound = new LinkedList<>();
    private Set<String> players;
    private Player[] arrayPlayers;
    String MESSAGE_ERROR = "Erreur car le player n'existe pas";

    public LocalUno(Set<String> players) {
        this.players = players;
    }

    protected LinkedList<String> getPlayerOnTheRound() {
        return playerOnTheRound;
    }

    //retourne le player selon le nom en paramètre
    Player findPlayerWithName(String player) {
        for (Player currentPlayer : arrayPlayers) {
            if (currentPlayer.getName().equals(player)) {
                return currentPlayer;
            }
        }
        return null;
    }

    @Override
    protected void giveCardsToPlayer(String player) throws IllegalArgumentException{
        Player thisPlayer = findPlayerWithName(player);
        if (thisPlayer != null) {
            thisPlayer.setHand();
        } else {
            throw new IllegalArgumentException(MESSAGE_ERROR);
        }
    }

    @Override
    protected boolean getCardFromPlayer(String player) throws IllegalArgumentException {
        Pile.showCardTopDeck();
        Player thisPlayer = findPlayerWithName(player);
        if (thisPlayer != null) {
            System.out.println("Au tour du joueur "+thisPlayer.getName());
            return thisPlayer.startToPlay(Pile.getCardTopDeck());
        } else {
            throw new IllegalArgumentException(MESSAGE_ERROR);
        }
    }

    //distribut pour chaque round
    @Override
    protected boolean playRound() {
        getCardsToRemove();
        for (String playerName : getPlayerOnTheRound()) {
            giveCardsToPlayer(playerName);

        }
        return super.playRound();
    }

    public static void main(String... args) {
        Set<String> players=new HashSet<>();
        players.add("Joueur4");
        players.add("Joueur3");
        players.add("Joueur2");
        players.add("Joueur1");
        LocalUno localWarGame = new LocalUno(players);
        localWarGame.play();
    }

    @Override
    protected void declareWinner(String name) {
        System.out.println("Le gagnant est " + name);
    }

    @Override
    protected void setPlayerOnTheRound() {
        for (String currentPlayer : players) {
            playerOnTheRound.add(currentPlayer);
            System.out.println(currentPlayer);
        }
        setPlayers();
    }

    //tableau des players pour récupérer les infos des players
    private void setPlayers(){
        arrayPlayers = new Player[playerOnTheRound.size()];
        for (int i = 0; i < playerOnTheRound.size() ; i++){
            arrayPlayers[i]=new Player(playerOnTheRound.get(i));
        }
    }

    @Override
    protected boolean verifIfWinner(String player) throws IllegalArgumentException {
        Player thisPlayer = findPlayerWithName(player);
        if (thisPlayer != null) {
            return thisPlayer.getHand().isEmpty();
        }
        throw new IllegalArgumentException(MESSAGE_ERROR);
    }

    @Override
    protected Set<String> getInitialPlayers() {
        return players;
    }

    protected int countPointPlayer(Deque<Card> cardInTheHand) {
        int point = 0;
        for (Card currCard : cardInTheHand) {
            point = point + currCard.getPoint();
        }
        return point;
    }

     @Override
     protected boolean countPointAllPlayers() throws IllegalArgumentException {
         int counterOfPoint = 0;
         for (String currPlayer : getPlayerOnTheRound()) {
             Player thisPlayer = findPlayerWithName(currPlayer);
             if (thisPlayer != null) {
                counterOfPoint = counterOfPoint + countPointPlayer(thisPlayer.getHand());
             } else {
                throw new IllegalArgumentException(MESSAGE_ERROR);
             }

         }
         Player thisPlayer = findPlayerWithName(getWinner());
         if (thisPlayer != null) {
             int pointBefore = thisPlayer.getPoint();
             int newPoint = pointBefore + counterOfPoint;
             thisPlayer.setPoint(newPoint);
             return thisPlayer.getPoint() >= 100;
         } else {
            throw new IllegalArgumentException(MESSAGE_ERROR);
         }
     }

    //quand on récupère les cartes un +2 ou +4 et met à jour la nouvelle position
    @Override
    protected int searchSpecialCardAdding(Card card, LinkedList<String> playerOnTheRound, int currPosition) throws IllegalArgumentException {
        int nextPlayer = currPosition + 1;
        if (currPosition >= playerOnTheRound.size() - 1) {
            nextPlayer = 0;
        }
        Player thisPlayer = findPlayerWithName(playerOnTheRound.get(nextPlayer));
        if (thisPlayer != null) {
            if (Card.isTake4(card)) {
                Strategie.add4Cards(thisPlayer.getHand());
                return nextPlayer;
            } else {
                Strategie.add2Cards(thisPlayer.getHand());
                return nextPlayer;
            }
        } else {
            throw new IllegalArgumentException(MESSAGE_ERROR);
        }
    }

    //quand un round est fini, remets ttes les cartes de tous les joueurs dans la pile
    @Override
    protected void getCardsToRemove() throws IllegalArgumentException {
        for (String currPlayer : getPlayerOnTheRound()) {
            Player thisPlayer = findPlayerWithName(currPlayer);
            if (thisPlayer != null) {
                if (!thisPlayer.getHand().isEmpty()) {
                    System.out.println(thisPlayer.getPoint() + "a gagnée");
                }
                while (!thisPlayer.getHand().isEmpty()) {
                    Deck.setPioche(Deck.getPiocheSize(), thisPlayer.getHand().poll());
                    Deck.setPiocheSize(Deck.getPiocheSize() + 1);
                }
            } else {
                throw new IllegalArgumentException(MESSAGE_ERROR);
            }
        }
        Deck.mixCards();
    }

    //quand on inverse le jeu, on inverse les joueurs dans la liste
    @Override
    protected void setNewPlayerOnTheRound(LinkedList<String> playerOnTheRound) {
        this.playerOnTheRound=playerOnTheRound;
    }

}


