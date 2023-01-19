package cn.edu.nju.cs;

import org.junit.Before;
import org.junit.Test;

import cn.edu.nju.cs.game.asciiPanel.AsciiFont;
import cn.edu.nju.cs.game.asciiPanel.AsciiPanel;
import cn.edu.nju.cs.game.screen.PauseScreen;
import cn.edu.nju.cs.game.screen.PlayScreen;
import cn.edu.nju.cs.game.screen.Screen;
import cn.edu.nju.cs.game.screen.StartScreen;

import java.awt.event.KeyEvent;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFrame;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.awt.AWTException;

public class PauseScreenTest {
    private StartScreen startScreen;
    private PauseScreen pauseScreen;
    private PlayScreen playScreen;
    private Screen screen;
    private AsciiPanel terminal;

    @Before
    public void prepareScreens() throws AWTException{
        this.terminal = new AsciiPanel(100, 50, AsciiFont.KENNEY_1BIT_PACK_17_17);
        this.playScreen = new PlayScreen("", "AlterNate Day", "New World");
        this.screen = playScreen;
        this.startScreen = null;
        this.pauseScreen = null;
        respondToUserInput(newKeyEvent(KeyEvent.VK_ESCAPE));
    }

    @Test
    public void testPauseAndDisplay(){
        assertNotNull(this.pauseScreen);
        assertTrue(this.playScreen.world().worldIsPaused());
        this.pauseScreen.displayOutput(this.terminal);
        respondToUserInput(newKeyEvent(KeyEvent.VK_ESCAPE));
        assertFalse(this.playScreen.world().worldIsPaused());
    }

    @Test
    public void testRestartGameAndDisplay(){
        assertNotNull(this.pauseScreen);
        assertTrue(this.playScreen.world().worldIsPaused());
        assertNull(this.startScreen);

        respondToUserInput(newKeyEvent(KeyEvent.VK_DOWN));
        respondToUserInput(newKeyEvent(KeyEvent.VK_ENTER));
        this.pauseScreen.displayOutput(this.terminal);
        respondToUserInput(newKeyEvent(KeyEvent.VK_LEFT));
        this.pauseScreen.displayOutput(this.terminal);
        respondToUserInput(newKeyEvent(KeyEvent.VK_RIGHT));
        this.pauseScreen.displayOutput(this.terminal);
        respondToUserInput(newKeyEvent(KeyEvent.VK_ENTER));
        this.pauseScreen.displayOutput(this.terminal);
        assertNull(this.startScreen);

        respondToUserInput(newKeyEvent(KeyEvent.VK_ENTER));
        this.pauseScreen.displayOutput(this.terminal);
        respondToUserInput(newKeyEvent(KeyEvent.VK_LEFT));
        this.pauseScreen.displayOutput(this.terminal);
        respondToUserInput(newKeyEvent(KeyEvent.VK_ENTER));
        this.pauseScreen.displayOutput(this.terminal);
        assertNotNull(this.startScreen);
    }

    @Test
    public void testSaveMapAndDisplay(){
        assertNotNull(this.pauseScreen);
        assertTrue(this.playScreen.world().worldIsPaused());

        Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("_yyyy-MM-dd_HH-mm-ss");
		String timeString = simpleDateFormat.format(date);
        respondToUserInput(newKeyEvent(KeyEvent.VK_ENTER));
        this.pauseScreen.displayOutput(this.terminal);
        respondToUserInput(newKeyEvent(KeyEvent.VK_LEFT));
        this.pauseScreen.displayOutput(this.terminal);
        respondToUserInput(newKeyEvent(KeyEvent.VK_RIGHT));
        this.pauseScreen.displayOutput(this.terminal);
        respondToUserInput(newKeyEvent(KeyEvent.VK_ENTER));
        this.pauseScreen.displayOutput(this.terminal);
        assertFalse(deleteFile("./src/main/resources/world/World"+timeString+".txt"));

        date = new Date(System.currentTimeMillis());
		simpleDateFormat = new SimpleDateFormat("_yyyy-MM-dd_HH-mm-ss");
		timeString = simpleDateFormat.format(date);
        respondToUserInput(newKeyEvent(KeyEvent.VK_ENTER));
        this.pauseScreen.displayOutput(this.terminal);
        respondToUserInput(newKeyEvent(KeyEvent.VK_LEFT));
        this.pauseScreen.displayOutput(this.terminal);
        respondToUserInput(newKeyEvent(KeyEvent.VK_ENTER));
        this.pauseScreen.displayOutput(this.terminal);
        assertTrue(deleteFile("./src/main/resources/world/World"+timeString+".txt"));
    }

    @Test
    public void testSnapShotAndDisplay(){
        assertNotNull(this.pauseScreen);
        assertTrue(this.playScreen.world().worldIsPaused());

        Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("_yyyy-MM-dd_HH-mm-ss");
		String timeString = simpleDateFormat.format(date);
        respondToUserInput(newKeyEvent(KeyEvent.VK_DOWN));
        this.pauseScreen.displayOutput(this.terminal);
        respondToUserInput(newKeyEvent(KeyEvent.VK_DOWN));
        this.pauseScreen.displayOutput(this.terminal);
        respondToUserInput(newKeyEvent(KeyEvent.VK_ENTER));
        this.pauseScreen.displayOutput(this.terminal);
        respondToUserInput(newKeyEvent(KeyEvent.VK_LEFT));
        this.pauseScreen.displayOutput(this.terminal);
        respondToUserInput(newKeyEvent(KeyEvent.VK_RIGHT));
        this.pauseScreen.displayOutput(this.terminal);
        respondToUserInput(newKeyEvent(KeyEvent.VK_ENTER));
        this.pauseScreen.displayOutput(this.terminal);
        assertFalse(deleteFile("./src/main/resources/snapShot/SnapShot"+timeString+".txt"));

        date = new Date(System.currentTimeMillis());
		simpleDateFormat = new SimpleDateFormat("_yyyy-MM-dd_HH-mm-ss");
		timeString = simpleDateFormat.format(date);
        respondToUserInput(newKeyEvent(KeyEvent.VK_ENTER));
        this.pauseScreen.displayOutput(this.terminal);
        respondToUserInput(newKeyEvent(KeyEvent.VK_LEFT));
        this.pauseScreen.displayOutput(this.terminal);
        respondToUserInput(newKeyEvent(KeyEvent.VK_ENTER));
        this.pauseScreen.displayOutput(this.terminal);
        assertTrue(deleteFile("./src/main/resources/snapShot/SnapShot"+timeString+".txt"));
    }

    @Test
    public void testRecordAndDisplay(){
        assertNotNull(this.pauseScreen);
        assertTrue(this.playScreen.world().worldIsPaused());

        Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("_yyyy-MM-dd_HH-mm-ss");
		String timeString = simpleDateFormat.format(date);
        respondToUserInput(newKeyEvent(KeyEvent.VK_DOWN));
        this.pauseScreen.displayOutput(this.terminal);
        respondToUserInput(newKeyEvent(KeyEvent.VK_DOWN));
        this.pauseScreen.displayOutput(this.terminal);
        respondToUserInput(newKeyEvent(KeyEvent.VK_DOWN));
        this.pauseScreen.displayOutput(this.terminal);
        respondToUserInput(newKeyEvent(KeyEvent.VK_ENTER));
        this.pauseScreen.displayOutput(this.terminal);
        respondToUserInput(newKeyEvent(KeyEvent.VK_LEFT));
        this.pauseScreen.displayOutput(this.terminal);
        respondToUserInput(newKeyEvent(KeyEvent.VK_RIGHT));
        this.pauseScreen.displayOutput(this.terminal);
        respondToUserInput(newKeyEvent(KeyEvent.VK_ENTER));
        this.pauseScreen.displayOutput(this.terminal);
        assertFalse(deleteFile("./src/main/resources/record/Record"+timeString+".txt"));


        date = new Date(System.currentTimeMillis());
		simpleDateFormat = new SimpleDateFormat("_yyyy-MM-dd_HH-mm-ss");
		timeString = simpleDateFormat.format(date);
        respondToUserInput(newKeyEvent(KeyEvent.VK_ENTER));
        this.pauseScreen.displayOutput(this.terminal);
        respondToUserInput(newKeyEvent(KeyEvent.VK_LEFT));
        this.pauseScreen.displayOutput(this.terminal);
        respondToUserInput(newKeyEvent(KeyEvent.VK_ENTER));
        this.pauseScreen.displayOutput(this.terminal);
        respondToUserInput(newKeyEvent(KeyEvent.VK_ENTER));
        this.pauseScreen.displayOutput(this.terminal);
        respondToUserInput(newKeyEvent(KeyEvent.VK_LEFT));
        this.pauseScreen.displayOutput(this.terminal);
        respondToUserInput(newKeyEvent(KeyEvent.VK_ENTER));
        this.pauseScreen.displayOutput(this.terminal);
        assertTrue(deleteFile("./src/main/resources/record/Record"+timeString+".txt"));
    }

    private void respondToUserInput(KeyEvent key) {
        this.screen = this.screen.respondToUserInput(key);
        if(this.screen instanceof PlayScreen)
            this.playScreen = (PlayScreen)this.screen;
        else if(this.screen instanceof PauseScreen)
            this.pauseScreen = (PauseScreen)this.screen;
        else if(this.screen instanceof StartScreen)
            this.startScreen = (StartScreen)this.screen;
    }

    private KeyEvent newKeyEvent(int keyCode){
        return new KeyEvent(new SimpleApplication(), 401, 16700336360121L, 0, keyCode, (char)keyCode);
    }

    private boolean deleteFile(String sPath) {
        boolean flag = false;
        File file = new File(sPath);
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }

    private class SimpleApplication extends JFrame {
        public SimpleApplication() {super();}
    }
}