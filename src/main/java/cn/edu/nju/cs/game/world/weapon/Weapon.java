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
 * @LastEditTime: 2023-01-16 21:34:52
 * @LastEditors: Xu YangXin
 * @Description: 此类为武器类，是所有武器的抽象基类
 * @FilePath: \jfp-poker-svg\src\main\java\cn\edu\nju\cs\game\world\weapon\Weapon.java
 * 此项目为南京大学《Java高级程序设计》项目
 */

package cn.edu.nju.cs.game.world.weapon;

import java.util.Timer;
import java.util.TimerTask;

import cn.edu.nju.cs.game.world.item.Tile;

import java.awt.Color;
import java.io.Serializable;

public abstract class Weapon implements Attack, Serializable{
    protected WeaponFactory factory;// 生产此武器的工厂
    protected String name;// 武器的名称
    private Tile image;
    private char glyph;
    private Color color;
    protected int attackValue;  // 武器的攻击力，远程武器则表示子弹的攻击力
    protected int range;// 武器的范围，近战武器表示攻击范围，远程武器代表射程
    protected int coldingInterval;// 武器的冷却时间，单位为毫秒
    protected boolean active;// 武器可用性
    transient protected Timer refreshTimer;
    protected int maxEnergy; // 能量上限
    protected int curEnergy; // 当前能量值

    /**
     * @author: Xu YangXin
     * @param {WeaponFactory} factory 武器工厂
     * @param {String} name 武器名称
     * @param {Tile} image 武器图片
     * @param {int} attackValue 武器攻击力
     * @param {int} range 武器攻击范围
     * @param {int} coldingInterval 武器冷却周期
     * @param {int} maxEnergy 能量上限
     * @return {*}
     * @description: 构造器
     */
    public Weapon(WeaponFactory factory, String name, Tile image,
                int attackValue, int range, int coldingInterval, int maxEnergy){
        this.factory = factory;
        this.name = name;
        this.image = image;
        this.glyph = image.glyph();
        this.color = image.color();
        this.attackValue = attackValue;
        this.range = range;
        this.coldingInterval = coldingInterval;
        this.active = true; // 武器可以攻击
        this.maxEnergy = maxEnergy;
        this.curEnergy = 0; // 武器的能量值初始化为0

        startRefresh();
    }

    /**
     * @author: Xu YangXin
     * @return {WeaponFactory} 武器工厂
     * @description: 获取生产此武器的工厂
     */    
    public WeaponFactory factory(){
        return this.factory;
    }

    /**
     * @author: Xu YangXin
     * @return {String} 武器名称
     * @description: 获取武器名称
     */    
    public String name(){
        return this.name;
    }

    /**
     * @author: Xu YangXin
     * @return {Tile} 武器形象
     * @description: 获取武器形象
     */    
    public Tile image(){
        return this.image;
    }

    /**
     * @author: Xu YangXin
     * @return {char} 武器字符
     * @description: 获取武器字符
     */    
    public char glyph(){
        return this.glyph;
    }

    /**
     * @author: Xu YangXin
     * @return {Color} 武器颜色
     * @description: 获取武器颜色
     */    
    public Color color(){
        return this.color;
    }

    /**
     * @author: Xu YangXin
     * @return {int} 武器的攻击力
     * @description: 获取武器的攻击力
     */    
    public int attackValue(){
        return this.attackValue;
    }

    /**
     * @author: Xu YangXin
     * @return {int} 武器的攻击范围
     * @description: 获取武器的攻击范围
     */    
    public int range(){
        return this.range;
    }

    /**
     * @author: Xu YangXin
     * @return {int} 武器的冷却周期
     * @description: 获取武器的冷却周期
     */    
    public int coldingInterval(){
        return this.coldingInterval;
    }

    /**
     * @author: Xu YangXin
     * @return {boolean} 武器是否可使用
     * @description: 查看武器是否可使用
     */    
    synchronized public boolean isActive(){
        return this.active;
    }
    
    /**
     * @author: Xu YangXin
     * @param {boolean} active 武器可用性
     * @return {}
     * @description: 设置武器可用性
     */    
    synchronized public void setActive(boolean active){
        this.active = active;
    }
    
    /**
     * @author: Xu YangXin
     * @return {int} 武器的最大能量值
     * @description: 获取武器的最大能力值
     */    
    public int maxEnergy(){
        return this.maxEnergy;
    }
    
    /**
     * @author: Xu YangXin
     * @return {int} 武器的当前能力值
     * @description: 获取武器的当前能力值
     */    
    public int curEnergy(){
        return this.curEnergy;
    }
    
    /**
     * @author: Xu YangXin
     * @return {boolean} 武器当前是否可以释放大招
     * @description: 查看武器当前是否可以释放大招
     */    
    public boolean bigAttackActive(){
        return this.curEnergy >= this.maxEnergy;
    }
    
    /**
     * @author: Xu YangXin
     * @return {*}
     * @description: 给武器充能一次
     */    
    public void chargeEnergy(){
        if(this.curEnergy < this.maxEnergy)
            this.curEnergy++;
        return;
    }
    
    /**
     * @author: Xu YangXin
     * @return {*}
     * @description: 清空武器能量
     */    
    public void clearEnergy(){
        this.curEnergy = 0;
    }

    /**
     * @author: Xu YangXin
     * @return {*}
     * @description: 武器开始刷新
     */    
    public void startRefresh(){
        // 周期性刷新武器可用性
        refreshTimer = new Timer();
        refreshTimer.schedule(
            new TimerTask() {
                public void run() {
                    if(factory().master().world().worldIsPaused())
                        return;
                    setActive(true);
                }
            }, coldingInterval , coldingInterval);
    }
}