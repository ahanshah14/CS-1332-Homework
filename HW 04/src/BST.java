import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;

/**
 * My implementation of a binary search tree.
 *
 * @author Jalo Moster
 * @version 1.0
 */
public class BST<T extends Comparable<? super T>> implements BSTInterface<T> {
    private BSTNode<T> root;
    private int size;

    /**
     * A no-argument constructor that should initialize an empty BST.
     * DO NOT IMPLEMENT THIS CONSTRUCTOR!
     */
    public BST() {
        root = null;
        size = 0;
    }

    /**
     * Initializes the BST with the data in the Collection. The data
     * should be added in the same order it is in the Collection.
     *
     * @param data the data to add to the tree
     * @throws IllegalArgumentException if data or any element in data is null
     */
    public BST(Collection<T> data) {
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
     * @param data data to add to {@link BST}
     * @param node current node in recursive call
     * @return root node with the data added
     */
    private BSTNode<T> add(T data, BSTNode<T> node) {
        if (node == null) {
            size++;
            return new BSTNode<>(data);
        } else if (data.compareTo(node.getData()) > 0) {
            node.setRight(add(data, node.getRight()));
        } else if (data.compareTo(node.getData()) < 0) {
            node.setLeft(add(data, node.getLeft()));
        }
        return node;
    }

    @Override
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }

        BSTNode<T> removed = new BSTNode<>(null);
        root = remove(data, root, removed);
        return removed.getData();
    }

    /**
     * {@link #remove(Comparable)} helper method.
     *
     * @param data    data to remove from {@link BST}
     * @param node    current node in recursive call
     * @param removed the removed data to return
     * @return root node without the removed data
     */
    private BSTNode<T> remove(T data, BSTNode<T> node, BSTNode<T> removed) {
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
                BSTNode<T> replace = new BSTNode<>(null);
                node.setLeft(getPredecessor(node.getLeft(), replace));
                node.setData(replace.getData());
            }
        }
        return node;
    }

    /**
     * Gets the predecessor of the parent node for removal and replacement.
     *
     * @param node    node to be deleted
     * @param replace the node replaced
     * @return node that's replaced by the predecessor
     */
    private BSTNode<T> getPredecessor(BSTNode<T> node, BSTNode<T> replace) {
        if (node.getRight() == null) {
            replace.setData(node.getData());
            return node.getLeft();
        } else {
            node.setRight(getPredecessor(node.getRight(), replace));
            return node;
        }
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
     * @param data data to get from {@link BST}
     * @param node current node of recursive call
     * @return node with matching data
     */
    private BSTNode<T> get(T data, BSTNode<T> node) {
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
     * @return {@code true} if code is found in {@link BST}
     */
    private boolean contains(T data, BSTNode<T> node) {
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
     * @param list list that contains {@link BST} elements in pre-order
     */
    private void preorder(BSTNode<T> node, List<T> list) {
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
     * @param list list that contains {@link BST} elements in post-order
     */
    private void postorder(BSTNode<T> node, List<T> list) {
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
     * @param list list that contains {@link BST} elements in in-order
     */
    private void inorder(BSTNode<T> node, List<T> list) {
        if (node != null) {
            inorder(node.getLeft(), list);
            list.add(node.getData());
            inorder(node.getRight(), list);
        }
    }

    @Override
    public List<T> levelorder() {
        BSTNode<T> node;
        List<T> list = new ArrayList<>();
        Queue<BSTNode<T>> queue = new LinkedList<>();

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
     * @param list list that contains {@link BST} leaves in descending order
     */
    private void listLeavesDescending(BSTNode<T> node, List<T> list) {
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
            return height(root);
        }
    }

    /**
     * {@link #height()} helper method.
     *
     * @param node current node of recursive call
     * @return the height of the current node
     */
    private int height(BSTNode<T> node) {
        if (node.getLeft() == null && node.getRight() == null) {
            return 0;
        } else if (node.getLeft() == null && node.getRight() != null) {
            return height(node.getRight()) + 1;
        } else if (node.getLeft() != null && node.getRight() == null) {
            return height(node.getLeft()) + 1;
        } else {
            int left = height(node.getLeft());
            int right = height(node.getRight());
            return ((left > right) ? left : right) + 1;
        }
    }

    @Override
    public BSTNode<T> getRoot() {
        return root;
    }
}
