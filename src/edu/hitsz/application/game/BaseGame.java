package edu.hitsz.application.game;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.aircraft.enemy.AbstractEnemyAircraft;
import edu.hitsz.aircraft.enemy.factory.BossEnemyFactory;
import edu.hitsz.aircraft.enemy.factory.EnemyFactory;
import edu.hitsz.application.HeroController;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.leaderboards.PlayerRecord;
import edu.hitsz.leaderboards.RecordDaoImpl;
import edu.hitsz.observer.Subscriber;
import edu.hitsz.prop.BaseProp;
import edu.hitsz.prop.BombProp;
import edu.hitsz.strategy.shoot.DirectShoot;
import edu.hitsz.strategy.shoot.ScatteringShoot;
import edu.hitsz.thread.MusicThread;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import ui.DifficultyChoice;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 游戏主面板，游戏启动
 *
 * @author hitsz
 */
public abstract class BaseGame extends JPanel {

    private int backGroundTop = 0;

    /**
     * Scheduled 线程池，用于任务调度
     */
    private final ScheduledExecutorService executorService;

    /**
     * scoreToBoss 生成BOSS敌机的阈值
     */
    private int scoreToBoss = 100;
    protected int bossHp = 150;
    protected int bossHpAdd = 0;

    protected int borderAddForBoss = 0;


    /**
     * 时间间隔(ms)，控制刷新频率su
     */
    protected int timeInterval = 40;

    private final HeroAircraft heroAircraft;


    private AbstractEnemyAircraft bossEnemy;


    private final List<AbstractEnemyAircraft> abstractEnemyAircrafts;
    private final List<BaseBullet> heroBullets;
    private final List<BaseBullet> enemyBullets;

    private final List<BaseProp> props;

    /**
     * 屏幕中出现的敌机最大数量
     */
    protected int enemyMaxNumber = 5;

    /**
     * 当前得分
     */
    private int score = 0;
    /**
     * 当前时刻
     */
    protected int time = 0;

    /**
     * 周期（ms)
     * 指示子弹的发射、敌机的产生频率
     */
    protected int cycleDuration = 600;
    protected int cycleTime = 0;

    /**
     * 概率
     * 指示普通敌机、精英敌机的产生分配的概率
     */
    protected double mobEnemyPro = 0.7;
    protected double superEnemyPro = 1 - mobEnemyPro;


    /**
     * 游戏结束标志
     */
    private boolean gameOverFlag = false;
    private MusicThread musicThread;

    public BaseGame() {
        gameOverFlag = false;
        heroAircraft = HeroAircraft.getHeroAircraft();

        abstractEnemyAircrafts = new LinkedList<>();
        heroBullets = new LinkedList<>();
        enemyBullets = new LinkedList<>();
        props = new LinkedList<>();

        /**
         * Scheduled 线程池，用于定时任务调度
         * 关于alibaba code guide：可命名的 ThreadFactory 一般需要第三方包
         * apache 第三方库： org.apache.commons.lang3.concurrent.BasicThreadFactory
         */
        this.executorService = new ScheduledThreadPoolExecutor(1,
                new BasicThreadFactory.Builder().namingPattern("game-action-%d").daemon(true).build());

        // 启动英雄机鼠标监听
        new HeroController(this, heroAircraft);

        // 开启bgm
        musicThread = new MusicThread("src/videos/bgm.wav");
        if (DifficultyChoice.isMusicSelected) {
            MusicThread.haveAudio = true;
            musicThread.setLoop(true);
            musicThread.start();
            System.out.println("音乐：开启");
        } else {
            MusicThread.haveAudio = false;
            System.out.println("音乐：关闭");
        }

    }

    /**
     * 游戏启动入口，执行游戏逻辑
     */
    public void action() {

        // 定时任务：绘制、对象产生、碰撞判定、击毁及结束判定
        Runnable task = () -> {
            time += timeInterval;
            // 周期性执行（控制频率）
            if (timeCountAndNewCycleJudge()) {
                if (abstractEnemyAircrafts.size() < enemyMaxNumber) {
                    AbstractEnemyAircraft enemy = getAbstractEnemyAircraft();
                    abstractEnemyAircrafts.add(enemy);
                }
                // 飞机射出子弹
                shootAction();
            }

            // 子弹移动
            bulletsMoveAction();

            // 道具移动
            propsMoveAction();

            // 飞机移动
            aircraftsMoveAction();

            // 撞击检测
            crashCheckAction();

            // BOSS敌机生成
            creatBossEnemy();

            // 后处理
            postProcessAction();

            //每个时刻重绘界面
            repaint();

            // 游戏结束检查英雄机是否存活
            if (heroAircraft.getHp() <= 0) {
                // 游戏结束
                //游戏结束音效响起来
                new MusicThread("src/videos/game_over.wav").start();
                // 关闭音乐bgm
                if (DifficultyChoice.isMusicSelected) {
                    musicThread.setToEnd(true);
                }
                if (bossEnemy != null) {
                    bossEnemy.vanish();
                }
                executorService.shutdown();
                gameOverFlag = true;
                System.out.println("Game Over!");

                //打印游戏记录排行榜
                RecordDaoImpl recordDao = new RecordDaoImpl();
                Date date = new Date();
                PlayerRecord userRecord = new PlayerRecord(Main.difficulty, score, date);
                System.out.println("玩家得分：" + userRecord);
                recordDao.updateData(userRecord);
            }
        };

        /**
         * 以固定延迟时间进行执行
         * 本次任务执行完成后，需要延迟设定的延迟时间，才会执行新的任务
         */
        executorService.scheduleWithFixedDelay(task, timeInterval, timeInterval, TimeUnit.MILLISECONDS);
    }

    /**
     * 生成普通敌机
     * 简单模式 难度不变 普通和困难模式 难度要变
     * @return 敌机对象
     */
    protected abstract AbstractEnemyAircraft getAbstractEnemyAircraft() ;


    //***********************
    //      Action 各部分
    //***********************

    public boolean timeCountAndNewCycleJudge(){
        // 控制游戏难度
        controlDifficulty();
        cycleTime += timeInterval;
        if (cycleTime >= cycleDuration && cycleTime - timeInterval < cycleTime) {
            // 跨越到新的周期
            cycleTime %= cycleDuration;
            return true;
        } else {
            return false;
        }
    }

    /**
     * 控制游戏难度 如周期，最大敌机数量，普通敌机概率，精英敌机概率，敌机血量增，敌机速度增幅
     * cycleDuration,enemyMaxNumber,mobEnemyPro,superEnemyPro, enemyHpAdd, enemySpeedAdd
     */
    protected abstract void controlDifficulty();


    /**
     * 监听 创建Boss敌机对象
     */
    public void creatBossEnemy(){
        synchronized (this) {
            EnemyFactory factory;
            // 分数达到设定阈值后出现BOSS敌机，可多次出现
            if (score >= scoreToBoss) {
                if (bossEnemy == null || bossEnemy.notValid()) {
                    factory = new BossEnemyFactory();
                    bossEnemy = factory.createEnemy(5, bossHp);
                    bossHp +=bossHpAdd;
                    System.out.println("boss敌机血量为" + bossEnemy.getHp());
                    //设置为散射弹道
                    bossEnemy.setShootStrategy(new ScatteringShoot());
                    abstractEnemyAircrafts.add(bossEnemy);
                    scoreToBoss += borderAddForBoss;
                }
            }
        }
    }

    private void shootAction() {
        // 敌机射击
        for (AbstractAircraft enemyAircraft : abstractEnemyAircrafts) {
            enemyBullets.addAll(enemyAircraft.shoot());
        }
        // 英雄射击
        heroBullets.addAll(heroAircraft.shoot());
    }


    private void bulletsMoveAction() {
        for (BaseBullet bullet : heroBullets) {
            bullet.forward();
        }
        for (BaseBullet bullet : enemyBullets) {
            bullet.forward();
        }
    }

    private void aircraftsMoveAction() {
        for (AbstractAircraft enemyAircraft : abstractEnemyAircrafts) {
            enemyAircraft.forward();
        }
    }

    /**
     * 道具移动
     */
    private void propsMoveAction() {
        for (BaseProp prop : props) {
            prop.forward();
        }
    }


    /**
     * 碰撞检测：
     * 1. 敌机攻击英雄
     * 2. 英雄攻击/撞击敌机
     * 3. 英雄获得补给
     */
    private void crashCheckAction() {
        // 敌机子弹攻击英雄
        for (BaseBullet bullet : enemyBullets) {
            if (bullet.notValid()) {
                continue;
            }
            if (heroAircraft.crash(bullet)) {
                // 加载被击中音效
                new MusicThread("src/videos/bullet_hit.wav").start();
                // 英雄撞击到精英敌机子弹
                // 英雄损失一定生命值
                heroAircraft.decreaseHp(bullet.getPower());
                bullet.vanish();
            }
        }
        // 英雄子弹攻击敌机
        for (BaseBullet bullet : heroBullets) {

            if (bullet.notValid()) {
                continue;
            }
            for (AbstractEnemyAircraft abstractEnemyAircraft : abstractEnemyAircrafts) {
                if (abstractEnemyAircraft.notValid()) {
                    // 已被其他子弹击毁的敌机，不再检测
                    // 避免多个子弹重复击毁同一敌机的判定
                    continue;
                }
                if (abstractEnemyAircraft.crash(bullet)) {
                    // 加载被击中音效
                    MusicThread bulletHitSoundThread = new MusicThread("src/videos/bullet_hit.wav");
                    bulletHitSoundThread.start();

                    // 敌机撞击到英雄机子弹
                    // 敌机损失一定生命值
                    abstractEnemyAircraft.decreaseHp(bullet.getPower());

                    bullet.vanish();

                    if (abstractEnemyAircraft.notValid()) {
                        // 获得分数，产生道具补给
                        //普通敌机 坠毁+10分  精英敌机+20  boss敌机+80
                        score += abstractEnemyAircraft.getCrashScore();
                        //产生道具补给   敌机坠毁后，以一定概率随机掉落某种道具
                        List<BaseProp> baseProps = abstractEnemyAircraft.dropProp();
                        if (baseProps != null) {
                            props.addAll(baseProps);
                        }
                    }
                }
                // 英雄机 与 敌机 相撞，均损毁
                if (abstractEnemyAircraft.crash(heroAircraft) || heroAircraft.crash(abstractEnemyAircraft)) {
                    abstractEnemyAircraft.vanish();
                    heroAircraft.decreaseHp(Integer.MAX_VALUE);
                }
            }
        }

        // 我方获得道具，道具生效

        for (BaseProp prop : props) {
            if (prop.notValid()) {
                continue;
            }
            if (heroAircraft.crash(prop)) {
                // 英雄碰到道具
                // 道具生效
                // 加载道具生效音效
                MusicThread propEffectSoundThread = new MusicThread("src/videos/get_supply.wav");
                propEffectSoundThread.start();
                // 将英雄机加入炸弹观察者清单
                if (prop instanceof BombProp) {
                    ((BombProp) prop).addSubscribers(enemyBullets);
                    ((BombProp) prop).addSubscribers(abstractEnemyAircrafts);
                    for (AbstractEnemyAircraft enemy : abstractEnemyAircrafts) {
                        score += enemy.getCrashScore();
                    }
                }
                prop.activeProp(heroAircraft);
                prop.vanish();
            }
        }


    }


    /**
     * 后处理：
     * 1. 删除无效的子弹
     * 2. 删除无效的敌机
     * 3.删除无用的道具             3/11
     * <p>
     * 无效的原因可能是撞击或者飞出边界
     */
    private void postProcessAction() {
        enemyBullets.removeIf(AbstractFlyingObject::notValid);
        heroBullets.removeIf(AbstractFlyingObject::notValid);
        abstractEnemyAircrafts.removeIf(AbstractFlyingObject::notValid);
        props.removeIf(AbstractFlyingObject::notValid);
    }


    //***********************
    //      Paint 各部分
    //***********************

    /**
     * 重写paint方法
     * 通过重复调用paint方法，实现游戏动画
     *
     * @param g 画
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        // 绘制背景,图片滚动
        g.drawImage(ImageManager.BACKGROUND_IMAGE, 0, this.backGroundTop - Main.WINDOW_HEIGHT, null);
        g.drawImage(ImageManager.BACKGROUND_IMAGE, 0, this.backGroundTop, null);
        this.backGroundTop += 1;
        if (this.backGroundTop == Main.WINDOW_HEIGHT) {
            this.backGroundTop = 0;
        }
        // 先绘制子弹，后绘制飞机
        // 这样子弹显示在飞机的下层
        paintImageWithPositionRevised(g, enemyBullets);
        paintImageWithPositionRevised(g, heroBullets);
        //绘制道具
        paintImageWithPositionRevised(g, props);
        paintImageWithPositionRevised(g, abstractEnemyAircrafts);
        g.drawImage(ImageManager.HERO_IMAGE, heroAircraft.getLocationX() - ImageManager.HERO_IMAGE.getWidth() / 2,
                heroAircraft.getLocationY() - ImageManager.HERO_IMAGE.getHeight() / 2, null);

        //绘制得分和生命值
        paintScoreAndLife(g);
    }

    private void paintImageWithPositionRevised(Graphics g, List<? extends AbstractFlyingObject> objects) {
        if (objects.size() == 0) {
            return;
        }
        for (AbstractFlyingObject object : objects) {
            BufferedImage image = object.getImage();
            assert image != null : objects.getClass().getName() + " has no image! ";
            g.drawImage(image, object.getLocationX() - image.getWidth() / 2,
                    object.getLocationY() - image.getHeight() / 2, null);
        }
    }

    private void paintScoreAndLife(Graphics g) {
        int x = 10;
        int y = 25;
        g.setColor(new Color(16711680));
        g.setFont(new Font("SansSerif", Font.BOLD, 22));
        g.drawString("SCORE:" + this.score, x, y);
        y = y + 20;
        g.drawString("LIFE:" + this.heroAircraft.getHp(), x, y);
    }

}