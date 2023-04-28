package edu.hitsz.application.game;

import edu.hitsz.aircraft.enemy.AbstractEnemyAircraft;
import edu.hitsz.aircraft.enemy.factory.EnemyFactory;
import edu.hitsz.aircraft.enemy.factory.MobEnemyFactory;
import edu.hitsz.aircraft.enemy.factory.SuperEnemyFactory;

public class EasyGame extends Game{
    /**
     * 监听 创建Boss敌机对象
     * 重写方法：简单模式不创建boss敌机
     */
    @Override
    public void creatBossEnemy() {
        return;
    }


    @Override
    protected AbstractEnemyAircraft getAbstractEnemyAircraft() {
        EnemyFactory factory;
        AbstractEnemyAircraft enemy;
        if (Math.random() <= superEnemyPro) {
            factory = new SuperEnemyFactory();
            enemy = factory.createEnemy(10,60);
            System.out.println(enemy.getSpeedY());
            System.out.println(enemy.getHp());
        } else {
            factory = new MobEnemyFactory();
            enemy = factory.createEnemy(10,30);
        }
        return enemy;
    }
}
