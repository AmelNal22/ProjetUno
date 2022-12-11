package fr.pantheonsorbonne.miage;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import org.junit.jupiter.api.Test;


public class UnoNetworkPlayerTest {


    @Test
    public void testPlayerOnTheRound() {
        Deque<String> playerOnTheRound=new LinkedList<>();
        playerOnTheRound.add("Meryam");
        playerOnTheRound.add("lili");
        playerOnTheRound.add("Amel");
        HostFacade hostFacade = Facade.getFacade();
        Set<String> players = new HashSet<>();
        players.add("Amel");
        players.add("lili");
        players.add("Meryam");
        fr.pantheonsorbonne.miage.model.Game uno = hostFacade.createNewGame("uno");
        UnoNetworkEngine engine = new UnoNetworkEngine(hostFacade, players, uno);
        engine.setPlayerOnTheRound();
        assertEquals(playerOnTheRound, engine.getPlayerOnTheRound());
    }

    @Test
     public void testGetInitialPlayers() {
        HostFacade hostFacade = Facade.getFacade();
         Set<String> players = new HashSet<>();
         players.add("Amel");
         players.add("Meryam");
         players.add("lili");
         fr.pantheonsorbonne.miage.model.Game uno = hostFacade.createNewGame("uno");
         UnoNetworkEngine network = new UnoNetworkEngine(hostFacade, players, uno);
         assertEquals(players, network.getInitialPlayers());
     }

   
}
