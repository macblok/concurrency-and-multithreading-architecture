package tasks.taskone;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

public class ThreadSafeMapWithLocks<K, V> implements Map {

    private final List<Node<K, V>> map = new ArrayList(100_000);

    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock readLock = lock.readLock();
    private final Lock writeLock = lock.writeLock();

    @Override
    public V get(Object key) {
        Node<K, V> node;
        return (node = getNodeByKey((K) key)) == null ? null : node.getValue();
    }

    @Override
    public V put(Object key, Object value) {
        writeLock.lock();
        try {
            var node = getNodeByKey((K) key);
            if (node != null) {
                V oldValue = node.getValue();
                node.setValue((V) value);
                return oldValue;
            } else {
                map.add(new Node<>((K) key, (V) value));
                return null;
            }
        } finally {
            writeLock.unlock();
        }
    }

    private Node<K, V> getNodeByKey(K key) {
        readLock.lock();

        try {
            return map.stream().filter(n -> n.getKey().equals(key)).findFirst().orElse(null);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean containsKey(Object key) {
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    @Override
    public Object remove(Object key) {
        return null;
    }

    @Override
    public void putAll(Map m) {

    }

    @Override
    public void clear() {

    }

    @Override
    public Set keySet() {
        return Set.of();
    }

    @Override
    public Collection values() {
        return map.stream().map(Node::getValue).collect(Collectors.toList());
    }

    @Override
    public Set<Map.Entry> entrySet() {
        return Set.of();
    }

    private class Node<K, V> implements Map.Entry<K, V> {

        final K key;
        V value;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public final K getKey() {
            return key;
        }

        @Override
        public final V getValue() {
            return value;
        }

        @Override
        public V setValue(V newValue) {
            V oldValue = value;
            value = newValue;
            return oldValue;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "key=" + key +
                    ", value=" + value +
                    '}';
        }
    }
}
