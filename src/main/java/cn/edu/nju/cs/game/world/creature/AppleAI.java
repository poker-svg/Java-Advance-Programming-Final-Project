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
 * @LastEditTime: 2023-01-16 16:32:54
 * @LastEditors: Xu YangXin
 * @Description: 此类代表苹果AI
 * @FilePath: \jfp-poker-svg\src\main\java\cn\edu\nju\cs\game\world\creature\AppleAI.java
 * 此项目为南京大学《Java高级程序设计》项目
 */

package cn.edu.nju.cs.game.world.creature;

public class AppleAI extends CreatureAI {

    transient private CreatureFactory factory;
    private int spreadcount = 0;
    public static int spores = 5;
    public static double spreadchance = 0.01;
    private int refreshCount;

    /**
     * @author: Xu YangXin
     * @param {Creature} creature 生物
     * @param {CreatureFactory} factory 生物工厂
     * @return {*}
     * @description: 构造器
     */    
    public AppleAI(Creature creature, CreatureFactory factory) {
        super(creature);
        this.factory = factory;
        refreshCount = 0;
    }

    /**
     * @author: Xu YangXin
     * @return {*}
     * @description: 更新操作，每隔300帧苹果扩散一次
     */    
    public void onUpdate() {
        if(refreshCount++ < 300)//0.1秒刷新一次，所以每30秒钟真菌传染一次
            return;

        refreshCount = 0;
        if (this.spreadcount < AppleAI.spores && Math.random() < AppleAI.spreadchance) {
            spread();
        }
    }

    /**
     * @author: Xu YangXin
     * @return {*}
     * @description: 苹果扩撒
     */    
    private void spread() {
        int newx = creature.x() + (int) (Math.random() * 11) - 5;
        int newy = creature.y() + (int) (Math.random() * 11) - 5;

        if (!creature.canEnter(newx, newy)) {
            return;
        }

        Creature child = this.factory.newApples();
        child.setX(newx);
        child.setY(newy);
        spreadcount++;
    }
}
