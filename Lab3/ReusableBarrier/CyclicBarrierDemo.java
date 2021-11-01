package Lab3.ReusableBarrier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierDemo {
        private CyclicBarrier cyclicBarrier;
        private List<List<Integer>> partialResults
                = Collections.synchronizedList(new ArrayList<>());
        private Random random = new Random();
        private int semiCompleteResults;
        private int workerCount;


    class CalculatingThread implements Runnable {

        @Override
        public void run() {
            String thisThreadName = Thread.currentThread().getName();
            List<Integer> partialResult = new ArrayList<>();

            // store semi complete results
            for (int i = 0; i < semiCompleteResults; i++) {
                Integer num = random.nextInt(5);
                System.out.println(thisThreadName + ": Calculating! Final result - " + num);
                partialResult.add(num);
            }

            partialResults.add(partialResult);
            try {
                System.out.println(thisThreadName + " waiting for remaining threads.");
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                // ...
            } catch (BrokenBarrierException e) {
                // ...
            }
        }
    }

    class AggregatorThread implements Runnable {

        @Override
        public void run() {

            String thisThreadName = Thread.currentThread().getName();

            System.out.println(
                    thisThreadName + ": Computing sum of " + workerCount + " workers, having " + semiCompleteResults + " results each.");
            int sum = 0;

            for (List<Integer> threadResult : partialResults) {
                System.out.print("Adding ");
                for (Integer partialResult : threadResult) {
                    System.out.print(partialResult+" ");
                    sum += partialResult;
                }
                System.out.println();
            }
            System.out.println(thisThreadName + ": Final result = " + sum);
        }
    }

    public void runCalculation(int numWorkers, int numberOfSemiCompleteResults) {
        semiCompleteResults = numberOfSemiCompleteResults;
        workerCount = numWorkers;

        cyclicBarrier = new CyclicBarrier(workerCount, new AggregatorThread());

        System.out.println("Creating " + workerCount
                + " worker threads to compute "
                + semiCompleteResults + " partial results each");

        for (int i = 0; i < workerCount; i++) {
            Thread worker = new Thread(new CalculatingThread());
            worker.setName("Thread " + i);
            worker.start();
        }
    }

    public static void main(String[] args) {
        CyclicBarrierDemo demo = new CyclicBarrierDemo();
        demo.runCalculation(3, 2);
    }
}

