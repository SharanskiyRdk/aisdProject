package Lesson6;

public class Main {
    public static void main(String[] args) {
        Queue1<Integer> queue = new Queue1<>();

        queue.add(1);
        queue.add(2);
        queue.add(3);

        System.out.println(queue.remove());
        System.out.println(queue.peek());

        queue.add(4);

        while (!queue.isEmpty()) {
            System.out.println(queue.remove());
        }
    }
}
