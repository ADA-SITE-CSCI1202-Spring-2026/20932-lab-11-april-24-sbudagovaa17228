class MathTask implements Runnable {
    @Override
    public void run() {
        long sum = 0;
        for (int i = 0; i < 10_000_000; i++) {
            sum += (long) i * i * i + i * 1;
        }
        System.out.println(Thread.currentThread().getName() + " finished. Result check: " + sum);
    }
}

public class DynamicScaling {
    public static void main(String[] args) throws InterruptedException {
        int cores = Runtime.getRuntime().availableProcessors();
        Thread[] threads = new Thread[cores];

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < cores; i++) {
            threads[i] = new Thread(new MathTask());
            threads[i].start();
        }

        for (Thread t : threads) {
            t.join();
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Cores used: " + cores);
        System.out.println("Total time: " + (endTime - startTime) + "ms");
    }
}