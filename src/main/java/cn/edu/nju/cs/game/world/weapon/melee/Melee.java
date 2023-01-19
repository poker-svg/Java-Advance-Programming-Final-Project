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
 * @LastEditTime: 2023-01-17 18:11:55
 * @LastEditors: Xu YangXin
 * @Description: 此类为近战武器类，是所有近战武器的抽象基类
 * @FilePath: \jfp-poker-svg\src\main\java\cn\edu\nju\cs\game\world\weapon\melee\Melee.java
 * 此项目为南京大学《Java高级程序设计》项目
 */

package cn.edu.nju.cs.game.world.weapon.melee;

import java.util.ArrayList;
import java.util.List;

import cn.edu.nju.cs.game.world.weapon.Weapon;
import cn.edu.nju.cs.game.world.weapon.WeaponFactory;
import cn.edu.nju.cs.game.world.creature.Creature;
import cn.edu.nju.cs.game.world.creature.Direction;
import cn.edu.nju.cs.game.world.item.Tile;
import cn.edu.nju.cs.game.world.item.World;

public abstract class Melee extends Weapon{
    protected Shape shape; // 攻击形状
    protected int remainTime; // 一次攻击的持续时间
    protected AttackDot attackDotSample; // 攻击点样板，用于攻击点的复制

    /**
     * @author: Xu YangXin
     * @param {WeaponFactory} factory 生产近战武器的武器工厂
     * @param {String} name 近战武器的名称
     * @param {Tile} image 近战武器的图像
     * @param {int} attackValue 近战武器的攻击力
     * @param {int} range 近战武器的攻击范围
     * @param {int} coldingInterval 近战武器的冷却间隔时间，单位为ms
     * @param {int} maxEnergy 近战武器的最大能量
     * @param {Shape} shape 近战武器的攻击形状，有扇形、半圆形、圆形
     * @param {int} remainTime 近战武器的残存时间(攻击持续时间)
     * @param {AttackDot} attackDotSample 近战武器的攻击点
     * @return {*}
     * @description: 近战武器的构造器
     */    
    public Melee(WeaponFactory factory, String name, Tile image,
                int attackValue, int range, int coldingInterval, int maxEnergy,
                Shape shape, int remainTime, AttackDot attackDotSample){
        super(factory, name, image, attackValue, range, coldingInterval, maxEnergy);
        this.shape = shape;
        this.remainTime = remainTime;
        this.attackDotSample = attackDotSample;
    }

    /**
     * @author: Xu YangXin
     * @return {Shape} 近战武器的攻击形状
     * @description: 读取近战武器的攻击形状
     */    
    public Shape shape(){
        return this.shape;
    }

    /**
     * @author: Xu YangXin
     * @return {int} 近战武器的攻击持续时间
     * @description: 读取近战武器的攻击持续时间
     */    
    public int remainTime(){
        return this.remainTime;
    }

    /**
     * @author: Xu YangXin
     * @return {*}
     * @description: 近战武器的攻击力提升20%
     */    
    public void attackUpgrade(){
        this.attackValue = (int)(this.attackValue*1.2);
        this.attackDotSample.attackUpgrade();
    }

    @Override
    /**
     * @author: Xu YangXin
     * @return {boolean} 近战武器的普攻是否成功
     * @description: 近战武器的普攻
     */    
    public boolean attack() {
        if(!this.isActive())// 武器处于冷却周期中
            return false;   // 攻击失败

        Creature master = this.factory().master();

        switch (this.shape) {
            case Quater:
                attackQuater(master.x(),master.y(),master.direction(),
                            master.world(),this.attackDotSample);
                break;
            case Half:
                attackHalf(master.x(),master.y(),master.direction(),
                            master.world(),this.attackDotSample);
                break;
            case Full:
                attackFull(master.x(),master.y(),
                            master.world(),this.attackDotSample);
                break;
            default:
                break;
        }

        return true;
    }

    /**
     * @author: Xu YangXin
     * @param {int} x 攻击起始点的x坐标
     * @param {int} y 攻击起始点的y坐标
     * @param {Direction} direction 攻击方向
     * @param {World} world 近战武器所处世界
     * @param {AttackDot} attackDot 近战武器的攻击点
     * @return {boolean} 攻击是否成功
     * @description: 近战武器向扇形区域发起攻击
     */    
    private boolean attackQuater(int x, int y, Direction direction, World world, AttackDot attackDot){
        List<AttackDot> dots = new ArrayList<>();
        switch (direction) {
            case Left:
            {
                int width = 1;
                for(int col = x - 1; col >= x - range; col--){
                    for(int row = y - width; row <= y + width; row++)
                        dots.add(new AttackDot(col, row, attackDot));
                    width++;
                }
                break;
            }
            case Right:
            {
                int width = 1;
                for(int col = x + 1; col <= x + range; col++){
                    for(int row = y - width; row <= y + width; row++)
                        dots.add(new AttackDot(col, row, attackDot));
                    width++;
                }
                break;
            }
            case Up:
            {
                int width = 1;
                for(int row = y - 1; row >= y - range; row--){
                    for(int col = x - width; col <= x + width; col++)
                        dots.add(new AttackDot(col, row, attackDot));
                    width++;
                }
                break;
            }
            case Down:
            {
                int width = 1;
                for(int row = y + 1; row <= y + range; row++){
                    for(int col = x - width; col <= x + width; col++)
                        dots.add(new AttackDot(col, row, attackDot));
                    width++;
                }
                break;
            }
            default:
                break;
        }
        if(dots.isEmpty())
            return false;

        world.addAttackDot(dots);
        return true;
    }

    /**
     * @author: Xu YangXin
     * @param {int} x 攻击起始点的x坐标
     * @param {int} y 攻击起始点的y坐标
     * @param {Direction} direction 攻击方向
     * @param {World} world 近战武器所处世界
     * @param {AttackDot} attackDot 近战武器的攻击点
     * @return {boolean} 攻击是否成功
     * @description: 近战武器向半圆区域发起攻击
     */    
    private boolean attackHalf(int x, int y, Direction direction, World world, AttackDot attackDot){
        List<AttackDot> dots = new ArrayList<>();
        switch (direction) {
            case Left:
            {
                for(int col = x - 1; col >= x - range; col--){
                    for(int row = y - range; row <= y + range; row++)
                        dots.add(new AttackDot(col, row, attackDot));
                }
                break;
            }
            case Right:
            {
                for(int col = x + 1; col <= x + range; col++){
                    for(int row = y - range; row <= y + range; row++)
                        dots.add(new AttackDot(col, row, attackDot));
                }
                break;
            }
            case Up:
            {
                for(int row = y - 1; row >= y - range; row--){
                    for(int col = x - range; col <= x + range; col++)
                        dots.add(new AttackDot(col, row, attackDot));
                }
                break;
            }
            case Down:
            {
                for(int row = y + 1; row <= y + range; row++){
                    for(int col = x - range; col <= x + range; col++)
                        dots.add(new AttackDot(col, row, attackDot));
                }
                break;
            }
            default:
                break;
        }
        if(dots.isEmpty())
            return false;

        world.addAttackDot(dots);
        return true;
    }

    /**
     * @author: Xu YangXin
     * @param {int} x 攻击起始点的x坐标
     * @param {int} y 攻击起始点的y坐标
     * @param {World} world 近战武器所处世界
     * @param {AttackDot} attackDot 近战武器的攻击点
     * @return {boolean} 攻击是否成功
     * @description: 近战武器向圆形区域发起攻击
     */    
    private boolean attackFull(int x, int y, World world, AttackDot attackDot){
        List<AttackDot> dots = new ArrayList<>();
        for(int col = x - range; col <= x + range; col++){
            for(int row = y - range; row <= y + range; row++)
                if(!(col == x && row == y))
                    dots.add(new AttackDot(col, row, attackDot));
        }
        if(dots.isEmpty())
            return false;

        world.addAttackDot(dots);
        return true;
    }
}
