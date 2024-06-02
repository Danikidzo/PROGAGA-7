package Objects;

public class Coordinates {
    private long x; //Поле не может быть null

    private Float y; //Поле не может быть null

    public Coordinates(long x, Float y) {
        this.x = x;
        this.y = y;
    }

    public Coordinates() {}

    public long getX() {
        return this.x;
    }

    public Float getY() {
        return this.y;
    }

    public void setX(long x) {
        this.x = x;
    }

    public void setY(Float y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "\n\tx=" + x
                + "\n\ty=" + y
                + "\n";
    }
}
