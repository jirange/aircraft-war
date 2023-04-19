package ui;

import edu.hitsz.application.Main;
import edu.hitsz.thread.MusicThread;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author leng
 */
public class DifficultyChoice {
    private JButton simpleButton;
    private JButton normalButton;
    private JButton difficultButton;
    private JComboBox checkAudio;
    private JPanel panel;

    private JFrame frame;

    public boolean isMusicSelected() {
        return isMusicSelected;
    }

    boolean isMusicSelected=true;

    public int getDifficulty() {
        return difficulty;
    }


    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    private int difficulty = 0;

    public JPanel getMainPanel() {
        return panel;
    }

    public DifficultyChoice() {
        simpleButton.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                //if (e.getSource()==simpleButton){
                //todo 点击简单模式后，转到简单模式
                System.out.println("difficulty:EASY");
                Main.difficulty = 1;
                Main.start();

            }
        });
        normalButton.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("difficulty:NORMAL");
                Main.difficulty = 2;
                Main.start();
            }
        });
        difficultButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("difficulty:DIFFICULT");
                Main.difficulty = 3;
                Main.start();
            }
        });
        checkAudio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(checkAudio.getSelectedItem());
                isMusicSelected ="开".equals(checkAudio.getSelectedItem());
            }
        });
    }
}