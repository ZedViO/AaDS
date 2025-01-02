package ru.dan.aisd;

public class Stack {
	private int[] arr;
	private int cap;
	private int numElems;
	
	public Stack() {
		arr = new int[1];
		cap = 1;
		numElems = 0;
	}
	
	public void push(int a) {
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
		for (int i = numElems - 1; i >= 0; --i) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}
	
	public int pop() {
		--numElems;
		if (numElems < 0) {
			numElems = 0;
			return -100111;
		}
		int toReturn = arr[numElems];
		
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
}
