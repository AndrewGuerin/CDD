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
}

