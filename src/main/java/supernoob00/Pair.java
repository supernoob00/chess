package supernoob00;

import java.util.Map;

public class Pair<K, V> {
    private K k;
    private V v;

    public Pair(K k, V v) {
        this.k = k;
        this.v = v;
    }

    public Pair() {

    }

    public K getKey() {
        return k;
    }

    public V getValue() {
        return v;
    }

    public V setValue(V value) {
        V oldValue = this.v;
        this.v = value;
        return oldValue;
    }

    public K setKey(K key) {
        K oldKey = this.k;
        this.k = key;
        return oldKey;
    }

    public void set(K key, V value) {
        this.k = key;
        this.v = value;
    }

    public void clear() {
        this.k = null;
        this.v = null;
    }
}
