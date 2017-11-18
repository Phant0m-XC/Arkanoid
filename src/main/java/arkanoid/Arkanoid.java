package arkanoid;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Arkanoid extends Thread {

    private int score;
    private int gameSpeed;
    private Ball ball;
    private Platform platform;
    private DrawPanel panel;
    private Mapper mapper;
    private PlaySound playSound;
    private List<Line> lifeCount;
    private int dialogButton;

    public Arkanoid(DrawPanel panel, Platform platform, Ball ball, Mapper mapper, PlaySound playSound) {
        this.score = 0;
        this.panel = panel;
        this.platform = platform;
        this.ball = ball;
        this.mapper = mapper;
        this.playSound = playSound;
        gameSpeed = 15;
        lifeCount = new ArrayList<Line>();
        addNewLife();
    }

    public void draw(Graphics gr) {
        platform.draw(gr);
        ball.draw(gr);
        if (gr instanceof Graphics2D) {
            Graphics2D g2 = (Graphics2D) gr;
            g2.setColor(Color.GREEN);
            g2.drawString("Очки: " + score, 50, 20);
        }
    }

    //проверка встречи с платформой
    public void checkPlatform() {
        if (ball.getY() >= platform.getY() - ball.getSize() &&
                ball.getY() <= platform.getY() &&
                ball.getX() >= platform.getX() - ball.getSize() &&
                ball.getX() <= platform.getX() + platform.getWidth()) {
            ball.changeDirection(Direction.UP, null);
            playSound.playSound(Sound.BOARD);
            //ball.changeMoveAngle();
        }
    }

    public void checkLines() {
        for (int i = 0; i < mapper.getLines().size(); i++) {
            //Удар шарика сверху
            if (ball.getY() >= mapper.getLines().get(i).getY() - ball.getSize() &&
                    ball.getY() <= mapper.getLines().get(i).getY() &&
                    ball.getX() >= mapper.getLines().get(i).getX() - ball.getSize() &&
                    ball.getX() <= mapper.getLines().get(i).getX() + mapper.getLines().get(i).getWidth()) {
                playSound.playSound(Sound.NOCK);
                ball.changeDirection(Direction.UP, null);
                mapper.getLines().remove(i);
                score += 10;
                ball.changeMoveAngle();
            } else
                //Удар шарика снизу
                if (ball.getY() <= mapper.getLines().get(i).getY() + mapper.getLines().get(i).getHeight() &&
                        ball.getY() >= mapper.getLines().get(i).getY() + mapper.getLines().get(i).getHeight() &&
                        ball.getX() >= mapper.getLines().get(i).getX() - ball.getSize() &&
                        ball.getX() <= mapper.getLines().get(i).getX() + mapper.getLines().get(i).getWidth()) {
                    playSound.playSound(Sound.NOCK);
                    ball.changeDirection(Direction.DOWN, null);
                    mapper.getLines().remove(i);
                    score += 10;
                    ball.changeMoveAngle();
                } else
                    //Удар шарика справа
                    if (ball.getX() <= mapper.getLines().get(i).getX() + mapper.getLines().get(i).getWidth() &&
                            ball.getX() >= mapper.getLines().get(i).getX() + mapper.getLines().get(i).getWidth() &&
                            ball.getY() >= mapper.getLines().get(i).getY() - ball.getSize() &&
                            ball.getY() <= mapper.getLines().get(i).getY() + mapper.getLines().get(i).getHeight()) {
                        playSound.playSound(Sound.NOCK);
                        ball.changeDirection(null, Direction.RIGHT);
                        mapper.getLines().remove(i);
                        score += 10;
                        ball.changeMoveAngle();
                    } else
                        //Удар шарика слева
                        if (ball.getX() >= mapper.getLines().get(i).getX() - ball.getSize() &&
                                ball.getX() <= mapper.getLines().get(i).getX() &&
                                ball.getY() >= mapper.getLines().get(i).getY() - ball.getSize() &&
                                ball.getY() <= mapper.getLines().get(i).getY() + mapper.getLines().get(i).getHeight()) {
                            playSound.playSound(Sound.NOCK);
                            ball.changeDirection(null, Direction.LEFT);
                            mapper.getLines().remove(i);
                            score += 10;
                            ball.changeMoveAngle();
                        }
        }
    }

    private int isWin(){
        return mapper.getLines().size();
    }

    private int isDead() {
        if (ball.getY() == 875) {
            playSound.playSound(Sound.DEAD);
            if (lifeCount.size() > 0) {
                lifeCount.remove(lifeCount.size() - 1);
                platform.resetPlatformPosition();
                ball.resetBallPosition(platform.getX() + platform.getWidth() / 2, platform.getY());
            }
        }
        return lifeCount.size();
    }

    private void addNewLife() {
        lifeCount.add(new Line(panel, 1020, 15, Color.GREEN));
        lifeCount.add(new Line(panel, 1080, 15, Color.GREEN));
        lifeCount.add(new Line(panel, 1140, 15, Color.GREEN));
    }

    private void resetGame() {
        score = 0;
        addNewLife();
        try {
            mapper.addMapping();
        } catch (IOException e) {
            e.printStackTrace();
        }
        platform.resetPlatformPosition();
        ball.resetBallPosition(platform.getX() + platform.getWidth() / 2, platform.getY());
    }

    public List<Line> getLifeCount() {
        return lifeCount;
    }

    @Override
    public void run() {
        while (true) {
            ball.go();
            checkPlatform();
            checkLines();
            if (isDead() == 0 || isWin() == 0) {
                dialogButton = JOptionPane.showOptionDialog(null, "Do you want start new game?", "GameOver",
                        0, JOptionPane.QUESTION_MESSAGE, null, null, null);
                if (dialogButton == JOptionPane.YES_OPTION)
                    resetGame();
                else
                    break;
            }
            try {
                Thread.sleep(gameSpeed);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            panel.repaint();
        }
    }
}