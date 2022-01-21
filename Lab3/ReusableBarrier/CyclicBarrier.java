package Lab3.ReusableBarrier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;

public class CyclicBarrier {
        private java.util.concurrent.CyclicBarrier cyclicBarrier;
        private List<List<Integer>> partialResults
                = Collections.synchronizedList(new ArrayList<>());
        private Random random = new Random();
        private int partResults;
        private int workerCount;


    class CalculatingThread implements Runnable {

        @Override
        public void run() {
            String thisThreadName = Thread.currentThread().getName();
            List<Integer> partialResult = new ArrayList<>();

            /**
             * store semi complete results
             */
            for (int index = 0; index < partResults; index++) {
                Integer num = random.nextInt(5);
                System.out.println(thisThreadName + ": Calculating! Final result - " + num);
                partialResult.add(num);
            }

            partialResults.add(partialResult);
            try {
                System.out.println(thisThreadName + " waiting for remaining threads.");
                cyclicBarrier.await();
            } catch (InterruptedException e) {
            } catch (BrokenBarrierException e) {
            }
        }
    }

    class AggregatorThread implements Runnable {

        /**
         * add the numbers in the partial list
         */
        @Override
        public void run() {

            String thisThreadName = Thread.currentThread().getName();

            System.out.println(thisThreadName + ": Computing sum of " + workerCount + " workers, having " + partResults + " results each.");
            int sum = 0;

            /**
             * tally sum
             */
            for (List<Integer> threadResult : partialResults) {
                for (Integer partialResult : threadResult) {
                    sum += partialResult;
                }
            }
            /**
             * prints the final sum of thread results
             */
            System.out.println(thisThreadName + ": Final result = " + sum);
        }
    }

    public void runCalculation(int numWorkers, int numberOfSemiCompleteResults) {
        partResults = numberOfSemiCompleteResults;
        workerCount = numWorkers;

        cyclicBarrier = new java.util.concurrent.CyclicBarrier(workerCount, new AggregatorThread());

        System.out.println("Creating " + workerCount + " worker threads to compute " + partResults + " partial results each");

        for (int index = 0; index < workerCount; index++) {
            Thread worker = new Thread(new CalculatingThread());
            worker.setName("Thread " + index);
            worker.start();
        }
    }

    public static void main(String[] args) {
        CyclicBarrier demo = new CyclicBarrier();
        demo.runCalculation(3, 2);
    }
}

