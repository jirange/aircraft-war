package edu.hitsz.strategy.shoot;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;

import java.util.*;

/**
 * 散射弹道
 * @author leng
 */
public class HeartShapedShoot implements ShootStrategy{
    List<Integer> yList = new ArrayList<>();
    List<Integer> xList = new ArrayList<>();

    public HeartShapedShoot() {
        super();
        Collections.addAll(yList,0,60,5,75,5,80,15,87,20,90,30,85,40,80,50,70,60);
        Collections.addAll(xList,0,0,5,5,10,10,20,20,30,30,40,40,50,50,55,55,60);

    }

    @Override
    public  List<BaseBullet> doShoot(int locationX,int locationY,int speedX,int speedY,int direction,int shootSpeed,int shootNum) {
        List<BaseBullet> res = new LinkedList<>();
        Random random = new Random();
        int x = locationX;
        int bulletSpeedY = speedY + direction* shootSpeed;
        if (direction==-1){
            int y = locationY + direction*3;
            for (int i = 0; i < xList.size(); i++) {
                res.add(new HeroBullet(x+xList.get(i), y-yList.get(i),xList.get(i)/15,-yList.get(i)/20+bulletSpeedY));
                res.add(new HeroBullet(x-xList.get(i), y-yList.get(i),-xList.get(i)/15,-yList.get(i)/20+bulletSpeedY));
            }
        }else {
            int bulletSpeedX = random.nextInt(20);
            int y = locationY+ direction*100;
            res.add(new EnemyBullet(x, y, bulletSpeedX, bulletSpeedY));
            res.add(new EnemyBullet(x, y, 0, bulletSpeedY));
            res.add(new EnemyBullet(x,y , -bulletSpeedX, bulletSpeedY));
        }
        return res;
    }
}
