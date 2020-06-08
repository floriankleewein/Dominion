package com.group7.localtestserver;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;
import com.floriankleewein.commonclasses.ClassRegistration;
import com.floriankleewein.commonclasses.Game;
import com.floriankleewein.commonclasses.gamelogic.GameHandler;
import com.floriankleewein.commonclasses.network.AddPlayerSuccessMsg;
import com.floriankleewein.commonclasses.network.GameUpdateMsg;
import com.floriankleewein.commonclasses.network.HasCheatedMessage;
import com.floriankleewein.commonclasses.network.StartGameMsg;
import com.floriankleewein.commonclasses.network.UpdatePlayerNamesMsg;
import com.floriankleewein.commonclasses.network.messages.NotEnoughRessourcesMsg;
import com.floriankleewein.commonclasses.user.User;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.Map;

import static org.mockito.Mockito.*;

public class TestServerTest {

    @Mock
    private GameHandler mockGameHandler;


    private Game game;

    @Mock
    private Map<User, Connection> mockMap;

    @Mock
    private Connection mockConnection;

    @Mock
    private HasCheatedMessage mockHasCheatedMsg;

    @Mock
    private Server mockServer;

    @Mock
    private ClassRegistration mockReg;

    @InjectMocks
    private TestServer m_cut;

    private NotEnoughRessourcesMsg errorMsg;


    @Before
    public void setup() {
        m_cut = new TestServer();
        game = Game.getGame();
        MockitoAnnotations.initMocks(this);
        User activeUser1 = new User("Flo");
        User activeUser2 = new User("Laura");
        game.addPlayer(activeUser1);
        game.addPlayer(activeUser2);
    }

    @After
    public void teardown() {
        m_cut = null;
        Game.setGame(null);
        errorMsg = null;
    }

    @Test
    public void startServer1() {
        try {
            m_cut.startServer();
            verify(mockServer, times(1)).start();
        } catch (InterruptedException e) {
            Log.error("Error caught. Interrupted Exception.");
        }
    }

    @Test
    public void startServer2() {
        try {
            m_cut.startServer();
            verify(mockServer, times(1)).bind(53217);
        } catch (IOException e) {
            Log.error("Error caught. IO Exception.");
        } catch (InterruptedException e) {
            Log.error("Error caught. Interrupted Exception.");
        }
    }

    @Test
    public void createGame() {
        m_cut.createGame();
        Assert.assertEquals(true, m_cut.hasGame());
    }

    @Test
    public void updateAll() {
        GameUpdateMsg msg = new GameUpdateMsg();
        m_cut.updateAll(msg);
        verify(mockGameHandler, times(1)).updateGameHandler(msg);
    }

    @Test
    public void setupGame() {
        m_cut.createGame();
        verify(mockGameHandler, times(0)).prepareGame();
        Assert.assertEquals(true, m_cut.hasGame());
        m_cut.setupGame();
        verify(mockGameHandler, times(0)).prepareGame();
    }

    @Test
    public void addPlayer() {
        AddPlayerSuccessMsg msg = new AddPlayerSuccessMsg();
        msg.setPlayerName("TomTurbo");
        m_cut.createGame();
        m_cut.addPlayerSuccessMsgFunctionality(msg, mockConnection);
        verify(mockConnection, times(1)).sendTCP(msg);
        Assert.assertEquals(3, m_cut.getGame().getPlayerNumber());
    }

    @Test
    public void resetMessage() {
        m_cut.createGame();
        Assert.assertEquals(2, m_cut.getGame().getPlayerNumber());
        m_cut.resetMsgFunctionality();
        Assert.assertEquals(0, m_cut.getGame().getPlayerNumber());
    }



}
