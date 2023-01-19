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
 * @LastEditTime: 2023-01-16 17:06:53
 * @LastEditors: Xu YangXin
 * @Description: 此类代表游戏中的所有生物
 * @FilePath: \jfp-poker-svg\src\main\java\cn\edu\nju\cs\game\world\creature\Creature.java
 * 此项目为南京大学《Java高级程序设计》项目
 */

package cn.edu.nju.cs.game.world.creature;

import java.awt.Color;
import java.io.Serializable;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import cn.edu.nju.cs.game.world.item.World;
import cn.edu.nju.cs.game.world.item.DoorPairs;
import cn.edu.nju.cs.game.world.item.Tile;
import cn.edu.nju.cs.game.world.weapon.bomb.Bomb;
import cn.edu.nju.cs.game.world.weapon.Weapon;
import cn.edu.nju.cs.game.world.weapon.WeaponFactory;

public class Creature implements Serializable{
    private World world; // 世界
    private int x;// 坐标x
    private int y;// 坐标y
    private char glyph;// 字符
    private Color color;// 颜色
    transient protected CreatureAI ai;// AI
    private int maxHP;// 最大血量
    private int hp;// 当前血量
    private int attackValue;// 手刀攻击力
    private int defenseValue;// 防御力
    private int visionRadius;// 视野
    private Bomb bomb;// 炸弹
    private Direction direction; // 方向
    protected int moveSpeedInterval; // 生物移动速度的时间间隔，单位毫秒
    private boolean visible;
    volatile protected boolean moveActive; // 生物当前可移动与否
    private static Lock moveLock = new ReentrantLock(); // 序列化移动行为
    protected WeaponFactory weaponFactory; // 武器工厂
    transient protected Timer moveTimer; // 生物移动计时器

    /**
     * @author: Xu YangXin
     * @param {World} world 生物所处世界
     * @param {char} glyph 生物的字符形象
     * @param {Color} color 生物的颜色
     * @param {int} maxHP 生物的最大血量
     * @param {int} attack 生物的手刀攻击力
     * @param {int} defense 生物的护甲值
     * @param {int} visionRadius 生物的视野
     * @param {int} moveSpeedInterval 生物的移动间隔
     * @return {*}
     * @description: 构造器
     */
    public Creature(World world, char glyph, Color color, int maxHP,
                    int attack, int defense, int visionRadius, int moveSpeedInterval) {
        this.world = world;
        this.glyph = glyph;
        this.color = color;
        this.maxHP = maxHP;
        this.hp = maxHP;
        this.attackValue = attack;
        this.defenseValue = defense;
        this.visionRadius = visionRadius;
        this.bomb = new Bomb(world, this, this.x(), this.y(), Tile.BOMB.glyph(), color, 100);
        this.direction = Direction.Right;
        this.weaponFactory = new WeaponFactory(this);
        this.moveSpeedInterval = moveSpeedInterval;
        this.moveTimer = null;
        this.moveActive = true;
        this.visible = true; // 生物一开始是可见的

        this.startMove();
    }

    /**
     * @author: Xu YangXin
     * @return {World} 生物所处的世界
     * @description: 读取生物所处的世界
     */    
    public World world(){
        return this.world;
    }
    
    /**
     * @author: Xu YangXin
     * @param {World} world 世界
     * @return {*}
     * @description: 设置生物所处的世界
     */    
    public void setWorld(World world){
        this.world = world;
    }

    /**
     * @author: Xu YangXin
     * @param {int} x 坐标
     * @return {*}
     * @description: 设置生物的位置的X坐标
     */    
    public void setX(int x) {
        this.x = x;
    }
    
    /**
     * @author: Xu YangXin
     * @return {int} 生物的x坐标
     * @description: 读取生物的x坐标
     */    
    public int x() {
        return x;
    }

    /**
     * @author: Xu YangXin
     * @param {int} y 坐标
     * @return {*}
     * @description: 设置生物的y坐标
     */    
    public void setY(int y) {
        this.y = y;
    }
    
    /**
     * @author: Xu YangXin
     * @return {int} 生物的y坐标
     * @description: 读取生物的y坐标
     */    
    public int y() {
        return y;
    }

    
    /**
     * @author: Xu YangXin
     * @return {char} 生物的字符形象
     * @description: 读取生物的字符形象
     */    
    public char glyph() {
        return this.glyph;
    }

    /**
     * @author: Xu YangXin
     * @return {Color} 生物颜色
     * @description: 读取生物颜色
     */    
    public Color color() {
        return this.color;
    }

    /**
     * @author: Xu YangXin
     * @param {CreatureAI} ai
     * @return {*}
     * @description: 设置生物的AI
     */    
    public void setAI(CreatureAI ai) {
        this.ai = ai;
    }

    /**
     * @author: Xu YangXin
     * @return {int}
     * @description: 读取生物的最大血量
     */    
    public int maxHP() {
        return this.maxHP;
    }

    /**
     * @author: Xu YangXin
     * @return {int} 生物的当前血量
     * @description: 读取生物的当前血量
     */    
    synchronized public int hp() {
        return this.hp;
    }
    
    /**
     * @author: Xu YangXin
     * @param {int} amount 血量的修改量
     * @return {*}
     * @description: 修改当前血量值
     */    
    synchronized public void modifyHP(int amount) {
        this.hp = Math.min(this.hp+amount, this.maxHP);

        if (this.hp < 1) {

            if(this.ai instanceof AppleAI){ // Apple会随机掉落药水和道具
                this.world.setThing(Tile.randomDropItem(), this.x, this.y);
            }
            else if(this.ai instanceof PlayerAI){ // 玩家以及妖精会掉落自己的当前武器
                Weapon curWeapon = this.weaponFactory.curWeapon();
                if(curWeapon != null)
                    this.world.setThing(
                        this.weaponFactory.curWeapon().image(),
                        this.x, this.y);
            }
            world.remove(this);
        }
    }

    /**
     * @author: Xu YangXin
     * @return {*}
     * @description: 增加最大血量值
     */    
    synchronized public void addHP() {
        if(this.hp >= this.maxHP){
            this.maxHP += 50;
            this.hp = this.maxHP;
            return;
        }
        this.hp += (int)this.maxHP/3;
        if(this.hp > this.maxHP)
            this.hp = this.maxHP;
    }

    /**
     * @author: Xu YangXin
     * @return {int}
     * @description: 读取生物的手刀攻击值
     */    
    public int attackValue() {
        return this.attackValue;
    }

    /**
     * @author: Xu YangXin
     * @return {*}
     * @description: 生物手刀攻击力提升20%
     */
    public void attackUpgrade() {
        this.attackValue = (int)(this.attackValue*1.2);
    }

    /**
     * @author: Xu YangXin
     * @return {int} 生物的护甲值
     * @description: 读取生物的护甲值
     */    
    public int defenseValue() {
        return this.defenseValue;
    }

    /**
     * @author: Xu YangXin
     * @return {int} 生物的视野
     * @description: 读取生物的视野
     */    
    public int visionRadius() {
        return this.visionRadius;
    }
    
    /**
     * @author: Xu YangXin
     * @return {*}
     * @description: 生物的视野+1
     */    
    public void visionRadiusUpgrade(){
        this.visionRadius++;
    }
    
    /**
     * @author: Xu YangXin
     * @param {int} v 生物视野的修改值
     * @return {*}
     * @description: 修改生物视野
     */    
    public void visionRadiusUpgrade(int v){
        this.visionRadius += v;
    }

    /**
     * @author: Xu YangXin
     * @return {*}
     * @description: 提升生物的炸弹攻击范围
     */    
    public void bombExpandRange(){
        this.bomb.expandRange();
    }

    /**
     * @author: Xu YangXin
     * @return {boolean} 炸弹是否设置成功
     * @description: 在生物所在处安装炸弹
     */    
    public boolean setBomb(){
        this.notify("You set a bomb '%s' at ( %d , %d ).", bomb.glyph(), x, y);

        // start activate, bomb will be active after 1s
        Bomb newBomb = new Bomb(this.bomb, this.x, this.y);
        world.addBomb(newBomb);
        world.setThing(Tile.BOMB, this.x, this.y);
        Runnable runnable = new Runnable() {
        	public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        	    newBomb.activate();
        	}
        };
        Thread thread = new Thread(runnable);
        thread.start();
        return true;
    }
    
    /**
     * @author: Xu YangXin
     * @return {Bomb}
     * @description: 读取生物的炸弹样品
     */    
    public Bomb bomb(){
        return this.bomb;
    }

    /**
     * @author: Xu YangXin
     * @return {Direction} 生物的当前方向
     * @description: 读取生物的当前方向
     */    
    synchronized public Direction direction(){
        return this.direction;
    }
    
    /**
     * @author: Xu YangXin
     * @return {boolean} 设置方向是否成功
     * @description: 设置生物的当前方向
     */    
    synchronized public boolean setDirection(Direction direction){
        this.direction = direction;
        return true;
    }

    /**
     * @author: Xu YangXin
     * @return {int} 生物的移动间隔时间
     * @description: 读取生物的移动间隔时间
     */    
    public int moveSpeedInterval(){
        return this.moveSpeedInterval;
    }
    
    /**
     * @author: Xu YangXin
     * @return {int} 生物的速度
     * @description: 读取生物的速度
     */    
    public int moveSpeed(){
        if(moveSpeedInterval > 0)
            return (int)(10000/moveSpeedInterval);
        else
            return 0;
    }
    
    /**
     * @author: Xu YangXin
     * @return {boolean} 生物当前是否可以移动
     * @description: 读取生物当前是否可以移动
     */    
    public boolean moveActive(){
        return this.moveActive;
    }
    
    /**
     * @author: Xu YangXin
     * @param {boolean} moveActive 是否可以移动
     * @return {*}
     * @description: 设置移动与否
     */    
    public void setActive(boolean moveActive){
        this.moveActive = moveActive;
    }
    
    /**
     * @author: Xu YangXin
     * @return {*}
     * @description: 冰冻生物
     */    
    public void freezon(){
        this.moveActive = false;
        return;
    }
    
    /**
     * @author: Xu YangXin
     * @param {int} interval 冰冻生物市时长
     * @return {*}
     * @description: 冰冻生物一段时间
     */    
    public void freezon(int interval){
        try {
            this.freezon();
            Runnable runnable = new Runnable() { // 一段时间后删除攻击点
                public void run() {
                    try {
                        Thread.sleep(interval);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    moveActive = true;
                    startMove();
                }
            };
            new Thread(runnable).start();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return;
    }
    
    /**
     * @author: Xu YangXin
     * @return {*}
     * @description: 生物速度提升10%
     */    
    public void speedUpgrade(){
        double temp = this.moveSpeedInterval*0.9;
        this.moveSpeedInterval = (int)Math.round(temp);
        this.startMove();
        return;
    }

    /**
     * @author: Xu YangXin
     * @param {int} wx 目标的x坐标
     * @param {int} wy 目标的y坐标
     * @return {boolean} 生物是否可以看见目标
     * @description: 目标是否可以看见某个坐标
     */    
    public boolean canSee(int wx, int wy) {
        return ai.canSee(wx, wy);
    }

    /**
     * @author: Xu YangXin
     * @param {int} x 目标的x坐标
     * @param {int} y 目标的y坐标
     * @return {*}
     * @description: 生物和某个坐标之间的距离
     */    
    public int distance(int x, int y) {
        return (int)Math.round(Math.sqrt((this.x - x) * (this.x - x) + (this.y - y)*(this.y - y)));
    }

    /**
     * @author: Xu YangXin
     * @param {int} wx 目标的x坐标
     * @param {int} wy 目标的y坐标
     * @return {Tile} 目标的图像
     * @description: 读取世界的某个坐标上的内容
     */    
    public Tile tile(int wx, int wy) {
        return world.tile(wx, wy);
    }

    /**
     * @author: Xu YangXin
     * @param {int} wx 目标的x坐标
     * @param {int} wy 目标的y坐标
     * @return {*}
     * @description: 生物挖掘目标一次
     */    
    public void dig(int wx, int wy) {
        world.dig(wx, wy);
    }

    /**
     * @author: Xu YangXin
     * @param {int} mx 移动的相对x值
     * @param {int} my 移动的相对y值
     * @return {*}
     * @description: 生物移动一段距离
     */    
    synchronized public void moveBy(int mx, int my) {
        if(!this.moveActive)
            return;
        int oldX = x, oldY = y;
        Creature other = world.creature(x + mx, y + my);

        if (other == null) {
            // 要移动啦！
            moveLock.lock();
            try {
                ai.onEnter(x + mx, y + my, world.tile(x + mx, y + my));
            } finally{
                moveLock.unlock(); // 必须使用finally释放锁
            }
        } else {
            attack(other);
        }

        if(x != oldX || y != oldY){ // 移动了，不在原地
            this.freezon(); // 冰冻一小段时间，控制移速
            this.transfer();// 检查是否传送
        }

        return;
    }

    /**
     * @author: Xu YangXin
     * @param {Creature} other 另一个生物
     * @return {*}
     * @description: 生物手刀攻击另一个生物
     */    
    public void attack(Creature other) {
        if(other == this)
            return;

        int damage = Math.max(0, this.attackValue() - other.defenseValue());
        damage = (int) (Math.random() * damage) + 1;

        other.modifyHP(-damage);
        this.chargeEnergy();

        this.notify("You attack the '%s' for %d damage.", other.glyph, damage);
        other.notify("The '%s' attacks you for %d damage.", glyph, damage);
    }

    /**
     * @author: Xu YangXin
     * @return {WeaponFactory} 生产生物自己的工厂
     * @description: 读取生成生物自己的工厂
     */    
    public WeaponFactory weaponFactory(){
        return this.weaponFactory;
    }

    /**
     * @author: Xu YangXin
     * @return {*}
     * @description: 生物更新自己
     */    
    public void update() {
        this.ai.onUpdate();
    }

    /**
     * @author: Xu YangXin
     * @param {int} x 目标x坐标
     * @param {int} y 目标y坐标
     * @return {boolean} 是否可以进入
     * @description: 判断生物是否可以进入某个坐标
     */    
    public boolean canEnter(int x, int y) {
        return world.tile(x, y).isGround();
    }

    /**
     * @author: Xu YangXin
     * @param {String} message 消息
     * @param {Object...} params format信息的参数
     * @return {*}
     * @description: 提醒生物消息
     */    
    public void notify(String message, Object... params) {
        ai.onNotify(String.format(message, params));
    }

    /**
     * @author: Xu YangXin
     * @return {*}
     * @description: 给生物当前的武器充能一次
     */    
    public void chargeEnergy(){
        this.weaponFactory.curWeapon().chargeEnergy();
        return;
    }

    /**
     * @author: Xu YangXin
     * @return {boolean} 传送是否成功
     * @description: 生物被传送到传送门的另一端
     */    
    public boolean transfer(){
        if(world.tile(this.x, this.y) != Tile.DOOR) // 没踩在传送门上
            return false;
        DoorPairs.Door targetDoor = world.doorPairs().search(this.x, this.y);
        if(targetDoor == null)
            return false;

        this.x = targetDoor.x();
        this.y = targetDoor.y();
        return true;
    }

    /**
     * @author: Xu YangXin
     * @return {boolean} 生物是否可见
     * @description: 读取生物是否可见
     */    
    public boolean isVisible(){
        return this.visible;
    }
    
    /**
     * @author: Xu YangXin
     * @return {*}
     * @description: 隐藏生物
     */    
    public void hide(){
        this.visible = false;
    }
    
    /**
     * @author: Xu YangXin
     * @return {*}
     * @description: 暴露生物
     */    
    public void expose(){
        this.visible = true;
    }

    /**
     * @author: Xu YangXin
     * @return {*}
     * @description: 生物开始移动
     */    
    public void startMove(){
        if(this.moveTimer != null)
            this.moveTimer.cancel();

        if(this.moveSpeedInterval > 0) // 传入参数大于零时表示可以移动
        {
            this.moveTimer = new Timer();
            this.moveTimer.schedule(
                new TimerTask() {
                    public void run() {
                        if(world().worldIsPaused())
                            return;
                        moveActive = true;
                    }
                }, this.moveSpeedInterval , this.moveSpeedInterval);
        }
    }
}
