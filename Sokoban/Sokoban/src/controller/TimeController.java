package controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class TimeController {

    private int timeRemaining;
    private Timer timer;
    private JLabel timerLabel;

    public TimeController(JLabel timerLabel, int initialTime) {
        this.timerLabel = timerLabel;
        this.timeRemaining = initialTime;
        this.timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (timeRemaining > 0) {
                    timeRemaining--;
                    updateTimerLabel();
                } else {
                    timer.stop();
                    onTimeUp();
                }
            }
        });

        updateTimerLabel();
    }

    public void start() {
        timer.start();
    }

    public void pause() {
        timer.stop();
    }

    public void reset(int newTime) {
        timer.stop();
        this.timeRemaining = newTime;
        updateTimerLabel();
    }

    private void updateTimerLabel() {
        timerLabel.setText("Time: " + timeRemaining + "s");
    }

    private void onTimeUp() {
        JOptionPane.showMessageDialog(null, "Time's up!", "Game Over", JOptionPane.WARNING_MESSAGE);

    }

    // 获取剩余时间（秒）
    public int getTimeRemaining() {
        return timeRemaining;
    }
}
