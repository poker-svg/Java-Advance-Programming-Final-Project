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
 * @Date: 2022-12-25 09:39:17
 * @LastEditTime: 2023-01-16 10:36:54
 * @LastEditors: Xu YangXin
 * @Description: 此类为客户机和服务器之间交互的消息类
 * @FilePath: \jfp-poker-svg\src\main\java\cn\edu\nju\cs\connection\Message.java
 * 此项目为南京大学《Java高级程序设计》项目
 */

package cn.edu.nju.cs.connection;

import java.io.Serializable;

import cn.edu.nju.cs.game.screen.*;
import cn.edu.nju.cs.game.world.creature.bros.Player;

public class Message implements Serializable{
    private PlayScreen playScreen;
    private String playerName;
    private int playerIndex;
    private int roomAddr;
    private String info;

    /**
     * @author: Xu YangXin
     * @param {PlayScreen} playScreen 房间
     * @param {String} playerName 玩家名称
     * @param {int} playerIndex 玩家下标
     * @param {int} roomAddr 房间地址
     * @param {String} info 消息信息
     * @return {*}
     * @description: 构造器
     */    
    public Message(PlayScreen playScreen, String playerName, int playerIndex, int roomAddr, String info){
        this.playScreen = playScreen;
        this.playerName = playerName;
        this.playerIndex = playerIndex;
        this.roomAddr = roomAddr;
        this.info = info;
    }

    @Override
    /**
     * @author: Xu YangXin
     * @return {String}
     * @description: 覆写Object类的toString()方法
     */    
    public String toString(){
        return this.info;
    }

    /**
     * @author: Xu YangXin
     * @param {byte[]} data
     * @return {Message}
     * @description: 将代表数据的字节数组转换成目标的Message对象
     */
    public static Message dataToObj(byte[] data){
        Message message = null;
        Object obj = ByteArrayUtils.bytesToObject(data).get();
        if(obj instanceof Message)
            message = (Message)obj;
        return message;
    }

    /**
     * @author: Xu YangXin
     * @return {PlayScreen}
     * @description: 读取消息中的房间字段
     */
    public PlayScreen playScreen(){
        return this.playScreen;
    }

    /**
     * @author: Xu YangXin
     * @param {PlayScreen} playScreen 房间
     * @return {*}
     * @description: 设置消息中的房间字段
     */
    public void setPlayScreen(PlayScreen playScreen){
        this.playScreen = playScreen;
    }

    /**
     * @author: Xu YangXin
     * @return {String}
     * @description: 读取消息中的玩家姓名字段
     */    
    public String playerName(){
        return this.playerName;
    }

    /**
     * @author: Xu YangXin
     * @param {String} playerName 玩家名称
     * @return {*}
     * @description: 设置消息中的玩家名称字段
     */    
    public void setPlayName(String playerName){
        this.playerName = playerName;
    }

    /**
     * @author: Xu YangXin
     * @return {int}
     * @description: 读取消息中的玩家下标字段
     */    
    public int playerIndex(){
        return this.playerIndex;
    }

    /**
     * @author: Xu YangXin
     * @param {int} playerIndex 玩家下标
     * @return {*}
     * @description: 设置玩家下标字段
     */    
    public void setPlayerIndex(int playerIndex){
        this.playerIndex = playerIndex;
    }

    /**
     * @author: Xu YangXin
     * @return {int}
     * @description: 读取消息中的房间地址字段
     */    
    public int roomAddr(){
        return this.roomAddr;
    }

    /**
     * @author: Xu YangXin
     * @param {int} roomAddr 房间地址
     * @return {*}
     * @description: 设置消息中的房间地址字段
     */    
    public void setRoomAddr(int roomAddr){
        this.roomAddr = roomAddr;
    }

    /**
     * @author: Xu YangXin
     * @return {Player}
     * @description: 根据消息提取出玩家信息
     */    
    public Player player(){
        if(this.playScreen == null)
            return null;
        return this.playScreen.world().searchPlayer(playerIndex);
    }

    /**
     * @author: Xu YangXin
     * @return {String}
     * @description: 读取消息中的信息字段
     */    
    public String info(){
        return this.info;
    }

    public static final String CONNECT_MESSAGE = "Client connect room";
    public static final String BACK_CONNECT_ROOM_MESSAGE = "Server response connect room";
    public static final String ADD_ROOM_MESSAGE = "Client add new room";
    public static final String BACK_ADD_ROOM_MESSAGE = "Server response add new room";
    public static final String ENTER_ROOM_MESSAGE = "Client enter room";
    public static final String BACK_ENTER_ROOM_MESSAGE = "Server response enter room";
    public static final String GET_LATEST_ROOM_MESSAGE = "Client get latest room";
    public static final String BACK_GET_LATEST_ROOM_MESSAGE = "Server response get latest room";
    public static final String MOVE_LEFT_MESSAGE = "Client move left";
    public static final String MOVE_RIGHT_MESSAGE = "Client move right";
    public static final String MOVE_UP_MESSAGE = "Client move up";
    public static final String MOVE_DOWN_MESSAGE = "Client move down";
    public static final String BACK_MOVE_MESSAGE = "Server response move";
    public static final String ATTACK_MESSAGE = "Client attack";
    public static final String BACK_ATTACK_MESSAGE = "Server response attack";
    public static final String BIG_ATTACK_MESSAGE = "Client big attack";
    public static final String BACK_BIG_ATTACK_MESSAGE = "Server response big attack";
    public static final String SWITCH_WEAPON_MESSAGE = "Client switch weapon";
    public static final String BACK_SWITCH_WEAPON_MESSAGE = "Server response switch weapon";
    public static final String SET_BOMB_MESSAGE = "Client set bomb";
    public static final String BACK_SET_BOMB_MESSAGE = "Server response set bomb";
    public static final String THROWABLE_WEAPON_MOVE_LEFT_MESSAGE = "Client's throwable weapon move left";
    public static final String THROWABLE_WEAPON_MOVE_RIGHT_MESSAGE = "Client's throwable weapon move right";
    public static final String THROWABLE_WEAPON_MOVE_UP_MESSAGE = "Client's throwable weapon move up";
    public static final String THROWABLE_WEAPON_MOVE_DOWN_MESSAGE = "Client's throwable weapon move down";
    public static final String BACK_THROWABLE_WEAPON_MOVE_MESSAGE = "Server response throwable weapon move";
    public static final String WORLD_PAUSE_MESSAGE = "Client pause world";
    public static final String BACK_WORLD_PAUSE_MESSAGE = "Server response pause world";
    public static final String WORLD_RECOVER_MESSAGE = "Client recover world";
    public static final String BACK_WORLD_RECOVER_MESSGAE = "Server response recover world";
    public static final String LOAD_ROOM_MESSAGE = "Client load room";
    public static final String BACK_LOAD_ROOM_MESSAGE = "Server response load room";
}