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
 * @LastEditTime: 2023-01-17 17:05:33
 * @LastEditors: Xu YangXin
 * @Description: 此类为子弹类
 * @FilePath: \jfp-poker-svg\src\main\java\cn\edu\nju\cs\game\world\weapon\gun\Bullet.java
 * 此项目为南京大学《Java高级程序设计》项目
 */

package cn.edu.nju.cs.game.world.weapon.gun;

import java.io.Serializable;
import java.util.Timer;
import java.util.TimerTask;

import cn.edu.nju.cs.game.world.item.Tile;
import cn.edu.nju.cs.game.world.creature.Creature;
import cn.edu.nju.cs.game.world.creature.Direction;

public class Bullet implements Serializable{
    private int x; // 子弹坐标x
    private int y; // 子弹坐标y
    protected int range; // 子弹有效范围
    private Direction direction; // 子弹方向
    protected int attackValue; // 伤害值
    protected Tile image; // 子弹图案
    private int speedInterval; // 代表子弹速度的时间，单位毫秒
    transient protected Timer movTimer; // 子弹移动计时器
    private Creature master;  // 子弹的主人

    
    // 通过此构造函数创造的子弹都是样弹，用于以后枪械的发射子弹的复制
    /**
     * @author: Xu YangXin
     * @param {int} x 子弹的x坐标
     * @param {int} y 子弹的y坐标
     * @param {Direction} direction 子弹的飞行方向
     * @param {int} range 子弹的飞行距离
     * @param {int} attackValue 子弹的攻击力
     * @param {Tile} image 子弹的图像
     * @param {int} speedInterval 子弹的飞行间隔时间
     * @param {Creature} master 子弹的主人
     * @return {*}
     * @description: 构造器
     */
    public Bullet(int x, int y, Direction direction, int range, int attackValue,
                    Tile image, int speedInterval, Creature master) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.range = range;
        this.attackValue = attackValue;
        this.image = image;
        this.speedInterval = speedInterval;
        this.master = master;
        movTimer = null;
    }

    /**
     * @author: Xu YangXin
     * @param {int} x 子弹的x坐标
     * @param {int} y 子弹的y坐标
     * @param {Direction} direction 子弹的飞行方向
     * @param {Bullet} bullet 子弹
     * @return {*}
     * @description: 复制构造器
     */
    public Bullet(int x, int y, Direction direction, Bullet bullet) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.range = bullet.range();
        this.attackValue = bullet.attackValue();
        this.image = bullet.image();
        this.speedInterval = bullet.speedInterval();
        this.master = bullet.master();
        startMove();
    }


    /**
     * @author: Xu YangXin
     * @return {int} 子弹的x坐标
     * @description: 获取子弹的x坐标
     */    
    public int x() {
        return this.x;
    }
    
    /**
     * @author: Xu YangXin
     * @param {int} x 子弹的x坐标
     * @return {*}
     * @description: 设置子弹的x坐标
     */    
    public void setX(int x) {
        this.x = x;
    }

    /**
     * @author: Xu YangXin
     * @return {int} 子弹的y坐标
     * @description: 获取子弹的y坐标
     */    
    public int y() {
        return this.y;
    }
    
    /**
     * @author: Xu YangXin
     * @param {int} y 子弹的y坐标
     * @return {*}
     * @description: 设置子弹的y坐标
     */    
    public void setY(int y) {
        this.y = y;
    }

    /**
     * @author: Xu YangXin
     * @return {int} 子弹的飞行距离
     * @description: 获取子弹的飞行距离
     */    
    public int range() {
        return this.range;
    }

    /**
     * @author: Xu YangXin
     * @return {Direction} 子弹的飞行方向
     * @description: 获取子弹的飞行方向
     */    
    public Direction direction() {
        return this.direction;
    }

    /**
     * @author: Xu YangXin
     * @return {int} 子弹的攻击力
     * @description: 获取子弹的攻击力
     */    
    public int attackValue() {
        return this.attackValue;
    }
    
    /**
     * @author: Xu YangXin
     * @return {*}
     * @description: 将子弹的攻击力翻倍
     */    
    public void doubleAttackValue(){ // 双倍伤害
        this.attackValue = this.attackValue*2;
    }
    
    /**
     * @author: Xu YangXin
     * @return {*}
     * @description: 子弹的攻击力提升20%
     */    
    public void attackUpgrade(){
        this.attackValue = (int)(this.attackValue*1.2);
    }

    /**
     * @author: Xu YangXin
     * @return {Tile} 子弹的图像
     * @description: 获取子弹的图像
     */    
    public Tile image() {
        return this.image;
    }

    /**
     * @author: Xu YangXin
     * @return {int} 子弹的飞行间隔时间
     * @description: 获取子弹的飞行间隔时间
     */    
    public int speedInterval() {
        return speedInterval;
    }

    /**
     * @author: Xu YangXin
     * @return {Creature} 子弹的主人
     * @description: 获取子弹的主人
     */    
    public Creature master(){
        return this.master;
    }

    /**
     * @author: Xu YangXin
     * @return {*}
     * @description: 子弹开始飞行
     */    
    public void startMove(){
        movTimer = new Timer();
        movTimer.schedule(
                new TimerTask() {
                    public void run() {
                        if(master().world().worldIsPaused())
                            return;
                        if (range < 1)
                            return;
                        range--;

                        int temp_x = x();
                        int temp_y = y();
                        switch (direction()) {
                            case Left:      temp_x--; break;
                            case Right:     temp_x++; break;
                            case Up:        temp_y--; break;
                            case Down:      temp_y++; break;
                            case LeftUp:    temp_x--; temp_y--; break;
                            case LeftDown:  temp_x--; temp_y++; break;
                            case RightUp:   temp_x++; temp_y--; break;
                            case RightDown: temp_x++; temp_y++; break;
                        }

                        setX(temp_x);
                        setY(temp_y);
                    }
                }, this.speedInterval, this.speedInterval);
    }

    /**
     * @author: Xu YangXin
     * @param {Creature} creature 目标生物
     * @return {*}
     * @description: 子弹攻击目标生物
     */    
    public boolean attack(Creature creature){
        if(this.x != creature.x() || this.y != creature.y())
            return false;

        if(this.master == creature) // 友伤取消
            return false;

        int damage = Math.max(0, this.attackValue - creature.defenseValue());
        damage = (int) ((Math.random()+1) * damage) + 1; // 1-2倍暴击率

        creature.modifyHP(-damage);
        this.master.chargeEnergy();
        creature.notify("The '%s' attacks you for %d damage.", this.image().glyph(), damage);
        return true;
    }
}
