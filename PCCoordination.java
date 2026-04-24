class SharedResource {
    private int data;
    private boolean bChanged = false;

    public synchronized int get() {
        while (!bChanged) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        bChanged = false;
        notify();
        return data;
    }

    public synchronized void set(int value) {
        while (bChanged) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        this.data = value;
        this.bChanged = true;
        notify();
    }
}

public class PCCoordination {
    public static void main(String[] args) {
        SharedResource resource = new SharedResource();

        new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                resource.set(i);
                System.out.println("Set: " + i);
            }
        }).start();

        new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                System.out.println("Got: " + resource.get());
            }
        }).start();
    }
}