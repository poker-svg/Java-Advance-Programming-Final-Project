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
 * @LastEditTime: 2023-01-17 20:07:43
 * @LastEditors: Xu YangXin
 * @Description: 此类为所有抛掷类武器的抽象基类
 * @FilePath: \jfp-poker-svg\src\main\java\cn\edu\nju\cs\game\world\weapon\throwable\ThrowableWeapon.java
 * 此项目为南京大学《Java高级程序设计》项目
 */

package cn.edu.nju.cs.game.world.weapon.throwable;

import java.util.Timer;
import java.util.TimerTask;

import cn.edu.nju.cs.game.world.creature.Creature;
import cn.edu.nju.cs.game.world.item.Tile;
import cn.edu.nju.cs.game.world.weapon.Weapon;
import cn.edu.nju.cs.game.world.weapon.WeaponFactory;

public abstract class ThrowableWeapon extends Weapon{
    protected int maxItemsAmount; // 武器容量
    protected int remainingItems; // 剩余扔物个数
    protected int addItemInterval; // 添加扔物的间隔时间，单位毫秒
    transient protected Timer addItemTimer; //添加子弹的计时器
    protected ThrowableItem throwableItemSample; // 扔物样板，用于复制
    protected int dx; // 相对于当前人物的x距离
    protected int dy; // 相对于当前人物的y距离

    /**
     * @author: Xu YangXin
     * @param {WeaponFactory} factory 生产此抛掷类武器的武器工厂
     * @param {String} name 抛掷类武器的名称
     * @param {Tile} image 抛掷类武器的图像
     * @param {int} attackValue 抛掷类武器的攻击力
     * @param {int} range 抛掷类武器的攻击范围
     * @param {int} coldingInterval 抛掷类武器的冷却间隔时间
     * @param {int} maxEnergy 抛掷类武器的最大能量值
     * @param {int} addItemInterval 抛掷类武器的添加抛掷物间隔时间
     * @param {int} maxItemsAmount 抛掷类武器的弹匣容量
     * @param {ThrowableItem} throwableItemSample 抛掷类武器的抛掷物
     * @return {*}
     * @description: 抛掷类武器的构造器
     */
    public ThrowableWeapon(WeaponFactory factory, String name, Tile image,
                        int attackValue, int range, int coldingInterval, int maxEnergy,
                        int addItemInterval, int maxItemsAmount, ThrowableItem throwableItemSample){
        super(factory, name, image, attackValue, range, coldingInterval, maxEnergy);
        this.remainingItems = 0;
        this.addItemInterval = addItemInterval;
        this.maxItemsAmount = maxItemsAmount;
        this.throwableItemSample = throwableItemSample;
        this.dx = 0;
        this.dy = 0;
        startAddItemTimer();
    }

    /**
     * @author: Xu YangXin
     * @return {int} 抛掷类武器的弹匣容量
     * @description: 获取抛掷类武器的诞下容量
     */    
    public int maxItemsAmount(){
        return this.maxItemsAmount;
    }

    /**
     * @author: Xu YangXin
     * @return {int} 抛掷类武器的剩余子弹数
     * @description: 获取抛掷类武器的剩余子弹数量
     */    
    public int remainingItems(){
        return this.remainingItems;
    }
    
    /**
     * @author: Xu YangXin
     * @return {*}
     * @description: 添加抛掷物
     */    
    public void addItem(){
        if(remainingItems < maxItemsAmount)
            this.remainingItems++;
        return;
    }

    /**
     * @author: Xu YangXin
     * @return {int} 抛掷类武器的添加抛掷物间隔时间
     * @description: 获取抛掷类武器的添加抛掷物间隔时间
     */    
    public int addItemInterval(){
        return this.addItemInterval;
    }

    /**
     * @author: Xu YangXin
     * @return {int} 相对于主人的x距离
     * @description: 获取相对于主人的x距离
     */    
    public int dx(){
        return this.dx;
    }
    
    /**
     * @author: Xu YangXin
     * @return {boolean} 瞄准镜向左移动是否成功
     * @description: 瞄准镜向左移动
     */    
    public boolean dxMoveRight(){
        Creature master = this.factory.master();
        if(Math.abs(this.dx+1) > this.range ||
        !master.world().inBound(this.dx+1+master.x(), this.dy+master.y()))
            return false;
        this.dx++;
        return true;
    }
    
    /**
     * @author: Xu YangXin
     * @return {boolean} 瞄准镜向右移动是否成功
     * @description: 瞄准镜向右移动
     */    
    public boolean dxMoveLeft(){
        Creature master = this.factory.master();
        if(Math.abs(this.dx-1) > this.range ||
        !master.world().inBound(this.dx-1+master.x(), this.dy+master.y()))
            return false;

        this.dx--;
        return true;
    }

    /**
     * @author: Xu YangXin
     * @return {int} 相对于主人的y距离
     * @description: 获取相对于主人的y距离
     */    
    public int dy(){
        return this.dy;
    }
    
    /**
     * @author: Xu YangXin
     * @return {boolean} 瞄准镜向上移动是否成功
     * @description: 瞄准镜向上移动
     */    
    public boolean dyMoveUp(){
        Creature master = this.factory.master();
        if(Math.abs(this.dy-1) > this.range ||
        !master.world().inBound(this.dx+master.x(), this.dy-1+master.y()))
            return false;
        this.dy--;
        return true;
    }
    
    /**
     * @author: Xu YangXin
     * @return {boolean} 瞄准镜向下移动是否成功
     * @description: 瞄准镜向下移动
     */    
    public boolean dyMoveDown(){
        Creature master = this.factory.master();
        if(Math.abs(this.dy+1) > this.range ||
        !master.world().inBound(this.dx+master.x(), this.dy+1+master.y()))
            return false;
        this.dy++;
        return true;
    }

    /**
     * @author: Xu YangXin
     * @return {*}
     * @description: 抛掷类武器攻击力提升20%
     */    
    public void attackUpgrade(){
        this.attackValue = (int)(this.attackValue*1.2);
        this.throwableItemSample.attackUpgrade();
    }

    /**
     * @author: Xu YangXin
     * @return {*}
     * @description: 启动抛掷类武器的添加抛掷物的计时器
     */    
    public void startAddItemTimer(){
        // 周期性添加扔物
        addItemTimer = new Timer();
        addItemTimer.schedule(
        new TimerTask() {
            public void run() {
                if(factory().master().world().worldIsPaused())
                    return;
                addItem();
            }
        }, addItemInterval , addItemInterval);
    }
}
