package tasks.taskfour;

public class BlockingObjectPool {

    private final Object[] pool;
    private int count;

    /**
     * Creates filled pool of passed size
     *
     * @param size of pool
     */
    public BlockingObjectPool(int size) {
        this.pool = new Object[size];
        for (int i = 0; i < size; i++) {
            this.pool[i] = new Object();
        }
        this.count = 0;
    }

    /**
     * Gets object from pool or blocks if pool is empty
     *
     * @return object from pool
     */
    public synchronized Object get() {
        while (count == 0) {
            try {
                wait();  // Wait until an object is available
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return null;
            }
        }
        Object object = pool[--count];  // Retrieve the object
        notifyAll();  // Notify any waiting threads
        return object;
    }

    /**
     * Puts object to pool or blocks if pool is full
     *
     * @param object to be taken back to pool
     */
    public synchronized void take(Object object) {
        while (count == pool.length) {
            try {
                wait();  // Wait until there's space in the pool
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
        pool[count++] = object;  // Add the object back to the pool
        notifyAll();  // Notify any waiting threads
    }
}

