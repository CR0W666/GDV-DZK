
import java.util.Arrays;
import java.util.Collections;

public class MazeGen {

    private int size;
    private int[][] map;

    public MazeGen(int size, MazeNav mn) {
        this.size = size;
        this.map = new int[size][size];
        generateMaze(0, 0);
        mn.solve(0, map, size);
    }

    private void generateMaze(int x, int y) {
        Direction[] dirs = Direction.values();
        Collections.shuffle(Arrays.asList(dirs));
        for (Direction dir : dirs) {
            int nx = x + dir.getX();
            int ny = y + dir.getY();
            if (insideMap(ny, nx) && map[ny][nx] == 0) {
                map[y][x] |= dir.getBit();
                map[ny][nx] |= dir.opposite.getBit();
                generateMaze(nx, ny);
            }
        }
    }

    private boolean insideMap(int x, int y) {
        return (y >= 0 && y < this.size && x >= 0 && x < this.size);
    }

    public int[][] getMap() {
        return this.map;
    }

}