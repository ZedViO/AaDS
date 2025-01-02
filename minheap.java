import java.io.PrintStream;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.round;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Queue<String> commands = new LinkedList<>();
        MinHeap mh = new MinHeap();
        PrintStream out = new PrintStream(System.out);

        String line;
        while (sc.hasNextLine()) {
            line = sc.nextLine();
            if (line.isEmpty()) {
                continue;
            }
            commands.add(line);
        }
        sc.close();

        while (!commands.isEmpty()) {
            try {
                String command = commands.poll();
                if (command.equals("min")) {
                    System.out.println(mh.get(mh.min()).key + " 0 " + mh.get(mh.min()).value);
                }
                else if (command.equals("max")) {
                    System.out.println(mh.get(mh.max()).key + " " + mh.max() + " " + mh.get(mh.max()).value);
                }
                else if (command.equals("print")) {
                    mh.print(out);
                }
                else if (command.equals("extract")) {
                    Pair extract = mh.extract();
                    System.out.println(extract.key + " " + extract.value);
                }
                else if (command.matches("^(search|delete) -?\\d+$")) {
                    String[] parts = command.split(" ");
                    String action = parts[0];
                    switch (action) {
                        case "search":
                            long key = Long.parseLong(parts[1]);
                            System.out.println((mh.search(key) != -1) ? "1 " + mh.search(key) + " " + mh.get(mh.search(key)).value : "0");
                            break;
                        case "delete":
                            mh.delete(Long.parseLong(parts[1]));
                            break;
                    }
                }
                else if (command.matches("^(set|add) -?\\d+ \\S+$")) {
                    String[] parts = command.split(" ");
                    String action = parts[0];
                    switch (action) {
                        case "set":
                            mh.set(Long.parseLong(parts[1]), parts[2]);
                            break;
                        case "add":
                            mh.add(Long.parseLong(parts[1]), parts[2]);
                            break;
                    }
                }
                else {
                    System.out.println("error");
                }
            }
            catch (Exception e) {
                System.out.print(e.getMessage());
            }
        }
    }
}
class MinHeap {
    private int size = 0;
    private Pair[] heap = new Pair[0];
    private final Map<Long, Integer> key_index = new HashMap<>();

    public Pair get(int index) {
        return heap[index];
    }
    public void add(long key, String value) {
        if (key_index.containsKey(key)) {
            throw new RuntimeException("error\n");
        }
        if (size == heap.length) {
            heap = Arrays.copyOf(heap, size * 2 + 1);
        }
        ++size;
        Pair newPair = new Pair();
        newPair.key = key;
        newPair.value = value;
        heap[size - 1] = newPair;
        int i = size - 1;
        int parent = (int) round(i / 2.0) - 1;
        key_index.put(key, i);
        while (i > 0 && heap[parent].key > key) {
            swapelems(parent, i);
            i = parent;
            parent = (int) round(i / 2.0) - 1;
        }
    }
    public void print(PrintStream out) {
        out.append((size == 0) ? "_" : "[" + heap[0].key + " " + heap[0].value + "]");
        int printedelems = 1;
        for (int i = 1; i < heap.length; i++) {
            if (i == printedelems) {
                if (i >= size) {
                    break;
                }
                System.out.println();
                printedelems = i * 2 + 1;
            }
            out.append((i < size) ? "[" + heap[i].key + " " + heap[i].value + " " + heap[(i - 1) / 2].key + "] " : "_ ");
        }
        out.append("\n");
    }
    public int min() {
        if (size == 0) {
            throw new RuntimeException("error\n");
        }
        return 0;
    }
    public int max() {
        if (size == 0) {
            throw new RuntimeException("error\n");
        }
        long max = heap[0].key;
        int index = 0;
        for (int i = size - 1; i * 2 + 1 >= size; i--) {
            if (max < heap[i].key) {
                max = heap[i].key;
                index = i;
            }
        }
        return index;
    }
    public void set(long key, String value) {
        if (key_index.containsKey(key)) {
            heap[key_index.get(key)].value = value;
        }
        else {
            throw new RuntimeException("error\n");
        }
    }
    public int search(long key) {
        if (key_index.containsKey(key)) {
            return key_index.get(key);
        }
        return -1;
    }
    public void heapify(int i) {
        int parent = (i - 1) / 2;
        if (i == 0 || heap[parent].key < heap[i].key) {
            heapifydown(i);
        }
        if (heap[i].key < heap[parent].key) {
            heapifyup(i);
        }
    }
    public void heapifyup(int i) {
        int parent = (i - 1) / 2;
        while (i > 0 && heap[parent].key > heap[i].key) {
            swapelems(parent, i);
            i = parent;
            parent = (i - 1) / 2;
        }
    }
    public void heapifydown(int i) {
        int smallest = i;
        int lch = 2 * i + 1;
        int rch = 2 * i + 2;
        if (lch < size && rch < size) {
            if (heap[lch].key < heap[smallest].key || heap[rch].key < heap[smallest].key) {
                Pair minch = (heap[lch].key < heap[rch].key) ? heap[lch] : heap[rch];
                smallest = key_index.get(minch.key);
            }
        }
        else if (lch < size) {
            if (heap[lch].key < heap[smallest].key) {
                smallest = lch;
            }
        }
        if (smallest != i) {
            swapelems(i, smallest);
            heapifydown(smallest);
        }
    }
    public void swapelems(int i, int j) {
        Pair temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
        key_index.replace(heap[i].key, i);
        key_index.replace(heap[j].key, j);
    }
    public Pair extract() {
        if (size == 0) throw new RuntimeException("error\n");
        Pair pair = heap[0];
        delete(heap[0].key);
        return pair;
    }
    public void delete(long key) {
        if (key_index.containsKey(key)) {
            int remindex = key_index.get(key);
            heap[remindex] = heap[size - 1];
            key_index.remove(key);
            if (!key_index.isEmpty()) {
                key_index.replace(heap[size - 1].key, remindex);
            }
            --size;
            heapify(remindex);
        }
        else {
            throw new RuntimeException("error\n");
        }
    }
}
class Pair {
    long key = 0;
    String value = null;
}