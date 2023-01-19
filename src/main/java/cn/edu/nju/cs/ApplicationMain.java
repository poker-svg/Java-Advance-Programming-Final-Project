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
 * @Date: 2023-01-09 10:40:17
 * @LastEditTime: 2023-01-16 21:16:26
 * @LastEditors: Xu YangXin
 * @Description: 此类为项目应用的启动界面
 * @FilePath: \jfp-poker-svg\src\main\java\cn\edu\nju\cs\ApplicationMain.java
 * 此项目为南京大学《Java高级程序设计》项目
 */


package cn.edu.nju.cs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

import cn.edu.nju.cs.game.asciiPanel.AsciiFont;
import cn.edu.nju.cs.game.asciiPanel.AsciiPanel;
import cn.edu.nju.cs.game.screen.Screen;
import cn.edu.nju.cs.game.screen.StartScreen;


public class ApplicationMain extends JFrame implements KeyListener {

    private AsciiPanel terminal;
    private Screen screen;
    private Timer refreshTimer;

    /**
     * @author: Xu YangXin
     * @return {*}
     * @description: 构造器
     */    
    public ApplicationMain() {
        super();

        terminal = new AsciiPanel(100, 50, AsciiFont.KENNEY_1BIT_PACK_17_17);
        add(terminal);
        pack();

        screen = new StartScreen();
        addKeyListener(this);
        repaint();
    }

    @Override
    /**
     * @author: Xu YangXin
     * @return {*}
     * @description: 重绘
     */    
    public void repaint() {
        terminal.clear();
        screen.displayOutput(terminal);
        super.repaint();
    }

    /**
     * @author: Xu YangXin
     * @param {KeyEvent} e 用户按下的按键事件
     * @return {*}
     * @description: 处理玩家按下按键
     */    
    public void keyPressed(KeyEvent e) {
        screen = screen.respondToUserInput(e);
    }

    /**
     * @author: Xu YangXin
     * @param {KeyEvent} e 用户松开的按键事件
     * @return {*}
     * @description: 处理用户松开按键
     */    
    public void keyReleased(KeyEvent e) {
    }

    /**
     * @author: Xu YangXin
     * @param {KeyEvent} e 用户操作的按键事件
     * @return {*}
     * @description: 处理用户操作按键
     */    
    public void keyTyped(KeyEvent e) {
    }

    /**
     * @author: Xu YangXin
     * @param {ApplicationMain} refreshTarget 目标应用
     * @return {*}
     * @description: 每隔一段时间刷新目标的应用
     */    
    public static void refresh(ApplicationMain refreshTarget){
        refreshTarget.repaint();
    }

    public static void main(String[] args) {
        ApplicationMain app = new ApplicationMain();

		app.setVisible(true);
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 周期性刷新界面,400ms
        app.refreshTimer = new Timer();

        app.refreshTimer.schedule(
            new TimerTask() {
                public void run() {
                    refresh(app);
                }
            }, 400 , 400);
    }
}



class BackgroundPanel extends JPanel
{
	Image im;
	public BackgroundPanel(Image im)
	{
		this.im=im;
		this.setOpaque(true);
	}
	//Draw the back ground.
	public void paintComponent(Graphics g)
	{
		g.drawImage(im,0,0,this.getWidth(),this.getHeight(),this);
		super.paintComponents(g);
	}
}