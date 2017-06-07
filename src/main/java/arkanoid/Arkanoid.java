package arkanoid;

import java.awt.*;

public class Arkanoid extends Thread {

    private int score;
    private int gameSpeed;
    private Ball ball;
    private Platform platform;
    private DrawPanel panel;
    private Mapper mapper;

    public Arkanoid(DrawPanel panel, Platform platform, Ball ball, Mapper mapper) {
        this.score = 0;
        this.panel = panel;
        this.platform = platform;
        this.ball = ball;
        this.mapper = mapper;
        gameSpeed = 15;
    }

    public void draw(Graphics gr) {
        platform.draw(gr);
        ball.draw(gr);
        if (gr instanceof Graphics2D) {
            Graphics2D g2 = (Graphics2D) gr;
            g2.setColor(Color.GREEN);
            g2.drawString("Очки: " + score, 50, 50);
        }
    }

    //проверка встречи с платформой
    public void checkPlatform() {
        if (ball.getY() >= platform.getY() - ball.getSize() &&
                ball.getY() <= platform.getY() &&
                ball.getX() >= platform.getX() - ball.getSize() &&
                ball.getX() <= platform.getX() + platform.getWidth()) {
            ball.changeDirection(Direction.UP, null);
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
                            ball.changeDirection(null, Direction.LEFT);
                            mapper.getLines().remove(i);
                            score += 10;
                            ball.changeMoveAngle();
                        }
        }
    }

    @Override
    public void run() {
        while (true) {
            ball.go();
            checkPlatform();
            checkLines();
            try {
                Thread.sleep(gameSpeed);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            panel.repaint();
        }
    }
}