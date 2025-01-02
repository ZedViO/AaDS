import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {

  public static void main(String [] args) {
    Scanner sc = new Scanner(System.in);
    List<String> commands = new ArrayList<String>();
    Deque deq = null;
    
    String line = "";
    while (true) {
      try {
        line = sc.nextLine();
        if (line == "") {
          continue;
        }
      }
      catch (NoSuchElementException e) {
        break;
      }
      commands.add(line);
    }
    sc.close();
    
    for (String command : commands) {
      try {
        if (deq == null && command.matches("^set_size \\S+$")) {
        	deq = new Deque(Integer.parseInt(command.split(" ")[1]));
        }
        else if (command.equals("print")) {
          deq.print();
        }
        else if (command.equals("popb")) {
          deq.popb();
        }
        else if (command.equals("popf")) {
          deq.popf();
        }
        else if (command.matches("^pushb \\S+$")) {
          deq.pushb(command.split(" ")[1]);
        }
        else if (command.matches("^pushf \\S+$")) {
          deq.pushf(command.split(" ")[1]);
        }
        else {
          System.out.println("error");
        }
      }
      catch (Exception e) {
        System.out.println("error");
      }
    }
  }
}

class Deque {
  private String[] arr;
  private int maxlen;
  private int size;
  private int front;
  private int back;
  
  public Deque(int maxlen) {
    this.maxlen = maxlen;
    arr = new String[this.maxlen];
    size = 0;
    front = 0;
    back = -1;
  }
  public void print() {
    if (size == 0) {
      System.out.println("empty");
      return;
    }
    for (int i = front; i != back; ++i) {
      if (i == arr.length) {
        i = -1;
        continue;
      }
      System.out.printf("%s ", arr[i]);
    }
    System.out.println(arr[back]);
  }
  
  public void pushf(String x) {
    if (size + 1 <= maxlen) {
      if (front > 0) {
        --front;
      }
      else {
        front = arr.length - 1;
      }
      
      arr[front] = x;
      ++size;
      
      if (size == 1) {
        back = front;
      }
    }
    else {
      System.out.println("overflow");
    }
  }
  
  public void pushb(String x) {
    if (size + 1 <= maxlen) {
      if (back == arr.length - 1) {
            back = 0;
        }
        else {
          ++back;
        }

        arr[back] = x;
        ++size;

        if (size == 1) {
            front = back;
        }
    }
    else {
      System.out.println("overflow");
    }
  }
  
  public void popf() {
    if (size != 0) {
      System.out.println(arr[front]);
      if (front == arr.length - 1) {
            front = 0;
        }
        else {
            ++front;
        }
      --size;
    }
    else {
      System.out.println("underflow");
    }
  }
  
  public void popb () {
    if (size != 0) {
      System.out.println(arr[back]);
      if (back == 0) {
            back = arr.length - 1;
        }
      else {
            --back;
      }
        --size;
    }
    else {
      System.out.println("underflow");
    }
  }
}