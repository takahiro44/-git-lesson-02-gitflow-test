import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class SummarizeMeasurements {
    public static void main(String[] args) throws IOException {
        Path csvPath = findMeasurementsCsv();
        List<Double> values = readValues(csvPath);

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

        System.out.println("読み込んだCSV: " + csvPath);
        System.out.println("確認観点: 条件ごとの測定値の違いを研究メモに記録する。");
        System.out.println("測定データの要約");
        System.out.println("件数: " + values.size());
        System.out.printf("平均値: %.2f%n", average);
        System.out.printf("最小値: %.2f%n", min);
        System.out.printf("最大値: %.2f%n", max);
    }

    private static Path findMeasurementsCsv() throws IOException {
        Path currentDir = Path.of("").toAbsolutePath().normalize();

        // まず、通常どおり「今いるフォルダ/data/measurements.csv」を確認する
        Path directPath = currentDir.resolve("data").resolve("measurements.csv");
        if (Files.exists(directPath)) {
            return directPath;
        }

        // 見つからない場合は、現在のフォルダ以下から探す
        try (Stream<Path> paths = Files.walk(currentDir, 5)) {
            return paths
                    .filter(Files::isRegularFile)
                    .filter(path -> path.getFileName().toString().equals("measurements.csv"))
                    .filter(path -> path.getParent() != null)
                    .filter(path -> path.getParent().getFileName().toString().equals("data"))
                    .findFirst()
                    .orElseThrow(() -> new NoSuchFileException(
                            "data/measurements.csv が見つかりません。現在の場所: " + currentDir
                    ));
        }
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
