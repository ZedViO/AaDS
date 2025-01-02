public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		int a = sc.nextInt();
		int b = sc.nextInt();
		sc.close();
		
		aer(a, b);
	}
	
	static void aer(int a, int b) {
		if (a != 0 && b != 0) {
			if (a >= b) {
				a = a % b;
			}
			else {
				b = b % a;
			}
			aer(a, b);
		}
		else {
			System.out.println((a > b) ? a : b);
		}
	}