package edu.hitsz.application.game;

import edu.hitsz.aircraft.enemy.AbstractEnemyAircraft;
import edu.hitsz.aircraft.enemy.factory.EnemyFactory;
import edu.hitsz.aircraft.enemy.factory.MobEnemyFactory;
import edu.hitsz.aircraft.enemy.factory.SuperEnemyFactory;

/**
 * @author leng
 */
public class EasyGame extends BaseGame {
    @Override
    public void creatBossEnemy(){}

    @Override
    protected AbstractEnemyAircraft getAbstractEnemyAircraft() {
        EnemyFactory factory;
        AbstractEnemyAircraft enemy;
        if (Math.random() <= superEnemyPro) {
            factory = new SuperEnemyFactory();
            enemy = factory.createEnemy(10,60);
        } else {
            factory = new MobEnemyFactory();
            enemy = factory.createEnemy(10,30);
        }
        return enemy;
    }

    /**
     * 简单模式：不用控制难度变化
     */
    @Override
    protected void controlDifficulty() {}
}
