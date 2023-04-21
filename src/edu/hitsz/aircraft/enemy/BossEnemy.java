package edu.hitsz.aircraft.enemy;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.prop.*;
import edu.hitsz.strategy.shoot.ScatteringShoot;
import edu.hitsz.thread.MusicThread;

import java.util.LinkedList;
import java.util.List;

/**
 * @author leng
 */
public class BossEnemy extends AbstractEnemyAircraft {


    /**攻击方式 */

    /**
     * 子弹一次发射数量
     */
    private int shootNum = 3;

    /**
     * 道具一次掉落数量
     */
    private int dropNum = 3;

    /**
     * 子弹伤害
     */
    private int power = 30;

    /**
     * 子弹射击方向 (向上发射：1，向下发射：-1)
     */
    private int direction = 1;

    /**
     * 子弹飞行速度(1-10  1最慢  10最快)  英雄机基准是5
     */
    private int shootSpeed = 7;

    /**
     * 飞机坠毁后加的分数值
     */
    private int crashScore = 80;

    public MusicThread bossBgmThread;

    public BossEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
        //todo boss出现音效

        bossBgmThread = new MusicThread("src/videos/bgm_boss.wav");
        bossBgmThread.setLoop(true);
        bossBgmThread.setToEnd(false);
        bossBgmThread.start();
    }

    @Override
    public void forward() {
        super.forward();
    }



    /**
     * 飞机射击方法，可射击对象必须实现
     * @return
     *  可射击对象需实现，返回子弹
     *  非可射击对象空实现，返回null
     *       * 通过射击产生子弹
     *      * @return 射击出的子弹List
     */
    @Override
    public List<BaseBullet> shoot() {
        this.setShootStrategy(new ScatteringShoot());
        return doShootStrategy(direction,shootSpeed,shootNum);
    }


    @Override
    public  List<BaseProp> dropProp(){

        List<BaseProp> props = new LinkedList<>();
        double dropPR = 1;

        int dropLocationX = locationX;

        for (int i=0; i<dropNum; i++) {
            BaseProp prop = BaseProp.getProp(dropPR,dropLocationX,locationY);
            if (prop !=null) {
                props.add(prop);
            }
            dropLocationX = dropLocationX+15;
        }
        return  props;
    }

    public int getCrashScore() {
        return crashScore;
    }

    @Override
    public void vanish() {
        isValid = false;
        //todo 停止播放boss-bgm
        bossBgmThread.setToEnd(true);
    }

    /**
     * 炸弹爆炸 boss敌机减少一定的血量
     */
    @Override
    public void update() {
        this.decreaseHp(60);
    }
}
