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
 * @LastEditTime: 2023-01-17 20:11:44
 * @LastEditors: Xu YangXin
 * @Description: 此类为爆竹发射器类
 * @FilePath: \jfp-poker-svg\src\main\java\cn\edu\nju\cs\game\world\weapon\throwable\DetonatorThrower.java
 * 此项目为南京大学《Java高级程序设计》项目
 */

package cn.edu.nju.cs.game.world.weapon.throwable;

import cn.edu.nju.cs.game.world.weapon.WeaponFactory;
import cn.edu.nju.cs.game.world.creature.Creature;
import cn.edu.nju.cs.game.world.item.Tile;

public class DetonatorThrower extends ThrowableWeapon{
    public static final int RANGE = 15;   // 可抛掷距离
    public static final String NAME = "DetonatorThrower";
    public static final Tile IMAGE = Tile.DETONATOR_THROWER;
    public static final int ATTACK_VALUE = 80;
    public static final int COLDING_INTERVAL = 2000;
    public static final int MAX_ENERGY = 7;
    public static final int ADD_ITEM_INTERVAL = 3000;
    public static final int MAX_ITEM_AMOUNT = 3;
    public static final int ACTIVATE_INTERVAL = 1000;

    /**
     * @author: Xu YangXin
     * @param {WeaponFactory} factory 生产该爆竹发射器的武器工厂
     * @param {ThrowableItem} throwableItemSample 爆竹发射器的抛掷物
     * @return {*}
     * @description: 爆竹发射器的构造器
     */
    public DetonatorThrower(WeaponFactory factory, ThrowableItem throwableItemSample){
        super(factory, DetonatorThrower.NAME, DetonatorThrower.IMAGE, DetonatorThrower.ATTACK_VALUE,
            DetonatorThrower.RANGE, DetonatorThrower.COLDING_INTERVAL, DetonatorThrower.MAX_ENERGY,
            DetonatorThrower.ADD_ITEM_INTERVAL, DetonatorThrower.MAX_ITEM_AMOUNT, throwableItemSample);
    }

    @Override
    /**
     * @author: Xu YangXin
     * @return {boolean} 爆竹发射器的普攻是否成功
     * @description: 爆竹发射器普攻，在某个区域投掷爆竹
     */    
    public boolean attack(){
        if(!this.isActive() ||          // 爆竹发射器处于冷却周期中
            this.remainingItems <= 0)  // 爆竹数量不足
            return false;               // 攻击失败

        this.remainingItems--;

        Creature master = this.factory().master();
        int x = master.x()+this.dx, y = master.y()+this.dy;

        if(master.world().inBound(x, y))
            master.world().addThrowableItem(new ThrowableItem(x, y, throwableItemSample));

        return true;
    }

    @Override
    /**
     * @author: Xu YangXin
     * @return {boolean} 抛掷类武器的大招是否成功释放
     * @description: 抛掷类武器释放大招 —— 更大的爆竹攻击范围
     */    
    public boolean bigAttack(){
        if(this.curEnergy < this.maxEnergy)// 能量不足
            return false;                  // 攻击失败

        this.throwableItemSample.enlargeRange();
        boolean ret = this.attack();
        this.throwableItemSample.shrinkRange();
        return ret;
    }
}
