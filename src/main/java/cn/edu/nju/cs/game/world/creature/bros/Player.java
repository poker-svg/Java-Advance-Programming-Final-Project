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
 * @Date: 2022-11-27 17:53:41
 * @LastEditTime: 2023-01-16 17:22:17
 * @LastEditors: Xu YangXin
 * @Description: 此类代表玩家人物，是抽象基类，继承自生物类Creature
 * @FilePath: \jfp-poker-svg\src\main\java\cn\edu\nju\cs\game\world\creature\bros\Player.java
 * 此项目为南京大学《Java高级程序设计》项目
 */

package cn.edu.nju.cs.game.world.creature.bros;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import cn.edu.nju.cs.game.world.creature.Creature;
import cn.edu.nju.cs.game.world.item.World;


public abstract class Player extends Creature{
    private int playerIndex;
    private List<String> messages;
    private List<String> oldMessages;

    /**
     * @author: Xu YangXin
     * @param {World} world 生物所处世界
     * @param {char} glyph 生物的字符形象
     * @param {Color} color 生物的颜色
     * @param {int} maxHP 生物的最大血量
     * @param {int} attack 生物的手刀攻击力
     * @param {int} defense 生物的护甲值
     * @param {int} visionRadius 生物的视野
     * @param {int} moveSpeedInterval 生物的移动间隔
     * @return {*}
     * @description: 构造器
     */
    public Player (World world, char glyph, Color color, int maxHP,
                int attack, int defense, int visionRadius, int moveSpeedInterval){
        super(world, glyph, color, maxHP, attack, defense, visionRadius, moveSpeedInterval);
        this.oldMessages = new ArrayList<String>();
    }

    /**
     * @author: Xu YangXin
     * @return {int} 玩家下标
     * @description: 读取玩家的下标
     */    
    public int playerIndex(){
        return this.playerIndex;
    }

    /**
     * @author: Xu YangXin
     * @param {int} playerIndex 玩家下标
     * @return {*}
     * @description: 设置玩家的下标
     */    
    public void setPlayerIndex(int playerIndex){
        this.playerIndex = playerIndex;
    }

    /**
     * @author: Xu YangXin
     * @return {List<String>} 玩家的消息序列
     * @description: 读取玩家的消息序列
     */    
    public List<String> messages(){
        return this.messages;
    }

    /**
     * @author: Xu YangXin
     * @param {List<String>} messages 消息序列
     * @return {*}
     * @description: 设置玩家的消息序列
     */    
    public void setMessage(List<String> messages){
        this.messages = messages;
    }

    /**
     * @author: Xu YangXin
     * @return {List<String>} 旧消息序列
     * @description: 读取玩家的旧消息序列
     */    
    public List<String> oldMessages(){
        return this.oldMessages;
    }

    /**
     * @author: Xu YangXin
     * @param {List<String>} oldMessages 旧消息序列
     * @return {*}
     * @description: 设置玩家的旧消息序列
     */    
    public void setOldMessage(List<String> oldMessages){
        this.oldMessages = oldMessages;
    }
}
