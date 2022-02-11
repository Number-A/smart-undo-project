//package src;

public class Stopwatch {
    private long startTime = 0;

    public void start() {
        this.startTime = System.nanoTime();
    }

    public long getElapsedMilliseconds() {
        return (System.nanoTime() - startTime) / 1000000;
    }
}
