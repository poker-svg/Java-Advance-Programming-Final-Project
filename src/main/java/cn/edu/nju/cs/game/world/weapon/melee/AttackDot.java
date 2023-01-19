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
 * @LastEditTime: 2023-01-17 17:58:59
 * @LastEditors: Xu YangXin
 * @Description: 此类为攻击点类
 * @FilePath: \jfp-poker-svg\src\main\java\cn\edu\nju\cs\game\world\weapon\melee\AttackDot.java
 * 此项目为南京大学《Java高级程序设计》项目
 */

package cn.edu.nju.cs.game.world.weapon.melee;

import cn.edu.nju.cs.game.world.item.Tile;

import java.io.Serializable;

import cn.edu.nju.cs.game.world.creature.Creature;

public class AttackDot implements Serializable {
    private int x; // 攻击点坐标x
    private int y; // 攻击点坐标y
    private int attackValue; // 攻击点攻击力
    private Tile image; // 攻击点图案
    private int remainTime; // 存在的时间长度
    private Creature master; // 攻击点的主人

    /**
     * @author: Xu YangXin
     * @return {int} 攻击点的x坐标
     * @description: 获取攻击点的x坐标
     */
    public int x() {
        return this.x;
    }
    
    /**
     * @author: Xu YangXin
     * @param {int} x 攻击点的x坐标
     * @return {*}
     * @description: 设置攻击点的x坐标
     */    
    public void setX(int x) {
        this.x = x;
    }
    
    /**
     * @author: Xu YangXin
     * @return {int} 攻击点的y坐标
     * @description: 获取攻击点的y坐标
     */
    public int y() {
        return this.y;
    }
    
    /**
     * @author: Xu YangXin
     * @param {int} y 攻击点的y坐标
     * @return {*}
     * @description: 设置攻击点的y坐标
     */    
    public void setY(int y) {
        this.y = y;
    }

    /**
     * @author: Xu YangXin
     * @return {int} 攻击点的攻击力
     * @description: 获取攻击点的攻击力
     */    
    public int attackValue() {
        return this.attackValue;
    }
    
    /**
     * @author: Xu YangXin
     * @return {*}
     * @description: 攻击点的攻击力提升20%
     */
    public void attackUpgrade(){
        this.attackValue = (int)(this.attackValue*1.2);
    }

    /**
     * @author: Xu YangXin
     * @return {Tile} 攻击点的图像
     * @description: 获取攻击点的图像
     */    
    public Tile image() {
        return this.image;
    }

    /**
     * @author: Xu YangXin
     * @return {int} 攻击点残存时间
     * @description: 获取攻击点的残存时间
     */    
    public int remainTime(){
        return this.remainTime;
    }

    /**
     * @author: Xu YangXin
     * @return {Creature} 攻击点的主人
     * @description: 获取攻击点的主人
     */    
    public Creature master(){
        return this.master;
    }

    // 通过此构造函数创造的攻击点都是样点，用于以后近战武器攻击时产生的攻击点的复制
    /**
     * @author: Xu YangXin
     * @param {int} x 攻击点的x坐标
     * @param {int} y 攻击点的y坐标
     * @param {int} attackValue 攻击点的攻击力
     * @param {Tile} image 攻击点的图像
     * @param {int} remainTime 攻击点的残存时间
     * @param {Creature} master 攻击点的主人
     * @return {*}
     * @description: 攻击点的构造器
     */
    public AttackDot(int x, int y, int attackValue, Tile image, int remainTime, Creature master) {
        this.x = x;
        this.y = y;
        this.attackValue = attackValue;
        this.image = image;
        this.remainTime = remainTime;
        this.master = master;
    }

    /**
     * @author: Xu YangXin
     * @param {int} x 攻击点的x坐标
     * @param {int} y 攻击点的y坐标
     * @param {AttackDot} attackDot 攻击点的目标模板
     * @return {*}
     * @description: 攻击点的复制构造器
     */    
    public AttackDot(int x, int y, AttackDot attackDot) {
        this.x = x;
        this.y = y;
        this.attackValue = attackDot.attackValue();
        this.image = attackDot.image();
        this.remainTime = attackDot.remainTime();
        this.master = attackDot.master();
    }

    public boolean attack(Creature creature){
        if(this.x != creature.x() || this.y != creature.y())
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
