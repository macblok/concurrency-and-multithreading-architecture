package tasks.taskone;

import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class UsingHashMap {

    public static void main(String[] args) {
        Map<Integer, Integer> map = new HashMap<>();

        Thread thread1 = new Thread(() -> {
           for (int i =0; i < 1000; i++) {
               map.put(i, i);
               try {
                   Thread.sleep(1);
               } catch (InterruptedException e) {
                   System.out.println(e.getMessage());
               }
           }
        });

        Thread thread2 = new Thread(() -> {
            try {
                AtomicInteger currentSum = new AtomicInteger();
                map.values().forEach(value -> {
                    System.out.println("Current sum " + currentSum + ", current value: " + value);
                    currentSum.addAndGet(value);
                    System.out.println(currentSum.get());
                });

            } catch (ConcurrentModificationException e) {
                System.out.println("Caught ConcurrentModificationException");
            }
        });

        thread1.start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        thread2.start();
    }
}
