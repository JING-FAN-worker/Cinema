public class Seat {
    private int row;
    private int col;
    private boolean isAvailable;

    public Seat(int row, int col, boolean isAvailable) {
        this.row = row;
        this.col = col;
        this.isAvailable = isAvailable;
    }

    public int getRow() {
        return this.row;
    }

    public int getCol() {
        return this.col;
    }

    public boolean isAvailable() {
        return this.isAvailable;
    }

    public void setAvailable(boolean available) {
        this.isAvailable = available;
    }

    @Override
    public String toString() {
        return String.format("Seat %d-%d", this.row, this.col);
    }
}