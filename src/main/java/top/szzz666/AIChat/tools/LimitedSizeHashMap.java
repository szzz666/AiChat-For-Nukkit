package top.szzz666.AIChat.tools;

import java.util.LinkedHashMap;
import java.util.Map;

public class LimitedSizeHashMap<K, V> extends LinkedHashMap<K, V> {
    private final int maxSize;

    public LimitedSizeHashMap(int maxSize) {
        super(16, 0.75f, false);
        this.maxSize = maxSize;
    }
    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > maxSize;
    }
}
