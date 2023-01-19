package cn.edu.nju.cs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import cn.edu.nju.cs.game.screen.PlayScreen;
import cn.edu.nju.cs.game.world.creature.bros.Player;
import cn.edu.nju.cs.game.world.item.World;
import cn.edu.nju.cs.game.world.item.WorldBuilder;
import cn.edu.nju.cs.logger.Recorder;

public class RecorderTest {
    WorldBuilder worldBuilder;
    World world;
    PlayScreen playScreen;

    @Before
    public void prepareWorld(){
        this.worldBuilder = new WorldBuilder(90, 31);
        world = worldBuilder.makeCaves().build();
        playScreen.setPlayer(playScreen.creatureFactory().newPlayer
                            (playScreen.messages(), "FirstBro"));
    }

    @Before
    public void preparePlayScreen(){
        this.playScreen = new PlayScreen("", "AlterNate Day", "New World");
    }

    @Test
    public void testStoreWorldNull(){
        worldBuilder.storeWorld(null);
    }

    @Test(expected = IOException.class)
    public void testLoadWorldIOException()
    throws IOException{
        try {
            Recorder.readWorld("xanilsn");
        } catch (IOException e) {
            throw e;
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    @Test
    public void testStoreAndLoadWorld(){
        Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("_yyyy-MM-dd_HH-mm-ss");
		String timeString = simpleDateFormat.format(date);
        worldBuilder.storeWorld(world);
        World copyWorld = worldBuilder.loadWorld("World"+timeString);

        assertEquals(this.world.width(), copyWorld.width());
        assertEquals(this.world.height(), copyWorld.height());
        assertEquals(this.world.worldIsPaused(), false);
        for(int i = 0; i < world.width(); i++){
            for(int j = 0; j < world.height(); j++){
                assertEquals(this.world.tile(i, j).glyph(), copyWorld.tile(i, j).glyph());
            }
        }
        assertEquals(this.world.map().doorPairs().doorPairsList().size(),copyWorld.map().doorPairs().doorPairsList().size());
        for(int i = 0; i < this.world.map().doorPairs().doorPairsList().size(); i++){
            assertEquals(this.world.map().doorPairs().doorPairsList().get(i).getKey().x(),
                            copyWorld.map().doorPairs().doorPairsList().get(i).getKey().x());
            assertEquals(this.world.map().doorPairs().doorPairsList().get(i).getKey().y(),
                            copyWorld.map().doorPairs().doorPairsList().get(i).getKey().y());
            assertEquals(this.world.map().doorPairs().doorPairsList().get(i).getValue().x(),
                            copyWorld.map().doorPairs().doorPairsList().get(i).getValue().x());
            assertEquals(this.world.map().doorPairs().doorPairsList().get(i).getValue().y(),
                            copyWorld.map().doorPairs().doorPairsList().get(i).getValue().y());
        }

        assertEquals(this.world.map().grasses().grassesList().size(),copyWorld.map().grasses().grassesList().size());
        for(int i = 0; i < this.world.map().grasses().grassesList().size(); i++){
            assertEquals(this.world.map().grasses().grassesList().get(i).x(),
                            copyWorld.map().grasses().grassesList().get(i).x());
            assertEquals(this.world.map().grasses().grassesList().get(i).y(),
                            copyWorld.map().grasses().grassesList().get(i).y());
        }

        assertEquals(this.world.map().firePiles().firePilesList().size(),copyWorld.map().firePiles().firePilesList().size());
        for(int i = 0; i < this.world.map().firePiles().firePilesList().size(); i++){
            assertEquals(this.world.map().firePiles().firePilesList().get(i).x(),
                            copyWorld.map().firePiles().firePilesList().get(i).x());
            assertEquals(this.world.map().firePiles().firePilesList().get(i).y(),
                            copyWorld.map().firePiles().firePilesList().get(i).y());
        }

        assertEquals(this.world.boxes().boxesList().size(),copyWorld.map().boxes().boxesList().size());
        for(int i = 0; i < this.world.map().boxes().boxesList().size(); i++){
            assertEquals(this.world.map().boxes().boxesList().get(i).x(),
                            copyWorld.map().boxes().boxesList().get(i).x());
            assertEquals(this.world.map().boxes().boxesList().get(i).y(),
                            copyWorld.map().boxes().boxesList().get(i).y());
            assertEquals(this.world.map().boxes().boxesList().get(i).weapon().glyph(),
                            copyWorld.map().boxes().boxesList().get(i).weapon().glyph());
        }

        assertTrue(deleteFile("./src/main/resources/world/World"+timeString+".txt"));
    }

    @Test
    public void testSnapShotAndReadSnapShot(){
        Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("_yyyy-MM-dd_HH-mm-ss");
		String timeString = simpleDateFormat.format(date);
        try {
            Recorder.snapShot(playScreen);
        } catch (Exception e) {}
        PlayScreen copyPlayScreen = null;
        try {
            copyPlayScreen= Recorder.readSnapShot("SnapShot"+timeString);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }

        this.world = this.playScreen.world();
        World copyWorld = copyPlayScreen.world();
        assertEquals(this.world.width(), copyWorld.width());
        assertEquals(this.world.height(), copyWorld.height());
        assertEquals(this.world.worldIsPaused(), false);
        for(int i = 0; i < world.width(); i++){
            for(int j = 0; j < world.height(); j++){
                assertEquals(this.world.tile(i, j).glyph(), copyWorld.tile(i, j).glyph());
            }
        }
        assertEquals(this.world.map().doorPairs().doorPairsList().size(),copyWorld.map().doorPairs().doorPairsList().size());
        for(int i = 0; i < this.world.map().doorPairs().doorPairsList().size(); i++){
            assertEquals(this.world.map().doorPairs().doorPairsList().get(i).getKey().x(),
                            copyWorld.map().doorPairs().doorPairsList().get(i).getKey().x());
            assertEquals(this.world.map().doorPairs().doorPairsList().get(i).getKey().y(),
                            copyWorld.map().doorPairs().doorPairsList().get(i).getKey().y());
            assertEquals(this.world.map().doorPairs().doorPairsList().get(i).getValue().x(),
                            copyWorld.map().doorPairs().doorPairsList().get(i).getValue().x());
            assertEquals(this.world.map().doorPairs().doorPairsList().get(i).getValue().y(),
                            copyWorld.map().doorPairs().doorPairsList().get(i).getValue().y());
        }

        assertEquals(this.world.map().grasses().grassesList().size(),copyWorld.map().grasses().grassesList().size());
        for(int i = 0; i < this.world.map().grasses().grassesList().size(); i++){
            assertEquals(this.world.map().grasses().grassesList().get(i).x(),
                            copyWorld.map().grasses().grassesList().get(i).x());
            assertEquals(this.world.map().grasses().grassesList().get(i).y(),
                            copyWorld.map().grasses().grassesList().get(i).y());
        }

        assertEquals(this.world.map().firePiles().firePilesList().size(),copyWorld.map().firePiles().firePilesList().size());
        for(int i = 0; i < this.world.map().firePiles().firePilesList().size(); i++){
            assertEquals(this.world.map().firePiles().firePilesList().get(i).x(),
                            copyWorld.map().firePiles().firePilesList().get(i).x());
            assertEquals(this.world.map().firePiles().firePilesList().get(i).y(),
                            copyWorld.map().firePiles().firePilesList().get(i).y());
        }

        assertEquals(this.world.boxes().boxesList().size(),copyWorld.map().boxes().boxesList().size());
        for(int i = 0; i < this.world.map().boxes().boxesList().size(); i++){
            assertEquals(this.world.map().boxes().boxesList().get(i).x(),
                            copyWorld.map().boxes().boxesList().get(i).x());
            assertEquals(this.world.map().boxes().boxesList().get(i).y(),
                            copyWorld.map().boxes().boxesList().get(i).y());
            assertEquals(this.world.map().boxes().boxesList().get(i).weapon().glyph(),
                            copyWorld.map().boxes().boxesList().get(i).weapon().glyph());
        }

        Player player1 = this.playScreen.player();
        Player player2 = copyPlayScreen.player();
        assertEquals(player1.attackValue(), player2.attackValue());
        assertEquals(player1.defenseValue(), player2.defenseValue());
        assertEquals(player1.direction(), player2.direction());
        assertEquals(player1.glyph(), player2.glyph());
        assertEquals(player1.hp(), player2.hp());
        assertEquals(player1.maxHP(), player2.maxHP());
        assertEquals(player1.isVisible(), player2.isVisible());
        assertEquals(player1.moveSpeed(), player2.moveSpeed());

        assertEquals(this.playScreen.screenWidth(), copyPlayScreen.screenWidth());
        assertEquals(this.playScreen.screenHeight(), copyPlayScreen.screenHeight());
        assertEquals(this.playScreen.musicName(), copyPlayScreen.musicName());
        assertEquals(this.playScreen.playScreenAddress(), copyPlayScreen.playScreenAddress());
        assertEquals(this.playScreen.recordFlag(), copyPlayScreen.recordFlag());

        assertTrue(deleteFile("./src/main/resources/snapShot/SnapShot"+timeString+".txt"));
    }

    @Test
    public void testRecord(){
        Recorder recorder = new Recorder();
        recorder.saveSnapShot(playScreen);
        recorder.saveSnapShot(playScreen);

        Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("_yyyy-MM-dd_HH-mm-ss");
		String timeString = simpleDateFormat.format(date);
        try {
            Recorder.writeRecorder(recorder);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
        Recorder copyRecorder = null;
        try {
            copyRecorder= Recorder.readRecorder("Record"+timeString);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }

        assertEquals(recorder.playScreens().size(), 2);
        assertEquals(copyRecorder.playScreens().size(), 2);
        // 每次快照的都是副本(深拷贝)
        assertNotEquals(recorder.playScreens().get(0).hashCode(),
                        recorder.playScreens().get(1).hashCode());
        assertNotEquals(copyRecorder.playScreens().get(0).hashCode(),
                        copyRecorder.playScreens().get(1).hashCode());
        assertNotEquals(recorder.playScreens().get(0).player().hashCode(),
                        copyRecorder.playScreens().get(1).player().hashCode());
        assertNotEquals(recorder.playScreens().get(0).world().hashCode(),
                        copyRecorder.playScreens().get(1).world().hashCode());

        this.world = recorder.playScreens().get(0).world();
        World copyWorld = copyRecorder.playScreens().get(0).world();
        assertEquals(this.world.width(), copyWorld.width());
        assertEquals(this.world.height(), copyWorld.height());
        assertEquals(this.world.worldIsPaused(), false);
        for(int i = 0; i < world.width(); i++){
            for(int j = 0; j < world.height(); j++){
                assertEquals(this.world.tile(i, j).glyph(), copyWorld.tile(i, j).glyph());
            }
        }
        assertEquals(this.world.map().doorPairs().doorPairsList().size(),copyWorld.map().doorPairs().doorPairsList().size());
        for(int i = 0; i < this.world.map().doorPairs().doorPairsList().size(); i++){
            assertEquals(this.world.map().doorPairs().doorPairsList().get(i).getKey().x(),
                            copyWorld.map().doorPairs().doorPairsList().get(i).getKey().x());
            assertEquals(this.world.map().doorPairs().doorPairsList().get(i).getKey().y(),
                            copyWorld.map().doorPairs().doorPairsList().get(i).getKey().y());
            assertEquals(this.world.map().doorPairs().doorPairsList().get(i).getValue().x(),
                            copyWorld.map().doorPairs().doorPairsList().get(i).getValue().x());
            assertEquals(this.world.map().doorPairs().doorPairsList().get(i).getValue().y(),
                            copyWorld.map().doorPairs().doorPairsList().get(i).getValue().y());
        }

        assertEquals(this.world.map().grasses().grassesList().size(),copyWorld.map().grasses().grassesList().size());
        for(int i = 0; i < this.world.map().grasses().grassesList().size(); i++){
            assertEquals(this.world.map().grasses().grassesList().get(i).x(),
                            copyWorld.map().grasses().grassesList().get(i).x());
            assertEquals(this.world.map().grasses().grassesList().get(i).y(),
                            copyWorld.map().grasses().grassesList().get(i).y());
        }

        assertEquals(this.world.map().firePiles().firePilesList().size(),copyWorld.map().firePiles().firePilesList().size());
        for(int i = 0; i < this.world.map().firePiles().firePilesList().size(); i++){
            assertEquals(this.world.map().firePiles().firePilesList().get(i).x(),
                            copyWorld.map().firePiles().firePilesList().get(i).x());
            assertEquals(this.world.map().firePiles().firePilesList().get(i).y(),
                            copyWorld.map().firePiles().firePilesList().get(i).y());
        }

        assertEquals(this.world.boxes().boxesList().size(),copyWorld.map().boxes().boxesList().size());
        for(int i = 0; i < this.world.map().boxes().boxesList().size(); i++){
            assertEquals(this.world.map().boxes().boxesList().get(i).x(),
                            copyWorld.map().boxes().boxesList().get(i).x());
            assertEquals(this.world.map().boxes().boxesList().get(i).y(),
                            copyWorld.map().boxes().boxesList().get(i).y());
            assertEquals(this.world.map().boxes().boxesList().get(i).weapon().glyph(),
                            copyWorld.map().boxes().boxesList().get(i).weapon().glyph());
        }

        this.playScreen = recorder.playScreens().get(0);
        PlayScreen copyPlayScreen = copyRecorder.playScreens().get(0);
        Player player1 = this.playScreen.player();
        Player player2 = copyPlayScreen.player();
        assertEquals(player1.attackValue(), player2.attackValue());
        assertEquals(player1.defenseValue(), player2.defenseValue());
        assertEquals(player1.direction(), player2.direction());
        assertEquals(player1.glyph(), player2.glyph());
        assertEquals(player1.hp(), player2.hp());
        assertEquals(player1.maxHP(), player2.maxHP());
        assertEquals(player1.isVisible(), player2.isVisible());
        assertEquals(player1.moveSpeed(), player2.moveSpeed());

        assertEquals(this.playScreen.screenWidth(), copyPlayScreen.screenWidth());
        assertEquals(this.playScreen.screenHeight(), copyPlayScreen.screenHeight());
        assertEquals(this.playScreen.musicName(), copyPlayScreen.musicName());
        assertEquals(this.playScreen.playScreenAddress(), copyPlayScreen.playScreenAddress());
        assertEquals(this.playScreen.recordFlag(), copyPlayScreen.recordFlag());

        deleteFile("./src/main/resources/record/Record"+timeString + ".txt");
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
}
