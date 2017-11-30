import java.util.NoSuchElementException;

/**
 * My implementation of an array-backed queue.
 *
 * @author Jalo Moster
 * @version 1.0
 */
public class ArrayQueue<T> implements QueueInterface<T> {
    private T[] backingArray;
    private int front;
    private int back;
    private int size;

    /**
     * Constructs a new ArrayQueue.
     */
    @SuppressWarnings("unchecked")
    public ArrayQueue() {
        backingArray = (T[]) new Object[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Dequeue from the front of the queue.
     *
     * Do not shrink the backing array.
     * If the queue becomes empty as a result of this call, you must not
     * explicitly reset front or back to 0.
     *
     * @see QueueInterface#dequeue()
     */
    @Override
    public T dequeue() {
        T data;
        if (isEmpty()) {
            throw new NoSuchElementException("Queue cannot be empty");
        } else {
            data = backingArray[front];
            backingArray[front] = null;
            front = (front + 1) % backingArray.length;
        }
        size--;
        return data;
    }

    /**
     * Add the given data to the queue.
     *
     * If sufficient space is not available in the backing array, you should
     * regrow it to double the current length. If a regrow is necessary,
     * you should copy elements to the front of the new array and reset
     * front to 0.
     *
     * @see QueueInterface#enqueue(T)
     */
    @Override
    @SuppressWarnings("unchecked")
    public void enqueue(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }

        if (size >= backingArray.length) {
            grow();
        }

        backingArray[back] = data;
        back = (back + 1) % backingArray.length;
        size++;
    }

    /**
     * Helper method that grows the backing array to twice its capacity.
     */
    @SuppressWarnings("unchecked")
    private void grow() {
        T[] temp = (T[]) new Object[size << 1];
        for (int i = 0; i < size; i++) {
            temp[i] = backingArray[(i + front) % size];
        }
        backingArray = temp;
        front = 0;
        back = size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    /**
     * Returns the backing array of this queue.
     * Normally, you would not do this, but we need it for grading your work.
     *
     * DO NOT USE THIS METHOD IN YOUR CODE.
     *
     * @return the backing array
     */
    public Object[] getBackingArray() {
        return backingArray;
    }
}
