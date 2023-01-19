/*
 *  ┌───┐   ┌───┬───┬───┬───┐ ┌───┬───┬───┬───┐ ┌───┬───┬───┬───┐ ┌───┬───┬───┐
 *  │Esc│   │ F1│ F2│ F3│ F4│ │ F5│ F6│ F7│ F8│ │ F9│F10│F11│F12│ │P/S│S L│P/B│  ┌┐    ┌┐    ┌┐
 *  └───┘   └───┴───┴───┴───┘ └───┴───┴───┴───┘ └───┴───┴───┴───┘ └───┴───┴───┘  └┘    └┘    └┘
 *  ┌───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───────┐ ┌───┬───┬───┐ ┌───┬───┬───┬───┐
 *  │~ `│! 1│@ 2│# 3│$ 4│% 5│^ 6│& 7│* 8│( 9│) 0│_ -│+ =│ BacSp │ │Ins│Hom│PUp│ │N L│ / │ * │ - │
 *  ├───┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─────┤ ├───┼───┼───┤ ├───┼───┼───┼───┤
 *  │ Tab │ Q │ W │ E │ R │ T │ Y │ U │ I │ O │ P │{ [│} ]│ | \ │ │Del│End│PDn│ │ 7 │ 8 │ 9 │   │
 *  ├─────┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴─────┤ └───┴───┴───┘ ├───┼───┼───┤ + │
 *  │ Caps │ A │ S │ D │ F │ G │ H │ J │ K │ L │: ;│" '│ Enter  │               │ 4 │ 5 │ 6 │   │
 *  ├──────┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴────────┤     ┌───┐     ├───┼───┼───┼───┤
 *  │ Shift  │ Z │ X │ C │ V │ B │ N │ M │< ,│> .│? /│  Shift   │     │ ↑ │     │ 1 │ 2 │ 3 │   │
 *  ├─────┬──┴─┬─┴──┬┴───┴───┴───┴───┴───┴──┬┴───┼───┴┬────┬────┤ ┌───┼───┼───┐ ├───┴───┼───┤ E││
 *  │ Ctrl│    │Alt │         Space         │ Alt│    │    │Ctrl│ │ ← │ ↓ │ → │ │   0   │ . │←─┘│
 *  └─────┴────┴────┴───────────────────────┴────┴────┴────┴────┘ └───┴───┴───┘ └───────┴───┴───┘
 * 
 * @Version: 1.0
 * @Author: Xu YangXin
 * @Date: 2022-11-27 17:53:41
 * @LastEditTime: 2023-01-16 16:24:19
 * @LastEditors: Xu YangXin
 * @Description: 此类为开始的选择界面类
 * @FilePath: \jfp-poker-svg\src\main\java\cn\edu\nju\cs\game\screen\StartScreen.java
 * 此项目为南京大学《Java高级程序设计》项目
 */

package cn.edu.nju.cs.game.screen;

import cn.edu.nju.cs.game.asciiPanel.AsciiPanel;
import cn.edu.nju.cs.logger.Recorder;
import cn.edu.nju.cs.music.MusicStuff;

import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.awt.Color;

import org.javatuples.Pair;

public class StartScreen extends RestartScreen {
    private String addr;
    private int playerIndex;
    private List<Pair<String, Color>> characters;
    private int musicNameIndex;
    private List<Pair<String, Color>> musics;
    private int mapNameIndex;
    private List<Pair<String, Color>> maps;
    private int snapShotIndex;
    private List<Pair<String, Color>> snapShots;
    private int recordIndex;
    private List<Pair<String, Color>> records;
    private int stage = 0; // 某个分支功能的进度
    private int branchIndex = 0;// 分支功能的下标
    private List<Pair<String,Color>> branches; // 功能分支
    private MusicStuff musicStuff;

    /**
     * @author: Xu YangXin
     * @return {*}
     * @description: 构造器
     */    
    public StartScreen(){
        this.addr = "";
        this.playerIndex = 0;
        this.stage = 0;
        this.musicNameIndex = 0;
        this.branchIndex = 0;
        this.snapShotIndex = 0;
        this.recordIndex = 0;

        initialBranches();
        initialCharacters();
        initialMusics();
        initialMaps();
        initialSnapShot();
        initialRecord();

        // 播放音乐
        musicStuff = new MusicStuff();
        musicStuff.playMusic("./src/main/resources/Title Screen.wav");
    }

    @Override
    /**
     * @author: Xu YangXin
     * @param {AsciiPanel} terminal 目标终端
     * @return {*}
     * @description: 展示界面内容
     */    
    public void displayOutput(AsciiPanel terminal) {
        terminal.clear();
        switch (this.stage) {
            case 0:
                for(int i = 0; i < this.branches.size(); i++){
                    String brachName = branches.get(i).getValue0();
                    Color color = branches.get(i).getValue1();
                    if(i == branchIndex)
                        terminal.write(padRight(brachName, 29, ' '), 35, 24 + i*2, color, AsciiPanel.brown);
                    else
                        terminal.write(padRight(brachName, 29, ' '), 35, 24 + i*2, color);
                }
                break;
            case 1:
                if(branchIndex == 0){
                    terminal.write("Please Input Room Address:", 37, 24, AsciiPanel.brightYellow, AsciiPanel.brown);
                    terminal.write(addr, 37, 25, AsciiPanel.brightYellow, AsciiPanel.brown);
                }
                else if(branchIndex == 1){
                    terminal.write(padRight("Please Select SNAPSHOT:", 29, ' '), 35 , 2, AsciiPanel.brightYellow, AsciiPanel.brown);
                    int snapIndexStart = Math.max(0, snapShotIndex-21);
                    int showSnapShotNumber = Math.min(22, this.snapShots.size());
                    for(int i = 0; i < showSnapShotNumber; i++){
                        int targetSnapShotIndex = (snapIndexStart + i)%this.snapShots.size();
                        String snapShotName = snapShots.get(targetSnapShotIndex).getValue0();
                        Color color = snapShots.get(targetSnapShotIndex).getValue1();
                        if(targetSnapShotIndex == snapShotIndex)
                            terminal.write(padRight(snapShotName, 29, ' '), 35, 4 + i*2, color, AsciiPanel.brown);
                        else
                            terminal.write(padRight(snapShotName, 29, ' '), 35, 4 + i*2, color);
                    }
                }
                else if(branchIndex == 2){
                    terminal.write(padRight("Please Select RECORD:", 29, ' '), 35 , 2, AsciiPanel.brightYellow, AsciiPanel.brown);
                    int snapIndexStart = Math.max(0, recordIndex-21);
                    int showRecordNumber = Math.min(22, this.records.size());
                    for(int i = 0; i < showRecordNumber; i++){
                        int targetRecordIndex = (snapIndexStart + i)%this.records.size();
                        String recordName = records.get(targetRecordIndex).getValue0();
                        Color color = records.get(targetRecordIndex).getValue1();
                        if(targetRecordIndex == recordIndex)
                            terminal.write(padRight(recordName, 29, ' '), 35, 4 + i*2, color, AsciiPanel.brown);
                        else
                            terminal.write(padRight(recordName, 29, ' '), 35, 4 + i*2, color);
                    }
                }
                break;
            case 2:
                if(branchIndex == 0){
                    terminal.write("Please Select Your Character:", 35 , 18, AsciiPanel.brightYellow, AsciiPanel.brown);
                    for(int i = 0; i < this.characters.size(); i++){
                        String name = characters.get(i).getValue0();
                        Color color = characters.get(i).getValue1();
                        if(i == playerIndex)
                            terminal.write(padRight(name, 29, ' '), 35, 20 + i*2, color, AsciiPanel.brown);
                        else
                        terminal.write(padRight(name, 29, ' '), 35, 20 + i*2, color);
                    }
                }
                else if(branchIndex == 1){
                    terminal.write("Press ENTER to START GAME!", 37, 25, AsciiPanel.brightYellow, AsciiPanel.brown);
                }
                break;
            case 3:
                if(branchIndex == 0){
                    terminal.write(padRight("Please Select BGM:", 29, ' '), 35 , 2, AsciiPanel.brightYellow, AsciiPanel.brown);
                    for(int i = 0; i < this.musics.size(); i++){
                        String musicName = musics.get(i).getValue0();
                        Color color = musics.get(i).getValue1();
                        if(i == musicNameIndex)
                            terminal.write(padRight(musicName, 29, ' '), 35, 4 + i*2, color, AsciiPanel.brown);
                        else
                            terminal.write(padRight(musicName, 29, ' '), 35, 4 + i*2, color);
                    }
                }
                break;
            case 4:
                if(branchIndex == 0){
                    terminal.write(padRight("Please Select MAP:", 29, ' '), 35 , 2, AsciiPanel.brightYellow, AsciiPanel.brown);
                    int mapIndexStart = Math.max(0, mapNameIndex-21);
                    int showMapNumber = Math.min(22, this.maps.size());
                    for(int i = 0; i < showMapNumber; i++){
                        int targetMapIndex = (mapIndexStart + i)%this.maps.size();
                        String mapName = maps.get(targetMapIndex).getValue0();
                        Color color = maps.get(targetMapIndex).getValue1();
                        if(targetMapIndex == mapNameIndex)
                            terminal.write(padRight(mapName, 29, ' '), 35, 4 + i*2, color, AsciiPanel.brown);
                        else
                            terminal.write(padRight(mapName, 29, ' '), 35, 4 + i*2, color);
                    }
                }
                break;
            case 5:
                if(branchIndex == 0){
                    terminal.write("Press ENTER to START GAME!", 37, 25, AsciiPanel.brightYellow, AsciiPanel.brown);
                }
                break;
            default:
                break;
        }
    }

    @Override
    /**
     * @author: Xu YangXin
     * @param {KeyEvent} key 玩家按下的按键
     * @return {*}
     * @description: 响应玩家的键盘操作
     */    
    public Screen respondToUserInput(KeyEvent key) {
        if(key.getKeyCode() == KeyEvent.VK_ESCAPE){
            this.stage = Math.max(0, this.stage-1);
            return this;
        }

        switch (this.stage) {
            case 0:
                selectBranches(key);
                return this;
            case 1:// 选择房间地址/选择快照/选择并查看记录
                if(branchIndex == 0) return this.chooseRoomAddr(key);
                else if(branchIndex == 1) return this.chooseSnapShot(key);
                else if(branchIndex == 2) return this.chooseRecord(key);
            case 2:// 选择人物/确认加载游戏
                if(branchIndex == 0) return this.choosePlayer(key);
                else if(branchIndex == 1)
                {
                    switch (key.getKeyCode()) {
                        case KeyEvent.VK_ENTER:
                        {
                            try {
                                musicStuff.stop();
                                PlayScreen playScreen = Recorder.readSnapShot(snapShots.get(snapShotIndex).getValue0());
                                return new ClientPlaySreen(playScreen);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        default:
                            return this;
                    }
                }
            case 3:// 选择音乐
                if(branchIndex == 0) return this.chooseMusic(key);
            case 4:
                if(branchIndex == 0) return this.chooseMap(key);
            case 5:// 开始游戏
                if(branchIndex == 0)
                {
                    switch (key.getKeyCode()) {
                        case KeyEvent.VK_ENTER:
                        {
                            musicStuff.stop();
                            String playerName = this.characters.get(this.playerIndex).getValue0();
                            String musicName = this.musics.get(this.musicNameIndex).getValue0();
                            String mapName = this.maps.get(this.mapNameIndex).getValue0();
                            // PlayScreen playScreen = new PlayScreen(this.addr, musicName, mapName);
                            // playScreen.setPlayer(playScreen.creatureFactory().newPlayer
                            //                     (playScreen.messages(), playerName));
                            // return playScreen;
                            return new ClientPlaySreen(this.addr, playerName, musicName, mapName);
                        }
                        default:
                            return this;
                    }
                }

            default:
                return null;
        }
    }

    /**
     * @author: Xu YangXin
     * @return {int}
     * @description: 读取启动界面的当前功能分支下标
     */
    public int branchIndex(){ return this.branchIndex; }

    /**
     * @author: Xu YangXin
     * @return {int}
     * @description: 读取启动界面的状态阶段
     */
    public int stage(){ return this.stage; }

    /**
     * @author: Xu YangXin
     * @return {int}
     * @description: 读取启动界面的代表当前记录文件的下标
     */
    public int recordIndex(){ return this.recordIndex; }

    /**
     * @author: Xu YangXin
     * @return {int}
     * @description: 读取启动界面的当前选择的玩家人物的下标
     */
    public int playerIndex(){ return this.playerIndex; }

    /**
     * @author: Xu YangXin
     * @return {int}
     * @description: 读取启动界面的当前选择的地图文件名的下标
     */
    public int mapNameIndex(){ return this.mapNameIndex; }


    /**
     * @author: Xu YangXin
     * @return {int}
     * @description: 读取启动界面的当前选择的闪存文件名的下标
     */
    public int snapShotIndex(){ return this.snapShotIndex; }

    /**
     * @author: Xu YangXin
     * @return {int}
     * @description: 读取启动界面的当前选择的音乐名称下标
     */
    public int musicNameIndex(){ return this.musicNameIndex; }

    /**
     * @author: Xu YangXin
     * @return {int}
     * @description: 读取启动界面的功能分支的数量
     */
    public int branchesSize(){ return this.branches.size(); }

    /**
     * @author: Xu YangXin
     * @param {KeyEvent} key 玩家按下的按键
     * @return {Screen} 处理后的界面
     * @description: 选择功能分支
     */    
    private Screen selectBranches(KeyEvent key){
        // 开始选择功能
        if(stage == 0){ // 尚未结束选择，按下enter键结束功能分支选择
            switch (key.getKeyCode()) {
                case KeyEvent.VK_ENTER:
                    stage++;    // 进入该功能分支的第二阶段
                    break;
                case KeyEvent.VK_UP:
                    branchIndex = (branchIndex-1+this.branches.size())%this.branches.size();
                    break;
                case KeyEvent.VK_DOWN:
                    branchIndex = (branchIndex+1)%this.branches.size();
                    break;
            }
        }
        return this;
    }

    /**
     * @author: Xu YangXin
     * @param {KeyEvent} key 玩家按下的按键
     * @return {Screen} 处理后的界面
     * @description: 选择房间地址
     */    
    private Screen chooseRoomAddr(KeyEvent key){
        // 开始选择房间
        if(branchIndex == 0 && stage == 1){ // 尚未结束输入，按下enter键结束房间地址的输入
            switch (key.getKeyCode()) {
                case KeyEvent.VK_ENTER:
                    stage++;
                    break;
                case KeyEvent.VK_BACK_SPACE:
                    if(addr.length() > 0)
                        addr = addr.substring(0, addr.length()-1);
                    break;
                default:
                    char c = key.getKeyChar();
                    if(c >= '0' && c <= '9')
                        addr += c;
                    break;
            }
        }
        return this;
    }

    /**
     * @author: Xu YangXin
     * @param {KeyEvent} key 玩家按下的按键
     * @return {Screen} 处理后的界面
     * @description: 选择人物
     */    
    private Screen choosePlayer(KeyEvent key){
        // 开始选择人物
        if(branchIndex == 0 && stage == 2){ // 尚未结束选择，按下enter键结束人物选择
            switch (key.getKeyCode()) {
                case KeyEvent.VK_ENTER:
                    stage++;
                    break;
                case KeyEvent.VK_UP:
                    if(playerIndex > 0) playerIndex--;
                    break;
                case KeyEvent.VK_DOWN:
                    if(playerIndex < this.characters.size()-1) playerIndex++;
                    break;
            }
        }
        return this;
    }

    /**
     * @author: Xu YangXin
     * @param {KeyEvent} key 玩家按下的按键
     * @return {Screen} 处理后的界面
     * @description: 选择音乐
     */
    private Screen chooseMusic(KeyEvent key){
        // 开始选择音乐
        if(branchIndex == 0 && stage == 3){ // 尚未结束选择，按下enter键结束音乐选择
            switch (key.getKeyCode()) {
                case KeyEvent.VK_ENTER:
                    stage++;
                    break;
                case KeyEvent.VK_UP:
                    musicNameIndex = (musicNameIndex-1+this.musics.size())%this.musics.size();
                    break;
                case KeyEvent.VK_DOWN:
                    musicNameIndex = (musicNameIndex+1)%this.musics.size();
                    break;
            }
        }
        return this;
    }

    /**
     * @author: Xu YangXin
     * @param {KeyEvent} key 玩家按下的按键
     * @return {Screen} 处理后的界面
     * @description: 选择地图
     */    
    private Screen chooseMap(KeyEvent key){
        // 开始选择地图
        if(branchIndex == 0 && stage == 4){ // 尚未结束选择，按下enter键结束地图选择
            switch (key.getKeyCode()) {
                case KeyEvent.VK_ENTER:
                    stage++;
                    break;
                case KeyEvent.VK_UP:
                    mapNameIndex = (mapNameIndex-1+this.maps.size())%this.maps.size();
                    break;
                case KeyEvent.VK_DOWN:
                    mapNameIndex = (mapNameIndex+1)%this.maps.size();
                    break;
            }
        }
        return this;
    }

    /**
     * @author: Xu YangXin
     * @param {KeyEvent} key 玩家按下的按键
     * @return {Screen} 处理后的界面
     * @description: 选择快照文件
     */    
    private Screen chooseSnapShot(KeyEvent key){
        // 开始选择快照
        if(branchIndex == 1 && stage == 1){ // 尚未结束选择，按下enter键结束快照选择
            switch (key.getKeyCode()) {
                case KeyEvent.VK_ENTER:
                    stage++;
                    break;
                case KeyEvent.VK_UP:
                    snapShotIndex = (snapShotIndex-1+this.snapShots.size())%this.snapShots.size();
                    break;
                case KeyEvent.VK_DOWN:
                    snapShotIndex = (snapShotIndex+1)%this.snapShots.size();
                    break;
            }
        }
        return this;
    }

    /**
     * @author: Xu YangXin
     * @param {KeyEvent} key 玩家按下的按键
     * @return {Screen} 处理后的界面
     * @description: 选择记录文件
     */    
    private Screen chooseRecord(KeyEvent key){
        // 开始选择记录
        if(branchIndex == 2 && stage == 1){ // 尚未结束选择，按下enter键结束记录选择
            switch (key.getKeyCode()) {
                case KeyEvent.VK_ENTER:
                    return new ReviewScreen(this.records.get(this.recordIndex).getValue0(), this);
                case KeyEvent.VK_UP:
                    recordIndex = (recordIndex-1+this.records.size())%this.records.size();
                    break;
                case KeyEvent.VK_DOWN:
                    recordIndex = (recordIndex+1)%this.records.size();
                    break;
            }
        }
        return this;
    }

    /**
     * @author: Xu YangXin
     * @return {List<Pair<String, Color>>} 音乐名的名称和颜色
     * @description: 返回所有音乐的列表
     */    
    public List<Pair<String, Color>> musics(){
        return this.musics;
    }

    /**
     * @author: Xu YangXin
     * @return {*}
     * @description: 初始化功能分支
     */    
    private void initialBranches(){
        this.branches = new ArrayList<>();
        Random random = new Random();
        this.branches.add(Pair.with("Start Game",
                        new Color(random.nextInt(255),
                                random.nextInt(255),
                                random.nextInt(255))));
        this.branches.add(Pair.with("Load Game",
                        new Color(random.nextInt(255),
                                random.nextInt(255),
                                random.nextInt(255))));
        this.branches.add(Pair.with("Record",
                        new Color(random.nextInt(255),
                                random.nextInt(255),
                                random.nextInt(255))));
    }

    /**
     * @author: Xu YangXin
     * @return {*}
     * @description: 初始化玩家人物
     */    
    private void initialCharacters(){
        characters = new ArrayList<>();
        characters.add(Pair.with("FirstBro", Color.red));
        characters.add(Pair.with("SecondBro", Color.orange));
        characters.add(Pair.with("ThirdBro", Color.yellow));
        characters.add(Pair.with("ForthBro", Color.green));
        characters.add(Pair.with("FifthBro", Color.blue));
        characters.add(Pair.with("SixthBro", Color.cyan));
        characters.add(Pair.with("SeventhBro", Color.magenta));
    }

    /**
     * @author: Xu YangXin
     * @return {*}
     * @description: 初始化音乐
     */    
    private void initialMusics(){
        musics =  new ArrayList<>();
        musics.add(Pair.with("AlterNate Day", new Color(100,121,187)));
        musics.add(Pair.with("Alternate Underground", new Color(194,223,243)));
        musics.add(Pair.with("Corruption", new Color(57,106,151)));
        musics.add(Pair.with("Crimson", new Color(98,131,247)));
        musics.add(Pair.with("Desert", new Color(181,202,249)));
        musics.add(Pair.with("Dungeon", new Color(62,95,86)));
        musics.add(Pair.with("Eeric", new Color(66,155,73)));
        musics.add(Pair.with("Ice", new Color(119,175,172)));
        musics.add(Pair.with("Jungle Day", new Color(215,1,1)));
        musics.add(Pair.with("Mushrooms", new Color(37,164,82)));
        musics.add(Pair.with("Ocean", new Color(227,103,14)));
        musics.add(Pair.with("Overworld Day", new Color(187,106,106)));
        musics.add(Pair.with("Overworld Night", new Color(71,136,240)));
        musics.add(Pair.with("Rain", new Color(172,114,235)));
        musics.add(Pair.with("Sandstorm", new Color(40,221,208)));
        musics.add(Pair.with("Space", new Color(77,153,254)));
        musics.add(Pair.with("The Hallow", new Color(231,125,187)));
        musics.add(Pair.with("Town Night", new Color(196,181,90)));
        musics.add(Pair.with("Underground", new Color(108,183,229)));
        musics.add(Pair.with("Underworld", new Color(199,217,27)));
        musics.add(Pair.with("Windy Day", new Color(253,83,183)));
    }

    /**
     * @author: Xu YangXin
     * @return {*}
     * @description: 初始化地图
     */    
    private void initialMaps(){
        Random random = new Random();
        String path = "./src/main/resources/world";
		File dir = new File(path);
		File[] files = dir.listFiles();
        maps = new ArrayList<>();
        maps.add(Pair.with("New World",new Color(random.nextInt(255),
                                                        random.nextInt(255),
                                                        random.nextInt(255))));
		for(File file : files){
			if(!file.isDirectory()){
                String fileName = file.getName();
                fileName = fileName.substring(0, fileName.lastIndexOf("."));
                maps.add(Pair.with(fileName, new Color( random.nextInt(255),
                                                        random.nextInt(255),
                                                        random.nextInt(255))));
            }
		}
    }

    /**
     * @author: Xu YangXin
     * @return {*}
     * @description: 初始化快照
     */    
    private void initialSnapShot(){
        Random random = new Random();
        String path = "./src/main/resources/snapShot";
		File dir = new File(path);
		File[] files = dir.listFiles();
        snapShots = new ArrayList<>();
		for(File file : files){
			if(!file.isDirectory()){
                String fileName = file.getName();
                fileName = fileName.substring(0, fileName.lastIndexOf("."));
                snapShots.add(Pair.with(fileName, new Color(random.nextInt(255),
                                                            random.nextInt(255),
                                                            random.nextInt(255))));
            }
		}
    }

    /**
     * @author: Xu YangXin
     * @return {*}
     * @description: 初始化记录
     */    
    private void initialRecord(){
        Random random = new Random();
        String path = "./src/main/resources/record";
		File dir = new File(path);
		File[] files = dir.listFiles();
        records = new ArrayList<>();
		for(File file : files){
			if(!file.isDirectory()){
                String fileName = file.getName();
                fileName = fileName.substring(0, fileName.lastIndexOf("."));
                records.add(Pair.with(fileName,new Color(random.nextInt(255),
                                                        random.nextInt(255),
                                                        random.nextInt(255))));
            }
		}
    }

    /**
     * @author: Xu YangXin
     * @param {String} src 原始字符串
     * @param {int} len 目标字符串长度
     * @param {char} ch 用于对齐时填充的字符
     * @return {String} 对齐后的字符串
     * @description: 将原始的字符串进行右侧对齐
     */
    public static String padRight(String src, int len, char ch) {
        int diff = len - src.length();
        if (diff <= 0) {
            return src;
        }

        char[] charr = new char[len];
        System.arraycopy(src.toCharArray(), 0, charr, 0, src.length());
        for (int i = src.length(); i < len; i++) {
            charr[i] = ch;
        }
        return new String(charr);
    }

}