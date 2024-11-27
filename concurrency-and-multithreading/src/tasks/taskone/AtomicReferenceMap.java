package tasks.taskone;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.Objects;
import java.util.stream.Collectors;

public class AtomicReferenceMap<K, V> implements Map<K, V> {
    private final AtomicReference<ArrayList<Node<K, V>>> map = new AtomicReference<>(new ArrayList<>(100_000));

    @Override
    public V put(K key, V value) {
        while (true) {
            ArrayList<Node<K, V>> currentList = new ArrayList<>(map.get());
            for (Node<K, V> node : currentList) {
                if (Objects.equals(node.getKey(), key)) {
                    V oldValue = node.getValue();
                    node.setValue(value);
                    if (map.compareAndSet(map.get(), currentList)) {
                        return oldValue;
                    } else {
                        break; // CAS failed, retry
                    }
                }
            }
            currentList.add(new Node<>(key, value));
            if (map.compareAndSet(map.get(), currentList)) {
                return null;
            }
            // CAS failed, retry
        }
    }

    @Override
    public V get(Object key) {
        ArrayList<Node<K, V>> currentList = map.get();
        for (Node<K, V> node : currentList) {
            if (Objects.equals(node.getKey(), key)) {
                return node.getValue();
            }
        }
        return null;
    }

    @Override
    public int size() {
        return map.get().size();
    }

    ;
    @Override
    public Collection<V> values() {
        return map.get().stream().map(Node::getValue).collect(Collectors.toList());
    }


    // Other methods are not implemented for simplicity
    @Override
    public boolean isEmpty() { throw new UnsupportedOperationException(); }
    @Override
    public boolean containsKey(Object key) { throw new UnsupportedOperationException(); }
    @Override
    public boolean containsValue(Object value) { throw new UnsupportedOperationException(); }
    @Override
    public V remove(Object key) { throw new UnsupportedOperationException(); }
    @Override
    public void putAll(Map<? extends K, ? extends V> m) { throw new UnsupportedOperationException(); }
    @Override
    public void clear() { throw new UnsupportedOperationException(); }
    @Override
    public Set<K> keySet() { throw new UnsupportedOperationException(); }
    @Override
    public Set<Map.Entry<K, V>> entrySet() { throw new UnsupportedOperationException(); }

    private static class Node<K, V> implements Map.Entry<K, V> {
        private final K key;
        private V value;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V value) {
            V old = this.value;
            this.value = value;
            return old;
        }
    }
}
