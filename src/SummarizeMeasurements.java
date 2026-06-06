import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class SummarizeMeasurements {
    public static void main(String[] args) throws IOException {
        List<Double> values = readValues(Path.of("data", "measurements.csv"));

        double sum = 0.0;
        double min = values.get(0);
        double max = values.get(0);

        for (double value : values) {
            sum += value;
            if (value < min) {
                min = value;
            }
            if (value > max) {
                max = value;
            }
        }

        double average = sum / values.size();

        System.out.println("測定データの要約");
        System.out.println("件数: " + values.size());
        System.out.printf("平均値: %.2f%n", average);
        System.out.printf("最小値: %.2f%n", min);
        System.out.printf("最大値: %.2f%n", max);
    }

    private static List<Double> readValues(Path path) throws IOException {
        List<String> lines = Files.readAllLines(path);
        List<Double> values = new ArrayList<>();

        for (int i = 1; i < lines.size(); i++) {
            String[] columns = lines.get(i).split(",");
            values.add(Double.parseDouble(columns[2]));
        }

        return values;
    }
}
