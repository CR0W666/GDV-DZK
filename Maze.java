import java.awt.*;
import java.awt.geom.Path2D;
import java.util.*;
import javax.swing.*;

/*TODO 

solver button - show solve path onclick
Save//Load of maze

*/

public class Maze extends JPanel {

    int[][] map; // map array
    LinkedList<Integer> solution;
    static int size = 32; // size of maze
    static final int CELL_SIZE = 25;
    static final int SPACING = 25;

    public Maze() {

        MazeNav mn = new MazeNav();
        MazeGen mg = new MazeGen(size, mn);
        this.map = mg.getMap();
        solution = mn.getSolution();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame f = new JFrame();
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setTitle("Maze Generator");
            f.setResizable(false);
            f.setPreferredSize(new Dimension(870, 880));
            f.setBackground(Color.white);
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
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {

                int x = SPACING + c * CELL_SIZE;
                int y = SPACING + r * CELL_SIZE;

                if ((map[r][c] & 1) == 0) // N
                    g.drawLine(x, y, x + CELL_SIZE, y);

                if ((map[r][c] & 2) == 0) // S
                    g.drawLine(x, y + CELL_SIZE, x + CELL_SIZE, y + CELL_SIZE);

                if ((map[r][c] & 4) == 0) // E
                    g.drawLine(x + CELL_SIZE, y, x + CELL_SIZE, y + CELL_SIZE);

                if ((map[r][c] & 8) == 0) // W
                    g.drawLine(x, y, x, y + CELL_SIZE);
            }
        }

        // draw pathfinding animation
        int offset = SPACING + CELL_SIZE / 2;

        Path2D path = new Path2D.Float();
        path.moveTo(offset, offset);

        for (int pos : solution) {
            int x = pos % size * CELL_SIZE + offset;
            int y = pos / size * CELL_SIZE + offset;
            path.lineTo(x, y);
        }

        g.setColor(Color.orange);
        g.draw(path);

        g.setColor(Color.blue);
        g.fillOval(offset - 5, offset - 5, 10, 10);

        g.setColor(Color.green);
        int x = offset + (size - 1) * CELL_SIZE;
        int y = offset + (size - 1) * CELL_SIZE;
        g.fillOval(x - 5, y - 5, 10, 10);
    }

    void animate() {
        try {
            Thread.sleep(50L);
        } catch (InterruptedException ignored) {
        }
        repaint();
    }
}