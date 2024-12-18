package Sound;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class AudioPlayer {

    private Clip bgmClip;
    private Clip soundClip;

    // 播放背景音乐（循环播放）
    public void playBGM(String filePath) {
        bgmClip = createClip(filePath);
        if (bgmClip != null) {
            bgmClip.loop(Clip.LOOP_CONTINUOUSLY);
            bgmClip.start();
        }
    }

    // 停止背景音乐
    public void stopBGM() {
        if (bgmClip != null && bgmClip.isRunning()) {
            bgmClip.stop();
            bgmClip.close();
        }
    }

    // 播放音效（胜利或失败）
    public void playSound(String filePath) {
        soundClip = createClip(filePath);
        if (soundClip != null) {
            soundClip.start();
        }
    }

    // 停止音效
    public void stopSound() {
        if (soundClip != null && soundClip.isRunning()) {
            soundClip.stop();
            soundClip.close();
        }
    }


    // 创建并返回Clip对象
    private Clip createClip(String filePath) {
        try {
            File audioFile = new File(filePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            AudioFormat format = audioStream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            Clip audioClip = (Clip) AudioSystem.getLine(info);
            audioClip.open(audioStream);
            return audioClip;
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
        return null;
    }
}


