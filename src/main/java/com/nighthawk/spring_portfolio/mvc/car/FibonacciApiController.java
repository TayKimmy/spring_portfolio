package com.nighthawk.spring_portfolio.mvc.car;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/fibonacci")
public class FibonacciApiController {

    @GetMapping("/speeds")
    public Map<String, Integer> getAlgorithmSpeeds(@RequestParam(required = false) Integer length) {
        // Use a fixed array size for testing if not provided by the user
        int size = (length != null && length > 0) ? length : 100;

        // 
        int forLoopTime = (int) measureTime(() -> fibonacciForLoop(size));
        int whileLoopTime = (int) measureTime(() -> fibonacciWhileLoop(size));
        int recursionTime = (int) measureTime(() -> fibonacciRecursion(size));
        int matrixTime = (int) measureTime(() -> fibonacciMatrix(size));
        

        // algorithm speeds
        Map<String, Integer> algorithmSpeeds = new HashMap<>();
        algorithmSpeeds.put("forLoop", forLoopTime);
        algorithmSpeeds.put("whileLoop", whileLoopTime);
        algorithmSpeeds.put("recursion", recursionTime);
        algorithmSpeeds.put("matrix", matrixTime);
        
        return algorithmSpeeds;
    }

    // method to determine times for each algorithm
    private long measureTime(Runnable task) {
        long startTime = System.currentTimeMillis();
        task.run();
        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }

    // Fibonacci using for loop
    private void fibonacciForLoop(int length) {
        long a = 0, b = 1;
        for (int i = 2; i < length; i++) {
            long temp = a + b;
            a = b;
            b = temp;
        }
    }

    // Fibonacci using while loop
    private void fibonacciWhileLoop(int length) {
        long a = 0, b = 1;
        int i = 2;
        while (i < length) {
            long temp = a + b;
            a = b;
            b = temp;
            i++;
        }
    }

    // Fibonacci using recursion
    private void fibonacciRecursion(int length) {
        for (int i = 0; i < length; i++) {
            finishRecursion(i);
        }
    }

    private long finishRecursion(int n) {
        if (n <= 1) {
            return n;
        }
        return finishRecursion(n - 1) + finishRecursion(n - 2);
    }

    // Fibonacci using matrices
    private void fibonacciMatrix(int length) {
        for (int i = 0; i < length; i++) {
            matrixRecursive(i);
        }
    }

    private void matrixRecursive(int n) {
        if (n == 0) {
            return;
        }
        long a = 0, b = 1, temp;
        for (int i = 2; i <= n; i++) {
            temp = a + b;
            a = b;
            b = temp;
        }
    }
}