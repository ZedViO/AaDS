package ru.dan.aisd;

public class Main {

  public static void main(String [] args) {
    for (int i = 1; i <= 10; ++i) {
      System.out.println(n(i));
    }
  }
  public static long n(int h) {
    if (h <= 1) return 1;
    long sumLeft = 0;
    long sumRight = 0;
    for (int i = 0; i <= h - 1; ++i) {
      sumLeft += n(h - 1) * n(i);
    }
    for (int i = 0; i <= h - 2; ++i) {
      sumRight += n(h - 1) * n(i);
    }
    return sumLeft + sumRight;
  }
}