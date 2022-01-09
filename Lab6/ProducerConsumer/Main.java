package Lab6.ProducerConsumer;
/**
 * Author: Andrew Guerin
 */

/**
 * Main Class
 */
class Main {
    public static void main(String args[]) {
        /**
         * Creating buffer queue and consumer and producer threads
         */
        task task = new task();
        new Producer(task);
        new Consumer(task);
    }
}