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
 * @Date: 2023-01-06 14:42:44
 * @LastEditTime: 2023-01-16 10:24:37
 * @LastEditors: Xu YangXin
 * @Description: 此类为客户端用于和服务器进行交互的代理类
 * @FilePath: \jfp-poker-svg\src\main\java\cn\edu\nju\cs\connection\Client.java
 * 此项目为南京大学《Java高级程序设计》项目
 */

package cn.edu.nju.cs.connection;


import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.awt.event.KeyEvent;

import cn.edu.nju.cs.game.screen.PlayScreen;

public class Client {
    private int roomAddr;
    private int playerIndex;
    public int roomAddr(){
        return this.roomAddr;
    }
    public int playerIndex(){
        return this.playerIndex;
    }

    private Message connectMessage;
    private Message enterRoomMessage;
    private Message addRoomMessage;
    private Message loadRoomMessage;
    private Message getLatestRoomMessage;
    private Message moveLeftMessage;
    private Message moveRightMessage;
    private Message moveDownMessage;
    private Message moveUpMessage;
    private Message setBombMessage;
    private Message attackMessage;
    private Message bigAttackMessage ;
    private Message switchWeaponMessage;
    private Message throwableWeaponMoveUpMessage;
    private Message throwableWeaponMoveDownMessage;
    private Message throwableWeaponMoveLeftMessage;
    private Message throwableWeaponMoveRightMessage;
    private Message pauseWorldMessage;
    private Message recoverWorldMessage;

    public Client(){
        
    }

    /**
     * @author: Xu YangXin
     * @param {int} roomAddr 房间地址
     * @param {String} playerName 玩家姓名
     * @param {PlayScreen} playScreen 目标房间
     * @return {*}
     * @description: 此构造器用于玩家开始新游戏时的配置
     */
    public Client(int roomAddr, String playerName, PlayScreen playScreen){
        playScreen.setPlayer(playScreen.creatureFactory().newPlayer(playScreen.messages(), playerName));

        this.connectMessage = new Message(null, null, -1, roomAddr, Message.CONNECT_MESSAGE);
        this.enterRoomMessage = new Message(null, playerName, -1, roomAddr, Message.ENTER_ROOM_MESSAGE);
        this.addRoomMessage = new Message(playScreen, null, -1, roomAddr, Message.ADD_ROOM_MESSAGE);

        this.connectRoom();

        this.getLatestRoomMessage = new Message(null, null, this.playerIndex, roomAddr, Message.GET_LATEST_ROOM_MESSAGE);
        this.moveLeftMessage = new Message(null, null, this.playerIndex, roomAddr, Message.MOVE_LEFT_MESSAGE);
        this.moveRightMessage = new Message(null, null, this.playerIndex, roomAddr, Message.MOVE_RIGHT_MESSAGE);
        this.moveDownMessage = new Message(null, null, this.playerIndex, roomAddr, Message.MOVE_DOWN_MESSAGE);
        this.moveUpMessage = new Message(null, null, this.playerIndex, roomAddr, Message.MOVE_UP_MESSAGE);
        this.setBombMessage = new Message(null, null, this.playerIndex, roomAddr, Message.SET_BOMB_MESSAGE);
        this.attackMessage = new Message(null, null, this.playerIndex, roomAddr, Message.ATTACK_MESSAGE);
        this.bigAttackMessage = new Message(null, null, this.playerIndex, roomAddr, Message.BIG_ATTACK_MESSAGE);
        this.switchWeaponMessage = new Message(null, null, this.playerIndex, roomAddr, Message.SWITCH_WEAPON_MESSAGE);
        this.throwableWeaponMoveUpMessage = new Message(null, null, this.playerIndex, roomAddr, Message.THROWABLE_WEAPON_MOVE_UP_MESSAGE);
        this.throwableWeaponMoveDownMessage = new Message(null, null, this.playerIndex, roomAddr, Message.THROWABLE_WEAPON_MOVE_DOWN_MESSAGE);
        this.throwableWeaponMoveLeftMessage = new Message(null, null, this.playerIndex, roomAddr, Message.THROWABLE_WEAPON_MOVE_LEFT_MESSAGE);
        this.throwableWeaponMoveRightMessage = new Message(null, null, this.playerIndex, roomAddr, Message.THROWABLE_WEAPON_MOVE_RIGHT_MESSAGE);
        this.pauseWorldMessage = new Message(null, null, this.playerIndex, roomAddr, Message.WORLD_PAUSE_MESSAGE);
        this.recoverWorldMessage = new Message(null, null, this.playerIndex, roomAddr, Message.WORLD_RECOVER_MESSAGE);
    }

    /**
     * @author: Xu YangXin
     * @param {int} roomAddr 房间地址
     * @param {int} playerIndex 玩家下标
     * @param {PlayScreen} playScreen 目标房间
     * @return {*}
     * @description: 此构造其用于玩家加载保存房间时的配置
     */
    public Client(int roomAddr, int playerIndex, PlayScreen playScreen){
        this.loadRoomMessage = new Message(playScreen, null, playerIndex, roomAddr, Message.LOAD_ROOM_MESSAGE);

        this.loadRoom();

        this.getLatestRoomMessage = new Message(null, null, playerIndex, roomAddr, Message.GET_LATEST_ROOM_MESSAGE);
        this.moveLeftMessage = new Message(null, null, playerIndex, roomAddr, Message.MOVE_LEFT_MESSAGE);
        this.moveRightMessage = new Message(null, null, playerIndex, roomAddr, Message.MOVE_RIGHT_MESSAGE);
        this.moveDownMessage = new Message(null, null, playerIndex, roomAddr, Message.MOVE_DOWN_MESSAGE);
        this.moveUpMessage = new Message(null, null, playerIndex, roomAddr, Message.MOVE_UP_MESSAGE);
        this.setBombMessage = new Message(null, null, playerIndex, roomAddr, Message.SET_BOMB_MESSAGE);
        this.attackMessage = new Message(null, null, playerIndex, roomAddr, Message.ATTACK_MESSAGE);
        this.bigAttackMessage = new Message(null, null, playerIndex, roomAddr, Message.BIG_ATTACK_MESSAGE);
        this.switchWeaponMessage = new Message(null, null, playerIndex, roomAddr, Message.SWITCH_WEAPON_MESSAGE);
        this.throwableWeaponMoveUpMessage = new Message(null, null, playerIndex, roomAddr, Message.THROWABLE_WEAPON_MOVE_UP_MESSAGE);
        this.throwableWeaponMoveDownMessage = new Message(null, null, playerIndex, roomAddr, Message.THROWABLE_WEAPON_MOVE_DOWN_MESSAGE);
        this.throwableWeaponMoveLeftMessage = new Message(null, null, playerIndex, roomAddr, Message.THROWABLE_WEAPON_MOVE_LEFT_MESSAGE);
        this.throwableWeaponMoveRightMessage = new Message(null, null, playerIndex, roomAddr, Message.THROWABLE_WEAPON_MOVE_RIGHT_MESSAGE);
        this.pauseWorldMessage = new Message(null, null, playerIndex, roomAddr, Message.WORLD_PAUSE_MESSAGE);
        this.recoverWorldMessage = new Message(null, null, playerIndex, roomAddr, Message.WORLD_RECOVER_MESSAGE);
    }


    /**
     * @author: Xu YangXin
     * @return {Message} 服务器响应的消息
     * @description: 连接服务器的目标房间，若房间不存在则将目标房间保存到服务器上
     */
    public Message connectRoom(){
        Message messageFromServer = this.sendMessage(this.connectMessage);

        // 房间存在
        if(messageFromServer.playScreen() != null){
            messageFromServer = this.sendMessage(this.enterRoomMessage);
        }
        // 房间不存在
        else{
            messageFromServer = this.sendMessage(this.addRoomMessage);
        }
        this.roomAddr = messageFromServer.roomAddr();
        this.playerIndex = messageFromServer.playerIndex();
        return messageFromServer;
    }

    /**
     * @author: Xu YangXin
     * @return {Message} 服务器响应的消息
     * @description: 加载玩家保存在本地的房间，并将目标房间发到服务器上
     */
    public Message loadRoom(){
        Message messageFromServer = this.sendMessage(this.loadRoomMessage);
        this.roomAddr = messageFromServer.roomAddr();
        this.playerIndex = messageFromServer.playerIndex();
        return messageFromServer;
    }

    /**
     * @author: Xu YangXin
     * @return {PlayScreen} 服务器上的目标房间
     * @description: 获取服务器上目标房间的最新状态
     */
    public PlayScreen getLatestRoom(){
        Message messageFromServer = this.sendMessage(this.getLatestRoomMessage);
        return messageFromServer.playScreen();
    }

    /**
     * @author: Xu YangXin
     * @param {KeyEvent} key 玩家按下的按键
     * @return {*}
     * @description: 处理玩家按下的按键，将对应的操作消息发送给服务器
     */
    public void operation(KeyEvent key){
        switch (key.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                this.sendMessage(this.moveLeftMessage);
                break;
            case KeyEvent.VK_RIGHT:
                this.sendMessage(this.moveRightMessage);
                break;
            case KeyEvent.VK_UP:
                this.sendMessage(this.moveUpMessage);
                break;
            case KeyEvent.VK_DOWN:
                this.sendMessage(this.moveDownMessage);
                break;
            case KeyEvent.VK_SPACE:
                this.sendMessage(this.setBombMessage);
                break;
            case KeyEvent.VK_X:
                this.sendMessage(this.attackMessage);
                break;
            case KeyEvent.VK_Z:
                this.sendMessage(this.bigAttackMessage);
                break;
            case KeyEvent.VK_C:
                this.sendMessage(this.switchWeaponMessage);
                break;
            case KeyEvent.VK_W:
                this.sendMessage(this.throwableWeaponMoveUpMessage);
                break;
            case KeyEvent.VK_S:
                this.sendMessage(this.throwableWeaponMoveDownMessage);
                break;
            case KeyEvent.VK_A:
                this.sendMessage(this.throwableWeaponMoveLeftMessage);
                break;
            case KeyEvent.VK_D:
                this.sendMessage(this.throwableWeaponMoveRightMessage);
                break;
            case KeyEvent.VK_ESCAPE:
                this.sendMessage(this.pauseWorldMessage);
                break;
        }
    }

    /**
     * @author: Xu YangXin
     * @return {*}
     * @description: 将玩家停止暂停的消息发给服务器
     */
    public void recoverWorld(){
        this.sendMessage(this.recoverWorldMessage);
    }

    /**
     * @author: Xu YangXin
     * @param {Message} messageToServer 要发送给服务器的消息
     * @return {Message} 服务器响应的消息
     * @description: send方法的包装版本，处理抛出的异常
     */    
    public Message sendMessage(Message messageToServer){
        try {
            return this.send(messageToServer);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @author: Xu YangXin
     * @param {Message} messageToServer 要发送给服务器的消息
     * @return {Message} 服务器响应的消息
     * @throws UnknownHostException 目标主机未知的异常
     * @throws IOException 输入输出异常
     * @description: 将目标消息发送到服务器
     */
    public Message send(Message messageToServer)
    throws UnknownHostException, IOException{
        Socket clientSocket = new Socket("localhost", 7070);

        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());

        //test
        boolean flag = true;
        while(flag){
            try {
                outToServer.write(ByteArrayUtils.objectToBytes(messageToServer).get());
                flag = false;
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
        outToServer.flush();
        clientSocket.shutdownOutput();

        InputStream inFromServer = clientSocket.getInputStream();
        byte[] dataFromServer = new byte[40960];
        inFromServer.read(dataFromServer);

        if(messageToServer.info() == Message.GET_LATEST_ROOM_MESSAGE){
            try {
                Thread.currentThread().sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Message messageFromServer = Message.dataToObj(dataFromServer);
        System.out.println("Response from Server : " + messageFromServer);
        clientSocket.close();

        return messageFromServer;
    }
}
