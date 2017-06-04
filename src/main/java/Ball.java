import javax.swing.*;
import java.awt.*;

public class Ball {
    private int x;
    private int y;
    private int size;
    private int speed;
    private JPanel panel;
    private Direction vertical;
    private Direction horizontal;

    public Ball(JPanel panel, int x, int y) {
        size = 25;
        this.panel = panel;
        this.x = x - size;
        this.y = y - size;
        speed = 5;
        vertical = Direction.UP;
        horizontal = Direction.RIGHT;
    }

    public void draw(Graphics gr) {
        gr.setColor(Color.RED);
        gr.fillArc(x, y, size, size, 0, 360);
        panel.repaint();
    }

    public void go() {
        if (horizontal == Direction.RIGHT)
            x += speed;
        else if (horizontal == Direction.LEFT)
            x -= speed;
        if (vertical == Direction.UP)
            y -= speed;
        else if (vertical == Direction.DOWN)
            y += speed;
        checkBounds();
    }

    private void checkBounds() {
        if (x == 1250 - size)
            changeDirection(vertical, Direction.LEFT);
        if (x == 0)
            changeDirection(vertical, Direction.RIGHT);
        if (y == 0)
            changeDirection(Direction.DOWN, horizontal);
        if (y == 900 - size)
            changeDirection(Direction.STOP, Direction.STOP);
    }

    protected void changeDirection(Direction vertical, Direction horizontal) {
        if (vertical != null)
            this.vertical = vertical;
        if (horizontal != null)
            this.horizontal = horizontal;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSize() {
        return size;
    }
}