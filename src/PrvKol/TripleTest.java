import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class TripleTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int a = scanner.nextInt();
        int b = scanner.nextInt();
        int c = scanner.nextInt();
        Triple<Integer> tInt = new Triple<Integer>(a, b, c);
        System.out.printf("%.2f\n", tInt.max());
        System.out.printf("%.2f\n", tInt.avarage());
        tInt.sort();
        System.out.println(tInt);
        float fa = scanner.nextFloat();
        float fb = scanner.nextFloat();
        float fc = scanner.nextFloat();
        Triple<Float> tFloat = new Triple<Float>(fa, fb, fc);
        System.out.printf("%.2f\n", tFloat.max());
        System.out.printf("%.2f\n", tFloat.avarage());
        tFloat.sort();
        System.out.println(tFloat);
        double da = scanner.nextDouble();
        double db = scanner.nextDouble();
        double dc = scanner.nextDouble();
        Triple<Double> tDouble = new Triple<Double>(da, db, dc);
        System.out.printf("%.2f\n", tDouble.max());
        System.out.printf("%.2f\n", tDouble.avarage());
        tDouble.sort();
        System.out.println(tDouble);
    }
}
// vasiot kod ovde
 class Triple <T extends Number & Comparable<T>> {
    List<T> nums;
    public Triple(T a, T b, T c) {
        nums = new ArrayList<T>();
        nums.add(a);
        nums.add(b);
        nums.add(c);
    }
    public double max() {
        return nums.stream().mapToDouble(item -> Double.parseDouble(String.valueOf(item))).max().orElseThrow(RuntimeException::new);
    }
    public double avarage(){
        return nums.stream().mapToDouble(item -> Double.parseDouble(String.valueOf(item))).sum() / nums.size();
    }
    public void sort() {
        nums.sort(Comparator.naturalOrder());
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < nums.size(); i++) {
            str.append(String.format("%.2f", Double.parseDouble(String.valueOf(nums.get(i)))));
            if (i != nums.size() - 1)
                str.append(" ");
        }
        return str.toString();
    }
}

