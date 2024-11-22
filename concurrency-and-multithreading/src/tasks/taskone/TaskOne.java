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

/*After replacing HashMap with ConcurrentHashMap or Collections.synchronizedMap(), we expect no ConcurrentModificationException
because both implementations handle synchronization internally. ConcurrentHashMap splits the data segments, so multiple threads
can access different segments simultaneously. Collections.synchronizedMap(), on the other hand, serializes all access to the mapping,
preventing concurrent modification issues.
In conclusion, ConcurrentHashMap should yield better performance in scenarios with high concurrency due to its segment-level locking
mechanism compared to Collections.synchronizedMap(), which locks the entire map on each access. The result using either of the improved
map structures should allow us to avoid exceptions and perform concurrent read/write operations effectively.
Both solutions address the question of how to fix the code to handle concurrent modification, depending on the exact needs of the
application in terms of performance and concurrency level. ConcurrentHashMap usually provides a better balance between concurrency
and performance, while Collections.synchronizedMap() ensures straightforward thread safety at the expense of locking overhead.
 */