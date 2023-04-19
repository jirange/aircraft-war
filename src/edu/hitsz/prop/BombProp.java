package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.thread.MusicThread;

/**
 * @author leng
 */
public class BombProp extends BaseProp {

    public static double drop_probability =0.3;

    public BombProp(int locationX, int locationY, int propSpeed) {
        super(locationX, locationY, propSpeed);
    }

    @Override
    public void activeProp(HeroAircraft heroAircraft) {
        System.out.println("BombSupply active!");
        //todo 炸弹爆炸音效
        new MusicThread("src/videos/bomb_explosion.wav").start();
    }
}
