package edu.hitsz.aircraft;

import edu.hitsz.aircraft.enemy.AbstractEnemyAircraft;
import edu.hitsz.aircraft.enemy.factory.BossEnemyFactory;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.prop.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BossEnemyTest {

/*    @Disabled
    @Test
    void dropProp2() {
        for (int i = 0; i < 100; i++) {
            //判断道具生成情况
            //创建敌机
            EnemyFactory factory = new SuperEnemyFactory();
            AbstractEnemyAircraft superEnemy = factory.createEnemy();
            //设置boss敌机的坐标
            int locationX = superEnemy.getLocationX();
            int locationY = superEnemy.getLocationY();
            //boss敌机被击毁坏
            superEnemy.vanish();

            List<BaseProp> baseProps = superEnemy.dropProp();

            assertEquals(true,baseProps.size()==1 ||baseProps.size()==0);

        }
    }*/

    @Test
    @DisplayName("dropProp05：test boss drop prop")
    void dropProp() {

            //判断是否生成了三个道具
            //创建boss敌机
            BossEnemyFactory bossEnemyFactory = new BossEnemyFactory();
            AbstractEnemyAircraft bossEnemy = bossEnemyFactory.createEnemy();
           /* //设置boss敌机的坐标
            int locationX = bossEnemy.getLocationX();
            int locationY = bossEnemy.getLocationY();
            //boss敌机被击毁坏
            //bossEnemy.vanish();*/

            List<BaseProp> baseProps = bossEnemy.dropProp();
            assertEquals(3,baseProps.size());
    }

    @Test
    @DisplayName("Shoot06：test boss enemy shoot")

    void shoot() {

        //测试在boss敌机射击时能否实现一轮三发，扇形发射

        //创建boss敌机
        BossEnemyFactory bossEnemyFactory = new BossEnemyFactory();
        AbstractEnemyAircraft bossEnemy = bossEnemyFactory.createEnemy();
        //调用shoot方法，返回子弹列表bullets
        List<BaseBullet> bullets = bossEnemy.shoot();
        //判断是否一轮三发（bullets中的子弹数是否是3）
        int bulletsNum = bullets.size();
        assertEquals(3,bulletsNum);
        //判断其是否扇形发射（横向速度不同，纵向速度相同）
        //横向速度不同
        HashSet<Integer> bulletSpeedX = new HashSet<>();
        bullets.stream().map(AbstractFlyingObject::getSpeedX).forEach(bulletSpeedX::add);
        boolean XisDifferent = bulletSpeedX.size()==3;
        assertEquals(true,XisDifferent);

        //纵向速度相同
        HashSet<Integer> bulletSpeedY = new HashSet<>();
        bullets.stream().map(AbstractFlyingObject::getSpeedY).forEach(bulletSpeedY::add);
        boolean YisSame = bulletSpeedY.size()==1;
        assertEquals(true,YisSame);
    }

}