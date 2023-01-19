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
 * @Date: 2023-01-09 10:27:11
 * @LastEditTime: 2023-01-16 13:18:57
 * @LastEditors: Xu YangXin
 * @Description: 此类为游戏的服务器类，网络地址是localhost:7070
 * @FilePath: \jfp-poker-svg\src\main\java\cn\edu\nju\cs\connection\Server.java
 * 此项目为南京大学《Java高级程序设计》项目
 */

package cn.edu.nju.cs.connection;

import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;

import cn.edu.nju.cs.game.asciiPanel.AsciiFont;
import cn.edu.nju.cs.game.asciiPanel.AsciiPanel;
import cn.edu.nju.cs.game.screen.PlayScreen;
import cn.edu.nju.cs.game.world.creature.bros.Player;

public class Server {
    //Server端口号
    public static final int SERVER_PORT = 7070;

    //房间号和对应游戏界面的映射
    private Map<Integer, Room> rooms;
    private Timer checkTimer;

    /**
     * @author: Xu YangXin
     * @return {*}
     * @description: 构造器
     */    
    public Server(){
        this.rooms = new ConcurrentHashMap<Integer, Room>();
        this.checkTimer = new Timer();
        this.checkTimer.schedule(
            new TimerTask() {
                public void run() {
                    for (Map.Entry<Integer, Room> entry : rooms.entrySet()) {
                        int roomAddr = entry.getKey();
                        Room room = entry.getValue();
                        PlayScreen playScreen = room.playScreen;
                        Timer timer = room.refreshTimer;

                        if(playScreen != null && playScreen.world().getPlayers().size() < 1){
                            deleteRoom(roomAddr);
                            timer.cancel();
                        }
                    }

                }
            }, 400 , 400);
    }

    public class Room{
        public PlayScreen playScreen;
        public AsciiPanel terminal;
        public Timer refreshTimer;
        public Room(PlayScreen playScreen){
            this.playScreen = playScreen;
            // this.playScreen.setPlayer(null);
            this.playScreen.musicStuff().stop();
            this.terminal = new AsciiPanel(100, 50, AsciiFont.KENNEY_1BIT_PACK_17_17);
            this.refreshTimer = new Timer();
            // 周期性刷新界面,100ms

            this.refreshTimer.schedule(
                new TimerTask() {
                    public void run() {
                        if(playScreen.world().getPlayers().size() < 1)
                            return;

                        terminal.clear();
                        playScreen.displayOutput(terminal);

                        for(Player player : playScreen.world().getPlayers()){
                            List<String> messages = player.messages();
                            List<String> oldMessages = player.oldMessages();
                            if(!messages.isEmpty()){
                                oldMessages.clear();
                                oldMessages.addAll(messages);
                                messages.clear();
                            }
                        }
                    }
                }, 100 , 100);
        }
    }

    /**
     * @author: Xu YangXin
     * @param {int} roomAddress 房间地址
     * @return {PlayScreen} 得到的房间
     * @description: 根据房间地址搜索服务器上的房间
     */    
    public PlayScreen getPlayScreen(int roomAddress){
        Room room = this.rooms.get(roomAddress);
        if(room == null)
            return null;
        return room.playScreen;
    }

    /**
     * @author: Xu YangXin
     * @param {int} roomAddress 房间地址
     * @param {PlayScreen} playScreen 对应的房间
     * @return {*}
     * @description: 将房间地址和房间的映射保存到服务器上
     */
    public void addRoom(int roomAddress, PlayScreen playScreen){
        this.rooms.put(roomAddress, new Room(playScreen));
    }

    /**
     * @author: Xu YangXin
     * @param {int} roomAddress 房间地址
     * @return {*}
     * @description: 删除服务器上的房间地址对应的目标房间
     */    
    public boolean deleteRoom(int roomAddress){
        return this.rooms.remove(roomAddress) != null;
    }

    /**
     * @author: Xu YangXin
     * @param {int} port 端口号
     * @return {*}
     * @throws Exception
     * @description: 在某个端口号上启动响应器
     */    
    public void startReactor(int port) throws Exception {

        ServerSocketChannel server = ServerSocketChannel.open();
        server.socket().bind(new InetSocketAddress(port));
        server.configureBlocking(false);

        Reactor reactor = new Reactor();
        reactor.registerChannel(SelectionKey.OP_ACCEPT, server);

        //对reactor注册对应时间的处理器
        reactor.registerEventHandler(
                SelectionKey.OP_ACCEPT, new AcceptEventHandler(
                reactor.getDemultiplexer(), this));

        reactor.registerEventHandler(
                SelectionKey.OP_READ, new ReadEventHandler(
                reactor.getDemultiplexer(), this));

        reactor.registerEventHandler(
                SelectionKey.OP_WRITE, new WriteEventHandler(
                reactor.getDemultiplexer(), this));

        //启动无限循环的服务器运行
        reactor.run(); // Run the dispatcher loop

    }

    public static void main(String[] args) {
        System.out.println("Server Started at port : " + SERVER_PORT);
        try {
            new Server().startReactor(SERVER_PORT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
