import java.util.concurrent.atomic.AtomicInteger;

class Logger {
    private static Logger instance;
    private AtomicInteger counter = new AtomicInteger(0);

    private Logger() {}

    public static synchronized Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }

    public synchronized void log(String message) {
        System.out.println("Log #" + counter.incrementAndGet() + ": " + message);
    }
}

class LogWriterThread extends Thread {
    @Override
    public void run() {
        Logger logger = Logger.getInstance();
        for (int i = 0; i < 3; i++) {
            logger.log("Message from thread " + Thread.currentThread().getId());
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            new LogWriterThread().start();
        }
    }
}
