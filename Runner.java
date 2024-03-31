import java.util.Random;

class Runner implements Runnable {
    private String name;
    private int distanceCovered;
    private int totalDistance;
    private Random random;

    public Runner(String name, int totalDistance) {
        this.name = name;
        this.distanceCovered = 0;
        this.totalDistance = totalDistance;
        this.random = new Random();
    }

    public void run() {
        while (distanceCovered < totalDistance) {
            int distanceToRun = random.nextInt(10);
            distanceCovered += distanceToRun;
            System.out.println(name + " has covered " + distanceCovered + " meters.");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(name + " has finished the race!");
    }
}

public class RaceSimulation {
    public static void main(String[] args) {
        int totalDistance = 100;
        Runner runner1 = new Runner("Runner 1", totalDistance);
        Runner runner2 = new Runner("Runner 2", totalDistance);
        Runner runner3 = new Runner("Runner 3", totalDistance);

        Thread thread1 = new Thread(runner1);
        Thread thread2 = new Thread(runner2);
        Thread thread3 = new Thread(runner3);

        thread1.start();
        thread2.start();
        thread3.start();
    }
}
