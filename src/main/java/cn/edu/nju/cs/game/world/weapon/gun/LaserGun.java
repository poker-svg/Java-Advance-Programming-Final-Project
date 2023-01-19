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
 * @LastEditTime: 2023-01-17 17:36:27
 * @LastEditors: Xu YangXin
 * @Description: 此类为激光枪类
 * @FilePath: \jfp-poker-svg\src\main\java\cn\edu\nju\cs\game\world\weapon\gun\LaserGun.java
 * 此项目为南京大学《Java高级程序设计》项目
 */

package cn.edu.nju.cs.game.world.weapon.gun;

import cn.edu.nju.cs.game.world.weapon.WeaponFactory;
import cn.edu.nju.cs.game.world.creature.Creature;
import cn.edu.nju.cs.game.world.creature.Direction;
import cn.edu.nju.cs.game.world.item.Tile;

public class LaserGun extends Gun{
    public static final int RANGE = 8;
    public static final String NAME = "LaserGun";
    public static final Tile IMAGE = Tile.LASER_GUN;
    public static final int ATTACK_VALUE = 35;
    public static final int COLDING_INTERVAL = 1000;
    public static final int MAX_ENERGY = 5;
    public static final int ADD_BULLET_INTERVAL = 2000;
    public static final int MAX_BULLET_AMOUNT = 5;

    /**
     * @author: Xu YangXin
     * @param {WeaponFactory} factory 生产此激光枪的武器工厂
     * @param {LaserBean} laserBeam 激光枪的激光子弹
     * @return {*}
     * @description: 构造器，激光枪的子弹必须是激光
     */    
    public LaserGun(WeaponFactory factory, LaserBeam laserBeam){
        super(factory, LaserGun.NAME, LaserGun.IMAGE, LaserGun.ATTACK_VALUE,
            LaserGun.RANGE, LaserGun.COLDING_INTERVAL, LaserGun.MAX_ENERGY,
            LaserGun.ADD_BULLET_INTERVAL, LaserGun.MAX_BULLET_AMOUNT, laserBeam);
    }

    @Override
    /**
     * @author: Xu YangXin
     * @return {boolean} 激光枪的普攻是否成功
     * @description: 激光枪的普攻，向玩家面向的方向射出一道激光
     */    
    public boolean attack(){
        if(!this.isActive() ||          // 枪处于冷却周期中
            this.remainingBullet <= 0)  // 子弹不足
            return false;               // 攻击失败

        this.remainingBullet--;

        Creature master = this.factory().master();
        int x = master.x(), y = master.y();
        Direction direction = master.direction();
        switch (direction) {
            case Left:
                x--;
                for(int i = x; i >= x-this.range; i--)
                    master.world().addBullet(
                        new LaserBeam(i, y, direction, this.factory().master())
                    );
                break;
            case Right:
                x++;
                for(int i = x; i <= x+this.range; i++)
                    master.world().addBullet(
                        new LaserBeam(i, y, direction, this.factory().master())
                    );
                break;
            case Up:
                y--;
                for(int j = y; j >= y-this.range; j--)
                    master.world().addBullet(
                        new LaserBeam(x, j, direction, this.factory().master())
                    );
                break;
            case Down:
                y++;
                for(int j = y; j <= y+this.range; j++)
                    master.world().addBullet(
                        new LaserBeam(x, j, direction, this.factory().master())
                    );
                break;
            default:
                break;
        }

        return true;
    }

    @Override
    /**
     * @author: Xu YangXin
     * @return {boolean} 激光枪的大招是否成功
     * @description: 激光枪发射大招，三道激光
     */    
    public boolean bigAttack(){
        if(!this.isActive() ||              // 枪处于冷却周期中
            this.remainingBullet <= 0 ||    // 子弹不足
            this.curEnergy < this.maxEnergy)// 能量不足
        return false;                       // 攻击失败

    this.remainingBullet--;

    Creature master = this.factory().master();
    int x = master.x(), y = master.y();
    Direction direction = master.direction();
    switch (direction) {
        case Left:
            x--;
            for(int i = x; i >= x-this.range; i--){
                master.world().addBullet(
                    new LaserBeam(i, y, direction, this.factory().master())
                );
                master.world().addBullet(
                    new LaserBeam(i, y-1, direction, this.factory().master())
                );
                master.world().addBullet(
                    new LaserBeam(i, y+1, direction, this.factory().master())
                );
            }
            break;
        case Right:
            x++;
            for(int i = x; i <= x+this.range; i++){
                master.world().addBullet(
                    new LaserBeam(i, y, direction, this.factory().master())
                );
                master.world().addBullet(
                    new LaserBeam(i, y-1, direction, this.factory().master())
                );
                master.world().addBullet(
                    new LaserBeam(i, y+1, direction, this.factory().master())
                );
            }
            break;
        case Up:
            y--;
            for(int j = y; j >= y-this.range; j--){
                master.world().addBullet(
                    new LaserBeam(x, j, direction, this.factory().master())
                );
                master.world().addBullet(
                    new LaserBeam(x-1, j, direction, this.factory().master())
                );
                master.world().addBullet(
                    new LaserBeam(x+1, j, direction, this.factory().master())
                );
            }
            break;
        case Down:
            y++;
            for(int j = y; j <= y+this.range; j++){
                master.world().addBullet(
                    new LaserBeam(x, j, direction, this.factory().master())
                );
                master.world().addBullet(
                    new LaserBeam(x-1, j, direction, this.factory().master())
                );
                master.world().addBullet(
                    new LaserBeam(x+1, j, direction, this.factory().master())
                );
            }
            break;
        default:
            break;
    }

    return true;
    }
}
