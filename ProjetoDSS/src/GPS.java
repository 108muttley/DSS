import java.util.Objects;

public class GPS {
    private int x;
    private int y;


    public GPS(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public GPS(){
        setX(0);
        setY(0);
    }
    public GPS( GPS g){
        setX(g.getX());
        setY(g.getY());
    }


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GPS gps = (GPS) o;
        return x == gps.x && y == gps.y;
    }

    @Override
    public String toString() {
        return "GPS{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
