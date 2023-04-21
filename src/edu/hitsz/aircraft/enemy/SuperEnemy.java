package edu.hitsz.aircraft.enemy;

import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.prop.*;
import edu.hitsz.strategy.shoot.DirectShoot;

import java.util.LinkedList;
import java.util.List;

/**
 * @author leng
 */
public class SuperEnemy extends AbstractEnemyAircraft {


    /**攻击方式 */

    /**
     * 子弹一次发射数量
     */
    private int shootNum = 1;

    /**
     * 子弹伤害
     */
    private int power = 30;


    /**
     * 子弹射击方向 (向上发射：1，向下发射：-1)
     */
    private int direction = 1;

    /**
     * 子弹射击间隔 (1-10  1最短最快  10最长最慢)  英雄机基准是5
     */
    private int shootSpeed = 7;

    /**
     * 飞机坠毁后加的分数值
     */
    private int crashScore = 20;

    public SuperEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
    }

    @Override
    public void forward() {
        super.forward();
        // 判定 y 轴向下飞行出界
        if (locationY >= Main.WINDOW_HEIGHT ) {
            vanish();
        }
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
        this.setShootStrategy(new DirectShoot());
        return doShootStrategy(direction,shootSpeed,shootNum);
    }


    @Override
    public  List<BaseProp>  dropProp(){
        List<BaseProp> props = new LinkedList<>();
        double dropPR = 0.9;
        BaseProp prop = BaseProp.getProp(dropPR,locationX,locationY);
        if (prop !=null) {
            props.add(prop);
        }
        return  props;
    }


    public int getCrashScore() {
        return crashScore;
    }

    /**
     *  炸弹爆炸 精英敌机全部消失
     */
    @Override
    public void update() {
        this.vanish();
    }
}
