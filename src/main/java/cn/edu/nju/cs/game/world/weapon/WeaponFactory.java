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
 * @LastEditTime: 2023-01-16 21:50:24
 * @LastEditors: Xu YangXin
 * @Description: 此类为生产武器的武器工厂类
 * @FilePath: \jfp-poker-svg\src\main\java\cn\edu\nju\cs\game\world\weapon\WeaponFactory.java
 * 此项目为南京大学《Java高级程序设计》项目
 */

package cn.edu.nju.cs.game.world.weapon;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cn.edu.nju.cs.game.world.creature.Creature;
import cn.edu.nju.cs.game.world.item.Tile;
import cn.edu.nju.cs.game.world.weapon.bomb.BombGlove;
import cn.edu.nju.cs.game.world.weapon.gun.*;
import cn.edu.nju.cs.game.world.weapon.melee.Baseball;
import cn.edu.nju.cs.game.world.weapon.melee.BaseballBat;
import cn.edu.nju.cs.game.world.weapon.melee.Torch;
import cn.edu.nju.cs.game.world.weapon.throwable.Detonator;
import cn.edu.nju.cs.game.world.weapon.throwable.DetonatorThrower;
import cn.edu.nju.cs.game.world.weapon.throwable.ThrowableWeapon;

public class WeaponFactory implements Serializable{

    private Creature master;
    private List<Weapon> weapons;
    private Weapon currentWeapon;
    private int currentWeaponIndex;

    /**
     * @author: Xu YangXin
     * @param {Creature} master 主人生物
     * @return {*}
     * @description: 构造器
     */    
    public WeaponFactory(Creature master) {
        this.master = master;
        weapons = new ArrayList<>();
        currentWeapon = null;
        currentWeaponIndex = -1;
    }

    /**
     * @author: Xu YangXin
     * @return {Creature} 主人
     * @description: 获取武器工厂的主人
     */    
    public Creature master(){
        return this.master;
    }

    /**
     * @author: Xu YangXin
     * @return {List<Weapon>} 武器列表
     * @description: 获取武器工厂生成的一系列武器
     */    
    public List<Weapon> weapons(){
        return this.weapons;
    }
    
    /**
     * @author: Xu YangXin
     * @return {int} 武器数量
     * @description: 获取武器数量
     */    
    public int weaponSize(){
        return this.weapons().size();
    }

    /**
     * @author: Xu YangXin
     * @return {Weapon} 当前武器
     * @description: 获取当前武器
     */    
    public Weapon curWeapon(){
        return this.currentWeapon;
    }

    /**
     * @author: Xu YangXin
     * @return {*}
     * @description: 开始所有武器的刷新
     */    
    public void startAllWeapons(){
        for(Weapon weapon : weapons){
            weapon.startRefresh();      // 武器开始刷新
            if(weapon instanceof Gun){  // 枪械武器开始刷新子弹
                Gun gun = (Gun)weapon;
                gun.startAddBulletTimer();
            }
            else if(weapon instanceof ThrowableWeapon){ // 抛掷类武器开始刷新扔物
                ThrowableWeapon throwableWeapon = (ThrowableWeapon)weapon;
                throwableWeapon.startAddItemTimer();
            }
        }
    }

    /**
     * @author: Xu YangXin
     * @return {Tile} 武器形象
     * @description: 生成随机的武器图片
     */    
    public static Tile newRandomWeapon(){
        Tile randomWeapon = null;

        switch (new Random().nextInt(7)) {
            case 0:
                randomWeapon = Tile.NORMAL_GUN;
                break;
            case 1:
                randomWeapon = Tile.SHOT_GUN;
                break;
            case 2:
                randomWeapon = Tile.SNIPER_GUN;
                break;
            case 3:
                randomWeapon = Tile.LASER_GUN;
                break;
            case 4:
                randomWeapon = Tile.BASEBALL_BAT;
                break;
            case 5:
                randomWeapon = Tile.DETONATOR_THROWER;
                break;
            case 6:
                randomWeapon = Tile.TORCH;
                break;
        }

        return randomWeapon;
    }

    /**
     * @author: Xu YangXin
     * @param {Tile} image 武器图片
     * @return {Weapon} 武器
     * @description: 根据武器图片生成对应的武器对象
     */    
    public void newWeapon(Tile image){
        if(image == null)
            return;
        switch (image) {
            case NORMAL_GUN:
                this.newNormalGun();
                break;
            case SNIPER_GUN:
                this.newSniperGun();
                break;
            case LASER_GUN:
                this.newLaserGun();
                break;
            case SHOT_GUN:
                this.newShotGun();
                break;
            case BASEBALL_BAT:
                this.newBaseballBat();
                break;
            case DETONATOR_THROWER:
                this.newDetonatorThrower();
                break;
            default:
                break;
        }
    }

    /**
     * @author: Xu YangXin
     * @return {*}
     * @description: 生成普通枪支
     */    
    public void newNormalGun(){
        this.weapons.add(new NormalGun(this,
                        new NormalBullet(NormalGun.RANGE, master.direction(), master)
                        )
                        );
        if(currentWeapon == null){
            currentWeapon = weapons.get(0);
            currentWeaponIndex = 0;
        }
    }

    /**
     * @author: Xu YangXin
     * @return {*}
     * @description: 生成霰弹枪
     */    
    public void newShotGun(){
        this.weapons.add(new ShotGun(this,
                        new ShotBullet(ShotGun.RANGE, master.direction(), master)
                        )
                        );
        if(currentWeapon == null){
            currentWeapon = weapons.get(0);
            currentWeaponIndex = 0;
        }
    }

    /**
     * @author: Xu YangXin
     * @return {*}
     * @description: 生成狙击步枪
     */    
    public void newSniperGun(){
        this.weapons.add(new SniperGun(this,
                        new SniperBullet(SniperGun.RANGE, master.direction(), master)
                        )
                        );
        if(currentWeapon == null){
            currentWeapon = weapons.get(0);
            currentWeaponIndex = 0;
        }
    }

    /**
     * @author: Xu YangXin
     * @return {*}
     * @description: 生成棒球棍
     */    
    public void newBaseballBat(){
        this.weapons.add(new BaseballBat(this,
                        new Baseball(this.master)
                        )
                        );
        if(currentWeapon == null){
            currentWeapon = weapons.get(0);
            currentWeaponIndex = 0;
        }
    }

    /**
     * @author: Xu YangXin
     * @return {*}
     * @description: 生成爆竹投掷器
     */    
    public void newDetonatorThrower(){
        this.weapons.add(new DetonatorThrower(this,
                        new Detonator(this.master))
                        );
        if(currentWeapon == null){
            currentWeapon = weapons.get(0);
            currentWeaponIndex = 0;
        }
    }

    /**
     * @author: Xu YangXin
     * @return {*}
     * @description: 生成激光枪
     */    
    public void newLaserGun(){
        this.weapons.add(new LaserGun(this,
                        new LaserBeam(0, 0, master.direction(), master))
                        );
        if(currentWeapon == null){
            currentWeapon = weapons.get(0);
            currentWeaponIndex = 0;
        }
    }

    /**
     * @author: Xu YangXin
     * @return {*}
     * @description: 生成炸弹手套
     */    
    public void newBombGlove(){
        this.weapons.add(new BombGlove(this));
        if(currentWeapon == null){
            currentWeapon = weapons.get(0);
            currentWeaponIndex = 0;
        }
    }

    /**
     * @author: Xu YangXin
     * @return {*}
     * @description: 生成火炬
     */    
    public void newTorch(){
        this.weapons.add(new Torch(this));
        if(currentWeapon == null){
            currentWeapon = weapons.get(0);
            currentWeaponIndex = 0;
        }
    }

    /**
     * @author: Xu YangXin
     * @return {boolean} 切换武器是否成功
     * @description: 切换武器
     */    
    public boolean switchWeapon(){
        if(this.weapons.isEmpty()){  //0个
            currentWeaponIndex = -1;
            currentWeapon = null;
            return false;
        }

        if(this.weapons.size() == 1){//1个
            currentWeaponIndex = 0;
            currentWeapon = this.weapons.get(0);
            return false;
        }

        Weapon oldWeapon = this.currentWeapon;

        if(currentWeaponIndex >= this.weapons.size()-1)
            currentWeaponIndex = 0;
        else
            currentWeaponIndex++;

        currentWeapon = this.weapons.get(currentWeaponIndex);

        if(this.currentWeapon instanceof Torch && !(oldWeapon instanceof Torch)){ // 从别的武器切到火炬
            Torch torch = (Torch)(this.currentWeapon);
            this.master().visionRadiusUpgrade(torch.range());
        }
        else if(!(this.currentWeapon instanceof Torch) && oldWeapon instanceof Torch){// 从火炬切换为别的武器
            Torch torch = (Torch)(oldWeapon);
            this.master().visionRadiusUpgrade(-torch.range());
        }

        return true;
    }
}