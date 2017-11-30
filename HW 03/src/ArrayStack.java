import java.util.NoSuchElementException;

/**
 * My implementation of an array-backed stack.
 *
 * @author Jalo Moster
 * @version 1.0
 */
public class ArrayStack<T> implements StackInterface<T> {
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new ArrayStack.
     */
    @SuppressWarnings("unchecked")
    public ArrayStack() {
        backingArray = (T[]) new Object[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Pop from the stack.
     *
     * Do not shrink the backing array.
     *
     * @see StackInterface#pop()
     */
    @Override
    public T pop() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack cannot be empty");
        }

        T popped = backingArray[size - 1];
        backingArray[size - 1] = null;
        size--;
        return popped;
    }

    /**
     * Push the given data onto the stack.
     *
     * If sufficient space is not available in the backing array, you should
     * regrow it to double the current length.
     *
     * @see StackInterface#push(T)
     */
    @Override
    public void push(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }

        if (size >= backingArray.length) {
            grow();
        }

        backingArray[size] = data;
        size++;
    }

    /**
     * Helper method that grows the backing array to twice its capacity.
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
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    /**
     * Returns the backing array of this stack.
     * Normally, you would not do this, but we need it for grading your work.
     *
     * DO NOT USE THIS METHOD IN YOUR CODE.
     *
     * @return the backing array
     */
    public Object[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }
}
