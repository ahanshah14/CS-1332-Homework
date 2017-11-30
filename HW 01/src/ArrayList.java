/**
 * My implementation of an ArrayList.
 *
 * @author Jalo Moster
 * @version 1
 */
public class ArrayList<T> implements ArrayListInterface<T> {
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new ArrayList.
     */
    @SuppressWarnings("unchecked")
    public ArrayList() {
        backingArray = (T[]) new Object[INITIAL_CAPACITY];
        size = 0;
    }

    @Override
    public void addAtIndex(int index, T data) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        } else if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }

        if (size >= backingArray.length) {
            grow();
        }

        for (int i = size - 1; i >= index; i--) {
            backingArray[i + 1] = backingArray[i];
        }

        backingArray[index] = data;
        size++;
    }

    @Override
    public void addToFront(T data) {
        addAtIndex(0, data);
    }

    @Override
    public void addToBack(T data) {
        addAtIndex(size, data);
    }

    /**
     * Helper method that, when called, grows the array by twice the size.
     */
    @SuppressWarnings("unchecked")
    private void grow() {
        T[] temp = (T[]) new Object[size << 1];
        for (int i = 0; i < size; i++) {
            temp[i] = backingArray[i];
        }
        backingArray = temp;
    }

    @Override
    public T removeAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }

        T removed = backingArray[index];
        for (int i = index; i < size - 1; i++) {
            backingArray[i] = backingArray[i + 1];
        }
        backingArray[size - 1] = null;
        size--;
        return removed;
    }

    @Override
    public T removeFromFront() {
        switch (size) {
        case 0:
            return null;
        default:
            return removeAtIndex(0);
        }
    }

    @Override
    public T removeFromBack() {
        switch (size) {
        case 0:
            return null;
        default:
            return removeAtIndex(size - 1);
        }
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }

        return backingArray[index];
    }

    /**
     * Returns a String message that shows the index and current size of the
     * backing array.
     *
     * @param index the input typically for the add, remove, and get methods
     * @return returns a descriptive message about the index and size
     */
    private String outOfBoundsMsg(int index) {
        return "Index: " + index + ", Size: " + size;
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
        backingArray = (T[]) new Object[INITIAL_CAPACITY];
        size = 0;
    }

    @Override
    public Object[] getBackingArray() {
        return backingArray;
    }
}
