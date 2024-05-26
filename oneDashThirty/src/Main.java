import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Point {
    private int x;
    private int y;
    private ArrayList<Point> connections = new ArrayList<>();
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public ArrayList<Point> getConnections() {
        return connections;
    }

    public void addConnection(Point point) {
        connections.add(point);
    }
}

public class Main {
    public static void main(String[] args) {
        Random rand = new Random();
        List<Point> graph = new ArrayList<Point>();
        graph.add(new Point(-21, 0));
        graph.add(new Point(-5, 0));
        graph.add(new Point(-1, 0));
        graph.add(new Point(0, 0));
        graph.add(new Point(1, 0));
        graph.add(new Point(3, 0));
        graph.add(new Point(11, 0));
        System.out.println("Points in graph:");
        for(Point p : graph) {
            System.out.print(p.getX() + " "+ p.getY());
            System.out.println();
        }
        System.out.println();
        // first huerestic, going to the nearest neighbor
        System.out.println("Nearest Neighbor");
        //nearestNeighbor(graph, 3);

        closestNeighbor(graph);

    }

    static void nearestNeighbor(List<Point> points, int start) {
        List<Point> visited = new ArrayList<>();
        Point p = points.get(start);
        System.out.println("Starting Point:");
        System.out.println("<"+p.getX() + "," + p.getY()+">");
        long sTime = System.nanoTime();
        long eTime = 0;
        while(visited.size() < points.size()) {
            Point closestUnvisited = null;
            int closestXDiff = 0;
            int closestYDiff = 0;
            for(int i = 0; i < points.size(); i++) {
                if(points.get(i) != p) {
                    if(closestUnvisited == null && !visited.contains(points.get(i))) {
                        closestUnvisited = points.get(i);
                        closestXDiff = Math.abs(closestUnvisited.getX() - p.getX());
                        closestYDiff = Math.abs(closestUnvisited.getY() - p.getY());
                    }else if(!visited.contains(points.get(i))){
                        int xDiff = Math.abs(points.get(i).getX() - p.getX());
                        int yDiff = Math.abs(points.get(i).getY() - p.getY());

                        if (xDiff + yDiff < closestXDiff + closestYDiff) {
                            closestUnvisited = points.get(i);
                            closestXDiff = Math.abs(closestUnvisited.getX() - p.getX());
                            closestYDiff = Math.abs(closestUnvisited.getY() - p.getY());
                        }
                    }
                }
            }
            visited.add(p);
            if(visited.size() == points.size()) {
                eTime = System.nanoTime();
                break;
            }

            System.out.println("Closest Neighbor to Point <" +p.getX()+ "," + p.getY() + ">: " + closestUnvisited.getX() + " " + closestUnvisited.getY());
            System.out.println(closestUnvisited.getX() + " " + closestUnvisited.getY());
            p = closestUnvisited;
        }
        System.out.println("Elapsed Time: " + (eTime - sTime)/10000);
    }

    static void closestNeighbor(List<Point> points) {
        int n = points.size();

        for(int i = 1; i < n; i++) {
            int d = Integer.MAX_VALUE;
            int sm = 0;
            int tm = 0;
            for(int j = 0; j < n; j++) {
                for(int k = 0; k < n; k++) {
                    if(k != j) {
                        int xDist = Math.abs(points.get(k).getX() - points.get(j).getX());
                        int yDist = Math.abs(points.get(k).getY() - points.get(j).getY());
                        if(xDist + yDist < d && !points.get(j).getConnections().contains(points.get(k))) {
                            d = xDist + yDist;
                            sm = k;
                            tm = j;
                        }
                    }
                }
            }
            points.get(sm).getConnections().add(points.get(tm));
            points.get(tm).getConnections().add(points.get(sm));
            System.out.println("Connecting Point <"+points.get(sm).getX()+","+points.get(sm).getY()+"> to point " + points.get(tm).getX()+","+points.get(tm).getY());
        }
    }
}