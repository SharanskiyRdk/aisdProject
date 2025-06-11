package Lesson4;

import Lesson4.Stack;

import java.util.EmptyStackException;

public class minStack {

    private Stack stack1;
    private Stack stack2;

    public minStack(int size) {
        stack1 = new Stack(size);
        stack2 = new Stack(size);
    }

    public boolean push(int x) {
        if (!stack1.push(x)) {
            return false;
        }

        if (stack2.isEmpty() || x <= stack2.peek()) {
            stack2.push(x);
        } else {
            stack2.push(stack2.peek());
        }
        return true;
    }

    public int pop() {
        if (stack1.isEmpty()) {
            throw new EmptyStackException();
        }
        stack2.pop();
        return stack1.pop();
    }

    public int getMin() {
        if (stack2.isEmpty()) {
            throw new EmptyStackException();
        }
        return stack2.peek();
    }

    @Override
    public String toString() {
        return "MinStack{" +
                "stack1=" + stack1 +
                ", stack2=" + stack2 +
                '}';
    }
}
