package edu.hitsz.aircraft;

import edu.hitsz.aircraft.enemy.AbstractEnemyAircraft;
import edu.hitsz.aircraft.enemy.factory.EnemyFactory;
import edu.hitsz.aircraft.enemy.factory.MobEnemyFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;
/**在系统中选择  英雄机、敌机、子弹和道具类的方法（包含其父类方法）作为单元测试的对象，
 * 为每个测试对象编写单元测试代码。
 * 要求至少选择3个类，每个类至少测试2个方法，
 * 并截图JUnit单元测试的结果。*/
class HeroAircraftTest {

    @DisplayName("Crash01：test crash")
    @ParameterizedTest
    @CsvSource({"100,150,120,170", "250,250,250,250","250,50,288,50"})
    void testCrash(double heroLocationX,double heroLocationY,double enemyLocationX,double enemyLocationY){

        //测试两个飞行物体的碰撞检测是否正常
        boolean isCrash=false;

        //获取英雄机
        HeroAircraft heroAircraft = HeroAircraft.getHeroAircraft();
        //设置英雄机坐标
        heroAircraft.setLocation(heroLocationX,heroLocationY);

        //创建指定坐标的道具或敌机
        EnemyFactory enemyFactory = new MobEnemyFactory();

        AbstractEnemyAircraft enemy = enemyFactory.createEnemy();
        //设置敌机坐标
        enemy.setLocation(enemyLocationX,enemyLocationY);




        isCrash = heroAircraft.crash(enemy);

        assertEquals(true,isCrash);

    }

    @Test
    @DisplayName("getHeroAircraft02：test get hero")
    void getHeroAircraft() {
        //测试英雄机能否被创建多个
        //创建英雄机
        HeroAircraft heroAircraft = HeroAircraft.getHeroAircraft();



        //创建第二个英雄机，看是否能成功
        HeroAircraft heroAircraft2 = HeroAircraft.getHeroAircraft();

        assertEquals(true,heroAircraft2==heroAircraft);
    }
}