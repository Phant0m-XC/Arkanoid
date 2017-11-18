package arkanoid;

import javax.swing.*;
import java.awt.*;

public class Line {

    JPanel panel;
    private int x;
    private int y;
    private int height;
    private int width;
    private Color color;

    public Line(JPanel panel, int x, int y, Color color) {
        this.panel = panel;
        this.x = x;
        this.y = y;
        height = 25;
        width = 50;
        this.color = color;
    }

    public void draw(Graphics gr) {
        gr.setColor(color);
        gr.fillRect(x, y, width - 1, height - 1); // -1 для того, чтоб были промежутки между линиями
        panel.repaint();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}