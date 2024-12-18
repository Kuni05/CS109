package view.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import Sound.AudioPlayer;
import controller.GameController;
import model.MapMatrix;
import model.Direction;
import controller.TimeController;
import userdata.UserInformation;
import view.FrameUtil;
import javax.swing.Box;

public class GameFrame extends JFrame {

    private GameController controller;
    private TimeController timeController;
    private JButton restartBtn;

    private JLabel stepLabel;
    private JLabel timerLabel; // 添加计时器显示标签
    private AudioPlayer bgmPlayer;//音乐

    private JButton saveBtn; // 保存按钮

    public void setUserInfo(UserInformation userInfo) {
        this.userInfo = userInfo;
    }

    private JButton loadBtn; // 加载按钮
    private JButton undoBtn; // 撤销按钮

    private UserInformation userInfo;//添加用户信息对象

    private JButton upBtn;
    private JButton downBtn;
    private JButton rightBtn;
    private JButton leftBtn;

    private GamePanel gamePanel;

    public GameFrame(int width, int height, MapMatrix mapMatrix) {
        this.setTitle("2024 CS109 Project Demo");
        this.setSize(width, height);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(240, 248, 255));
        this.setContentPane(mainPanel);


        gamePanel = new GamePanel(mapMatrix);
        this.controller = new GameController(gamePanel, mapMatrix);
        mainPanel.add(gamePanel, BorderLayout.CENTER);


        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        sidePanel.setBackground(new Color(240, 248, 255));
        sidePanel.setPreferredSize(new Dimension(200, height));
        mainPanel.add(sidePanel, BorderLayout.EAST);


        timerLabel = new JLabel("Time: 0s");
        timerLabel.setFont(new Font("Serif", Font.BOLD, 20));
        timerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        stepLabel = new JLabel("Steps: 0");
        stepLabel.setFont(new Font("Serif", Font.ITALIC, 20));
        stepLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        sidePanel.add(Box.createVerticalStrut(20));
        sidePanel.add(timerLabel);
        sidePanel.add(Box.createVerticalStrut(20));
        sidePanel.add(stepLabel);
        gamePanel.setStepLabel(stepLabel);

        timeController = new TimeController(timerLabel, 120);


        restartBtn = createButton("Restart");
        saveBtn = createButton("Save");
        loadBtn = createButton("Load");
        upBtn = createButton("Up");
        downBtn = createButton("Down");
        leftBtn = createButton("Left");
        rightBtn = createButton("Right");
        undoBtn = createButton("undo");


        sidePanel.add(Box.createVerticalStrut(30));
        sidePanel.add(restartBtn);
        sidePanel.add(Box.createVerticalStrut(15));
        sidePanel.add(saveBtn);
        sidePanel.add(Box.createVerticalStrut(15));
        sidePanel.add(loadBtn);
        sidePanel.add(Box.createVerticalStrut(15));
        sidePanel.add(undoBtn);
        sidePanel.add(Box.createVerticalStrut(15));


        sidePanel.add(upBtn);
        sidePanel.add(Box.createVerticalStrut(15));
        JPanel directionPanel = new JPanel();
        directionPanel.setLayout(new GridLayout(2, 3, 10, 10));
        directionPanel.setBackground(new Color(240, 248, 255));
        directionPanel.add(new JLabel());
        directionPanel.add(upBtn);
        directionPanel.add(new JLabel());
        directionPanel.add(leftBtn);
        directionPanel.add(downBtn);
        directionPanel.add(rightBtn);

        sidePanel.add(directionPanel);


        addActionListeners();

        this.setLocationRelativeTo(null);
        timeController.start();

        //音乐BGM
        bgmPlayer = new AudioPlayer();
        bgmPlayer.playBGM("Sokoban/src/Sound/BGM.wav");


//原       //保存按钮监听器
//        this.saveBtn.addActionListener(e -> {
//            String filePath = JOptionPane.showInputDialog(this, "Input save file path:");
//            controller.saveGame(filePath);
//        });
//        //加载按钮监听器
//        this.loadBtn.addActionListener(e -> {
//            String filePath = JOptionPane.showInputDialog(this, "Input load file path:");
//            controller.loadGame(filePath);
//        });
//        //保存功能
//        //自动保存功能
//        autoSaveTimer = new Timer(15 * 1000, new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                controller.saveGame(AUTO_SAVE_PATH);
//                System.out.println("自动保存成功：" + AUTO_SAVE_PATH);
//            }
//        });
//        autoSaveTimer.start();
//        //退出保存功能
//        this.addWindowListener(new WindowAdapter() {
//            @Override
//            public void windowClosing(WindowEvent e) {
//                controller.saveGame(AUTO_SAVE_PATH);
//                System.out.println("在退出时保存成功：" + AUTO_SAVE_PATH);
//                dispose();
//            }
//        });



        // 保存按钮监听器
        this.saveBtn.addActionListener(e -> {
            if (userInfo != null && userInfo.getUsername() != null) {
                controller.saveGame(getSaveFilePath(userInfo.getUsername()));
            }
            else {
                JOptionPane.showMessageDialog(this, "Failed to save game: Username is not set.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        // 加载按钮监听器
        this.loadBtn.addActionListener(e -> {
            if (userInfo != null && userInfo.getUsername() != null) {
                controller.loadGame(getSaveFilePath(userInfo.getUsername()));
            }
            else {
                JOptionPane.showMessageDialog(this, "Failed to load game: Username is not set.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    // 保存功能
        // 自动保存功能
        autoSaveTimer = new Timer(15 * 1000, e -> {
            if (userInfo != null && userInfo.getUsername() != null) {
                controller.saveGame(getSaveFilePath(userInfo.getUsername()));
                System.out.println("自动保存成功：" + getSaveFilePath(userInfo.getUsername()));
            }
        });
        autoSaveTimer.start();

        // 退出保存功能
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (userInfo != null && userInfo.getUsername() != null) {
                    controller.saveGame(getSaveFilePath(userInfo.getUsername()));
                    System.out.println("在退出时保存成功：" + getSaveFilePath(userInfo.getUsername()));
                }
                dispose();
            }
        });






        this.undoBtn.addActionListener(e -> { controller.undo(); });




    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.PLAIN, 18));
        return button;
    }

    private void addActionListeners() {
        restartBtn.addActionListener(e -> {
            controller.restartGame();
            gamePanel.requestFocusInWindow();
            timeController.reset(120);
            timeController.start();
        });

//        saveBtn.addActionListener(e -> {
//            String filePath = JOptionPane.showInputDialog(this, "Input save file path:");
//            controller.saveGame(filePath);
//        });

//        loadBtn.addActionListener(e -> {
//            String filePath = JOptionPane.showInputDialog(this, "Input load file path:");
//            controller.loadGame(filePath);
//            gamePanel.requestFocusInWindow();
//        });

        saveBtn.addActionListener(e -> {
            if (userInfo != null && userInfo.getUsername() != null) {
                controller.saveGame(userInfo.getUsername());
            } else {
                JOptionPane.showMessageDialog(this, "Failed to save game: Username is not set.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });



        upBtn.addActionListener(e -> {
            gamePanel.doMoveUp();
            gamePanel.requestFocusInWindow();
        });

        downBtn.addActionListener(e -> {
            gamePanel.doMoveDown();
            gamePanel.requestFocusInWindow();
        });

        leftBtn.addActionListener(e -> {
            gamePanel.doMoveLeft();
            gamePanel.requestFocusInWindow();
        });

        rightBtn.addActionListener(e -> {
            gamePanel.doMoveRight();
            gamePanel.requestFocusInWindow();
        });
    }

    //窗口关闭时停止计时器
    public void dispose() {
        if (autoSaveTimer != null) {
            autoSaveTimer.stop();
        }
        super.dispose();
    }
    private Timer autoSaveTimer;
//    private static final String AUTO_SAVE_PATH = "C:\\Users\\Lenovo\\Desktop\\Sokoban\\Sokoban\\data\\autosave.txt";

    private static String getSaveFilePath(String username) {
        return "C:\\Users\\Lenovo\\Desktop\\Sokoban\\Sokoban\\data\\" + "user_" + username + "_save.txt";

    }


}