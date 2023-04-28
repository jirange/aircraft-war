package edu.hitsz.application.game;

import edu.hitsz.aircraft.enemy.AbstractEnemyAircraft;
import edu.hitsz.aircraft.enemy.factory.EnemyFactory;
import edu.hitsz.aircraft.enemy.factory.MobEnemyFactory;
import edu.hitsz.aircraft.enemy.factory.SuperEnemyFactory;

public class NormalGame extends Game {

    public NormalGame() {
        //普通模式下 boss敌机出现 每次分数阈值涨幅为 borderToBoss = 800
        borderAddForBoss=800;
    }


    int enemyHpAdd = 0;
    int enemySpeedAdd = 0;


    @Override
    protected AbstractEnemyAircraft getAbstractEnemyAircraft() {
        EnemyFactory factory;
        AbstractEnemyAircraft enemy;
        if (Math.random() <= superEnemyPro) {
            factory = new SuperEnemyFactory();
            enemy = factory.createEnemy(10 + enemySpeedAdd, 60 + enemyHpAdd);
        } else {
            factory = new MobEnemyFactory();
            enemy = factory.createEnemy(10 + enemySpeedAdd, 30 + enemyHpAdd);
        }
        return enemy;
    }

    @Override
    protected void controlDifficulty() {
        if (time % 4000 == 0) {
            if (cycleDuration >= 300) {
                cycleDuration -= 10;
            }
            if (mobEnemyPro >= 0.3) {
                mobEnemyPro -= 0.01;
                superEnemyPro += 0.01;
            }
            if (enemySpeedAdd <= 30) {
                enemyHpAdd += 2;
                if (enemyHpAdd % 4==0) {
                    enemySpeedAdd += 1;
                }
            }
            enemyMaxNumber++;

            System.out.printf("时间:%d\t周期:%d\t最大敌机数量:%d\t普通敌机概率:%.2f\t精英敌机概率:%.2f\t敌机血量增幅:%d\t敌机速度增幅:%d\n",time,cycleDuration,enemyMaxNumber,mobEnemyPro,superEnemyPro, enemyHpAdd, enemySpeedAdd);
        }
    }

}
