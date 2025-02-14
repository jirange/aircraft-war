package edu.hitsz.prop;


import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.application.Main;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.prop.factory.BloodPropFactory;
import edu.hitsz.prop.factory.BombPropFactory;
import edu.hitsz.prop.factory.BulletPropFactory;
import edu.hitsz.prop.factory.PropFactory;

import java.util.Random;


public abstract class BaseProp extends AbstractFlyingObject {

    private int prop_speed = 10;
    public BaseProp() {}


    public BaseProp(int locationX, int locationY, int prop_speed) {
        super(locationX, locationY, 0, prop_speed);
        this.prop_speed = prop_speed;
    }


    @Override
    public void forward() {
        super.forward();

        // 判定 x 轴出界
        if (locationX <= 0 || locationX >= Main.WINDOW_WIDTH) {
            vanish();
        }
        // 判定 y 轴出界
        if (speedY > 0 && locationY >= Main.WINDOW_HEIGHT) {
            // 向下飞行出界
            vanish();
        } else if (locationY <= 0) {
            // 向上飞行出界
            vanish();
        }

    }


    public String toString() {
        return "BaseProp{prop_speed = " + prop_speed + "}";
    }


    public abstract void activeProp(HeroAircraft heroAircraft);

    public  static BaseProp getProp(double dropPR, int locationX, int locationY) {

        PropFactory propFactory;
        BaseProp prop = null;
        Random random = new Random();

        double bulletPropDropPR = dropPR / (BulletProp.drop_probability * 10);
        double bloodPropDropPR = dropPR / (BloodProp.drop_probability * 10);
        double bombPropDropPR = dropPR / (BombProp.drop_probability * 10);

        double whoRandom = random.nextDouble();
        double bulletPro = bulletPropDropPR;
        double bombPro = bulletPro + bombPropDropPR;
        double bloodPro = bombPro + bloodPropDropPR;

        if (whoRandom < bulletPro) {
            //掉落火力道具
            propFactory = new BulletPropFactory();
            prop = propFactory.createProp(locationX, locationY);
        } else if (bulletPro <= whoRandom && whoRandom < bombPro) {
            //掉落炸弹道具
            propFactory = new BombPropFactory();
            prop = propFactory.createProp(locationX, locationY);
        } else if (bombPro <= whoRandom && whoRandom < bloodPro) {
            //掉落加血道具
            propFactory = new BloodPropFactory();
            prop = propFactory.createProp(locationX, locationY);
        }
        return prop;
    }
}
