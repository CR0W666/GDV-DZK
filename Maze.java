import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Path2D;
import java.util.*;
import javax.swing.*;

/*TODO 

Solver kinda
Swing render
Save//Load of maze

*/

public class Maze extends JPanel {

    int[][] map; // map array
    LinkedList<Integer> solution;
    static int mazeSize; // size of maze
    static final int CELL_SIZE = 25;
    static final int SPACING = 25;

    public Maze() {

        Maze.mazeSize = 32; // sets the X, Y map size to this num
        MazeNav mn = new MazeNav();
        MazeGen mg = new MazeGen(mazeSize, mn);
        this.map = mg.getMap();
        solution = mn.getSolution();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame f = new JFrame();
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setTitle("Maze Generator");
            f.setResizable(false);
            f.add(new Maze(), BorderLayout.CENTER);
            f.pack();
            f.setLocationRelativeTo(null);
            f.setVisible(true);
        });

    }

    @Override
    public void paintComponent(Graphics gg) {
        super.paintComponent(gg);
        Graphics2D g = (Graphics2D) gg;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g.setStroke(new BasicStroke(5));
        g.setColor(Color.black);

        // draw maze
        for (int x = 0; x < mazeSize; x++) {
            for (int y = 0; y < mazeSize; y++) {

                int s = SPACING + x * CELL_SIZE;
                int e = SPACING + y * CELL_SIZE;

                if ((map[y][x] & 1) == 0) // N
                    g.drawLine(s, e, s + CELL_SIZE, e);

                if ((map[y][x] & 2) == 0) // S
                    g.drawLine(s, e + CELL_SIZE, s + CELL_SIZE, e + CELL_SIZE);

                if ((map[y][x] & 4) == 0) // E
                    g.drawLine(s + CELL_SIZE, e, s + CELL_SIZE, e + CELL_SIZE);

                if ((map[y][x] & 8) == 0) // W
                    g.drawLine(s, e, s, e + CELL_SIZE);
            }
        }

        // draw pathfinding animation
        int offset = SPACING + CELL_SIZE / 2;

        Path2D path = new Path2D.Float();
        path.moveTo(offset, offset);

        for (int pos : solution) {
            int s = pos % mazeSize * CELL_SIZE + offset;
            int e = pos / mazeSize * CELL_SIZE + offset;
            path.lineTo(s, e);
        }

        g.setColor(Color.orange);
        g.draw(path);

        g.setColor(Color.blue);
        g.fillOval(offset - 5, offset - 5, 10, 10);

        g.setColor(Color.green);
        int s = offset + (mazeSize - 1) * CELL_SIZE;
        int e = offset + (mazeSize - 1) * CELL_SIZE;
        g.fillOval(s - 5, e - 5, 10, 10);

    }

    void animate() {
        try {
            Thread.sleep(50L);
        } catch (InterruptedException ignored) {
        }
        repaint();
    }
}