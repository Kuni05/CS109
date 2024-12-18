package view.game;

import javax.swing.*;
import java.awt.*;

public class Hero extends JComponent {
    private int row;
    private int col;
    //加入英雄的图片
    private Image heroImage;
    private static final String HERO_IMAGE_PATH = "/img/hero.jpeg";

    private final int value = 20;
    private static Color color = new Color(87, 171, 220);

    private void loadHeroImage() {
        try {
            heroImage = new ImageIcon(getClass().getResource(HERO_IMAGE_PATH)).getImage();
        }
        catch (Exception e) {
            System.err.println("无法加载英雄图片：" + HERO_IMAGE_PATH);
            e.printStackTrace();
        }
    }
    public Hero(int width, int height, int row, int col) {
        this.row = row;
        this.col = col;
        this.setSize(width, height);
        this.setLocation(8, 8);
        loadHeroImage();

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(heroImage, 0, 0, getWidth(), getHeight(), null);
        //g.setColor(Color.BLACK);
        //g.fillOval(0, 0, getWidth(), getHeight());
        //g.setColor(color);
        //g.fillOval(1,1,getWidth()-2,getHeight()-2);
    }

    public int getValue() {
        return value;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }



}
