package edu.hitsz.application;

import edu.hitsz.aircraft.enemy.AbstractEnemyAircraft;
import edu.hitsz.aircraft.enemy.factory.BossEnemyFactory;
import edu.hitsz.aircraft.enemy.factory.EnemyFactory;
import edu.hitsz.aircraft.enemy.factory.MobEnemyFactory;
import edu.hitsz.aircraft.enemy.factory.SuperEnemyFactory;
import edu.hitsz.leaderboards.PlayerRecord;
import edu.hitsz.leaderboards.RecordDaoImpl;
import edu.hitsz.strategy.shoot.ScatteringShoot;
import edu.hitsz.thread.MusicThread;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class EasyGame extends Game{
    private int scoreToBoss = 100;



    /**
     * 概率
     * 指示普通敌机、精英敌机的产生分配的概率
     */
    private double mobEnemyPro = 0.7;
    private double superEnemyPro = 1 - mobEnemyPro;

    /**
     * 屏幕中出现的敌机最大数量
     */
    private int enemyMaxNumber = 5;

    /**
     *
     */
    @Override
    public void createEnemyAircraftByCycle() {
        super.creatHelp(enemyMaxNumber,superEnemyPro,0,0);
    }

    /**
     * 监听 创建Boss敌机对象
     */
    @Override
    public void creatBossEnemy() {
        return;
    }

    /**
     * 周期（ms)
     * 指示子弹的发射、敌机的产生频率
     */
    private int cycleDuration = 600;
    private int cycleTime = 0;
    /**
     * 时间间隔(ms)，控制刷新频率su
     */
    private int timeInterval = 40;
    @Override
    public boolean timeCountAndNewCycleJudge() {
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
