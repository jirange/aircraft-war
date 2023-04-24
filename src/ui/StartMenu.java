package ui;

import edu.hitsz.application.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartMenu {
    //    private static JPanel GImage;
    private JButton startButton;
    private JComboBox leaderboardBox;
    private JPanel mainPanel;
    private JButton leaderboardButton;
    private JLabel label;
    private JLabel l2;
    int difficulty=1;

    static JPanel GImage;



    public static void main(String[] args) {


        JFrame frame = new JFrame("StartMenu");
        frame.setContentPane(new StartMenu().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(512, 768));
        frame.pack();
        frame.getLayeredPane().add(GImage);

        frame.setVisible(true);

//        frame.getContentPane().add(new BackgroundJPanel("src/images/bg.jpg"));
        frame.repaint();
    }

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
                System.out.println(selectedIndex);

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
        GImage = new JPanel() {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                ImageIcon icon = new ImageIcon("src/images/bg.jpg");
                Image img = icon.getImage();
                g.drawImage(img, 0, 0, icon.getIconWidth(), icon.getIconHeight(), icon.getImageObserver());

            }
        };




    }

    public JPanel getMainPanel() {
        return mainPanel;
    }


}
