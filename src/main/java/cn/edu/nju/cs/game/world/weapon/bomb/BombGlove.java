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
 * @LastEditTime: 2023-01-17 16:56:37
 * @LastEditors: Xu YangXin
 * @Description: 此类为炸弹手套类，是可移动炸弹的近战武器
 * @FilePath: \jfp-poker-svg\src\main\java\cn\edu\nju\cs\game\world\weapon\bomb\BombGlove.java
 * 此项目为南京大学《Java高级程序设计》项目
 */

package cn.edu.nju.cs.game.world.weapon.bomb;

import cn.edu.nju.cs.game.world.creature.Creature;
import cn.edu.nju.cs.game.world.creature.Direction;
import cn.edu.nju.cs.game.world.item.Tile;
import cn.edu.nju.cs.game.world.weapon.Weapon;
import cn.edu.nju.cs.game.world.weapon.WeaponFactory;

public class BombGlove extends Weapon{
    public static final String NAME = "BombGlove";
    public static final Tile IMAGE = Tile.BOMB_GLOVE;
    public static final int ATTACK_VALUE = 0;
    public static final int RANGE = 0;
    public static final int COLDING_INTERVAL = 3000;
    public static final int MAX_ENERGY = 3;

    /**
     * @author: Xu YangXin
     * @param {WeaponFactory} factory 生成此炸弹手套的武器工厂
     * @return {*}
     * @description: 构造器
     */    
    public BombGlove(WeaponFactory factory){
        super(factory, BombGlove.NAME, BombGlove.IMAGE, BombGlove.ATTACK_VALUE,
            BombGlove.RANGE, BombGlove.COLDING_INTERVAL, BombGlove.MAX_ENERGY);
    }

    @Override
    /**
     * @author: Xu YangXin
     * @return {boolean} 攻击是否成功
     * @description: 炸弹手套的普攻 —— 推炸弹
     */    
    public boolean attack() {
        Creature master = this.factory().master();
        int x = master.x(), y = master.y();
        Direction direction = master.direction();
        switch (direction) {
            case Left:  x--; break;
            case Right: x++; break;
            case Up:    y--; break;
            case Down:  y++; break;
            default:         break;
        }
        Bomb targetBomb = master.world().getBomb(x, y); // 要推的目标炸弹
        if(targetBomb == null)
            return false;
        targetBomb.startMove(direction);
        return true;
    }

    @Override
    /**
     * @author: Xu YangXin
     * @return {boolean} 大招是否成功释放
     * @description: 炸弹手套的大招 —— 和普攻一样
     */    
    public boolean bigAttack(){
        return this.attack();
    }

    @Override
    /**
     * @author: Xu YangXin
     * @return {*}
     * @description: 炸弹手套的武器升级，没啥用
     */    
    public void attackUpgrade(){

    }
}
