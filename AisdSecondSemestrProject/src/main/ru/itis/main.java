package main.ru.itis;

import java.util.concurrent.ThreadLocalRandom;

public class main {
    public static void main(String[] args) {
        FibonacciHeap heap = new FibonacciHeap();
        int[] data = new int[10000];

        for (int i = 0; i < data.length; i++) {
            data[i] = ThreadLocalRandom.current().nextInt(0,100000);
        }

        for (int num : data) {
            heap.add(num);
        }

        for (int i = 0; i < 100; i ++) {
            int index = ThreadLocalRandom.current().nextInt(0, data.length);
            heap.contains(data[index]);
        }

        for (int i = 0; i < 1000; i++) {
            heap.extractMin();
        }
    }
}
