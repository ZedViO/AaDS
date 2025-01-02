import java.util.Arrays;
import static java.lang.Math.round;

public class Main {
    public static void main(String[] args) {
        SortingMinHeap smh = new SortingMinHeap();
        long[] arr = {345, 67542, 34425, -2343412, -345245, -4234, 33653, 3452, 35344536, -456546273, 1, 2, 3, 4, 5, 6,
        7, 8, 9, 0, 4125, -36347587, 2341, 1213, 54687, -6262, -11, -9785845, -1, -2 , 3, -4, -5, -6};
        arr = smh.sort(arr);
        System.out.println(Arrays.toString(arr));
    }
}
class SortingMinHeap {
    private int size = 0;
    private long[] heap = new long[0];

    private void add(long key) {
        if (size == heap.length) {
            heap = Arrays.copyOf(heap, size * 2 + 1);
        }
        ++size;
        heap[size - 1] = key;
        int i = size - 1;
        int parent = (int) round(i / 2.0) - 1;
        while (i > 0 && heap[parent] > key) {
            swapelems(parent, i);
            i = parent;
            parent = (int) round(i / 2.0) - 1;
        }
    }
    private void heapifydown(int i) {
        int smallest = i;
        int lch = 2 * i + 1;
        int rch = 2 * i + 2;
        if (lch < size && rch < size) {
            if (heap[lch] < heap[smallest] || heap[rch] < heap[smallest]) {
                long minch = Math.min(heap[lch], heap[rch]);
                if (minch == heap[lch]) smallest = lch;
                else smallest = rch;
            }
        }
        else if (lch < size) {
            if (heap[lch] < heap[smallest]) {
                smallest = lch;
            }
        }
        if (smallest != i) {
            swapelems(i, smallest);
            heapifydown(smallest);
        }
    }
    private void swapelems(int i, int j) {
        long temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }
    private long extract() {
        long del = heap[0];
        heap[0] = heap[size - 1];
        --size;
        heapifydown(0);
        return del;
    }
    public long[] sort(long[] arr) {
        long[] result = new long[arr.length];
        for (long elem : arr) {
            add(elem);
        }
        for (int i = 0; i < arr.length; ++i) {
            result[i] = extract();
        }
        return result;
    }
}