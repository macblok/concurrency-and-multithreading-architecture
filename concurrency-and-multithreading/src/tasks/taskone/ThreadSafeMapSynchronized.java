package tasks.taskone;

import java.util.*;
import java.util.stream.Collectors;

public class ThreadSafeMapSynchronized<K, V> implements Map {

    private final List<Node<K, V>> map = new ArrayList(100_000);

    public synchronized void printValues() {
        map.forEach(System.out::println);
    }

    @Override
    public synchronized int size() {
        return map.size();
    }

    @Override
    public synchronized boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public synchronized boolean containsKey(Object key) {
        return getNodeByKey((K) key) != null;
    }

    @Override
    public synchronized boolean containsValue(Object value) {
        return map.stream().filter(n -> n.getValue().equals(value)).findFirst().orElse(null) != null;
    }

    @Override
    public synchronized V get(Object key) {
        Node<K, V> node;
        return (node = getNodeByKey((K) key)) == null ? null : node.getValue();
    }

    @Override
    public synchronized V put(Object key, Object value) {
        var node = getNodeByKey((K) key);
        if (node != null) {
            V oldValue = node.getValue();
            node.setValue((V) value);
            return oldValue;
        }
        map.add(new Node<>((K) key, (V) value));
        return null;
    }

    @Override
    public synchronized V remove(Object key) {
        var node = getNodeByKey((K) key);
        if (node == null) return null;
        map.remove(node);
        return node.getValue();
    }

    @Override
    public synchronized void putAll(Map m) {
        m.entrySet().forEach(entry -> {
            Map.Entry<?,?> e = (Map.Entry<?,?>) entry;
            K key = (K) e.getKey();
            V value = (V) e.getValue();
            put(key, value);
        });
    }

    @Override
    public synchronized void clear() {
        map.clear();
    }

    @Override
    public synchronized Set<K> keySet() {
        return map.stream().map(n -> n.getKey()).collect(Collectors.toSet());
    }

    @Override
    public synchronized Collection<V> values() {
        return map.stream().map(n -> n.getValue()).collect(Collectors.toList());
    }

    @Override
    public synchronized Set<Map.Entry<K, V>> entrySet() {
        return new HashSet<>(map);
    }

    private synchronized Node<K, V> getNodeByKey(K key) {
        return map.stream().filter(n -> n.getKey().equals(key)).findFirst().orElse(null);
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
