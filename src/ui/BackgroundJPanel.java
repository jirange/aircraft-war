package ui;

import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class BackgroundJPanel extends JPanel {

    private Image backgroundImage;


    public BackgroundJPanel(String fileName){
        try {
            backgroundImage = ImageIO.read(new File(fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, this);

    }}


