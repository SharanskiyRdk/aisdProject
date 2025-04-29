import java.io.*;
import java.util.*;

public class ALP_ALGORITHM {
    static class Edge {
        int to;
        int length;

        public Edge(int to, int length) {
            this.to = to;
            this.length = length;
        }
    }

    private static final Random random = new Random();
    private static final int INFINITY = Integer.MAX_VALUE;
    private static final String GRAPHS_FOLDER = "D://graphs/";

    public static void main(String[] args) {

        File folder = new File(GRAPHS_FOLDER);
        File[] files = folder.listFiles();
        if (files == null || files.length == 0) {
            System.err.println("Нет файлов с графами для тестирования!");
            return;
        }

        for (File file : files) {
            System.out.println("\nОбработка файла: " + file.getName());
            try {
                processGraphFile(file);
            } catch (IOException e) {
                System.err.println("Ошибка при обработке файла " + file.getName() + ": " + e.getMessage());
            }
        }
    }

    private static void processGraphFile(File file) throws IOException {
        try (Scanner scanner = new Scanner(file)) {
            int numbersOfVertices = scanner.nextInt();
            int edgesCount = scanner.nextInt();

            List<List<Edge>> graph = new ArrayList<>();
            for (int i = 0; i < numbersOfVertices; i++) {
                graph.add(new ArrayList<>());
            }

            for (int i = 0; i < edgesCount; i++) {
                int from = scanner.nextInt();
                int to = scanner.nextInt();
                int length = scanner.nextInt();
                graph.get(from).add(new Edge(to, length));
            }

            Random random = new Random();
            int start = random.nextInt(numbersOfVertices);
            int finish = random.nextInt(numbersOfVertices);

            if (numbersOfVertices <= 0 || start < 0 || start >= numbersOfVertices ||
                    finish < 0 || finish >= numbersOfVertices) {
                throw new IllegalArgumentException("Некорректные параметры графа.");
            }

            int[] d = new int[numbersOfVertices];
            Arrays.fill(d, INFINITY);
            d[start] = 0;

            int[] state = new int[numbersOfVertices];
            Arrays.fill(state, 2);
            state[start] = 1;

            Deque<Integer> q = new ArrayDeque<>();
            q.addLast(start);

            int[] p = new int[numbersOfVertices];
            Arrays.fill(p, -1);

            long startTime = System.nanoTime(); // Точное время в наносекундах
            int iterations = 0; // Счётчик итераций

            while (!q.isEmpty()) {
                iterations++; // Увеличиваем счётчик итераций
                int vertex = q.pollFirst();
                state[vertex] = 0;

                for (Edge edge : graph.get(vertex)) {
                    int to = edge.to;
                    int length = edge.length;

                    if (d[vertex] != INFINITY && d[to] > d[vertex] + length) {
                        d[to] = d[vertex] + length;
                        if (state[to] == 2) {
                            q.addLast(to);
                        } else if (state[to] == 0) {
                            q.addFirst(to);
                        }
                        p[to] = vertex;
                        state[to] = 1;
                    }
                }
            }

            long endTime = System.nanoTime();
            long durationNs = endTime - startTime;
            double durationMs = durationNs / 1_000_000.0; // Переводим в миллисекунды
            // --- Конец измерений ---

            // Вывод результатов
            System.out.println("Граф: " + numbersOfVertices + " вершин, " + edgesCount + " рёбер");
            System.out.println("Старт: " + start + ", Финиш: " + finish);
            System.out.println("Время работы алгоритма: " + durationMs + " мс");
            System.out.println("Количество итераций: " + iterations);


            if (p[finish] == -1) {
                System.out.println("Путь не найден");
            } else {
                List<Integer> path = new ArrayList<>();
                for (int vertex = finish; vertex != -1; vertex = p[vertex]) {
                    path.add(vertex);
                }
                Collections.reverse(path);
                System.out.print("Кратчайший путь (длина " + d[finish] + "): ");
                for (int vertex : path) {
                    System.out.print(vertex + " ");
                }
                System.out.println();
            }
        }
    }
}