package tasks.taskone;

import java.util.Map;

public class TaskOneCustomMapsPerformance {

    public static void main(String[] args) throws InterruptedException {

        Map<Integer, Integer> synchronizedMap = new ThreadSafeMapSynchronized<>();
        Map<Integer, Integer> withoutSynchronizationMap = new AnotherMap<>();

        int numbers = 100;
        int summingIterations = 100;

        Thread thread1SynchronizedMap = new Thread(() -> {
            for (int i = 0; i < numbers; i++) {
                synchronizedMap.put(i, i + 1);
            }
        });

        Thread thread2SynchronizedMap = new Thread(() -> {
            for (int i = 0; i < summingIterations; i++) {
                int sum = 0;
                for (Integer integer : synchronizedMap.values()) {
                    sum += integer;
                }
            }
        });

        Thread thread3SynchronizedMap = new Thread(() -> {
            for (int i = 0; i < summingIterations; i++) {
                int sum = 0;
                for (Integer integer : synchronizedMap.values()) {
                    sum += integer;
                }
            }
        });


        Thread thread1MapWithoutSynchronization = new Thread(() -> {
            for (int i = 0; i < numbers; i++) {
                withoutSynchronizationMap.put(i, i + 1);
            }
        });

        Thread thread2MapWithoutSynchronization = new Thread(() -> {
            for (int i = 0; i < summingIterations; i++) {
                int sum = 0;
                for (Integer integer : withoutSynchronizationMap.values()) {
                    sum += integer;
                }
            }
        });

        Thread thread3MapWithoutSynchronization = new Thread(() -> {
            for (int i = 0; i < summingIterations; i++) {
                int sum = 0;
                for (Integer integer : withoutSynchronizationMap.values()) {
                    sum += integer;
                }
            }
        });

        // Start and join threads for synchronizedMap
        long startSynchronized = System.nanoTime();
        thread1SynchronizedMap.start();
        thread2SynchronizedMap.start();
        thread3SynchronizedMap.start();

        thread1SynchronizedMap.join();
        thread2SynchronizedMap.join();
        thread3SynchronizedMap.join();

        long endSynchronized = System.nanoTime();
        System.out.println("Time with synchronization: " + ((endSynchronized - startSynchronized)/1_000_000) + " ms");

        // Check data integrity after all modifications have been completed
        if (synchronizedMap.size() != numbers) {
            System.out.println("Synchronization error: Incorrect number of elements. Expected: " + numbers + ", got: " + synchronizedMap.size());
        }


        // Start and join threads for withoutSynchronizationMap
        long startWithoutSynchronization = System.nanoTime();
        thread1MapWithoutSynchronization.start();
        thread2MapWithoutSynchronization.start();
        thread3MapWithoutSynchronization.start();

        thread1MapWithoutSynchronization.join();
        thread2MapWithoutSynchronization.join();
        thread3MapWithoutSynchronization.join();

        long endWithoutSynchronization = System.nanoTime();
        System.out.println("Time without synchronization: " + (endWithoutSynchronization - startWithoutSynchronization)/1_000_000 + " ms");

        // Similarly, check data integrity for the non-synchronized map
        if (withoutSynchronizationMap.size() != numbers) {
            System.out.println("Data integrity error in non-synchronized map: Incorrect number of elements. Expected: " + numbers + ", got: " + withoutSynchronizationMap.size());
        }
    }
}
