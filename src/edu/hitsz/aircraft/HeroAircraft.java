package edu.hitsz.aircraft;

import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.strategy.shoot.DirectShoot;

/**
 * 英雄飞机，游戏玩家操控
 * @author hitsz
 */
public class HeroAircraft extends AbstractAircraft {

    public volatile static HeroAircraft heroAircraft;

    /**
     * @param locationX 英雄机位置x坐标
     * @param locationY 英雄机位置y坐标
     * @param speedX 英雄机射出的子弹的基准速度（英雄机无特定速度）
     * @param speedY 英雄机射出的子弹的基准速度（英雄机无特定速度）
     * @param hp    初始生命值
     */
    private HeroAircraft(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
        /*攻击方式 */
        this.shootNum=1;
        this.power=30;
        this.direction=-1;

        this.setShootStrategy(new DirectShoot());
    }

    @Override
    public void forward() {
        // 英雄机由鼠标控制，不通过forward函数移动
    }


    public static HeroAircraft getHeroAircraft(){
        if (heroAircraft == null){
            synchronized (HeroAircraft.class){
                if (heroAircraft == null){
                    //3/17
                    // 血量要设置到1000  3/26
                    heroAircraft = new HeroAircraft(
                            Main.WINDOW_WIDTH / 2,
                            Main.WINDOW_HEIGHT - ImageManager.HERO_IMAGE.getHeight(),
                            0, 0, 1500);
                }
            }
        }
        return heroAircraft;
    }
}
