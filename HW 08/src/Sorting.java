import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

/**
 * My implementation of various sorting algorithms.
 *
 * @author Jalo Moster
 * @version 1.0
 */
public class Sorting {

    /**
     * Implement cocktail sort.
     *
     * It should be:
     *  in-place
     *  stable
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n)
     *
     * Any duplicates in the array should be in the same relative position after
     * sorting as they were before sorting. (stable).
     *
     * See the PDF for more info on this sort.
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void cocktailSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Arguments cannot be null");
        }

        boolean swapped = true;
        int i = 0;
        int j = arr.length - 1;
        while (i < j && swapped) {
            swapped = false;
            for (int k = i; k < j; k++) {
                if (comparator.compare(arr[k], arr[k + 1]) > 0) {
                    swap(arr, k, k + 1);
                    swapped = true;
                }
            }
            j--;
            if (swapped) {
                swapped = false;
                for (int k = j; k > i; k--) {
                    if (comparator.compare(arr[k], arr[k - 1]) < 0) {
                        swap(arr, k, k - 1);
                        swapped = true;
                    }
                }
            }
            i++;
        }
    }

    /**
     * Implement insertion sort.
     *
     * It should be:
     *  in-place
     *  stable
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n)
     *
     * Any duplicates in the array should be in the same relative position after
     * sorting as they were before sorting. (stable).
     *
     * See the PDF for more info on this sort.
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Arguments cannot be null");
        }

        for (int i = 1; i < arr.length; i++) {
            for (int j = i; j > 0; j--) {
                if (comparator.compare(arr[j], arr[j - 1]) < 0) {
                    swap(arr, j, j - 1);
                } else {
                    j = 0;
                }
            }
        }
    }

    /**
     * Implement selection sort.
     *
     * It should be:
     *  in-place
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n^2)
     *
     * Note that there may be duplicates in the array, but they may not
     * necessarily stay in the same relative order.
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void selectionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Arguments cannot be null");
        }

        for (int i = 0; i < arr.length; i++) {
            int min = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (comparator.compare(arr[j], arr[min]) < 0) {
                    min = j;
                }
            }
            swap(arr, i, min);
        }
    }

    /**
     * Implement quick sort.
     *
     * Use the provided random object to select your pivots.
     * For example if you need a pivot between a (inclusive)
     * and b (exclusive) where b > a, use the following code:
     *
     * int pivotIndex = r.nextInt(b - a) + a;
     *
     * It should be:
     *  in-place
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n log n)
     *
     * Note that there may be duplicates in the array.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not use the one we have taught you!
     *
     * @throws IllegalArgumentException if the array or comparator or rand is
     * null
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @param rand the Random object used to select pivots
     */
    public static <T> void quickSort(T[] arr, Comparator<T> comparator,
                                     Random rand) {
        if (arr == null || comparator == null || rand == null) {
            throw new IllegalArgumentException("Arguments cannot be null");
        }

        quickSort(arr, comparator, rand, 0, arr.length - 1);
    }

    /**
     * {@link #quickSort(Object[], Comparator, Random)} helper method.
     *
     * @param arr the array to sort
     * @param comparator the Comparator used to compare the data in arr
     * @param rand the Random object used to select pivots
     * @param low the low index
     * @param high the high index
     * @param <T> data to sort
     */
    private static <T> void quickSort(T[] arr, Comparator<T> comparator,
                                     Random rand, int low, int high) {
        if (low < high) {
            int p = partition(arr, comparator, rand, low, high);

            quickSort(arr, comparator, rand, low, p - 1);
            quickSort(arr, comparator, rand, p + 1, high);
        }
    }

    /**
     * Sorts and partitions the array.
     *
     * @param arr the array to partition
     * @param comparator the Comparator used to compare the data in arr
     * @param rand the Random object used to select pivots
     * @param low the low index
     * @param high the high index
     * @param <T> data to partition
     * @return an integer corresponding to the parition index
     */
    private static <T> int partition(T[] arr, Comparator<T> comparator,
                                     Random rand, int low, int high) {
        int pivot = rand.nextInt((high + 1) - low) + low;
        swap(arr, low, pivot);
        int i = low + 1;
        int j = high;
        while (i <= j) {
            while (i <= j && comparator.compare(arr[i], arr[low]) <= 0) {
                i++;
            }
            while (i <= j && comparator.compare(arr[j], arr[low]) >= 0) {
                j--;
            }
            if (i <= j) {
                swap(arr, i, j);
                i++;
                j--;
            }
        }
        swap(arr, low, j);
        return j;
    }

    /**
     * Implement merge sort.
     *
     * It should be:
     *  stable
     *
     * Have a worst case running time of:
     *  O(n log n)
     *
     * And a best case running time of:
     *  O(n log n)
     *
     * You can create more arrays to run mergesort, but at the end,
     * everything should be merged back into the original T[]
     * which was passed in.
     *
     * Any duplicates in the array should be in the same relative position after
     * sorting as they were before sorting.
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Input cannot be null");
        }

        mergeSort(arr, comparator, 0, arr.length - 1);
    }

    /**
     * {@link #mergeSort(Object[], Comparator)} helper method.
     *
     * @param arr the array to sort
     * @param comparator the Comparator used to compare the data in arr
     * @param low the low index
     * @param high the high index
     * @param <T> data to sort
     */
    private static <T> void mergeSort(T[] arr, Comparator<T> comparator,
                                      int low, int high) {
        if (low < high) {
            int mid = (low + high) / 2;
            mergeSort(arr, comparator, low, mid);
            mergeSort(arr, comparator, mid + 1, high);
            merge(arr, comparator, low, mid, high);
        }
    }

    /**
     * Sorts and merges the array.
     *
     * @param arr the array to merge
     * @param comparator the Comparator used to compare the data in arr
     * @param low the low index
     * @param mid the middle index
     * @param high the high index
     * @param <T> data to merge
     */
    @SuppressWarnings("unchecked")
    private static <T> void merge(T[] arr, Comparator<T> comparator,
                                  int low, int mid, int high) {
        T[] temp = (T[]) new Object[arr.length];
        for (int i = low; i <= high; i++) {
            temp[i] = arr[i];
        }
        int i = low;
        int j = mid + 1;
        for (int k = low; k <= high; k++) {
            if (i > mid) {
                arr[k] = temp[j];
                j++;
            } else if (j > high) {
                arr[k] = temp[i];
                i++;
            } else if (comparator.compare(temp[i], temp[j]) <= 0) {
                arr[k] = temp[i];
                i++;
            } else {
                arr[k] = temp[j];
                j++;
            }
        }
    }

    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * Remember you CANNOT convert the ints to strings at any point in your
     * code! Doing so may result in a 0 for the implementation.
     *
     * It should be:
     *  stable
     *
     * Have a worst case running time of:
     *  O(kn)
     *
     * And a best case running time of:
     *  O(kn)
     *
     * Any duplicates in the array should be in the same relative position after
     * sorting as they were before sorting. (stable)
     *
     * Do NOT use {@code Math.pow()} in your sort.
     *
     * You may use {@code java.util.ArrayList} or {@code java.util.LinkedList}
     * if you wish, but it may only be used inside radix sort and any radix sort
     * helpers. Do NOT use these classes with other sorts.
     *
     * @throws IllegalArgumentException if the array is null
     * @param arr the array to be sorted
     */
    @SuppressWarnings("unchecked")
    public static void lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("Arguments cannot be null");
        }

        if (arr.length == 0) {
            return;
        }

        int div = 1;
        int itr = getIteration(arr);
        for (int i = 0; i < itr; i++) {
            ArrayList<Integer>[] bucket = new ArrayList[19];

            for (int item : arr) {
                int digit = (item / div) % 10;
                if (bucket[digit + 9] == null) {
                    bucket[digit + 9] = new ArrayList<>();
                }
                bucket[digit + 9].add(item);
            }

            int index = 0;
            for (ArrayList<Integer> list : bucket) {
                if (list != null) {
                    for (int j = 0; j < list.size(); j++) {
                        arr[index] = list.get(j);
                        index++;
                    }
                }
            }
            div *= 10;
        }
    }

    /**
     * Calculate the max number of iterations to perform based on the largest
     * or smallest in the array.
     *
     * @param arr the array that contains the largest/smallest number
     * @return an integer representing the max number of iterations
     */
    private static int getIteration(int[] arr) {
        int min = arr[0];
        int max = arr[0];

        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < min) {
                min = arr[i];
            }
            if (arr[i] > max) {
                max = arr[i];
            }
        }

        int digits = 0;
        while (!(min == 0 && max == 0)) {
            min /= 10;
            max /= 10;
            digits++;
        }
        return digits;
    }

    /**
     * Method that swaps two elements in an array.
     *
     * @param arr array to access
     * @param index1 first index to swap
     * @param index2 second index to swap
     * @param <T> data type that represents the elements being swapped
     */
    private static <T> void swap(T[] arr, int index1, int index2) {
        T temp = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = temp;
    }
}
