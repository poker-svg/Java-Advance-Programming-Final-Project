package cn.edu.nju.cs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.UnknownHostException;

import org.junit.BeforeClass;
import org.junit.Test;

import cn.edu.nju.cs.connection.Client;
import cn.edu.nju.cs.connection.Message;
import cn.edu.nju.cs.connection.Server;
import cn.edu.nju.cs.game.screen.PlayScreen;
import cn.edu.nju.cs.game.world.creature.bros.Player;
import cn.edu.nju.cs.game.world.weapon.bomb.BombGlove;
import cn.edu.nju.cs.game.world.weapon.melee.BaseballBat;
import cn.edu.nju.cs.game.world.weapon.throwable.ThrowableWeapon;

public class ConnectionTest {
    private static Client client;
    private static Server server;

    @BeforeClass
    public static void prepareServerAndClient(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Server Started at port : " + Server.SERVER_PORT);
                try {
                    server = new Server();
                    server.startReactor(Server.SERVER_PORT);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();

        client = new Client();
    }

    @Test
    public void testConnectRoom1()
    throws UnknownHostException,IOException{
        Message messageFromServer = client.send(new Message(null, null, -1, 2020, Message.CONNECT_MESSAGE));
        assertEquals(messageFromServer.info(), Message.BACK_CONNECT_ROOM_MESSAGE);
        assertNull(messageFromServer.playScreen());
    }

    @Test
    public void testConnectRoom2()
    throws UnknownHostException,IOException{
        PlayScreen playScreen = new PlayScreen("2020", "Alternate Day", "New World");
        playScreen.setPlayer(playScreen.creatureFactory().newPlayer(playScreen.messages(), "FirstBro"));
        Message messageFromServer = client.send(
            new Message(playScreen, null, -1, 2020, Message.ADD_ROOM_MESSAGE));
        assertEquals(messageFromServer.info(), Message.BACK_ADD_ROOM_MESSAGE);
        messageFromServer = client.send(new Message(
            null, null, 0, 2020,  Message.GET_LATEST_ROOM_MESSAGE));
        assertNotNull(messageFromServer.playScreen());
        assertEquals(messageFromServer.playerIndex(),0);
        assertNotNull(server.getPlayScreen(2020));
    }

    @Test
    public void testConnectRoom3()
    throws UnknownHostException,IOException{
        PlayScreen playScreen = new PlayScreen("2021", "Alternate Day", "New World");
        playScreen.setPlayer(playScreen.creatureFactory().newPlayer(playScreen.messages(), "FirstBro"));
        Message messageFromServer = client.send(new Message(
            playScreen, null, -1, 2021, Message.ADD_ROOM_MESSAGE));
        assertEquals(messageFromServer.info(), Message.BACK_ADD_ROOM_MESSAGE);
        messageFromServer = client.send(new Message(
            null, null, 0, 2021,  Message.GET_LATEST_ROOM_MESSAGE));
        assertNotNull(messageFromServer.playScreen());
        assertEquals(messageFromServer.playerIndex(),0);
        assertNotNull(server.getPlayScreen(2021));

        messageFromServer = client.send(new Message(null, "SecondBro", -1, 2021, Message.ENTER_ROOM_MESSAGE));
        assertEquals(messageFromServer.info(), Message.BACK_ENTER_ROOM_MESSAGE);
        assertEquals(messageFromServer.playerIndex(), 1);
        playScreen = messageFromServer.playScreen();
        assertNotNull(server.getPlayScreen(2021));
        assertEquals(server.getPlayScreen(2021).world().getPlayers().size(), 2);
    }

    @Test
    public void testGetLatestRoom()
    throws UnknownHostException,IOException{
        PlayScreen playScreen = new PlayScreen("2022", "Alternate Day", "New World");
        playScreen.setPlayer(playScreen.creatureFactory().newPlayer(playScreen.messages(), "FirstBro"));
        Message messageFromServer = client.send(new Message(
            playScreen, null, -1, 2022, Message.ADD_ROOM_MESSAGE));
        assertEquals(messageFromServer.info(), Message.BACK_ADD_ROOM_MESSAGE);
        messageFromServer = client.send(new Message(
            null, null, 0, 2022,  Message.GET_LATEST_ROOM_MESSAGE));
        assertNotNull(messageFromServer.playScreen());
        assertEquals(messageFromServer.playerIndex(),0);
        assertNotNull(server.getPlayScreen(2022));

        messageFromServer = client.send(new Message(
            null, null, 0, 2022,  Message.GET_LATEST_ROOM_MESSAGE));
        assertEquals(messageFromServer.info(), Message.BACK_GET_LATEST_ROOM_MESSAGE);
        assertNotNull(messageFromServer.playScreen());
        assertEquals(messageFromServer.playerIndex(), 0);
        assertNotNull(messageFromServer.player());
    }

    @Test
    public void testMove()
    throws UnknownHostException,IOException{
        PlayScreen playScreen = new PlayScreen("2023", "Alternate Day", "New World");
        playScreen.setPlayer(playScreen.creatureFactory().newPlayer(playScreen.messages(), "FirstBro"));
        Message messageFromServer = client.send(new Message(
            playScreen, null, -1, 2023, Message.ADD_ROOM_MESSAGE));
        messageFromServer = client.send(new Message(
            null, null, 0, 2023,  Message.GET_LATEST_ROOM_MESSAGE));
        assertNotNull(messageFromServer.playScreen());
        assertEquals(messageFromServer.playerIndex(),0);
        assertNotNull(server.getPlayScreen(2023));

        int oldX = messageFromServer.player().x(), oldY = messageFromServer.player().y();
        messageFromServer = client.send(new Message(
            null, null, 0, 2023, Message.MOVE_DOWN_MESSAGE));
        assertEquals(messageFromServer.info(), Message.BACK_MOVE_MESSAGE);
        messageFromServer = client.send(new Message(
            null, null, 0, 2023,  Message.GET_LATEST_ROOM_MESSAGE));
        int newX = messageFromServer.player().x(), newY = messageFromServer.player().y();
        assertEquals(oldX, newX);
        assertEquals(oldY+1, newY);

        oldX = newX;
        oldY = newY;
        messageFromServer = client.send(new Message(
            null, null, 0, 2023, Message.MOVE_UP_MESSAGE));
        assertEquals(messageFromServer.info(), Message.BACK_MOVE_MESSAGE);
        messageFromServer = client.send(new Message(
            null, null, 0, 2023, Message.GET_LATEST_ROOM_MESSAGE));
        newX = messageFromServer.player().x();
        newY = messageFromServer.player().y();
        assertEquals(oldX, newX);
        assertEquals(oldY-1, newY);

        oldX = newX;
        oldY = newY;
        messageFromServer = client.send(new Message(
            null, null, 0, 2023, Message.MOVE_LEFT_MESSAGE));
        assertEquals(messageFromServer.info(), Message.BACK_MOVE_MESSAGE);
        messageFromServer = client.send(new Message(
            null, null, 0, 2023, Message.GET_LATEST_ROOM_MESSAGE));
        newX = messageFromServer.player().x();
        newY = messageFromServer.player().y();
        assertEquals(oldX-1, newX);
        assertEquals(oldY, newY);

        oldX = newX;
        oldY = newY;
        messageFromServer = client.send(new Message(
            null, null, 0, 2023, Message.MOVE_RIGHT_MESSAGE));
        assertEquals(messageFromServer.info(), Message.BACK_MOVE_MESSAGE);
        messageFromServer = client.send(new Message(
            null, null, 0, 2023, Message.GET_LATEST_ROOM_MESSAGE));
        newX = messageFromServer.player().x();
        newY = messageFromServer.player().y();
        assertEquals(oldX+1, newX);
        assertEquals(oldY, newY);
    }

    @Test
    public void testAttack()
    throws UnknownHostException,IOException{
        PlayScreen playScreen = new PlayScreen("2024", "Alternate Day", "New World");
        playScreen.setPlayer(playScreen.creatureFactory().newPlayer(playScreen.messages(), "FirstBro"));
        Message messageFromServer = client.send(new Message(
            playScreen, null, -1, 2024, Message.ADD_ROOM_MESSAGE));
        assertEquals(messageFromServer.info(), Message.BACK_ADD_ROOM_MESSAGE);
        messageFromServer = client.send(new Message(
            null, null, 0, 2024, Message.GET_LATEST_ROOM_MESSAGE));
        assertNotNull(messageFromServer.playScreen());
        assertEquals(messageFromServer.playerIndex(),0);
        assertNotNull(server.getPlayScreen(2024));

        Player player = playScreen.world().searchPlayer(0);
        assertTrue(player.weaponFactory().curWeapon().isActive());
        messageFromServer = client.send(new Message(
            null, null, 0, 2024, Message.ATTACK_MESSAGE));
        assertEquals(messageFromServer.info(), Message.BACK_ATTACK_MESSAGE);
        messageFromServer = client.send(new Message(
            null, null, 0, 2024, Message.GET_LATEST_ROOM_MESSAGE));
        player = messageFromServer.playScreen().world().searchPlayer(
            messageFromServer.playerIndex());
        assertFalse(player.weaponFactory().curWeapon().isActive());
    }

    @Test
    public void testBigAttack()
    throws UnknownHostException,IOException{
        PlayScreen playScreen = new PlayScreen("2025", "Alternate Day", "New World");
        playScreen.setPlayer(playScreen.creatureFactory().newPlayer(playScreen.messages(), "FirstBro"));
        Message messageFromServer = client.send(new Message(
            playScreen, null, -1, 2025, Message.ADD_ROOM_MESSAGE));
        assertEquals(messageFromServer.info(), Message.BACK_ADD_ROOM_MESSAGE);
        messageFromServer = client.send(new Message(
            null, null, 0, 2025, Message.GET_LATEST_ROOM_MESSAGE));
        assertNotNull(messageFromServer.playScreen());
        assertEquals(messageFromServer.playerIndex(),0);
        assertNotNull(server.getPlayScreen(2025));

        Player player = playScreen.world().searchPlayer(0);
        assertTrue(player.weaponFactory().curWeapon().isActive());
        messageFromServer = client.send(new Message(
            null, null, 0, 2025, Message.BIG_ATTACK_MESSAGE));
        assertEquals(messageFromServer.info(), Message.BACK_BIG_ATTACK_MESSAGE);
        messageFromServer = client.send(new Message(
            null, null, 0, 2025, Message.GET_LATEST_ROOM_MESSAGE));
        player = messageFromServer.playScreen().world().searchPlayer(
            messageFromServer.playerIndex());
        assertTrue(player.weaponFactory().curWeapon().isActive());
    }

    @Test
    public void testSwitchWeapon()
    throws UnknownHostException,IOException{
        PlayScreen playScreen = new PlayScreen("2026", "Alternate Day", "New World");
        playScreen.setPlayer(playScreen.creatureFactory().newPlayer(playScreen.messages(), "FirstBro"));
        Message messageFromServer = client.send(new Message(
            playScreen, null, -1, 2026, Message.ADD_ROOM_MESSAGE));
        assertEquals(messageFromServer.info(), Message.BACK_ADD_ROOM_MESSAGE);
        messageFromServer = client.send(new Message(
            null, null, 0, 2026, Message.GET_LATEST_ROOM_MESSAGE));
        assertNotNull(messageFromServer.playScreen());
        assertEquals(messageFromServer.playerIndex(),0);
        assertNotNull(server.getPlayScreen(2026));

        Player player = playScreen.world().searchPlayer(0);
        assertEquals(player.weaponFactory().curWeapon().name(), BaseballBat.NAME);
        messageFromServer = client.send(new Message(
            null, null, 0, 2026, Message.SWITCH_WEAPON_MESSAGE));
        assertEquals(messageFromServer.info(), Message.BACK_SWITCH_WEAPON_MESSAGE);
        messageFromServer = client.send(new Message(
            null, null, 0, 2026, Message.GET_LATEST_ROOM_MESSAGE));
        player = messageFromServer.playScreen().world().searchPlayer(
            messageFromServer.playerIndex());
        assertEquals(player.weaponFactory().curWeapon().name(), BombGlove.NAME);
    }

    @Test
    public void setBomb()
    throws UnknownHostException,IOException{
        PlayScreen playScreen = new PlayScreen("2027", "Alternate Day", "New World");
        playScreen.setPlayer(playScreen.creatureFactory().newPlayer(playScreen.messages(), "FirstBro"));
        Message messageFromServer = client.send(new Message(
            playScreen, null, -1, 2027, Message.ADD_ROOM_MESSAGE));
        assertEquals(messageFromServer.info(), Message.BACK_ADD_ROOM_MESSAGE);
        messageFromServer = client.send(new Message(
            null, null, 0, 2027, Message.GET_LATEST_ROOM_MESSAGE));
        assertNotNull(messageFromServer.playScreen());
        assertEquals(messageFromServer.playerIndex(),0);
        assertNotNull(server.getPlayScreen(2027));

        Player player = playScreen.world().searchPlayer(0);
        assertEquals(player.world().getBombsSize(), 0);
        messageFromServer = client.send(new Message(
            null, null, 0, 2027, Message.SET_BOMB_MESSAGE));
        assertEquals(messageFromServer.info(), Message.BACK_SET_BOMB_MESSAGE);
        messageFromServer = client.send(new Message(
            null, null, 0, 2027, Message.GET_LATEST_ROOM_MESSAGE));
        player = messageFromServer.playScreen().world().searchPlayer(
            messageFromServer.playerIndex());
        assertEquals(player.world().getBombsSize(), 1);

    }

    @Test
    public void testThrowableWeaponMove()
    throws UnknownHostException,IOException{
        PlayScreen playScreen = new PlayScreen("2028", "Alternate Day", "New World");
        playScreen.setPlayer(playScreen.creatureFactory().newPlayer(playScreen.messages(), "SeventhBro"));
        Message messageFromServer = client.send(new Message(
            playScreen, null, -1, 2028, Message.ADD_ROOM_MESSAGE));
        assertEquals(messageFromServer.info(), Message.BACK_ADD_ROOM_MESSAGE);
        messageFromServer = client.send(new Message(
            null, null, 0, 2028, Message.GET_LATEST_ROOM_MESSAGE));
        assertNotNull(messageFromServer.playScreen());
        assertEquals(messageFromServer.playerIndex(),0);
        assertNotNull(server.getPlayScreen(2028));

        int dx = ((ThrowableWeapon)messageFromServer.player().weaponFactory().curWeapon()).dx(),
            dy = ((ThrowableWeapon)messageFromServer.player().weaponFactory().curWeapon()).dy();
        assertEquals(dx, 0);
        assertEquals(dy, 0);
        messageFromServer = client.send(new Message(
            null, null, 0, 2028, Message.THROWABLE_WEAPON_MOVE_DOWN_MESSAGE));
        assertEquals(messageFromServer.info(), Message.BACK_THROWABLE_WEAPON_MOVE_MESSAGE);
        messageFromServer = client.send(new Message(
            null, null, 0, 2028, Message.GET_LATEST_ROOM_MESSAGE));
        dx = ((ThrowableWeapon)messageFromServer.player().weaponFactory().curWeapon()).dx();
        dy = ((ThrowableWeapon)messageFromServer.player().weaponFactory().curWeapon()).dy();
        assertEquals(dx, 0);
        assertEquals(dy, 1);

        messageFromServer = client.send(new Message(
            null, null, 0, 2028, Message.THROWABLE_WEAPON_MOVE_UP_MESSAGE));
        assertEquals(messageFromServer.info(), Message.BACK_THROWABLE_WEAPON_MOVE_MESSAGE);
        messageFromServer = client.send(new Message(
            null, null, 0, 2028, Message.GET_LATEST_ROOM_MESSAGE));
        dx = ((ThrowableWeapon)messageFromServer.player().weaponFactory().curWeapon()).dx();
        dy = ((ThrowableWeapon)messageFromServer.player().weaponFactory().curWeapon()).dy();
        assertEquals(dx, 0);
        assertEquals(dy, 0);

        messageFromServer = client.send(new Message(
            null, null, 0, 2028, Message.THROWABLE_WEAPON_MOVE_LEFT_MESSAGE));
        assertEquals(messageFromServer.info(), Message.BACK_THROWABLE_WEAPON_MOVE_MESSAGE);
        messageFromServer = client.send(new Message(
            null, null, 0, 2028, Message.GET_LATEST_ROOM_MESSAGE));
        dx = ((ThrowableWeapon)messageFromServer.player().weaponFactory().curWeapon()).dx();
        dy = ((ThrowableWeapon)messageFromServer.player().weaponFactory().curWeapon()).dy();
        assertEquals(dx, -1);
        assertEquals(dy, 0);

        messageFromServer = client.send(new Message(
            null, null, 0, 2028, Message.THROWABLE_WEAPON_MOVE_RIGHT_MESSAGE));
        assertEquals(messageFromServer.info(), Message.BACK_THROWABLE_WEAPON_MOVE_MESSAGE);
        messageFromServer = client.send(new Message(
            null, null, 0, 2028, Message.GET_LATEST_ROOM_MESSAGE));
        dx = ((ThrowableWeapon)messageFromServer.player().weaponFactory().curWeapon()).dx();
        dy = ((ThrowableWeapon)messageFromServer.player().weaponFactory().curWeapon()).dy();
        assertEquals(dx, 0);
        assertEquals(dy, 0);
    }

}
