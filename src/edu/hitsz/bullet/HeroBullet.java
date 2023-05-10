package edu.hitsz.bullet;

/**
 * @author hitsz leng
 */
public class HeroBullet extends BaseBullet {

    private int power = 30;
    public HeroBullet(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
        setPower(power);
    }

    @Override
    public int getPower() {
        return power;
    }

    @Override
    public void setPower(int power) {
        this.power = power;
    }

    /**
     * 炸弹爆炸时 英雄机子弹无动作
     */
    @Override
    public void update() {}
}
