package edu.hitsz.aircraft.enemy;

import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.prop.BaseProp;

import java.util.LinkedList;
import java.util.List;

/**
 * 普通敌机
 * 不可射击
 *
 * @author hitsz
 */
public class MobEnemy extends AbstractEnemyAircraft {
    /**
     * 飞机坠毁后加的分数值
     */
    private int crashScore = 10;
    public MobEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
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

    @Override
    public List<BaseBullet> shoot() {
        return new LinkedList<>();
    }


    @Override
    public List<BaseProp> dropProp() {
        return new LinkedList<>();
    }

    public int getCrashScore() {
        return crashScore;
    }

    /**
     * 炸弹爆炸时 普通敌机 全部消失
     */
    @Override
    public void update() {
        this.vanish();
    }
}
