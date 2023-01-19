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
 * @LastEditTime: 2023-01-16 17:53:09
 * @LastEditors: Xu YangXin
 * @Description: 此类代表世界中的火堆地形
 * @FilePath: \jfp-poker-svg\src\main\java\cn\edu\nju\cs\game\world\item\FirePiles.java
 * 此项目为南京大学《Java高级程序设计》项目
 */

package cn.edu.nju.cs.game.world.item;

import java.util.List;

import cn.edu.nju.cs.game.world.creature.bros.Player;

import java.io.Serializable;
import java.util.ArrayList;

public class FirePiles implements Serializable{
    private int width,height;
    private List<FirePile> firePiles;

    /**
     * @author: Xu YangXin
     * @param {int} width 世界宽度
     * @param {int} height 世界高度
     * @return {*}
     * @description: 构造器
     */    
    public FirePiles(int width, int height){
        this.width = width;
        this.height = height;
        this.firePiles = new ArrayList<>();
    }

    /**
     * @author: Xu YangXin
     * @return {List<FirePile>} 火堆列表
     * @description: 获取火堆列表
     */    
    public List<FirePile> firePilesList(){
        return this.firePiles;
    }

    /**
     * @author: Xu YangXin
     * @param {int} x 目标的x坐标
     * @param {int} y 目标的y坐标
     * @return {FirePiles.FirePile} 目标火堆
     * @description: 根据坐标搜索火堆，找不到返回null
     */    
    public FirePiles.FirePile search(int x, int y){
        for(FirePile firePile : firePiles)
            if(firePile.equal(x, y))
                return firePile;
        return null;
    }

    private int warmCount = 0;
    /**
     * @author: Xu YangXin
     * @param {Player} player 目标玩家
     * @return {boolean} 火堆是否成功给玩家回血
     * @description: 火堆温暖玩家，给玩家回血
     */    
    public boolean warmCreature(Player player){
        if(player == null)
            return false;

        if(player.world().tile(player.x(), player.y()) != Tile.FIRE_PILE)
            return false;

        if(warmCount++ < 1)
            return false;

        player.modifyHP(1);
        return true;
    }

    /**
     * @author: Xu YangXin
     * @param {World} world 目标世界
     * @return {*}
     * @description: 刷新火堆
     */    
    public void refreshFirePiles(World world){
        for(FirePile firePile : world.firePiles().firePilesList()){
            world.setThing(firePile.image(), firePile.x(), firePile.y());
        }
    }

    /**
     * @author: Xu YangXin
     * @param {int} x 目标的x坐标
     * @param {int} y 目标的y坐标
     * @param {Tile} image 目标的形象
     * @return {boolean} 添加火堆是否成功
     * @description: 在某一坐标上添加火堆
     */    
    public boolean addFirePile(int x, int y, Tile image){
        if (x < 0 || x >= width || y < 0 || y >= height)
            return false;
        firePiles.add(new FirePiles.FirePile(x, y, image));
        return true;
    }

    public class FirePile implements Serializable{
        private int x, y;
        public int x(){ return this.x; }
        public int y(){ return this.y; }

        private Tile image;
        public Tile image(){
            return this.image;
        }

        public FirePile(int x, int y, Tile image){
            this.x = x;
            this.y = y;
            this.image = image;
        }

        public boolean equal(int x, int y){
            return this.x == x && this.y == y;
        }
    }
}
