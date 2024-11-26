package tasks.taskfour;

import java.util.Random;

public class Main {

    public static void main(String[] args) {
        BlockingObjectPool pool = new BlockingObjectPool(5);

        Thread producer = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                pool.take(new Object());
                System.out.println("Object put to pool");
                try {
                    Thread.sleep(new Random().nextInt(3000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread consumer = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                Object obj = pool.get();
                System.out.println("Object got from pool");
                try {
                    Thread.sleep(new Random().nextInt(3000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        producer.start();
        consumer.start();
    }
}
