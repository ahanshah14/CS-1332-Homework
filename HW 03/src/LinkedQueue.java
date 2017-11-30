import java.util.NoSuchElementException;

/**
 * My implementation of a linked queue.
 *
 * @author Jalo Moster
 * @version 1.0
 */
public class LinkedQueue<T> implements QueueInterface<T> {
    private LinkedNode<T> head;
    private LinkedNode<T> tail;
    private int size;

    @Override
    public T dequeue() {
        T data;
        switch (size) {
        case 0:
            throw new NoSuchElementException("Queue cannot be empty");
        case 1:
            data = head.getData();
            head = null;
            tail = null;
            break;
        default:
            data = head.getData();
            head = head.getNext();
        }
        size--;
        return data;
    }

    @Override
    public void enqueue(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }

        LinkedNode<T> newNode = new LinkedNode<>(data);
        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else {
            tail.setNext(newNode);
            tail = newNode;
        }
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
     * Returns the head of this queue.
     * Normally, you would not do this, but we need it for grading your work.
     *
     * DO NOT USE THIS METHOD IN YOUR CODE.
     *
     * @return the head node
     */
    public LinkedNode<T> getHead() {
        return head;
    }

    /**
     * Returns the tail of this queue.
     * Normally, you would not do this, but we need it for grading your work.
     *
     * DO NOT USE THIS METHOD IN YOUR CODE.
     *
     * @return the tail node
     */
    public LinkedNode<T> getTail() {
        return tail;
    }
}