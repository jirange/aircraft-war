package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.observer.Subscriber;
import edu.hitsz.thread.MusicThread;

import java.util.ArrayList;
import java.util.List;

/**
 * @author leng
 */
public class BombProp extends BaseProp {

    public static double drop_probability =0.3;

    public BombProp(int locationX, int locationY, int propSpeed) {
        super(locationX, locationY, propSpeed);
    }

    public void setSubscribers(List<Subscriber> subscribers) {
        this.subscribers = subscribers;
    }

    /**
     * 观察者列表
     */
    public List<Subscriber> subscribers=new ArrayList<>();



    /**
     * 通知所有观察者
     */
    public void notifyAllSubscribers(){
        for (Subscriber subscriber : subscribers) {
            subscriber.update();
        }
    }

    /**
     * 炸弹道具生效
     * mainBusinessLogic
     * @param heroAircraft 英雄
     */
    @Override
    public void activeProp(HeroAircraft heroAircraft) {
        // 炸弹爆炸音效
        new MusicThread("src/videos/bomb_explosion.wav").start();
        notifyAllSubscribers();
    }
}
