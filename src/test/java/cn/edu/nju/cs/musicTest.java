package cn.edu.nju.cs;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

import cn.edu.nju.cs.game.screen.StartScreen;
import cn.edu.nju.cs.music.MusicStuff;


public class musicTest {
    @Test
    public void testMusicCouldPlay(){
        MusicStuff musicStuff = new MusicStuff();
        StartScreen startScreen = new StartScreen();

        for(int i = 0; i < startScreen.musics().size(); i++){
            String name = startScreen.musics().get(i).getValue0();
            assertTrue(musicStuff.playMusic("./src/main/resources/" + name + ".wav"));
            musicStuff.stop();
        }
    }
}
