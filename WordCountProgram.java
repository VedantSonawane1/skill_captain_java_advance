import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class WordCountProgram {
    private static final int THREAD_POOL_SIZE = 10;
    private static final AtomicInteger totalWordCount = new AtomicInteger(0);

    public static void main(String[] args) {
        String directoryPath = "path/to/directory";

        ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

        File directory = new File(directoryPath);
        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        executor.submit(() -> {
                            int wordCount = countWords(file);
                            totalWordCount.addAndGet(wordCount);
                        });
                    }
                }
            }
        }

        executor.shutdown();
        while (!executor.isTerminated()) {
        }

        System.out.println("Total word count: " + totalWordCount.get());
    }

    private static int countWords(File file) {
        int count = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] words = line.split("\\s+");
                count += words.length;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return count;
    }
}
