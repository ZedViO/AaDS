public static boolean inAllArrs(int search, Queue<int[]> q) {
        int k = q.size();
        if (q.isEmpty()) return false;
        HashMap<Integer, Integer> elemsAppearing = new HashMap<>(); //Наша хеш-таблица
        for (int elem : q.poll()) {                                 //Заполняем ее счетчиками
            if (!elemsAppearing.containsKey(elem)) {
                elemsAppearing.put(elem, 1);
            }
        }
        if (elemsAppearing.get(search) == null) return false;
        while (!q.isEmpty()) {                                      //Пройдемся по остальным k-1 массивам
            HashSet<Integer> curr = new HashSet<>();
            for (int elem : q.poll()) {                             //Поэлементно
                if (elemsAppearing.containsKey(elem)) {             //Если элемент есть в массиве
                    curr.add(elem);
                }
            }
            for (int elem : curr) {
                if (elemsAppearing.containsKey(elem)) {
                    elemsAppearing.replace(elem, elemsAppearing.get(elem) + 1); //Инкрементируем счетчик
                }
            }
        }
        return elemsAppearing.get(search) == k;
    }
}