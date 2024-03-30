public class PrintNumbers {
    public static void main(String[] args) {
        Thread threadA = new Thread(() -> {
            for (int i = 1; i <= 10; i += 2) {
                System.out.println("ThreadA: " + i);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread threadB = new Thread(() -> {
            for (int i = 2; i <= 10; i += 2) {
                System.out.println("ThreadB: " + i);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        threadA.start();
        threadB.start();
    }
}
