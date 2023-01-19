package cn.edu.nju.cs;

import org.junit.Before;
import org.junit.Test;

import cn.edu.nju.cs.game.asciiPanel.AsciiFont;
import cn.edu.nju.cs.game.asciiPanel.AsciiPanel;
import cn.edu.nju.cs.game.screen.PlayScreen;
import cn.edu.nju.cs.game.screen.ReviewScreen;
import cn.edu.nju.cs.game.screen.Screen;
import cn.edu.nju.cs.game.screen.StartScreen;

import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.JFrame;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.awt.AWTException;

public class ReviewScreenTest {
    private StartScreen startScreen;
    private PlayScreen playScreen;
    private ReviewScreen reviewScreen;
    private AsciiPanel terminal;
    private Screen screen;

    @Before
    public void prepareScreens() throws AWTException{
        this.startScreen = new StartScreen();
        this.screen = startScreen;
        this.terminal = new AsciiPanel(100, 50, AsciiFont.KENNEY_1BIT_PACK_17_17);
        this.playScreen = null;
        this.reviewScreen = null;
    }

    @Test
    public void testReviewRecord(){
        respondToUserInput(newKeyEvent(KeyEvent.VK_DOWN));
        respondToUserInput(newKeyEvent(KeyEvent.VK_DOWN));
        respondToUserInput(newKeyEvent(KeyEvent.VK_ENTER));
        this.startScreen.displayOutput(this.terminal);
        assertEquals(this.startScreen.stage(), 1);
        assertEquals(this.startScreen.branchIndex(), 2);

        String path = "./src/main/resources/record";
		File dir = new File(path);
		File[] files = dir.listFiles();

        assertNull(this.reviewScreen);
        this.startScreen.displayOutput(this.terminal);
        if(files.length > 0){
            respondToUserInput(newKeyEvent(KeyEvent.VK_ENTER));
            assertNotNull(this.reviewScreen);
            assertEquals(this.screen.hashCode(), this.reviewScreen.hashCode());

            this.reviewScreen.displayOutput(this.terminal);
            respondToUserInput(newKeyEvent(KeyEvent.VK_ESCAPE));
            assertEquals(this.screen.hashCode(), this.startScreen.hashCode());
        }
    }

    private void respondToUserInput(KeyEvent key) {
        this.screen = this.screen.respondToUserInput(key);
        if(this.screen instanceof PlayScreen)
            this.playScreen = (PlayScreen)this.screen;
        else if(this.screen instanceof StartScreen)
            this.startScreen = (StartScreen)this.screen;
        else if(this.screen instanceof ReviewScreen)
            this.reviewScreen = (ReviewScreen)this.screen;
    }

    private KeyEvent newKeyEvent(int keyCode){
        return new KeyEvent(new SimpleApplication(), 401, 16700336360121L, 0, keyCode, (char)keyCode);
    }

    private class SimpleApplication extends JFrame {
        public SimpleApplication() {super();}
    }
}