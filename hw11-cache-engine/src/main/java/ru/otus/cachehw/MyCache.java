package ru.otus.cachehw;


import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

public class MyCache<K, V> implements HwCache<K, V> {

    private final WeakHashMap<K, V> cacheStorage = new WeakHashMap<>();
    private final List<HwListener<K, V>> listeners = new ArrayList<>();

    @Override
    public void put(K key, V value) {
        cacheStorage.put(key, value);
        listeners.forEach(h -> h.notify(key, value, "put"));
    }

    @Override
    public void remove(K key) {
        V value = cacheStorage.remove(key);
        listeners.forEach(h -> h.notify(key, value,"remove"));
    }

    @Override
    public V get(K key) {
        V value = cacheStorage.get(key);
        listeners.forEach(h -> h.notify(key, value,"get"));
        return value;
    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        listeners.remove(listener);
    }
}
