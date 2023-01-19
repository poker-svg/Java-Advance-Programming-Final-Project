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
 * @Date: 2023-01-02 11:06:00
 * @LastEditTime: 2023-01-16 13:28:25
 * @LastEditors: Xu YangXin
 * @Description: 此类为服务器写事件的处理器
 * @FilePath: \jfp-poker-svg\src\main\java\cn\edu\nju\cs\connection\WriteEventHandler.java
 * 此项目为南京大学《Java高级程序设计》项目
 */

package cn.edu.nju.cs.connection;


import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;

import cn.edu.nju.cs.game.screen.PlayScreen;
import cn.edu.nju.cs.game.world.creature.Direction;
import cn.edu.nju.cs.game.world.creature.bros.Player;
import cn.edu.nju.cs.game.world.weapon.Weapon;
import cn.edu.nju.cs.game.world.weapon.bomb.BombGlove;
import cn.edu.nju.cs.game.world.weapon.throwable.ThrowableWeapon;

public class WriteEventHandler implements EventHandler {
    //分发器
    private Selector demultiplexer;
	//服务器
    private Server server;

    /**
     * @author: Xu YangXin
     * @param {Selector} demultiplexer 分发器
     * @param {Server} server 服务器
     * @return {*}
     * @description: 构造器
     */    
    public WriteEventHandler(Selector demultiplexer, Server server) {
        this.demultiplexer = demultiplexer;
        this.server = server;
    }

    /**
     * @author: Xu YangXin
     * @param {SelectionKey} handle 服务器接收到的选择键
     * @return {*}
     * @throws Exception
     * @description: 处理写事件的具体函数
     */
    public void handleEvent(SelectionKey handle) throws Exception {
        // System.out.println("===== Write Event Handler =====");

        //写入服务器数据的缓冲区
        ByteBuffer outputBuffer = null;
        SocketChannel socketChannel =
                (SocketChannel) handle.channel();
        outputBuffer = (ByteBuffer) handle.attachment();


        // 将服务器写出缓冲区切换为读状态，并将数据从缓冲区读入byte数组
        outputBuffer.flip();
        byte[] buffer = new byte[outputBuffer.limit()];
        outputBuffer.get(buffer);
        // 将服务器写出缓冲区切换为写状态，让服务器可以写入
        outputBuffer.flip();
        Message messageFromClient = Message.dataToObj(buffer);
        // System.out.println("Received message from client : " +
        //     messageFromClient);

        this.parseMessage(messageFromClient, outputBuffer);
        socketChannel.write(outputBuffer);
        socketChannel.close();
    }

    /**
     * @author: Xu YangXin
     * @param {Message} messageFromClient 客户端发来的消息
     * @param {ByteBuffer} outputBuffer 发回给客户端的缓冲区
     * @return {*}
     * @description: 解析客户端发来的消息，是一个分发器
     */    
    private void parseMessage(Message messageFromClient, ByteBuffer outputBuffer){
        switch (messageFromClient.info()) {
            case Message.CONNECT_MESSAGE:
                this.handleConnectMessage(messageFromClient, outputBuffer);
                break;
            case Message.ADD_ROOM_MESSAGE:
                this.handleAddRoomMessage(messageFromClient, outputBuffer);
                break;
            case Message.ENTER_ROOM_MESSAGE:
                this.handleEnterRoomMessage(messageFromClient, outputBuffer);
                break;
            case Message.LOAD_ROOM_MESSAGE:
                this.handleLoadRoomMessage(messageFromClient, outputBuffer);
                break;
            case Message.GET_LATEST_ROOM_MESSAGE:
                this.handleGetLatestRoomMessage(messageFromClient, outputBuffer);
                break;
            case Message.MOVE_DOWN_MESSAGE:
            case Message.MOVE_LEFT_MESSAGE:
            case Message.MOVE_RIGHT_MESSAGE:
            case Message.MOVE_UP_MESSAGE:
                this.handleMoveMessage(messageFromClient, outputBuffer);
                break;
            case Message.THROWABLE_WEAPON_MOVE_DOWN_MESSAGE:
            case Message.THROWABLE_WEAPON_MOVE_LEFT_MESSAGE:
            case Message.THROWABLE_WEAPON_MOVE_RIGHT_MESSAGE:
            case Message.THROWABLE_WEAPON_MOVE_UP_MESSAGE:
                this.handleThrowableWeaponMoveMessage(messageFromClient, outputBuffer);
                break;
            case Message.ATTACK_MESSAGE:
                this.handleAttackMessage(messageFromClient, outputBuffer);
                break;
            case Message.BIG_ATTACK_MESSAGE:
                this.handleBigAttackMessage(messageFromClient, outputBuffer);
                break;
            case Message.SWITCH_WEAPON_MESSAGE:
                this.handleSwitchWeaponMessage(messageFromClient, outputBuffer);
                break;
            case Message.SET_BOMB_MESSAGE:
                this.handleSetBombMessage(messageFromClient, outputBuffer);
                break;
            case Message.WORLD_PAUSE_MESSAGE:
                this.handlePauseWorld(messageFromClient, outputBuffer);
                break;
            case Message.WORLD_RECOVER_MESSAGE:
                this.handleRecoverWorld(messageFromClient, outputBuffer);
                break;
            default:
                break;
        }
    }

    /**
     * @author: Xu YangXin
     * @param {int} roomAddr
     * @return {PlayScreen} 目标房间
     * @description: 根据房间地址搜索房间
     */    
    private PlayScreen searchRoom(int roomAddr){
        return this.server.getPlayScreen(roomAddr);
    }

    /**
     * @author: Xu YangXin
     * @param {Message} outputBufferMessage 要写入缓冲区的消息
     * @param {ByteBuffer} outputBuffer 缓冲区
     * @return {*}
     * @description: 将Message对象转换成字节流并写入缓冲区
     */
    private void writeMessageToOutputBuffer(Message outputBufferMessage, ByteBuffer outputBuffer){
        outputBuffer.clear();
        outputBuffer.put(ByteArrayUtils.objectToBytes(outputBufferMessage).get());
        //切换为读状态，这样用户可以读到最新的数据
        outputBuffer.flip();
    }

    /**
     * @author: Xu YangXin
     * @param {Message} messageFromClient 客户端发来的消息
     * @param {ByteBuffer} outputBuffer 输出缓冲区
     * @return {*}
     * @description: 处理客户端的连接房间信息请求
     */
    private void handleConnectMessage(Message messageFromClient, ByteBuffer outputBuffer){
        PlayScreen targetPlayScreen = this.searchRoom(messageFromClient.roomAddr());

        this.writeMessageToOutputBuffer(
            new Message(targetPlayScreen, null, -1,
                        messageFromClient.roomAddr(),
                        Message.BACK_CONNECT_ROOM_MESSAGE),
            outputBuffer);
    }

    /**
     * @author: Xu YangXin
     * @param {Message} messageFromClient 客户端发来的消息
     * @param {ByteBuffer} outputBuffer 输出缓冲区
     * @return {*}
     * @description: 处理客户端的添加房间信息请求
     */
    private void handleAddRoomMessage(Message messageFromClient, ByteBuffer outputBuffer){
        PlayScreen playScreen = new PlayScreen(messageFromClient.playScreen());
        int roomAddr = messageFromClient.roomAddr();
        this.server.addRoom(roomAddr, playScreen);

        this.writeMessageToOutputBuffer(
            new Message(null, null, 0,
                        messageFromClient.roomAddr(),
                        Message.BACK_ADD_ROOM_MESSAGE),
            outputBuffer);
    }

    /**
     * @author: Xu YangXin
     * @param {Message} messageFromClient 客户端发来的消息
     * @param {ByteBuffer} outputBuffer 输出缓冲区
     * @return {*}
     * @description: 处理客户端的进入房间信息请求
     */
    private void handleEnterRoomMessage(Message messageFromClient, ByteBuffer outputBuffer){
        PlayScreen room = this.searchRoom(messageFromClient.roomAddr());
        String playerName = messageFromClient.playerName();
        Player player = room.creatureFactory().newPlayer(new ArrayList<>(), playerName);

        this.writeMessageToOutputBuffer(
            new Message(null, playerName, player.playerIndex(),
                        messageFromClient.roomAddr(),
                        Message.BACK_ENTER_ROOM_MESSAGE),
            outputBuffer);
    }

    /**
     * @author: Xu YangXin
     * @param {Message} messageFromClient 客户端发来的消息
     * @param {ByteBuffer} outputBuffer 输出缓冲区
     * @return {*}
     * @description: 处理客户端的加载保存房间信息请求
     */
    private void handleLoadRoomMessage(Message messageFromClient, ByteBuffer outputBuffer){
        int roomAddr = messageFromClient.roomAddr();
        int playerIndex = messageFromClient.playerIndex();
        PlayScreen playScreen = this.searchRoom(roomAddr);

        if(playScreen == null){
            playScreen = new PlayScreen(messageFromClient.playScreen());
            this.server.addRoom(roomAddr, playScreen);
        }

        this.writeMessageToOutputBuffer(
            new Message(null, null, playerIndex, roomAddr,
                        Message.BACK_LOAD_ROOM_MESSAGE),
            outputBuffer);
    }

    /**
     * @author: Xu YangXin
     * @param {Message} messageFromClient 客户端发来的消息
     * @param {ByteBuffer} outputBuffer 输出缓冲区
     * @return {*}
     * @description: 处理客户端的获取最新房间信息请求
     */
    private void handleGetLatestRoomMessage(Message messageFromClient, ByteBuffer outputBuffer){
        int roomAddr = messageFromClient.roomAddr();
        int playerIndex = messageFromClient.playerIndex();
        PlayScreen playScreen = this.searchRoom(roomAddr);

        this.writeMessageToOutputBuffer(
            new Message(playScreen, null, playerIndex, roomAddr,
                        Message.BACK_GET_LATEST_ROOM_MESSAGE),
            outputBuffer);
    }

    /**
     * @author: Xu YangXin
     * @param {Message} messageFromClient 客户端发来的消息
     * @param {ByteBuffer} outputBuffer 输出缓冲区
     * @return {*}
     * @description: 处理客户端的移动信息请求
     */
    private void handleMoveMessage(Message messageFromClient, ByteBuffer outputBuffer){
        int roomAddr = messageFromClient.roomAddr();
        int playerIndex = messageFromClient.playerIndex();
        PlayScreen playScreen = this.searchRoom(roomAddr);
        Player player = playScreen.world().searchPlayer(playerIndex);

        if(player == null || playScreen == null) return;

        switch (messageFromClient.info()) {
            case Message.MOVE_LEFT_MESSAGE:
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
            case Message.MOVE_RIGHT_MESSAGE:
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
            case Message.MOVE_UP_MESSAGE:
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
            case Message.MOVE_DOWN_MESSAGE:
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
            default:
                break;
        }

        playScreen.refreshWorld();
        this.writeMessageToOutputBuffer(
            new Message(null, null,
                        playerIndex, roomAddr,
                        Message.BACK_MOVE_MESSAGE),
            outputBuffer);
    }

    /**
     * @author: Xu YangXin
     * @param {Message} messageFromClient 客户端发来的消息
     * @param {ByteBuffer} outputBuffer 输出缓冲区
     * @return {*}
     * @description: 处理客户端的移动准星信息请求
     */
    private void handleThrowableWeaponMoveMessage(Message messageFromClient, ByteBuffer outputBuffer){
        int roomAddr = messageFromClient.roomAddr();
        int playerIndex = messageFromClient.playerIndex();
        PlayScreen playScreen = this.searchRoom(roomAddr);
        Player player = playScreen.world().searchPlayer(playerIndex);

        if(player == null || playScreen == null) return;

        switch (messageFromClient.info()) {
            case Message.THROWABLE_WEAPON_MOVE_LEFT_MESSAGE:
                try {
                    ((ThrowableWeapon)player.weaponFactory().curWeapon()).dxMoveLeft();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                } catch (ClassCastException e){
                    e.printStackTrace();
                }
                break;
            case Message.THROWABLE_WEAPON_MOVE_RIGHT_MESSAGE:
                try {
                    ((ThrowableWeapon)player.weaponFactory().curWeapon()).dxMoveRight();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                } catch (ClassCastException e){
                    e.printStackTrace();
                }
                break;
            case Message.THROWABLE_WEAPON_MOVE_UP_MESSAGE:
                try {
                    ((ThrowableWeapon)player.weaponFactory().curWeapon()).dyMoveUp();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                } catch (ClassCastException e){
                    e.printStackTrace();
                }
                break;
            case Message.THROWABLE_WEAPON_MOVE_DOWN_MESSAGE:
                try {
                    ((ThrowableWeapon)player.weaponFactory().curWeapon()).dyMoveDown();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                } catch (ClassCastException e){
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }

        playScreen.refreshWorld();
        this.writeMessageToOutputBuffer(
            new Message(null, null, playerIndex, roomAddr,
                        Message.BACK_THROWABLE_WEAPON_MOVE_MESSAGE),
            outputBuffer);
    }

    /**
     * @author: Xu YangXin
     * @param {Message} messageFromClient 客户端发来的消息
     * @param {ByteBuffer} outputBuffer 输出缓冲区
     * @return {*}
     * @description: 处理客户端的攻击信息请求
     */
    private void handleAttackMessage(Message messageFromClient, ByteBuffer outputBuffer){
        int roomAddr = messageFromClient.roomAddr();
        int playerIndex = messageFromClient.playerIndex();
        PlayScreen playScreen = this.searchRoom(roomAddr);
        Player player = playScreen.world().searchPlayer(playerIndex);

        try {
            Weapon weapon = player.weaponFactory().curWeapon();
            if(weapon.attack())
                weapon.setActive(false);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        playScreen.refreshWorld();
        this.writeMessageToOutputBuffer(
            new Message(null, null,
                        playerIndex, roomAddr,
                        Message.BACK_ATTACK_MESSAGE),
            outputBuffer);
    }

    /**
     * @author: Xu YangXin
     * @param {Message} messageFromClient 客户端发来的消息
     * @param {ByteBuffer} outputBuffer 输出缓冲区
     * @return {*}
     * @description: 处理客户端的大招信息请求
     */
    private void handleBigAttackMessage(Message messageFromClient, ByteBuffer outputBuffer){
        int roomAddr = messageFromClient.roomAddr();
        int playerIndex = messageFromClient.playerIndex();
        PlayScreen playScreen = this.searchRoom(roomAddr);
        Player player = playScreen.world().searchPlayer(playerIndex);

        try {
            Weapon weapon = player.weaponFactory().curWeapon();
            if(weapon.bigAttack()){
                weapon.clearEnergy();
                weapon.setActive(false);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        playScreen.refreshWorld();
        this.writeMessageToOutputBuffer(
            new Message(null, null,
                        playerIndex, roomAddr,
                        Message.BACK_BIG_ATTACK_MESSAGE),
            outputBuffer);
    }

    /**
     * @author: Xu YangXin
     * @param {Message} messageFromClient 客户端发来的消息
     * @param {ByteBuffer} outputBuffer 输出缓冲区
     * @return {*}
     * @description: 处理客户端的切换武器信息请求
     */
    private void handleSwitchWeaponMessage(Message messageFromClient, ByteBuffer outputBuffer){
        int roomAddr = messageFromClient.roomAddr();
        int playerIndex = messageFromClient.playerIndex();
        PlayScreen playScreen = this.searchRoom(roomAddr);
        Player player = playScreen.world().searchPlayer(playerIndex);

        try {
            player.weaponFactory().switchWeapon();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        playScreen.refreshWorld();
        this.writeMessageToOutputBuffer(
            new Message(null, null,
                        playerIndex, roomAddr,
                        Message.BACK_SWITCH_WEAPON_MESSAGE),
            outputBuffer);
    }

    /**
     * @author: Xu YangXin
     * @param {Message} messageFromClient 客户端发来的消息
     * @param {ByteBuffer} outputBuffer 输出缓冲区
     * @return {*}
     * @description: 处理客户端的放置炸弹信息请求
     */
    private void handleSetBombMessage(Message messageFromClient, ByteBuffer outputBuffer){
        int roomAddr = messageFromClient.roomAddr();
        int playerIndex = messageFromClient.playerIndex();
        PlayScreen playScreen = this.searchRoom(roomAddr);
        Player player = playScreen.world().searchPlayer(playerIndex);

        player.setBomb();

        playScreen.refreshWorld();
        this.writeMessageToOutputBuffer(
            new Message(null, null,
                        playerIndex, roomAddr,
                        Message.BACK_SET_BOMB_MESSAGE),
            outputBuffer);
    }

    /**
     * @author: Xu YangXin
     * @param {Message} messageFromClient 客户端发来的消息
     * @param {ByteBuffer} outputBuffer 输出缓冲区
     * @return {*}
     * @description: 处理客户端的暂停房间信息请求
     */
    private void handlePauseWorld(Message messageFromClient, ByteBuffer outputBuffer){
        int roomAddr = messageFromClient.roomAddr();
        int playerIndex = messageFromClient.playerIndex();
        PlayScreen playScreen = this.searchRoom(roomAddr);
        playScreen.world().setWorldPauseFlag(true);

        this.writeMessageToOutputBuffer(
            new Message(null, null,
                        playerIndex, roomAddr,
                        Message.BACK_WORLD_PAUSE_MESSAGE),
            outputBuffer);
    }

    /**
     * @author: Xu YangXin
     * @param {Message} messageFromClient 客户端发来的消息
     * @param {ByteBuffer} outputBuffer 输出缓冲区
     * @return {*}
     * @description: 处理客户端的恢复房间信息请求
     */
    private void handleRecoverWorld(Message messageFromClient, ByteBuffer outputBuffer){
        int roomAddr = messageFromClient.roomAddr();
        int playerIndex = messageFromClient.playerIndex();
        PlayScreen playScreen = this.searchRoom(roomAddr);
        playScreen.world().setWorldPauseFlag(false);

        this.writeMessageToOutputBuffer(
            new Message(null, null,
                        playerIndex, roomAddr,
                        Message.BACK_WORLD_RECOVER_MESSGAE),
            outputBuffer);
    }

}
