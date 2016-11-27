/**
 * Created by aqueelmiqdad on 11/24/16.
 */
public class Point {

     double x;
     double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
