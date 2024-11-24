package tasks.taskfour;

public class Main {

    public static void main(String[] args) {
        BlockingObjectPool pool = new BlockingObjectPool(5);

        Thread producer = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                pool.take(new Object());
                System.out.println("Object put to pool");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread consumer = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                Object obj = pool.get();
                System.out.println("Object got from pool");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        producer.start();
        consumer.start();
    }
}
