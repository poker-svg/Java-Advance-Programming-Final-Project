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
 * @LastEditTime: 2023-01-16 21:02:35
 * @LastEditors: Xu YangXin
 * @Description: 此类为创建世界的世界建造器类
 * @FilePath: \jfp-poker-svg\src\main\java\cn\edu\nju\cs\game\world\item\WorldBuilder.java
 * 此项目为南京大学《Java高级程序设计》项目
 */

package cn.edu.nju.cs.game.world.item;

import java.io.IOException;
import java.io.Serializable;
import java.util.Random;

import cn.edu.nju.cs.logger.Recorder;

public class WorldBuilder implements Serializable{
    private int width;
    private int height;
    private Tile[][] tiles;
    private DoorPairs doorPairs;
    private Grasses grasses;
    private Boxes boxes;
    private FirePiles firePiles;

    /**
     * @author: Xu YangXin
     * @param {int} width 世界的宽度
     * @param {int} height 世界的高度
     * @return {*}
     * @description: 构造器
     */    
    public WorldBuilder(int width, int height) {
        this.width = width;
        this.height = height;
        this.tiles = new Tile[width][height];
        this.doorPairs = new DoorPairs(width, height);
        this.grasses = new Grasses(width, height);
        this.boxes = new Boxes(width, height);
        this.firePiles = new FirePiles(width, height);
    }

    /**
     * @author: Xu YangXin
     * @return {World} 创建的世界
     * @description: 建造世界
     */    
    public World build() {
        return new World(tiles, doorPairs, grasses, boxes, firePiles);
    }

    /**
     * @author: Xu YangXin
     * @param {String} worldName 世界名称
     * @return {World}
     * @description: 根据存储的世界文件名来加载世界
     */    
    public static World loadWorld(String worldName){
        World targetWorld = null;
        try {
            targetWorld = Recorder.readWorld(worldName);
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
        return targetWorld;
    }

    /**
     * @author: Xu YangXin
     * @param {World} world 目标世界
     * @return {*}
     * @description: 存储世界
     */
    public static void storeWorld(World world){
        if(world == null)
            return;
        try {
            Recorder.writeWorld(world);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * @author: Xu YangXin
     * @return {WorldBuilder} 用于后续的建造
     * @description: 随机生成地形
     */    
    private WorldBuilder randomizeTiles() {
        for (int width = 0; width < this.width; width++) {
            for (int height = 0; height < this.height; height++) {
                Random rand = new Random();
                switch (rand.nextInt(World.TILE_TYPES)) {
                    case 0:
                        tiles[width][height] = Tile.FLOOR;
                        break;
                    case 1:
                        tiles[width][height] = Tile.WALL;
                        break;
                }
            }
        }
        return this;
    }

    /**
     * @author: Xu YangXin
     * @param {int} factor 参数
     * @return {WorldBuilder} 用于后续的建造
     * @description: 顺滑地形边界
     */    
    private WorldBuilder smooth(int factor) {
        Tile[][] newtemp = new Tile[width][height];
        if (factor > 1) {
            smooth(factor - 1);
        }
        for (int width = 0; width < this.width; width++) {
            for (int height = 0; height < this.height; height++) {
                // Surrounding walls and floor
                int surrwalls = 0;
                int surrfloor = 0;

                // Check the tiles in a 3x3 area around center tile
                for (int dwidth = -1; dwidth < 2; dwidth++) {
                    for (int dheight = -1; dheight < 2; dheight++) {
                        if (width + dwidth < 0 || width + dwidth >= this.width || height + dheight < 0
                                || height + dheight >= this.height) {
                            continue;
                        } else if (tiles[width + dwidth][height + dheight] == Tile.FLOOR) {
                            surrfloor++;
                        } else if (tiles[width + dwidth][height + dheight] == Tile.WALL) {
                            surrwalls++;
                        }
                    }
                }
                Tile replacement;
                if (surrwalls > surrfloor) {
                    replacement = Tile.WALL;
                } else {
                    replacement = Tile.FLOOR;
                }
                newtemp[width][height] = replacement;
            }
        }
        tiles = newtemp;
        return this;
    }

    /**
     * @author: Xu YangXin
     * @param {int} size 传送门对数量
     * @return {WorldBuilder} 用于后续的建造
     * @description: 为世界添加传送门对
     */    
    public WorldBuilder addDoors(int size) {
        Random randX = new Random();
        Random randY = new Random();
        int x1, y1, x2, y2;

        for(int i = 0; i < size;){
            x1 = randX.nextInt(this.width);
            y1 = randY.nextInt(this.height);
            x2 = randX.nextInt(this.width);
            y2 = randY.nextInt(this.height);

            if( this.tiles[x1][y1] == Tile.FLOOR && this.tiles[x2][y2] == Tile.FLOOR &&
                doorPairs.search(x1, y1) == null && doorPairs.search(x2, y2) == null){
                i++;
                doorPairs.addPairs(x1, y1, x2, y2);
                this.tiles[x1][y1] = Tile.DOOR;
                this.tiles[x2][y2] = Tile.DOOR;
            }
        }
        return this;
    }

    /**
     * @author: Xu YangXin
     * @param {int} size 草丛数量
     * @return {WorldBuilder} 用于后续的建造
     * @description: 为世界添加草丛
     */    
    public WorldBuilder addGrasses(int size) {
        Random randX = new Random();
        Random randY = new Random();
        Tile grass = Tile.GRASS_1;
        int x, y;

        for(int i = 0; i < size;){
            x = randX.nextInt(this.width);
            y = randY.nextInt(this.height);

            if( this.tiles[x][y] == Tile.FLOOR && this.grasses.search(x, y) == null){
                i++;
                grass = Tile.randomGrass();
                this.grasses.addGrass(x, y, grass);
                this.tiles[x][y] = grass;
            }
        }

        return this;
    }

    /**
     * @author: Xu YangXin
     * @param {int} size 火堆数量
     * @return {WorldBuilder} 用于后续的建造
     * @description: 为世界添加火堆
     */    
    public WorldBuilder addFirePiles(int size) {
        Random randX = new Random();
        Random randY = new Random();
        int x, y;

        for(int i = 0; i < size;){
            x = randX.nextInt(this.width);
            y = randY.nextInt(this.height);

            if( this.tiles[x][y] == Tile.FLOOR && this.firePiles.search(x, y) == null){
                i++;
                this.firePiles.addFirePile(x, y, Tile.FIRE_PILE);
                this.tiles[x][y] = Tile.FIRE_PILE;
            }
        }

        return this;
    }

    /**
     * @author: Xu YangXin
     * @param {int} size 树的数目
     * @return {WorldBuilder} 用于后续的建造
     * @description: 为世界添加树木
     */    
    public WorldBuilder addTrees(int size) {
        Random randX = new Random();
        Random randY = new Random();
        int x, y;

        for(int i = 0; i < size;){
            x = randX.nextInt(this.width);
            y = randY.nextInt(this.height);

            if(this.tiles[x][y] == Tile.FLOOR){
                i++;
                this.tiles[x][y] = Tile.randomTree();
            }
        }

        return this;
    }

    /**
     * @author: Xu YangXin
     * @param {int} size 房屋数目
     * @return {WorldBuilder} 用于后续的建造
     * @description: 为世界添加房屋
     */    
    public WorldBuilder addHouses(int size) {
        Random randX = new Random();
        Random randY = new Random();
        int x, y;

        for(int i = 0; i < size;){
            x = randX.nextInt(this.width);
            y = randY.nextInt(this.height);

            if(this.tiles[x][y] == Tile.FLOOR){
                i++;
                this.tiles[x][y] = Tile.randomHouse();
            }
        }

        return this;
    }

    /**
     * @author: Xu YangXin
     * @param {int} size 宝箱数目
     * @return {WorldBuilder} 用于后续的建造
     * @description: 为世界添加宝箱
     */    
    public WorldBuilder addBoxes(int size) {
        Random randX = new Random();
        Random randY = new Random();
        Tile box = Tile.BOX_CLOSE;
        int x, y;

        for(int i = 0; i < size;){
            x = randX.nextInt(this.width);
            y = randY.nextInt(this.height);

            if( this.tiles[x][y] == Tile.FLOOR && this.boxes.search(x, y) == null){
                i++;
                this.boxes.addBox(x, y);
                this.tiles[x][y] = box;
            }
        }

        return this;
    }

    /**
     * @author: Xu YangXin
     * @param {int} size 路牌数目
     * @return {WorldBuilder} 用于后续的建造
     * @description: 为世界添加路牌
     */    
    public WorldBuilder addBoard(int size) {
        Random randX = new Random();
        Random randY = new Random();
        int x, y;

        for(int i = 0; i < size;){
            x = randX.nextInt(this.width);
            y = randY.nextInt(this.height);

            if(this.tiles[x][y] == Tile.FLOOR){
                i++;
                this.tiles[x][y] = Tile.randomBoard();
            }
        }

        return this;
    }

    /**
     * @author: Xu YangXin
     * @return {WorldBuilder} 用于后续的建造
     * @description: 创建洞穴
     */    
    public WorldBuilder makeCaves() {
        // return randomizeTiles().smooth(8).addDoors(5).addGrasses(30).
        //                         addTrees(30).addHouses(30).addBoxes(30).
        //                         addBoard(20).addFirePiles(30);
        return randomizeTiles().smooth(4).addDoors(5).addGrasses(10).
                                addTrees(10).addHouses(20).addBoxes(10).
                                addBoard(10).addFirePiles(10);
    }

}
