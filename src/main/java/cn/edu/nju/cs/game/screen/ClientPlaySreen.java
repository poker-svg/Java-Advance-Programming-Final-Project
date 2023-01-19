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
 * @Date: 2022-12-13 14:41:25
 * @LastEditTime: 2023-01-16 15:33:55
 * @LastEditors: Xu YangXin
 * @Description: 此类代表用户的游戏界面
 * @FilePath: \jfp-poker-svg\src\main\java\cn\edu\nju\cs\game\screen\ClientPlaySreen.java
 * 此项目为南京大学《Java高级程序设计》项目
 */

package cn.edu.nju.cs.game.screen;

import cn.edu.nju.cs.connection.Client;
import cn.edu.nju.cs.game.asciiPanel.AsciiPanel;
import cn.edu.nju.cs.game.world.creature.bros.Player;
import cn.edu.nju.cs.logger.Recorder;
import cn.edu.nju.cs.music.MusicStuff;

import java.awt.event.KeyEvent;
import java.io.IOException;

public class ClientPlaySreen implements Screen{
    private int roomAddr;
    private int playerIndex;
    private String playerName;
    private String musicName;
    private String mapName;
    private Client client;
    private PlayScreen playScreen;
    private MusicStuff musicStuff;
    private boolean recordFlag;
    private Recorder recorder;

    /**
     * @author: Xu YangXin
     * @param {String} roomAddr 房间地址(只含数字)
     * @param {String} playerName 玩家名字
     * @param {Stirng} musicName 音乐名称
     * @param {String} mapName 地图名称
     * @return {*}
     * @description: 客户游戏界面构造器
     */
    public ClientPlaySreen(String roomAddr, String playerName, String musicName, String mapName){
        this.roomAddr = Integer.parseInt(roomAddr);
        this.playerName = playerName;
        this.musicName = musicName;
        this.mapName = mapName;
        this.musicStuff = new MusicStuff();
        this.musicStuff.playMusic("./src/main/resources/"+ musicName + ".wav");

        PlayScreen newRoom = new PlayScreen(roomAddr, musicName, mapName);
        newRoom.musicStuff().stop();
        this.client = new Client(this.roomAddr,
                                 this.playerName,
                                 newRoom);

        this.playerIndex = this.client.playerIndex();
        this.roomAddr = this.client.roomAddr();
    }

    /**
     * @author: Xu YangXin
     * @param {PlayScreen} snapShotPlayScreen
     * @return {*}
     * @description: 用户加载游戏闪照构造器
     */    
    public ClientPlaySreen(PlayScreen snapShotPlayScreen){
        this.playScreen = snapShotPlayScreen;
        this.playScreen.musicStuff().stop();
        this.roomAddr = Integer.parseInt(snapShotPlayScreen.playScreenAddress());
        this.playerName = snapShotPlayScreen.player().getClass().getSimpleName();
        this.musicName = snapShotPlayScreen.musicName();
        this.mapName = null;
        this.musicStuff = new MusicStuff();
        this.musicStuff.playMusic("./src/main/resources/"+ this.musicName + ".wav");

        this.client = new Client(roomAddr,
                                playerIndex,
                                snapShotPlayScreen);

        this.playerIndex = this.client.playerIndex();
        this.roomAddr = this.client.roomAddr();
    }

    @Override
    /**
     * @author: Xu YangXin
     * @param {AsciiPanel} terminal 目标终端
     * @return {*}
     * @description: 用于展示当前游戏界面
     */
    public void displayOutput(AsciiPanel terminal){
        this.playScreen = this.client.getLatestRoom();
        if(playScreen == null) // 房间不存在
            return;

        Player player = this.playScreen.world().searchPlayer(this.playerIndex);
        if(player == null)
            return;
        this.playScreen.setPlayer(player);
        this.playScreen.setMessages(player.messages());
        this.playScreen.setOldMessages(player.oldMessages());
        this.playScreen.resetAllAI();
        this.playScreen.displayTiles(terminal, playScreen.getScrollX(), playScreen.getScrollY());
        this.playScreen.displayPlayerInfo(terminal);
        this.playScreen.displayCurWeaponInfo(terminal);
        this.playScreen.displayMessages(terminal, playScreen.messages());

        if(this.recordFlag)
            this.recorder.saveSnapShot(this.playScreen);
    }

    @Override
    /**
     * @author: Xu YangXin
     * @param {KeyEvent} key 玩家按下的按键
     * @return {Screen} 响应后的屏幕
     * @description: 响应玩家键盘操作
     */
    public Screen respondToUserInput(KeyEvent key){
        if(this.playScreen == null || this.playScreen.player().hp() < 1){
            this.musicStuff.stop();
            return new LoseScreen();
        }

        if(this.playScreen.world().worldIsPaused() && key.getKeyCode() != KeyEvent.VK_ESCAPE)
            return this;

        switch (key.getKeyCode()) {
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_UP:
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_SPACE:
            case KeyEvent.VK_X:
            case KeyEvent.VK_Z:
            case KeyEvent.VK_C:
            case KeyEvent.VK_W:
            case KeyEvent.VK_S:
            case KeyEvent.VK_A:
            case KeyEvent.VK_D:
                this.client.operation(key);
                break;
            case KeyEvent.VK_ESCAPE:
                this.client.operation(key);
                this.musicStuff.stop();
                return new ClientPauseScreen(this);
        }

        return this;
    }

    /**
     * @author: Xu YangXin
     * @return {*}
     * @description: 开始记录
     */    
    public void startRecord(){
        this.recorder = new Recorder();
        this.recordFlag = true;
    }

    /**
     * @author: Xu YangXin
     * @return {*}
     * @description: 停止记录
     */    
    public void endRecord(){
        this.recordFlag = false;
        try {
            Recorder.writeRecorder(recorder);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    /**
     * @author: Xu YangXin
     * @return {boolean}
     * @description: 读取游戏玩家游戏界面是否处于记录中的状态
     */    
    public boolean recordFlag(){ return this.recordFlag; }

    /**
     * @author: Xu YangXin
     * @return {Client}
     * @description: 读取玩家游戏界面的负责与服务器进行交互的客户端对象
     */
    public Client client(){ return this.client; }

    /**
     * @author: Xu YangXin
     * @return {MusicStuff}
     * @description: 读取玩家游戏界面中负责BGM播放的音乐播放器
     */    
    public MusicStuff musicStuff(){ return this.musicStuff; }

    /**
     * @author: Xu YangXin
     * @return {PlayScreen}
     * @description: 读取玩家游戏界面中的游戏界面
     */    
    public PlayScreen playScreen(){ return this.playScreen; }


}
