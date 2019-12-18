package core.basesyntax;

import java.util.Objects;

/**
 * <p>Реалізувати свою HashMap, а саме методи `put(K key, V value)`, `getValue()` та `getSize()`.
 * Дотриматися основних вимог щодо реалізації мапи (initial capacity, load factor, resize...)
 * За бажанням можна реалізувати інші методи інтрефейсу Map.</p>
 */
public class MyHashMap<K, V> implements MyMap<K, V> {

    private static final int CAPACITY = 16;
    private static final double LOAD_FACTOR = 0.75;
    private int size;
    private Entry[] array;

    public MyHashMap() {
        size = 0;
        array = new Entry[CAPACITY];
    }

    @Override
    public void put(K key, V value) {
        resize();
        int index = indexFor(key);
        Entry<K, V> entry = new Entry(key, value);
        if (array[index] == null) {
            array[index] = entry;
            size++;
            return;
        }
        Entry<K, V> node = array[index];
        while (node != null) {
            if (Objects.equals(node.key, key)) {
                node.value = entry.value;
                return;
            }
            if (node.next == null) {
                break;
            }
            node = node.next;
        }
        node.next = entry;
        size++;
    }

    @Override
    public V getValue(K key) {
        V value = null;
        int index = indexFor(key);
        Entry<K, V> node = array[index];
        while (node != null) {
            if (Objects.equals(node.key, key)) {
                return node.value;
            }
            if (node.next == null) {
                break;
            }
            node = node.next;
        }
        return value;
    }

    @Override
    public int getSize() {
        return size;
    }

    private void resize() {
        if (!checkSize()) {
            return;
        }
        Entry[] currentArray = array;
        array = new Entry[currentArray.length * 2];
        size = 0;
        for (int i = 0; i < currentArray.length; i++) {
            if (currentArray[i] != null) {
                Entry<K, V> entry = currentArray[i];
                while (entry != null) {
                    put(entry.key, entry.value);
                    entry = entry.next;
                }
            }
        }
    }

    private boolean checkSize() {
        return size >= array.length * LOAD_FACTOR;
    }

    private int indexFor(K key) {
        return key == null ? 0 : Math.abs(key.hashCode() % array.length);
    }

    private class Entry<K, V> {
        private K key;
        private Entry next;
        private V value;

        private Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}

