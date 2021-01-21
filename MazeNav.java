import java.util.LinkedList;

public class MazeNav {

    private LinkedList<Integer> solution = new LinkedList<>();
    // private LinkedList<Integer> steps = new LinkedList<>();

    public void startSolve(int size, int[][] map) {
        solution.clear();
        solve(0, map, size);
    }

    public boolean solve(int pos, int[][] map, int size) {
        if (pos == size * size - 1)
            return true;

        int c = pos % size;
        int r = pos / size;

        for (Direction dir : Direction.values()) {
            int nc = c + dir.getX();
            int nr = r + dir.getY();

            if (insideMap(nr, nc, size) && (map[r][c] & dir.getBit()) != 0 && (map[nr][nc] & 16) == 0) {

                int newPos = nr * size + nc;
                // steps.add(nr * size + nc);
                solution.add(newPos);

                map[nr][nc] |= 16;

                if (solve(newPos, map, size))
                    return true;

                solution.removeLast();
                map[nr][nc] &= ~16;
            }
        }

        return false;
    }

    private boolean insideMap(int x, int y, int size) {
        return (y >= 0 && y < size && x >= 0 && x < size);
    }

    public LinkedList<Integer> getSolution() {
        return this.solution;
    }

    // public LinkedList<Integer> getSteps() {
    // return this.steps;
    // }

}
