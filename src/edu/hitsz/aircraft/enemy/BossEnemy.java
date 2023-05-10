package edu.hitsz.aircraft.enemy;

import edu.hitsz.prop.*;
import edu.hitsz.strategy.shoot.ScatteringShoot;
import edu.hitsz.thread.MusicThread;

import java.util.LinkedList;
import java.util.List;

/**
 * @author leng
 */
public class BossEnemy extends AbstractEnemyAircraft {

    public MusicThread bossBgmThread;

    public BossEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
        // boss出现音效

        bossBgmThread = new MusicThread("src/videos/bgm_boss.wav");
        bossBgmThread.setLoop(true);
        bossBgmThread.setToEnd(false);
        bossBgmThread.start();

        /*攻击方式 */
        this.shootNum=3;
        this.dropNum = 3;
        this.direction=1;
        this.shootSpeed=7;
        this.crashScore=80;

        this.setShootStrategy(new ScatteringShoot());

    }

    @Override
    public void forward() {
        super.forward();
    }


    @Override
    public  List<BaseProp> dropProp(){

        List<BaseProp> props = new LinkedList<>();
        int dropLocationX = locationX;

        for (int i=0; i<dropNum; i++) {
            BaseProp prop = BaseProp.getProp(1.0,dropLocationX,locationY);
            if (prop !=null) {
                props.add(prop);
            }
            dropLocationX = dropLocationX+15;
        }
        return  props;
    }

    @Override
    public void vanish() {
        isValid = false;
        // 停止播放boss-bgm
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
