import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * My implementation of HashMap.
 *
 * @author Jalo Moster
 * @version 1.0
 */
public class HashMap<K, V> implements HashMapInterface<K, V> {
    private MapEntry<K, V>[] table;
    private int size;

    /**
     * Create a hash map with no entries. The backing array has an initial
     * capacity of {@code INITIAL_CAPACITY}.
     *
     * Do not use magic numbers!
     *
     * Use constructor chaining.
     */
    @SuppressWarnings("unchecked")
    public HashMap() {
        this(INITIAL_CAPACITY);
    }

    /**
     * Create a hash map with no entries. The backing array has an initial
     * capacity of {@code initialCapacity}.
     *
     * You may assume {@code initialCapacity} will always be positive.
     *
     * @param initialCapacity initial capacity of the backing array
     */
    @SuppressWarnings("unchecked")
    public HashMap(int initialCapacity) {
        table = new MapEntry[initialCapacity];
        size = 0;
    }

    @Override
    public V put(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException("Key/Value cannot be null");
        }

        double load = (double) (size + 1) / table.length;
        if (load > MAX_LOAD_FACTOR) {
            resizeBackingTable((table.length << 1) + 1);
        }

        MapEntry<K, V> entry = new MapEntry<>(key, value);
        int hashCode = key.hashCode();
        int hash = ((hashCode < 0) ? -hashCode : hashCode) % table.length;
        int firstRemoved = 0;
        boolean foundRemoved = false;
        for (int i = 0; i < table.length; i++) {
            int index = (hash + i) % table.length;
            MapEntry<K, V> curr = table[index];
            if (curr == null) {
                if (foundRemoved) {
                    table[firstRemoved] = entry;
                } else {
                    table[index] = entry;
                }
                size++;
                return null;
            } else {
                if (!curr.isRemoved()) {
                    if (curr.getKey().equals(key)) {
                        V oldValue = curr.getValue();
                        table[index] = entry;
                        return oldValue;
                    }
                } else {
                    if (!foundRemoved) {
                        firstRemoved = index;
                        foundRemoved = true;
                    }
                }
            }
        }

        if (foundRemoved) {
            table[firstRemoved] = entry;
            size++;
        }
        return null;
    }

    @Override
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }

        int hash = Math.abs(key.hashCode()) % table.length;
        for (int i = 0; i < table.length; i++) {
            int index = (hash + i) % table.length;
            MapEntry<K, V> curr = table[index];
            if (curr != null) {
                if (!curr.isRemoved()) {
                    if (curr.getKey().equals(key)) {
                        V oldValue = curr.getValue();
                        table[index].setKey(null);
                        table[index].setValue(null);
                        table[index].setRemoved(true);
                        size--;
                        return oldValue;
                    }
                }
            } else {
                throw new NoSuchElementException("Key not found");
            }
        }
        throw new NoSuchElementException("Key not found");
    }

    @Override
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }

        int hash = Math.abs(key.hashCode()) % table.length;
        for (int i = 0; i < table.length; i++) {
            int index = (hash + i) % table.length;
            MapEntry<K, V> curr = table[index];
            if (curr != null) {
                if (!curr.isRemoved()) {
                    if (curr.getKey().equals(key)) {
                        return curr.getValue();
                    }
                }
            } else {
                throw new NoSuchElementException("Key not found");
            }
        }
        throw new NoSuchElementException("Key not found");
    }

    @Override
    public boolean containsKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }

        int hash = Math.abs(key.hashCode()) % table.length;
        for (int i = 0; i < table.length; i++) {
            int index = (hash + i) % table.length;
            MapEntry<K, V> curr = table[index];
            if (curr != null) {
                if (!curr.isRemoved()) {
                    if (curr.getKey().equals(key)) {
                        return true;
                    }
                }
            } else {
                return false;
            }
        }
        return false;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void clear() {
        table = new MapEntry[INITIAL_CAPACITY];
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Set<K> keySet() {
        Set<K> set = new HashSet<>();

        if (size == 0) {
            return set;
        }

        for (MapEntry<K, V> entry : table) {
            if (entry != null) {
                if (!entry.isRemoved()) {
                    if (entry.getKey() != null) {
                        set.add(entry.getKey());
                    }
                }
            }
        }
        return set;
    }

    @Override
    public List<V> values() {
        List<V> list = new ArrayList<>();

        if (size == 0) {
            return list;
        }

        for (MapEntry<K, V> entry : table) {
            if (entry != null) {
                if (!entry.isRemoved()) {
                    if (entry.getValue() != null) {
                        list.add(entry.getValue());
                    }
                }
            }
        }
        return list;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void resizeBackingTable(int length) {
        if (length < 0 || length < size) {
            throw new IllegalArgumentException("Length: " + length
                    + ", Size: " + size);
        }

        MapEntry<K, V>[] temp = new MapEntry[length];
        for (int i = 0; i < table.length; i++) {
            MapEntry<K, V> curr = table[i];
            if (curr != null) {
                if (!curr.isRemoved()) {
                    K key = curr.getKey();
                    V value = curr.getValue();
                    int hashCode = key.hashCode();
                    int hash = ((hashCode < 0) ? -hashCode : hashCode) % length;
                    boolean found = false;
                    for (int j = 0; j < length && !found; j++) {
                        int index = (hash + j) % length;
                        if (temp[index] == null) {
                            temp[index] = new MapEntry<>(key, value);
                            found = true;
                        }
                    }
                }
            }
        }
        table = temp;
    }

    @Override
    public MapEntry<K, V>[] getTable() {
        return table;
    }
}
