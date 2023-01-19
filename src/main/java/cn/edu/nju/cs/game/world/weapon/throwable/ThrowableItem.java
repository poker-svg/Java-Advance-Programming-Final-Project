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
 * @LastEditTime: 2023-01-17 19:55:52
 * @LastEditors: Xu YangXin
 * @Description: 此类为抛掷物品类，为抛掷类武器的子弹
 * @FilePath: \jfp-poker-svg\src\main\java\cn\edu\nju\cs\game\world\weapon\throwable\ThrowableItem.java
 * 此项目为南京大学《Java高级程序设计》项目
 */

package cn.edu.nju.cs.game.world.weapon.throwable;

import cn.edu.nju.cs.game.world.item.Tile;

import java.io.Serializable;

import cn.edu.nju.cs.game.world.creature.Creature;

public class ThrowableItem implements Serializable {
    private int x; // 坐标x
    private int y; // 坐标y
    private int range; // 攻击有效范围
    private int attackValue;
    private Tile image; // 图案
    private int activateInterval;
    private boolean active; //激活与否
    private boolean alive;  //生死与否
    private int remainTime; //爆炸后存在时间
    private Creature master; // 投掷物的主人

    /**
     * @author: Xu YangXin
     * @return {int} 抛掷物品的x坐标
     * @description: 获取抛掷物品的x坐标
     */    
    public int x() {
        return this.x;
    }
    
    /**
     * @author: Xu YangXin
     * @param {int} x 抛掷物品的x坐标
     * @return {*}
     * @description: 设置抛掷物品的x坐标
     */    
    public void setX(int x) {
        this.x = x;
    }
    
    /**
     * @author: Xu YangXin
     * @return {int} 抛掷物品的y坐标
     * @description: 获取抛掷物品的y坐标
     */    
    public int y() {
        return this.y;
    }
    
    /**
     * @author: Xu YangXin
     * @param {int} y 抛掷物品的y坐标
     * @return {*}
     * @description: 设置抛掷物品的y坐标
     */    
    public void setY(int y) {
        this.y = y;
    }

    /**
     * @author: Xu YangXin
     * @return {int} 抛掷物品的攻击范围
     * @description: 获取抛掷物品的攻击范围
     */    
    public int range() {
        return this.range;
    }
    
    /**
     * @author: Xu YangXin
     * @param {int} x x坐标
     * @param {int} y y坐标
     * @return {boolean}
     * @description: 判断某个坐标是否在抛掷物品的攻击范围内
     */    
    public boolean inRange(int x, int y){
        return  (Math.abs(this.x - x) <= this.range) &&
                (Math.abs(this.y - y) <= this.range);
    }
    
    /**
     * @author: Xu YangXin
     * @return {*}
     * @description: 扩大抛掷物品的攻击范围
     */    
    public void enlargeRange(){
        this.range = this.range*2;
        return;
    }
    
    /**
     * @author: Xu YangXin
     * @return {*}
     * @description: 缩小抛掷物品的攻击范围
     */    
    public void shrinkRange(){
        this.range = this.range/2;
        return;
    }

    /**
     * @author: Xu YangXin
     * @return {int} 抛掷物品的攻击力
     * @description: 获取抛掷物品的攻击力
     */    
    public int attackValue() {
        return this.attackValue;
    }
    
    /**
     * @author: Xu YangXin
     * @return {*}
     * @description: 提升抛掷物品的攻击力20%
     */    
    public void attackUpgrade(){
        this.attackValue = (int)(this.attackValue*1.2);
    }

    /**
     * @author: Xu YangXin
     * @return {Tile} 抛掷物品的图像
     * @description: 获取抛掷物品的图像
     */    
    public Tile image() {
        return this.image;
    }

    /**
     * @author: Xu YangXin
     * @return {int} 抛掷物品的激活时间
     * @description: 获取抛掷物品的激活时间
     */    
    public int activateInterval(){
        return this.activateInterval;
    }

    /**
     * @author: Xu YangXin
     * @return {boolean} 抛掷物品是否激活
     * @description: 查看抛掷物品是否激活
     */    
    public boolean isActive(){
        return this.active;
    }
    
    /**
     * @author: Xu YangXin
     * @return {*}
     * @description: 激活抛掷物品
     */    
    public void activate(){
        this.active = true;
    }

    /**
     * @author: Xu YangXin
     * @return {boolean} 抛掷物品是否生存
     * @description: 查看抛掷物品是否生存
     */    
    public boolean isAlive(){
        return this.alive;
    }
    
    /**
     * @author: Xu YangXin
     * @return {*}
     * @description: 设置抛掷物品死亡消失
     */    
    public void dead(){
        this.alive = false;
    }

    /**
     * @author: Xu YangXin
     * @return {int}
     * @description: 抛掷物品的残存时间
     */    
    public int remainTime(){
        return this.remainTime;
    }

    /**
     * @author: Xu YangXin
     * @return {Creature} 抛掷物品的主人
     * @description: 获取抛掷物品的主人
     */    
    public Creature master(){
        return this.master;
    }

    // 通过此构造函数创造的扔物都是样弹，用于以后武器的发射的扔物复制
    /**
     * @author: Xu YangXin
     * @param {int} x 抛掷物的x坐标
     * @param {int} y 抛掷物的y坐标
     * @param {int} range 抛掷物的攻击范围
     * @param {int} attackValue 抛掷物的攻击力
     * @param {Tile} image 抛掷物的图像
     * @param {int} activateInterval 抛掷物的激活间隔时间
     * @param {int} remainTime 抛掷物的残存时间
     * @param {Creature} master 抛掷物的主人
     * @return {*}
     * @description: 构造器
     */
    public ThrowableItem(int x, int y, int range, int attackValue, Tile image,
                        int activateInterval, int remainTime, Creature master) {
        this.x = x;
        this.y = y;
        this.range = range;
        this.attackValue = attackValue;
        this.image = image;
        this.activateInterval = activateInterval;
        this.remainTime = remainTime;
        this.master = master;
        this.alive = true;
        this.active = false;
    }

    /**
     * @author: Xu YangXin
     * @param {int} x 抛掷物的x坐标
     * @param {int} y 抛掷物的y坐标
     * @param {ThrowableItem} throwableItem 抛掷物的模板
     * @return {*}
     * @description: 抛掷物的复制构造器
     */    
    public ThrowableItem(int x, int y, ThrowableItem throwableItem) {
        this.x = x;
        this.y = y;
        this.range = throwableItem.range();
        this.attackValue = throwableItem.attackValue();
        this.image = throwableItem.image();
        this.activateInterval = throwableItem.activateInterval();
        this.remainTime = throwableItem.remainTime();
        this.master = throwableItem.master();
        this.alive = throwableItem.isAlive();
        this.active = throwableItem.isActive();
    }

    /**
     * @author: Xu YangXin
     * @param {Creature} creature 目标生物
     * @return {boolean} 抛掷物攻击生物是否成功
     * @description: 抛掷物攻击目标生物
     */    
    public boolean attack(Creature creature){
        if(!this.inRange(creature.x(), creature.y()))
            return false;

        if(this.master == creature)
            return false;

        int damage = Math.max(0, this.attackValue - creature.defenseValue());
        damage = (int) ((Math.random()+1) * damage) + 1; // 1-2倍暴击率

        creature.modifyHP(-damage);
        this.master.chargeEnergy();
        creature.notify("The '%s' attacks you for %d damage.", this.image().glyph(), damage);
        return true;
    }
}