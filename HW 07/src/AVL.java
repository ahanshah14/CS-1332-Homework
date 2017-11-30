import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;

/**
 * My implementation of an AVL Tree.
 *
 * @author Jalo Moster
 * @version 1.0
 */
public class AVL<T extends Comparable<? super T>> implements AVLInterface<T> {
    private AVLNode<T> root;
    private int size;

    /**
     * A no argument constructor that should initialize an empty AVL tree.
     * DO NOT IMPLEMENT THIS CONSTRUCTOR!
     */
    public AVL() {
        root = null;
        size = 0;
    }

    /**
     * Initializes the AVL tree with the data in the Collection. The data
     * should be added in the same order it is in the Collection.
     *
     * @param data the data to add to the tree
     * @throws IllegalArgumentException if data or any element in data is null
     */
    public AVL(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }

        for (T t : data) {
            add(t);
        }
    }

    @Override
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }

        root = add(data, root);
    }

    /**
     * {@link #add(Comparable)} helper method.
     *
     * @param data data to add
     * @param node current node in recursive call
     * @return root node with the data added
     */
    private AVLNode<T> add(T data, AVLNode<T> node) {
        if (node == null) {
            size++;
            return new AVLNode<>(data);
        } else if (data.compareTo(node.getData()) > 0) {
            node.setRight(add(data, node.getRight()));
        } else if (data.compareTo(node.getData()) < 0) {
            node.setLeft(add(data, node.getLeft()));
        }
        update(node);
        node = rebalance(node);
        return node;
    }

    @Override
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot null");
        }

        AVLNode<T> removed = new AVLNode<>(null);
        root = remove(data, root, removed);
        return removed.getData();
    }

    /**
     * {@link #remove(Comparable)} helper method.
     *
     * @param data    data to remove
     * @param node    current node in recursive call
     * @param removed container to save removed data
     * @return root node without the data removed
     */
    private AVLNode<T> remove(T data, AVLNode<T> node, AVLNode<T> removed) {
        if (node == null) {
            throw new NoSuchElementException("Data not found");
        } else if (data.compareTo(node.getData()) > 0) {
            node.setRight(remove(data, node.getRight(), removed));
        } else if (data.compareTo(node.getData()) < 0) {
            node.setLeft(remove(data, node.getLeft(), removed));
        } else {
            removed.setData(node.getData());
            if (node.getLeft() == null) {
                size--;
                return node.getRight();
            } else if (node.getRight() == null) {
                size--;
                return node.getLeft();
            } else {
                size--;
                AVLNode<T> replace = new AVLNode<>(null);
                node.setLeft(getPredecessor(node.getLeft(), replace));
                node.setData(replace.getData());
            }
        }
        update(node);
        node = rebalance(node);
        return node;
    }

    /**
     * Gets the predecessor of the parent node for removal and replacement.
     *
     * @param node    node to be deleted
     * @param replace the
     * @return node that's replaced by the predecessor
     */
    private AVLNode<T> getPredecessor(AVLNode<T> node, AVLNode<T> replace) {
        if (node.getRight() == null) {
            replace.setData(node.getData());
            return node.getLeft();
        } else {
            node.setRight(getPredecessor(node.getRight(), replace));
            update(node);
            node = rebalance(node);
            return node;
        }
    }

    /**
     * Updates the height and balance factor of individual nodes.
     *
     * @param node current node
     */
    private void update(AVLNode<T> node) {
        if (node != null) {
            AVLNode<T> left = node.getLeft();
            AVLNode<T> right = node.getRight();
            int leftHeight = (left != null) ? left.getHeight() : -1;
            int rightHeight = (right != null) ? right.getHeight() : -1;
            node.setBalanceFactor(leftHeight - rightHeight);
            node.setHeight(max(leftHeight, rightHeight) + 1);
        }
    }

    /**
     * Gets the max between two integer values.
     *
     * @param left  left child height value
     * @param right right child height value
     * @return the max of the two values
     */
    private int max(int left, int right) {
        return (left > right) ? left : right;
    }

    /**
     * Checks if node does not have AVL properties and re-balances accordingly.
     *
     * @param node current node to check AVL properties
     * @return root node with rebalanced children
     */
    private AVLNode<T> rebalance(AVLNode<T> node) {
        switch (node.getBalanceFactor()) {
        case 2:
            switch (node.getLeft().getBalanceFactor()) {
            case 0:
            case 1:
                node = rotateRight(node);
                break;
            case -1:
                node.setLeft(rotateLeft(node.getLeft()));
                node = rotateRight(node);
                break;
            default:
                break;
            }
            return node;
        case -2:
            switch (node.getRight().getBalanceFactor()) {
            case 0:
            case -1:
                node = rotateLeft(node);
                break;
            case 1:
                node.setRight(rotateRight(node.getRight()));
                node = rotateLeft(node);
                break;
            default:
                break;
            }
            return node;
        default:
            return node;
        }
    }

    /**
     * Rotate the node and its child right.
     *
     * @param node the parent node
     * @return the child node with the parent node as its right child
     */
    private AVLNode<T> rotateRight(AVLNode<T> node) {
        AVLNode<T> pivot = node.getLeft();
        node.setLeft(pivot.getRight());
        update(node);
        pivot.setRight(node);
        update(pivot);
        return pivot;
    }

    /**
     * Rotate the node and its child left.
     *
     * @param node the parent node
     * @return the child node with the parent node as its left child
     */
    private AVLNode<T> rotateLeft(AVLNode<T> node) {
        AVLNode<T> pivot = node.getRight();
        node.setRight(pivot.getLeft());
        update(node);
        pivot.setLeft(node);
        update(pivot);
        return pivot;
    }

    @Override
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }

        return get(data, root).getData();
    }

    /**
     * {@link #get(Comparable)} helper method.
     *
     * @param data data to get from {@link AVL}
     * @param node current node of recursive call
     * @return node with matching data
     */
    private AVLNode<T> get(T data, AVLNode<T> node) {
        if (node == null) {
            throw new NoSuchElementException("Data not found");
        } else if (data.compareTo(node.getData()) > 0) {
            return get(data, node.getRight());
        } else if (data.compareTo(node.getData()) < 0) {
            return get(data, node.getLeft());
        } else {
            return node;
        }
    }

    @Override
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }

        return contains(data, root);
    }

    /**
     * {@link #contains(Comparable)} helper method.
     *
     * @param data data to find
     * @param node current node of recursive call
     * @return {@code true} if code is found in {@link AVL}
     */
    private boolean contains(T data, AVLNode<T> node) {
        if (node == null) {
            return false;
        } else if (data.compareTo(node.getData()) > 0) {
            return contains(data, node.getRight());
        } else if (data.compareTo(node.getData()) < 0) {
            return contains(data, node.getLeft());
        } else {
            return true;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public List<T> preorder() {
        List<T> list = new ArrayList<>();
        preorder(root, list);
        return list;
    }

    /**
     * {@link #preorder()} helper method.
     *
     * @param node current node of recursive call
     * @param list list that contains {@link AVL} elements in pre-order
     */
    private void preorder(AVLNode<T> node, List<T> list) {
        if (node != null) {
            list.add(node.getData());
            preorder(node.getLeft(), list);
            preorder(node.getRight(), list);
        }
    }

    @Override
    public List<T> postorder() {
        List<T> list = new ArrayList<>();
        postorder(root, list);
        return list;
    }

    /**
     * {@link #postorder()} helper method.
     *
     * @param node current node of recursive call
     * @param list list that contains {@link AVL} elements in post-order
     */
    private void postorder(AVLNode<T> node, List<T> list) {
        if (node != null) {
            postorder(node.getLeft(), list);
            postorder(node.getRight(), list);
            list.add(node.getData());
        }
    }

    @Override
    public List<T> inorder() {
        List<T> list = new ArrayList<>();
        inorder(root, list);
        return list;
    }

    /**
     * {@link #inorder()} helper method.
     *
     * @param node current node of recursive call
     * @param list list that contains {@link AVL} elements in in-order
     */
    private void inorder(AVLNode<T> node, List<T> list) {
        if (node != null) {
            inorder(node.getLeft(), list);
            list.add(node.getData());
            inorder(node.getRight(), list);
        }
    }

    @Override
    public List<T> levelorder() {
        AVLNode<T> node;
        List<T> list = new ArrayList<>();
        Queue<AVLNode<T>> queue = new LinkedList<>();

        if (size == 0) {
            return list;
        }

        queue.add(root);
        while (!queue.isEmpty()) {
            node = queue.remove();
            list.add(node.getData());
            if (node.getLeft() != null) {
                queue.add(node.getLeft());
            }
            if (node.getRight() != null) {
                queue.add(node.getRight());
            }
        }
        return list;
    }

    @Override
    public List<T> listLeavesDescending() {
        List<T> list = new ArrayList<>();
        listLeavesDescending(root, list);
        return list;
    }

    /**
     * {@link #listLeavesDescending()} helper method.
     *
     * @param node current node of recursive call
     * @param list list that contains {@link AVL} leaves in descending order
     */
    private void listLeavesDescending(AVLNode<T> node, List<T> list) {
        if (node != null) {
            listLeavesDescending(node.getRight(), list);
            if (node.getRight() == null && node.getLeft() == null) {
                list.add(node.getData());
            }
            listLeavesDescending(node.getLeft(), list);
        }
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public int height() {
        if (size == 0) {
            return -1;
        } else {
            AVLNode<T> left = root.getLeft();
            AVLNode<T> right = root.getRight();
            int leftHeight = ((left != null) ? left.getHeight() : -1);
            int rightHeight = ((right != null) ? right.getHeight() : -1);
            return ((leftHeight > rightHeight) ? leftHeight : rightHeight) + 1;
        }
    }

    @Override
    public AVLNode<T> getRoot() {
        return root;
    }
}
