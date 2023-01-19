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
 * @Date: 2023-01-09 10:34:44
 * @LastEditTime: 2023-01-16 16:05:19
 * @LastEditors: Xu YangXin
 * @Description: 此类为进行游戏交互的游戏界面类
 * @FilePath: \jfp-poker-svg\src\main\java\cn\edu\nju\cs\game\screen\PlayScreen.java
 * 此项目为南京大学《Java高级程序设计》项目
 */

package cn.edu.nju.cs.game.screen;

import cn.edu.nju.cs.game.asciiPanel.AsciiPanel;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.edu.nju.cs.game.world.item.World;
import cn.edu.nju.cs.game.world.item.WorldBuilder;
import cn.edu.nju.cs.game.world.item.Tile;
import cn.edu.nju.cs.game.world.creature.AppleAI;
import cn.edu.nju.cs.game.world.creature.Creature;
import cn.edu.nju.cs.game.world.creature.bros.Player;
import cn.edu.nju.cs.game.world.creature.goblins.Goblin;
import cn.edu.nju.cs.game.world.creature.CreatureFactory;
import cn.edu.nju.cs.game.world.creature.Direction;
import cn.edu.nju.cs.game.world.creature.PlayerAI;
import cn.edu.nju.cs.game.world.weapon.Weapon;
import cn.edu.nju.cs.game.world.weapon.gun.*;
import cn.edu.nju.cs.game.world.weapon.melee.AttackDot;
import cn.edu.nju.cs.game.world.weapon.melee.Torch;
import cn.edu.nju.cs.game.world.weapon.throwable.ThrowableItem;
import cn.edu.nju.cs.game.world.weapon.throwable.ThrowableWeapon;
import cn.edu.nju.cs.logger.Recorder;
import cn.edu.nju.cs.music.MusicStuff;
import cn.edu.nju.cs.game.world.weapon.bomb.Bomb;
import cn.edu.nju.cs.game.world.weapon.bomb.BombGlove;

public class PlayScreen implements Screen, Serializable {
    private World world;
    private WorldBuilder worldBuilder;
    private Player player;
    private int screenWidth;
    private int screenHeight;
    private List<String> messages;
    private List<String> oldMessages;
    private String playScreenAddress;
    private String musicName;
    private boolean recordFlag;
    transient private MusicStuff musicStuff;
    transient private CreatureFactory creatureFactory;
    transient private Recorder recorder;
    public static final int GOBLIN_SIZE = 5;

    /**
     * @author: Xu YangXin
     * @param {String} roomAddr 房间地址
     * @param {String} musicName 音乐名称
     * @param {String} mapName 地图名称
     * @return {*}
     * @description: 构造器
     */
    public PlayScreen(String roomAddr, String musicName, String mapName) {
        // this.screenWidth = 80;
        // this.screenHeight = 24;
        // this.worldBuilder = new WorldBuilder(90, 31);
        this.screenWidth = 60;
        this.screenHeight = 24;
        this.worldBuilder = new WorldBuilder(60, 31);
        loadWorld(mapName);
        this.messages = new ArrayList<String>();
        this.oldMessages = new ArrayList<String>();
        this.playScreenAddress = roomAddr;
        this.musicName = musicName;
        this.creatureFactory = new CreatureFactory(this.world, PlayScreen.GOBLIN_SIZE);
        createCreatures(creatureFactory);
        this.recorder = new Recorder();
        this.recordFlag = false; // 一开始没有录制
        // 放音乐
        musicStuff = new MusicStuff();
        musicStuff.playMusic("./src/main/resources/"+ musicName + ".wav");
    }

    /**
     * @author: Xu YangXin
     * @param {PlayScreen} otherPlayScreen 另外一个游戏界面
     * @return {*}
     * @description: 复制构造器，用于重启本地保存或者网络传输的游戏界面(房间)
     */
    public PlayScreen(PlayScreen otherPlayScreen){
        this.world = otherPlayScreen.world;
        this.world.resetWorld();
        this.worldBuilder = otherPlayScreen.worldBuilder;
        this.player = otherPlayScreen.player;
        this.screenWidth = otherPlayScreen.screenWidth;
        this.screenHeight = otherPlayScreen.screenHeight;
        this.messages = otherPlayScreen.messages;
        this.oldMessages = otherPlayScreen.oldMessages;
        this.playScreenAddress = otherPlayScreen.playScreenAddress;
        this.musicName = otherPlayScreen.musicName;
        this.musicStuff = new MusicStuff();
        musicStuff.playMusic("./src/main/resources/"+ musicName + ".wav");

        this.resetAllAI();

        this.creatureFactory = new CreatureFactory(this.world, PlayScreen.GOBLIN_SIZE);
        creatureFactory.enableGoblinsStartMove();
        this.recorder = new Recorder();
        this.recordFlag = false;
    }

    /**
     * @author: Xu YangXin
     * @return {*}
     * @description: 重新设置所有生物的AI
     */    
    public void resetAllAI(){
        // 设置AI
        // 妖精
        for(Goblin goblin : this.world.getGoblins())
            new PlayerAI(goblin, new ArrayList<String>());
        for(Player player : this.world.getPlayers())
            new PlayerAI(player, player.messages());
        for(Creature apple : this.world.getCreatures()){
            if(! ((apple instanceof Goblin)||(apple instanceof Player)) ){
                new AppleAI(apple, this.creatureFactory);
            }
        }
        new PlayerAI(this.player, this.messages);
    }

    @Override
    /**
     * @author: Xu YangXin
     * @param {AsciiPanel} terminal 目标终端
     * @return {*}
     * @description: 展示游戏界面
     */    
    public void displayOutput(AsciiPanel terminal) {
        // Terrain , creatures , bombs , bullet
        displayTiles(terminal, getScrollX(), getScrollY());

        // Player
        //terminal.write(player.glyph(), player.x() - getScrollX(), player.y() - getScrollY(), player.color());

        // PlayerInfo
        displayPlayerInfo(terminal);

        // Current Weapon
        displayCurWeaponInfo(terminal);

        // Messages
        displayMessages(terminal, this.messages);

        for(Player player : this.world.getPlayers())
            this.world.firePiles().warmCreature(player);

        if(this.recordFlag)
            this.recorder.saveSnapShot(this);
    }

    @Override
    /**
     * @author: Xu YangXin
     * @param {KeyEvent} key 玩家按下的键
     * @return {*}
     * @description: 响应玩家的按键
     */    
    public Screen respondToUserInput(KeyEvent key) {
        if(world.worldIsPaused() && key.getKeyCode() != KeyEvent.VK_ESCAPE)
            return this;
        switch (key.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                player.setDirection(Direction.Left);
                try {
                    Weapon weapon = player.weaponFactory().curWeapon();
                    if(weapon instanceof BombGlove && weapon.attack())
                        weapon.setActive(false);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                player.moveBy(-1, 0);
                break;
            case KeyEvent.VK_RIGHT:
                player.setDirection(Direction.Right);
                try {
                    Weapon weapon = player.weaponFactory().curWeapon();
                    if(weapon instanceof BombGlove && weapon.attack())
                        weapon.setActive(false);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                player.moveBy(1, 0);
                break;
            case KeyEvent.VK_UP:
                player.setDirection(Direction.Up);
                try {
                    Weapon weapon = player.weaponFactory().curWeapon();
                    if(weapon instanceof BombGlove && weapon.attack())
                        weapon.setActive(false);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                player.moveBy(0, -1);
                break;
            case KeyEvent.VK_DOWN:
                player.setDirection(Direction.Down);
                try {
                    Weapon weapon = player.weaponFactory().curWeapon();
                    if(weapon instanceof BombGlove && weapon.attack())
                        weapon.setActive(false);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                player.moveBy(0, 1);
                break;
            case KeyEvent.VK_SPACE:
                player.setBomb();
                break;
            case KeyEvent.VK_X:
                try {
                    Weapon weapon = player.weaponFactory().curWeapon();
                    if(weapon.attack())
                        weapon.setActive(false);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                break;
            case KeyEvent.VK_Z:
                try {
                    Weapon weapon = player.weaponFactory().curWeapon();
                    if(weapon.bigAttack()){
                        weapon.clearEnergy();
                        weapon.setActive(false);
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                break;
            case KeyEvent.VK_C:
                try {
                    player.weaponFactory().switchWeapon();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                break;
            case KeyEvent.VK_W:
                try {
                    ((ThrowableWeapon)player.weaponFactory().curWeapon()).dyMoveUp();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                } catch (ClassCastException e){
                    e.printStackTrace();
                }
                break;
            case KeyEvent.VK_S:
                try {
                    ((ThrowableWeapon)player.weaponFactory().curWeapon()).dyMoveDown();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                } catch (ClassCastException e){
                    e.printStackTrace();
                }
                break;
            case KeyEvent.VK_A:
                try {
                    ((ThrowableWeapon)player.weaponFactory().curWeapon()).dxMoveLeft();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                } catch (ClassCastException e){
                    e.printStackTrace();
                }
                break;
            case KeyEvent.VK_D:
                try {
                    ((ThrowableWeapon)player.weaponFactory().curWeapon()).dxMoveRight();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                } catch (ClassCastException e){
                    e.printStackTrace();
                }
                break;
            case KeyEvent.VK_ESCAPE:
                world.setWorldPauseFlag(true);
                musicStuff.stop();
                return new PauseScreen(this);
        }

        return this.refreshWorld();
    }

    /**
     * @author: Xu YangXin
     * @return {int} 游戏界面的ScrollX值
     * @description: 读取游戏界面的ScrollX值
     */    
    public int getScrollX() {
        return Math.max(0, Math.min(player.x() - screenWidth / 2, world.width() - screenWidth));
    }

    /**
     * @author: Xu YangXin
     * @return {int} 游戏界面的ScrollY值
     * @description: 读取游戏界面的ScrollY值
     */    
    public int getScrollY() {
        return Math.max(0, Math.min(player.y() - screenHeight / 2, world.height() - screenHeight));
    }

    /**
     * @author: Xu YangXin
     * @return {World} 游戏界面的世界
     * @description: 读取游戏界面的world字段
     */    
    public World world(){
        return this.world;
    }

    /**
     * @author: Xu YangXin
     * @return {WorldBuilder} 游戏界面的世界构建器
     * @description: 读取游戏界面的worldBuilder字段
     */    
    public WorldBuilder worldBuilder(){
        return this.worldBuilder;
    }

    /**
     * @author: Xu YangXin
     * @return {MusicStuff} 游戏界面的音乐播放器
     * @description: 读取游戏界面的musicStuff字段
     */    
    public MusicStuff musicStuff(){
        return this.musicStuff;
    }

    /**
     * @author: Xu YangXin
     * @param {Player} player 玩家人物
     * @return {*}
     * @description: 设置游戏界面的玩家人物
     */    
    public void setPlayer(Player player){ this.player = player; }

    /**
     * @author: Xu YangXin
     * @return {Player} 游戏界面的玩家人物
     * @description: 读取游戏界面的player字段
     */    
    public Player player(){ return this.player; }

    /**
     * @author: Xu YangXin
     * @return {List<String>} 游戏界面的消息序列
     * @description: 读取游戏界面的messages字段
     */    
    public List<String> messages(){ return this.messages; }

    /**
     * @author: Xu YangXin
     * @param {List<String>} messages 消息序列
     * @return {*}
     * @description: 设置游戏界面的消息序列
     */    
    public void setMessages(List<String> messages){ this.messages = messages; }

    /**
     * @author: Xu YangXin
     * @param {List<String>} oldMessages 旧消息序列
     * @return {*}
     * @description: 设置游戏界面的就消息序列
     */    
    public void setOldMessages(List<String> oldMessages){ this.oldMessages = oldMessages; }

    /**
     * @author: Xu YangXin
     * @return {CreatureFactory} 游戏界面的生物工厂
     * @description: 读取游戏界面的creatureFactory字段
     */    
    public CreatureFactory creatureFactory(){ return this.creatureFactory; }

    /**
     * @author: Xu YangXin
     * @return {Recorder} 游戏界面的记录器
     * @description: 读取游戏界面的recorder字段
     */    
    public Recorder recorder(){ return this.recorder; }

    /**
     * @author: Xu YangXin
     * @return {boolean} 游戏界面的记录标志
     * @description: 读取游戏界面的recordFlag字段
     */    
    public boolean recordFlag(){ return this.recordFlag; }

    /**
     * @author: Xu YangXin
     * @return {int} 游戏界面的屏幕宽度
     * @description: 读取游戏界面的screenWidth字段
     */    
    public int screenWidth(){ return this.screenWidth; }

    /**
     * @author: Xu YangXin
     * @return {int} 游戏界面的屏幕高度
     * @description: 读取游戏界面的screenHeight字段
     */    
    public int screenHeight(){ return this.screenHeight; }

    /**
     * @author: Xu YangXin
     * @return {String} 游戏界面的音乐名称
     * @description: 读取游戏界面的musicName字段
     */    
    public String musicName(){ return this.musicName; }

    /**
     * @author: Xu YangXin
     * @return {String} 游戏界面的房间地址
     * @description: 读取游戏界面的playScreenAddress字段
     */    
    public String playScreenAddress(){ return this.playScreenAddress; }

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
     * @param {CreatureFactory} creatureFactory 用于生成生物的工厂
     * @return {*}
     * @description: 在此游戏界面的世界中生成生物
     */    
    protected void createCreatures(CreatureFactory creatureFactory) {
        // 创造苹果
        for (int i = 0; i < 10; i++) {
            Creature apple = creatureFactory.newApples();
            new AppleAI(apple, this.creatureFactory);
            //new AppleAI(apple, new CreatureFactory(world, 0));
        }

        // 创造妖精
        for(int i = 0; i < PlayScreen.GOBLIN_SIZE; i++) {
            creatureFactory.newRandomGoblin();
        }
        // 妖精们开始运动
        creatureFactory.enableGoblinsStartMove();
    }

    /**
     * @author: Xu YangXin
     * @return {*}
     * @description: 创建世界
     */    
    protected void createWorld() {
        world = this.worldBuilder.makeCaves().build();
    }

    /**
     * @author: Xu YangXin
     * @param {String} worldName 世界名称
     * @return {*}
     * @description: 加载世界
     */    
    protected boolean loadWorld(String worldName) {
        if(worldName == "New World"){
            createWorld();
            return true;
        }
        world = this.worldBuilder.loadWorld(worldName);
        return (world != null);
    }

    /**
     * @author: Xu YangXin
     * @param {AsciiPanel} terminal 目标终端
     * @param {List<String>} messages 最新消息序列
     * @return {*}
     * @description: 展示消息序列的内容
     */    
    public void displayMessages(AsciiPanel terminal, List<String> messages) {
        if(!messages.isEmpty()){
            this.oldMessages.clear();
            this.oldMessages.addAll(messages);
            messages.clear();
        }

        int top = this.screenHeight - messages.size();
        for (int i = 0; i < oldMessages.size(); i++) {
            terminal.write(oldMessages.get(i), 1, top + i + 1);
        }

    }

    /**
     * @author: Xu YangXin
     * @param {AsciiPanel} terminal 目标终端
     * @param {int} left 左间距
     * @param {int} top 上间距
     * @return {*}
     * @description: 展示世界的地形、生物、玩家状态等等内容
     */    
    public void displayTiles(AsciiPanel terminal, int left, int top) {
        showTerrain(terminal, left, top);
        showBombs(terminal,left,top);
        showBullets(terminal, left, top);
        showAttackDots(terminal, left, top);
        showThrowableItems(terminal, left, top);
        showCreatures(terminal, left, top);
        showThrowableWeapon(terminal, left, top);
        // Creatures can choose their next action now
        world.update();
    }

    /**
     * @author: Xu YangXin
     * @param {AsciiPanel} terminal 目标终端
     * @return {*}
     * @description: 展示玩家当前的状态
     */    
    public void displayPlayerInfo(AsciiPanel terminal) {
        // Stats
        // Direction
        Tile direction = this.player.direction().image();
        terminal.write(direction.glyph(), 1, 24, direction.color());

        terminal.write("   ", terminal.getCursorX(), 24);
        // HP
        terminal.write(Tile.HP.glyph(), terminal.getCursorX(), 24, Tile.HP.color());
        String stats = String.format(":%3d/%3d   ", player.hp(), player.maxHP());
        terminal.write(stats, terminal.getCursorX(), 24, Tile.HP.color());
        // Speed
        terminal.write(Tile.SPEED.glyph(), terminal.getCursorX(), 24, Tile.SPEED.color());
        stats = String.format(":%d   ", player.moveSpeed());
        terminal.write(stats, terminal.getCursorX(), 24, Tile.SPEED.color());
    }

    /**
     * @author: Xu YangXin
     * @param {AsciiPanel} terminal 目标终端
     * @return {*}
     * @description: 展示当前武器的信息
     */    
    public void displayCurWeaponInfo(AsciiPanel terminal) {
        Weapon curWeapon = this.player.weaponFactory().curWeapon();
        try {
            String weaponStats = null;
            terminal.write(curWeapon.glyph(), terminal.getCursorX(), 24, curWeapon.color());

            weaponStats = (" " + (curWeapon.attackValue()));
            terminal.write(weaponStats, terminal.getCursorX(), 24, curWeapon.color());

            if(curWeapon instanceof Gun){ // 当前武器是枪，显示剩余子弹数量
                Gun curGun = (Gun)curWeapon;
                weaponStats = " %d/%d";
                weaponStats = String.format(weaponStats, curGun.remainingBullet(), curGun.maxBulletAmount());
                terminal.write(weaponStats, terminal.getCursorX(), 24, AsciiPanel.brightYellow);
                terminal.write(Tile.BULLET.glyph(), terminal.getCursorX(), 24, Tile.BULLET.color());
            }
            else if(curWeapon instanceof ThrowableWeapon){ // 当前武器是抛掷类武器
                ThrowableWeapon curThrowableWeapon = (ThrowableWeapon)curWeapon;
                weaponStats = " %d/%d";
                weaponStats = String.format(weaponStats, curThrowableWeapon.remainingItems(), curThrowableWeapon.maxItemsAmount());
                terminal.write(weaponStats, terminal.getCursorX(), 24, AsciiPanel.brightYellow);
                terminal.write(Tile.THROW_ITEM.glyph(), terminal.getCursorX(), 24, Tile.THROW_ITEM.color());
            }

            weaponStats = (" " + (curWeapon.curEnergy()*100/curWeapon.maxEnergy()) + "%");
            terminal.write(weaponStats, terminal.getCursorX(), 24, AsciiPanel.brightYellow);
            terminal.write(Tile.ENERGY.glyph(), terminal.getCursorX(), 24, Tile.ENERGY.color());
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     * @author: Xu YangXin
     * @param {AsciiPanel} terminal 目标终端
     * @param {int} left 左间距
     * @param {int} top 上间距
     * @return {*}
     * @description: 展示地形信息
     */    
    protected void showTerrain(AsciiPanel terminal, int left, int top){
        // 刷新传送门位置
        this.world.doorPairs().refreshDoorPairs(this.world);

        // 刷新草丛位置
        this.world.grasses().refreshGrasses(this.world);

        // 刷新宝箱位置
        this.world.boxes().refreshBox(this.world);

        // 刷新火堆位置
        this.world.firePiles().refreshFirePiles(this.world);

        Tile tile = null;
        for (int x = 0; x < screenWidth; x++) {
            for (int y = 0; y < screenHeight; y++) {
                int wx = x + left;
                int wy = y + top;

                if (player.canSee(wx, wy)) {
                    tile = world.tile(wx, wy);

                    if (this.player.weaponFactory().curWeapon() instanceof Torch &&
                        tile == Tile.FLOOR && this.player.distance(wx, wy) <= Torch.RANGE)
                        terminal.write(Tile.FLOOR_LIGHT.glyph(), x, y, Tile.FLOOR_LIGHT.color());
                    else
                        terminal.write(world.glyph(wx, wy), x, y, world.color(wx, wy));
                }
                // else {
                //     terminal.write(world.glyph(wx, wy), x, y, Color.DARK_GRAY);
                // }
            }
        }
    }

    /**
     * @author: Xu YangXin
     * @param {AsciiPanel} terminal 目标终端
     * @param {int} left 左间距
     * @param {int} top 上间距
     * @return {*}
     * @description: 绘制炸弹并进行攻击交互
     */    
    protected void showBombs(AsciiPanel terminal, int left, int top){
        int old_size = this.world.getBombsSize();
        int new_size = this.world.getBombsSize();
        for(int index = 0; index < new_size;){
            Bomb bomb = this.world.getBombAt(index);
            int x = bomb.x();
            int y = bomb.y();
            int range = bomb.range();

            if(!bomb.isAlive()){ // 炸弹死了
                for(int i = x-range; i <= x+range; i++)
                    this.world.setThing(Tile.FLOOR, i, y);
                for(int j = y-range; j <= y+range; j++)
                    if(j != y)this.world.setThing(Tile.FLOOR, x, j);
                this.world.removeBomb(bomb);
            }
            else if(bombAttackCreatures(bomb)){
                bomb.stopMove();
                for(int i = x-range; i <= x+range; i++)
                    if(i != x) this.world.setThing(Tile.BOMB_X, i, y);
                for(int j = y-range; j <= y+range; j++)
                    if(j != y) this.world.setThing(Tile.BOMB_Y, x, j);
            }

            new_size = this.world.getBombsSize();
            if(new_size < old_size)
                old_size = new_size;
            else
                index++;
        }
    }

    /**
     * @author: Xu YangXin
     * @param {AsciiPanel} terminal 目标终端
     * @param {int} left 左间距
     * @param {int} top 上间距
     * @return {*}
     * @description: 绘制子弹并进行攻击交互
     */    
    protected void showBullets(AsciiPanel terminal, int left, int top) {
        int old_size = this.world.getBullets().size();
        int new_size = this.world.getBullets().size();
        for(int index = 0; index < new_size;){
            Bullet bullet = this.world.getBullets().get(index);
            int x = bullet.x();
            int y = bullet.y();

            if(!this.world.tile(x, y).isGround() || // 子弹触碰到障碍物
                bullet.range() == 0 ||              // 子弹超出射程
                bulletAttackCreatures(bullet)       // 子弹击中生物
                ){
                this.world.removeBullet(bullet);;   // 移除该子弹
            }
            else{ // 没碰到障碍物，没超出射程，也没几种生物，绘制子弹
                if (x >= left && x < left + screenWidth &&
                    y >= top  && y < top + screenHeight &&
                    player.canSee(x, y))
                    terminal.write(bullet.image().glyph(), x - left, y - top, bullet.image().color());
            }
            new_size = this.world.getBullets().size();
            if(new_size < old_size)
                old_size = new_size;
            else
                index++;
        }
    }

    /**
     * @author: Xu YangXin
     * @param {AsciiPanel} terminal 目标终端
     * @param {int} left 左间距
     * @param {int} top 上间距
     * @return {*}
     * @description: 绘制世界中的所有生物
     */    
    protected void showCreatures(AsciiPanel terminal, int left, int top) {
        synchronized(world.getCreatures()){
            for (Creature creature : world.getCreatures()) {
                if (creature.x() >= left && creature.x() < left + screenWidth &&
                    creature.y() >= top  && creature.y() < top + screenHeight) {
                    if (player.canSee(creature.x(), creature.y()) && creature.isVisible()) {
                        terminal.write(creature.glyph(), creature.x() - left, creature.y() - top, creature.color());
                    }
                }
            }
        }
    }

    /**
     * @author: Xu YangXin
     * @param {AsciiPanel} terminal 目标终端
     * @param {int} left 左间距
     * @param {int} top 上间距
     * @return {*}
     * @description: 绘制攻击点并进行攻击交互
     */    
    protected void showAttackDots(AsciiPanel terminal, int left, int top) {
        int old_size = this.world.getAttackDots().size();
        int new_size = this.world.getAttackDots().size();
        for(int index = 0; index < new_size;){
            AttackDot attackDot = this.world.getAttackDots().get(index);
            int x = attackDot.x();
            int y = attackDot.y();

            // 攻击
            dotAttackCreatures(attackDot);

            // 绘图
            if (x >= left && x < left + screenWidth &&
                y >= top  && y < top + screenHeight &&
                player.canSee(x, y))
                terminal.write(attackDot.image().glyph(), x - left, y - top, attackDot.image().color());

            new_size = this.world.getAttackDots().size();
            if(new_size < old_size)
                old_size = new_size;
            else
                index++;
        }
    }

    /**
     * @author: Xu YangXin
     * @param {AsciiPanel} terminal 目标终端
     * @param {int} left 左间距
     * @param {int} top 上间距
     * @return {*}
     * @description: 绘制抛掷物并进行攻击交互
     */    
    protected void showThrowableItems(AsciiPanel terminal, int left, int top){
        int old_size = this.world.getThrowableItems().size();
        int new_size = this.world.getThrowableItems().size();
        for(int index = 0; index < new_size;){
            ThrowableItem throwableItem = this.world.getThrowableItems().get(index);
            int x = throwableItem.x();
            int y = throwableItem.y();
            int range = throwableItem.range();

            if(!throwableItem.isAlive()){ // 扔物爆炸完了，要清除
                for(int i = x-range; i <= x+range; i++)
                    for(int j = y-range; j <= y+range; j++)
                        this.world.setThing(Tile.FLOOR, i, j);
                this.world.removeThrowableItem(throwableItem);
            }
            else if(throwableItemAttackCreatures(throwableItem)){
                for(int i = x-range; i <= x+range; i++)
                    for(int j = y-range; j <= y+range; j++)
                        this.world.setThing(throwableItem.image(), i, j);
            }

            new_size = this.world.getThrowableItems().size();
            if(new_size < old_size)
                old_size = new_size;
            else
                index++;
        }
    }

    /**
     * @author: Xu YangXin
     * @param {AsciiPanel} terminal 目标终端
     * @param {int} left 左间距
     * @param {int} top 上间距
     * @return {*}
     * @description: 绘制抛掷物的准星
     */
    protected void showThrowableWeapon(AsciiPanel terminal, int left, int top){
        Weapon currentWeapon = player.weaponFactory().curWeapon();
        if(!(currentWeapon instanceof ThrowableWeapon))
            return;

            ThrowableWeapon throwableWeapon = (ThrowableWeapon)currentWeapon;
        int x = this.player.x() + throwableWeapon.dx();
        int y = this.player.y() + throwableWeapon.dy();

        if(this.world.inBound(x, y))
            terminal.write(Tile.AIM.glyph(), x - left, y - top, Tile.AIM.color());
    }

    /**
     * @author: Xu YangXin
     * @param {AsciiPanel} terminal 目标终端
     * @param {int} left 左间距
     * @param {int} top 上间距
     * @return {*}
     * @description: 让炸弹去攻击生物以及可摧毁地形
     */
    protected boolean bombAttackCreatures(Bomb bomb){
        if(!bomb.isActive())
            return false;

        // 攻击生物(AI和玩家)
        int old_size = this.world.getCreaturesSize();
        int new_size = this.world.getCreaturesSize();
        for(int i = 0; i < new_size;){
            bomb.attack(this.world.getCreatureAt(i));

            new_size = this.world.getCreaturesSize();
            if(new_size < old_size)
                old_size = new_size;
            else
                i++;
        }

        // 1秒后死亡
        Runnable runnable = new Runnable() {
        	public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        		bomb.dead();
        	}
        };
        Thread thread = new Thread(runnable);
        thread.start();

        return true;
    }

    /**
     * @author: Xu YangXin
     * @param {AsciiPanel} terminal 目标终端
     * @param {int} left 左间距
     * @param {int} top 上间距
     * @return {*}
     * @description: 让子弹去攻击生物
     */
    protected boolean bulletAttackCreatures(Bullet bullet){
        boolean hit = false;
        int old_size = this.world.getCreaturesSize();
        int new_size = this.world.getCreaturesSize();
        for(int i = 0; i < new_size;){
            if(bullet.attack(this.world.getCreatureAt(i)))
                hit = true;

            new_size = this.world.getCreaturesSize();
            if(new_size < old_size)
                old_size = new_size;
            else
                i++;
        }
        return hit;
    }

    /**
     * @author: Xu YangXin
     * @param {AsciiPanel} terminal 目标终端
     * @param {int} left 左间距
     * @param {int} top 上间距
     * @return {*}
     * @description: 让攻击点去攻击生物
     */
    protected boolean dotAttackCreatures(AttackDot attackDot){
        boolean hit = false;
        int old_size = this.world.getCreaturesSize();
        int new_size = this.world.getCreaturesSize();
        for(int i = 0; i < new_size;){
            if(attackDot.attack(this.world.getCreatureAt(i)))
                hit = true;

            new_size = this.world.getCreaturesSize();
            if(new_size < old_size)
                old_size = new_size;
            else
                i++;
        }
        return hit;
    }

    /**
     * @author: Xu YangXin
     * @param {AsciiPanel} terminal 目标终端
     * @param {int} left 左间距
     * @param {int} top 上间距
     * @return {*}
     * @description: 让抛掷物去攻击生物
     */
    protected boolean throwableItemAttackCreatures(ThrowableItem throwableItem){
        if(!throwableItem.isActive())
            return false;

        // 攻击生物(AI和玩家)
        int old_size = this.world.getCreaturesSize();
        int new_size = this.world.getCreaturesSize();
        for(int i = 0; i < new_size;){
            throwableItem.attack(this.world.getCreatureAt(i));

            new_size = this.world.getCreaturesSize();
            if(new_size < old_size)
                old_size = new_size;
            else
                i++;
        }

        // 一段时间(remainTime)后死亡
        Runnable runnable = new Runnable() {
        	public void run() {
                try {
                    Thread.sleep(throwableItem.remainTime());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        		throwableItem.dead();
        	}
        };
        Thread thread = new Thread(runnable);
        thread.start();

        return true;
    }

    /**
     * @author: Xu YangXin
     * @return {*}
     * @description: 玩家每次按下按键会刷新一次世界，来判断玩家是否死亡、是否进入特殊地形等
     */    
    public Screen refreshWorld(){
        for(Player player : this.world.getPlayers()){
            if(player.hp() < 1){
                this.musicStuff.stop();
                this.world.remove(player);
                return new LoseScreen();
            }

            if(player.world().grasses().hideCreature(player))
                return this;

            if(player.world().boxes().enterBox(player))
                return this;

            player.expose();
            int x = player.x(), y = player.y();
            Tile stepOnThing = this.world.tile(x, y);

            switch (stepOnThing) {
                // 踩到道具
                case BOMB_UPGRADE_RANGE:
                    player.bombExpandRange();
                    player.notify("Bomb's range ++ !");
                    break;
                case SPEED_UPGRADE:
                    player.speedUpgrade();
                    player.notify("Speed ++ !");
                    break;
                case HP_UPGRADE:
                    player.addHP();;
                    player.notify("Hp ++ !");
                    break;
                case ENERGY_UPGRADE:
                    player.weaponFactory().curWeapon().chargeEnergy();
                    player.notify("Energy ++ !");
                    break;
                case VISUAL_RANGE_UPGRADE:
                    player.visionRadiusUpgrade();
                    player.notify("Vision Radius ++ !");
                    break;
                case ATTACK_UPGRADE:
                    player.attackUpgrade();
                    player.notify("Hand Attack ++ !");
                    break;
                case WEAPON_ATTACK_UPGRADE:
                    player.weaponFactory().curWeapon().attackUpgrade();;
                    player.notify("Weapon Attack ++ !");
                    break;

                // 踩到武器
                case NORMAL_GUN:
                    player.weaponFactory().newNormalGun();
                    player.notify("Get Normal Gun !");
                    break;
                case LASER_GUN:
                    player.weaponFactory().newLaserGun();
                    player.notify("Get Laser Gun !");
                    break;
                case SHOT_GUN:
                    player.weaponFactory().newShotGun();
                    player.notify("Get Shot Gun !");
                    break;
                case SNIPER_GUN:
                    player.weaponFactory().newSniperGun();
                    player.notify("Get Sniper Gun !");
                    break;
                case BASEBALL_BAT:
                    player.weaponFactory().newBaseballBat();
                    player.notify("Get Baseball Bat !");
                    break;
                case DETONATOR_THROWER:
                    player.weaponFactory().newDetonatorThrower();
                    player.notify("Get Detonator Thrower !");
                    break;
                case BOMB_GLOVE:
                    player.weaponFactory().newBombGlove();
                    player.notify("Get Bomb Glove !");
                    break;
                case TORCH:
                    player.weaponFactory().newTorch();;
                    player.notify("Get Torch !");
                    break;
                default:    // 没有踩到道具
                    continue;
            }
            this.world.setThing(Tile.FLOOR, x, y);
        }
        return this;
    }
}
