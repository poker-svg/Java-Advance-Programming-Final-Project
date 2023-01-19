package cn.edu.nju.cs;

import org.junit.Before;
import org.junit.Test;

import cn.edu.nju.cs.game.asciiPanel.AsciiPanel;
import cn.edu.nju.cs.game.screen.LoseScreen;
import cn.edu.nju.cs.game.screen.Screen;
import cn.edu.nju.cs.game.screen.StartScreen;
import cn.edu.nju.cs.game.screen.WinScreen;

import java.awt.event.KeyEvent;

import javax.swing.JFrame;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class WinAndLoseScreenTest {
    private WinScreen winScreen;
    private LoseScreen loseScreen;
    private StartScreen startScreen;
    private Screen screen;
    private AsciiPanel terminal;

    @Before
    public void prepareScreens(){
        this.winScreen = new WinScreen();
        this.loseScreen = new LoseScreen();
        this.terminal = new AsciiPanel();
        this.startScreen = null;
    }

    @Test
    public void testWinScreen(){
        this.screen = this.winScreen;
        this.winScreen.displayOutput(this.terminal);
        assertNull(this.startScreen);
        respondToUserInput(newKeyEvent(KeyEvent.VK_ENTER));
        assertNotNull(this.startScreen);
    }

    @Test
    public void testLoseScreen(){
        this.screen = this.loseScreen;
        this.loseScreen.displayOutput(this.terminal);
        assertNull(this.startScreen);
        respondToUserInput(newKeyEvent(KeyEvent.VK_ENTER));
        assertNotNull(this.startScreen);
    }


    private void respondToUserInput(KeyEvent key) {
        this.screen = this.screen.respondToUserInput(key);
        if(this.screen instanceof StartScreen)
            this.startScreen = (StartScreen)this.screen;
        else if(this.screen instanceof WinScreen)
            this.winScreen = (WinScreen)this.screen;
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
