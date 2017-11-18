package arkanoid;

import javax.swing.*;
import java.awt.*;

public class Ball {
    private int x;
    private int y;
    private int size;
    private int speedX;
    private int speedY;
    private JPanel panel;
    private PlaySound playSound;
    public Direction vertical;
    public Direction horizontal;


    public Ball(JPanel panel, int x, int y, PlaySound playSound) {
        size = 25;
        this.panel = panel;
        this.x = x - size;
        this.y = y - size;
        speedX = 5;
        speedY = 5;
        vertical = Direction.UP;
        horizontal = Direction.RIGHT;
        this.playSound = playSound;
    }

    public void draw(Graphics gr) {
        gr.setColor(Color.RED);
        gr.fillArc(x, y, size, size, 0, 360);
        panel.repaint();
    }

    public void go() {
        if (horizontal == Direction.RIGHT)
            x += speedX;
        else if (horizontal == Direction.LEFT)
            x -= speedX;
        if (vertical == Direction.UP)
            y -= speedY;
        else if (vertical == Direction.DOWN)
            y += speedY;
        checkBounds();
    }

    private void checkBounds() {
        if (x >= 1250 - size) {
            playSound.playSound(Sound.BOARD);
            x = 1250 - size;
            changeDirection(vertical, Direction.LEFT);
            changeMoveAngle();
        }
        if (x <= 0) {
            playSound.playSound(Sound.BOARD);
            x = 0;
            changeDirection(vertical, Direction.RIGHT);
            changeMoveAngle();
        }
        if (y <= 0) {
            playSound.playSound(Sound.BOARD);
            y = 0;
            changeDirection(Direction.DOWN, horizontal);
            changeMoveAngle();
        }
        if (y >= 900 - size) {
            playSound.playSound(Sound.BOARD);
            y = 900 - size;
            changeDirection(Direction.STOP, Direction.STOP);
            changeMoveAngle();
        }
    }

    protected void changeDirection(Direction vertical, Direction horizontal) {
        if (vertical != null)
            this.vertical = vertical;
        if (horizontal != null)
            this.horizontal = horizontal;
    }

    //Изменение угла движения
    protected void changeMoveAngle() {
        speedX = (int) (Math.random() * 100 % 5 + 5);
        speedY = (int) (Math.random() * 100 % 5 + 5);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void resetBallPosition(int x, int y) {
        changeDirection(Direction.UP, Direction.RIGHT);
        this.x = x - size;
        this.y = y - size;
    }

    public int getSize() {
        return size;
    }
}