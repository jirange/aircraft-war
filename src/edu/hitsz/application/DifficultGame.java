package edu.hitsz.application;

import edu.hitsz.aircraft.enemy.AbstractEnemyAircraft;

public class DifficultGame extends Game {
    private AbstractEnemyAircraft bossEnemy;

    int hpAdd = 50;
    /**
     * borderToBoss 生成BOSS敌机的阈值的增长幅度
     */
    private int borderToBoss = 800;


    /**
     * 监听 创建Boss敌机对象
     */

    /**
     * 屏幕中出现的敌机最大数量
     */
    private int enemyMaxNumber = 5;

    /**
     * 时间间隔(ms)，控制刷新频率su
     */
    private int timeInterval = 40;
    int hpAdd4enemy = 0;
    int speedAdd = 0;

    /**
     * 概率
     * 指示普通敌机、精英敌机的产生分配的概率
     */
    private double mobEnemyPro = 0.7;
    private double superEnemyPro = 1 - mobEnemyPro;

    @Override
    public void creatBossEnemy() {
        super.creatBossEnemyHelpHelp(borderToBoss, hpAdd);
    }

    @Override
    public void createEnemyAircraftByCycle() {
        //controlDifficulty();
        super.creatHelp(enemyMaxNumber, superEnemyPro, hpAdd4enemy, speedAdd);

    }

/*    private void controlDifficulty() {
        if (super.getTime() % 1000 == 0) {

        }
    }*/

    /**
     * 周期（ms)
     * 指示子弹的发射、敌机的产生频率
     */
    private int cycleDuration = 500;
    private int cycleTime = 0;

    @Override
    public boolean timeCountAndNewCycleJudge() {
        if (super.getTime() % 3000 == 0) {
            if (cycleDuration >= 200) {
                cycleDuration -= 10;
                System.out.println("cycleDuration : " + cycleDuration);
            }

            if (mobEnemyPro >= 0.1) {
                mobEnemyPro -= 0.02;
                superEnemyPro += 0.02;
                System.out.println("mobEnemyPro:" + mobEnemyPro + " superEnemyPro:" + superEnemyPro);
            }
            if (speedAdd <= 30) {
                hpAdd4enemy += 1;
                speedAdd += 1;

            }

        } else if (super.getTime() % 5000 == 0) {
            enemyMaxNumber++;
            System.out.println("enemyMaxNumber : " + enemyMaxNumber);
        }

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
