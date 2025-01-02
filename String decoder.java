package aisdthread2;

import java.util.Scanner;
import java.util.Stack;

public class Main {
	public static void main(String[] args) 
	{
		Scanner sc = new Scanner(System.in);
		char[] encoded = sc.nextLine().toCharArray();
		sc.close();
		
		Stack<String> stackString = new Stack<String>();
		Stack<Integer> stackInt = new Stack<Integer>();
		String decoded = "";
		String k = "";
		
		for (char l : encoded) {
			if ("1234567890".contains(Character.toString(l))) 
			{
				k += l;
			}
			else if (l == '[')
			{
				stackString.push(decoded);
				stackInt.push(Integer.parseInt(k));
				decoded = "";
				k = "";
			}
			else if (l == ']') 
			{
				String buff = decoded;
				decoded = stackString.pop();
				for (int i = 0; i < stackInt.peek(); ++i )
				{
					decoded += buff;
				}
				stackInt.pop();
			}
			else 
			{
				decoded += l;
			}
		}
		System.out.println(decoded);
	}
}