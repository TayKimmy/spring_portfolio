package com.nighthawk.spring_portfolio.mvc.car;

import java.util.Arrays;
import java.util.Random;

public class CarSortingAlgorithm {
    protected int iterations;
    protected int comparisons;
    protected int mergesOrSwaps;

    // Timing logic for sorting algorithms
    public void timeSortingAlgorithm(int[] arr, String algorithmName) {
        iterations = 0;
        comparisons = 0;
        mergesOrSwaps = 0;

        long startTime = System.nanoTime();

        // Invoke the specific sorting algorithm
        sort(arr);

        long endTime = System.nanoTime();
        long duration = endTime - startTime;

        System.out.println(algorithmName + " Sorted Array: " + Arrays.toString(arr));
        System.out.println(algorithmName + " Time: " + duration + " nanoseconds");
        System.out.println(algorithmName + " Iterations: " + iterations);
        System.out.println(algorithmName + " Comparisons: " + comparisons);
        System.out.println(algorithmName + " Merges/Swaps: " + mergesOrSwaps);
        System.out.println();
    }

    // Method to be implemented by subclasses
    protected void sort(int[] arr) {
        // To be overridden by specific sorting algorithm
    }

    // Generate a random array of integers
    protected int[] generateRandomArray(int length) {
        Random random = new Random();
        int[] arr = new int[length];
        for (int i = 0; i < length; i++) {
            arr[i] = random.nextInt(100); // Adjust the bound as needed
        }
        return arr;
    }
}

class InsertionSort extends CarSortingAlgorithm {
    @Override
    protected void sort(int[] arr) {
        int n = arr.length;
        for (int i = 1; i < n; ++i) {
            int key = arr[i];
            int j = i - 1;

            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j = j - 1;

                iterations++;
                comparisons++;
                mergesOrSwaps++;
            }
            arr[j + 1] = key;
        }
    }
}

class MergeSort extends CarSortingAlgorithm {
    @Override
    protected void sort(int[] arr) {
        mergeSort(arr, 0, arr.length - 1);
    }

    private void mergeSort(int[] arr, int l, int r) {
        if (l < r) {
            int m = (l + r) / 2;

            mergeSort(arr, l, m);
            mergeSort(arr, m + 1, r);

            merge(arr, l, m, r);
        }
    }

    private void merge(int[] arr, int l, int m, int r) {
        int n1 = m - l + 1;
        int n2 = r - m;

        int[] L = new int[n1];
        int[] R = new int[n2];

        for (int i = 0; i < n1; ++i) {
            L[i] = arr[l + i];
            iterations++;
            mergesOrSwaps++;
        }
        for (int j = 0; j < n2; ++j) {
            R[j] = arr[m + 1 + j];
            iterations++;
            mergesOrSwaps++;
        }

        int i = 0, j = 0;
        int k = l;
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                arr[k] = L[i];
                i++;
            } else {
                arr[k] = R[j];
                j++;
            }
            k++;
            comparisons++;
            mergesOrSwaps++;
        }

        while (i < n1) {
            arr[k] = L[i];
            i++;
            k++;
            mergesOrSwaps++;
        }

        while (j < n2) {
            arr[k] = R[j];
            j++;
            k++;
            mergesOrSwaps++;
        }
    }
}

class SelectionSort extends CarSortingAlgorithm {
    @Override
    protected void sort(int[] arr) {
        int n = arr.length;

        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;

            for (int j = i + 1; j < n; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
                iterations++;
                comparisons++;
                mergesOrSwaps++;
            }

            int temp = arr[minIndex];
            arr[minIndex] = arr[i];
            arr[i] = temp;
            mergesOrSwaps++;
        }
    }
}

class BubbleSort extends CarSortingAlgorithm {
    @Override
    protected void sort(int[] arr) {
        int n = arr.length;

        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    mergesOrSwaps++;
                }
                iterations++;
                comparisons++;
            }
        }
    }
}

class Main {
    public static void main(String[] args) {
        CarSortingAlgorithm sortingAlgorithm = new CarSortingAlgorithm();

        int length = 10; // Adjust the length as needed
        int[] arr = sortingAlgorithm.generateRandomArray(length);

        InsertionSort insertionSort = new InsertionSort();
        insertionSort.timeSortingAlgorithm(Arrays.copyOf(arr, arr.length), "Insertion Sort");

        MergeSort mergeSort = new MergeSort();
        mergeSort.timeSortingAlgorithm(Arrays.copyOf(arr, arr.length), "Merge Sort");

        SelectionSort selectionSort = new SelectionSort();
        selectionSort.timeSortingAlgorithm(Arrays.copyOf(arr, arr.length), "Selection Sort");

        BubbleSort bubbleSort = new BubbleSort();
        bubbleSort.timeSortingAlgorithm(Arrays.copyOf(arr, arr.length), "Bubble Sort");
    }
}
