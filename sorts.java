class Sorts {
    /*
    T = O(n), O(n^2), O(n^2)
    Space O(1)
    IP true
    Adapt true
    Stab true
    Обычный учебный алгоритм
     */
    public static void BubbleSort(int[] arr) {
        boolean swapped = false;
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    swapped = true;
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
            if (swapped) {
                break;
            }
        }
    }
    /*
    T O(n), O(n^2), O(n^2)
    Space O(1)
    IP true
    Adapt true
    Stab true
    Почти отсортированные массивы и небольшие массивы
     */
    public static void InsertionSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            for (int j = i; j > 0; j--) {
                if (arr[j] < arr[j - 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j - 1];
                    arr[j - 1] = temp;
                }
                else {
                    break;
                }
            }
        }
    }
    /*
    T O(nlogn)
    Space O(1)
    IP true
    Adapt false
    Stab false
    Почти отсортированный массив или найти k наименьших или наибольших элементов
     */
    //public static void HeapSort(int[] arr) {}
    /*
    T O(n^2)
    Space O(1)
    IP true
    Adapt false
    Stab false or true
    Учебная сортировка
     */
    public static void SelectionSort(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            int maxindex = 0;
            for (int j = 0; j < arr.length - i; j++) {
                if (arr[maxindex] <= arr[j]) {
                    maxindex = j;
                }
            }
            int temp = arr[arr.length - 1 - i];
            arr[arr.length - 1 - i] = arr[maxindex];
            arr[maxindex] = temp;
        }
    }
    /*
    T O(n + (max-min))
    Space O(max-min)
    IP false
    Adapt false
    Stab false
    Применяется для работы с большими массивами данных с повторяющимися числами
     */
    public static void CountingSort(int[] arr) {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int elem : arr) {
            if (elem < min) min = elem;
            if (elem > max) max = elem;
        }
        int[] vals = new int[max - min + 1];
        for (int elem : arr) vals[elem - min]++;
        int j = 0;
        for (int i = 0; i < vals.length; i++) {
            while (vals[i] > 0) {
                arr[j++] = min + i;
                vals[i]--;
            }
        }
    }
    /*
    T O(nk)
    Space O(n + k)
    устойчива
    неадапт
    Применяется для сортировки не только чисел.
    Эффективен тогда, когда числа имеют примерно одинаковое кол-во разрядов.
     */
    public static void RadixSort(int[] arr) {
        int maxbin = 0;
        List<List<Integer>> bins = new ArrayList<>();
        for (int i = 0; i < 10; ++i) {
            bins.add(new ArrayList<>());
        }
        for (int elem : arr) {
            int elembin = Integer.toString(elem).length();
            if (elembin > maxbin) maxbin = elembin;
        }
        for (int i = 0; i < maxbin; i++) {
            for (int value : arr) {
                int bin = (int) (value / Math.pow(10, i)) % 10;
                bins.get(bin).add(value);
            }
            int k = 0;
            for (List<Integer> queue : bins) {
                for (int num : queue) {
                    arr[k] = num;
                    ++k;
                }
            }
            for (int l = 0; l < 10; ++l) {
                bins.set(l, new ArrayList<>());
            }
        }
    }
}