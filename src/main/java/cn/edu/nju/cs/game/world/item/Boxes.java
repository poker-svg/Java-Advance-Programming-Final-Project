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
 * @LastEditTime: 2023-01-16 17:43:08
 * @LastEditors: Xu YangXin
 * @Description: 此类为箱子类，所有箱子内部一开始含有随机的武器，不可摧毁且可隐藏
 * @FilePath: \jfp-poker-svg\src\main\java\cn\edu\nju\cs\game\world\item\Boxes.java
 * 此项目为南京大学《Java高级程序设计》项目
 */

package cn.edu.nju.cs.game.world.item;

import java.util.List;
import java.io.Serializable;
import java.util.ArrayList;

import cn.edu.nju.cs.game.world.creature.bros.Player;
import cn.edu.nju.cs.game.world.weapon.WeaponFactory;

public class Boxes implements Serializable{

    private int width,height;
    private List<Box> boxes;

    /**
     * @author: Xu YangXin
     * @param {int} width 世界的宽度
     * @param {int} height 世界的高度
     * @return {*}
     * @description: 构造器
     */    
    public Boxes(int width, int height){
        this.width = width;
        this.height = height;
        this.boxes = new ArrayList<>();
    }

    /**
     * @author: Xu YangXin
     * @return {Lis<Box>} 箱子序列
     * @description: 读取所有箱子
     */    
    public List<Box> boxesList(){
        return this.boxes;
    }

    /**
     * @author: Xu YangXin
     * @param {int} x 目标的x坐标
     * @param {int} y 目标的y坐标
     * @return {Boxes.Box} 目标箱子
     * @description: 根据坐标搜索目标箱子，若坐标上没有箱子则返回null
     */    
    public Boxes.Box search(int x, int y){
        for(Box box : boxes)
            if(box.equal(x, y))
                return box;
        return null;
    }

    /**
     * @author: Xu YangXin
     * @param {int} x 目标的x坐标
     * @param {int} y 目标的y坐标
     * @return {*}
     * @description: 在目标坐标上添加箱子
     */    
    public boolean addBox(int x, int y){
        if (x < 0 || x >= width || y < 0 || y >= height)
            return false;
        boxes.add(new Boxes.Box(x, y));
        return true;
    }

    /**
     * @author: Xu YangXin
     * @param {Player} player 玩家
     * @return {boolean}
     * @description: 玩家进入箱子，若玩家进入箱则隐身+拿武器，若未进入则什么也不做
     */    
    public boolean enterBox(Player player){
        player.expose();
        int x = player.x(), y = player.y();
        World world = player.world();

        try {
            switch (world.tile(x, y)) {
                case BOX_CLOSE:
                    player.hide();
                    Box box1 = world.boxes().search(x, y);
                    if(box1 != null && box1.weapon() != null){
                        player.weaponFactory().newWeapon(box1.open());
                    }
                    return true;
                case BOX_OPEN:
                    player.hide();
                    Box box2 = world.boxes().search(x, y);
                    if(box2 != null)
                        box2.close();
                    return true;
                default:
                    break;
            }
            return false;
        } finally {
            this.refreshBox(world);
        }
    }

    /**
     * @author: Xu YangXin
     * @param {World} world 箱子所处世界
     * @return {*}
     * @description: 刷新箱子
     */    
    public void refreshBox(World world){
        for(Box box : this.boxesList()){
            world.setThing(box.image(), box.x(), box.y());
        }
    }

    public class Box implements Serializable{
        private int x, y;
        public int x(){ return this.x; }
        public int y(){ return this.y; }

        private Tile image;
        public Tile image(){
            return this.image;
        }

        private Tile weapon;
        public Tile weapon(){
            return this.weapon;
        }

        public Tile open(){
            try {
                this.image = Tile.BOX_OPEN;
                return this.weapon;
            }
            finally{
                this.weapon = null;
            }
        }

        public void close(){
            this.image = Tile.BOX_CLOSE;
        }

        public Box(int x, int y){
            this.x = x;
            this.y = y;
            this.image = Tile.BOX_CLOSE;
            this.weapon = WeaponFactory.newRandomWeapon();
        }

        public boolean equal(int x, int y){
            return this.x == x && this.y == y;
        }
    }
}
