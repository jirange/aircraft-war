package edu.hitsz.application.game;

import edu.hitsz.aircraft.enemy.AbstractEnemyAircraft;
import edu.hitsz.aircraft.enemy.factory.EnemyFactory;
import edu.hitsz.aircraft.enemy.factory.MobEnemyFactory;
import edu.hitsz.aircraft.enemy.factory.SuperEnemyFactory;

public class NormalGame extends Game {

    private AbstractEnemyAircraft bossEnemy;
    /**
     * borderToBoss 生成BOSS敌机的阈值的增长幅度
     */
    private int borderToBoss = 800;


    /**
     * 屏幕中出现的敌机最大数量
     */
    private int enemyMaxNumber = 5;

    /**
     * 监听 创建Boss敌机对象
     */

    /**
     * 概率
     * 指示普通敌机、精英敌机的产生分配的概率
     */
    private double mobEnemyPro = 0.7;
    private double superEnemyPro = 1 - mobEnemyPro;

    /**
     * 时间间隔(ms)，控制刷新频率su
     */
    private int timeInterval = 40;
    int hpAdd4enemy = 0;
    int speedAdd = 0;

    @Override
    public void creatBossEnemy() {
        super.setBorderAddForBoss(borderToBoss);
        super.creatBossEnemy();

    }

//    @Override
//    public void createEnemyAircraftByCycle() {
//        //controlDifficulty();
////        super.creatHelp(enemyMaxNumber, superEnemyPro,hpAdd4enemy,speedAdd);
//        if (abstractEnemyAircrafts.size() < enemyMaxNumber) {
//            EnemyFactory factory;
//            AbstractEnemyAircraft enemy;
//            if (Math.random() <= superEnemyPro) {
//                factory = new SuperEnemyFactory();
//                enemy = factory.createEnemy(10,60);
//                System.out.println(enemy.getSpeedY());
//                System.out.println(enemy.getHp());
//            } else {
//                factory = new MobEnemyFactory();
//                enemy = factory.createEnemy(10,30);
//            }
//            abstractEnemyAircrafts.add(enemy);
//        }
//
//
//
//    }

    @Override
    protected AbstractEnemyAircraft getAbstractEnemyAircraft() {
        EnemyFactory factory;
        AbstractEnemyAircraft enemy;
        if (Math.random() <= superEnemyPro) {
            factory = new SuperEnemyFactory();
            enemy = factory.createEnemy(10 + speedAdd, 60 + hpAdd4enemy);
            System.out.println("速度" + enemy.getSpeedY());
            System.out.println("血量" + enemy.getHp());
        } else {
            factory = new MobEnemyFactory();
            enemy = factory.createEnemy(10 + speedAdd, 30 + hpAdd4enemy);
        }
        return enemy;
    }


    private void controlDifficulty() {
        if (super.getTime() % 3000 == 0) {
            if (cycleDuration >= 300) {
                cycleDuration -= 10;
            }
            if (mobEnemyPro >= 0.3) {
                mobEnemyPro -= 0.01;
                superEnemyPro += 0.01;
            }
            if (speedAdd <= 30) {
                hpAdd4enemy += 1;
                if (speedAdd % 3==0) {
                    speedAdd += 1;
                }
            }
            enemyMaxNumber++;

            System.out.printf("周期=%d\t最大敌机数量=%d\t普通敌机概率:%.2f\t精英敌机概率:%.2f\t敌机血量增幅:%d\t敌机速度增幅:%d\n",cycleDuration,enemyMaxNumber,mobEnemyPro,superEnemyPro,hpAdd4enemy,speedAdd);

        }
    }

    /**
     * 周期（ms)
     * 指示子弹的发射、敌机的产生频率
     */
    private int cycleDuration = 600;
    private int cycleTime = 0;

    @Override
    public boolean timeCountAndNewCycleJudge() {
        controlDifficulty();
        cycleTime += timeInterval;
        if (cycleTime >= cycleDuration && cycleTime - timeInterval < cycleTime) {
            // 跨越到新的周期
            cycleTime %= cycleDuration;
            return true;
        } else {
            return false;
        }
    }
}
