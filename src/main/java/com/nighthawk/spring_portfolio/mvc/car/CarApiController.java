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
        abstract void sort(int[] arr);

        int measureSortingSpeed(int[] arr) {
            long startTime = System.currentTimeMillis();
            sort(arr);
            long endTime = System.currentTimeMillis();
            return (int) (endTime - startTime);
        }
    }

    // merge sort
    class MergeSort extends SortingAlgorithm {
        @Override
        void sort(int[] arr) {
            Arrays.sort(arr);
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
                    }
                }
                int temp = arr[minIdx];
                arr[minIdx] = arr[i];
                arr[i] = temp;
            }
        }
    }

    @GetMapping("/speeds")
    public Map<String, Integer> getAlgorithmSpeeds(@RequestParam(required = false) Integer arraySize) {
        int size = (arraySize != null && arraySize > 0) ? arraySize : 40000;

        int[] randomArray = generateRandomArray(size);

        Map<String, Integer> algorithmSpeeds = new HashMap<>();

        algorithmSpeeds.put("mergeSort", new MergeSort().measureSortingSpeed(randomArray.clone()));
        algorithmSpeeds.put("insertionSort", new InsertionSort().measureSortingSpeed(randomArray.clone()));
        algorithmSpeeds.put("bubbleSort", new BubbleSort().measureSortingSpeed(randomArray.clone()));
        algorithmSpeeds.put("selectionSort", new SelectionSort().measureSortingSpeed(randomArray.clone()));

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

@GetMapping("/bet")

public Map<String, Object> betOnSortRace(@RequestParam(required = true) int betAmount,
                                         @RequestParam(required = true) int startingPoints) {
    Betting game = new Betting(startingPoints);

    Map<String, Integer> speeds = getAlgorithmSpeeds(null);

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
    result.put("funFact", algorithmFacts.get(fastestAlgorithm)); // Add fun fact
    result.put("didUserWin", isGuessCorrect); // Add win/loss information

    return result;
}

private Map<String, String> getAlgorithmFacts() {
    Map<String, String> facts = new HashMap<>();
    facts.put("mergeSort", "Merge Sort is an efficient, stable, comparison-based, divide and conquer sorting algorithm. Most implementations produce a stable sort, meaning that the implementation preserves the input order of equal elements in the sorted output.");
    facts.put("insertionSort", "Insertion Sort is a simple sorting algorithm that builds the final sorted array one item at a time. It is much less efficient on large lists than more advanced algorithms such as quicksort, heapsort, or merge sort.");
    facts.put("bubbleSort", "Bubble Sort is a simple sorting algorithm that repeatedly steps through the list, compares adjacent elements and swaps them if they are in the wrong order. The pass through the list is repeated until the list is sorted.");
    facts.put("selectionSort", "Selection Sort is an in-place comparison sorting algorithm. It has an O(n^2) complexity, which makes it inefficient on large lists, and generally performs worse than the similar insertion sort.");
    return facts;
}

}
