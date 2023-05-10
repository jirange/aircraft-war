package edu.hitsz.aircraft;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.strategy.shoot.ShootStrategy;

import java.util.List;

/**
 * 所有种类飞机的抽象父类：
 * 敌机（BOSS, SUPER, MOB），英雄飞机
 *
 * @author hitsz
 */
public abstract class AbstractAircraft extends AbstractFlyingObject {


    /**
     * *攻击方式
     * 子弹一次发射数量
     * 道具一次掉落数量
     * 子弹伤害
     * 子弹射击方向 (向上发射：1，向下发射：-1)
     * 子弹飞行速度(1-10  1最慢  10最快)  英雄机基准是5
     */
    protected int shootNum = 3;
    protected int dropNum = 3;
    protected int power = 30;
    protected int direction = 1;
    protected int shootSpeed = 5;

    /**
     * 生命值
     */
    protected int maxHp;

    public void setHp(int hp) {
        this.hp = hp;
    }

    protected int hp;

    private ShootStrategy shootStrategy;

    public void setShootStrategy(ShootStrategy shootStrategy) {
        this.shootStrategy = shootStrategy;
    }

    public AbstractAircraft(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY);
        this.hp = hp;
        this.maxHp = hp;
    }

    public void decreaseHp(int decrease){
        hp -= decrease;
        if(hp <= 0){
            hp=0;
            vanish();
        }
    }

    /**
     * 加血   加血道具加血
     */
    public void increaseHp(int increase){
        hp += increase;
        if(hp >= maxHp){
            hp=maxHp;
        }
    }

    public int getHp() {
        return hp;
    }

    /**
     * 飞机射击方法，可射击对象必须实现
     * @return
     *  可射击对象需实现，返回子弹
     *  非可射击对象空实现，返回null
     */
    public List<BaseBullet> shoot(){
//        return doShootStrategy(direction,shootSpeed,shootNum);
        return shootStrategy.doShoot(locationX,locationY,speedX,speedY,direction,shootSpeed,shootNum);
    }
}


