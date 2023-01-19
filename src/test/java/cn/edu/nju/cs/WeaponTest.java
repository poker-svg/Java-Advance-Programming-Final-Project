package cn.edu.nju.cs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import cn.edu.nju.cs.game.asciiPanel.AsciiPanel;
import cn.edu.nju.cs.game.world.creature.Direction;
import cn.edu.nju.cs.game.world.creature.PlayerAI;
import cn.edu.nju.cs.game.world.creature.bros.Player;
import cn.edu.nju.cs.game.world.item.Boxes;
import cn.edu.nju.cs.game.world.item.DoorPairs;
import cn.edu.nju.cs.game.world.item.FirePiles;
import cn.edu.nju.cs.game.world.item.Grasses;
import cn.edu.nju.cs.game.world.item.Tile;
import cn.edu.nju.cs.game.world.item.World;
import cn.edu.nju.cs.game.world.weapon.Weapon;
import cn.edu.nju.cs.game.world.weapon.bomb.BombGlove;
import cn.edu.nju.cs.game.world.weapon.gun.*;
import cn.edu.nju.cs.game.world.weapon.melee.BaseballBat;
import cn.edu.nju.cs.game.world.weapon.throwable.DetonatorThrower;


public class WeaponTest {
    private World world;
    private Player player;
    private Weapon curWeapon;
    private Tile tile[][];

    class EmptyPlayer extends Player{
        public EmptyPlayer(World world){
            super(world,' ',AsciiPanel.black,10,10,10,10,10);
        }
    }

    @Before
    public void prepare(){
        tile = new Tile[10][10];
        for(int i = 0; i < 10; i++)
            for(int j = 0; j < 10; j++)
                tile[i][j] = Tile.FLOOR;

        world = new World(tile, new DoorPairs(10, 10),new Grasses(10, 10),new Boxes(10, 10), new FirePiles(10, 10));
        player = new EmptyPlayer(world);
        player.setAI(new PlayerAI(player, new ArrayList<String>()));
        player.setX(5);
        player.setY(5);
    }

    @Test
    public void testNormalGun(){
        this.player.weaponFactory().newNormalGun();
        curWeapon = this.player.weaponFactory().curWeapon();
        assertEquals(curWeapon.name(), NormalGun.NAME);

        assertFalse(curWeapon.attack());

        NormalGun normalGun = (NormalGun)curWeapon;
        normalGun.setActive(true);
        normalGun.addBullet();
        assertTrue(curWeapon.attack());
        assertEquals(world.getBullets().size(), 1);

        assertFalse(normalGun.bigAttack());
        normalGun.setActive(true);
        normalGun.addBullet();
        normalGun.chargeEnergy();
        assertFalse(normalGun.bigAttack());

        int remainChargeTime = normalGun.maxEnergy()-normalGun.curEnergy();
        for(int i = 0; i < remainChargeTime; i++)
            normalGun.chargeEnergy();
        assertTrue(normalGun.bigAttack());
        assertEquals(world.getBullets().size(), 2);


        // 其余方向：
        this.player.setDirection(Direction.Left);
        normalGun.setActive(true);
        normalGun.addBullet();
        assertTrue(curWeapon.attack());
        assertEquals(world.getBullets().size(), 3);

        this.player.setDirection(Direction.Up);
        normalGun.setActive(true);
        normalGun.addBullet();
        assertTrue(curWeapon.attack());
        assertEquals(world.getBullets().size(), 4);

        this.player.setDirection(Direction.Down);
        normalGun.setActive(true);
        normalGun.addBullet();
        assertTrue(curWeapon.attack());
        assertEquals(world.getBullets().size(), 5);

        int size = normalGun.maxBulletAmount() - normalGun.remainingBullet();
        for(int i = 0; i <= size; i++)
            normalGun.addBullet();
        assertEquals(normalGun.remainingBullet(), normalGun.maxBulletAmount());
    }

    @Test
    public void testShotGun(){
        this.player.weaponFactory().newShotGun();
        curWeapon = this.player.weaponFactory().curWeapon();
        assertEquals(curWeapon.name(), ShotGun.NAME);

        assertFalse(curWeapon.attack());

        ShotGun shotGun = (ShotGun)curWeapon;
        shotGun.setActive(true);
        shotGun.addBullet();
        assertTrue(curWeapon.attack());
        assertEquals(world.getBullets().size(), 1*3);

        assertFalse(shotGun.bigAttack());
        shotGun.setActive(true);
        shotGun.addBullet();
        shotGun.chargeEnergy();
        assertFalse(shotGun.bigAttack());

        int remainChargeTime = shotGun.maxEnergy()-shotGun.curEnergy();
        for(int i = 0; i < remainChargeTime; i++)
            shotGun.chargeEnergy();
        assertTrue(shotGun.bigAttack());
        assertEquals(world.getBullets().size(), 1*3+8);


        // 其余方向：
        this.player.setDirection(Direction.Left);
        shotGun.setActive(true);
        shotGun.addBullet();
        assertTrue(curWeapon.attack());
        assertEquals(world.getBullets().size(), 2*3+8);

        this.player.setDirection(Direction.Up);
        shotGun.setActive(true);
        shotGun.addBullet();
        assertTrue(curWeapon.attack());
        assertEquals(world.getBullets().size(), 3*3+8);

        this.player.setDirection(Direction.Down);
        shotGun.setActive(true);
        shotGun.addBullet();
        assertTrue(curWeapon.attack());
        assertEquals(world.getBullets().size(), 4*3+8);

        int size = shotGun.maxBulletAmount() - shotGun.remainingBullet();
        for(int i = 0; i <= size; i++)
            shotGun.addBullet();
        assertEquals(shotGun.remainingBullet(), shotGun.maxBulletAmount());
    }

    @Test
    public void testSniperGun(){
        this.player.weaponFactory().newSniperGun();
        curWeapon = this.player.weaponFactory().curWeapon();
        assertEquals(curWeapon.name(), SniperGun.NAME);

        assertFalse(curWeapon.attack());

        SniperGun sniperGun = (SniperGun)curWeapon;
        sniperGun.setActive(true);
        sniperGun.addBullet();
        assertTrue(curWeapon.attack());
        assertEquals(world.getBullets().size(), 1);

        assertFalse(sniperGun.bigAttack());
        sniperGun.setActive(true);
        sniperGun.addBullet();
        sniperGun.chargeEnergy();
        assertFalse(sniperGun.bigAttack());

        int remainChargeTime = sniperGun.maxEnergy()-sniperGun.curEnergy();
        for(int i = 0; i < remainChargeTime; i++)
            sniperGun.chargeEnergy();
        assertTrue(sniperGun.bigAttack());
        assertEquals(world.getBullets().size(), 2);


        // 其余方向：
        this.player.setDirection(Direction.Left);
        sniperGun.setActive(true);
        sniperGun.addBullet();
        assertTrue(curWeapon.attack());
        assertEquals(world.getBullets().size(), 3);

        this.player.setDirection(Direction.Up);
        sniperGun.setActive(true);
        sniperGun.addBullet();
        assertTrue(curWeapon.attack());
        assertEquals(world.getBullets().size(), 4);

        this.player.setDirection(Direction.Down);
        sniperGun.setActive(true);
        sniperGun.addBullet();
        assertTrue(curWeapon.attack());
        assertEquals(world.getBullets().size(), 5);

        int size = sniperGun.maxBulletAmount() - sniperGun.remainingBullet();
        for(int i = 0; i <= size; i++)
            sniperGun.addBullet();
        assertEquals(sniperGun.remainingBullet(), sniperGun.maxBulletAmount());
    }

    @Test
    public void testLaserGun(){
        this.player.weaponFactory().newLaserGun();
        curWeapon = this.player.weaponFactory().curWeapon();
        assertEquals(curWeapon.name(), LaserGun.NAME);

        assertFalse(curWeapon.attack());

        LaserGun laserGun = (LaserGun)curWeapon;
        laserGun.setActive(true);
        laserGun.addBullet();
        assertTrue(curWeapon.attack());
        assertEquals(world.getBullets().size(),
        Math.min(laserGun.range(), this.world.width()-1-this.player.x())*1);

        assertFalse(laserGun.bigAttack());
        laserGun.setActive(true);
        laserGun.addBullet();
        laserGun.chargeEnergy();
        assertFalse(laserGun.bigAttack());

        int remainChargeTime = laserGun.maxEnergy()-laserGun.curEnergy();
        for(int i = 0; i < remainChargeTime; i++)
            laserGun.chargeEnergy();
        assertTrue(laserGun.bigAttack());
        assertEquals(world.getBullets().size(),
        Math.min(laserGun.range(), this.world.width()-1-this.player.x())*4);


        // 其余方向：
        this.player.setDirection(Direction.Left);
        laserGun.setActive(true);
        laserGun.addBullet();
        assertTrue(curWeapon.attack());
        assertEquals(world.getBullets().size(),
        Math.min(laserGun.range(), this.world.width()-1-this.player.x())*4+
        Math.min(laserGun.range(), this.player.x()));

        this.player.setDirection(Direction.Up);
        laserGun.setActive(true);
        laserGun.addBullet();
        assertTrue(curWeapon.attack());
        assertEquals(world.getBullets().size(),
        Math.min(laserGun.range(), this.world.width()-1-this.player.x())*4+
        Math.min(laserGun.range(), this.player.x())+
        Math.min(laserGun.range(), this.player.y()));

        this.player.setDirection(Direction.Down);
        laserGun.setActive(true);
        laserGun.addBullet();
        assertTrue(curWeapon.attack());
        assertEquals(world.getBullets().size(),
        Math.min(laserGun.range(), this.world.width()-1-this.player.x())*4+
        Math.min(laserGun.range(), this.world.height()-1-this.player.y())+
        Math.min(laserGun.range(), this.player.x())+
        Math.min(laserGun.range(), this.player.y()));

        int size = laserGun.maxBulletAmount() - laserGun.remainingBullet();
        for(int i = 0; i <= size; i++)
            laserGun.addBullet();
        assertEquals(laserGun.remainingBullet(), laserGun.maxBulletAmount());
    }

    @Test
    public void testBaseballBat(){
        this.player.weaponFactory().newBaseballBat();
        curWeapon = this.player.weaponFactory().curWeapon();
        assertEquals(curWeapon.name(), BaseballBat.NAME);

        BaseballBat baseballBat = (BaseballBat)curWeapon;

        baseballBat.setActive(true);
        assertTrue(curWeapon.attack());
        assertEquals(world.getAttackDots().size(),10);

        assertFalse(baseballBat.bigAttack());
        baseballBat.setActive(true);
        baseballBat.chargeEnergy();
        assertFalse(baseballBat.bigAttack());

        int remainChargeTime = baseballBat.maxEnergy()-baseballBat.curEnergy();
        for(int i = 0; i < remainChargeTime; i++)
            baseballBat.chargeEnergy();
        assertTrue(baseballBat.bigAttack());
        assertEquals(world.getAttackDots().size(),10+24);


        // 其余方向：
        this.player.setDirection(Direction.Left);
        baseballBat.setActive(true);
        assertTrue(curWeapon.attack());
        assertEquals(world.getAttackDots().size(),10*2+24);

        this.player.setDirection(Direction.Up);
        baseballBat.setActive(true);
        assertTrue(curWeapon.attack());
        assertEquals(world.getAttackDots().size(),10*3+24);

        this.player.setDirection(Direction.Down);
        baseballBat.setActive(true);
        assertTrue(curWeapon.attack());
        assertEquals(world.getAttackDots().size(),10*4+24);
    }

    @Test
    public void testDetonatorThrower(){
        Player player2 = new EmptyPlayer(world);
        player2.setAI(new PlayerAI(player2, new ArrayList<String>()));
        player2.setX(0);
        player2.setY(6);

        this.player.weaponFactory().newDetonatorThrower();
        curWeapon = this.player.weaponFactory().curWeapon();
        assertEquals(curWeapon.name(), DetonatorThrower.NAME);

        assertFalse(curWeapon.attack());

        DetonatorThrower detonatorThrower = (DetonatorThrower)curWeapon;
        detonatorThrower.setActive(true);
        detonatorThrower.addItem();
        for(int i = 0; i < this.player.x(); i++)
            assertTrue(detonatorThrower.dxMoveLeft());
        assertEquals(detonatorThrower.dx(), -5);
        assertEquals(detonatorThrower.dy(), 0);
        assertTrue(curWeapon.attack());
        assertEquals(world.getThrowableItems().size(), 1);
        assertFalse(world.getThrowableItems().get(0).attack(player));
        assertTrue(world.getThrowableItems().get(0).attack(player2));
        assertNotEquals(player2.hp(), player2.maxHP());

        assertFalse(detonatorThrower.bigAttack());
        detonatorThrower.setActive(true);
        detonatorThrower.addItem();
        detonatorThrower.chargeEnergy();
        assertFalse(detonatorThrower.bigAttack());
        int remainChargeTime = detonatorThrower.maxEnergy()-detonatorThrower.curEnergy();
        for(int i = 0; i < remainChargeTime; i++)
            detonatorThrower.chargeEnergy();
        assertFalse(detonatorThrower.dxMoveLeft());
        assertEquals(detonatorThrower.dx(), -5);
        assertEquals(detonatorThrower.dy(), 0);
        assertTrue(detonatorThrower.bigAttack());
        assertEquals(world.getThrowableItems().size(), 2);


        // 其余方向：
        this.player.setDirection(Direction.Left);
        detonatorThrower.setActive(true);
        detonatorThrower.addItem();
        assertTrue(detonatorThrower.dxMoveRight());
        assertEquals(detonatorThrower.dx(), -4);
        assertEquals(detonatorThrower.dy(), 0);
        assertTrue(curWeapon.attack());
        assertEquals(world.getThrowableItems().size(), 3);

        this.player.setDirection(Direction.Up);
        detonatorThrower.setActive(true);
        detonatorThrower.addItem();
        assertTrue( detonatorThrower.dyMoveUp());
        assertEquals(detonatorThrower.dx(), -4);
        assertEquals(detonatorThrower.dy(), -1);
        assertTrue(curWeapon.attack());
        assertEquals(world.getThrowableItems().size(), 4);

        this.player.setDirection(Direction.Down);
        detonatorThrower.setActive(true);
        detonatorThrower.addItem();
        assertTrue(detonatorThrower.dyMoveDown());
        assertEquals(detonatorThrower.dx(), -4);
        assertEquals(detonatorThrower.dy(), 0);
        assertTrue(curWeapon.attack());
        assertEquals(world.getThrowableItems().size(), 5);

        int size = detonatorThrower.maxItemsAmount() - detonatorThrower.remainingItems();
        for(int i = 0; i <= size; i++)
            detonatorThrower.addItem();
        assertEquals(detonatorThrower.remainingItems(), detonatorThrower.maxItemsAmount());
    }

    @Test
    public void testBombGlove(){
        this.player.weaponFactory().newBombGlove();
        curWeapon = this.player.weaponFactory().curWeapon();
        assertEquals(curWeapon.name(), BombGlove.NAME);

        assertFalse(curWeapon.attack());
        assertFalse(curWeapon.bigAttack());

        BombGlove bombGlove = (BombGlove)curWeapon;
        bombGlove.setActive(true);
        this.player.setBomb();
        this.player.setActive(true);
        this.player.moveBy(-1, 0);
        this.player.setDirection(Direction.Right);
        assertTrue(bombGlove.attack());

        // 其余方向：
        bombGlove.setActive(true);
        this.player.setBomb();
        this.player.setActive(true);
        this.player.moveBy(0, -1);
        this.player.setDirection(Direction.Down);
        assertTrue(bombGlove.bigAttack());

        bombGlove.setActive(true);
        this.player.setBomb();
        this.player.setActive(true);
        this.player.moveBy(1, 0);
        this.player.setDirection(Direction.Left);
        assertTrue(bombGlove.attack());

        bombGlove.setActive(true);
        this.player.setBomb();
        this.player.setActive(true);
        this.player.moveBy(0, 1);
        this.player.setDirection(Direction.Up);
        assertTrue(bombGlove.bigAttack());

        this.player.setActive(true);
        this.player.moveBy(0, 1);
        this.player.setActive(true);
        this.player.moveBy(0, 1);
    }
}