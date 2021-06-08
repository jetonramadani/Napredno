import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class WeatherStationTest {
    public static void main(String[] args) throws ParseException {
        Scanner scanner = new Scanner(System.in);
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        int n = scanner.nextInt();
        scanner.nextLine();
        WeatherStation ws = new WeatherStation(n);
        while (true) {
            String line = scanner.nextLine();
            if (line.equals("=====")) {
                break;
            }
            String[] parts = line.split(" ");
            float temp = Float.parseFloat(parts[0]);
            float wind = Float.parseFloat(parts[1]);
            float hum = Float.parseFloat(parts[2]);
            float vis = Float.parseFloat(parts[3]);
            line = scanner.nextLine();
            Date date = df.parse(line);
            ws.addMeasurment(temp, wind, hum, vis, date);
        }
        String line = scanner.nextLine();
        Date from = df.parse(line);
        line = scanner.nextLine();
        Date to = df.parse(line);
        scanner.close();
        System.out.println(ws.total());
        try {
            ws.status(from, to);
        } catch (RuntimeException e) {
            System.out.println(e);
        }
    }
}

// vashiot kod ovde

class Information {
    private float temp;
    private float wind;
    private float hum;
    private float vis;
    private Date time;

    public Information(float temp, float wind, float hum, float vis, Date time) {
        this.temp = temp;
        this.wind = wind;
        this.hum = hum;
        this.vis = vis;
        this.time = time;
    }
    public boolean olderThan(int days, Date newT) {
        long timeMs = newT.getTime() - this.time.getTime();
        return Float.compare(timeMs/86_400_000f, days) < 0;
    }
    public  boolean isLess150sec(Information i) {
        long timeMs = i.time.getTime() - this.time.getTime();
        return Float.compare(timeMs/1000f, 150) <= 0;
    }

    public float getTemp() {
        return temp;
    }

    @Override
    public String toString() {
        return String.format("%.1f %.1f km/h %.1f%% %.1f km %s", temp, wind, hum, vis, time);
    }
    public boolean onRange(Date from, Date to) {
        return from.equals(time) || to.equals(time) || (time.after(from) && time.before(to));
    }
}
class WeatherStation {
    private List<Information> informationList;
    private Information last;
    private int days;
    WeatherStation(int days) {
        this.days = days;
        informationList = new ArrayList<>();
        last = null;
    }
    private void removeOlder(Date date) {
        informationList = informationList.stream()
                .filter(item -> item.olderThan(this.days, date))
                .collect(Collectors.toList());
    }
    public void addMeasurment(float temperature, float wind, float humidity, float visibility, Date date) {
        Information inf = new Information(temperature, wind, humidity, visibility, date);
        if (last != null) {
            if (last.isLess150sec(inf))
                return;
            removeOlder(date);
        }
        last = inf;
        informationList.add(inf);
    }
    public int total() {
        return informationList.size();
    }
    public void status(Date from, Date to) {
        float total = 0;
        int items = 0;
        for (Information f : informationList) {
            if (f.onRange(from, to)) {
                items++;
                total += f.getTemp();
                System.out.println(f);
            }
        }
        if (items == 0)
            throw  new RuntimeException();
        System.out.printf("Average temperature: %.2f\n", total / items);
    }
}