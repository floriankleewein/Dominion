package com.group7.localtestserver;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;
import com.floriankleewein.commonclasses.ClassRegistration;
import com.floriankleewein.commonclasses.Game;
import com.floriankleewein.commonclasses.gamelogic.GameHandler;
import com.floriankleewein.commonclasses.network.GameUpdateMsg;
import com.floriankleewein.commonclasses.network.HasCheatedMessage;
import com.floriankleewein.commonclasses.user.User;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import static org.mockito.Mockito.*;

public class TestServerTest {

    @Mock
    private GameHandler mockGameHandler;


    private Game game;

    @Mock
    private Connection Mockconnection;

    @Mock
    private HasCheatedMessage mockHasCheatedMsg;

    @Mock
    private Server mockServer;

    @Mock
    private ClassRegistration mockReg;

    @InjectMocks
    private TestServer m_cut;

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
        game = null;
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


}
