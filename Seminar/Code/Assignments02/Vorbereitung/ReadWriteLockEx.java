package Webapplication.Assignments02.Vorbereitung;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
public class ReadWriteLockEx {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        ReadWriteCounter counter = new ReadWriteCounter();
        Runnable readTask = () -> {
            System.out.println(Thread.currentThread().getName() +
                    " Read Task : " + counter.getCount());
        };
        Runnable writeTask = () -> {
            System.out.println(Thread.currentThread().getName() +
                    " Write Task : " + counter.incrementAndGetCount());
        };
        executorService.submit(readTask);
        executorService.submit(readTask);
        executorService.submit(writeTask);
        executorService.submit(readTask);
        executorService.submit(readTask);
        executorService.shutdown();
    }
}
