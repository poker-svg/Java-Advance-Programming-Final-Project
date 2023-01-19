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
 * @Date: 2023-01-09 17:53:57
 * @LastEditTime: 2023-01-16 21:26:36
 * @LastEditors: Xu YangXin
 * @Description: 此为地形的枚举，包含游戏项目中所有东西的形象
 * @FilePath: \jfp-poker-svg\src\main\java\cn\edu\nju\cs\game\world\item\Tile.java
 * 此项目为南京大学《Java高级程序设计》项目
 */

package cn.edu.nju.cs.game.world.item;

import cn.edu.nju.cs.game.asciiPanel.AsciiPanel;

import java.awt.Color;
import java.io.Serializable;
import java.util.Random;
import java.util.PrimitiveIterator.OfDouble;

public enum Tile implements Serializable{

    // 玩家状态
    HP((char)(256+10*49+42), AsciiPanel.brightRed),
    SPEED((char)(256+0*49+39), AsciiPanel.brightBlue),
    ENERGY((char)(256+15*49+24), AsciiPanel.brightYellow),
    UP((char)(256+10*49+44), AsciiPanel.skyBlue),
    RIGHT((char)(256+10*49+45), AsciiPanel.skyBlue),
    DOWN((char)(256+10*49+46), AsciiPanel.skyBlue),
    LEFT((char)(256+10*49+47), AsciiPanel.skyBlue),

    // 地形
    FLOOR((char)(256+2*49+11), new Color(122, 68, 74)),
    FLOOR_LIGHT((char)(256+2*49+11), new Color(240,140,31)),
    WALL((char)(256+17*49+10), Color.gray),
    BOUNDS('x', AsciiPanel.magenta),
    DOOR((char)(256+9*49+3), AsciiPanel.brown),
    BOX_CLOSE((char)(256+6*49+8), AsciiPanel.brown),
    BOX_OPEN((char)(256+6*49+9), AsciiPanel.brown),
    FIRE_PILE((char)(256+10*49+14), AsciiPanel.brightRed),
    GRASS_1((char)(256+2*49+0), AsciiPanel.grassGreen),
    GRASS_2((char)(256+0*49+5), AsciiPanel.grassGreen),
    GRASS_3((char)(256+0*49+6), AsciiPanel.grassGreen),
    GRASS_4((char)(256+0*49+7), AsciiPanel.grassGreen),
    GRASS_5((char)(256+2*49+1), AsciiPanel.grassGreen),
    GRASS_6((char)(256+2*49+2), AsciiPanel.grassGreen),
    GRASS_7((char)(256+6*49+13), AsciiPanel.grassGreen),
    GRASS_8((char)(256+6*49+14), AsciiPanel.grassGreen),
    GRASS_9((char)(256+6*49+15), AsciiPanel.grassGreen),
    GRASS_10((char)(256+6*49+16), AsciiPanel.grassGreen),
    GRASS_11((char)(256+6*49+17), AsciiPanel.grassGreen),
    TREE_1((char)(256+1*49+0), AsciiPanel.grassGreen),
    TREE_2((char)(256+1*49+1), AsciiPanel.grassGreen),
    TREE_3((char)(256+1*49+2), AsciiPanel.grassGreen),
    TREE_4((char)(256+1*49+3), AsciiPanel.grassGreen),
    TREE_5((char)(256+1*49+4), AsciiPanel.grassGreen),
    TREE_6((char)(256+1*49+5), AsciiPanel.grassGreen),
    TREE_7((char)(256+2*49+6), new Color(191,121,88)),
    HOUSE_1((char)(256+19*49+0), AsciiPanel.brown),
    HOUSE_2((char)(256+19*49+1), AsciiPanel.brown),
    HOUSE_3((char)(256+19*49+2), AsciiPanel.brown),
    HOUSE_4((char)(256+19*49+3), AsciiPanel.brown),
    HOUSE_5((char)(256+19*49+4), AsciiPanel.brown),
    HOUSE_6((char)(256+20*49+0), AsciiPanel.brown),
    HOUSE_7((char)(256+20*49+1), AsciiPanel.brown),
    HOUSE_8((char)(256+20*49+2), AsciiPanel.brown),
    HOUSE_9((char)(256+20*49+3), AsciiPanel.brown),
    HOUSE_10((char)(256+20*49+4), AsciiPanel.brown),
    HOUSE_11((char)(256+20*49+5), AsciiPanel.brown),
    HOUSE_12((char)(256+20*49+6), AsciiPanel.brown),
    HOUSE_13((char)(256+20*49+7), AsciiPanel.brown),
    HOUSE_14((char)(256+20*49+8), AsciiPanel.brown),
    HOUSE_15((char)(256+10*49+18), AsciiPanel.brown),
    HOUSE_16((char)(256+10*49+19), AsciiPanel.brown),
    BOARD_1((char)(256+7*49+0), AsciiPanel.brown),
    BOARD_2((char)(256+7*49+1), AsciiPanel.brown),
    BOARD_3((char)(256+7*49+2), AsciiPanel.brown),
    BOARD_4((char)(256+4*49+48), AsciiPanel.brown),
    BOARD_5((char)(256+5*49+48), AsciiPanel.brown),
    BOARD_6((char)(256+6*49+48), AsciiPanel.brown),
    BOARD_7((char)(256+7*49+48), AsciiPanel.brown),
    BOARD_8((char)(256+7*49+14), AsciiPanel.brown),
    BOARD_9((char)(256+7*49+15), AsciiPanel.brown),
    BOARD_10((char)(256+7*49+16), AsciiPanel.brown),

    // 炸弹
    BOMB((char)(256+21*49+40), new Color(244, 180, 27)),
    BOMB_X((char)(256+15*49+20), AsciiPanel.red),
    BOMB_Y((char)(256+13*49+25), AsciiPanel.red),

    // 子弹
    BULLET((char)(256+11*49+26), AsciiPanel.brightYellow),
    NORMAL_BULLET((char)(256+4*49+22), new Color(244, 180, 27)),
    SHOT_BULLET((char)(256+4*49+22), AsciiPanel.red),
    SNIPER_BULLET((char)(256+4*49+22), AsciiPanel.brightYellow),
    LASER_BEAM_X((char)(256+15*49+20), AsciiPanel.brightBlue),
    LASER_BEAM_Y((char)(256+3*49+14), AsciiPanel.brightBlue),
    BASEBALL((char)15, AsciiPanel.red),
    THROW_ITEM((char)(256+9*49+45), AsciiPanel.brightYellow),
    DETONATOR((char)(256+9*49+46), new Color(230, 72, 46)),
    AIM((char)(256+14*49+22), AsciiPanel.green),

    // 生物
    FIRST_BRO((char)(256+7*49+24), AsciiPanel.brightRed),
    SECOND_BRO((char)(256+6*49+31), Color.orange),
    THIRD_BRO((char)(256+0*49+29), AsciiPanel.brightYellow),
    FORTH_BRO((char)(256+9*49+29), AsciiPanel.brightGreen),
    FIFTH_BRO((char)(256+9*49+30), AsciiPanel.brightBlue),
    SIXTH_BRO((char)(256+2*49+24), AsciiPanel.brightCyan),
    SEVEN_BRO((char)(256+6*49+31), Color.magenta),
    APPLE((char)(256+18*49+33), new Color(230, 72, 46)),
    BAT((char)(256+8*49+26), new Color(207, 198, 184)),
    SCORPION((char)(256+5*49+24), new Color(207, 198, 184)),
    SPIDER((char)(256+5*49+29), new Color(207, 198, 184)),
    SNAKER((char)(256+8*49+28), new Color(207,198,184)),

    // 武器
    NORMAL_GUN((char)(256+9*49+37), AsciiPanel.brightYellow),
    LASER_GUN((char)(256+9*49+38), AsciiPanel.brightYellow),
    SHOT_GUN((char)(256+9*49+39), AsciiPanel.brightYellow),
    SNIPER_GUN((char)(256+9*49+42), AsciiPanel.brightYellow),
    BASEBALL_BAT((char)(256+2*49+34), AsciiPanel.brightYellow),
    DETONATOR_THROWER((char)(256+21*49+32), AsciiPanel.brightYellow),
    BOMB_GLOVE((char)(256+0*49+41), AsciiPanel.brightYellow),
    TORCH((char)(256+3*49+42), AsciiPanel.brightYellow),

    // 道具
    BOMB_UPGRADE_RANGE((char)(256+13*49+32), new Color(60, 172 ,215)),
    HP_UPGRADE((char)(256+13*49+34), new Color(230, 72, 46)),
    ENERGY_UPGRADE((char)(256+13*49+33), new Color(135,206,250)),
    VISUAL_RANGE_UPGRADE((char)(256+13*49+33), new Color(135,206,250)),
    WEAPON_ATTACK_UPGRADE((char)(256+11*49+42), new Color(244, 180, 27)),
    ATTACK_UPGRADE((char)(256+11*49+42), new Color(255, 255, 255)),
    SPEED_UPGRADE((char)(256+13*49+32), AsciiPanel.grassGreen);

    private char glyph;
    private Color color;

    /**
     * @author: Xu YangXin
     * @return {char} 代表形象的字符
     * @description: 读取代表形象的字符
     */    
    public char glyph() {
        return this.glyph;
    }

    /**
     * @author: Xu YangXin
     * @return {Color} 颜色
     * @description: 读取颜色
     */    
    public Color color() {
        return this.color;
    }

    /**
     * @author: Xu YangXin
     * @return {boolean} 是否可以挖掘
     * @description: 判断是否可以挖掘
     */    
    public boolean isDiggable() {
        return  this != Tile.WALL    && this != Tile.BOUNDS   && this != Tile.TREE_1   &&
                this != Tile.TREE_2  && this != Tile.TREE_3   && this != Tile.TREE_4   &&
                this != Tile.TREE_5  && this != Tile.TREE_6   && this != Tile.TREE_7   &&
                this != Tile.HOUSE_1 && this != Tile.HOUSE_2  && this != Tile.HOUSE_3  &&
                this != Tile.HOUSE_4 && this != Tile.HOUSE_5  && this != Tile.HOUSE_6  &&
                this != Tile.HOUSE_7 && this != Tile.HOUSE_8  && this != Tile.HOUSE_9  &&
                this != Tile.HOUSE_10&& this != Tile.HOUSE_11 && this != Tile.HOUSE_12 &&
                this != Tile.HOUSE_13&& this != Tile.HOUSE_14 && this != Tile.HOUSE_15 &&
                this != Tile.HOUSE_16&& this != Tile.BOARD_1  && this != Tile.BOARD_2  &&
                this != Tile.BOARD_3 && this != Tile.BOARD_4  && this != Tile.BOARD_5  &&
                this != Tile.BOARD_6 && this != Tile.BOARD_7  && this != Tile.BOARD_8  &&
                this != Tile.BOARD_9 && this != Tile.BOARD_10;
    }

    /**
     * @author: Xu YangXin
     * @return {boolean} 是否是可行走的地面
     * @description: 判断是否是可行走的地面
     */    
    public boolean isGround() {
        return  this != Tile.WALL    && this != Tile.BOUNDS   && this != Tile.TREE_1   &&
                this != Tile.TREE_2  && this != Tile.TREE_3   && this != Tile.TREE_4   &&
                this != Tile.TREE_5  && this != Tile.TREE_6   && this != Tile.TREE_7   &&
                this != Tile.HOUSE_1 && this != Tile.HOUSE_2  && this != Tile.HOUSE_3  &&
                this != Tile.HOUSE_4 && this != Tile.HOUSE_5  && this != Tile.HOUSE_6  &&
                this != Tile.HOUSE_7 && this != Tile.HOUSE_8  && this != Tile.HOUSE_9  &&
                this != Tile.HOUSE_10&& this != Tile.HOUSE_11 && this != Tile.HOUSE_12 &&
                this != Tile.HOUSE_13&& this != Tile.HOUSE_14 && this != Tile.HOUSE_15 &&
                this != Tile.HOUSE_16&& this != Tile.BOARD_1  && this != Tile.BOARD_2  &&
                this != Tile.BOARD_3 && this != Tile.BOARD_4  && this != Tile.BOARD_5  &&
                this != Tile.BOARD_6 && this != Tile.BOARD_7  && this != Tile.BOARD_8  &&
                this != Tile.BOARD_9 && this != Tile.BOARD_10;
    }

    /**
     * @author: Xu YangXin
     * @return {boolean} 是否是地板
     * @description: 判断是否是地板
     */    
    public boolean isFloor(){
        return this == Tile.FLOOR;
    }

    /**
     * @author: Xu YangXin
     * @return {boolean} 是否是草丛
     * @description: 判断是否是某个草丛
     */    
    public boolean isGrass(){
        return  this == Tile.GRASS_1  || this == Tile.GRASS_2 || this == Tile.GRASS_3 ||
                this == Tile.GRASS_4  || this == Tile.GRASS_5 || this == Tile.GRASS_6 ||
                this == Tile.GRASS_7  || this == Tile.GRASS_8 || this == Tile.GRASS_9 ||
                this == Tile.GRASS_10 || this == Tile.GRASS_11;
    }

    /**
     * @author: Xu YangXin
     * @param{char} glyph 字符
     * @param{Color} color 颜色
     * @return {*}
     * @description: 构造器
     */    
    Tile(char glyph, Color color) {
        this.glyph = glyph;
        this.color = color;
    }

    /**
     * @author: Xu YangXin
     * @return {Tile} 地形
     * @description: 随机掉落药水
     */    
    public static Tile randomDropItem(){
        Tile dropItem = null;

        switch (new Random().nextInt(7)) {
            case 0: dropItem = Tile.BOMB_UPGRADE_RANGE;     break;
            case 1: dropItem = Tile.SPEED_UPGRADE;          break;
            case 2: dropItem = Tile.HP_UPGRADE;             break;
            case 3: dropItem = Tile.ENERGY_UPGRADE;         break;
            case 4: dropItem = Tile.VISUAL_RANGE_UPGRADE;   break;
            case 5: dropItem = Tile.ATTACK_UPGRADE;         break;
            case 6: dropItem = Tile.WEAPON_ATTACK_UPGRADE;   break;
        }

        return dropItem;
    }

    /**
     * @author: Xu YangXin
     * @return {Tile} 地形
     * @description: 随机生成树木
     */    
    public static Tile randomTree(){
        Tile TREE = null;

        switch (new Random().nextInt(7)) {
            case 0: TREE = Tile.TREE_1;     break;
            case 1: TREE = Tile.TREE_2;     break;
            case 2: TREE = Tile.TREE_3;     break;
            case 3: TREE = Tile.TREE_4;     break;
            case 4: TREE = Tile.TREE_5;     break;
            case 5: TREE = Tile.TREE_6;     break;
            case 6: TREE = Tile.TREE_7;     break;
        }

        return TREE;
    }

    /**
     * @author: Xu YangXin
     * @return {Tile} 地形
     * @description: 随机生成草丛
     */    
    public static Tile randomGrass(){
        Tile grass = null;

        switch (new Random().nextInt(11)) {
            case 0: grass = Tile.GRASS_1;      break;
            case 1: grass = Tile.GRASS_2;      break;
            case 2: grass = Tile.GRASS_3;      break;
            case 3: grass = Tile.GRASS_4;      break;
            case 4: grass = Tile.GRASS_5;      break;
            case 5: grass = Tile.GRASS_6;      break;
            case 6: grass = Tile.GRASS_7;      break;
            case 7: grass = Tile.GRASS_8;      break;
            case 8: grass = Tile.GRASS_9;      break;
            case 9: grass = Tile.GRASS_10;     break;
            case 10:grass = Tile.GRASS_11;     break;
        }

        return grass;
    }

    /**
     * @author: Xu YangXin
     * @return {Tile} 地形
     * @description: 随机生成房屋
     */    
    public static Tile randomHouse(){
        Tile house = null;

        switch (new Random().nextInt(16)) {
            case 0: house = Tile.HOUSE_1;       break;
            case 1: house = Tile.HOUSE_2;       break;
            case 2: house = Tile.HOUSE_3;       break;
            case 3: house = Tile.HOUSE_4;       break;
            case 4: house = Tile.HOUSE_5;       break;
            case 5: house = Tile.HOUSE_6;       break;
            case 6: house = Tile.HOUSE_7;       break;
            case 7: house = Tile.HOUSE_8;       break;
            case 8: house = Tile.HOUSE_9;       break;
            case 9: house = Tile.HOUSE_10;      break;
            case 10:house = Tile.HOUSE_11;      break;
            case 11:house = Tile.HOUSE_12;      break;
            case 12:house = Tile.HOUSE_13;      break;
            case 13:house = Tile.HOUSE_14;      break;
            case 14:house = Tile.HOUSE_15;      break;
            case 15:house = Tile.HOUSE_16;      break;
        }

        return house;
    }

    /**
     * @author: Xu YangXin
     * @return {Tile} 地形
     * @description: 随机生成路牌
     */    
    public static Tile randomBoard(){
        Tile board = null;

        switch (new Random().nextInt(10)) {
            case 0: board = Tile.BOARD_1;       break;
            case 1: board = Tile.BOARD_2;       break;
            case 2: board = Tile.BOARD_3;       break;
            case 3: board = Tile.BOARD_4;       break;
            case 4: board = Tile.BOARD_5;       break;
            case 5: board = Tile.BOARD_6;       break;
            case 6: board = Tile.BOARD_7;       break;
            case 7: board = Tile.BOARD_8;       break;
            case 8: board = Tile.BOARD_9;       break;
            case 9: board = Tile.BOARD_10;      break;
        }

        return board;
    }
}
