package controller;

import Sound.AudioPlayer;
import model.Direction;
import model.MapMatrix;
import view.game.GamePanel;
import view.game.GridComponent;
import view.game.Hero;

import view.game.GameFrame;
import view.level.LevelFrame;
import view.login.LoginFrame;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import javax.swing.JOptionPane;

/**
 * It is a bridge to combine GamePanel(view) and MapMatrix(model) in one game.
 * You can design several methods about the game logic in this class.
 */
public class GameController {
    private final GamePanel view;
    private final MapMatrix model;

    private AudioPlayer bgmPlayer;
    private AudioPlayer failSoundPlayer;
    private AudioPlayer victorySoundPlayer;


    public GameController(GamePanel view, MapMatrix model) {
        this.view = view;
        this.model = model;
        this.stateList = new ArrayList<>();
        this.bgmPlayer = new AudioPlayer(); // 确保初始化 bgmPlayer
        this.victorySoundPlayer = new AudioPlayer();
        this.failSoundPlayer = new AudioPlayer();
        saveState(); // 保存初始状态
        view.setController(this);
        bgmPlayer.playBGM("src/Sound/BGM.wav"); // 开始播放背景音乐
    }

    //重开模块
    public void restartGame() {
        //这个函数是新加的
        //System.out.println("Do restart game here");
        this.model.resetMapMatrix();
        this.view.restartGame();
        this.view.restartSteps();
        //bgmPlayer.stopBGM(); // 停止背景音乐
        //bgmPlayer.playBGM("src/Sound/BGM.wav"); // 重新播放背景音乐
    }

    public boolean doMove(int row, int col, Direction direction) {
        saveState();
        // 在移动之前保存当前状态是undo部分的需要
        GridComponent currentGrid = view.getGridComponent(row, col);
        //target row can column.记做tRow和tCol在接下来判断tRow处是1，0，2分类讨论即可
        int tRow = row + direction.getRow();
        int tCol = col + direction.getCol();
        GridComponent targetGrid = view.getGridComponent(tRow, tCol);
        int[][] map = model.getMatrix();
        if (map[tRow][tCol] == 1) {
            return false;
        }
//        if(map[tRow][tCol] == 2&&map[tRow + row][tCol + col]==1){
//            return false;
//        }
        else if (map[tRow][tCol] == 0 || map[tRow][tCol] == 2) {
            //update hero in MapMatrix
            model.getMatrix()[row][col] -= 20;
            model.getMatrix()[tRow][tCol] += 20;
            //Update hero in GamePanel
            Hero h = currentGrid.removeHeroFromGrid();
            targetGrid.setHeroInGrid(h);
            //Update the row and column attribute in hero
            h.setRow(tRow);
            h.setCol(tCol);
            return true;
        }
        else if (map[tRow][tCol] == 10 || map[tRow][tCol] == 12) {
            int boxNewRow = tRow + direction.getRow();
            int boxNewCol = tCol + direction.getCol();

            if (map[boxNewRow][boxNewCol] == 0 || map[boxNewRow][boxNewCol] == 2) {
//                model.getMatrix()[row][col] -= 20;
                //人的位置替换
                model.getMatrix()[row][col] -= 20;
                model.getMatrix()[tRow][tCol] += 20;

//                model.getMatrix()[tRow][tCol] = (map[tRow][tCol] == 12) ? 2 : 0; // 如果规则？是箱子推到了就不能再推了
                //箱子被推到boxNew
                model.getMatrix()[tRow][tCol] -= 10;
                model.getMatrix()[boxNewRow][boxNewCol] += 10;

                Hero h = currentGrid.removeHeroFromGrid();
                targetGrid.setHeroInGrid(h);
                GridComponent boxNewGrid = view.getGridComponent(boxNewRow, boxNewCol);
                boxNewGrid.setBoxInGrid(targetGrid.removeBoxFromGrid());

                h.setRow(tRow);
                h.setCol(tCol);
                if (checkVictory()) {
                    showVictoryPopup();
                }
                if(checkFail()){
                    showFailPopup();
                }
                return true;
            }
        }
        return false;
    }

    //todo: add other methods such as loadGame, saveGame...


    //结束模块
    private boolean checkVictory() {
        int[][] map = model.getMatrix();

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == 2 || map[i][j] == 22) {
                    return false;
                }
            }
        }
        return true;
    }
    public boolean checkFail(){int[][] map = model.getMatrix();

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == 10) {
                    int wallsAround = 0;
                    if (i - 1 >= 0 && map[i - 1][j] == 1) {
                        wallsAround++;
                    }
                    if (i + 1 < map.length && map[i + 1][j] == 1) {
                        wallsAround++;
                    }
                    if (j - 1 >= 0 && map[i][j - 1] == 1) {
                        wallsAround++;
                    }
                    if (j + 1 < map[i].length && map[i][j + 1] == 1) {
                        wallsAround++;
                    }
                    if (wallsAround >= 2) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    private void showFailPopup() {
        gameEnd(false);
        JOptionPane.showMessageDialog(null, "Fail! ribald fool!", "Conratulations", JOptionPane.INFORMATION_MESSAGE);

        restartGame();
        failSoundPlayer.stopSound();
    }
    private void showVictoryPopup() {
//        int timeSpent = TimeController.getTimeRemaining();// 获取游戏时间
//        int steps = view.getSteps();
//        String username = "user1234"; // 用户名，可以从登录信息中获取
//        int level= LevelFrame.getCurrentLevelNumber(); // 当前关卡
//        if(level>0)
//            saveLevelData(username, level, timeSpent, steps);
        gameEnd(true);
        JOptionPane.showMessageDialog(null, "Victory! You have completed the level!", "Congratulations", JOptionPane.INFORMATION_MESSAGE);

        restartGame();
        victorySoundPlayer.stopSound();
    }
    //结束+音乐结束模块
    public void gameEnd(boolean isVictory) {
        bgmPlayer.stopBGM(); // 停止背景音乐
        if (isVictory) {
            victorySoundPlayer.playSound("src/Sound/victory.wav");
        } else {
            failSoundPlayer.playSound("src/Sound/Viva_La_Vida-Coldplay.320.wav");
        }

    }


    //保存 检查 加载文件
    public void saveGame(String filePath) {
        File file = new File(filePath);
        System.out.println(file.getAbsolutePath());
        try {
            if(file.createNewFile()){
                System.out.println("File created successfully");
            } else {
                System.out.println("File already exists");
            }
            int[][] matrix = model.getMatrix();
            FileWriter writer = new FileWriter(file);
            for(int i = 0; i < model.getHeight(); i++) {
                for(int j = 0; j < model.getWidth(); j++) {
                    writer.write(matrix[i][j] + ",");
                }
                writer.write("\n");
            }
            writer.write("steps:" + view.getSteps() + "\n");
            writer.close();
            System.out.println("File written successfully");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    public boolean checkSaveFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("File does not exist");
            return false;
        }
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            int row = 0;

            while ((line = reader.readLine()) != null) {
                if (row < model.getHeight()) {
                    String[] values = line.split(",");
                    if (values.length != model.getWidth()) {
                        System.out.println("Wrong: file's row isn't correct");
                        reader.close();
                        return false;
                    }
                    for (String value : values) {
                        try {
                            Integer.parseInt(value);
                        } catch (NumberFormatException e) {
                            System.out.println("Wrong: file includes !number ");
                            reader.close();
                            return false;
                        }
                    }
                } else {
                    if (!line.startsWith("steps:")) {
                        System.out.println("Wrong: file doesn't contain steps");
                        reader.close();
                        return false;
                    }
                    try {
                        Integer.parseInt(line.split(":")[1]);
                    } catch (NumberFormatException e) {
                        System.out.println("错误：步数信息格式不正确");
                        reader.close();
                        return false;
                    }
                }
                row++;
            }
            if (row != model.getHeight() + 1) {
                System.out.println("错误：保存文件的行数不正确");
                reader.close();
                return false;
            }
            reader.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public void loadGame(String filePath) {
        if (!checkSaveFile(filePath)) {
            System.out.println("保存文件检查失败");
            return;
        }

        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            int[][] matrix = new int[model.getHeight()][model.getWidth()];
            int row = 0;
            int steps = 0;

            while ((line = reader.readLine()) != null) {
                if (row < model.getHeight()) {
                    String[] values = line.split(",");
                    for (int col = 0; col < values.length; col++) {
                        matrix[row][col] = Integer.parseInt(values[col]);
                    }
                } else {
                    steps = Integer.parseInt(line.split(":")[1]);
                }
                row++;
            }

            model.setMatrix(matrix);
            view.updateView();
            view.setSteps(steps);
            reader.close();
            System.out.println("游戏加载成功");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("错误：无法加载保存文件");
        }
    }
//    //完成自保存
//    private void saveLevelData(String username, int level, long timeSpent, int steps) {
//        String filePath = String.format("C:\\Users\\Lenovo\\Desktop\\java project\\user %s.txt", username);
//        File file = new File(filePath);
//        try {
//            if (!file.exists()) {
//                file.createNewFile();
//            }
//            List<String> lines = new ArrayList<>(Arrays.asList(Files.readAllLines(file.toPath()).toArray(new String[0])));
//            while (lines.size() < 6) { // 假设最多6个关卡
//                lines.add("level" + (lines.size() + 1) + ": time: null steps: null");
//            }
//            lines.set(level - 1, String.format("level%d: time: %ds steps: %d", level, timeSpent, steps));
//            Files.write(file.toPath(), lines);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }


    //回撤模块
    private List<GameState> stateList; // 用于保存每一步状态
    private void saveState() {
        int[][] currentState = copyMatrix(model.getMatrix());
        int steps = view.getSteps();
        if (stateList.isEmpty() || !isSameMatrix(currentState, stateList.get(stateList.size() - 1).matrix)) {
            stateList.add(new GameState(currentState, steps));
        }
    }
    private int[][] copyMatrix(int[][] original) {
        int[][] copy = new int[original.length][original[0].length];
        for (int i = 0; i < original.length; i++) {
            System.arraycopy(original[i], 0, copy[i], 0, original[i].length);
        }
        return copy;
    }
    private boolean isSameMatrix(int[][] matrix1, int[][] matrix2) {
        if (matrix1.length != matrix2.length) return false;
        for (int i = 0; i < matrix1.length; i++) {
            if (matrix1[i].length != matrix2[i].length) return false;
            for (int j = 0; j < matrix1[i].length; j++) {
                if (matrix1[i][j] != matrix2[i][j]) return false;
            }
        }
        return true;
    }
    public void undo() {
        int steps = view.getSteps();
        if (steps > 0) {
            GameState previousState = stateList.get(steps - 1);
            model.setMatrix(previousState.matrix);
            view.updateView();
            view.setSteps(previousState.steps);
            stateList.remove(steps); // 移除当前状态
            System.out.println("撤销成功");
            view.requestFocusInWindow(); // 确保键盘事件正常捕获
        } else {
            JOptionPane.showMessageDialog(null, "你已经退无可退", "提示", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    // 用于保存游戏状态的内部类
    private static class GameState {
        int[][] matrix;
        int steps;

        GameState(int[][] matrix, int steps) {
            this.matrix = matrix;
            this.steps = steps;
        }
    }


}
