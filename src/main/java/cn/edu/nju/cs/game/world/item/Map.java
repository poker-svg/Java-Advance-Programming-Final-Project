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
 * @Date: 2022-12-04 14:59:13
 * @LastEditTime: 2023-01-16 18:18:26
 * @LastEditors: Xu YangXin
 * @Description: 此类代表世界的地图
 * @FilePath: \jfp-poker-svg\src\main\java\cn\edu\nju\cs\game\world\item\Map.java
 * 此项目为南京大学《Java高级程序设计》项目
 */

package cn.edu.nju.cs.game.world.item;

import java.io.Serializable;

public class Map implements Serializable{
    private Tile[][] tiles;
    private int width;
    private int height;
    private DoorPairs doorPairs;
    private Grasses grasses;
    private Boxes boxes;
    private FirePiles firePiles;

    /**
     * @author: Xu YangXin
     * @param {Tile[][]} tiles 地形
     * @param {DoorPairs} doorPairs 传送门对
     * @param {Grasses} grasses 草丛
     * @param {Boxes} boxes 宝箱
     * @param {FirePiles} firePiles 火堆
     * @return {*}
     * @description: 构造器
     */
    
    public Map(Tile[][] tiles, DoorPairs doorPairs, Grasses grasses, Boxes boxes, FirePiles firePiles) {
        this.tiles = tiles;
        this.doorPairs = doorPairs;
        this.grasses = grasses;
        this.boxes = boxes;
        this.firePiles = firePiles;
        this.width = tiles.length;
        this.height = tiles[0].length;
    }

    /**
     * @author: Xu YangXin
     * @return {Tile[][]} 地形的二维数组
     * @description: 获取地图的地形二维数组
     */    
    public Tile[][] tiles() { return this.tiles; }

    /**
     * @author: Xu YangXin
     * @return {int} 地图宽度
     * @description: 获取地图宽度
     */    
    public int width() { return this.width; }
    
    /**
     * @author: Xu YangXin
     * @return {int} 地图高度
     * @description: 获取地图高度
     */    
    public int height() { return this.height; }

    /**
     * @author: Xu YangXin
     * @return {DoorPairs} 地图的传送门对
     * @description: 获取地图的传送门对
     */    
    public DoorPairs doorPairs(){ return this.doorPairs;}

    /**
     * @author: Xu YangXin
     * @return {Grasses} 地图的草丛
     * @description: 获取地图的所有草丛
     */    
    public Grasses grasses(){ return this.grasses;}

    /**
     * @author: Xu YangXin
     * @return {Boxex} 地图的宝箱
     * @description: 获取地图的所有宝箱
     */    
    public Boxes boxes(){ return this.boxes; }

    /**
     * @author: Xu YangXin
     * @return {FirePiles} 地图的火堆
     * @description: 获取地图的所有火堆
     */    
    public FirePiles firePiles(){ return this.firePiles; }
}
