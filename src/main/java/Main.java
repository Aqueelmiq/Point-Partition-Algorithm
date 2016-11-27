
import java.util.*;

/**
 * Created by aqueelmiqdad on 11/24/16.
 */
public class Main {

    public static void main(String[] args) {


        int input = 6;
        int count = 0;

        while (input > 0) {

            count++;
            input--;

            List<Point> points;
            if (count < 10)
                points = RW.read("input/instance0" + count + ".txt");
            else
                points = RW.read("input/instance" + count + ".txt");

            HashMap<Point, ArrayList<Point>> neighborX = new HashMap<>();
            HashMap<Point, ArrayList<Point>> neighborY = new HashMap<>();

            List<Point> sortedX = new ArrayList<>(points);
            sortedX.sort((Point o1, Point o2) -> {
                if (o1.x - o2.x > 0)
                    return 1;
                else if (o1.x - o2.x < 0)
                    return -1;
                else
                    return 0;
            });

            List<Point> sortedY = new ArrayList<>(points);
            sortedY.sort((Point o1, Point o2) -> {
                if (o1.y - o2.y > 0)
                    return 1;
                else if (o1.y - o2.y < 0)
                    return -1;
                else
                    return 0;
            });

            for (Point p : points) {
                ArrayList<Point> tmp = new ArrayList<>(sortedX);
                neighborX.put(p, tmp);
                tmp = new ArrayList<>(sortedY);
                neighborY.put(p, tmp);
            }
            List<Line> sol = solve(sortedX, sortedY, neighborX, neighborY);
            //Output formatted file
            if (count < 10)
                generateOutput(sol, "greedy_solution0" + count + ".txt");
            else
                generateOutput(sol, "greedy_solution" + count + ".txt");

        }


    }

    private static void generateOutput(List<Line> sol, String trace) {

        List<String> outList = new ArrayList<>();
        outList.add(sol.size() + "");
        for (Line l : sol)
            outList.add(l.toString());
        RW.write(outList, "output/" + trace);
    }



    public static List<Line> solve2(List<Point> X, List<Point> Y, HashMap<Point, ArrayList<Point>> nbs) {

        List<Line> sol = new ArrayList<>();

        boolean end = false;
        double at = 0;
        char dir = 'q';

        //Partition until all points have no neighbors
        // O(n^4)
        int x = 0;
        while (!end) {
            x++;

            //midpoints store all the midpoint combinations for the given set of points
            //divideX stores the effectiveness of division for each line through mid point
            HashSet<Double> midpointX = new HashSet<>();
            HashMap<Double, Integer> divideX = new HashMap<>();
            HashSet<Double> midpointY = new HashSet<>();
            HashMap<Double, Integer> divideY = new HashMap<>();

            // O(n^2)
            for (int i = 1; i < Y.size(); i++) {

                midpointY.add((Y.get(i - 1).y + Y.get(i).y) / 2);
                midpointX.add((X.get(i - 1).x + X.get(i).x) / 2);

            }

            // O(n^3)
            for (double mid : midpointX) {

                HashSet<ArrayList<Point>> neighborhood = new HashSet<>();
                divideX.put(mid, 0);
                for (Point p : X) {

                    int side1 = 0, side2 = 0;
                    if (neighborhood.contains(nbs.get(p)))
                        continue;
                    neighborhood.add(nbs.get(p));
                    for (Point q : nbs.get(p)) {
                        if (q.x > mid)
                            side1++;
                        else
                            side2++;
                    }
                    divideX.put(mid, divideX.get(mid) + Math.abs(side1 - side2));

                }


            }

            // O(n^3)
            for (double mid : midpointY) {

                HashSet<ArrayList<Point>> neighborhood = new HashSet<>();
                divideY.put(mid, 0);
                for (Point p : Y) {

                    int side1 = 0, side2 = 0;
                    if (neighborhood.contains(nbs.get(p)))
                        continue;
                    neighborhood.add(nbs.get(p));
                    for (Point q : nbs.get(p)) {
                        if (q.y > mid)
                            side1++;
                        else
                            side2++;
                    }
                    divideY.put(mid, divideY.get(mid) + Math.abs(side1 - side2));

                }

            }

            int min = Integer.MAX_VALUE;

            // O(n^2)
            for (double mid : midpointX) {

                if (min > divideX.get(mid)) {
                    min = divideX.get(mid);
                    dir = 'v';
                    at = mid;
                }

            }

            // O(n^2)
            for (double mid : midpointY) {

                if (min > divideY.get(mid)) {
                    min = divideY.get(mid);
                    dir = 'h';
                    at = mid;
                }
            }

            if (dir == 'h') {

                for (Point p : Y) {

                    ArrayList<Point> neighbors = nbs.get(p);
                    HashSet<Point> remove = new HashSet<>();
                    for (Point q : neighbors) {

                        if ((q.y < at && p.y > at) || (q.y > at && p.y < at)) {
                            remove.add(q);
                        }

                    }
                    for (Point q : remove) {
                        neighbors.remove(q);
                    }
                    nbs.put(p, neighbors);
                    nbs.put(p, neighbors);

                }

            } else {

                for (Point p : X) {

                    ArrayList<Point> neighbors = nbs.get(p);
                    HashSet<Point> remove = new HashSet<>();
                    for (Point q : neighbors) {

                        if ((q.x < at && p.x > at) || (q.x > at && p.x < at)) {
                            remove.add(q);
                        }

                    }
                    for (Point q : remove) {
                        neighbors.remove(q);
                    }
                    nbs.put(p, neighbors);
                    nbs.put(p, neighbors);

                }

            }

            // See if partitioning is complete - check if no point has neighbors
            end = true;

            for (Point p : X) {
                if (nbs.get(p).size() > 1)
                    end = false;
            }


            // Add the line to solution set
            sol.add(new Line(at, dir));

        }

        return sol;
    }


    public static List<Line> solve(List<Point> X, List<Point> Y, HashMap<Point, ArrayList<Point>> nX, HashMap<Point, ArrayList<Point>> nY) {

        List<Line> sol = new ArrayList<>();

        boolean end = false;
        double at = 0;
        char dir = 'q';

        //Partition until all points have no neighbors
        // O(n^4)
        int x = 0;
        while (!end) {
            x++;

            //midpoints store all the midpoint combinations for the given set of points
            //divideX stores the effectiveness of division for each line through mid point
            HashSet<Double> midpointX = new HashSet<>();
            HashMap<Double, Integer> divideX = new HashMap<>();
            HashSet<Double> midpointY = new HashSet<>();
            HashMap<Double, Integer> divideY = new HashMap<>();

            // O(n^2)
            for (int i = 1; i < Y.size(); i++) {

                midpointY.add((Y.get(i - 1).y + Y.get(i).y) / 2);
                midpointX.add((X.get(i - 1).x + X.get(i).x) / 2);

            }

            // O(n^3)
            for (double mid : midpointX) {

                HashSet<ArrayList<Point>> neighborhood = new HashSet<>();
                divideX.put(mid, 0);
                for (Point p : X) {

                    int side1 = 0, side2 = 0;
                    if (neighborhood.contains(nX.get(p)))
                        continue;
                    neighborhood.add(nX.get(p));
                    for (Point q : nX.get(p)) {
                        if (q.x > mid)
                            side1++;
                        else
                            side2++;
                    }
                    divideX.put(mid, divideX.get(mid) + Math.abs(side1 - side2));

                }


            }

            // O(n^3)
            for (double mid : midpointY) {

                HashSet<ArrayList<Point>> neighborhood = new HashSet<>();
                divideY.put(mid, 0);
                for (Point p : Y) {

                    int side1 = 0, side2 = 0;
                    if (neighborhood.contains(nY.get(p)))
                        continue;
                    neighborhood.add(nY.get(p));
                    for (Point q : nY.get(p)) {
                        if (q.y > mid)
                            side1++;
                        else
                            side2++;
                    }
                    divideY.put(mid, divideY.get(mid) + Math.abs(side1 - side2));

                }

            }

            int min = Integer.MAX_VALUE;

            // O(n^2)
            for (double mid : midpointX) {

                if (min > divideX.get(mid)) {
                    min = divideX.get(mid);
                    dir = 'v';
                    at = mid;
                }

            }

            // O(n^2)
            for (double mid : midpointY) {

                if (min > divideY.get(mid)) {
                    min = divideY.get(mid);
                    dir = 'h';
                    at = mid;
                }
            }

            if (dir == 'h') {

                for (Point p : Y) {

                    ArrayList<Point> neighbors = nY.get(p);
                    HashSet<Point> remove = new HashSet<>();
                    for (Point q : neighbors) {

                        if ((q.y < at && p.y > at) || (q.y > at && p.y < at)) {
                            remove.add(q);
                        }

                    }
                    for (Point q : remove) {
                        neighbors.remove(q);
                    }
                    nY.put(p, neighbors);
                    nX.put(p, neighbors);

                }

            } else {

                for (Point p : X) {

                    ArrayList<Point> neighbors = nX.get(p);
                    HashSet<Point> remove = new HashSet<>();
                    for (Point q : neighbors) {

                        if ((q.x < at && p.x > at) || (q.x > at && p.x < at)) {
                            remove.add(q);
                        }

                    }
                    for (Point q : remove) {
                        neighbors.remove(q);
                    }
                    nY.put(p, neighbors);
                    nX.put(p, neighbors);

                }

            }

            // See if partitioning is complete - check if no point has neighbors
            end = true;

            for (Point p : X) {
                if (nX.get(p).size() > 1)
                    end = false;
            }


            // Add the line to solution set
            sol.add(new Line(at, dir));

        }

        System.out.println(x);

        return sol;
    }

    public static List<Line> solvegreedy(List<Point> points, HashMap<Point, ArrayList<Point>> neighbors, HashMap<Point, HashSet<Point>> ns, HashSet<Point> ps) {

        List<Line> sol = new ArrayList<>();
        HashSet<Pairs> nsep = new HashSet<>();

        for(Point a: points) {
            for(Point b: points) {
                nsep.add(new Pairs(a, b));
            }
        }


        boolean end = false;
        double at = 0;
        char dir = 'q';

        while (!end) {

            for(Pairs p: nsep) {
                for(Pairs q: nsep) {
                    int count = 0;
                }
            }


            for (Point p : points) {
                System.out.println(neighbors.get(p));
            }

            // See if partitioning is complete - check if no point has neighbors
            end = true;

            for (Point p : points) {
                if (neighbors.get(p).size() > 1)
                    end = false;
            }

            // Add the line to solution set
            sol.add(new Line(at, dir));
            System.out.println(sol);

        }

        return sol;

    }

}
