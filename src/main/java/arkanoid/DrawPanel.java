package arkanoid;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

public class DrawPanel extends JPanel {

    private Arkanoid arkanoid;
    private Platform platform;
    private Ball ball;
    private Mapper mapper;

    public DrawPanel() {
        this.setFocusable(true);
        this.requestFocus();
        this.addKeyListener(new GameController());
        platform = new Platform(this);
        ball = new Ball(this, platform.getX() + platform.getWidth() / 2, platform.getY());
        try {
            mapper = new Mapper(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        arkanoid = new Arkanoid(this, platform, ball, mapper);
        new Thread(arkanoid).start();
    }

    @Override
    protected void paintComponent(Graphics gr) {
        gr.fillRect(0, 0, 1250, 900);
        arkanoid.draw(gr);
        for(int i = 0; i < mapper.getLines().size(); i++){
            mapper.getLines().get(i).draw(gr);
        }
    }

    class GameController implements KeyListener {

        public void keyTyped(KeyEvent e) {
        }

        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_LEFT)
                platform.goLeft();
            if (e.getKeyCode() == KeyEvent.VK_RIGHT)
                platform.goRight();
        }

        public void keyReleased(KeyEvent e) {
        }
    }
}
