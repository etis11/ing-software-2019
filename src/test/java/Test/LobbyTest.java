package Test;

import model.Lobby;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LobbyTest {
    private Lobby lobby;
    private User user1;
    private User user2;
    private User user3;
    private User user4;
    private User user5;

    @BeforeEach
    public void init(){
        lobby = new Lobby();
        user1 = new User();
        user2 = new User();
        user3 = new User();
        user4 = new User();
        user5 = new User();
    }

    @Test
    public void instanceTest(){
        assertTrue(lobby instanceof Lobby, "ERROR: wrong instance");
    }

    @Test
    public void maxUser(){
        assertSame(Lobby.getMaxPlayerInLobby(), 5, "ERROR: wrong costant");
    }

    @Test
    public void joinTest(){
        assertTrue(lobby.getUsers().isEmpty(), "ERROR: not empty");

        assertDoesNotThrow(()->lobby.join(user1), "ERROR: lobby not full");
        assertSame(lobby.getUsers().size(), 1, "ERROR: not inserted user");

        assertDoesNotThrow(()->lobby.join(user2), "ERROR: lobby not full");
        assertSame(lobby.getUsers().size(), 2, "ERROR: not inserted user");

        assertDoesNotThrow(()->lobby.join(user3), "ERROR: lobby not full");
        assertSame(lobby.getUsers().size(), 3, "ERROR: not inserted user");

        assertDoesNotThrow(()->lobby.join(user4), "ERROR: lobby not full");
        assertSame(lobby.getUsers().size(), 4, "ERROR: not inserted user");

        assertDoesNotThrow(()->lobby.join(user5), "ERROR: lobby not full");
        assertSame(lobby.getUsers().size(), 5, "ERROR: not inserted user");

        User user6 = new User();
        assertThrows(Exception.class, ()->lobby.join(user6));
        assertSame(lobby.getUsers().size(), 5, "ERROR: inserted player in a full lobby");
    }
    @Test
    public void removeTest(){
        assertThrows(IllegalArgumentException.class, ()->lobby.removeUser(null));

        assertDoesNotThrow(()->lobby.join(user1), "ERROR: lobby not full");
        assertDoesNotThrow(()->lobby.join(user2), "ERROR: lobby not full");
        assertDoesNotThrow(()->lobby.join(user3), "ERROR: lobby not full");
        assertSame(lobby.getUsers().size(), 3, "ERROR: not inserted all users");

        //assertNull(lobby.removeUser(user4));
        assertSame(lobby.getUsers().size(), 3, "ERROR: not to remove user");

        assertSame(lobby.removeUser(user2), user2, "ERROR: user 2 not to be removed");
        assertSame(lobby.getUsers().size(), 2, "ERROR: not removed user");
        assertFalse(lobby.getUsers().contains(user2), "ERROR: user still in the lobby");


    }


}
