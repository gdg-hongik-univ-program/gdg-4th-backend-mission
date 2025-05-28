package gdg.hongik.mission.dto;

public class StatisticItem {
    private String name;
    private int count;
    private double average;

    public StatisticItem(String name, int count, double average) {
        this.name = name;
        this.count = count;
        this.average = average;
    }
    public String getName() { return name; }
    public int getCount() { return count; }
    public double getAverage() { return average; }
}
