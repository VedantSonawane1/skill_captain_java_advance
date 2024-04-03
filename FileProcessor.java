import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

class FileProcessor {
    public CompletableFuture<Summary> processFile(String filePath) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                int wordCount = 0;
                int lineCount = 0;
                BufferedReader reader = new BufferedReader(new FileReader(filePath));
                String line;
                while ((line = reader.readLine()) != null) {
                    lineCount++;
                    wordCount += line.split("\\s+").length;
                }
                reader.close();
                return new Summary(filePath, wordCount, lineCount);
            } catch (IOException e) {
                e.printStackTrace();
                return new Summary(filePath, 0, 0);
            }
        });
    }
}

class FileApp {
    public void processFiles(String[] filePaths) {
        CompletableFuture<Void>[] futures = new CompletableFuture[filePaths.length];

        for (int i = 0; i < filePaths.length; i++) {
            FileProcessor processor = new FileProcessor();
            futures[i] = processor.processFile(filePaths[i])
                    .thenAccept(summary -> System.out.println(summary));
        }

        CompletableFuture.allOf(futures).join();
    }
}

class Summary {
    private String filePath;
    private int wordCount;
    private int lineCount;

    public Summary(String filePath, int wordCount, int lineCount) {
        this.filePath = filePath;
        this.wordCount = wordCount;
        this.lineCount = lineCount;
    }

    @Override
    public String toString() {
        return String.format("- File: %s\n  Word Count: %d\n  Line Count: %d\n", filePath, wordCount, lineCount);
    }
}

public class Main {
    public static void main(String[] args) {
        String[] filePaths = {"file1.txt", "file2.txt", "file3.txt"};
        FileApp fileApp = new FileApp();
        fileApp.processFiles(filePaths);
    }
}
