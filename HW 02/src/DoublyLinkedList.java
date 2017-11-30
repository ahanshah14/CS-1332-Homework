/**
 * My implementation of a DoublyLinkedList
 *
 * @author Jalo Moster
 * @version 1.0
 */
public class DoublyLinkedList<T> implements LinkedListInterface<T> {
    private LinkedListNode<T> head;
    private LinkedListNode<T> tail;
    private int size;

    @Override
    public void addAtIndex(int index, T data) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        } else if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }

        if (index == 0) {
            addToFront(data);
        } else if (index == size) {
            addToBack(data);
        } else {
            LinkedListNode<T> newNode = new LinkedListNode<>(data);

            if (index < ((size / 2) + 1)) {
                LinkedListNode<T> temp = head;
                int i = 0;
                while (i < index) {
                    temp = temp.getNext();
                    i++;
                }
                newNode.setNext(temp);
                newNode.setPrevious(temp.getPrevious());
                newNode.getPrevious().setNext(newNode);
                newNode.getNext().setPrevious(newNode);
            } else {
                LinkedListNode<T> temp = tail;
                int i = size - 1;
                while (i > index) {
                    temp = temp.getPrevious();
                    i--;
                }
                newNode.setNext(temp);
                newNode.setPrevious(temp.getPrevious());
                newNode.getPrevious().setNext(newNode);
                newNode.getNext().setPrevious(newNode);
            }
            size++;
        }
    }

    @Override
    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }

        LinkedListNode<T> newNode = new LinkedListNode<>(data);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else if (head == tail) {
            newNode.setNext(tail);
            tail.setPrevious(newNode);
            head = newNode;
        } else {
            newNode.setNext(head);
            head.setPrevious(newNode);
            head = newNode;
        }
        size++;
    }

    @Override
    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }

        LinkedListNode<T> newNode = new LinkedListNode<>(data);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else if (head == tail) {
            newNode.setPrevious(tail);
            head.setNext(newNode);
            tail = newNode;
        } else {
            newNode.setPrevious(tail);
            tail.setNext(newNode);
            tail = newNode;
        }
        size++;
    }

    @Override
    public T removeAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }

        if (index == 0) {
            return removeFromFront();
        } else if (index == size - 1) {
            return removeFromBack();
        } else {
            T data;
            if (index < ((size / 2) + 1)) {
                LinkedListNode<T> temp = head;
                int i = 0;
                while (i < index) {
                    temp = temp.getNext();
                    i++;
                }
                data = temp.getData();
                temp.getPrevious().setNext(temp.getNext());
                temp.getNext().setPrevious(temp.getPrevious());
            } else {
                LinkedListNode<T> temp = tail;
                int i = size - 1;
                while (i > index) {
                    temp = temp.getPrevious();
                    i--;
                }
                data = temp.getData();
                temp.getPrevious().setNext(temp.getNext());
                temp.getNext().setPrevious(temp.getPrevious());
            }
            size--;
            return data;
        }
    }

    @Override
    public T removeFromFront() {
        T data = null;
        if (head == null) {
            return data;
        } else if (head == tail) {
            data = head.getData();
            head = null;
            tail = null;
            size--;
            return data;
        } else {
            data = head.getData();
            head = head.getNext();
            head.setPrevious(null);
            size--;
            return data;
        }
    }

    @Override
    public T removeFromBack() {
        T data = null;
        if (head == null) {
            return data;
        } else if (head == tail) {
            data = tail.getData();
            head = null;
            tail = null;
            size--;
            return data;
        } else {
            data = tail.getData();
            tail = tail.getPrevious();
            tail.setNext(null);
            size--;
            return data;
        }
    }

    @Override
    public boolean removeFirstOccurrence(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }

        if (size == 0) {
            return false;
        } else {
            LinkedListNode<T> curr = head;

            for (int i = 0; i < size; i++) {
                if (data.equals(curr.getData())) {
                    if (i == 0) {
                        removeFromFront();
                    } else if (i == size - 1) {
                        removeFromBack();
                    } else {
                        curr.getPrevious().setNext(curr.getNext());
                        curr.getNext().setPrevious(curr.getPrevious());
                        size--;
                    }
                    return true;
                }
                curr = curr.getNext();
            }
            return false;
        }
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }

        if (index == 0) {
            return head.getData();
        } else if (index == size - 1) {
            return tail.getData();
        } else {
            if (index < ((size / 2) + 1)) {
                LinkedListNode<T> temp = head;
                int i = 0;
                while (i < index) {
                    temp = temp.getNext();
                    i++;
                }
                return temp.getData();
            } else {
                LinkedListNode<T> temp = tail;
                int i = size - 1;
                while (i > index) {
                    temp = temp.getPrevious();
                    i--;
                }
                return temp.getData();
            }
        }
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
    public Object[] toArray() {
        Object[] objects = new Object[size];
        if (isEmpty()) {
            return objects;
        }

        LinkedListNode<T> curr = head;
        for (int i = 0; i < size; i++) {
            objects[i] = curr.getData();
            curr = curr.getNext();
        }
        return objects;
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
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public LinkedListNode<T> getHead() {
        return head;
    }

    @Override
    public LinkedListNode<T> getTail() {
        return tail;
    }
}
