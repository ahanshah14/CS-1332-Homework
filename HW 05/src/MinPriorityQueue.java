import java.util.NoSuchElementException;

/**
 * My implementation of a min priority queue.
 * 
 * @author Jalo Moster
 * @version 1.0
 */
public class MinPriorityQueue<T extends Comparable<? super T>> implements PriorityQueueInterface<T> {
    private HeapInterface<T> backingHeap;

    /**
     * Creates a priority queue.
     */
    public MinPriorityQueue() {
        backingHeap = new MinHeap<>();
    }

    @Override
    public void enqueue(T item) {
        backingHeap.add(item);
    }

    @Override
    public T dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue cannot be empty");
        }
        return backingHeap.remove();
    }

    @Override
    public boolean isEmpty() {
        return backingHeap.isEmpty();
    }

    @Override
    public int size() {
        return backingHeap.size();
    }

    @Override
    public void clear() {
        backingHeap.clear();
    }

    @Override
    public HeapInterface<T> getBackingHeap() {
        return backingHeap;
    }
}
