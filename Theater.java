import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Theater {
    private int totalSeats;
    private boolean[] seats;
    private Lock lock;

    public Theater(int totalSeats) {
        this.totalSeats = totalSeats;
        this.seats = new boolean[totalSeats];
        for (int i = 0; i < totalSeats; i++) {
            seats[i] = true;
        }
        this.lock = new ReentrantLock();
    }

    public synchronized boolean reserveSeat(int seatNumber) {
        if (seatNumber < 1 || seatNumber > totalSeats) {
            return false;
        }
        if (!seats[seatNumber - 1]) {
            return false;
        }
        seats[seatNumber - 1] = false;
        return true;
    }

    public int getTotalSeats() {
        return totalSeats;
    }
}
