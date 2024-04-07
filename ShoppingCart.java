import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

class ShoppingCart {
    private List<String> items = new CopyOnWriteArrayList<>();

    public void addItem(String item) {
        items.add(item);
    }

    public void removeItem(String item) {
        items.remove(item);
    }

    public List<String> getItems() {
        return items;
    }
}

class CustomerThread extends Thread {
    private ShoppingCart cart;

    public CustomerThread(ShoppingCart cart) {
        this.cart = cart;
    }

    @Override
    public void run() {
        cart.addItem("Item " + getId());
        System.out.println("Added item for customer " + getId() + ", cart items: " + cart.getItems());

        try {
            Thread.sleep(100); // Simulating customer shopping
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        cart.removeItem("Item " + getId());
        System.out.println("Removed item for customer " + getId() + ", cart items: " + cart.getItems());
    }
}

public class Main {
    public static void main(String[] args) {
        ShoppingCart cart = new ShoppingCart();

        for (int i = 1; i <= 5; i++) {
            CustomerThread customerThread = new CustomerThread(cart);
            customerThread.start();
        }
    }
}
