import java.util.Arrays;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;

/**
 * Created by drproduck on 1/30/17.
 */
public class convexHull {
    public static void main(String[] args) {
        Point[] array = {new Point(0, 2), new Point(2, 2), new Point(2, 0), new Point(0, 0), new Point(0.5, 0.5), new Point(1, 1), new Point(1.5, 1.5), new Point(1.7, 1.3)};
        System.out.println(GrahamScanWithStack(array).toString());
        System.out.println(BruteForce(array));
    }

    public static Stack<Point> GrahamScanWithStack(Point[] points) {
        int lowY = 0;
        for (int i = 1; i < points.length; i++) {
            if (points[lowY].y > points[i].y) {
                lowY = i;
            }
            else if (points[lowY].y == points[i].y) {
                lowY = (points[lowY].x < points[i].x) ? lowY : i;
            }
        }
        swap(points, 0, lowY);
        Arrays.sort(points, 1, points.length-1, (Point a, Point b) -> Double.compare(slope(a, points[0]), slope(b, points[0])));
        Stack<Point> st = new Stack<>();
        st.push(points[0]);
        st.push(points[1]);
        for (int i = 2; i <= points.length; i++) {
            Point current;
            if (i == points.length) {
                current = points[0];
            } else current = points[i];
           Point last1 = st.pop();
           Point last2 = st.peek();
                while (ccw(last2, last1, current) <= 0) {
                    if (st.size() == 1) {
                        break;
                    }
                    last1 = st.pop();
                    last2 = st.peek();
                }
            st.push(last1);
            if (i!=points.length)
                st.push(points[i]);
        }
        return st;
    }

    public static Set<Point> BruteForce(Point[] points) {
        Set<Point> set = new TreeSet<>();
        for (int i = 0; i < points.length; i++) {
            for (int j = i+1;j<points.length;j++) {
                Function f = new Function(points[i], points[j]);
                double sign = 0;
                boolean consistent = true;
                for (int k = 0; k < points.length; k++) {
                    double a = f.sign(points[k]);
                    if (sign!=0) {
                        if (a!=0 && a != sign) {
                            consistent = false;
                            break;
                        }
                    } else sign = a;
                }
                if (consistent) {
                    set.add(points[i]);
                    set.add(points[j]);
                }
            }
        }
        return set;
    }

    static double slope(Point p1, Point p2) {
        return ((p1.y - p2.y) / (p1.x - p2.x));
    }
    static void swap(Point[] points, int p1, int p2) {
        if (p1 == p2) {
            return;
        } else {
            Point temp = points[p1];
            points[p1] = points[p2];
            points[p2] = temp;
        }
    }

    static double ccw(Point p1, Point p2, Point p3) {
       return  (p2.x - p1.x)*(p3.y - p1.y) - (p2.y - p1.y)*(p3.x - p1.x);
    }

    static class Point implements Comparable<Point>{
        double x,y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public static double angle(Point p1, Point p2, Point p3) {
            return  (p2.x - p1.x)*(p3.y - p1.y) - (p2.y - p1.y)*(p3.x - p1.x);
        }

        public static Point random(int rangeX, int rangeY) {
            int a = (int)(Math.random() * 2 * rangeX) - rangeX;
            int b = (int) (Math.random() * 2 * rangeY) - rangeY;
            return new Point(a, b);
        }

        public String toString() {
            return "(" + x + ", " + y + ")";
        }

        @Override
        public int compareTo(Point o) {
            if (this.x != o.x) {
                return Double.compare(this.x, o.x);
            } else return Double.compare(this.y, o.y);
        }

        public int hashCode() {
            return (int) (this.x + this.y);
        }
    }

    static class Function {
            double a,b; //represents y + ax + b = 0;

            public Function(Point x, Point y) {
                double delta = (x.y - y.y) / (x.x - y.x);
                a = -1 * delta;
                b = -(x.y - delta * x.x);
            }

            public double sign(Point x) {
                return Math.signum(x.y + a * x.x + b);
            }
        }
}

