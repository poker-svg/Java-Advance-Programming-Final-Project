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
 * @LastEditTime: 2023-01-16 17:48:15
 * @LastEditors: Xu YangXin
 * @Description: 此类代表世界中的传送门对地形
 * @FilePath: \jfp-poker-svg\src\main\java\cn\edu\nju\cs\game\world\item\DoorPairs.java
 * 此项目为南京大学《Java高级程序设计》项目
 */

package cn.edu.nju.cs.game.world.item;

import javafx.util.Pair;
import java.util.List;
import java.io.Serializable;
import java.util.ArrayList;

public class DoorPairs implements Serializable{
    private int width,height;
    private List<Pair<Door,Door>> doorPairs;

    /**
     * @author: Xu YangXin
     * @param {int} width 世界宽度
     * @param {int} height 世界高度
     * @return {*}
     * @description: 构造器
     */    
    public DoorPairs(int width, int height){
        this.doorPairs = new ArrayList<>();
        this.width = width;
        this.height = height;
    }

    /**
     * @author: Xu YangXin
     * @return {List<Pair<Door,Door>>} 所有的传送门对
     * @description: 读取传送门对列表
     */    
    public List<Pair<Door,Door>> doorPairsList(){
        return this.doorPairs;
    }

    /**
     * @author: Xu YangXin
     * @param {int} x1 第一个传送门的x坐标
     * @param {int} y1 第一个传送门的y坐标
     * @param {int} x2 第二个传送门的x坐标
     * @param {int} y2 第二个传送门的y坐标
     * @return {boolean} 添加传送门对是否成功
     * @description: 在世界的两个坐标上添加传送门对
     */    
    public boolean addPairs(int x1, int y1, int x2, int y2){
        if(x1 == x2 && y1 == y2)
            return false;

        if (x1 < 0 || x1 >= width || x2 < 0 || x2 >= width ||
            y1 < 0 || y1 >=height || y2 < 0 || y2 >= height)
            return false;
        DoorPairs.Door door1 = new DoorPairs.Door(x1, y1);
        DoorPairs.Door door2 = new DoorPairs.Door(x2, y2);
        doorPairs.add(new Pair<DoorPairs.Door,DoorPairs.Door>(door1, door2));
        return true;
    }

    /**
     * @author: Xu YangXin
     * @param {int} x 目标的x坐标
     * @param {int} y 目标的y坐标
     * @return {DoorPairs.Door}
     * @description: 根据坐标搜索传送门，找不到返回null
     */    
    public DoorPairs.Door search(int x, int y){
        for(Pair<DoorPairs.Door,DoorPairs.Door> pair : doorPairs){
            DoorPairs.Door door1 = pair.getKey();
            DoorPairs.Door door2 = pair.getValue();
            if(door1.equal(x, y))
                return door2;
            if(door2.equal(x, y))
                return door1;
        }
        return null;
    }

    /**
     * @author: Xu YangXin
     * @param {World} world 目标世界
     * @return {*}
     * @description: 刷新传送门对
     */    
    public void refreshDoorPairs(World world){
        for(Pair<DoorPairs.Door,DoorPairs.Door> pair : world.doorPairs().doorPairsList()){
            DoorPairs.Door door1 = pair.getKey();
            DoorPairs.Door door2 = pair.getValue();
            world.setThing(Tile.DOOR, door1.x(), door1.y());
            world.setThing(Tile.DOOR, door2.x(), door2.y());
        }
    }

    public class Door implements Serializable{
        private int x, y;
        public int x(){ return this.x; }
        public int y(){ return this.y; }

        public Door(int x, int y){
            this.x = x;
            this.y = y;
        }

        public boolean equal(int x, int y){
            return this.x == x && this.y == y;
        }
    }
}
