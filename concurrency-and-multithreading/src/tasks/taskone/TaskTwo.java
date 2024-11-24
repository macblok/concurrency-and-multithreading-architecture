package tasks.taskone;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TaskTwo {

    public static void main(String[] args) {
        List<Integer> numbers = new ArrayList<>();

        Thread generator = new Thread(() -> {
            Random random = new Random();
            while (true) {
                synchronized (numbers) {
                    numbers.add(random.nextInt(100));
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        Thread sumPrinter = new Thread(() -> {
            while (true) {
                int sum = 0;
                synchronized (numbers) {
                    for (int number : numbers) {
                        sum += number;
                    }
                }
                System.out.println("Sum: " + sum);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        Thread sqrtOfSumOfSquaresPrinter = new Thread(() -> {
            while (true) {
                double sumOfSquares = 0.0;
                synchronized (numbers) {
                    for (int number : numbers) {
                        sumOfSquares += Math.pow(number, 2);
                    }
                }
                System.out.println("Square root of sum of squares: " + Math.sqrt(sumOfSquares));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        generator.start();
        sumPrinter.start();
        sqrtOfSumOfSquaresPrinter.start();
    }
}

//In this example deadlock can't occur, since there is only one resource shared between threads