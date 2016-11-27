/**
 * Created by aqueelmiqdad on 11/24/16.
 */
public class Line {

    double loc;
    char dir;

    public Line(double loc, char dir) {
        this.loc = loc;
        this.dir = dir;
    }

    @Override
    public String toString() {
        return dir + " " + loc;
    }
}
