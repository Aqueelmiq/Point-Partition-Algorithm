/**
 * Created by aqueelmiqdad on 11/24/16.
 */
public class Lines {

    double loc;
    char dir;

    public Lines(double loc, char dir) {
        this.loc = loc;
        this.dir = dir;
    }

    public Lines(char dir, double loc) {
        this.loc = loc;
        this.dir = dir;
    }

    @Override
    public String toString() {
        return dir + " " + loc;
    }
}
