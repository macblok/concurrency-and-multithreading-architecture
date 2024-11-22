package tasks.taskone;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class TaskOne {

    public static void main(String[] args) {
        TaskOne taskOne = new TaskOne();
        taskOne.run(new HashMap<>());
        taskOne.run(new ConcurrentHashMap<>());
        taskOne.run(Collections.synchronizedMap(new HashMap<>()));

    }

    private void run(Map<Integer, Integer> map) {

        Thread thread1 = new Thread(() -> {
           for (int i =0; i < 1000; i++) {
               map.put(i, i);
               try {
                   Thread.sleep(4);
               } catch (InterruptedException e) {
                   System.out.println(e.getMessage());
               }
           }
        });

        Thread thread2 = new Thread(() -> {
            try {
                AtomicInteger currentSum = new AtomicInteger();
                map.values().forEach(value -> {
                    System.out.println("Map type: " + map.getClass() + ", Previous sum = " + currentSum + ", current value: = " + value);
                    currentSum.addAndGet(value);
                    System.out.println("Current sum = " + currentSum.get());
                });

            } catch (ConcurrentModificationException e) {
                System.out.println("Caught ConcurrentModificationException");
            }
            System.out.println("#".repeat(100));
        });

        thread1.start();

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        thread2.start();
    }
}

//After map implementation exchanging, there is no ConcurrentModificationException exception thrown.