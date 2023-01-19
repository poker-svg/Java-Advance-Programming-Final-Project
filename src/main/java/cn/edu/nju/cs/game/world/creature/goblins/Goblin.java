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
 * @LastEditTime: 2023-01-16 17:35:52
 * @LastEditors: Xu YangXin
 * @Description: 此类为妖精类，是所有妖精的抽象基类
 * @FilePath: \jfp-poker-svg\src\main\java\cn\edu\nju\cs\game\world\creature\goblins\Goblin.java
 * 此项目为南京大学《Java高级程序设计》项目
 */

package cn.edu.nju.cs.game.world.creature.goblins;

import java.awt.Color;
import java.util.Random;

import cn.edu.nju.cs.game.world.creature.Creature;
import cn.edu.nju.cs.game.world.creature.Direction;
import cn.edu.nju.cs.game.world.creature.bros.Player;
import cn.edu.nju.cs.game.world.item.World;

public abstract class Goblin extends Creature{

    private int range = 3;
    private int remainRange = 3;

    /**
     * @author: Xu YangXin
     * @return {int} 妖精的活动范围
     * @description: 读取妖精的活动范围
     */    
    public int range(){
        return this.range;
    }

    /**
     * @author: Xu YangXin
     * @return {int} 妖精当前剩余的活动范围
     * @description: 读取妖精当前剩余的活动范围
     */    
    public int remainRange(){
        return this.remainRange;
    }

    /**
     * @author: Xu YangXin
     * @param world 妖精所处世界
     * @param glyph 妖精的字符表示
     * @param color 妖精的颜色
     * @param maxHP 妖精的最大血量
     * @param attack 妖精的手刀攻击值
     * @param defense 妖精的护甲值
     * @param visionRadius 妖精的视野
     * @param moveSpeedInterval 妖精的移动间隔时间
     * @return {*}
     * @description: 构造器
     */
    public Goblin (World world, char glyph, Color color, int maxHP,
                int attack, int defense, int visionRadius, int moveSpeedInterval){
        super(world, glyph, color, maxHP, attack, defense, visionRadius, moveSpeedInterval);
        Random random = new Random();
        switch (random.nextInt(4)) {
            case 0:
                this.setDirection(Direction.Left);
                break;
            case 1:
                this.setDirection(Direction.Right);
                break;
            case 2:
                this.setDirection(Direction.Up);
                break;
            case 3:
                this.setDirection(Direction.Down);
                break;
        }
    }

    /**
     * @author: Xu YangXin
     * @return {*}
     * @description: 妖精环视周围并进行攻击
     */    
    public void seeAndAttack(){
        int playerX, playerY;
        int goblinX = this.x(), goblinY = this.y();
        synchronized(this.weaponFactory.master().world().getPlayers()){


            for(Player player : this.weaponFactory.master().world().getPlayers()){
                playerX = player.x(); playerY = player.y();

                boolean canAttack = false;
                if(this.canSee(playerX, playerY))
                {
                    if(goblinX == playerX){ // 在同一列
                        if(goblinY < playerY &&     // 妖精在玩家上方，并且妖精方向向下
                            this.direction() == Direction.Down)
                            canAttack = true;
                        if(goblinY > playerY &&     // 妖精在玩家下方，并且妖精方向向上
                            this.direction() == Direction.Up)
                            canAttack = true;
                    }
                    else if(goblinY == playerY){ //在同一行
                        if(goblinX < playerX &&     // 妖精在玩家左侧，并且妖精方向向右
                            this.direction() == Direction.Right)
                            canAttack = true;
                        if(goblinY > playerY &&     // 妖精在玩家右侧，并且妖精方向向左
                            this.direction() == Direction.Left)
                            canAttack = true;
                    }
                }

                if(canAttack)
                    this.weaponFactory.curWeapon().attack();
            }


        }
    }

    /**
     * @author: Xu YangXin
     * @return {*}
     * @description: 妖精移动一步
     */    
    public void moveOneStep(){
        if(!this.moveActive || this.hp() < 1)
            return;

        if(this.remainRange <= 0){
            this.remainRange = this.range;
            this.reverseDirection();
        }

        int oldX = this.x(), oldY = this.y();

        switch (this.direction()) {
            case Left:
                this.moveBy(-1, 0);
                break;
            case Right:
                this.moveBy(1, 0);
                break;
            case Up:
                this.moveBy(0, -1);
                break;
            case Down:
                this.moveBy(0, 1);
                break;
            default:
                break;
        }

        if(oldX == this.x() && oldY == this.y()){ // 动不了
            this.reverseDirection();
        }
        else{   // 动了
            this.remainRange--;
        }
    }

    /**
     * @author: Xu YangXin
     * @return {*}
     * @description: 妖精换一个相反的方向
     */    
    private void reverseDirection(){
        switch (this.direction()) {
            case Right:
                this.setDirection(Direction.Left);
                break;
            case Left:
                this.setDirection(Direction.Right);
                break;
            case Down:
                this.setDirection(Direction.Up);
                break;
            case Up:
                this.setDirection(Direction.Down);
                break;
            default:
                break;
        }
    }
}
