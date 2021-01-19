public enum Direction {
    N(1, 0, -1), S(2, 0, 1), E(4, 1, 0), W(8, -1, 0);

    private final int dx;
    private final int dy;
    private final int bit;
    Direction opposite;

    Direction(int bit, int dx, int dy) {
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
