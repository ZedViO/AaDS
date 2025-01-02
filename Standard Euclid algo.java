Scanner sc = new Scanner(System.in);

int a = sc.nextInt();
int b = sc.nextInt();
sc.close();

while (a != 0 && b != 0) {
	if (a >= b) {
		a = a % b;
		System.out.println(a + " " + b);
	}
	else {
		b = b % a;
		System.out.println(a + " " + b);
	}
}
System.out.println((a > b) ? a : b);