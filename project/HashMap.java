package com.mycompany.projectdatastructure;

public class HashMap<K, V> {

    class Entry {
        K key;
        V value;
        Entry next;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }
    }

    private Object[] buckets;
    private int size;
    private int capacity;

    private static final float LOAD_FACTOR   = 0.75f;
    private static final int   INIT_CAPACITY = 16;

    HashMap() {
        capacity = INIT_CAPACITY;
        buckets  = new Object[capacity];
        size     = 0;
    }

    private int hash(K key) {
        int h = key.hashCode();
        h ^= (h >>> 16);
        return Math.abs(h) % capacity;
    }

    public void put(K key, V value) {
        if ((float) size / capacity >= LOAD_FACTOR) {
            resize();
        }

        int idx = hash(key);
        Entry head = (Entry) buckets[idx];
        Entry current = head;

        while (current != null) {
            if (current.key.equals(key)) {
                current.value = value;
                return;
            }
            current = current.next;
        }

        Entry newEntry = new Entry(key, value);
        newEntry.next  = head;
        buckets[idx] = newEntry;
        size++;
    }

    public V get(K key) {
        int idx = hash(key);
        Entry current = (Entry) buckets[idx];

        while (current != null) {
            if (current.key.equals(key)) {
                return current.value;
            }
            current = current.next;
        }
        return null;
    }

    public void remove(K key) {
        int idx = hash(key);
        Entry current = (Entry) buckets[idx];
        Entry prev = null;

        while (current != null) {
            if (current.key.equals(key)) {
                if (prev == null) {
                    buckets[idx] = current.next;
                } else {
                    prev.next = current.next;
                }
                size--;
                return;
            }
            prev    = current;
            current = current.next;
        }
    }

    public boolean containsKey(K key) {
        return get(key) != null;
    }

    public V getOrDefault(K key, V defaultValue) {
        V val = get(key);
        return val != null ? val : defaultValue;
    }

    public DinamicArray<V> getAll() {
        DinamicArray<V> result = new DinamicArray<>();
        for (int i = 0; i < capacity; i++) {
            Entry current = (Entry) buckets[i];
            while (current != null) {
                result.insert(current.value);
                current = current.next;
            }
        }
        return result;
    }

    private void resize() {
        int oldCapacity = capacity;
        Object[] oldBuckets  = buckets;
        capacity = capacity * 2;
        buckets = new Object[capacity];
        size = 0;
        for (int i = 0; i < oldCapacity; i++) {
            Entry current = (Entry) oldBuckets[i];
            while (current != null) {
                put(current.key, current.value);
                current = current.next;
            }
        }
    }

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }
}