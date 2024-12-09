package org.example.hm8;

import lombok.Getter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Теория<br/>
 * <a href="http://e-maxx.ru/algo/treap">http://e-maxx.ru/algo/treap</a><br/>
 * <a href="https://www.geeksforgeeks.org/treap-a-randomized-binary-search-tree/">https://www.geeksforgeeks.org/treap-a-randomized-binary-search-tree/</a><br/>
 * <a href="https://www.geeksforgeeks.org/implementation-of-search-insert-and-delete-in-treap/">https://www.geeksforgeeks.org/implementation-of-search-insert-and-delete-in-treap/</a><br/>
 * <a href="http://faculty.washington.edu/aragon/pubs/rst89.pdf">http://faculty.washington.edu/aragon/pubs/rst89.pdf</a><br/>
 * <a href="https://habr.com/ru/articles/101818/">https://habr.com/ru/articles/101818/</a><br/>
 * Примеение в linux kernel<br/>
 * <a href="https://www.kernel.org/doc/mirror/ols2005v2.pdf">https://www.kernel.org/doc/mirror/ols2005v2.pdf</a>
 */
public class Treap<T extends Comparable<T>> {

    Node<T> root;

    public Treap() {
    }


    public void add(T key) {
        root = insert(root, key);
    }


    public void remove(T key) {
        root = deleteNode(root, key);
    }

    private Node<T> deleteNode(Node<T> cur, T key) {
        if (cur == null)
            return cur;

        if (key.compareTo(cur.key) < 0)
            cur.left = deleteNode(cur.left, key);
        else if (key.compareTo(cur.key) > 0)
            cur.right = deleteNode(cur.right, key);

            // IF KEY IS AT ROOT

            // If left is NULL
        else if (cur.left == null) {
            Node<T> temp = cur.right;
            cur = temp;  // Make right child as root
        }
        // If Right is NULL
        else if (cur.right == null) {
            Node<T> temp = cur.left;
            cur = temp;  // Make left child as root
        }
        // If key is at root and both left and right are not NULL
        else if (cur.left.priority < cur.right.priority) {
            cur = leftRotation(cur);
            cur.left = deleteNode(cur.left, key);
        } else {
            cur = rightRotation(cur);
            cur.right = deleteNode(cur.right, key);
        }

        return cur;
    }


    private Node<T> searchNode(Node<T> cur, T key) {
        if (cur == null || key.compareTo(cur.key) == 0) {
            return cur;
        }
        if (key.compareTo(cur.key) > 0) {
            return searchNode(cur.right, key);
        }
        return searchNode(cur.left, key);
    }


    private Node<T> insert(Node<T> cur, T key) {
        if (cur == null) {
            return new Node<>(key);
        }
        if (key.compareTo(cur.key) > 0) {
            cur.right = insert(cur.right, key);
            if (cur.right.priority < cur.priority) {
                cur = leftRotation(cur);
            }

        } else {
            cur.left = insert(cur.left, key);
            if (cur.left.priority < cur.priority) {
                cur = rightRotation(cur);
            }

        }
        return cur;
    }

    /* T1, T2 and T3 are subtrees of the tree rooted with y
  (on left side) or x (on right side)
                y                               x
               / \     Right Rotation          /  \
              x   T3   – – – – – – – >        T1   y
             / \       < - - - - - - -            / \
            T1  T2     Left Rotation            T2  T3 */

    private Node<T> leftRotation(Node<T> x) {
        Node<T> y = x.right;
        Node<T> T2 = y.left;

        y.left = x;
        x.right = T2;

        return y;
    }

    private Node<T> rightRotation(Node<T> y) {
        Node<T> x = y.left;
        Node<T> T2 = x.right;

        x.right = y;
        y.left = T2;

        return x;
    }

    public T next(T key, T defaultIfNull) {
        Node<T> moreKeys = split(key)[1];
        Node<T> minValue = getMinValue(moreKeys);
        return minValue == null ? defaultIfNull : minValue.key;
    }

    private Node<T> getMinValue(Node<T> node) {
        while (node != null && node.left != null) {
            node = node.left;
        }
        return node;
    }

    private Node<T>[] split(T key) {
        return root.split(key);
    }

    public Double getProfit(Integer amount, T price) {
        Node<T>[] node = split(price);
        Node<T> suitableCustomers = node[1];
        int size = inorder(suitableCustomers).size();
        return Math.min(size * 0.01, amount * 0.01);
    }

    public List<Node<T>> inorder(Node<T> cur) {
        List<Node<T>> res = new ArrayList<>();
        inorder(cur, res);
        return res;
    }


    private void inorder(Node<T> cur, List<Node<T>> res) {
        if (cur == null) {
            return;
        }
        inorder(cur.left, res);
        res.add(cur);
        inorder(cur.right, res);
    }


    @Getter
    public static class Node<T extends Comparable<T>> {
        static Random RND = new Random();
        T key;
        int priority;
        Node<T> left;
        Node<T> right;

        public Node(T key) {
            this(key, RND.nextInt(10));
        }

        public Node(T key, int priority) {
            this(key, priority, null, null);
        }

        public Node(T key, int priority, Node<T> left, Node<T> right) {
            this.key = key;
            this.priority = priority;
            this.left = left;
            this.right = right;
        }

        @Override
        public String toString() {
            return String.format("(%f,%d,%d)", key, priority);
        }

        public Node<T>[] split(T key) {
            Node<T> tmp = null;

            Node<T>[] res = (Node<T>[]) Array.newInstance(this.getClass(), 2);

            if (this.key.compareTo(key) < 0) {
                if (this.right == null) {
                    res[1] = null;
                } else {
                    Node<T>[] rightSplit = this.right.split(key);
                    res[1] = rightSplit[1];
                    tmp = rightSplit[0];
                }
                res[0] = new Node<>(this.key, priority, this.left, tmp);
            } else {
                if (left == null) {
                    res[0] = null;
                } else {
                    Node<T>[] leftSplit = this.left.split(key);
                    res[0] = leftSplit[0];
                    tmp = leftSplit[1];
                }
                res[1] = new Node<>(this.key, priority, tmp, this.right);
            }
            return res;
        }
    }
}
