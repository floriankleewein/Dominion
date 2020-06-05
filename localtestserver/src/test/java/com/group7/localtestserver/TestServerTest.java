package com.group7.localtestserver;


import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;
import com.floriankleewein.commonclasses.ClassRegistration;
import com.floriankleewein.commonclasses.Game;
import com.floriankleewein.commonclasses.gamelogic.GameHandler;
import com.floriankleewein.commonclasses.user.User;

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

    @Mock
    Game mockGame;

    @Mock
    Server mockServer;

    @Mock
    ClassRegistration mockReg;

    @InjectMocks
    private TestServer m_cut;

    @Before
    public void setup() {
        m_cut = new TestServer();
        MockitoAnnotations.initMocks(this);
        User activeUser = new User("Flo");
        when(mockGame.getActivePlayer()).thenReturn(activeUser);

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

}
