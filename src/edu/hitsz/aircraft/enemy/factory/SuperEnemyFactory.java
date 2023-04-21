package edu.hitsz.aircraft.enemy.factory;

import edu.hitsz.aircraft.enemy.AbstractEnemyAircraft;
import edu.hitsz.aircraft.enemy.SuperEnemy;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;

/**
 * @author leng
 */
public class SuperEnemyFactory implements EnemyFactory{
    private int speed;
    private int hp;
    @Override
    public AbstractEnemyAircraft createEnemy(int speed,int hp) {
        return new SuperEnemy((int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth())),
                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05),
                0,
                speed,
                hp);
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }
}
