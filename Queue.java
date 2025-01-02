package ru.dan.aisd;

public class Queue {
	int[] arr;
	private int cap;
	private int numElems;
	
	public Queue() {
		arr = new int[1];
		cap = 1;
		numElems = 0;
	}
	
	public void enqueue(int a) {
		if (numElems == cap) {
			int[] newarr = new int[cap * 2];
			for (int i = 0; i < cap; ++i) {
				newarr[i] = arr[i];
			}
			cap *= 2;
			arr = newarr;
			arr[numElems] = a;
			++numElems;
		}
		else {
			arr[numElems] = a;
			++numElems;
		}
	}
	
	public void print() {
		System.out.println(numElems + " " + cap);
		for (int i = 0; i < numElems; ++i) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}
	
	public int dequeue() {
		int toReturn = -100111;
		if (numElems != 0) {
			toReturn = arr[0];
			for (int i = 0; i < numElems - 1; ++i) {
				arr[i] = arr[i + 1];
			}
			--numElems;
		}
		if (numElems == cap / 2 && cap != 1) {
			int[] newarr = new int[cap / 2];
			for (int i = 0; i < cap / 2; ++i) {
				newarr[i] = arr[i];
			}
			cap /= 2;
			arr = newarr;
		}
		return toReturn;
	}
	
	public int top() {
		return arr[0];
	}
}
/*
int n = 20;                                              // Выводим n первых чисел
Queue q2 = new Queue();                                  // Наши очереди со степенями 2, 3, 5 соответственно
Queue q3 = new Queue();
Queue q5 = new Queue();

// Заполняем очереди степенями
q2.enqueue(2);
q3.enqueue(3);
q5.enqueue(5);

System.out.print(1 + " ");
for (int i = 1; i < n; ++i) {    						 // Печатаем наши элементы в порядке возрастания
	int next = min(min(q2.top(), q3.top()), q5.top());
	System.out.print(next + " ");
	
	if (next == q2.top()) {
		q2.dequeue();
		q2.enqueue(next * 2);
		q3.enqueue(next * 3);
		q5.enqueue(next * 5);
	}
	else if (next == q3.top()) {
		q3.dequeue();
		q3.enqueue(next * 3);
		q5.enqueue(next * 5);
	}
	else if (next == q5.top()) {
		q5.dequeue();
		q5.enqueue(next * 5);
	}
}
*/

