public enum Direction {
    N(0, -1, 1), E(1, 0, 2), S(0, 1, 4), W(-1, 0, 8);

    private final int dx;
    private final int dy;
    private final int bit;
    Direction opposite;

    private Direction(int dx, int dy, int bit) {
        this.bit = bit;
        this.dx = dx;
        this.dy = dy;
    }

    static {
        N.opposite = S;
        S.opposite = N;
        E.opposite = W;
        W.opposite = E;
    }

    public int getX() {
        return dx;
    }

    public int getY() {
        return dy;
    }

    public int getBit() {
        return bit;
    }
}
