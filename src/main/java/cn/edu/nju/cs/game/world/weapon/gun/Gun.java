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
 * @LastEditTime: 2023-01-17 17:29:13
 * @LastEditors: Xu YangXin
 * @Description: 此类为所有远程攻击武器的抽象基类 —— 枪类
 * @FilePath: \jfp-poker-svg\src\main\java\cn\edu\nju\cs\game\world\weapon\gun\Gun.java
 * 此项目为南京大学《Java高级程序设计》项目
 */

package cn.edu.nju.cs.game.world.weapon.gun;

import java.util.Timer;
import java.util.TimerTask;

import cn.edu.nju.cs.game.world.item.Tile;
import cn.edu.nju.cs.game.world.weapon.Weapon;
import cn.edu.nju.cs.game.world.weapon.WeaponFactory;

public abstract class Gun extends Weapon{
    protected int remainingBullet; // 剩余子弹个数
    transient protected Timer addBulletTimer; //添加子弹的计时器
    protected Bullet bulletSample; // 子弹样板，用于子弹的复制
    protected int addBulletInterval; // 添加子弹的间隔时间，单位毫秒
    protected int maxBulletAmount; // 弹匣容量

    /**
     * @author: Xu YangXin
     * @param {WeaponFactory} factory 生产该枪械的武器工厂
     * @param {String} name 枪械名称
     * @param {Tile} image 枪械图像
     * @param {int} attackValue 枪械攻击力
     * @param {int} range 枪械射击距离
     * @param {int} coldingInterval 枪械冷却间隔时间，单位ms
     * @param {int} maxEnergy 枪械的最大能量值
     * @param {int} addBulletInterval 枪械的添加子弹的周期时间
     * @param {int} maxBulletAmount 枪械的弹匣容量
     * @param {Bullet} bulletSample 枪械所装的子弹
     * @return {*}
     * @description: 构造器
     */
    public Gun(WeaponFactory factory, String name, Tile image,
            int attackValue, int range, int coldingInterval, int maxEnergy,
            int addBulletInterval, int maxBulletAmount, Bullet bulletSample){
        super(factory, name, image, attackValue, range, coldingInterval, maxEnergy);
        this.remainingBullet = 0;
        this.addBulletInterval = addBulletInterval;
        this.maxBulletAmount = maxBulletAmount;
        this.bulletSample = bulletSample;

        startAddBulletTimer();
    }

    /**
     * @author: Xu YangXin
     * @return {int} 枪的剩余子弹数量
     * @description: 获取枪的剩余子弹数量
     */    
    public int remainingBullet(){
        return this.remainingBullet;
    }

    /**
     * @author: Xu YangXin
     * @return {*}
     * @description: 为枪添加子弹
     */    
    public void addBullet(){
        if(remainingBullet < maxBulletAmount)
            this.remainingBullet++;
        return;
    }

    /**
     * @author: Xu YangXin
     * @return {int} 添加子弹的间隔时间
     * @description: 获取添加子弹的间隔时间
     */    
    public int addBulletInterval(){
        return this.addBulletInterval;
    }

    /**
     * @author: Xu YangXin
     * @return {int} 枪的弹匣容量
     * @description: 获取枪的弹匣容量
     */    
    public int maxBulletAmount(){
        return this.maxBulletAmount;
    }

    @Override
    /**
     * @author: Xu YangXin
     * @return {*}
     * @description: 枪械攻击力提升20%
     */    
    public void attackUpgrade(){
        this.attackValue = (int)(this.attackValue*1.2);
        this.bulletSample.attackUpgrade();
    }

    /**
     * @author: Xu YangXin
     * @return {*}
     * @description: 启动枪械添加子弹的计时器
     */    
    public void startAddBulletTimer(){
        // 周期性添加子弹
        addBulletTimer = new Timer();
        addBulletTimer.schedule(
        new TimerTask() {
            public void run() {
                if(factory().master().world().worldIsPaused())
                    return;
                addBullet();
            }
        }, addBulletInterval , addBulletInterval);
    }
}
