package cn.edu.nju.cs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Color;

import org.junit.Test;

import cn.edu.nju.cs.game.world.creature.Creature;
import cn.edu.nju.cs.game.world.creature.bros.Player;
import cn.edu.nju.cs.game.world.creature.bros.ThirdBro;
import cn.edu.nju.cs.game.world.item.Tile;
import cn.edu.nju.cs.game.world.item.World;
import cn.edu.nju.cs.game.world.weapon.WeaponFactory;
import cn.edu.nju.cs.game.world.weapon.bomb.BombGlove;
import cn.edu.nju.cs.game.world.weapon.gun.LaserGun;
import cn.edu.nju.cs.game.world.weapon.gun.NormalGun;
import cn.edu.nju.cs.game.world.weapon.gun.ShotGun;
import cn.edu.nju.cs.game.world.weapon.gun.SniperGun;
import cn.edu.nju.cs.game.world.weapon.melee.BaseballBat;
import cn.edu.nju.cs.game.world.weapon.melee.Torch;
import cn.edu.nju.cs.game.world.weapon.throwable.DetonatorThrower;

public class WeaponFactoryTest {
    private Creature creature;
    private WeaponFactory weaponFactory;

    class EmptyPlayer extends Player{
        public EmptyPlayer(){
            super(null,' ',Color.red,10,10,10,10,10);
        }
    }


    @Test
    /**
     * 测试武器工厂的切换武器功能
     */
    public void testWeaponSwitch(){
        this.creature = new ThirdBro((World)null);
        this.weaponFactory = this.creature.weaponFactory();

        assertEquals(this.weaponFactory.curWeapon().name() , BaseballBat.NAME);
        this.weaponFactory.switchWeapon();
        assertEquals(this.weaponFactory.curWeapon().name() , BombGlove.NAME);

        this.weaponFactory.newDetonatorThrower();
        this.weaponFactory.switchWeapon();
        assertEquals(this.weaponFactory.curWeapon().name() , DetonatorThrower.NAME);

        this.weaponFactory.newLaserGun();
        this.weaponFactory.switchWeapon();
        assertEquals(this.weaponFactory.curWeapon().name() , LaserGun.NAME);

        this.weaponFactory.newNormalGun();
        this.weaponFactory.switchWeapon();
        assertEquals(this.weaponFactory.curWeapon().name() , NormalGun.NAME);

        this.weaponFactory.newShotGun();
        this.weaponFactory.switchWeapon();
        assertEquals(this.weaponFactory.curWeapon().name() , ShotGun.NAME);

        this.weaponFactory.newSniperGun();
        this.weaponFactory.switchWeapon();
        assertEquals(this.weaponFactory.curWeapon().name() , SniperGun.NAME);

        this.weaponFactory.newTorch();
        this.weaponFactory.switchWeapon();
        assertEquals(this.weaponFactory.curWeapon().name() , Torch.NAME);

        this.weaponFactory.switchWeapon();
        assertEquals(this.weaponFactory.curWeapon().name(), BaseballBat.NAME);
    }

    @Test
    /**
     * 测试根据图片生成对应武器
     */
    public void testNewWeapon(){
        this.creature = new EmptyPlayer();
        this.weaponFactory = this.creature.weaponFactory();

        this.weaponFactory.newWeapon(null);
        assertFalse(weaponFactory.switchWeapon());
        assertEquals(weaponFactory.curWeapon(), null);

        this.weaponFactory.newWeapon(Tile.BASEBALL_BAT);
        assertFalse(weaponFactory.switchWeapon());
        assertEquals(weaponFactory.curWeapon().name(), BaseballBat.NAME);

        this.weaponFactory.newWeapon(Tile.NORMAL_GUN);
        assertTrue(weaponFactory.switchWeapon());
        assertEquals(weaponFactory.curWeapon().name(), NormalGun.NAME);

        this.weaponFactory.newWeapon(Tile.SHOT_GUN);
        assertTrue(weaponFactory.switchWeapon());
        assertEquals(weaponFactory.curWeapon().name(), ShotGun.NAME);

        this.weaponFactory.newWeapon(Tile.LASER_GUN);
        assertTrue(weaponFactory.switchWeapon());
        assertEquals(weaponFactory.curWeapon().name(), LaserGun.NAME);

        this.weaponFactory.newWeapon(Tile.SNIPER_GUN);
        assertTrue(weaponFactory.switchWeapon());
        assertEquals(weaponFactory.curWeapon().name(), SniperGun.NAME);

        this.weaponFactory.newWeapon(Tile.DETONATOR_THROWER);
        assertTrue(weaponFactory.switchWeapon());
        assertEquals(weaponFactory.curWeapon().name(), DetonatorThrower.NAME);

        this.weaponFactory.newWeapon(Tile.FLOOR);
        assertTrue(weaponFactory.switchWeapon());
        assertEquals(weaponFactory.curWeapon().name(), BaseballBat.NAME);

    }

    @Test
    public void testNewBaseballBat(){
        this.creature = new EmptyPlayer();
        this.weaponFactory = this.creature.weaponFactory();
        assertEquals(weaponFactory.curWeapon(), null);
        this.weaponFactory.newBaseballBat();
        assertEquals(weaponFactory.curWeapon().name(), BaseballBat.NAME);
    }
    @Test
    public void testNewNormalGun(){
        this.creature = new EmptyPlayer();
        this.weaponFactory = this.creature.weaponFactory();
        assertEquals(weaponFactory.curWeapon(), null);
        this.weaponFactory.newNormalGun();
        assertEquals(weaponFactory.curWeapon().name(), NormalGun.NAME);
    }
    @Test
    public void testNewShotGun(){
        this.creature = new EmptyPlayer();
        this.weaponFactory = this.creature.weaponFactory();
        assertEquals(weaponFactory.curWeapon(), null);
        this.weaponFactory.newShotGun();
        assertEquals(weaponFactory.curWeapon().name(), ShotGun.NAME);
    }
    @Test
    public void testNewSniperGun(){
        this.creature = new EmptyPlayer();
        this.weaponFactory = this.creature.weaponFactory();
        assertEquals(weaponFactory.curWeapon(), null);
        this.weaponFactory.newSniperGun();
        assertEquals(weaponFactory.curWeapon().name(), SniperGun.NAME);
    }
    @Test
    public void testNewDetonatorThrower(){
        this.creature = new EmptyPlayer();
        this.weaponFactory = this.creature.weaponFactory();
        assertEquals(weaponFactory.curWeapon(), null);
        this.weaponFactory.newDetonatorThrower();
        assertEquals(weaponFactory.curWeapon().name(), DetonatorThrower.NAME);
    }
    @Test
    public void testNewLaserGun(){
        this.creature = new EmptyPlayer();
        this.weaponFactory = this.creature.weaponFactory();
        assertEquals(weaponFactory.curWeapon(), null);
        this.weaponFactory.newLaserGun();
        assertEquals(weaponFactory.curWeapon().name(), LaserGun.NAME);
    }
    @Test
    public void testNewBombGlove(){
        this.creature = new EmptyPlayer();
        this.weaponFactory = this.creature.weaponFactory();
        assertEquals(weaponFactory.curWeapon(), null);
        this.weaponFactory.newBombGlove();
        assertEquals(weaponFactory.curWeapon().name(), BombGlove.NAME);
    }
    @Test
    public void testNewTorch(){
        this.creature = new EmptyPlayer();
        this.weaponFactory = this.creature.weaponFactory();
        assertEquals(weaponFactory.curWeapon(), null);
        this.weaponFactory.newTorch();
        assertEquals(weaponFactory.curWeapon().name(), Torch.NAME);
    }
}
