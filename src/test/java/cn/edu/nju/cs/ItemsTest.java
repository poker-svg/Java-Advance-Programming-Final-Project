package cn.edu.nju.cs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import cn.edu.nju.cs.game.world.creature.PlayerAI;
import cn.edu.nju.cs.game.world.creature.bros.Player;
import cn.edu.nju.cs.game.world.creature.bros.ThirdBro;
import cn.edu.nju.cs.game.world.item.Boxes;
import cn.edu.nju.cs.game.world.item.DoorPairs;
import cn.edu.nju.cs.game.world.item.FirePiles;
import cn.edu.nju.cs.game.world.item.Grasses;
import cn.edu.nju.cs.game.world.item.Tile;
import cn.edu.nju.cs.game.world.item.World;

public class ItemsTest {
    private World world;
    private Player player;
    private DoorPairs doorPairs;
    private FirePiles firePiles;
    private Grasses grasses;
    private Boxes boxes;
    private Tile tile[][];

    @Before
    public void prepare(){
        tile = new Tile[10][10];
        for(int i = 0; i < 10; i++)
            for(int j = 0; j < 10; j++)
                tile[i][j] = Tile.FLOOR;

        doorPairs = new DoorPairs(10, 10);
        firePiles = new FirePiles(10, 10);
        grasses = new Grasses(10, 10);
        boxes = new Boxes(10, 10);
        world = new World(tile, doorPairs, grasses, boxes, firePiles);
        player = new ThirdBro(world);
        player.setAI(new PlayerAI(player, new ArrayList<String>()));
        player.setX(0);
        player.setY(0);
    }

    @Test
    public void testDoorPairs(){
        // 超出边界，相同位置，search失败条件
        assertFalse(doorPairs.addPairs(-1, -1, 11, 11));
        assertFalse(doorPairs.addPairs(1, 1, 1, 1));
        assertEquals(doorPairs.search(0, 0), null);

        // 添加传送门：(1,1) 和 (8,8)
        doorPairs.addPairs(1, 1, 8, 8);
        world.setThing(Tile.DOOR, 1, 1);
        world.setThing(Tile.DOOR, 8, 8);
        assertFalse(player.transfer());
        assertEquals(player.x(), 0);
        assertEquals(player.y(), 0);

        player.moveBy(1, 0);
        player.setActive(true);
        assertEquals(player.x(), 1);
        assertEquals(player.y(), 0);
        player.moveBy(0, 1);
        player.setActive(true);

        assertEquals(player.x(), 8);
        assertEquals(player.y(), 8);

        // 将(8,8)位置的传送门进行删除，模拟炸弹或者其它破坏性武器摧毁传送门
        world.setThing(Tile.FLOOR, 8, 8);
        // 重新生成传送门对
        doorPairs.refreshDoorPairs(world);
        assertEquals(world.tile(8, 8), Tile.DOOR);

        // 从反方向再次传送：
        player.moveBy(1, 0);
        player.setActive(true);
        assertFalse(player.transfer());
        player.moveBy(-1, 0);
        player.setActive(true);
        assertEquals(player.x(), 1);
        assertEquals(player.y(), 1);
    }

    @Test
    /**
     * 测试火堆的恢复能力，每2次会恢复1点HP
     */
    public void testFirePiles(){
        // 超出边界，search失败条件
        assertFalse(firePiles.addFirePile(-1, -1, Tile.FIRE_PILE));
        assertEquals(firePiles.search(0, 0), null);


        firePiles.addFirePile(1, 0, Tile.FIRE_PILE);
        world.setThing(Tile.FIRE_PILE, 1, 0);
        player.modifyHP(-1);
        assertEquals(player.hp(), player.maxHP()-1);

        assertFalse(firePiles.warmCreature(null));

        assertFalse(firePiles.warmCreature(player));
        player.moveBy(1, 0);
        assertFalse(firePiles.warmCreature(player));
        assertTrue(firePiles.warmCreature(player));
        assertEquals(player.hp(), player.maxHP());

        // 将(1,0)位置的火堆进行删除，模拟炸弹或者其它破坏性武器摧毁
        world.setThing(Tile.FLOOR, 1, 0);
        // 重新生成火堆
        firePiles.refreshFirePiles(world);
        assertEquals(world.tile(1, 0), Tile.FIRE_PILE);
    }

    @Test
    /**
     * 测试草丛的隐藏能力
     */
    public void testGrasses(){
        Tile grass = Tile.randomGrass();

        // 超出边界，search失败条件
        assertFalse(grasses.addGrass(-1, -1, grass));
        assertEquals(grasses.search(0, 0), null);

        grasses.addGrass(1, 0, grass);
        world.setThing(grass, 1, 0);


        assertFalse(player.world().grasses().hideCreature(player));
        assertTrue(player.isVisible());

        player.moveBy(1, 0);
        player.setActive(true);
        assertTrue(player.world().grasses().hideCreature(player));
        assertFalse(player.isVisible());

        player.moveBy(1, 0);
        assertFalse(player.world().grasses().hideCreature(player));
        assertTrue(player.isVisible());

        // 将(1,0)位置的草丛进行删除，模拟炸弹或者其它破坏性武器摧毁
        world.setThing(Tile.FLOOR, 1, 0);
        // 重新生成草丛
        grasses.refreshGrasses(world);
        assertTrue(world.tile(1, 0).isGrass());
    }
    @Test
    /**
     * 测试宝箱功能
     */
    public void testBoxes(){
        // 超出边界，search失败条件
        assertFalse(boxes.addBox(-1, -1));
        assertEquals(boxes.search(0, 0), null);

        boxes.addBox(1, 0);
        world.setThing(Tile.BOX_CLOSE, 1, 0);

        // 初始状态
        int initialWeaponSize = player.weaponFactory().weaponSize();
        assertFalse(player.world().boxes().enterBox(player));
        assertTrue(player.isVisible());
        assertEquals(world.tile(1, 0), Tile.BOX_CLOSE);

        // 初次进入宝箱，获取武器，隐藏身形
        player.moveBy(1, 0);
        player.setActive(true);
        assertTrue(player.world().boxes().enterBox(player));
        assertFalse(player.isVisible());
        assertEquals(player.weaponFactory().weaponSize(), initialWeaponSize+1);
        assertEquals(world.tile(1, 0), Tile.BOX_OPEN);

        // 离开宝箱，宝箱处于打开状态
        player.moveBy(1, 0);
        player.setActive(true);
        assertFalse(player.world().boxes().enterBox(player));
        assertTrue(player.isVisible());
        assertEquals(player.weaponFactory().weaponSize(), initialWeaponSize+1);
        assertEquals(world.tile(1, 0), Tile.BOX_OPEN);

        // 再次进入宝箱，隐藏到宝箱中，宝箱关闭
        player.moveBy(-1, 0);
        player.setActive(true);
        assertTrue(player.world().boxes().enterBox(player));
        assertFalse(player.isVisible());
        assertEquals(player.weaponFactory().weaponSize(), initialWeaponSize+1);
        assertEquals(world.tile(1, 0), Tile.BOX_CLOSE);

        // 再次离开宝箱，宝箱关闭状态，看不出来是否有人
        player.moveBy(1, 0);
        player.setActive(true);
        assertFalse(player.world().boxes().enterBox(player));
        assertTrue(player.isVisible());
        assertEquals(player.weaponFactory().weaponSize(), initialWeaponSize+1);
        assertEquals(world.tile(1, 0), Tile.BOX_CLOSE);

        // 将(1,0)位置的宝箱进行删除，模拟炸弹或者其它破坏性武器摧毁
        world.setThing(Tile.FLOOR, 1, 0);
        // 重新生成宝箱
        boxes.refreshBox(world);
        assertEquals(world.tile(1, 0), Tile.BOX_CLOSE);
    }
}
