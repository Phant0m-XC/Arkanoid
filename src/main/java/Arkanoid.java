import java.awt.*;

public class Arkanoid extends Thread {

    private int score;
    private int gameSpeed;
    private Ball ball;
    private Platform platform;
    private DrawPanel panel;
    private Mapper mapper;
    /*private int totalLinesCount; //увеличиваем скорость игры в зависимости от процента оставшихся линий
    private boolean canChangeSpeed;*/

    public Arkanoid(DrawPanel panel, Platform platform, Ball ball, Mapper mapper) {
        this.score = 0;
        this.panel = panel;
        this.platform = platform;
        this.ball = ball;
        this.mapper = mapper;
        gameSpeed = 10;
        /*totalLinesCount = mapper.getLines().size();
        canChangeSpeed = false;*/
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
        if (ball.getY() == platform.getY() - ball.getSize() &&
                ball.getX() >= platform.getX() - ball.getSize() &&
                ball.getX() <= platform.getX() + platform.getWidth())
            ball.changeDirection(Direction.UP, null);
    }

    public void checkLines() {
        for (int i = 0; i < mapper.getLines().size(); i++) {
            //Удар шарика сверху
            if (ball.getY() == mapper.getLines().get(i).getY() - ball.getSize() &&
                    ball.getX() >= mapper.getLines().get(i).getX() - ball.getSize() &&
                    ball.getX() <= mapper.getLines().get(i).getX() + mapper.getLines().get(i).getWidth()) {
                ball.changeDirection(Direction.UP, null);
                mapper.getLines().remove(i);
                score += 10;
            } else
                //Удар шарика снизу
                if (ball.getY() == mapper.getLines().get(i).getY() + mapper.getLines().get(i).getHeight() &&
                        ball.getX() >= mapper.getLines().get(i).getX() - ball.getSize() &&
                        ball.getX() <= mapper.getLines().get(i).getX() + mapper.getLines().get(i).getWidth()) {
                    ball.changeDirection(Direction.DOWN, null);
                    mapper.getLines().remove(i);
                    score += 10;
                } else
                    //Удар шарика справа
                    if (ball.getX() == mapper.getLines().get(i).getX() + mapper.getLines().get(i).getWidth() &&
                            ball.getY() >= mapper.getLines().get(i).getY() - ball.getSize() &&
                            ball.getY() <= mapper.getLines().get(i).getY() + mapper.getLines().get(i).getHeight()) {
                        ball.changeDirection(Direction.RIGHT, null);
                        mapper.getLines().remove(i);
                        score += 10;
                    } else
                        //Удар шарика слева
                        if (ball.getX() == mapper.getLines().get(i).getX() - ball.getSize() &&
                                ball.getY() >= mapper.getLines().get(i).getY() - ball.getSize() &&
                                ball.getY() <= mapper.getLines().get(i).getY() + mapper.getLines().get(i).getHeight()) {
                            ball.changeDirection(Direction.LEFT, null);
                            mapper.getLines().remove(i);
                            score += 10;
                        }
        }
    }

    private void randomChangeDirection(){

    }

    /*private void speedUp() {
        int leftover = mapper.getLines().size() / totalLinesCount * 100;
        if (leftover > 80)
            gameSpeed -= 10;
        else if (leftover > 60)
            gameSpeed -= 10;
        else if (leftover > 40)
            gameSpeed -= 10;
        else if(leftover > 20)
            gameSpeed -= 10;
    }*/

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