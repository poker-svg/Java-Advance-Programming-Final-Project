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
 * @LastEditTime: 2023-01-16 18:04:45
 * @LastEditors: Xu YangXin
 * @Description: 此类代表世界中的草丛地形，可隐藏生物
 * @FilePath: \jfp-poker-svg\src\main\java\cn\edu\nju\cs\game\world\item\Grasses.java
 * 此项目为南京大学《Java高级程序设计》项目
 */

package cn.edu.nju.cs.game.world.item;

import java.util.List;

import cn.edu.nju.cs.game.world.creature.Creature;

import java.io.Serializable;
import java.util.ArrayList;

public class Grasses implements Serializable{
    private int width,height;
    private List<Grass> grasses;

    /**
     * @author: Xu YangXin
     * @param {int} width 世界宽度
     * @param {int} height 世界高度
     * @return {*}
     * @description: 构造器
     */    
    public Grasses(int width, int height){
        this.width = width;
        this.height = height;
        this.grasses = new ArrayList<>();
    }

    /**
     * @author: Xu YangXin
     * @return {List<Grass>} 草丛列表
     * @description: 获取所有草丛的列表
     */    
    public List<Grass> grassesList(){
        return this.grasses;
    }

    /**
     * @author: Xu YangXin
     * @param {int} x 目标的x坐标
     * @param {int} y 目标的y坐标
     * @return {Grasses.Grass} 搜索结果
     * @description: 根据坐标搜索目标草丛，找不到则返回null
     */    
    public Grasses.Grass search(int x, int y){
        for(Grass grass : grasses)
            if(grass.equal(x, y))
                return grass;
        return null;
    }

    /**
     * @author: Xu YangXin
     * @param {int} x 目标的x坐标
     * @param {int} y 目标的y坐标
     * @param {Tile} image 草丛的图像
     * @return {boolean} 添加是否成功
     * @description: 在世界的某一坐标添加草丛
     */    
    public boolean addGrass(int x, int y, Tile image){
        if (x < 0 || x >= width || y < 0 || y >= height)
            return false;
        grasses.add(new Grasses.Grass(x, y, image));
        return true;
    }

    /**
     * @author: Xu YangXin
     * @param {Creature} creature 目标生物
     * @return {boolean} 隐藏是否成功
     * @description: 将某一生物隐藏
     */    
    public boolean hideCreature(Creature creature){
        creature.expose();

        switch (creature.world().tile(creature.x(), creature.y())) {
            case GRASS_1:
            case GRASS_2:
            case GRASS_3:
            case GRASS_4:
            case GRASS_5: // 一堆草地
            case GRASS_6:
            case GRASS_7:
            case GRASS_8:
            case GRASS_9:
            case GRASS_10:
            case GRASS_11:
                creature.hide();
                return true;
            default:
                break;
        }

        return false;
    }

    /**
     * @author: Xu YangXin
     * @param {World} world
     * @return {*}
     * @description: 刷新草丛
     */    
    public void refreshGrasses(World world){
        for(Grass grass : world.grasses().grassesList()){
            world.setThing(grass.image(), grass.x(), grass.y());
        }
    }

    public class Grass implements Serializable{
        private int x, y;
        public int x(){ return this.x; }
        public int y(){ return this.y; }

        private Tile image;
        public Tile image(){
            return this.image;
        }

        public Grass(int x, int y, Tile image){
            this.x = x;
            this.y = y;
            this.image = image;
        }

        public boolean equal(int x, int y){
            return this.x == x && this.y == y;
        }
    }
}
