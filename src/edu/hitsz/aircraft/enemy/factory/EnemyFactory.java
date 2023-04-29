package edu.hitsz.aircraft.enemy.factory;

import edu.hitsz.aircraft.enemy.AbstractEnemyAircraft;

/**
 * @author leng
 */
public interface EnemyFactory {
    /**
     * 创造敌机对象
     * @return AbstractEnemyAircraft
     * @param speed 敌机纵向速度
     * @param hp 敌机血量
     */
    AbstractEnemyAircraft createEnemy(int speed,int hp);
}
