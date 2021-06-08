package PrvKol;

import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class F1Test {

    public static void main(String[] args) {
        F1Race f1Race = new F1Race();
        f1Race.readResults(System.in);
        f1Race.printSorted(System.out);
    }

}
class Lap implements Comparable<Lap> {
    private String time;

    public Lap(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    @Override
    public int compareTo(Lap o) {
        return time.compareTo(o.time);
    }

    @Override
    public String toString() {
        return time;
    }
}
class F1Driver {
    private String name;
    private List<Lap> laps;

    private F1Driver(String name, List<Lap> laps) {
        this.name = name;
        this.laps = laps;
    }

    public static F1Driver getInstance(String line) {
        String[] parts = line.split("\\s+");
        return new F1Driver(parts[0], Arrays.stream(parts).skip(0).map(Lap::new).collect(Collectors.toList()));
    }
    public String bestTime(){
        return laps.stream().sorted().collect(Collectors.toList()).get(0).toString();
    }

    @Override
    public String toString() {
        return String.format("%-11s %s", name, bestTime());
    }
}
class F1Race {
    List<F1Driver> drivers;

    public F1Race() {
        this.drivers = new ArrayList<>();
    }

    public void readResults(InputStream in) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        drivers = reader.lines().map(F1Driver::getInstance).collect(Collectors.toList());
    }

    public void printSorted(PrintStream out) {
        PrintWriter writer = new PrintWriter(out);
        AtomicInteger in = new AtomicInteger(0);
        drivers.stream()
                .sorted(Comparator.comparing(F1Driver::bestTime))
                .forEach(item -> writer.printf("%d. %s\n", in.incrementAndGet(), item));
        writer.flush();
    }
    // vashiot kod ovde

}