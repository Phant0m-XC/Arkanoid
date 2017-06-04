import java.awt.*;

public class Platform {

    private int x;
    private int y;
    private int height;
    private int width;
    private DrawPanel panel;

    public Platform(DrawPanel panel) {
        this.x = 550;
        this.y = 850;
        this.height = 25;
        this.width = 100;
        this.panel = panel;
    }

    public void draw(Graphics gr) {
        gr.setColor(Color.GREEN);
        gr.fillRect(x, y, width, height);
    }

    public void goLeft() {
        if (x > 0)
            x -= 25;
    }

    public void goRight() {
        if (x < 1250 - width)
            x += 25;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }
}
