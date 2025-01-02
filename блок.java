import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        /*
        Отбор и ввод данных в программу осуществляется за О(1)
         */
        Scanner sc = new Scanner(System.in);
        String[] npbbmax = sc.nextLine().split(" ");
        int n = Integer.parseInt(npbbmax[0]);
        int p = Integer.parseInt(npbbmax[1]);
        int b = Integer.parseInt(npbbmax[2]);
        int bmax = Integer.parseInt(npbbmax[3]);
        int timenow = Integer.parseInt(npbbmax[4]);

        List<Integer> falselogons = new ArrayList<>();
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if (2 * bmax >= timenow - Integer.parseInt(line)) {
                falselogons.add(Integer.parseInt(line));
            }
        }
        sc.close();

        /*
        Алгоритм сортировки в Java (Timsort) занимает O(n*logn)
         */
        Collections.sort(falselogons);
        boolean isBlocked = false;
        int blocktime = 0;
        int blocked_since = 0;
        int count = 0;
        /*
        Проход по неудачным попыткам ввода займет O(n)
         */
        for (int i = 0; i < falselogons.size(); i++) {
            ++count;
            if (count >= n && i >= n - 1 && falselogons.get(i) - falselogons.get(i - n + 1) <= p) {
                isBlocked = true;
                blocktime = b;
                b = Math.min(b * 2, bmax);
                blocked_since = falselogons.get(i);
                count = 0;
            }
            if (falselogons.get(i) - blocked_since > blocktime) {
                isBlocked = false;
            }
        }
        System.out.println((isBlocked && blocked_since + blocktime > timenow) ? blocked_since + blocktime : "ok");
    }
}
/*
Суммарная сложность работы программы составит O(1 + n + nlogn) = O(nlogn)
По памяти всегда будет O(1)
 */