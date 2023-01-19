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
 * @LastEditTime: 2023-01-17 18:15:07
 * @LastEditors: Xu YangXin
 * @Description: 此类为火炬类，用于照明
 * @FilePath: \jfp-poker-svg\src\main\java\cn\edu\nju\cs\game\world\weapon\melee\Torch.java
 * 此项目为南京大学《Java高级程序设计》项目
 */

package cn.edu.nju.cs.game.world.weapon.melee;

import cn.edu.nju.cs.game.world.item.Tile;
import cn.edu.nju.cs.game.world.weapon.Weapon;
import cn.edu.nju.cs.game.world.weapon.WeaponFactory;


public class Torch extends Weapon {
    public static final String NAME = "Torch";
    public static final Tile IMAGE = Tile.TORCH;
    public static final int ATTACK_VALUE = 0;
    public static final int RANGE = 2;
    public static final int COLDING_INTERVAL = 300000;
    public static final int MAX_ENERGY = 4;

    /**
     * @author: Xu YangXin
     * @param {WeaponFactory} factory 生产此火炬的武器工厂
     * @return {*}
     * @description: 火炬的构造器
     */    
    public Torch(WeaponFactory factory){
        super(factory, Torch.NAME, Torch.IMAGE, Torch.ATTACK_VALUE,
            Torch.RANGE, Torch.COLDING_INTERVAL, Torch.MAX_ENERGY);
    }

    @Override
    /**
     * @author: Xu YangXin
     * @return {boolean} 火炬释放大招是否成功
     * @description: 火炬的大招，无用
     */    
    public boolean bigAttack(){
        return true;
    }

    @Override
    /**
     * @author: Xu YangXin
     * @return {boolean} 火炬释放普攻是否成功
     * @description: 火炬的普攻，无用
     */    
    public boolean attack(){
        return true;
    }

    @Override
    /**
     * @author: Xu YangXin
     * @return {*}
     * @description: 火炬的攻击升级，无用，火炬无法攻击
     */    
    public void attackUpgrade(){

    }
}
