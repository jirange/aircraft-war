package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.strategy.shoot.DirectShoot;
import edu.hitsz.strategy.shoot.HeartShapedShoot;
import edu.hitsz.strategy.shoot.ScatteringShoot;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author leng
 */
public class BulletProp extends BaseProp {

    public static double drop_probability = 0.3;

    public BulletProp(int locationX, int locationY, int propSpeed) {
        super(locationX, locationY, propSpeed);
    }

    @Override
    public void activeProp(HeroAircraft heroAircraft) {
        //让直射线程暂停五秒 散射线程持续五秒后关闭
        Runnable scatteringRun = () -> {
            synchronized (BulletProp.class) {
                heroAircraft.setShootStrategy(new ScatteringShoot());
                try {
                    BulletProp.class.wait(4400);
                    heroAircraft.setShootStrategy(new HeartShapedShoot());
                    BulletProp.class.wait(600);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                heroAircraft.setShootStrategy(new DirectShoot());
            }
        };
        ScheduledExecutorService executorService= new ScheduledThreadPoolExecutor(1,
                new BasicThreadFactory.Builder().namingPattern("bullet-prop-action-%d").daemon(true).build());

        executorService.schedule(scatteringRun,40, TimeUnit.MILLISECONDS);
    }

}
