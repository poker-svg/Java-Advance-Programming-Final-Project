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
 * @Date: 2022-12-06 15:00:22
 * @LastEditTime: 2023-01-16 16:11:47
 * @LastEditors: Xu YangXin
 * @Description: 此类为观看本地录制内容的回顾界面
 * @FilePath: \jfp-poker-svg\src\main\java\cn\edu\nju\cs\game\screen\ReviewScreen.java
 * 此项目为南京大学《Java高级程序设计》项目
 */

package cn.edu.nju.cs.game.screen;

import java.io.IOException;
import java.util.Iterator;
import java.awt.event.KeyEvent;

import cn.edu.nju.cs.game.asciiPanel.AsciiPanel;
import cn.edu.nju.cs.logger.Recorder;

public class ReviewScreen implements Screen{
    Iterator<PlayScreen> iter;
    Recorder recorder;
    StartScreen backStartScreen;

    /**
     * @author: Xu YangXin
     * @param {String} recordName 记录文件名
     * @param {StartScreen} backStartScreen 返回的启动界面
     * @return {*}
     * @description: 构造器
     */
    public ReviewScreen(String recordName, StartScreen backStartScreen){
        try{
            this.recorder = Recorder.readRecorder(recordName);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        this.iter = this.recorder.playScreens().iterator();
        this.backStartScreen = backStartScreen;
    }

    @Override
    /**
     * @author: Xu YangXin
     * @param {AsciiPanel} terminal 目标终端
     * @return {*}
     * @description: 按帧展示记录文件的内容
     */    
    public void displayOutput(AsciiPanel terminal){
        if(iter.hasNext()) {
            PlayScreen playScreen = (PlayScreen)iter.next();
            playScreen.resetAllAI();
            playScreen.displayTiles(terminal, playScreen.getScrollX(), playScreen.getScrollY());
            playScreen.displayPlayerInfo(terminal);
            playScreen.displayCurWeaponInfo(terminal);
            playScreen.displayMessages(terminal, playScreen.messages());
        }
    }

    @Override
    /**
     * @author: Xu YangXin
     * @param {KeyEvent} key 玩家按下的按键
     * @return {*}
     * @description: 响应玩家的键盘操作
     */    
    public Screen respondToUserInput(KeyEvent key){
        if(key.getKeyCode() == KeyEvent.VK_ESCAPE)
            return this.backStartScreen;
        return this;
    }
}
