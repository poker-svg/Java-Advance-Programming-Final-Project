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
 * @LastEditTime: 2023-01-16 17:11:53
 * @LastEditors: Xu YangXin
 * @Description: 此类为生物工厂类，用于生成生物
 * @FilePath: \jfp-poker-svg\src\main\java\cn\edu\nju\cs\game\world\creature\CreatureFactory.java
 * 此项目为南京大学《Java高级程序设计》项目
 */

package cn.edu.nju.cs.game.world.creature;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import cn.edu.nju.cs.game.world.item.Tile;
import cn.edu.nju.cs.game.world.item.World;
import cn.edu.nju.cs.game.world.creature.bros.*;
import cn.edu.nju.cs.game.world.creature.goblins.*;

public class CreatureFactory{

    private World world;
    private int goblinSize;
    private ScheduledExecutorService goblinsExecutorService;

    /**
     * @author: Xu YangXin
     * @param {World} world 目标世界
     * @param {int} goblinSize 妖精数量
     * @return {*}
     * @description: 构造器
     */    
    public CreatureFactory(World world, int goblinSize) {
        this.world = world;
        this.goblinSize = goblinSize;
        if(goblinSize > 0){
            this.goblinsExecutorService = Executors.newScheduledThreadPool(this.goblinSize);
        }
    }

    /**
     * @author: Xu YangXin
     * @param {List<String>} messages 玩家的消息序列
     * @param {String} playerName 玩家选择的人物名称
     * @return {*}
     * @description: 根据玩家选择的人物名称生成对应的葫芦娃
     */    
    public Player newPlayer(List<String> messages, String playerName) {
        //Creature player = new Creature(this.world, (char)2, AsciiPanel.brightWhite, 100, 20, 5, 9);
        Player player = null;
        switch (playerName) {
            case "FirstBro":   player = new FirstBro(world);   break;
            case "SecondBro":  player = new SecondBro(world);  break;
            case "ThirdBro":   player = new ThirdBro(world);   break;
            case "ForthBro":   player = new ForthBro(world);   break;
            case "FifthBro":   player = new FifthBro(world);   break;
            case "SixthBro":   player = new SixthBro(world);   break;
            case "SeventhBro": player = new SeventhBro(world); break;
            default:
                break;
        }

        world.addAtEmptyLocation(player);
        new PlayerAI(player, messages);
        player.setMessage(messages);
        return player;
    }

    /**
     * @author: Xu YangXin
     * @return {*}
     * @description: 生成一堆苹果
     */    
    public Creature newApples() {
        Creature apple = new Creature(this.world, Tile.APPLE.glyph(), Tile.APPLE.color(), 10, 0, 0, 0, 0);
        world.addAtEmptyLocation(apple);
        return apple;
    }

    /**
     * @author: Xu YangXin
     * @return {*}
     * @description: 生成随机的妖精
     */    
    public Goblin newRandomGoblin(){
        Goblin goblin = null;
        switch (new Random().nextInt(4)) {
            case 0:
                goblin = new Snake(world);
                break;
            case 1:
                goblin = new Scorpion(world);
                break;
            case 2:
                goblin = new Bat(world);
                break;
            case 3:
                goblin = new Spider(world);
                break;
            default:
                break;
        }

        world.addAtEmptyLocation(goblin);
        new PlayerAI(goblin, new ArrayList<String>());
        return goblin;
    }

    /**
     * @author: Xu YangXin
     * @return {*}
     * @description: 让世界中的所有妖精开始移动
     */
    public void enableGoblinsStartMove(){
        for(Goblin goblin : this.world.getGoblins()){
            goblinStartMove(goblin);
        }
    }

    /**
     * @author: Xu YangXin
     * @param {Goblin} goblin 目标妖精
     * @return {*}
     * @description: 让某个特定的妖精开始移动
     */    
    private void goblinStartMove(Goblin goblin){
        // 妖精周期性进行移动和攻击
        this.goblinsExecutorService.scheduleAtFixedRate(
        new Runnable() {
            @Override
            public void run() {
                if(goblin.hp() < 0)
                    return;
                if(world.worldIsPaused())
                    return;
                goblin.moveOneStep();
                goblin.seeAndAttack();

            }
        },
        goblin.moveSpeedInterval, goblin.moveSpeedInterval, TimeUnit.MILLISECONDS);
    }

}
