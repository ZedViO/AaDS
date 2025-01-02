import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

	public static void main(String [] args) {
		Scanner sc = new Scanner(System.in);
		String[] array = Arrays.stream(sc.nextLine().trim().split(" ")).filter(s -> !s.isEmpty()).toArray(String[]::new);
		
		List<Integer> commands = new ArrayList<Integer>();
		while (sc.hasNextLine()) {
			String command = sc.nextLine();
			commands.add(Integer.parseInt(command.split(" ")[1]));
		}
		sc.close();
		for (int elem : commands) {
			System.out.println(BinarySearch(array, elem, 0, array.length - 1));
		}
	}

	public static int BinarySearch(String[] arr, int val, int lowerbound, int upperbound) {
	    if (arr.length == 0) {
	        return -1;
	    }
	    
	    while (lowerbound <= upperbound) {
	        int index = (lowerbound + upperbound) / 2;
	        int elem = Integer.parseInt(arr[index]);
	        if (elem < val) {
	            lowerbound = index + 1;
	            return BinarySearch(arr, val, lowerbound, upperbound);
	        } 
	        else if (elem > val) {
	            upperbound = index - 1;
	            return BinarySearch(arr, val, lowerbound, upperbound);
	        } 
	        else {
	            if (index == 0 || Integer.parseInt(arr[index - 1]) != val) {
	                return index;
	            } 
	            else {
	            	return BinarySearch(arr, val, lowerbound, index - 1);
	            }
	        }
	    }
	    return -1;
	}
}