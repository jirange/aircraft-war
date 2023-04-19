package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PropTest {


    //测试英雄能够相撞后获得道具  能有生成道具
    //测试能成功掉落道具
    @Test
    @DisplayName("ActiveProp03：test prop active")
    void activeProp() {
        //测试加血道具能否成功加血

        //创建英雄机
        HeroAircraft heroAircraft = HeroAircraft.getHeroAircraft();

        //设置英雄机血量-掉血500
        heroAircraft.decreaseHp(500);

        //获取英雄机血量
        int bloodBefore = heroAircraft.getHp();
//        System.out.println(bloodBefore);
        //创建加血道具
/*        BloodPropFactory factory = new BloodPropFactory();
        BaseProp prop = factory.createProp(100, 100);*/
        BloodProp bloodProp = new BloodProp();
        //使道具生效
        bloodProp.activeProp(heroAircraft);
        //获取使用道具后的英雄机血量
        int bloodAfter = heroAircraft.getHp();
//        System.out.println(bloodAfter);

        assertEquals(BloodProp.increase_blood,bloodAfter-bloodBefore);

    }

    //测试能否成功生成道具
    @Test
    @DisplayName("GetProp04：test create prop")
    void getProp(){
        double dropPR = 1.0;
        int locationX = 100;
        int locationY = 100;
        BaseProp prop = BaseProp.getProp(dropPR, locationX, locationY);

        assertNotNull(prop);
        assertEquals(locationX,prop.getLocationX());
        assertEquals(locationY,prop.getLocationY());

    }
}