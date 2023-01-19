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
 * @LastEditTime: 2023-01-17 16:53:06
 * @LastEditors: Xu YangXin
 * @Description: 此类为炸弹类。
 * @FilePath: \jfp-poker-svg\src\main\java\cn\edu\nju\cs\game\world\weapon\bomb\Bomb.java
 * 此项目为南京大学《Java高级程序设计》项目
 */

package cn.edu.nju.cs.game.world.weapon.bomb;

import java.awt.Color;
import java.io.Serializable;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import cn.edu.nju.cs.game.asciiPanel.*;
import cn.edu.nju.cs.game.world.item.World;
import cn.edu.nju.cs.game.world.item.Tile;
import cn.edu.nju.cs.game.world.creature.Creature;
import cn.edu.nju.cs.game.world.creature.Direction;

public class Bomb implements Serializable{
    private World world;
    private Creature master;
    private int x;
    private int y;
    private int range;
    private char glyph;
    private char glyph_bomb_x;
    private char glyph_bomb_y;
    private Color color;
    private int attackValue;
    private boolean active;
    private boolean alive;

    transient private Timer moveTimer;
    transient private TimerTask moveLeft;
    transient private TimerTask moveRight;
    transient private TimerTask moveUp;
    transient private TimerTask moveDown;

    private static ReentrantReadWriteLock lockX = new ReentrantReadWriteLock();
    private static ReentrantReadWriteLock.ReadLock readLockX = lockX.readLock();
    private static ReentrantReadWriteLock.WriteLock writeLockX = lockX.writeLock();
    private static ReentrantReadWriteLock lockY = new ReentrantReadWriteLock();
    private static ReentrantReadWriteLock.ReadLock readLockY = lockY.readLock();
    private static ReentrantReadWriteLock.WriteLock writeLockY = lockY.writeLock();

    public static final int MOVE_SPEED_INTERVAL = 300;

    /**
     * @author: Xu YangXin
     * @param {World} world 炸弹所处世界
     * @param {Creature} master 炸弹的主人
     * @param {int} x 炸弹的x坐标
     * @param {int} y 炸弹的y坐标
     * @param {char} glyph 炸弹的字符形象
     * @param {Color} color 炸弹的颜色
     * @param {int} attack 炸弹的攻击力
     * @return {*}
     * @description: 构造器
     */
    public Bomb(World world, Creature master, int x, int y, char glyph, Color color, int attack) {
        this.world = world;
        this.master = master;
        this.x = x;
        this.y = y;
        this.glyph = glyph;
        this.glyph_bomb_x = Tile.BOMB_X.glyph();
        this.glyph_bomb_y = Tile.BOMB_Y.glyph();
        this.color = color;
        this.attackValue = attack;
        this.range = 1;
        this.active = false;
        this.alive = true;
        moveLeft =
        new TimerTask() {
            public void run(){
                if(master().world().worldIsPaused())
                    return;
                if(!moveLeft())
                    stopMove();
            }
        };
        moveRight =
        new TimerTask() {
            public void run(){
                if(master().world().worldIsPaused())
                    return;
                if(!moveRight())
                    stopMove();
            }
        };
        moveUp =
        new TimerTask() {
            public void run(){
                if(master().world().worldIsPaused())
                    return;
                if(!moveUp())
                    stopMove();
            }
        };
        moveDown =
        new TimerTask() {
            public void run(){
                if(master().world().worldIsPaused())
                    return;
                if(!moveDown())
                    stopMove();
            }
        };
    }

    /**
     * @author: Xu YangXin
     * @param {Bomb} bomb 样板炸弹
     * @param {int} x 炸弹的x坐标
     * @param {int} y 炸弹的y坐标
     * @return {*}
     * @description: 复制构造器
     */    
    public Bomb(Bomb other, int x, int y){
        this.world = other.world();
        this.master = other.master();
        this.x = x;
        this.y = y;
        this.glyph = other.glyph();
        this.glyph_bomb_x = other.glyphBombX();
        this.glyph_bomb_y = other.glyphBombY();
        this.color = other.color();
        this.attackValue = other.attackValue();
        this.range = other.range();
        this.active = other.isActive();
        this.alive = other.isAlive();
        this.moveTimer = null;

        moveLeft =
        new TimerTask() {
            public void run(){
                if(master().world().worldIsPaused())
                    return;
                if(!moveLeft())
                    stopMove();
            }
        };
        moveRight =
        new TimerTask() {
            public void run(){
                if(master().world().worldIsPaused())
                    return;
                if(!moveRight())
                    stopMove();
            }
        };
        moveUp =
        new TimerTask() {
            public void run(){
                if(master().world().worldIsPaused())
                    return;
                if(!moveUp())
                    stopMove();
            }
        };
        moveDown =
        new TimerTask() {
            public void run(){
                if(master().world().worldIsPaused())
                    return;
                if(!moveDown())
                    stopMove();
            }
        };
    }

    /**
     * @author: Xu YangXin
     * @return {World} 炸弹所处世界
     * @description: 读取炸弹所处的世界
     */    
    public World world(){
        return world;
    }
    
    /**
     * @author: Xu YangXin
     * @param {World} world 目标世界
     * @return {*}
     * @description: 设置炸弹所处世界
     */    
    public void setWorld(World world){
        this.world = world;
    }

    /**
     * @author: Xu YangXin
     * @return {Creature} 生物
     * @description: 读取设置炸弹的主人
     */    
    public Creature master(){
        return this.master;
    }
    
    /**
     * @author: Xu YangXin
     * @param {Creature} creature 目标生物
     * @return {*}
     * @description: 设置炸弹的主人
     */    
    public void setMaster(Creature creature){
        this.master = creature;
    }
    
    /**
     * @author: Xu YangXin
     * @return {int} 炸弹的x坐标
     * @description: 获取炸弹的x坐标
     */    
    public int x() {
        int tempX = 0;
        readLockX.lock();
        try {
            tempX = this.x;
        } finally {
            readLockX.unlock();
        }
        return tempX;
    }
    
    /**
     * @author: Xu YangXin
     * @return {int} 炸弹的y坐标
     * @description: 获取炸弹的y坐标
     */    
    public int y() {
        int tempY = 0;
        readLockY.lock();
        try {
            tempY = y;
        } finally {
            readLockY.unlock();
        }
        return tempY;
    }

    /**
     * @author: Xu YangXin
     * @return {boolean} 炸弹左移是否成功
     * @description: 炸弹左移一步
     */    
    public boolean moveLeft(){
        int tempX = x(), tempY = y();
        if(world.inBound(tempX-1, tempY) && world.tile(tempX-1, tempY).isGround()){
            writeLockX.lock();
            try {
                this.x--;
                this.refreshWorld(tempX, tempY);
            } finally {
                writeLockX.unlock();
            }
            return true;
        }

        return false;
    }

    /**
     * @author: Xu YangXin
     * @return {boolean} 炸弹右移是否成功
     * @description: 炸弹右移一步
     */    
    public boolean moveRight(){
        int tempX = x(), tempY = y();
        if(world.inBound(tempX+1, tempY) && world.tile(tempX+1, tempY).isGround()){
            writeLockX.lock();
            try {
                this.x++;
                this.refreshWorld(tempX, tempY);
            } finally {
                writeLockX.unlock();
            }
            return true;
        }

        return false;
    }

    /**
     * @author: Xu YangXin
     * @return {boolean} 炸弹上移是否成功
     * @description: 炸弹上移一步
     */    
    public boolean moveUp(){
        int tempX = x(), tempY = y();
        if(world.inBound(tempX, tempY-1) && world.tile(tempX, tempY-1).isGround()){
            writeLockY.lock();
            try {
                this.y--;
                this.refreshWorld(tempX, tempY);
            } finally {
                writeLockY.unlock();
            }
            return true;
        }

        return false;
    }

    /**
     * @author: Xu YangXin
     * @return {boolean} 炸弹下移是否成功
     * @description: 炸弹下移是否成功
     */    
    public boolean moveDown(){
        int tempX = x(), tempY = y();
        if(world.inBound(tempX, tempY+1) && world.tile(tempX, tempY+1).isGround()){
            writeLockY.lock();
            try {
                this.y++;
                this.refreshWorld(tempX, tempY);
            } finally {
                writeLockY.unlock();
            }
            return true;
        }

        return false;
    }

    /**
     * @author: Xu YangXin
     * @param {int} oldX 炸弹的旧x坐标
     * @param {int} oldY 炸弹的旧y坐标
     * @return {*}
     * @description: 刷新世界，设置炸弹的新位置并删除旧位置的图像
     */    
    private void refreshWorld(int oldX, int oldY){
        int newX = this.x(), newY = this.y();
        this.world.setThing(Tile.FLOOR, oldX, oldY);
        this.world.setThing(Tile.BOMB, newX, newY);
        return;
    }

    /**
     * @author: Xu YangXin
     * @return {int} 炸弹的爆炸范围
     * @description: 获取炸弹的爆炸范围
     */    
    public int range(){
        return range;
    }
    
    /**
     * @author: Xu YangXin
     * @return {*}
     * @description: 扩展炸弹的爆炸范围+1
     */    
    public void expandRange(){
        range++;
    }
    
    /**
     * @author: Xu YangXin
     * @param {Creature} creature 目标生物
     * @return {boolean}
     * @description: 判断生物是否在炸弹的爆炸范围内
     */    
    public boolean isInRange(Creature creature){
        int dis_x = Math.abs(creature.x() - this.x);
        int dis_y = Math.abs(creature.y() - this.y);
        if(dis_x == 0 && dis_y <= range)
            return true;
        if(dis_x <= range && dis_y == 0)
            return true;
        return false;
    }

    /**
     * @author: Xu YangXin
     * @return {char} 炸弹的字符形象
     * @description: 获取炸弹的字符形象
     */    
    public char glyph() {
        return this.glyph;
    }

    /**
     * @author: Xu YangXin
     * @return {char} 炸弹横向爆炸冲击波的字符形象
     * @description: 获取炸弹横向爆炸冲击波的字符形象
     */    
    public char glyphBombX(){
        return glyph_bomb_x;
    }
    
    /**
     * @author: Xu YangXin
     * @return {char} 炸弹纵向爆炸冲击波的字符形象
     * @description: 获取炸弹纵向爆炸冲击波的字符形象
     */    
    public char glyphBombY(){
        return glyph_bomb_y;
    }

    /**
     * @author: Xu YangXin
     * @return {Color} 炸弹的颜色
     * @description: 获取炸弹的颜色
     */    
    public Color color() {
        return this.color;
    }

    /**
     * @author: Xu YangXin
     * @return {int} 炸弹的攻击力
     * @description: 获取炸弹的攻击力
     */    
    public int attackValue() {
        return this.attackValue;
    }

    /**
     * @author: Xu YangXin
     * @return {boolean} 炸弹是否激活
     * @description: 查看炸弹是否被激活
     */    
    public boolean isActive(){
        return active;
    }
    
    /**
     * @author: Xu YangXin
     * @return {*}
     * @description: 激活炸弹
     */    
    public void activate(){
        this.active = true;
        this.color = AsciiPanel.red;
    }

    /**
     * @author: Xu YangXin
     * @return {boolean} 炸弹是否还活着
     * @description: 查看炸弹是否活着(存在)
     */    
    public boolean isAlive(){
        return alive;
    }
    
    /**
     * @author: Xu YangXin
     * @return {*}
     * @description: 让炸弹死亡
     */    
    public void dead(){
        this.alive = false;
        this.active = false;
    }

    /**
     * @author: Xu YangXin
     * @param {int} wx x坐标
     * @param {int} wy y坐标
     * @return {Tile} 地形
     * @description: 获取地图上某坐标的地形，若不存在则返回null
     */    
    public Tile tile(int wx, int wy) {
        return world.tile(wx, wy);
    }

    /**
     * @author: Xu YangXin
     * @param {Creature} other 目标生物
     * @return {*}
     * @description: 炸弹攻击目标生物
     */    
    public void attack(Creature other){
        if(!this.isInRange(other))
            return;

        other.modifyHP(-attackValue);
        this.master.chargeEnergy();
        return;
    }

    /**
     * @author: Xu YangXin
     * @param {Direction} direction 移动方向
     * @return {boolean} 开始移动是否成功
     * @description: 让炸弹向某个方向开始移动
     */    
    public boolean startMove(Direction direction){
        moveTimer = new Timer();
        switch (direction) {
            case Left:
                moveTimer.schedule(moveLeft, 0, Bomb.MOVE_SPEED_INTERVAL);
                break;
            case Right:
                moveTimer.schedule(moveRight, 0, Bomb.MOVE_SPEED_INTERVAL);
                break;
            case Up:
                moveTimer.schedule(moveUp, 0, Bomb.MOVE_SPEED_INTERVAL);
                break;
            case Down:
                moveTimer.schedule(moveDown, 0, Bomb.MOVE_SPEED_INTERVAL);
                break;
            default:
                break;
        }
        return true;
    }
    
    /**
     * @author: Xu YangXin
     * @return {*}
     * @description: 让炸弹停止移动
     */    
    public void stopMove(){
        try {
            moveTimer.cancel();
            moveTimer = null;
        } catch (NullPointerException e) {
            // e.printStackTrace();
        }
    }
}
