package tasks.taskone;

import java.util.Map;

public class TaskOneCustomMapsPerformance {

    public static void main(String[] args) throws InterruptedException {

        Map<Integer, Integer> synchronizedMap = new ThreadSafeMapSynchronized<>();
        Map<Integer, Integer> threadSafeMapWithLocks = new ThreadSafeMapWithLocks<>();
        Map<Integer, Integer> hashMapWithLocks = new HashMapWithLocks<>();
        Map<Integer, Integer> atomicReferenceMap = new AtomicReferenceMap<>();

        int numbers = 10000;
        int summingIterations = 1;


        //Start ThreadSafeMapSynchronized performance test
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

        // Start and join threads for synchronizedMap
        long startSynchronized = System.nanoTime();
        thread1SynchronizedMap.start();
        thread2SynchronizedMap.start();
        thread3SynchronizedMap.start();

        thread1SynchronizedMap.join();
        thread2SynchronizedMap.join();
        thread3SynchronizedMap.join();

        long endSynchronized = System.nanoTime();
        System.out.println("Time ThreadSafeMapSynchronized: " + ((endSynchronized - startSynchronized)/1_000_000) + " ms");

        // Check data integrity after all modifications have been completed
        if (synchronizedMap.size() != numbers) {
            System.out.println("Data integrity error in ThreadSafeMapSynchronized map. Expected: " + numbers + ", got: " + synchronizedMap.size());
        }
        //End ThreadSafeMapSynchronized performance test

        //Start ThreadSafeMapWithLocks performance test
        Thread thread1ThreadSafeMapWithLocks = new Thread(() -> {
            for (int i = 0; i < numbers; i++) {
                threadSafeMapWithLocks.put(i, i + 1);
            }
        });

        Thread thread2ThreadSafeMapWithLocks = new Thread(() -> {
            for (int i = 0; i < summingIterations; i++) {
                int sum = 0;
                for (Integer integer : threadSafeMapWithLocks.values()) {
                    sum += integer;
                }
            }
        });

        Thread thread3ThreadSafeMapWithLocks = new Thread(() -> {
            for (int i = 0; i < summingIterations; i++) {
                int sum = 0;
                for (Integer integer : threadSafeMapWithLocks.values()) {
                    sum += integer;
                }
            }
        });

        // Start and join threads for ThreadSafeMapWithLocks
        long startThreadSafeMapWithLocks = System.nanoTime();
        thread1ThreadSafeMapWithLocks.start();
        thread2ThreadSafeMapWithLocks.start();
        thread3ThreadSafeMapWithLocks.start();

        thread1ThreadSafeMapWithLocks.join();
        thread2ThreadSafeMapWithLocks.join();
        thread3ThreadSafeMapWithLocks.join();

        long endThreadSafeMapWithLocks = System.nanoTime();
        System.out.println("Time ThreadSafeMapWithLocks: " + (endThreadSafeMapWithLocks - startThreadSafeMapWithLocks)/1_000_000 + " ms");

        // Similarly, check data integrity for the ThreadSafeMapWithLocks map
        if (threadSafeMapWithLocks.size() != numbers) {
            System.out.println("Data integrity error in ThreadSafeMapWithLocks map: Incorrect number of elements. Expected: " + numbers + ", got: " + threadSafeMapWithLocks.size());
        }
        //End ThreadSafeMapWithLocks performance test

        //Start HashMapWithLocks performance test
        Thread thread1HashMapWithLocks = new Thread(() -> {
            for (int i = 0; i < numbers; i++) {
                hashMapWithLocks.put(i, i + 1);
            }
        });

        Thread thread2HashMapWithLocks = new Thread(() -> {
            for (int i = 0; i < summingIterations; i++) {
                int sum = 0;
                for (Integer integer : hashMapWithLocks.values()) {
                    sum += integer;
                }
            }
        });

        Thread thread3HashMapWithLocks = new Thread(() -> {
            for (int i = 0; i < summingIterations; i++) {
                int sum = 0;
                for (Integer integer : hashMapWithLocks.values()) {
                    sum += integer;
                }
            }
        });

        // Start and join threads for AnotherMap
        long startHashMapWithLocks = System.nanoTime();
        thread1HashMapWithLocks.start();
        thread2HashMapWithLocks.start();
        thread3HashMapWithLocks.start();

        thread1HashMapWithLocks.join();
        thread2HashMapWithLocks.join();
        thread3HashMapWithLocks.join();

        long endHashMapWithLocks = System.nanoTime();
        System.out.println("Time HashMapWithLocks: " + (endHashMapWithLocks - startHashMapWithLocks)/1_000_000 + " ms");

        // Similarly, check data integrity for the non-synchronized map
        if (hashMapWithLocks.size() != numbers) {
            System.out.println("Data integrity error in HashMapWithLocks: Incorrect number of elements. Expected: " + numbers + ", got: " + hashMapWithLocks.size());
        }
        //End HashMapWithLocks performance test

        //Start AtomicReferenceMap performance test
        Thread thread1AtomicReferenceMap = new Thread(() -> {
            for (int i = 0; i < numbers; i++) {
                atomicReferenceMap.put(i, i + 1);
            }
        });

        Thread thread2AtomicReferenceMap = new Thread(() -> {
            for (int i = 0; i < summingIterations; i++) {
                int sum = 0;
                for (Integer integer : atomicReferenceMap.values()) {
                    sum += integer;
                }
            }
        });

        Thread thread3AtomicReferenceMap = new Thread(() -> {
            for (int i = 0; i < summingIterations; i++) {
                int sum = 0;
                for (Integer integer : atomicReferenceMap.values()) {
                    sum += integer;
                }
            }
        });

        // Start and join threads for synchronizedMap
        long startAtomicReferenceMap = System.nanoTime();
        thread1AtomicReferenceMap.start();
        thread2AtomicReferenceMap.start();
        thread3AtomicReferenceMap.start();

        thread1AtomicReferenceMap.join();
        thread2AtomicReferenceMap.join();
        thread3AtomicReferenceMap.join();

        long endAtomicReferenceMap = System.nanoTime();
        System.out.println("Time AtomicReferenceMap: " + ((endAtomicReferenceMap - startAtomicReferenceMap)/1_000_000) + " ms");

        // Check data integrity after all modifications have been completed
        if (atomicReferenceMap.size() != numbers) {
            System.out.println("Data integrity error in AtomicReferenceMap: Incorrect number of elements. Expected: " + numbers + ", got: " + atomicReferenceMap.size());
        }
        //End ThreadSafeMapSynchronized performance test








    }
}
