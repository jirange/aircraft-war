package edu.hitsz.aircraft.enemy;

import edu.hitsz.application.Main;
import edu.hitsz.prop.*;
import edu.hitsz.strategy.shoot.DirectShoot;

import java.util.LinkedList;
import java.util.List;

/**
 * @author leng
 */
public class SuperEnemy extends AbstractEnemyAircraft {


    public SuperEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);

        /*攻击方式 */
        this.shootNum=1;
        this.power=30;
        this.direction=1;
        this.shootSpeed=7;
        this.crashScore=20;

        this.setShootStrategy(new DirectShoot());

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
