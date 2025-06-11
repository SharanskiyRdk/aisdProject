package Lesson6;

import java.util.NoSuchElementException;
import java.util.Stack;

public class Queue1<I> {
    Stack<Integer> stack1;
    Stack<Integer> stack2;

    public Queue1(){
        stack1 = new Stack<>();
        stack2 = new Stack<>();
    }

    public void add(int x) {
        stack1.push(x);
    }

    public int remove(){
        if (!stack2.isEmpty()) {
            return stack2.pop();
        } else if(!stack1.isEmpty()) {
            reverse();
            return stack2.pop();
        } else {
            throw new NullPointerException();
        }
    }

    private void reverse(){
        while (!stack1.isEmpty()) {
            stack2.push(stack1.pop());
        }
    }

    public boolean isEmpty() {
        return stack1.isEmpty() && stack2.isEmpty();
    }

    public int peek() {
        if (!stack2.isEmpty()) {
            return stack2.peek();
        } else if (!stack1.isEmpty()) {
            reverse();
            return stack2.peek();
        } else {
            throw new NoSuchElementException("Очередь пуста");
        }
    }
}
