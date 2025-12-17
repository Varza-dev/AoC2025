import java.util.*;

import static util.InputReaderUtils.readInputLines;

public class Day8 {

    public static class Point {
        int x, y, z;

        public Point(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }

    public static class Distance implements Comparable<Distance> {
        Point a, b;
        Double distance;

        public Distance(Point a, Point b, double distance) {
            this.a = a;
            this.b = b;
            this.distance = distance;
        }

        @Override
        public int compareTo(Distance other) {
            return Double.compare(this.distance, other.distance);
        }
    }

    public static class Circuit implements Comparable<Circuit> {
        Set<Point> points;

        public Circuit() {
            points = new HashSet<>();
        }

        @Override
        public int compareTo(Circuit other) {
            return Integer.compare(this.points.size(), other.points.size());
        }
    }

    // multiply together the sizes of the 3 largest circuits
    public long makeCircuitsPt1() {
        List<String> inputLines
                = readInputLines("./day8.txt");

        List<Point> points = new ArrayList<>();
        List<Distance> distances = new ArrayList<>();

        for (String line: inputLines) {
            // 3 numbers on a line
            String[] tokens = line.split(",");
            Point newPoint = new Point(Integer.parseInt(tokens[0]),
                    Integer.parseInt(tokens[1]),
                    Integer.parseInt(tokens[2]));
            for (Point point: points) {
                double distance = distance(point, newPoint);
                distances.add(new Distance(point, newPoint, distance));
            }
            points.add(newPoint);
        }

        Collections.sort(distances);

        List<Circuit> circuits = new ArrayList<>();

        // Add all points within the first 1000 (shortest) distances to circuits
        for (int i = 0; i < 1000; i++) {
            Distance distance = distances.get(i);
            Point a = distance.a;
            Point b = distance.b;
            boolean foundA = false;
            boolean foundB = false;

            Circuit aCircuit = null, bCircuit = null;

            // If only one point is in a circuit, add the other to its circuit
            for (Circuit circuit: circuits) {
                if (circuit.points.contains(a)) {
                    foundA = true;
                    aCircuit = circuit;
                }

                if (circuit.points.contains(b)) {
                    foundB = true;
                    bCircuit = circuit;
                }
            }

            if (foundA && !foundB) {
                aCircuit.points.add(b);
            }

            if (foundB && !foundA) {
                bCircuit.points.add(a);
            }

            // If both nodes are in circuits, the new connection merges their circuits
            if (foundA && foundB && aCircuit != bCircuit) {
                aCircuit.points.addAll(bCircuit.points);
                circuits.remove(bCircuit);
            }

            // If neither node is in a circuit, create a new circuit with the two nodes
            if (!foundA && !foundB) {
                Circuit newCircuit = new Circuit();
                newCircuit.points.add(a);
                newCircuit.points.add(b);
                circuits.add(newCircuit);
            }
        }

        // Add all remaining points to singleton circuits
        for (Point point : points) {
            boolean found = false;
            for (Circuit c : circuits) {
                if (c.points.contains(point)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                Circuit singleton = new Circuit();
                singleton.points.add(point);
                circuits.add(singleton);
            }
        }

        Collections.sort(circuits);
        Collections.reverse(circuits);
        long result = 1;

        for (int i = 0; i < 3; i++) {
            result *= circuits.get(i).points.size();
        }

        return result;
    }

    public long makeCircuitsPt2() {
        List<String> inputLines
                = readInputLines("./day8.txt");

        List<Point> points = new ArrayList<>();
        List<Distance> distances = new ArrayList<>();

        for (String line: inputLines) {
            // 3 numbers on a line
            String[] tokens = line.split(",");
            Point newPoint = new Point(Integer.parseInt(tokens[0]),
                    Integer.parseInt(tokens[1]),
                    Integer.parseInt(tokens[2]));
            for (Point point: points) {
                double distance = distance(point, newPoint);
                distances.add(new Distance(point, newPoint, distance));
            }
            points.add(newPoint);
        }

        Collections.sort(distances);
        int dIndex = 0;

        List<Circuit> circuits = new ArrayList<>();
        Set<Point> remainingPoints = new HashSet<>(Set.copyOf(points));

        long result = 1;

        while (!remainingPoints.isEmpty()) {
            Distance distance = distances.get(dIndex);
            dIndex++;
            Point a = distance.a;
            Point b = distance.b;
            result = (long) a.x * b.x;
            boolean foundA = false;
            boolean foundB = false;

            Circuit aCircuit = null, bCircuit = null;

            // If only one point is in a circuit, add the other to its circuit
            for (Circuit circuit: circuits) {
                if (circuit.points.contains(a)) {
                    foundA = true;
                    aCircuit = circuit;
                }

                if (circuit.points.contains(b)) {
                    foundB = true;
                    bCircuit = circuit;
                }
            }

            if (foundA && !foundB) {
                aCircuit.points.add(b);
                remainingPoints.remove(b);
            }

            if (foundB && !foundA) {
                bCircuit.points.add(a);
                remainingPoints.remove(a);
            }

            // If both nodes are in circuits, the new connection merges their circuits
            if (foundA && foundB && aCircuit != bCircuit) {
                aCircuit.points.addAll(bCircuit.points);
                circuits.remove(bCircuit);
            }

            // If neither node is in a circuit, create a new circuit with the two nodes
            if (!foundA && !foundB) {
                Circuit newCircuit = new Circuit();
                newCircuit.points.add(a);
                newCircuit.points.add(b);
                remainingPoints.remove(a);
                remainingPoints.remove(b);
                circuits.add(newCircuit);
            }
        }

        return result;
    }

    // Calculates Euclidean distance between two 3D points
    public double distance(Point a, Point b) {
        return Math.sqrt(Math.pow(b.x - a.x, 2) + Math.pow(b.y - a.y, 2) + Math.pow(b.z - a.z, 2));
    }
}
