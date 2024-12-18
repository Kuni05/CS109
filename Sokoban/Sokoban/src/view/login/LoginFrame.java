package view.login;

import userdata.UserInformation;
import view.FrameUtil;
import view.level.LevelFrame;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private JTextField username;
    private JPasswordField password;
    private JButton submitBtn;
    private JButton resetBtn;
    private JButton registerBtn;
    private JButton guestBtn;
    private LevelFrame levelFrame;

    private UserInformation userId;//添加用户信息对象

    public LoginFrame(int width, int height) {
        this.setTitle("Login Frame");
        this.setSize(width, height);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        // Set layout manager
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(230, 230, 250));
        this.add(panel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Username label and text field
        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(new Font("Arial", Font.BOLD, 14));
        userLabel.setForeground(new Color(25, 25, 112)); // 深蓝色字体
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(userLabel, gbc);

        username = new JTextField(15);
        username.setBackground(Color.WHITE);
        username.setForeground(Color.BLACK);
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(username, gbc);

        // Password label and text field
        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(new Font("Arial", Font.BOLD, 14));
        passLabel.setForeground(new Color(25, 25, 112)); // 深蓝色字体
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(passLabel, gbc);

        password = new JPasswordField(15);
        password.setBackground(Color.WHITE);
        password.setForeground(Color.BLACK);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(password, gbc);

        // Confirm button
        submitBtn = new JButton("Confirm");
        submitBtn.setFont(new Font("Arial", Font.PLAIN, 14));
        submitBtn.setOpaque(true);
        submitBtn.setContentAreaFilled(true);
        submitBtn.setBackground(new Color(60, 179, 113));
        submitBtn.setForeground(Color.BLACK);
        submitBtn.setFocusPainted(false);
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(submitBtn, gbc);

        // Reset button
        resetBtn = new JButton("Reset");
        resetBtn.setFont(new Font("Arial", Font.PLAIN, 14));
        resetBtn.setOpaque(true);
        resetBtn.setContentAreaFilled(true);
        resetBtn.setBackground(new Color(255, 99, 71)); // 设置背景色为番茄红
        resetBtn.setForeground(Color.BLACK);
        resetBtn.setFocusPainted(false);
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(resetBtn, gbc);

        // Register button
        registerBtn = new JButton("Register");
        registerBtn.setFont(new Font("Arial", Font.PLAIN, 14));
        registerBtn.setOpaque(true);
        registerBtn.setContentAreaFilled(true);
        registerBtn.setBackground(new Color(70, 130, 180));
        registerBtn.setForeground(Color.BLACK);
        registerBtn.setFocusPainted(false);
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(registerBtn, gbc);

        // Guest button
        guestBtn = new JButton("Guest");
        guestBtn.setFont(new Font("Arial", Font.PLAIN, 14));
        guestBtn.setOpaque(true);
        guestBtn.setContentAreaFilled(true);
        guestBtn.setBackground(new Color(100, 149, 237));
        guestBtn.setForeground(Color.BLACK);
        guestBtn.setFocusPainted(false);
        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(guestBtn, gbc);


        // Add action listeners
        submitBtn.addActionListener(e -> {
            String inputUsername = username.getText().trim();
            String inputPassword = new String(password.getPassword()).trim();

            if (inputUsername.isEmpty() || inputPassword.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Username and Password cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (checkLogin(inputUsername, inputPassword)) {
                JOptionPane.showMessageDialog(this, "Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                userId = new UserInformation(inputUsername);//创建用户信息对象
                if (this.levelFrame != null) {
                    this.levelFrame.setUserInfo(userId);// 传递用户信息对象
                    this.levelFrame.setVisible(true);
                    this.setVisible(false);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });


        resetBtn.addActionListener(e -> {
            username.setText("");
            password.setText("");
        });

        registerBtn.addActionListener(e -> {
            RegisterFrame registerFrame = new RegisterFrame(300, 300);
            registerFrame.setVisible(true);
            this.setVisible(false);
        });

        guestBtn.addActionListener(e -> {
            userId = new UserInformation("guest"); // 为Guest模式创建用户信息对象
            if (this.levelFrame != null) {
                this.levelFrame.setUserInfo(userId); // 传递用户信息对象
                this.levelFrame.setVisible(true);
                this.setVisible(false);
            }
        });

    }
    private boolean checkLogin(String inputUsername, String inputPassword) {
        try {
            java.util.List<String> lines = java.nio.file.Files.readAllLines(java.nio.file.Paths.get("users.txt"));

            for (String line : lines) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String username = parts[0].trim();
                    String password = parts[1].trim();

                    if (username.equals(inputUsername) && password.equals(inputPassword)) {
                        return true;
                    }
                }
            }
        } catch (java.io.IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to read user data file.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    public void setLevelFrame(LevelFrame levelFrame) {
        this.levelFrame = levelFrame;
    }

}
