import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * My implementation of various applications of course concepts.
 *
 * @author Jalo Moster
 * @version 1.0
 */
public class ConceptApplication {

    /**
     * For this method, find the total number of pairs in a given array that
     * sum up to the given k value. Individual entries in the array cannot be
     * used for more than one pair and negative values may be present. You do
     * not have to worry about integer underflow or overflow.
     *
     * This solution should run in O(n) time and use O(n) extra space.
     * Only make one pass through the array when solving this problem.
     *
     * @param arr the array to find pairs within
     * @param k the desired sum of pairs to find
     * @return the number of pairs present in arr that sum up to k
     */
    public static int countAllPairs(int[] arr, int k) {
        if (arr == null) {
            return 0;
        }

        Map<Integer, Integer> occurrences = new HashMap<>();
        Map<Integer, Boolean> isUsed = new HashMap<>();
        for (int item : arr) {
            occurrences.putIfAbsent(item, 0);
            isUsed.putIfAbsent(item, false);
            occurrences.put(item, occurrences.get(item) + 1);
        }

        int count = 0;
        for (int item : arr) {
            if (isUsed.get(k - item) != null) {
                if (!isUsed.get(k - item)) {
                    isUsed.put(k - item, true);
                    if (occurrences.get(k - item) != null) {
                        count += occurrences.get(k - item);
                    }
                }
            }
        }
        return count / 2;
    }

    /**
     * Reverse the order of nodes contained in the given LinkedList. Given a
     * list of nodes a->b->c, the returned list from this method should be
     * c->b->a. The reverse of a singular or null node is simply the node
     * itself.
     *
     * Implement this method in O(n) time and O(1) space, not including the
     * recursive stack if utilized. Only make one pass through the list when
     * solving this problem.
     *
     * @param head the head reference for the LinkedList to reverse
     * @param <T> data type
     * @return a reverse of the passed in LinkedList
     */
    public static <T> LinkedListNode<T> reverse(LinkedListNode<T> head) {
        if (head == null) {
            return null;
        }
        return createStack(head, new LinkedListNode<>(head.getData()));
    }

    /**
     * {@link #reverse(LinkedListNode)} helper method that recursively builds a
     * linked list stack
     *
     * @param node the node to reverse
     * @param stack the stack being built
     * @param <T> generic typing of data in the linked list
     * @return the reversed linked list as a stack
     */
    private static <T> LinkedListNode<T> createStack(LinkedListNode<T> node,
                                                     LinkedListNode<T> stack) {
        if (node.getNext() != null) {
            node = node.getNext();
            stack = new LinkedListNode<>(node.getData(), stack);
            return createStack(node, stack);
        }
        return stack;
    }

    /**
     * Given a Binary Tree, determine whether it is symmetric about the root.
     * For this assignment, symmetry is defined as a mirroring of the nodes to
     * the left and right of the root with regards to shape and data. A singular
     * or null node is symmetric. For example, the following tree is considered
     * to be symmetric.
     *
     *                          a
     *                        /   \
     *                       b     b
     *                     /  \  /  \
     *                    c   d d    c
     *
     * Your implementation should run in O(n) time and use O(1) extra space, not
     * including the recursive stack if utilized. Only make one traversal
     * through the tree when solving this problem.
     *
     * Do not modify the given tree.
     *
     * @param root the root of the tree to check
     * @param <T> data type
     * @return true if the tree is symmetric, false otherwise
     */
    public static <T> boolean isSymmetric(BinaryNode<T> root) {
        return isSymmetric(root, root);
    }

    /**
     * {@link #isSymmetric(BinaryNode)} helper method that recursively checks if
     * a binary search tree is symmetric in nature.
     *
     * @param node1 the left subtree of the current node
     * @param node2 the right subtree of the current node
     * @param <T> generic typing of the data being compared
     * @return {@code true} if the binary search tree is symmetric
     */
    private static <T> boolean isSymmetric(BinaryNode<T> node1,
                                          BinaryNode<T> node2) {
        if (node1 == null && node2 == null) {
            return true;
        } else if (node1 != null && node2 != null) {
            if (node1.getData().equals(node2.getData())) {
                return isSymmetric(node1.getLeft(), node2.getRight())
                        && isSymmetric(node1.getRight(), node2.getLeft());
            }
        }
        return false;
    }

    /**
     * In this problem, you are given an array of comparable objects. You are
     * told to return a list of the k largest objects in ascending order.
     *
     * If k is not positive, return an empty array
     * If k > array length, return all contents of the array in ascending order.
     * The array you are given will never be null.
     *
     * This solution should run in average and worst case O(n log k) time and
     * use just O(k) space. Only make one pass through the array when solving
     * this problem.
     *
     * @param arr the input array of Comparable objects
     * @param k the number of elements to return
     * @param <T> a comparable object
     * @return an array of the k largest elements in arr in ascending order
     */
    @SuppressWarnings("unchecked")
    public static <T extends Comparable<? super T>> T[] findKLargest(T[] arr,
                                                                     int k) {
        T[] largest;
        if (k <= 0) {
            largest = (T[]) new Comparable[]{};
        } else if (k > arr.length) {
            buildHeap(arr); // O(n)
            heapSort(arr, 0); // O(k log n)
            largest = arr;
        } else {
            buildHeap(arr); // O(n)
            heapSort(arr, arr.length - k); // O(k log n)
            largest = (T[]) new Comparable[k];
            int count = 0;
            for (int i = arr.length - k; i < arr.length; i++) {
                largest[count] = arr[i];
                count++;
            }
        }
        return largest;
    }

    /**
     * Heapify method that recursively heapifies the current node down to its
     * left and right children. Heapify follows a max heap configuration. The
     * time complexity of Heapify is O(log n)
     *
     * @param arr the array to heapify
     * @param size the size of the array
     * @param i the current node to heapify
     * @param <T> generic typing of data being heapified
     */
    private static <T extends Comparable<? super T>> void heapify(T[] arr,
                                                                  int size,
                                                                  int i) {
        int max = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < size && arr[left].compareTo(arr[max]) > 0) {
            max = left;
        }

        if (right < size && arr[right].compareTo(arr[max]) > 0) {
            max = right;
        }

        if (max != i) {
            T temp = arr[i];
            arr[i] = arr[max];
            arr[max] = temp;

            heapify(arr, size, max);
        }
    }

    /**
     * Build heap takes O(n) time and uses the array passed in so no extra
     * space allotted!
     *
     * @param arr the array to build the heap on
     * @param <T> generic typing of data in the array
     */
    private static <T extends Comparable<? super T>> void buildHeap(T[] arr) {
        int size = arr.length;
        for (int i = size / 2 - 1; i >= 0; i--) {
            heapify(arr, size, i);
        }
    }

    /**
     * The heap sort algorithm takes O(n log n) time to sort and allots O(1)
     * storage space. This modified heap sort algorithm should only take
     * O(k log n) time.
     *
     * @param arr the array to sort
     * @param stop index to stop sorting at
     * @param <T> generic typing of data to sort
     */
    private static <T extends Comparable<? super T>> void heapSort(T[] arr,
                                                                   int stop) {
        int size = arr.length;
        for (int i = size - 1; i >= stop; i--) {
            T temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;

            heapify(arr, i, 0);
        }
    }

    /**
     * In this problem you are given a string of characters. You must act
     * somewhat like a parser and determine if this string is valid based
     * on the brackets (parenthesis (), square brackets [], curly braces {}).
     *
     * It will return true if for every open bracket: (, [, {, there is a
     * corresponding closed bracket: ), ], }, and no two pairs of brackets
     * partially overlap.
     *
     * [()] is valid, but [(]) is not as the contents of the parenthesis
     * partially overlap with the contents of the square brackets.
     *
     * A string without brackets is also a valid string. You will never be
     * given a null string as input.
     *
     * This should run in O(n) time with O(n) extra space. Only make one pass
     * through the string when solving this problem.
     *
     * @param str input of characters that needs to be parsed
     * @return whether or not the string has a valid combination of brackets:
     *  {}, (), []
     */
    public static boolean matchingBrackets(String str) {
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == '{' || c == '(' || c == '[') {
                stack.push(c);
            }

            if (c == '}' || c == ')' || c == ']') {
                if (stack.isEmpty()) {
                    return false;
                }
                char open = stack.pop();
                char close = c;
                if (!((open == '{' && close == '}')
                    || (open == '(' && close == ')')
                    || (open == '[' && close == ']'))) {
                    return false;
                }
            }
        }

        return stack.isEmpty();
    }
}
