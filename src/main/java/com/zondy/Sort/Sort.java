package com.zondy.Sort;

public class Sort {
    public void Print(int[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + "\t");
        }
        System.out.println("");
    }

    public void bubbleSort(int[] arr) {
        int pos = 0, tmpPos = 0;
        for (int i = 0; i < arr.length; i++) {
            pos = tmpPos;
            for (int j = arr.length - 1; j > pos; j--) {
                if (arr[j] < arr[j - 1]) {
                    int tmp = arr[j - 1];
                    arr[j - 1] = arr[j];
                    arr[j] = tmp;
                    tmpPos = j;
                }
            }
            if (tmpPos == pos) {
                break;
            }

        }
    }

    public int partition(int[] arr, int left, int right) {
        int paviot = arr[left];
        while (left != right) {
            while (arr[right] >= paviot && right > left) {
                right--;
            }
            arr[left] = arr[right];
            while (arr[left] <= paviot && left < right) {
                left++;
            }
            arr[right] = arr[left];
        }
        arr[left] = paviot;
        return left;
    }

    public void quickSort(int[] arr, int left, int right) {
        if (left >= right) return;
        int p = partition(arr, left, right);
        quickSort(arr, left, p - 1);
        quickSort(arr, p + 1, right);
    }

    public static void main(String args[]) {
        Sort demo = new Sort();
//        int[] arr = new int[]{21,12, 5, 9, 50, 33, 27, 10};
//        /*
//            21,12, 5, 9, 50, 33, 27, 10
//            10,12, 5, 9, 50, 33, 27, 10
//            10,12, 5, 9, 50, 33, 27, 50
//            10,12, 5, 9, 21, 33, 27, 50
//         */

        int[] arr = new int[]{21, 17, 29, 67, 13, 25, 82, 9, 15, 36, 43, 17, 30};
        	/*	21

			21 17 29 67 13 25 82 9 15 36 43 17 30

			17 17 29 67 13 25 82 9 15 36 43 17 30
			17 17 29 67 13 25 82 9 15 36 43 29 30
			17 17 29 67 13 25 82 9 29 36 43 29 30
			17 17 9 67 13 25 82 9 29 36 43 29 30
			17 17 9 67 13 25 82 67 29 36 43 29 30
			17 17 9 13 13 25 82 67 29 36 43 29 30

			17 17 9 13 21 25 82 67 29 36 43 29 30
		*/
        //demo.partition(arr, 0, arr.length - 1);
        demo.bubbleSort(arr);
        demo.Print(arr);
    }
}
