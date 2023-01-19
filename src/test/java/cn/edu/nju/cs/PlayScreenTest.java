package cn.edu.nju.cs;

import org.junit.Before;
import org.junit.Test;

import cn.edu.nju.cs.game.asciiPanel.AsciiFont;
import cn.edu.nju.cs.game.asciiPanel.AsciiPanel;
import cn.edu.nju.cs.game.screen.LoseScreen;
import cn.edu.nju.cs.game.screen.PlayScreen;
import cn.edu.nju.cs.game.screen.Screen;
import cn.edu.nju.cs.game.world.item.DoorPairs;
import cn.edu.nju.cs.game.world.item.Tile;

import java.awt.event.KeyEvent;

import javax.swing.JFrame;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.awt.AWTException;

public class PlayScreenTest {
    private PlayScreen playScreen;
    private AsciiPanel terminal;
    private Screen screen;
    private LoseScreen loseScreen;

    @Before
    public void prepareScreens() throws AWTException{
        this.terminal = new AsciiPanel(100, 50, AsciiFont.KENNEY_1BIT_PACK_17_17);
        this.playScreen = new PlayScreen("", "AlterNate Day", "New World");
        this.playScreen.setPlayer(playScreen.creatureFactory().newPlayer(playScreen.messages(), "SeventhBro"));
        this.screen = this.playScreen;
        this.loseScreen = null;
    }

    @Test
    public void testPlayerDead(){
        this.playScreen.displayOutput(this.terminal);

        this.playScreen.player().modifyHP(-(this.playScreen.player().maxHP()));
        assertTrue(this.playScreen.player().hp() <= 0);
        assertNull(this.loseScreen);
        assertEquals(this.playScreen.hashCode(), this.screen.hashCode());

        respondToUserInput(newKeyEvent(KeyEvent.VK_LEFT));
        assertNotNull(this.loseScreen);
        assertEquals(this.loseScreen.hashCode(), this.screen.hashCode());
    }

    @Test
    public void testPlayerBasicMove(){
        int oldX, oldY, newX, newY;

        oldX = this.playScreen.player().x();
        oldY = this.playScreen.player().y();
        respondToUserInput(newKeyEvent(KeyEvent.VK_LEFT));
        newX = this.playScreen.player().x();
        newY = this.playScreen.player().y();
        if(this.playScreen.world().tile(oldX-1, oldY) != Tile.DOOR){
            if(this.playScreen.world().tile(oldX-1, oldY).isGround()){
                assertEquals(oldX-1, newX);
                assertEquals(oldY, newY);
            }
            else{
                assertEquals(oldX, newX);
                assertEquals(oldY, newY);
            }
        }

        this.playScreen.player().setActive(true);
        oldX = this.playScreen.player().x();
        oldY = this.playScreen.player().y();
        respondToUserInput(newKeyEvent(KeyEvent.VK_RIGHT));
        newX = this.playScreen.player().x();
        newY = this.playScreen.player().y();
        if(this.playScreen.world().tile(oldX+1, oldY) != Tile.DOOR){
            if(this.playScreen.world().tile(oldX+1, oldY).isGround()){
                assertEquals(oldX+1, newX);
                assertEquals(oldY, newY);
            }
            else{
                assertEquals(oldX, newX);
                assertEquals(oldY, newY);
            }
        }

        this.playScreen.player().setActive(true);
        oldX = this.playScreen.player().x();
        oldY = this.playScreen.player().y();
        respondToUserInput(newKeyEvent(KeyEvent.VK_UP));
        newX = this.playScreen.player().x();
        newY = this.playScreen.player().y();
        if(this.playScreen.world().tile(oldX, oldY-1) != Tile.DOOR){
            if(this.playScreen.world().tile(oldX, oldY-1).isGround()){
                assertEquals(oldX, newX);
                assertEquals(oldY-1, newY);
            }
            else{
                assertEquals(oldX, newX);
                assertEquals(oldY, newY);
            }
        }

        this.playScreen.player().setActive(true);
        oldX = this.playScreen.player().x();
        oldY = this.playScreen.player().y();
        respondToUserInput(newKeyEvent(KeyEvent.VK_DOWN));
        newX = this.playScreen.player().x();
        newY = this.playScreen.player().y();
        if(this.playScreen.world().tile(oldX, oldY+1) != Tile.DOOR){
            if(this.playScreen.world().tile(oldX, oldY+1).isGround()){
                assertEquals(oldX, newX);
                assertEquals(oldY+1, newY);
            }
            else{
                assertEquals(oldX, newX);
                assertEquals(oldY, newY);
            }
        }

    }

    @Test
    public void testTransferDoor(){
        DoorPairs doorPairs = this.playScreen.world().doorPairs();
        int playerStartX, playerStartY, playerTargetX, playerTargetY;

        int doorStartX = doorPairs.doorPairsList().get(0).getKey().x();
        int doorStartY = doorPairs.doorPairsList().get(0).getKey().y();
        int doorTargetX = doorPairs.doorPairsList().get(0).getValue().x();
        int doorTargetY = doorPairs.doorPairsList().get(0).getValue().y();

        playerStartY = doorStartY;
        if(doorStartX > 0)
            playerStartX = doorStartX-1;
        else
            playerStartX = doorStartX+1;

        this.playScreen.world().setThing(Tile.FLOOR, playerStartX, playerStartY);
        this.playScreen.player().setX(playerStartX);
        this.playScreen.player().setY(playerStartY);
        if(doorStartX > 0)
            respondToUserInput(newKeyEvent(KeyEvent.VK_RIGHT));
        else
            respondToUserInput(newKeyEvent(KeyEvent.VK_LEFT));
        playerTargetX = this.playScreen.player().x();
        playerTargetY = this.playScreen.player().y();

        assertEquals(doorTargetX, playerTargetX);
        assertEquals(doorTargetY, playerTargetY);
    }

    // @Test
    // public void testWeaponAttack(){

    // }

    private void respondToUserInput(KeyEvent key) {
        this.screen = this.screen.respondToUserInput(key);
        if(this.screen instanceof PlayScreen)
            this.playScreen = (PlayScreen)this.screen;
        else if(this.screen instanceof LoseScreen)
            this.loseScreen = (LoseScreen)this.screen;
    }

    private KeyEvent newKeyEvent(int keyCode){
        return new KeyEvent(new SimpleApplication(), 401, 16700336360121L, 0, keyCode, (char)keyCode);
    }

    private class SimpleApplication extends JFrame {
        public SimpleApplication() {super();}
    }
}