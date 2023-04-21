package edu.hitsz.aircraft.enemy.factory;

import edu.hitsz.aircraft.enemy.AbstractEnemyAircraft;

/**
 * @author leng
 */
public interface EnemyFactory {
    /**
     * 创造敌机对象
     * @return AbstractEnemyAircraft
     */
    public abstract AbstractEnemyAircraft createEnemy(int speed,int hp);
}
