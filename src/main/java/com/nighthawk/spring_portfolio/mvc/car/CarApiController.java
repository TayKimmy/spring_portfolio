package com.nighthawk.spring_portfolio.mvc.car;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nighthawk.spring_portfolio.mvc.betting.Betting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/sort")
public class CarApiController {

    // sorting algorithms superclass
    abstract class SortingAlgorithm {
        private int iterations; // iteration count

        abstract void sort(int[] arr);

        int measureSortingSpeed(int[] arr) {
            long startTime = System.currentTimeMillis();
            iterations = 0; // set iteration count to 0
            sort(arr);
            long endTime = System.currentTimeMillis();
            return (int) (endTime - startTime);
        }

        int getIterations() {
            return iterations;
        }

        void incrementIterations() {
            iterations++; // add to iteration count
        }
    }

    // merge sort
    class MergeSort extends SortingAlgorithm {
        @Override
        void sort(int[] arr) {
            mergeSort(arr, 0, arr.length - 1);
        }
    
        private void mergeSort(int[] arr, int low, int high) {
            if (low < high) {
                int mid = low + (high - low) / 2;
    
                mergeSort(arr, low, mid);
                mergeSort(arr, mid + 1, high);
    
                merge(arr, low, mid, high);
            }
        }
    
        private void merge(int[] arr, int low, int mid, int high) {
            int n1 = mid - low + 1;
            int n2 = high - mid;
    
            int[] left = new int[n1];
            int[] right = new int[n2];
    
            for (int i = 0; i < n1; ++i) {
                left[i] = arr[low + i];
            }
    
            for (int j = 0; j < n2; ++j) {
                right[j] = arr[mid + 1 + j];
            }
    
            int i = 0, j = 0;
            int k = low;
    
            while (i < n1 && j < n2) {
                if (left[i] <= right[j]) {
                    arr[k] = left[i];
                    i++;
                } else {
                    arr[k] = right[j];
                    j++;
                }
                k++;
                incrementIterations(); 
            }
    
            while (i < n1) {
                arr[k] = left[i];
                i++;
                k++;
                incrementIterations(); 
            }
    
            while (j < n2) {
                arr[k] = right[j];
                j++;
                k++;
                incrementIterations();
            }
        }
    }
    // insertion sort
    class InsertionSort extends SortingAlgorithm {
        @Override
        void sort(int[] arr) {
            int n = arr.length;
            for (int i = 1; i < n; ++i) {
                int key = arr[i];
                int j = i - 1;

                while (j >= 0 && arr[j] > key) {
                    arr[j + 1] = arr[j];
                    j = j - 1;
                    incrementIterations(); // Move it here
                }
                arr[j + 1] = key;
            }
        }
    }
    // bubble sort
    class BubbleSort extends SortingAlgorithm {
        @Override
        void sort(int[] arr) {
            int n = arr.length;
            for (int i = 0; i < n - 1; i++) {
                incrementIterations(); 

                for (int j = 0; j < n - i - 1; j++) {
                    if (arr[j] > arr[j + 1]) {
                        int temp = arr[j];
                        arr[j] = arr[j + 1];
                        arr[j + 1] = temp;
                    }
                }
            }
        }
    }
    // selection sort
    class SelectionSort extends SortingAlgorithm {
        @Override
        void sort(int[] arr) {
            int n = arr.length;
            for (int i = 0; i < n - 1; i++) {
                int minIdx = i;
                for (int j = i + 1; j < n; j++) {
                    if (arr[j] < arr[minIdx]) {
                        minIdx = j;
                        incrementIterations();
                    }
                }
                int temp = arr[minIdx];
                arr[minIdx] = arr[i];
                arr[i] = temp;
            }
        }
    }

    @GetMapping("/speeds")
    public Map<String, Object> getAlgorithmSpeeds(@RequestParam(required = false) Integer arraySize) {
        int size = (arraySize != null && arraySize > 0) ? arraySize : 40000;

        int[] randomArray = generateRandomArray(size);

        Map<String, Object> algorithmSpeeds = new HashMap<>();

        algorithmSpeeds.put("mergeSort", getAlgorithmInfo(new MergeSort(), randomArray.clone()));
        algorithmSpeeds.put("insertionSort", getAlgorithmInfo(new InsertionSort(), randomArray.clone()));
        algorithmSpeeds.put("bubbleSort", getAlgorithmInfo(new BubbleSort(), randomArray.clone()));
        algorithmSpeeds.put("selectionSort", getAlgorithmInfo(new SelectionSort(), randomArray.clone()));

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
    private Map<String, Object> getAlgorithmInfo(SortingAlgorithm algorithm, int[] arr) {
        Map<String, Object> algorithmInfo = new HashMap<>();

        int time = algorithm.measureSortingSpeed(arr);
        int iterations = algorithm.getIterations();

        algorithmInfo.put("time", time);
        algorithmInfo.put("iterations", iterations);

        return algorithmInfo;
    }
@GetMapping("/bet")

public Map<String, Object> betOnSortRace(@RequestParam(required = true) int betAmount,
                                         @RequestParam(required = true) int startingPoints) {
    Betting game = new Betting(startingPoints);

    Map<String, Object> speeds = getAlgorithmSpeeds(null);

    // randomly select an algorithm
    List<String> algorithms = new ArrayList<>(speeds.keySet());
    String selectedAlgorithm = algorithms.get(new Random().nextInt(algorithms.size()));

    String fastestAlgorithm = speeds.entrySet().stream()
            .min(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElse("none");

    boolean isGuessCorrect = selectedAlgorithm.equals(fastestAlgorithm);

    game.placeBet(betAmount, isGuessCorrect);

    Map<String, Object> result = new HashMap<>();
    result.put("message", game.getResultMessage());
    result.put("newScore", game.getPoints());
    result.put("selectedAlgorithm", selectedAlgorithm); // selected algorithm in the response

    Map<String, String> algorithmFacts = getAlgorithmFacts();
    result.put("fastestAlgorithm", fastestAlgorithm); // The actual winning algorithm
    result.put("funFact", algorithmFacts.get(fastestAlgorithm));

    return result;
}

private Map<String, String> getAlgorithmFacts() {
    Map<String, String> facts = new HashMap<>();
    facts.put("mergeSort", "Merge Sort is an efficient, stable, comparison-based, divide and conquer sorting algorithm. Most implementations produce a stable sort, meaning that the implementation preserves the input order of equal elements in the sorted output. Merge Sort also has a time complexity of O(nlogn), which is quite fast!");
    facts.put("insertionSort", "Insertion Sort is a simple sorting algorithm that builds the final sorted array one item at a time. It is much less efficient on large lists than more advanced algorithms such as quicksort, heapsort, or merge sort. Insertion Sort has an average time complexity of O(n^2), making it quite inefficient for larger lists.");
    facts.put("bubbleSort", "Bubble Sort is a simple sorting algorithm that repeatedly steps through the list, compares adjacent elements and swaps them if they are in the wrong order. The pass through the list is repeated until the list is sorted. Bubble Sort has a time complexity of O(n^2), also making it quite inefficient for larger lists.");
    facts.put("selectionSort", "Selection Sort is an in-place comparison sorting algorithm. It has an O(n^2) complexity, which makes it inefficient on large lists, and generally performs worse than the similar insertion sort.");
    return facts;
}

}
