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

public class WaitNotifyLab {
    public static void main(String[] args) {
        SharedResource resource = new SharedResource();

        Thread producer = new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                resource.set(i);
                System.out.println("Produced: " + i);
            }
        });

        Thread consumer = new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                int val = resource.get();
                System.out.println("Consumed: " + val);
            }
        });

        producer.start();
        consumer.start();
    }
}