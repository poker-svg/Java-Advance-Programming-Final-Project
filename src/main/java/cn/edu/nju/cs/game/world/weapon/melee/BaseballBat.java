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
 * @LastEditTime: 2023-01-17 18:02:58
 * @LastEditors: Xu YangXin
 * @Description: 此类为棒球棍类
 * @FilePath: \jfp-poker-svg\src\main\java\cn\edu\nju\cs\game\world\weapon\melee\BaseballBat.java
 * 此项目为南京大学《Java高级程序设计》项目
 */

package cn.edu.nju.cs.game.world.weapon.melee;

import cn.edu.nju.cs.game.world.item.Tile;
import cn.edu.nju.cs.game.world.weapon.WeaponFactory;


public class BaseballBat extends Melee {
    public static final String NAME = "BaseballBat";
    public static final Tile IMAGE = Tile.BASEBALL_BAT;
    public static final int ATTACK_VALUE = 1;
    public static final int RANGE = 2;
    public static final int COLDING_INTERVAL = 2000;
    public static final int MAX_ENERGY = 4;
    public static Shape SHAPE = Shape.Half;
    public static final int REMAIN_TIME = 800;

    /**
     * @author: Xu YangXin
     * @param {WeaponFactory} factory 生产该棒球棍的武器工厂
     * @param {AttackDot} attackDotSample 棒球棍的棒球
     * @return {*}
     * @description: 棒球棍的构造器
     */    
    public BaseballBat(WeaponFactory factory, AttackDot attackDotSample){
        super(factory, BaseballBat.NAME, BaseballBat.IMAGE, BaseballBat.ATTACK_VALUE,
            BaseballBat.RANGE, BaseballBat.COLDING_INTERVAL, BaseballBat.MAX_ENERGY,
            BaseballBat.SHAPE, BaseballBat.REMAIN_TIME, attackDotSample);
    }

    @Override
    /**
     * @author: Xu YangXin
     * @return {boolean} 棒球棍的大招是否成功释放
     * @description: 棒球棍释放大招，向四周区域发射出棒球
     */    
    public boolean bigAttack(){
        if(this.curEnergy < this.maxEnergy)// 能量不足
            return false;                  // 攻击失败

        this.shape = Shape.Full;
        this.attack();
        this.shape = BaseballBat.SHAPE;
        return true;
    }
}
