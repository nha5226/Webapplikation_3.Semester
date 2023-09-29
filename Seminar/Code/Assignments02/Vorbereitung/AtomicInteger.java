package Webapplication.Assignments02.Vorbereitung;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;


class AtomicCounter {
    private AtomicInteger count = new AtomicInteger(0);
    public int incrementAndGet() { return count.incrementAndGet(); }
    public int getCount() { return count.get(); }
}

class AtomicIntegerEx {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        AtomicCounter atomicCounter = new AtomicCounter();
        for(int i = 0; i < 1000; i++) {
            executorService.submit(() -> atomicCounter.incrementAndGet());
        }
        executorService.shutdown();
        executorService.awaitTermination(5, TimeUnit.SECONDS);
        System.out.println("Final Count is : " + atomicCounter.getCount());
    }
}
