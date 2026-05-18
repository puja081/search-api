package search_api.service;

import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * In-memory tracker for search query frequencies.
 *
 * Uses ConcurrentHashMap for thread-safe frequency counting and a
 * min-heap (PriorityQueue) of size K for O(n log k) top-K retrieval.
 *
 * This is the classic "Top K Frequent Elements" interview pattern:
 *   - HashMap  → O(1) per record
 *   - Min-Heap → O(n log k) for getTopK
 */
@Component
public class SearchTracker {

    private final ConcurrentHashMap<String, Long> frequencyMap = new ConcurrentHashMap<>();

    public void record(String query) {
        if (query == null || query.isBlank()) return;
        String normalized = query.strip().toLowerCase();
        frequencyMap.merge(normalized, 1L, Long::sum);
    }

    public List<Map<String, Object>> getTopK(int k) {
        if (k <= 0 || frequencyMap.isEmpty()) return List.of();

        // Min-heap ordered by frequency — keeps only the top K entries
        PriorityQueue<Map.Entry<String, Long>> minHeap =
                new PriorityQueue<>(Comparator.comparingLong(Map.Entry::getValue));

        for (Map.Entry<String, Long> entry : frequencyMap.entrySet()) {
            minHeap.offer(entry);
            if (minHeap.size() > k) {
                minHeap.poll(); // evict the smallest
            }
        }

        // Drain heap into result list, highest frequency first
        LinkedList<Map<String, Object>> result = new LinkedList<>();
        while (!minHeap.isEmpty()) {
            Map.Entry<String, Long> entry = minHeap.poll();
            Map<String, Object> item = new LinkedHashMap<>();
            item.put("query", entry.getKey());
            item.put("count", entry.getValue());
            result.addFirst(item); // reverse so highest is first
        }

        return result;
    }

    public void reset() {
        frequencyMap.clear();
    }
}
