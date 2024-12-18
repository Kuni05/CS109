package view.game;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class Box extends JComponent {
    private Image boxImage;
    private final int value = 10;

    private static final String BOX_IMAGE_PATH = "/img/box.jpeg";

    public Box(int width, int height) {
        this.setSize(width, height);
        this.setLocation(5, 5);
        loadBoxImage();
    }
    private void loadBoxImage() {
        boxImage = new ImageIcon(getClass().getResource(BOX_IMAGE_PATH)).getImage();
    }
    public void paintComponent(Graphics g) {
        g.drawImage(boxImage, 0, 0, getWidth(), getHeight(), null);
        //g.setColor(Color.ORANGE);
        //g.fillRect(0, 0, getWidth(), getHeight());
        //Border border = BorderFactory.createLineBorder(Color.black, 1);
        //this.setBorder(border);
    }

    public int getValue() {
        return value;
    }
}