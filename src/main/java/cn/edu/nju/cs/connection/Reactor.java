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
 * @Date: 2022-12-25 09:37:55
 * @LastEditTime: 2023-01-16 10:45:06
 * @LastEditors: Xu YangXin
 * @Description: 此类为死循环处理客户端发来的请求的响应类
 * @FilePath: \jfp-poker-svg\src\main\java\cn\edu\nju\cs\connection\Reactor.java
 * 此项目为南京大学《Java高级程序设计》项目
 */

package cn.edu.nju.cs.connection;

import java.io.IOException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


/* This is the Initiation Dispatcher or the Reactor */
public class Reactor {
    private Map<Integer, EventHandler> registeredHandlers = new ConcurrentHashMap<Integer, EventHandler>();
    private Selector demultiplexer;

    /**
     * @author: Xu YangXin
     * @return {*}
     * @throws IOException 如果选择器开启出现了输入输出失败
     * @description: 构造器
     */
    public Reactor() throws IOException {
        demultiplexer = Selector.open();
    }

    /**
     * @author: Xu YangXin
     * @return {Selector}
     * @description: 读取分发器字段
     */    
    public Selector getDemultiplexer() {
        return demultiplexer;
    }

    /**
     * @author: Xu YangXin
     * @param {int} eventType 事件类型
     * @param {EventHandler} eventHandler 事件处理器
     * @return {*}
     * @description: 将 <事件类型,事件处理器> 的映射进行注册
     */
    public void registerEventHandler(
            int eventType, EventHandler eventHandler) {
        registeredHandlers.put(eventType, eventHandler);
    }

    /**
     * @author: Xu YangXin
     * @param {int} eventType 事件类型
     * @param {SelectableChannel} channel 信道
     * @return {*}
     * @throws Exception
     * @description: 将事件类型注册到信道处理中
     */
    public void registerChannel(
            int eventType, SelectableChannel channel) throws Exception {
        channel.register(demultiplexer, eventType);
    }

    /**
     * @author: Xu YangXin
     * @return {*}
     * @description: 开始死循环运行并处理事件
     */
    public void run() {
        try {
            // Loop indefinitely
            while (true) {
                demultiplexer.select();

                Set<SelectionKey> readyHandles =
                        demultiplexer.selectedKeys();
                Iterator<SelectionKey> handleIterator =
                        readyHandles.iterator();

                while (handleIterator.hasNext()) {
                    SelectionKey handle = handleIterator.next();

                    if (handle.isAcceptable()) {
                        EventHandler handler =
                                registeredHandlers.get(SelectionKey.OP_ACCEPT);
                        handler.handleEvent(handle);
                    }

                    if (handle.isReadable()) {
                        EventHandler handler =
                                registeredHandlers.get(SelectionKey.OP_READ);
                        handler.handleEvent(handle);
                        handleIterator.remove();
                    }

                    if (handle.isWritable()) {
                        EventHandler handler =
                                registeredHandlers.get(SelectionKey.OP_WRITE);
                        handler.handleEvent(handle);
                        handleIterator.remove();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
