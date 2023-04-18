package edu.hitsz.bullet;

/**
 * @Author hitsz
 */
public class EnemyBullet extends BaseBullet {
    private int power = 30;
    public EnemyBullet(int locationX, int locationY, int speedX, int speedY, int power) {
        super(locationX, locationY, speedX, speedY, power);
    }

    public EnemyBullet(int locationX, int locationY, int speedX, int speedY) {
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

}
