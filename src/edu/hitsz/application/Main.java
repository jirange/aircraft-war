package edu.hitsz.application;

import edu.hitsz.thread.MusicThread;
import ui.DifficultyChoice;
import ui.RecordsTable;

import javax.swing.*;
import java.awt.*;
import java.util.Date;

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
    public static CardLayout cardLayout = new CardLayout(0,0);
    public static JPanel cardPanel = new JPanel(cardLayout);

    public static void start() {
/*        Runnable mainGame = () -> {getMain();};
        mainGameThread = new Thread(mainGame, "mainGame");
        mainGameThread.start();*/
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
        frame.add(cardPanel);

        difficultyChoice = new DifficultyChoice();
        cardPanel.add(difficultyChoice.getMainPanel());
        frame.setVisible(true);

    }


    private static void getMain() {
            Game game = new Game();
            cardPanel.add(game);
            cardLayout.next(cardPanel);
            game.action();
        }



    /**
     * 获取
     * @return difficultyStr
     */
    public static String getDifficultyStr() {
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
