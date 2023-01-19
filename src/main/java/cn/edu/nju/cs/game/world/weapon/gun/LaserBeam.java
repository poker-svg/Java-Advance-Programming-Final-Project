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
 * @LastEditTime: 2023-01-17 17:32:59
 * @LastEditors: Xu YangXin
 * @Description: 此类为激光类，是激光器的子弹
 * @FilePath: \jfp-poker-svg\src\main\java\cn\edu\nju\cs\game\world\weapon\gun\LaserBeam.java
 * 此项目为南京大学《Java高级程序设计》项目
 */

package cn.edu.nju.cs.game.world.weapon.gun;

import cn.edu.nju.cs.game.world.creature.Creature;
import cn.edu.nju.cs.game.world.creature.Direction;
import cn.edu.nju.cs.game.world.item.Tile;

public class LaserBeam extends Bullet{
    public static final int REMAIN_TIME = 500;
    public static final int ATTACK_VALUE = 30;

    private int remainTime; //激光束存在时间


    /**
     * @author: Xu YangXin
     * @param {int} x 激光的x坐标
     * @param {int} y 激光的y坐标
     * @param {Direction} direction 激光的方向
     * @param {Creature} master 激光的主人
     * @return {*}
     * @description: 构造器
     */    
    public LaserBeam(int x, int y, Direction direction, Creature master) {
        // 使用该构造器产生的子弹是静态的
        super(x, y, direction, 1,  LaserBeam.ATTACK_VALUE, Tile.LASER_BEAM_X, 0, master);
        if(direction == Direction.Up || direction == Direction.Down)
            this.image = Tile.LASER_BEAM_Y;
        this.remainTime = LaserBeam.REMAIN_TIME;

        //激光束一段时间(remainTime)后死亡
        Runnable runnable = new Runnable() {
        	public void run() {
                try {
                    Thread.sleep(remainTime());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        		dead();
        	}
        };
        new Thread(runnable).start();
    }

    /**
     * @author: Xu YangXin
     * @return {int} 激光的残存时间
     * @description: 获取激光的残存时间
     */    
    public int remainTime(){
        return this.remainTime;
    }

    /**
     * @author: Xu YangXin
     * @return {boolean} 激光是否存在
     * @description: 查看激光是否存在
     */    
    public boolean isAlive(){
        return this.range != 0;
    }

    /**
     * @author: Xu YangXin
     * @return {*}
     * @description: 抹除激光
     */    
    public void dead(){
        this.range = 0;
    }
}
