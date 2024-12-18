package view.login;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class RegisterFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton confirmBtn;
    private JButton backBtn;

    public RegisterFrame(int width, int height) {
        this.setTitle("Register Frame");
        this.setSize(width, height);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        // Set layout manager
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(240, 248, 255)); // Light blue background
        this.add(panel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Username label and text field
        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(userLabel, gbc);

        usernameField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(usernameField, gbc);

        // Password label and text field
        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(passLabel, gbc);

        passwordField = new JPasswordField(15);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(passwordField, gbc);

        // Register button
        confirmBtn = new JButton("Register");
        confirmBtn.setFont(new Font("Arial", Font.PLAIN, 14));
        confirmBtn.setBackground(new Color(60, 179, 113)); // Sea green background
        confirmBtn.setForeground(Color.DARK_GRAY);
        confirmBtn.setFocusPainted(false);
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(confirmBtn, gbc);

        // Back button
        backBtn = new JButton("Back");
        backBtn.setFont(new Font("Arial", Font.PLAIN, 14));
        backBtn.setBackground(new Color(70, 130, 180)); // Steel blue background
        backBtn.setForeground(Color.DARK_GRAY);
        backBtn.setFocusPainted(false);
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(backBtn, gbc);

        // Add action listeners
        confirmBtn.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Username and Password cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (isUsernameTaken(username)) {
                JOptionPane.showMessageDialog(this, "Username already exists. Please choose another one.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            saveUserToFile(username, password);
            JOptionPane.showMessageDialog(this, "Registration successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            clearFields();
        });

        backBtn.addActionListener(e -> {
            new LoginFrame(300, 300).setVisible(true);
            this.dispose();
        });
    }

    private boolean isUsernameTaken(String username) {
        try {
            List<String> lines = Files.readAllLines(Paths.get("users.txt"));
            for (String line : lines) {
                String[] parts = line.split(",");
                if (parts.length > 0 && parts[0].equals(username)) {
                    return true;
                }
            }
        } catch (IOException e) {
            return false;
        }
        return false;
    }

    private void saveUserToFile(String username, String password) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("users.txt", true))) {
            writer.write(username + "," + password);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to save user information.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        usernameField.setText("");
        passwordField.setText("");
    }
}
