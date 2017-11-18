package arkanoid;

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
    private DrawPanel panel;

    public Mapper(DrawPanel panel) throws IOException {
        colors = new Color[]{Color.BLUE, Color.CYAN, Color.GREEN, Color.MAGENTA, Color.ORANGE, Color.PINK,
                Color.RED, Color.YELLOW};
        this.panel = panel;
        lines = new ArrayList<Line>();
        addMapping();
    }

    public List<Line> getLines() {
        return lines;
    }

    public void addMapping() throws IOException {
        if(lines.size() > 0)
            lines.clear();
        File file = new File("src/main/resources/map.txt");
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
}
