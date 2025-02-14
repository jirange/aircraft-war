package edu.hitsz.strategy.shoot;

import edu.hitsz.bullet.BaseBullet;

import java.util.List;

/**
 * @author leng
 */
public interface ShootStrategy {

    /**
     * 进行射击
     * @param locationX:飞机的横坐标
     * @param locationY:飞机的纵坐标
     * @param speedX:飞机的横向速度
     * @param speedY:飞机的纵向速度
     * @param direction:子弹射击方向 (hero向上发射：-1，enemy向下发射：1)
     * @param shootSpeed:子弹飞行的相对速度
     * @param shootNum:一轮子弹数量
     * @return 子弹列表 List<BaseBullet>
     */
    List<BaseBullet> doShoot(int locationX,int locationY,int speedX,int speedY,int direction,int shootSpeed,int shootNum);
}
