import java.util.NoSuchElementException;

/**
 * My implementation of a linked stack.
 *
 * @author Jalo Moster
 * @version 1.0
 */
public class LinkedStack<T> implements StackInterface<T> {
    private LinkedNode<T> head;
    private int size;

    @Override
    public T pop() {
        T data;
        switch (size) {
        case 0:
            throw new NoSuchElementException("Stack cannot be empty");
        case 1:
            data = head.getData();
            head = null;
            break;
        default:
            data = head.getData();
            head = head.getNext();
        }
        size--;
        return data;
    }

    @Override
    public void push(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }

        head = new LinkedNode<>(data, head);
        size++;
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
     * Returns the head of this stack.
     * Normally, you would not do this, but we need it for grading your work.
     *
     * DO NOT USE THIS METHOD IN YOUR CODE.
     *
     * @return the head node
     */
    public LinkedNode<T> getHead() {
        return head;
    }
}