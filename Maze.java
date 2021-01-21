import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Path2D;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import javax.swing.*;

/*TODO 

solver button - show solve path onclick
Save//Load of maze
comments

*/

public class Maze extends JPanel {

    private static final String DIRECTORY = System.getProperty("user.dir");
    private final static String OLD_SAVE_LOC = DIRECTORY + File.separator + "oldmaze.txt";
    static final int CELL_SIZE = 25;
    static final int SPACING = 25;

    public static int size = 32; // size of maze
    public static int[][] map; // map array
    public static LinkedList<Integer> solution = new LinkedList<>();
    // public static LinkedList<Integer> steps;
    public static boolean generated = false;

    public static MazeNav mn = new MazeNav();
    public static MazeGen mg = new MazeGen(size);
    public static Maze me;

    public Maze() {
        Maze.me = this;
        // this will probably change with buttons

    }

    public static boolean isSolutionDone() {
        return generated;
    }

    public static void solutionDone(boolean state) {
        generated = state;
    }

    public static int[][] getMap() {
        return map;
    }

    public static void setMap(int[][] nMap) {
        generated = false;
        map = nMap;
    }

    public LinkedList<Integer> getSolution() {
        return solution;
    }

    public static void setSolution(LinkedList<Integer> nSolution) {
        solution = nSolution;
    }

    // public LinkedList<Integer> getSteps() {
    // return steps;
    // }

    // public static void setSteps(LinkedList<Integer> nSteps) {
    // steps = nSteps;
    // }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame f = new JFrame();
            JButton bSave = new JButton("Save Maze");
            JButton bLoad = new JButton("Load Maze");
            JButton bSolve = new JButton("Solve Maze");
            setButtons(f, bSave, bLoad, bSolve);
            setFrame(f, bSave, bLoad, bSolve);

        });
    }

    public static void setButtons(JFrame f, JButton bSave, JButton bLoad, JButton bSolve) {
        setSaveBttn(f, bSave);
        setLoadBttn(f, bLoad);
        setSolveBttn(f, bSolve);
    }

    public static void setSaveBttn(JFrame f, JButton bSave) {
        bSave.setBounds(350, 850, 95, 30);
        bSave.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("savebtn");
                saveMaze();
                // me.repaint();
            }
        });

    }

    public static void setLoadBttn(JFrame f, JButton bLoad) {
        bLoad.setBounds(450, 850, 95, 30);
        bLoad.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("loadbtn");
                loadMaze();
                generated = false;
                me.repaint();
            }
        });

    }

    public static void setSolveBttn(JFrame f, JButton bSolve) {
        bSolve.setBounds(250, 850, 95, 30);
        bSolve.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("solvebtn");
                solveMaze();
                System.out.println(generated);
                me.repaint();

            }
        });

    }

    public static void setFrame(JFrame f, JButton bSave, JButton bLoad, JButton bSolve) {
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setTitle("Maze Generator");
        f.setResizable(false);
        f.setPreferredSize(new Dimension(870, 950));
        f.setBackground(Color.white);
        f.add(bSave);
        f.add(bLoad);
        f.add(bSolve);
        newMaze();

        f.add(new Maze(), BorderLayout.CENTER);
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

    public static void newMaze() {
        mg.startGen();
        setMap(mg.getMap());
        // me.repaint();

    }

    public static void loadMaze() {
        BufferedReader br;
        String line;
        int[][] map;
        int size;

        try {

            br = new BufferedReader(new FileReader(OLD_SAVE_LOC));

            while ((line = br.readLine()) != null) {

                String[] sizeXRest = line.split("[|]");
                line = sizeXRest[1];
                size = Integer.parseInt(sizeXRest[0]);
                map = new int[size][size];

                String[] cells = line.split(" ");

                int r = 0;
                int c = 0;
                for (String cell : cells) {

                    if (c >= size) {
                        r++;
                        c = 0;
                    }
                    map[r][c] = Integer.parseInt(cell);
                    c++;
                }
                setMap(map);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public static void saveMaze() {
        StringBuilder sb = new StringBuilder();
        sb.append(size + "|");
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                sb.append(map[r][c]);
                sb.append(" ");
            }
        }

        try {

            File file = new File(OLD_SAVE_LOC);
            if (!file.exists()) {
                file.createNewFile();
            }

            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            bw.write(sb.toString());

            bw.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public static void solveMaze() {
        System.out.println("sol1:" + solution);
        System.out.println(solution.size());
        solution.clear();
        System.out.println("sol2:" + solution);
        System.out.println(solution.size());
        mn.startSolve(size, getMap());
        System.out.println(solution.size());
        setSolution(mn.getSolution());
        System.out.println(solution.size());
        // setSteps(mn.getSteps());
        solutionDone(true);

    }

    public void terminalRender() {
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                System.out.print(" " + map[r][c] + " ");
            }
            System.out.println("");
        }
    }

    @Override
    public void paintComponent(Graphics gg) {
        terminalRender();

        super.paintComponent(gg);
        Graphics2D g = (Graphics2D) gg;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g.setStroke(new BasicStroke(5));
        g.setColor(Color.black);

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
        System.out.println("b " + generated + " | " + isSolutionDone());
        if (isSolutionDone()) {
            System.out.println("a");
            // draw pathfinding animation
            int offset = SPACING + CELL_SIZE / 2;

            Path2D path = new Path2D.Float();
            path.moveTo(offset, offset);

            for (int pos : solution) {

                int x = pos % size * CELL_SIZE + offset;
                int y = pos / size * CELL_SIZE + offset;
                path.lineTo(x, y);
                g.draw(path);

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
    }

}