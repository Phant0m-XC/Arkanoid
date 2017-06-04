import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Mapper {

    private List<Line> lines;
    private Color[] colors;

    public Mapper(DrawPanel panel) throws IOException {
        colors = new Color[] {Color.BLUE, Color.CYAN, Color.GREEN, Color.MAGENTA, Color.ORANGE, Color.PINK,
                Color.RED, Color.YELLOW};
        lines = new ArrayList<Line>();
        File file = new File("K:\\MyProjects\\Java\\41\\arkanoid\\src\\main\\resources\\map.txt");
        FileReader mapFileReader = new FileReader(file);
        BufferedReader reader = new BufferedReader(mapFileReader);
        String line;
        int numOfLine = 0;
        while ((line = reader.readLine()) != null) {
            for (int i = 0; i < line.length(); i++) {
                if (line.charAt(i) == '-') {
                    int index = (int) (Math.random() * 10 % 8);
                    lines.add(new Line(panel, i * 50, numOfLine * 25, colors[index]));
                }
            }
            numOfLine++;
        }
    }

    public List<Line> getLines() {
        return lines;
    }
}
