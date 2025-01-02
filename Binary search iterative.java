public static int BinarySearch(int[] arr, int val) {
    int lowerbound = 0;
    int upperbound = arr.length - 1;
    while (true) 
    {
      int index = (lowerbound + upperbound) / 2;
      if (lowerbound > upperbound) {
        return -1;
      }
      else if (arr[index] < val) 
      {
        lowerbound = index + 1;
      }
      else if (arr[index] > val) 
      {
        upperbound = index - 1;
      }
      else if (arr[index] == val)
      {
        return index;
      }
    }
  }