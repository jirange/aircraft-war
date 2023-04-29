package edu.hitsz.application;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.application.game.BaseGame;
import edu.hitsz.application.game.DifficultGame;
import edu.hitsz.application.game.EasyGame;
import edu.hitsz.application.game.NormalGame;
import ui.BackgroundJPanel;
import ui.DifficultyChoice;
import ui.StartMenu;

import javax.swing.*;
import java.awt.*;

/**
 * 程序入口
 *
 * @author hitsz
 */
public class Main {

    public static final int WINDOW_WIDTH = 512;
    public static final int WINDOW_HEIGHT = 768;
    public static int difficulty = 0;
    public static Thread mainGameThread;
    public static DifficultyChoice difficultyChoice;
    public static StartMenu startMenu;
    public static CardLayout cardLayout = new CardLayout(0, 0);
    public static JPanel cardPanel = new JPanel(cardLayout);

    public static void start() {
//        Runnable mainGame = () -> {getMain();};
//        mainGameThread = new Thread(mainGame, "mainGame");
//        mainGameThread.start();
        getMain();
    }

    public static void main(String[] args) {

        new Main();
        System.out.println("Hello Aircraft War");
        // 获得屏幕的分辨率，初始化 Frame
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        JFrame frame = new JFrame("Aircraft War");
        frame.setSize(Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(((int) screenSize.getWidth() - WINDOW_WIDTH) / 2, 0,
                WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.getLayeredPane().add(new BackgroundJPanel("src/images/bg.jpg"));
        frame.add(cardPanel);

        startMenu = new StartMenu();
        cardPanel.add(startMenu.getMainPanel());
        frame.setVisible(true);

    }


    private static void getMain() {
        BaseGame game;

        switch (Main.difficulty) {
            case 1:
                game = new EasyGame();
                break;
            case 2:
                game = new NormalGame();
                break;
            case 3:
                game = new DifficultGame();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + Main.difficulty);
        }

        if (HeroAircraft.heroAircraft != null) {
            HeroAircraft.heroAircraft=null;
        }
        // 更新一下页面背景
        ImageManager.updateBackground();
        cardPanel.add(game);
        cardLayout.last(cardPanel);
        game.action();
    }


    /**
     * 获取
     *
     * @return difficultyStr
     */
    public static String getDifficultyStr(int difficulty) {
        switch (difficulty) {
            case 1:
                return "EASY";
            case 2:
                return "NORMAL";
            case 3:
                return "DIFFICULT";
            default:
                throw new IllegalStateException("Unexpected value: " + difficulty);
        }

    }


}
