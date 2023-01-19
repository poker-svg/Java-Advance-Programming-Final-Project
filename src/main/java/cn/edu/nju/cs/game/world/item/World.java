package cn.edu.nju.cs.game.world.item;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.edu.nju.cs.game.world.creature.Creature;
import cn.edu.nju.cs.game.world.creature.bros.*;
import cn.edu.nju.cs.game.world.creature.goblins.*;
import cn.edu.nju.cs.game.world.weapon.bomb.Bomb;
import cn.edu.nju.cs.game.world.weapon.gun.Bullet;
import cn.edu.nju.cs.game.world.weapon.melee.AttackDot;
import cn.edu.nju.cs.game.world.weapon.throwable.ThrowableItem;

public class World implements Serializable{
    public static final int TILE_TYPES = 2;

    private Map map;
    private volatile boolean world_pause_flag;
    private List<Creature> creatures;
    private List<Goblin> goblins;
    private List<Player> players;
    private List<Bomb> bombs;
    private List<Bullet> bullets;
    private List<AttackDot> attackDots;
    private List<ThrowableItem> throwableItems;

    /**
     * @author: Xu YangXin
     * @param {Tile[][]} tiles 地形的二维数组
     * @param {DoorPairs} doorPairs 传送门对
     * @param {Grasses} grasses 草丛
     * @param {Boxes} boxes 宝箱
     * @param {FirePiles} firePiles 火堆
     * @return {*}
     * @description: 构造器
     */
    public World(Tile[][] tiles, DoorPairs doorPairs, Grasses grasses, Boxes boxes, FirePiles firePiles) {
        this.map = new Map(tiles, doorPairs, grasses, boxes, firePiles);
        this.creatures = new ArrayList<>();
        this.goblins = new ArrayList<>();
        this.players = new ArrayList<>();
        this.bombs = new ArrayList<>();
        this.bullets = new ArrayList<>();
        this.attackDots = new ArrayList<>();
        this.throwableItems = new ArrayList<>();
    }

    /**
     * @author: Xu YangXin
     * @param {Map} otherMap 地图
     * @return {*}
     * @description: 构造器
     */    
    public World(Map otherMap){
        this.map = otherMap;
        this.world_pause_flag = false;
        this.creatures = new ArrayList<>();
        this.goblins = new ArrayList<>();
        this.players = new ArrayList<>();
        this.bombs = new ArrayList<>();
        this.bullets = new ArrayList<>();
        this.attackDots = new ArrayList<>();
        this.throwableItems = new ArrayList<>();
    }


    /**
     * @author: Xu YangXin
     * @return {Map} 地图
     * @description: 获取世界的地图
     */    
    public Map map(){ return this.map;}

    /**
     * @author: Xu YangXin
     * @return {DoorPairs} 传送门对
     * @description: 获取世界的所有传送门对
     */    
    public DoorPairs doorPairs(){ return this.map.doorPairs();}

    /**
     * @author: Xu YangXin
     * @return {Grasses} 草丛
     * @description: 获取世界的所有草丛
     */    
    public Grasses grasses(){ return this.map.grasses();}
    
    /**
     * @author: Xu YangXin
     * @return {Boxes} 宝箱
     * @description: 获取世界的所有宝箱
     */    
    public Boxes boxes(){ return this.map.boxes(); }

    /**
     * @author: Xu YangXin
     * @return {FirePiles} 火堆
     * @description: 获取世界的所有火堆
     */    
    public FirePiles firePiles(){ return this.map.firePiles(); }

    /**
     * @author: Xu YangXin
     * @return {*}
     * @description: 重启世界，让世界动起来
     */    
    public void resetWorld(){
        this.world_pause_flag = false;
        for(Creature creature: this.creatures)
            creature.startMove();
        for(Player player : this.players)
            player.weaponFactory().startAllWeapons();
        for(Goblin goblin : this.goblins)
            goblin.weaponFactory().startAllWeapons();

        this.bombs = new ArrayList<>();
        this.bullets = new ArrayList<>();
        this.attackDots = new ArrayList<>();
        this.throwableItems = new ArrayList<>();
    }

    /**
     * @author: Xu YangXin
     * @param {int} x 目标的x坐标
     * @param {int} y 目标的y坐标
     * @return {Tile} 目标地形
     * @description: 获取地图的某个坐标上的地形
     */    
    public Tile tile(int x, int y) {
        if (x < 0 || x >= this.map.width() || y < 0 || y >= this.map.height()) {
            return Tile.BOUNDS;
        } else {
            return this.map.tiles()[x][y];
        }
    }

    /**
     * @author: Xu YangXin
     * @param {int} x 目标的x坐标
     * @param {int} y 目标的y坐标
     * @return {char} 目标地形的字符
     * @description: 获取地图的某个坐标上的地形的字符
     */    
    public char glyph(int x, int y) {
        return this.map.tiles()[x][y].glyph();
    }

    /**
     * @author: Xu YangXin
     * @param {int} x 目标的x坐标
     * @param {int} y 目标的y坐标
     * @return {Color} 目标地形的颜色
     * @description: 获取地图的某个坐标上的地形的颜色
     */    
    public Color color(int x, int y) {
        return this.map.tiles()[x][y].color();
    }

    /**
     * @author: Xu YangXin
     * @return {int} 地图宽度
     * @description: 获取地图宽度
     */    
    public int width() {
        return this.map.width();
    }

    /**
     * @author: Xu YangXin
     * @return {int} 地图高度
     * @description: 获取地图高度
     */    
    public int height() {
        return this.map.height();
    }

    /**
     * @author: Xu YangXin
     * @param {int} x 目标的x坐标
     * @param {int} y 目标的y坐标
     * @return {boolean} 坐标是否在地图内部
     * @description: 判断某个坐标是否在地图内部
     */    
    public boolean inBound(int x, int y){
        return x >= 0 && x < this.map.width() && y >= 0 && y < this.map.height();
    }

    /**
     * @author: Xu YangXin
     * @param {int} x 目标的x坐标
     * @param {int} y 目标的y坐标
     * @return {*} 
     * @description: 挖掘某个坐标的地形
     */    
    public void dig(int x, int y) {
        if (tile(x, y).isDiggable()) {
            this.map.tiles()[x][y] = Tile.FLOOR;
        }
    }

    /**
     * @author: Xu YangXin
     * @param {Tile} targetTile 目标地形
     * @param {int} x 目标的x坐标
     * @param {int} y 目标的y坐标
     * @return {boolean} 是否设置地形成功
     * @description: 将某个地形设置到坐标
     */    
    public boolean setThing(Tile targetTile, int x, int y){
        if(tile(x, y) == Tile.BOUNDS)
            return false;

        this.map.tiles()[x][y] = targetTile;
        return true;
    }

    /**
     * @author: Xu YangXin
     * @param {Creature} creature 目标生物
     * @return {*}
     * @description: 将某个生物随机设置到地图的某个空区域
     */    
    public void addAtEmptyLocation(Creature creature) {
        int x;
        int y;

        do {
            x = (int) (Math.random() * this.map.width());
            y = (int) (Math.random() * this.map.height());
        } while (!tile(x, y).isGround() || this.creature(x, y) != null);

        creature.setX(x);
        creature.setY(y);
        creature.setWorld(this);

        this.addCreature(creature);
        if(creature instanceof Player){
            Player player = (Player)creature;
            this.addPlayer(player);
        }else if(creature instanceof Goblin){
            Goblin goblin = (Goblin)creature;
            this.addGoblin(goblin);
        }

    }

    /**
     * @author: Xu YangXin
     * @return {*}
     * @description: 更新世界
     */    
    public void update() {
        ArrayList<Creature> toUpdate = new ArrayList<>(this.creatures);

        for (Creature creature : toUpdate) {
            creature.update();
        }
    }

    /**
     * @author: Xu YangXin
     * @param {int} x 目标的x坐标
     * @param {int} y 目标的y坐标
     * @return {Creature} 获取到的生物
     * @description: 根据坐标获取生物
     */    
    public Creature creature(int x, int y) {
        synchronized(this.creatures){
            for (Creature c : this.creatures) {
                if (c.x() == x && c.y() == y) {
                    return c;
                }
            }
        }
        return null;
    }
    
    /**
     * @author: Xu YangXin
     * @return {List<Creature>} 生物列表
     * @description: 获取世界中所有生物的列表
     */    
    public List<Creature> getCreatures() {
        return this.creatures;
    }
    
    /**
     * @author: Xu YangXin
     * @param {int} i 生物在列表中的下标
     * @return {Creature} 目标生物
     * @description: 获取第i个生物，找不到则返回null
     */    
    public Creature getCreatureAt(int i) {
        Creature ret = null;
        synchronized(this.creatures){
            ret = this.creatures.get(i);
        }
        return ret;
    }
    
    /**
     * @author: Xu YangXin
     * @return {int} 生物数量
     * @description: 获取世界中所有生物的数量
     */    
    public int getCreaturesSize() {
        int s = 0;
        synchronized(this.creatures){
            s = this.creatures.size();
        }
        return s;
    }
    
    /**
     * @author: Xu YangXin
     * @return {List<Player>} 玩家列表
     * @description: 获取世界中的所有玩家的列表
     */    
    public List<Player> getPlayers(){
            return this.players;
    }
    
    /**
     * @author: Xu YangXin
     * @param {int} playerIndex 玩家ID
     * @return {Player}
     * @description: 根据玩家ID获取目标玩家，获取不了则返回null
     */    
    public Player searchPlayer(int playerIndex){
        for(Player player:this.players){
            if(player.playerIndex() == playerIndex){
                return player;
            }
        }
        return null;
    }

    /**
     * @author: Xu YangXin
     * @return {List<Goblin>} 妖精列表
     * @description:获取世界中的所有妖精的列表
     */    
    public List<Goblin> getGoblins(){
            return this.goblins;
    }
    
    /**
     * @author: Xu YangXin
     * @param {Creature} creature 生物
     * @return {boolean} 添加生物是否成功
     * @description: 为世界添加生物
     */    
    public boolean addCreature(Creature creature){
        if(creature == null)
            return false;
        synchronized(this.creatures){
            this.creatures.add(creature);
        }
        return true;
    }
    
    /**
     * @author: Xu YangXin
     * @param {Player} player 玩家
     * @return {boolean} 添加玩家是否成功
     * @description: 为世界添加玩家
     */    
    public boolean addPlayer(Player player){
        if(player == null)
            return false;
        synchronized(this.players){
            player.setPlayerIndex(this.players.size());
            this.players.add(player);
        }
        return true;
    }
    
    /**
     * @author: Xu YangXin
     * @param {Goblin} goblin 妖精
     * @return {boolean} 添加妖精是否成功
     * @description: 为世界添加妖精
     */    
    public boolean addGoblin(Goblin goblin){
        if(goblin == null)
            return false;
        synchronized(this.goblins){
            this.goblins.add(goblin);
        }
        return true;
    }
    
    /**
     * @author: Xu YangXin
     * @param {Creature} target 目标生物
     * @return {*}
     * @description: 将目标生物移除
     */    
    public void remove(Creature target) {
        if(target == null)
            return;
        synchronized(this.creatures){
            this.creatures.remove(target);
        }
        if(target instanceof Player){
            Player player = (Player)target;
            synchronized(this.players){
                this.players.remove(player);
            }
        }else if(target instanceof Goblin){
            Goblin goblin = (Goblin)target;
            synchronized(this.goblins){
                this.goblins.remove(goblin);
            }
        }
    }

    /**
     * @author: Xu YangXin
     * @param {Bomb} bomb 炸弹
     * @return {boolean} 添加炸弹是否成功
     * @description: 为世界添加炸弹
     */    
    public boolean addBomb(Bomb bomb){
        synchronized(this.bombs){
            this.bombs.add(bomb);
        }
        return true;
    }
    
    /**
     * @author: Xu YangXin
     * @param {Bomb} target 目标炸弹
     * @return {*}
     * @description: 移除目标炸弹
     */    
    public void removeBomb(Bomb target) {
        synchronized(this.bombs){
            this.bombs.remove(target);
        }
    }
    
    /**
     * @author: Xu YangXin
     * @return {List<Bomb>} 炸弹列表
     * @description: 获取世界中的所有炸弹的列表
     */    
    public List<Bomb> getBombs(){
        synchronized(this.bombs){
            return bombs;
        }
    }
    
    /**
     * @author: Xu YangXin
     * @param {int} x 目标的x坐标
     * @param {int} y 目标的y坐标
     * @return {Bomb} 目标炸弹
     * @description: 根据坐标获取目标炸弹，找不到则返回bull
     */    
    public Bomb getBomb(int x, int y){
        synchronized(this.bombs){
            for(Bomb bomb : bombs)
                if(bomb.x() == x && bomb.y() == y)
                    return bomb;
        }
        return null;
    }
    
    /**
     * @author: Xu YangXin
     * @return {int} 炸弹数量
     * @description: 获取世界中所有炸弹的数量
     */    
    public int getBombsSize(){
        int size;
        synchronized(this.bombs){
            size = this.bombs.size();
        }
        return size;
    }
    
    /**
     * @author: Xu YangXin
     * @param {int} i 炸弹下标
     * @return {Bomb} 目标炸弹
     * @description: 获取炸弹列表的第i个炸弹
     */    
    public Bomb getBombAt(int i){
        Bomb bomb = null;
        synchronized(this.bombs){
            bomb = this.bombs.get(i);
        }
        return bomb;
    }

    /**
     * @author: Xu YangXin
     * @param {Bullet} bullet 子弹
     * @return {boolean} 添加子弹是否成功
     * @description: 为世界添加子弹
     */    
    public boolean addBullet(Bullet bullet){
        if(this.inBound(bullet.x(),bullet.y())){
            synchronized(this.bullets){
                this.bullets.add(bullet);
            }
        }
        return true;
    }
    
    /**
     * @author: Xu YangXin
     * @param {Bullet} target 目标子弹
     * @return {*}
     * @description: 将目标子弹移除
     */    
    public void removeBullet(Bullet target) {
        synchronized(this.bullets){
            this.bullets.remove(target);
        }
    }
    
    /**
     * @author: Xu YangXin
     * @return {List<Bullet>} 子弹列表
     * @description: 获取世界中所有子弹的列表
     */    
    public List<Bullet> getBullets(){
        synchronized(this.bullets){
            return this.bullets;
        }
    }

    /**
     * @author: Xu YangXin
     * @param {AttackDot} attackDot 攻击点
     * @return {boolean} 添加攻击点是否成功
     * @description: 为世界添加一个攻击点
     */    
    public boolean addAttackDot(AttackDot attackDot){
        if( !inBound(attackDot.x(), attackDot.y()) ||   // 不在地图范围内
            !this.tile(attackDot.x(), attackDot.y()).isGround() ) // 撞着墙了
            return false;

        synchronized(this.attackDots){
            this.attackDots.add(attackDot);
        }
        Runnable runnable = new Runnable() { // 一段时间后删除攻击点
			public void run() {
				while (true) {
					try {
						Thread.sleep(attackDot.remainTime());
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
                    removeAttackDot(attackDot);
				}
			}
		};
		new Thread(runnable).start();

        return true;
    }
    
    /**
     * @author: Xu YangXin
     * @param {List<AttackDot>} attackDots 攻击点列表
     * @return {boolean} 添加攻击点列表是否成功
     * @description: 为世界添加一堆攻击点
     */    
    public boolean addAttackDot(List<AttackDot> attackDots){
        for (AttackDot attackDot : attackDots) {
            this.addAttackDot(attackDot);
        }
        return true;
    }
    
    /**
     * @author: Xu YangXin
     * @param {AttackDot} target 目标攻击点
     * @return {*}
     * @description: 将目标攻击点移除
     */    
    public void removeAttackDot(AttackDot target) {
        synchronized(this.attackDots){
            this.attackDots.remove(target);
        }
    }
    
    /**
     * @author: Xu YangXin
     * @return {List<AttackDot>} 攻击点列表
     * @description: 获取世界中所有攻击点的列表
     */    
    public List<AttackDot> getAttackDots(){
        synchronized(this.attackDots){
            return this.attackDots;
        }
    }

    /**
     * @author: Xu YangXin
     * @param {ThrowableItem} throwableItem 投掷物品
     * @return {boolean} 添加投掷物品是否成功
     * @description: 为世界添加投掷物品
     */    
    public boolean addThrowableItem(ThrowableItem throwableItem){
        synchronized(this.throwableItems){
            this.throwableItems.add(throwableItem);
            Runnable runnable = new Runnable() {
                public void run() {
                    try {
                        Thread.sleep(throwableItem.activateInterval());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    throwableItem.activate();
                }
            };
            new Thread(runnable).start();
        }
        return true;
    }
    
    /**
     * @author: Xu YangXin
     * @param {ThrowableItem} target 投目标投掷物品
     * @return {*}
     * @description: 将目标投掷物品移除
     */    
    public void removeThrowableItem(ThrowableItem target) {
        synchronized(this.throwableItems){
            this.throwableItems.remove(target);
        }
    }
    
    /**
     * @author: Xu YangXin
     * @return {List<ThrowableItem>} 投掷物品列表
     * @description: 获取世界中的所有投掷物品的列表
     */    
    public List<ThrowableItem> getThrowableItems(){
        synchronized(this.throwableItems){
            return throwableItems;
        }
    }

    /**
     * @author: Xu YangXin
     * @return {boolean} 世界是否暂停
     * @description: 查看世界是否停止
     */    
    synchronized public boolean worldIsPaused(){
        return world_pause_flag;
    }
    
    /**
     * @author: Xu YangXin
     * @param {boolean} flag 世界暂停的标志
     * @return {*}
     * @description: 设置世界是否暂停
     */    
    synchronized public void setWorldPauseFlag(boolean flag){
        world_pause_flag = flag;
    }
}