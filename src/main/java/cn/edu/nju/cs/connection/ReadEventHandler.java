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
 * @LastEditTime: 2023-01-16 13:13:53
 * @LastEditors: Xu YangXin
 * @Description: 此类为服务器处理读事件的处理器类
 * @FilePath: \jfp-poker-svg\src\main\java\cn\edu\nju\cs\connection\ReadEventHandler.java
 * 此项目为南京大学《Java高级程序设计》项目
 */

package cn.edu.nju.cs.connection;


import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public class ReadEventHandler implements EventHandler {

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
    public ReadEventHandler(Selector demultiplexer, Server server) {
        this.demultiplexer = demultiplexer;
        this.server = server;
    }

    @Override
    /**
     * @author: Xu YangXin
     * @param {SelectionKey} handle 服务器接收到的选择键
     * @return {*}
     * @throws Exception 打开通道时可能会产生异常
     * @description: 处理服务器接收到的的读事件
     */
    public void handleEvent(SelectionKey handle) throws Exception {
        // System.out.println("===== Read Event Handler =====");

        //读入客户数据的缓冲区
        ByteBuffer inputBuffer = ByteBuffer.allocate(40960);
        //写入服务器数据的缓冲区
        ByteBuffer outputBuffer = ByteBuffer.allocate(40960);
        outputBuffer.flip();

        SocketChannel socketChannel =
                (SocketChannel) handle.channel();

        // 将这个隧道对应的客户的数据读入到缓冲区中
        inputBuffer.clear();
        socketChannel.read(inputBuffer);

        // 将服务器读入缓冲区切换为读状态，并将数据从缓冲区读入byte数组
        inputBuffer.flip();
        byte[] buffer = new byte[inputBuffer.limit()];
        inputBuffer.get(buffer);

        // 将服务器读入缓冲区切换为写状态，让客户端可以写入
        inputBuffer.flip();


        // 将服务器写出缓冲区切换为写状态，并注册服务器响应写事件
        outputBuffer.flip();

        outputBuffer.clear();
        outputBuffer.put(buffer);
        socketChannel.register(
                demultiplexer, SelectionKey.OP_WRITE, outputBuffer);

        Message messageFromClient = Message.dataToObj(buffer);
        System.out.println("Received message from client : " +
            messageFromClient);
    }
}
