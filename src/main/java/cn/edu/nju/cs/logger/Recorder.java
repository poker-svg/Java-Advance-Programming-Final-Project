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
 * @Date: 2022-12-03 16:30:43
 * @LastEditTime: 2023-01-16 21:10:24
 * @LastEditors: Xu YangXin
 * @Description: 此类用于记录，包括保存地图、保存游戏快照、录制。
 * @FilePath: \jfp-poker-svg\src\main\java\cn\edu\nju\cs\logger\Recorder.java
 * 此项目为南京大学《Java高级程序设计》项目
 */

package cn.edu.nju.cs.logger;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.edu.nju.cs.game.screen.PlayScreen;
import cn.edu.nju.cs.game.world.item.Map;
import cn.edu.nju.cs.game.world.item.World;

public class Recorder implements Serializable{
    private List<PlayScreen> playScreens;

    /**
     * @author: Xu YangXin
     * @return {*}
     * @description: 构造器
     */    
    public Recorder(){
        this.playScreens = new ArrayList<>();
    }

    /**
     * @author: Xu YangXin
     * @return {List<PlayScreen>} 游戏界面的列表
     * @description: 获取连续多帧的游戏界面的列表
     */    
    public List<PlayScreen> playScreens(){
        return this.playScreens;
    }

    /**
     * @author: Xu YangXin
     * @param {PlayScreen} snapShotPlayScreen 要保存的游戏界面快照
     * @return {*}
     * @description: 将目标游戏界面保存到本地文件中
     */    
    public void saveSnapShot(PlayScreen snapShotPlayScreen){
        try {
            this.playScreens.add(Recorder.clone(snapShotPlayScreen));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * @author: Xu YangXin
     * @param {World} world 目标世界
     * @return {*}
     * @description: 将目标世界写入本地文件中
     */    
    public static void writeWorld(World world)
    throws IOException, ClassNotFoundException {
        Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("_yyyy-MM-dd_HH-mm-ss");
		String timeString = simpleDateFormat.format(date);

        File file=new File("./src/main/resources/world/World"+timeString+".txt");
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        ObjectOutputStream  ObjectOutputStream  = new ObjectOutputStream(fileOutputStream);
        ObjectOutputStream.writeObject(world.map());
        ObjectOutputStream.flush();
        ObjectOutputStream.close();
    }

    /**
     * @author: Xu YangXin
     * @param {String} worldName 保存的世界文件名
     * @return {World} 得到的世界
     * @description: 读取本地文件并转换成世界对象
     */    
    public static World readWorld(String worldName)
    throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream("./src/main/resources/world/"+worldName + ".txt");
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        Map map = (Map) objectInputStream.readObject();
        objectInputStream.close();
        return new World(map);
    }

    /**
     * @author: Xu YangXin
     * @param {PlayScreen} playScreen 目标游戏界面
     * @return {*}
     * @description: 将某一帧游戏界面快照并保存到本地文件中
     */    
    public static void snapShot(PlayScreen playScreen)
    throws IOException, ClassNotFoundException {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("_yyyy-MM-dd_HH-mm-ss");
        String timeString = simpleDateFormat.format(date);

        File file=new File("./src/main/resources/snapShot/SnapShot"+timeString+".txt");
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        ObjectOutputStream  ObjectOutputStream  = new ObjectOutputStream(fileOutputStream);
        ObjectOutputStream.writeObject(playScreen);
        ObjectOutputStream.flush();
        ObjectOutputStream.close();
    }

    /**
     * @author: Xu YangXin
     * @param {String} snapShotName 本地游戏界面快照文件名
     * @return {PlayScreen}
     * @description: 读取本地游戏界面快照文件并转换成游戏界面
     */    
    public static PlayScreen readSnapShot(String snapShotName)
    throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream("./src/main/resources/snapShot/"+snapShotName+".txt");
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        PlayScreen playScreen = (PlayScreen) objectInputStream.readObject();
        objectInputStream.close();
        return new PlayScreen(playScreen);
    }

    /**
     * @author: Xu YangXin
     * @param {Recorder} recorder 录制器
     * @return {*}
     * @description: 将包含多帧的游戏界面的游戏录制内容保存到本地文件
     */    
    public static void writeRecorder(Recorder recorder)
    throws IOException, ClassNotFoundException {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("_yyyy-MM-dd_HH-mm-ss");
        String timeString = simpleDateFormat.format(date);

        File file=new File("./src/main/resources/record/Record"+timeString+".txt");
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        ObjectOutputStream  ObjectOutputStream  = new ObjectOutputStream(fileOutputStream);
        ObjectOutputStream.writeObject(recorder);
        ObjectOutputStream.flush();
        ObjectOutputStream.close();
    }

    /**
     * @author: Xu YangXin
     * @param {String} recorderName 本地录制文件名称
     * @return {*}
     * @description: 读取本地录制文件并转换成录制器
     */    
    public static Recorder readRecorder(String recorderName)
    throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream("./src/main/resources/record/"+recorderName+".txt");
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        Recorder recorder = (Recorder) objectInputStream.readObject();
        objectInputStream.close();
        return recorder;
    }

    /**
     * @author: Xu YangXin
     * @return {T} 目标对象
     * @description: 对某个对象进行深拷贝
     */    
    public static <T extends Serializable> T clone(T obj)
    throws IOException, ClassNotFoundException {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bout);
        oos.writeObject(obj);

        ByteArrayInputStream bin = new ByteArrayInputStream(bout.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bin);
        return (T) ois.readObject();
    }

}
