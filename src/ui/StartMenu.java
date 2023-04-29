package ui;

import edu.hitsz.application.Main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author leng
 */
public class StartMenu {
    private JButton startButton;
    private JComboBox leaderboardBox;
    private JPanel mainPanel;
    private JButton leaderboardButton;
    private JLabel wordLabel1;
    private JLabel wordLabel2;
    int difficulty=1;

    public StartMenu() {
        mainPanel.setOpaque(false);

        startButton.addActionListener(new ActionListener() {
            /**
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.difficultyChoice = new DifficultyChoice();
                Main.cardPanel.add(Main.difficultyChoice.getMainPanel());
                Main.cardLayout.next(Main.cardPanel);
            }
        });
        leaderboardBox.addActionListener(new ActionListener() {
            /**
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = leaderboardBox.getSelectedIndex();
                difficulty = selectedIndex + 1;
            }
        });

        leaderboardButton.addActionListener(new ActionListener() {
            /**
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                RecordsTable recordsTable = new RecordsTable(difficulty);
                Main.cardPanel.add(recordsTable.getMainPanel());
                Main.cardLayout.last(Main.cardPanel);
            }
        });
    }
    public JPanel getMainPanel() {
        return mainPanel;
    }
}
