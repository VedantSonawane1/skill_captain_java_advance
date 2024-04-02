import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FactorialCalculator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a number: ");
        int number = scanner.nextInt();
        
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Long> future = executor.submit(() -> calculateFactorial(number));

        System.out.println("Calculating factorial...");
        
        try {
            long result = future.get();
            System.out.println("Factorial of " + number + " is: " + result);
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Error occurred during calculation: " + e.getMessage());
        }

        executor.shutdown();
        scanner.close();
    }

    public static long calculateFactorial(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Factorial is not defined for negative numbers.");
        }
        long factorial = 1;
        for (int i = 1; i <= n; i++) {
            factorial *= i;
        }
        return factorial;
    }
}
