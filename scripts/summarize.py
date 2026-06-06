from pathlib import Path
import csv


DATA_PATH = Path("data/measurements.csv")


def read_values(path):
    with path.open(encoding="utf-8", newline="") as file:
        reader = csv.DictReader(file)
        return [float(row["value"]) for row in reader]


def main():
    values = read_values(DATA_PATH)
    average = sum(values) / len(values)

    print("測定データの要約")
    print(f"件数: {len(values)}")
    print(f"平均値: {average:.2f}")
    print(f"最小値: {min(values):.2f}")
    print(f"最大値: {max(values):.2f}")


if __name__ == "__main__":
    main()
