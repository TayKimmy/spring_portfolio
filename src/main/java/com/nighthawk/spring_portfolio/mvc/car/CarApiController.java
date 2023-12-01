package com.nighthawk.spring_portfolio.mvc.car;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/api/sort")
public class CarApiController {

    @GetMapping("/speeds")
    public Map<String, Integer> getAlgorithmSpeeds(@RequestParam(required = false) Integer arraySize) {
        // Use a fixed array size for testing if not provided by the user
        int size = (arraySize != null && arraySize > 0) ? arraySize : 10000;


        // generate random array based on the specified size
        int[] randomArray = generateRandomArray(size);

        // measure sorting speeds for different algorithms
        long mergeSortStartTime = System.currentTimeMillis();
        // merge sort on the random array
        mergeSort(randomArray.clone());
        long mergeSortEndTime = System.currentTimeMillis();
        int mergeSortSpeed = (int) (mergeSortEndTime - mergeSortStartTime);

        long insertionSortStartTime = System.currentTimeMillis();
        // insertion sort on the random array
        insertionSort(randomArray.clone());
        long insertionSortEndTime = System.currentTimeMillis();
        int insertionSortSpeed = (int) (insertionSortEndTime - insertionSortStartTime);

        long bubbleSortStartTime = System.currentTimeMillis();
        // bubble sort on the random array
        bubbleSort(randomArray.clone());
        long bubbleSortEndTime = System.currentTimeMillis();
        int bubbleSortSpeed = (int) (bubbleSortEndTime - bubbleSortStartTime);

        long selectionSortStartTime = System.currentTimeMillis();
        // selection sort on the random array
        selectionSort(randomArray.clone());
        long selectionSortEndTime = System.currentTimeMillis();
        int selectionSortSpeed = (int) (selectionSortEndTime - selectionSortStartTime);

        // algorithm speeds
        Map<String, Integer> algorithmSpeeds = new HashMap<>();
        algorithmSpeeds.put("mergeSort", mergeSortSpeed);
        algorithmSpeeds.put("insertionSort", insertionSortSpeed);
        algorithmSpeeds.put("bubbleSort", bubbleSortSpeed);
        algorithmSpeeds.put("selectionSort", selectionSortSpeed);
        
        return algorithmSpeeds;
    }

    private int[] generateRandomArray(int size) {
        int[] randomArray = new int[size];
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            randomArray[i] = random.nextInt(100);
        }
        return randomArray;
    }

    // sorting algorithms
    private void mergeSort(int[] arr) {
        Arrays.sort(arr);
    }

    private void insertionSort(int[] arr) {
        int n = arr.length;
        for (int i = 1; i < n; ++i) {
            int key = arr[i];
            int j = i - 1;

            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j = j - 1;
            }
            arr[j + 1] = key;
        }
    }
    
    private void bubbleSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n-1; i++) {
            for (int j = 0; j < n-i-1; j++) {
                if (arr[j] > arr[j+1]) {
                    int temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }
            }
        }
    }

    private void selectionSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n-1; i++) {
            int minIdx = i;
            for (int j = i+1; j < n; j++) {
                if (arr[j] < arr[minIdx]) {
                    minIdx = j;
                }
            }
            int temp = arr[minIdx];
            arr[minIdx] = arr[i];
            arr[i] = temp;
        }
    }
}
