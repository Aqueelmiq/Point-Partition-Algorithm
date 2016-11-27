/**
 * Created by aqueelmiqdad on 11/26/16.
 */
public class Pairs {


    Point a;
    Point b;
    double midX;
    double midY;

    public Pairs(Point a, Point b) {
        this.a = a;
        this.b = b;
        midX = (a.x + b.x)/2;
        midY = (a.y + b.y)/2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pairs)) return false;

        Pairs pairs = (Pairs) o;

        if(a.equals(pairs.a) && b.equals(pairs.b))
            return true;
        if(a.equals(pairs.b) && b.equals(pairs.a))
            return true;
        return false;

    }

    @Override
    public int hashCode() {
        return 31*a.hashCode() + 31*b.hashCode();
    }
}
