import java.util.HashMap;

public class LoginSessionManager {
    private static final ThreadLocal<HashMap<String, String>> threadLocal = ThreadLocal.withInitial(HashMap::new);

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(() -> {
                HashMap<String, String> sessionData = threadLocal.get();
                sessionData.put("username", "user" + Thread.currentThread().getId());
                sessionData.put("sessionID", "session" + Thread.currentThread().getId());
                System.out.println("Thread " + Thread.currentThread().getId() + " login session data: " + sessionData);
            });
            thread.start();
        }
    }
}