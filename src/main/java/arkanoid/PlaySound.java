package arkanoid;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class PlaySound {

    private File nock;
    private File board;
    private File dead;

    public PlaySound() {
        nock = new File("src/main/resources/nock.wav");
        board = new File("src/main/resources/board.wav");
        dead = new File("src/main/resources/dead.wav");
    }

    public synchronized void playSound(final Sound sound) {
        new Thread(new Runnable() {
            public void run() {
                try {
                    Clip clip = AudioSystem.getClip();
                    AudioInputStream inputStream;
                    switch (sound) {
                        case BOARD:
                            inputStream = AudioSystem.getAudioInputStream(board);
                            break;
                        case NOCK:
                            inputStream = AudioSystem.getAudioInputStream(nock);
                            break;
                        case DEAD:
                            inputStream = AudioSystem.getAudioInputStream(dead);
                            break;
                        default:
                            inputStream = AudioSystem.getAudioInputStream(nock);
                            break;
                    }
                    clip.open(inputStream);
                    clip.start();
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        }).start();
    }
}