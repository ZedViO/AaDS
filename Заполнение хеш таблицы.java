package aisdthread2;

public class Main {
	public static void main(String[] args)
	{
		int[] arr = {22, 1, 13, 11, 24, 33, 18, 42, 31};
		int m = 11;
		int[] res = new int[m];
		
		for (int i = 0; i < m; ++i) {
			res[i] = Integer.MIN_VALUE; 
		}
		
		for (int x : arr) {
			int h1 = x % m;
			int h2 = (x % (m - 1)) + 1;
			
			boolean isAdded = false;
			for (int i = 0; !isAdded; ++i) {
				int hash = (h1 + i * h2) % m;
				if (res[hash] == Integer.MIN_VALUE) {
					res[hash] = x;
					isAdded = true;
				}
			}
		}
		for (int i = 0; i < res.length; ++i) {
			System.out.println(i + " : " + (res[i] == Integer.MIN_VALUE ? "Empty" : res[i]));
		}
	}
}