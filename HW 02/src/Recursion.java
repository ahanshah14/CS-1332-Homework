/**
 * My implementation of the isPalindrome and gcd methods
 *
 * @author Jalo Moster
 * @version 1.0
 */
public class Recursion {

    /**
     * Returns a boolean value representing whether the passed in character
     * sequence is a valid palindrome. A palindrome is defined as such:
     * A word, phrase, or sequence that reads the same backward as forward.
     *
     * Palindromes are recursively defined as such:
     * Case 1: An empty string or single character is considered a palindrome
     * Case 2: A string is a palindrome if and only if the first and last
     * characters are the same and the remaining string is also a palindrome
     *
     * For the purposes of this method, two characters are considered
     * 'the same' if they have the same primitive value. You do not need
     * to do any case conversion. Do NOT ignore spaces.
     *
     * This method must be computed recursively! Failure to do so will result
     * in zero credit for this method.
     *
     * @param text The sequence that will be tested
     * @return Whether the passed in word is a palindrome
     * @throws IllegalArgumentException if text is null
     */
    public static boolean isPalindrome(String text) {
        if (text == null) {
            throw new IllegalArgumentException("Text cannot be null");
        }

        return isPalindrome(text, 0, text.length() - 1);
    }

    /**
     * The isPalindrome helper method.
     *
     * @param text  the sequence that will be tested
     * @param start the starting index
     * @param end   the ending index
     * @return whether the passed in word is a palindrome
     */
    private static boolean isPalindrome(String text, int start, int end) {
        if (start <= end) {
            if (text.isEmpty() || text.length() == 1) {
                return true;
            } else if (text.charAt(start) != text.charAt(end)) {
                return false;
            } else {
                return isPalindrome(text, start + 1, end - 1);
            }
        }
        return true;
    }

    /**
     * Returns the greatest common divisor of integers x and y. The greatest
     * common divisor can be determined by the recursive function as follows:
     *
     * Case 1: gcd(x, y) = gcd(x-y, y) where x > y
     * Case 2: gcd(x, y) = gcd(x, y-x) where x < y
     * Case 3: gcd(x, y) = x = y where x == y
     * Case 4 (Edge case): gcd(x, y) = {x if y == 0 or y if x == 0}
     *
     * This method must be computed recursively! Failure to do so will result
     * in zero credit for this method.
     *
     * For the purposes of this assignment, do not worry about
     * handling negative numbers. Throw an IllegalArgumentException
     * if either x or y is negative.
     *
     * @param x The first integer
     * @param y The second integer
     * @return The greatest common divisor of x and y
     * @throws IllegalArgumentException if either x or y is negative
     */
    public static int gcd(int x, int y) {
        if (x < 0 || y < 0) {
            throw new IllegalArgumentException("X: " + x + ", Y: " + y);
        }

        if (x == 0) {
            return y;
        }
        if (y == 0) {
            return x;
        }

        if (x > y) {
            return gcd(x - y, y);
        } else if (x < y) {
            return gcd(x, y - x);
        } else if (x == y) {
            return x;
        } else {
            return 0;
        }
    }
}
