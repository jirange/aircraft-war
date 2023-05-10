package edu.hitsz.aircraft.enemy;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.observer.Subscriber;
import edu.hitsz.prop.BaseProp;

import java.util.List;

/**
 * @author leng
 */
public abstract class AbstractEnemyAircraft extends AbstractAircraft implements Subscriber {


    /**
     * 飞机坠毁后加的分数值
     */
    protected int crashScore=30;
    public int getCrashScore() {
        return crashScore;
    }

    public AbstractEnemyAircraft(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
    }

    /**
     * 掉落道具
     * @return List<BaseProp>
     */
    public abstract List<BaseProp> dropProp();


}
