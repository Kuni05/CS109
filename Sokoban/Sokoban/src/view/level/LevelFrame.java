package view.level;

import controller.FrameController;
import model.MapMatrix;
import userdata.UserInformation;
import view.FrameUtil;
import view.game.GameFrame;

import javax.swing.*;
import java.awt.*;

public class LevelFrame extends JFrame {
    private static FrameController frameController = new FrameController();

    private UserInformation userInfo;////////////////////////////////



    public LevelFrame(int width, int height) {
        this.setTitle("Select Level");
        this.setSize(width, height);
        this.setLayout(null);


        this.getContentPane().setBackground(new Color(240, 248, 255));

        JLabel titleLabel = new JLabel("Choose a Level！", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(25, 25, 112)); //
        titleLabel.setBounds(0, 20, width, 200);
        this.add(titleLabel);


        int buttonSize = 60;
        int spacing = 20;
        int startX = 20;
        int startY = height / 2 - buttonSize - spacing;


        JButton level1Btn = createLevelButton("Level 1", startX, startY, buttonSize);
        JButton level2Btn = createLevelButton("Level 2", startX + (buttonSize + spacing) * 1, startY, buttonSize);
        JButton level3Btn = createLevelButton("Level 3", startX + (buttonSize + spacing) * 2, startY, buttonSize);
        JButton level4Btn = createLevelButton("Level 4", startX + (buttonSize + spacing) * 3, startY, buttonSize);
        JButton level5Btn = createLevelButton("Level 5", startX + (buttonSize + spacing) * 4, startY, buttonSize);
        JButton level6Btn = createLevelButton("Level 6", startX + (buttonSize + spacing) * 5, startY, buttonSize);


        level1Btn.addActionListener(l ->{
            userInfo.setSelectedLevel(level1Btn.getText());
            loadGameFrame(new int[][]{
                    {1, 1, 1, 1, 1, 1},
                    {1, 20, 0, 0, 0, 1},
                    {1, 0, 0, 10, 2, 1},
                    {1, 0, 2, 10, 0, 1},
                    {1, 1, 1, 1, 1, 1},
            });
        });

        level2Btn.addActionListener(l -> {
            userInfo.setSelectedLevel(level2Btn.getText());
            loadGameFrame(new int[][]{
                    {1, 1, 1, 1, 1, 1, 0},
                    {1, 20, 0, 0, 0, 1, 1},
                    {1, 0, 10, 10, 0, 0, 1},
                    {1, 0, 1, 2, 0, 2, 1},
                    {1, 0, 0, 0, 0, 0, 1},
                    {1, 1, 1, 1, 1, 1, 1},
            });
        });

        level3Btn.addActionListener(l ->{
            userInfo.setSelectedLevel(level3Btn.getText());
            loadGameFrame(new int[][]{
                    {0, 0, 1, 1, 1, 1, 0},
                    {1, 1, 1, 0, 0, 1, 0},
                    {1, 20, 0, 10, 2, 1, 1},
                    {1, 0, 0, 0, 2, 0, 1},
                    {1, 0, 1, 10, 0, 0, 1},
                    {1, 0, 0, 0, 0, 0, 1},
                    {1, 1, 1, 1, 1, 1, 1},
            });
        });

        level4Btn.addActionListener(l ->{
            userInfo.setSelectedLevel(level4Btn.getText());
            loadGameFrame(new int[][]{
                    {0, 1, 1, 1, 1, 1, 0},
                    {1, 1, 20, 0, 0, 1, 1},
                    {1, 0, 0, 1, 0, 0, 1},
                    {1, 0, 10, 12, 10, 0, 1},
                    {1, 0, 0, 2, 0, 0, 1},
                    {1, 1, 0, 2, 0, 1, 1},
                    {0, 1, 1, 1, 1, 1, 0},
            });
        });

        level5Btn.addActionListener(l ->{
            userInfo.setSelectedLevel(level5Btn.getText());
            loadGameFrame(new int[][]{
                    {1, 1, 1, 1, 1, 1, 0, 0},
                    {1, 0, 0, 0, 0, 1, 1, 1},
                    {1, 0, 0, 0, 2, 2, 0, 1},
                    {1, 0, 10, 10, 10, 20, 0, 1},
                    {1, 0, 0, 1, 0, 2, 0, 1},
                    {1, 1, 1, 1, 1, 1, 1, 1},
            });
        });

        level6Btn.addActionListener(l ->{
            userInfo.setSelectedLevel(level6Btn.getText());
            loadGameFrame(new int[][]{
                    {1, 1, 1, 1, 1, 1, 1, 1},
                    {1, 2, 10, 0, 2, 2, 0, 1},
                    {1, 0, 10, 10, 10, 20, 1, 1},
                    {1, 0, 0, 10, 0, 2, 0, 1},
                    {1, 0, 0, 1, 0, 2, 0, 1},
                    {1, 1, 1, 1, 1, 1, 1, 1},
            });
        });





        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frameController.setLevelFrame(this);
    }

    private JButton createLevelButton(String text, int x, int y, int size) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setBounds(x, y, size, size);
        button.setBackground(new Color(100, 149, 237));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setBorder(BorderFactory.createLineBorder(new Color(25, 25, 112), 2));
        this.add(button);
        return button;
    }

    private void loadGameFrame(int[][] mapMatrixArray) {
        MapMatrix mapMatrix = new MapMatrix(mapMatrixArray);
        GameFrame gameFrame = new GameFrame(600, 450, mapMatrix);
        gameFrame.setUserInfo(userInfo);
        this.setVisible(false);
        gameFrame.setVisible(true);
    }

    public static FrameController getFrameController() {
        return frameController;
    }

    public void setUserInfo(UserInformation userInfo){
        this.userInfo = userInfo;
    }
}
