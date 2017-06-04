import javax.swing.*;
import java.awt.*;

public class Launcher {

    JFrame frame;

    public void launch() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        DrawPanel panel = new DrawPanel();
        frame.getContentPane().add(BorderLayout.CENTER, panel);
        frame.setSize(1260, 930);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        Launcher launcher = new Launcher();
        launcher.launch();
    }
}
