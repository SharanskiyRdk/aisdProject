package main.ru.itis;

import java.util.*;

public class FibonacciHeap {
    private static int operationsCount = 0;

    private static class Node {
        int key;
        int degree = 0;
        boolean marked = false;
        Node parent, child, left, right;

        public Node(int key) {
            this.key = key;
            this.left = this;
            this.right = this;
            operationsCount += 3;
        }
    }

    private Node minNode;
    private int size = 0;

    public void add(int key){
        long startTime = System.nanoTime();
        operationsCount = 0;

        Node node = new Node(key);
        operationsCount += 1;

        if (minNode != null) {
            node.left = minNode;
            node.right = minNode.right;
            minNode.right = node;
            node.right.left = node;
            operationsCount += 4;

            if (key < minNode.key) {
                minNode = node;
                operationsCount += 1;
            }
        } else {
            minNode = node;
            operationsCount += 1;
        }

        size++;
        operationsCount += 1;

        long endTime = System.nanoTime();
        System.out.printf("Add: %d, operations: %d, Time: %d ns\n", key, operationsCount, endTime-startTime);
    }

    public boolean contains(int key) {
        long startTime = System.nanoTime();
        operationsCount = 0;

        if (minNode == null) {
            operationsCount += 1;
            long endTime = System.nanoTime();
            System.out.printf("Search: %d not found? operations: %d, time: %d ns\n", key, operationsCount, endTime-startTime);
            return false;
        }

        Stack<Node> stack = new Stack<>();
        stack.push(minNode);
        operationsCount += 2;

        while (!stack.isEmpty()) {
            Node curr = stack.pop();
            operationsCount += 1;

            if(curr.key == key) {
                operationsCount += 1;
                long endTime = System.nanoTime();
                System.out.printf("Search: %d found, Operations: %d, Time: %d ns\n", key, operationsCount, endTime - startTime);
                return true;
            }

            if (curr.child != null) {
                stack.push(curr.child);
                operationsCount += 1;
            }

            Node start = curr;
            curr = curr.right;
            operationsCount += 2;

            while (curr != start) {
                if (curr.key == key) {
                    operationsCount += 1;
                    long endTime = System.nanoTime();
                    System.out.printf("Search: %d found, Operations: %d, Time: %d ns\n", key, operationsCount, endTime - startTime);
                    return true;
                }

                if (curr.child != null) {
                    stack.push(curr.child);
                    operationsCount += 1;
                }

                curr = curr.right;
                operationsCount += 1;
            }
            operationsCount += 1;
        }

        long endTime = System.nanoTime();
        System.out.printf("Search: %d not found, Operations: %d, Time: %d ns\n", key, operationsCount, endTime - startTime);
        return false;
    }

    public Integer extractMin() {
        long startTime = System.nanoTime();
        operationsCount = 0;

        if (minNode == null) {
            operationsCount += 1;
            long endTime = System.nanoTime();
            System.out.printf("ExtractMin: heap empty, Operations: %d, Time: %d ns\n", operationsCount, endTime - startTime);
            return null;
        }

        Node z = minNode;
        operationsCount += 1;

        if (z.child != null) {
            Node x = z.child;
            do {
                x.parent = null;
                x = x.right;
                operationsCount += 2;
            } while (x != z.child);
                operationsCount += 1;

                Node minLeft = minNode.left;
                Node childLeft = z.child.left;
                operationsCount += 2;

                minLeft.right = z.child;
                z.child.left = minLeft;
                childLeft.right = minNode;
                minNode.left = childLeft;
                operationsCount += 4;
        }

        z.left.right = z.right;
        z.right.left = z.left;
        operationsCount += 2;

        if (z == z.right) {
            minNode = null;
            operationsCount += 1;
        } else {
            minNode = z.right;
            consolidate();
            operationsCount += 1;
        }

        size--;
        operationsCount += 1;

        long endTime = System.nanoTime();
        System.out.printf("ExtractMin: %d, Operations: %d, Time: %d ns\n", z.key, operationsCount, endTime - startTime);
        return z.key;
    }

    private void consolidate() {
        int maxDegree = (int) Math.ceil(Math.log(size) / Math.log(2)) + 1;
        Node[] degreeArray = new Node[maxDegree];
        operationsCount += 2;

        Node start = minNode;
        Node current = minNode;
        int numRoots = 0;
        operationsCount += 3;

        if (current != null) {
            numRoots++;
            current = current.right;
            operationsCount += 2;

            while(current != start) {
                numRoots++;
                current = current.right;
                operationsCount += 2;
            }
        }
        operationsCount += 1;

        while (numRoots > 0) {
            int degree = current.degree;
            Node next = current.right;
            operationsCount += 2;

            while (degreeArray[degree] != null) {
                Node other = degreeArray[degree];
                operationsCount += 1;

                if (current.key > other.key) {
                    Node temp = current;
                    current = other;
                    other = temp;
                    operationsCount += 3;
                }

                link(other, current);
                degreeArray[degree] = null;
                degree++;
                operationsCount += 2;
            }
            operationsCount += 1;

            degreeArray[degree] = current;
            current = next;
            numRoots--;
            operationsCount += 3;
        }
        operationsCount += 1;
        minNode = null;
        operationsCount += 1;

        for (Node node : degreeArray) {
            if (node != null) {
                operationsCount += 1;
                if (minNode == null) {
                    minNode = node;
                    operationsCount += 1;
                } else {
                    node.left.right = node.right;
                    node.right.left = node.left;
                    operationsCount += 2;

                    node.left = minNode;
                    node.right = minNode.right;
                    minNode.right = node;
                    node.right.left = node;
                    operationsCount += 4;

                    if (node.key < minNode.key) {
                        minNode = node;
                        operationsCount += 1;
                    }
                }
            }
        }
    }

    private void link(Node child, Node parent) {
        child.left.right = child.right;
        child.right.left = child.left;
        operationsCount += 2;

        child.parent = parent;
        operationsCount += 1;

        if (parent.child == null) {
            parent.child = child;
            child.right = child;
            child.left = child;
            operationsCount += 3;
        } else {
            child.left = parent.child;
            child.right = parent.child.right;
            parent.child.right = child;
            child.right.left = child;
            operationsCount += 4;
        }
        parent.degree++;
        operationsCount += 1;
    }
}