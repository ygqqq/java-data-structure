package com.zondy.OrdArray;

public class OrdArray {
    private int[] array;
    private int len = 0;

    public OrdArray(int capcity) {
        array = new int[capcity];
    }

    public OrdArray(int[] arr, int capcity) {
        this(capcity);
        for (int i = 0; i < arr.length; i++) {
            array[i] = arr[i];
            len++;
        }
    }

    /*
     如果是插入：二分查找待插入的索引，如果已存在则返回-1，否则返回插入位置索引
    　否则就是查找或删除：二分查找待查找或待删除的的索引，找到返回索引，否则返回-1
  */
    public int FindIndex(int key, boolean isInsert) {
        int low = 0;
        int high = len - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            if (key == array[mid]) {
                if (isInsert) return -1;
                else return mid;
            } else if (key > array[mid]) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        if (isInsert) return low;
        else return -1;
    }

    public boolean Insert(int key) {
        if (len == 0) {
            array[0] = key;
            len++;
            return true;
        }
        int index = FindIndex(key, true);
        if (index == -1) {
            System.out.println("重复插入数据:" + key);
            return false;
        }
        for (int j = len; j > index; j--) {
            array[j] = array[j - 1];
        }
        array[index] = key;
        len++;
        return true;
    }

    public boolean Delete(int key) {
        int index = FindIndex(key, false);
        if (index == -1) {
            System.out.println("要删除的数据不存在");
            return false;
        }
        for (int i = index; i < len; i++) {
            array[i] = array[i + 1];
        }
        len--;
        return true;
    }

    public void Print() {
        for (int i = 0; i < len; i++) {
            System.out.print(array[i] + "\t");
        }
        System.out.println("");
    }

    public static void main(String args[]) {
        OrdArray demo = new OrdArray(new int[]{1, 3, 5, 8, 10, 13, 18, 20, 25}, 100);
        demo.Insert(11);
        demo.Insert(12);
        demo.Insert(19);
        demo.Print();
        demo.Delete(18);
        demo.Delete(13);
        demo.Print();

    }
}
