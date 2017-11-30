import java.util.NoSuchElementException;

/**
 * My implementation of a min heap.
 *
 * @author Jalo Moster
 * @version 1.0
 */
public class MinHeap<T extends Comparable<? super T>> implements HeapInterface<T> {
    private T[] backingArray;
    private int size;

    /**
     * Creates a Heap with an initial capacity of {@code INITIAL_CAPACITY}
     * for the backing array.
     *
     * Use the constant field in the interface. Do not use magic numbers!
     */
    @SuppressWarnings("unchecked")
    public MinHeap() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Get the current node's parent.
     *
     * @param index current node's index
     * @return parent's index
     */
    private int parent(int index) {
        return index / 2;
    }

    /**
     * Get the current node's left child.
     *
     * @param index current node's index
     * @return left child's index
     */
    private int left(int index) {
        return 2 * index;
    }

    /**
     * Get the current node's right child.
     *
     * @param index current node's index
     * @return right child's index
     */
    private int right(int index) {
        return (2 * index + 1);
    }

    @Override
    public void add(T item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }

        if ((size + 1) == backingArray.length) {
            grow();
        }

        size++;
        backingArray[size] = item;

        heapifyUp(size);
    }

    /**
     * Helper method that, when called, grows the array by twice the size.
     */
    @SuppressWarnings("unchecked")
    private void grow() {
        T[] temp = (T[]) new Comparable[backingArray.length << 1];
        for (int i = 0; i < backingArray.length; i++) {
            temp[i] = backingArray[i];
        }
        backingArray = temp;
    }

    /**
     * Checks if current item is smaller than its parent and heapifies up until
     * it is in the correct position.
     *
     * @param index the index of the element you want to heapify up
     */
    private void heapifyUp(int index) {
        if (index > 1) {
            T parent = backingArray[parent(index)];
            if (backingArray[index].compareTo(parent) < 0) {
                swap(backingArray, index, parent(index));
                heapifyUp(parent(index));
            }
        }
    }

    @Override
    public T remove() {
        T removed = backingArray[1];
        switch (size) {
        case 0:
            throw new NoSuchElementException("Heap cannot be empty");
        case 1:
            backingArray[1] = null;
            size--;
            break;
        default:
            backingArray[1] = backingArray[size];
            backingArray[size] = null;
            size--;
            heapifyDown(1);
        }
        return removed;
    }

    /**
     * Method to percolate the current index down.
     *
     * @param index The index of the element you want to percolate down.
     */
    private void heapifyDown(int index) {
        int min = index;
        int l = left(index);
        int r = right(index);

        if (l < min && backingArray[l].compareTo(backingArray[min]) < 0) {
            min = l;
        }

        if (r < min && backingArray[r].compareTo(backingArray[min]) < 0) {
            min = r;
        }

        if (min != index) {
            swap(backingArray, index, min);
            heapifyDown(min);
        }
    }

    /**
     * A helper method that swaps index {@code index1} and {@code index2}.
     *
     * @param arr    the array being accessed
     * @param index1 the first index
     * @param index2 the second index
     */
    private void swap(T[] arr, int index1, int index2) {
        T temp = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = temp;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void clear() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        size = 0;
    }

    @Override
    public Comparable[] getBackingArray() {
        return backingArray;
    }

}
