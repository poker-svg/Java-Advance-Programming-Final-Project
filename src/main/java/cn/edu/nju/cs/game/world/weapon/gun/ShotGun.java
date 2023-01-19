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
 * @LastEditTime: 2023-01-17 17:51:20
 * @LastEditors: Xu YangXin
 * @Description: 此类为霰弹枪类
 * @FilePath: \jfp-poker-svg\src\main\java\cn\edu\nju\cs\game\world\weapon\gun\ShotGun.java
 * 此项目为南京大学《Java高级程序设计》项目
 */

package cn.edu.nju.cs.game.world.weapon.gun;

import cn.edu.nju.cs.game.world.weapon.WeaponFactory;
import cn.edu.nju.cs.game.world.creature.Creature;
import cn.edu.nju.cs.game.world.creature.Direction;
import cn.edu.nju.cs.game.world.item.Tile;

public class ShotGun extends Gun {
    public static final String NAME = "ShotGun";
    public static final Tile IMAGE = Tile.SHOT_GUN;
    public static final int ATTACK_VALUE = 50;
    public static final int RANGE = 5;
    public static final int COLDING_INTERVAL = 2000;
    public static final int MAX_ENERGY = 4;
    public static final int ADD_BULLET_INTERVAL = 3000;
    public static final int MAX_BULLET_AMOUNT = 5;

    /**
     * @author: Xu YangXin
     * @param {WeaponFactory} factory 生产此霰弹枪的武器工厂
     * @param {Bullet} bulletSample 霰弹枪的子弹
     * @return {*}
     * @description: 构造器
     */    
    public ShotGun(WeaponFactory factory, Bullet bulletSample){
        super(factory,ShotGun.NAME, ShotGun.IMAGE, ShotGun.ATTACK_VALUE,
                ShotGun.RANGE, ShotGun.COLDING_INTERVAL, ShotGun.MAX_ENERGY,
                ShotGun.ADD_BULLET_INTERVAL, ShotGun.MAX_BULLET_AMOUNT, bulletSample);
    }

    @Override
    /**
     * @author: Xu YangXin
     * @return {boolean} 霰弹枪械的普攻是否成功
     * @description: 霰弹枪普攻，向玩家面向的方向射出3发子弹
     */    
    public boolean attack(){
        if(!this.isActive() ||          // 枪处于冷却周期中
            this.remainingBullet <= 0)  // 子弹不足
            return false;               // 攻击失败

        this.remainingBullet--;

        Creature master = this.factory().master();
        int x1 = master.x(), y1 = master.y();
        int x2 = x1, y2 = y1, x3 = x1, y3 = y1;
        Direction direction1 = master.direction(), direction2 = null, direction3 = null;
        switch (direction1) {
            case Left:  x1--; x2--; y2++; x3--; y3--; direction2 = Direction.LeftDown; direction3 = Direction.LeftUp;    break;
            case Right: x1++; x2++; y2--; x3++; y3++; direction2 = Direction.RightUp;   direction3 = Direction.RightDown; break;
            case Up:    y1--; x2--; y2--; x3++; y3--; direction2 = Direction.LeftUp;    direction3 = Direction.RightUp;   break;
            case Down:  y1++; x2++; y2++; x3--; y3++; direction2 = Direction.RightDown; direction3 = Direction.LeftDown;  break;
            default:
                break;
        }

        // 主方向射击
        master.world().addBullet(
            new Bullet(x1, y1, direction1 ,this.bulletSample)
        );
        // 副方向射击
        master.world().addBullet(
            new Bullet(x2, y2, direction2 ,this.bulletSample)
        );
        // 副方向射击
        master.world().addBullet(
            new Bullet(x3, y3, direction3 ,this.bulletSample)
        );

        return true;
    }

    @Override
    /**
     * @author: Xu YangXin
     * @return {boolean} 霰弹枪的大招是否成功
     * @description: 霰弹枪的大招，向玩家四周发出8发子弹
     */    
    public boolean bigAttack(){
        if(!this.isActive() ||              // 枪处于冷却周期中
            this.remainingBullet <= 0 ||    // 子弹不足
            this.curEnergy < this.maxEnergy)// 能量不足
        return false;                       // 攻击失败

        this.remainingBullet--;

        Creature master = this.factory().master();
        int x = master.x(), y = master.y();

        master.world().addBullet(
            new Bullet(x-1, y, Direction.Left ,this.bulletSample)
        );
        master.world().addBullet(
            new Bullet(x+1, y, Direction.Right ,this.bulletSample)
        );
        master.world().addBullet(
            new Bullet(x, y-1, Direction.Up ,this.bulletSample)
        );
        master.world().addBullet(
            new Bullet(x, y+1, Direction.Down ,this.bulletSample)
        );
        master.world().addBullet(
            new Bullet(x-1, y-1, Direction.LeftUp ,this.bulletSample)
        );
        master.world().addBullet(
            new Bullet(x-1, y+1, Direction.LeftDown ,this.bulletSample)
        );
        master.world().addBullet(
            new Bullet(x+1, y-1, Direction.RightUp ,this.bulletSample)
        );
        master.world().addBullet(
            new Bullet(x+1, y+1, Direction.RightDown ,this.bulletSample)
        );

        return true;
    }
}
