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
 * @LastEditTime: 2023-01-17 17:43:10
 * @LastEditors: Xu YangXin
 * @Description: 此类为普通枪械类
 * @FilePath: \jfp-poker-svg\src\main\java\cn\edu\nju\cs\game\world\weapon\gun\NormalGun.java
 * 此项目为南京大学《Java高级程序设计》项目
 */

package cn.edu.nju.cs.game.world.weapon.gun;

import cn.edu.nju.cs.game.world.weapon.WeaponFactory;
import cn.edu.nju.cs.game.world.creature.Creature;
import cn.edu.nju.cs.game.world.item.Tile;
public class NormalGun extends Gun{
    public static final int RANGE = 5;
    public static final String NAME = "NormalGun";
    public static final Tile IMAGE = Tile.NORMAL_GUN;
    public static final int ATTACK_VALUE = 10;
    public static final int COLDING_INTERVAL = 800;
    public static final int MAX_ENERGY = 3;
    public static final int ADD_BULLET_INTERVAL = 1000;
    public static final int MAX_BULLET_AMOUNT = 3;

    /**
     * @author: Xu YangXin
     * @param {WeaponFactory} factory 生产此普通枪的武器工厂
     * @param {Bullet} bulletSample 普通枪的子弹
     * @return {*}
     * @description: 构造器
     */    
    public NormalGun(WeaponFactory factory, Bullet bulletSample){
        super(factory, NormalGun.NAME, NormalGun.IMAGE, NormalGun.ATTACK_VALUE,
                NormalGun.RANGE, NormalGun.COLDING_INTERVAL, NormalGun.MAX_ENERGY,
                NormalGun.ADD_BULLET_INTERVAL, NormalGun.MAX_BULLET_AMOUNT, bulletSample);
    }

    @Override
    /**
     * @author: Xu YangXin
     * @return {boolean} 普通枪械的普攻是否成功
     * @description: 普通枪普攻，向玩家面向的方向射出子弹
     */    
    public boolean attack(){
        if(!this.isActive() ||          // 枪处于冷却周期中
            this.remainingBullet <= 0)  // 子弹不足
            return false;               // 攻击失败

        Creature master = this.factory().master();
        int x = master.x(), y = master.y();
        switch (master.direction()) {
            case Left:
                x--;
                break;
            case Right:
                x++;
                break;
            case Up:
                y--;
                break;
            case Down:
                y++;
                break;
            default:
                break;
        }

        master.world().addBullet(
            new Bullet(x, y, master.direction(), this.bulletSample)
        );
        this.remainingBullet--;

        return true;
    }

    @Override
    /**
     * @author: Xu YangXin
     * @return {boolean} 普通枪的大招是否成功
     * @description: 普通枪的大招，和普攻一致
     */    
    public boolean bigAttack(){
        if(!this.isActive() ||              // 枪处于冷却周期中
            this.remainingBullet <= 0 ||    // 子弹不足
            this.curEnergy < this.maxEnergy)// 能量不足
        return false;                       // 攻击失败

        Creature master = this.factory().master();
        int x = master.x(), y = master.y();
        switch (master.direction()) {
            case Left:
                x--;
                break;
            case Right:
                x++;
                break;
            case Up:
                y--;
                break;
            case Down:
                y++;
                break;
            default:
                break;
        }

        master.world().addBullet(
            new Bullet(x, y, master.direction(), this.bulletSample)
        );
        this.remainingBullet--;

        return true;
    }
}
