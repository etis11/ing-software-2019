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
    public void init() {
        lobby = new Lobby();
        user1 = new User();
        user2 = new User();
        user3 = new User();
        user4 = new User();
        user5 = new User();
    }

    @Test
    public void instanceTest() {
        assertTrue(lobby instanceof Lobby, "ERROR: wrong instance");
    }

    @Test
    public void maxUser() {
        assertSame(lobby.getMaxPlayerInLobby(), 5, "ERROR: wrong costant");
    }

    @Test
    public void joinTest() {
        assertTrue(lobby.getUsers().isEmpty(), "ERROR: not empty");

        assertDoesNotThrow(() -> lobby.join(user1), "ERROR: lobby not full");
        assertSame(lobby.getUsers().size(), 1, "ERROR: not inserted user");

        assertDoesNotThrow(() -> lobby.join(user2), "ERROR: lobby not full");
        assertSame(lobby.getUsers().size(), 2, "ERROR: not inserted user");

        assertDoesNotThrow(() -> lobby.join(user3), "ERROR: lobby not full");
        assertSame(lobby.getUsers().size(), 3, "ERROR: not inserted user");

        assertDoesNotThrow(() -> lobby.join(user4), "ERROR: lobby not full");
        assertSame(lobby.getUsers().size(), 4, "ERROR: not inserted user");

        assertDoesNotThrow(() -> lobby.join(user5), "ERROR: lobby not full");
        assertSame(lobby.getUsers().size(), 5, "ERROR: not inserted user");

        User user6 = new User();
        assertThrows(Exception.class, () -> lobby.join(user6));
        assertSame(lobby.getUsers().size(), 5, "ERROR: inserted player in a full lobby");
    }

    @Test
    public void removeTest() {
        assertThrows(IllegalArgumentException.class, () -> lobby.removeUser(null));

        assertDoesNotThrow(() -> lobby.join(user1), "ERROR: lobby not full");
        assertDoesNotThrow(() -> lobby.join(user2), "ERROR: lobby not full");
        assertDoesNotThrow(() -> lobby.join(user3), "ERROR: lobby not full");
        assertSame(lobby.getUsers().size(), 3, "ERROR: not inserted all users");

        assertNull(lobby.removeUser(user4));
        assertSame(lobby.getUsers().size(), 3, "ERROR: not to remove user");

        assertSame(lobby.removeUser(user2), user2, "ERROR: user 2 not to be removed");
        assertSame(lobby.getUsers().size(), 2, "ERROR: not removed user");
        assertFalse(lobby.getUsers().contains(user2), "ERROR: user still in the lobby");


    }

    @Test
    public void openedLobbyTest(){
        assertFalse(lobby.isClosed(), "ERROR Lobby has to be open");
        lobby.closeLobby();
        assertTrue(lobby.isClosed(), "ERROR lobby has to be closed");
        lobby.openLobby();
        assertFalse(lobby.isClosed(), "ERROR Lobby has to be open");
    }

    @Test
    public void minMaxPlayerInLobby(){
        assertSame(3, lobby.getMinPlayerInLobby(), "ERROR min player incorrect");
        assertSame(5, lobby.getMaxPlayerInLobby(), "ERROR max player incorrect");
        lobby.setMaxPlayerInLobby(4);
        assertSame(4, lobby.getMaxPlayerInLobby(), "ERROR max player not updated");
    }

    @Test
    public void limitatedJoinTest(){
        lobby.setMaxPlayerInLobby(4);
        assertDoesNotThrow(() -> lobby.join(user1), "ERROR: lobby not full");
        assertDoesNotThrow(() -> lobby.join(user2), "ERROR: lobby not full");
        assertFalse(lobby.hasReachedMinCapacity(), "ERROR min capacity not reached");
        assertDoesNotThrow(() -> lobby.join(user3), "ERROR: lobby not full");
        assertTrue(lobby.hasReachedMinCapacity(), "ERROR min capacity reached");
        assertFalse(lobby.hasReachedMaxCapacity(), "ERROR max capacity not reached");
        assertDoesNotThrow(() -> lobby.join(user4), "ERROR: lobby not full");
        assertTrue(lobby.hasReachedMaxCapacity(), "ERROR max capacity reached");
        assertThrows(Exception.class, () -> lobby.join(user5));
        assertSame(4, lobby.getNumOfUsers(), "ERROR wrong user number");

    }
}
