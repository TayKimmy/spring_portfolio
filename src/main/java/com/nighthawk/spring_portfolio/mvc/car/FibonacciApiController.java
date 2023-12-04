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

    abstract static class FibonacciAlgorithm {
        abstract long calculateFibonacci(int length);
    }

    static class ForLoopFibonacci extends FibonacciAlgorithm {
        @Override
        long calculateFibonacci(int length) {
            long a = 0, b = 1;
            for (int i = 2; i < length; i++) {
                long temp = a + b;
                a = b;
                b = temp;
            }
            return a;
        }
    }

    static class WhileLoopFibonacci extends FibonacciAlgorithm {
        @Override
        long calculateFibonacci(int length) {
            long a = 0, b = 1;
            int i = 2;
            while (i < length) {
                long temp = a + b;
                a = b;
                b = temp;
                i++;
            }
            return a;
        }
    }

    static class RecursionFibonacci extends FibonacciAlgorithm {
        @Override
        long calculateFibonacci(int length) {
            return finishRecursion(length);
        }

        private long finishRecursion(int n) {
            if (n <= 1) {
                return n;
            }
            return finishRecursion(n - 1) + finishRecursion(n - 2);
        }
    }

    static class MatrixFibonacci extends FibonacciAlgorithm {
        @Override
        long calculateFibonacci(int length) {
            return matrixRecursive(length);
        }

        private long matrixRecursive(int n) {
            if (n == 0) {
                return 0;
            }
            long a = 0, b = 1, temp;
            for (int i = 2; i <= n; i++) {
                temp = a + b;
                a = b;
                b = temp;
            }
            return a;
        }
    }

    @GetMapping("/calculate")
    public Map<String, Object> calculateFibonacci(
            @RequestParam(required = false) Integer length,
            @RequestParam(required = true) String algorithm) {
        int size = (length != null && length > 0) ? length : 10;

        FibonacciAlgorithm fibonacciAlgorithm = createFibonacciAlgorithm(algorithm);

        Map<String, Object> response = measureFibonacciTime(fibonacciAlgorithm, size);
        response.put("result", fibonacciAlgorithm.calculateFibonacci(size));

        return response;
    }

    private FibonacciAlgorithm createFibonacciAlgorithm(String algorithm) {
        Map<String, FibonacciAlgorithm> algorithmMap = new HashMap<>();
        algorithmMap.put("forloop", new ForLoopFibonacci());
        algorithmMap.put("whileloop", new WhileLoopFibonacci());
        algorithmMap.put("recursion", new RecursionFibonacci());
        algorithmMap.put("matrix", new MatrixFibonacci());

        return algorithmMap.get(algorithm.toLowerCase());
    }

    private Map<String, Object> measureFibonacciTime(FibonacciAlgorithm algorithm, int size) {
        long startTime = System.currentTimeMillis();
        long result = algorithm.calculateFibonacci(size);
        long endTime = System.currentTimeMillis();

        Map<String, Object> response = new HashMap<>();
        response.put("result", result);
        response.put("time", endTime - startTime);

        return response;
    }
}