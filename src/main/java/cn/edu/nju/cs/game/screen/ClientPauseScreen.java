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
 * @Date: 2023-01-06 10:59:18
 * @LastEditTime: 2023-01-16 15:37:30
 * @LastEditors: Xu YangXin
 * @Description: 此类为客户端的暂停界面类
 * @FilePath: \jfp-poker-svg\src\main\java\cn\edu\nju\cs\game\screen\ClientPauseScreen.java
 * 此项目为南京大学《Java高级程序设计》项目
 */

package cn.edu.nju.cs.game.screen;

import cn.edu.nju.cs.connection.Client;
import cn.edu.nju.cs.game.asciiPanel.AsciiPanel;
import cn.edu.nju.cs.game.world.item.World;
import cn.edu.nju.cs.game.world.item.WorldBuilder;
import cn.edu.nju.cs.logger.Recorder;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.awt.Color;
import java.util.Random;

import org.javatuples.Pair;

public class ClientPauseScreen extends RestartScreen{
    private int stage = 0; // 某个分支功能的进度
    private int branchIndex = 0;// 分支功能的下标
    private List<Pair<String,Color>> branches; // 功能分支
    private ClientPlaySreen clientPlaySreen;
    private World world;
    private Client client;
    private boolean confirmSaveMap;
    private boolean confirmRestartGame;
    private boolean confirmSnapShot;
    private boolean confirmRecord;

    /**
     * @author: Xu YangXin
     * @param {ClientPlayScreen} clientPlayScreen 客户游戏界面
     * @return {*}
     * @description: 构造器
     */    
    public ClientPauseScreen(ClientPlaySreen clientPlaySreen){
        this.clientPlaySreen = clientPlaySreen;
        this.world = clientPlaySreen.playScreen().world();
        this.client = clientPlaySreen.client();
        this.branchIndex = 0;
        this.stage = 0;
        this.branches = new ArrayList<>();
        Random random = new Random();

        this.branches.add(Pair.with("Save Map",
                        new Color(random.nextInt(255),
                                random.nextInt(255),
                                random.nextInt(255))));
        this.branches.add(Pair.with("Restart Game",
                        new Color(random.nextInt(255),
                                random.nextInt(255),
                                random.nextInt(255))));
        this.branches.add(Pair.with("Snap Shot",
                        new Color(random.nextInt(255),
                                random.nextInt(255),
                                random.nextInt(255))));
        if(this.clientPlaySreen.recordFlag()){
            this.branches.add(Pair.with("End Record",
                            new Color(random.nextInt(255),
                                    random.nextInt(255),
                                    random.nextInt(255))));
        }
        else{
            this.branches.add(Pair.with("Start Record",
                            new Color(random.nextInt(255),
                                    random.nextInt(255),
                                    random.nextInt(255))));
        }
    }

    @Override
    /**
     * @author: Xu YangXin
     * @param {AsciiPanel} terminal 要输出的目标终端
     * @return {*}
     * @description: 将当前的内容显示到屏幕上
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
                displayStage_1(terminal);
                break;
            default:
                break;
        }
    }

    @Override
    /**
     * @author: Xu YangXin
     * @param {KeyEvent} key 用户按下的按键
     * @return {Screen}
     * @description: 响应用户按下的按键操作
     */    
    public Screen respondToUserInput(KeyEvent key) {
        switch (this.stage)
        {
            case 0: // 选择分支功能
                return selectBranches(key);
            case 1: // 确认
                switch (this.branchIndex)
                {
                    case 0: // 保存地图的第二阶段 —— 确认
                        return saveMap(key);
                    case 1: // 重新开始游戏的第二阶段 —— 确认
                        return restartGame(key);
                    case 2: // 快照的第二阶段 —— 确认
                        return snapShot(key);
                    case 3: // 录制的第三阶段 —— 确认
                        return record(key);
                    default:
                        return this;
                }
            default:
                return this;
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

    /**
     * @author: Xu YangXin
     * @param {KeyEvent} key 用户按下的按键
     * @return {Screen} 更新后的屏幕
     * @description: 处理用户选择功能分支时的操作
     */
    private Screen selectBranches(KeyEvent key){
        // 开始选择功能
        if(stage == 0){ // 尚未结束选择，按下enter键结束功能分支选择
            switch (key.getKeyCode()) {
                case KeyEvent.VK_ESCAPE:
                    this.client.recoverWorld();
                    this.clientPlaySreen.musicStuff().start();
                    return this.clientPlaySreen;
                case KeyEvent.VK_ENTER:
                    if(branchIndex == 0) // 存储地图的功能分支
                        confirmSaveMap = false;
                    else if(branchIndex == 1) // 重新开始游戏的功能分支
                        confirmRestartGame = false;
                    else if(branchIndex == 2) // 快照的功能分支
                        confirmSnapShot = false;
                    else if(branchIndex == 3) // 录制的功能分支
                        confirmRecord = false;
                    stage++;    // 进入该功能分支的第二阶段
                    break;
                case KeyEvent.VK_UP:
                    if(branchIndex > 0) branchIndex--;
                    break;
                case KeyEvent.VK_DOWN:
                    if(branchIndex < this.branches.size()-1) branchIndex++;
                    break;
            }
        }
        return this;
    }

    /**
     * @author: Xu YangXin
     * @param {KeyEvent} key 用户按下的按键
     * @return {Screen} 更新后的屏幕
     * @description: 处理用户存储地图时的操作
     */
    private Screen saveMap(KeyEvent key){
        if(stage == 1){ // 取消或者确认保存地图
            switch (key.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    confirmSaveMap = true;
                    break;
                case KeyEvent.VK_RIGHT:
                    confirmSaveMap = false;
                    break;
                case KeyEvent.VK_ENTER:
                    if(confirmSaveMap){
                        WorldBuilder.storeWorld(this.world);
                        confirmSaveMap = false;
                    }
                    stage--;
                    break;
                case KeyEvent.VK_ESCAPE:
                    stage--;
                    break;
                default:
                    break;
            }
        }
        return this;
    }

    /**
     * @author: Xu YangXin
     * @param {KeyEvent} key 用户按下的按键
     * @return {Screen} 更新后的屏幕
     * @description: 处理用户重启游戏时的操作
     */
    private Screen restartGame(KeyEvent key){
        if(stage == 1){ // 取消或者确认重新开始游戏
            switch (key.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    confirmRestartGame = true;
                    break;
                case KeyEvent.VK_RIGHT:
                    confirmRestartGame = false;
                    break;
                case KeyEvent.VK_ENTER:
                    if(confirmRestartGame)
                        return new StartScreen();
                    stage--;
                    break;
                case KeyEvent.VK_ESCAPE:
                    stage--;
                    break;
                default:
                    break;
            }
        }
        return this;
    }

    /**
     * @author: Xu YangXin
     * @param {KeyEvent} key 用户按下的按键
     * @return {Screen} 更新后的屏幕
     * @description: 处理用户暂存游戏进度的操作
     */
    private Screen snapShot(KeyEvent key){
        if(stage == 1){ // 取消或者确认快照
            switch (key.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    confirmSnapShot = true;
                    break;
                case KeyEvent.VK_RIGHT:
                    confirmSnapShot = false;
                    break;
                case KeyEvent.VK_ENTER:
                    if(confirmSnapShot){
                        try {
                            Recorder.snapShot(this.clientPlaySreen.playScreen());
                        } catch (IOException e) {
                            System.err.println(e);
                        }
                        catch (ClassNotFoundException e) {
                            System.err.println(e);
                        }
                        confirmSnapShot = false;
                    }
                    stage--;
                    break;
                case KeyEvent.VK_ESCAPE:
                    stage--;
                    break;
                default:
                    break;
            }
        }
        return this;
    }

    /**
     * @author: Xu YangXin
     * @param {KeyEvent} key 用户按下的按键
     * @return {Screen} 更新后的屏幕
     * @description: 处理用户开始/结束记录游戏时的操作
     */
    private Screen record(KeyEvent key){
        if(stage == 1){ // 取消或者确认记录
            switch (key.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    confirmRecord = true;
                    break;
                case KeyEvent.VK_RIGHT:
                    confirmRecord = false;
                    break;
                case KeyEvent.VK_ENTER:
                    if(confirmRecord){
                        if(switchRecord())
                            this.clientPlaySreen.startRecord();
                        else
                            this.clientPlaySreen.endRecord();
                        confirmRecord = false;
                    }
                    stage--;
                    break;
                case KeyEvent.VK_ESCAPE:
                    stage--;
                    break;
                default:
                    break;
            }
        }
        return this;
    }

    /**
     * @author: Xu YangXin
     * @param {AsciiPanel} terminal 目标终端
     * @return {*}
     * @description: 用于展示各个功能分支的第一阶段
     */
    private void displayStage_1(AsciiPanel terminal){ // 功能的第二阶段
        switch (this.branchIndex) {
            case 0: // 保存地图的功能
                if(confirmSaveMap){
                    terminal.write("Yes", 45, 25, AsciiPanel.brightYellow, AsciiPanel.brown);
                    terminal.write("No", 50, 25, AsciiPanel.brown);
                }
                else{
                    terminal.write("Yes", 45, 25, AsciiPanel.brown);
                    terminal.write("No", 50, 25, AsciiPanel.brightYellow, AsciiPanel.brown);
                }
                break;
            case 1: // 重新开始游戏的功能
                if(confirmRestartGame){
                    terminal.write("Yes", 45, 25, AsciiPanel.brightYellow, AsciiPanel.brown);
                    terminal.write("No", 50, 25, AsciiPanel.brown);
                }
                else{
                    terminal.write("Yes", 45, 25, AsciiPanel.brown);
                    terminal.write("No", 50, 25, AsciiPanel.brightYellow, AsciiPanel.brown);
                }
                break;
            case 2:
                if(confirmSnapShot){
                    terminal.write("Yes", 45, 25, AsciiPanel.brightYellow, AsciiPanel.brown);
                    terminal.write("No", 50, 25, AsciiPanel.brown);
                }
                else{
                    terminal.write("Yes", 45, 25, AsciiPanel.brown);
                    terminal.write("No", 50, 25, AsciiPanel.brightYellow, AsciiPanel.brown);
                }
                break;
            case 3:
                if(confirmRecord){
                    terminal.write("Yes", 45, 25, AsciiPanel.brightYellow, AsciiPanel.brown);
                    terminal.write("No", 50, 25, AsciiPanel.brown);
                }
                else{
                    terminal.write("Yes", 45, 25, AsciiPanel.brown);
                    terminal.write("No", 50, 25, AsciiPanel.brightYellow, AsciiPanel.brown);
                }
                break;
            default:
                break;
        }
    }

    /**
     * @author: Xu YangXin
     * @return {boolean} 是否处于记录中状态
     * @description: 切换记录状态
     */
    private boolean switchRecord(){
        Pair<String,Color> targetPair = this.branches.get(3);
        if(targetPair.getValue0() == "Start Record") {
            this.branches.set(3, Pair.with("End Record", (Color)targetPair.getValue1()));
            return true;
        }
        else {
            this.branches.set(3, Pair.with("Start Record", (Color)targetPair.getValue1()));
            return false;
        }
    }

}
