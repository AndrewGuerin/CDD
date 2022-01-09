package Lab6.ProducerConsumer;

import java.util.concurrent.Semaphore;
/**
 * Author: Andrew Guerin
 */

/**
 * Task Class
 */
public class task {

    int item;
    /**
     * The consumerSemaphore is initialized to zero to allow the putInBuffer method to execute first
     */
    static Semaphore producerSemaphore = new Semaphore(1);
    static Semaphore consumerSemaphore = new Semaphore(0);


    void putItemInBuffer(int item)
    {
        /**
         * The producer Semaphore must aquire before the item can be consumed
         */
        try {
            producerSemaphore.acquire();
        }
        catch (InterruptedException e) {
            System.out.println("Exception handled");
        }

        this.item = item;
        System.out.println("Producer produced item : " + item);
        /**
         * must release after item is consumed
         */
        consumerSemaphore.release();
    }

    void getItemFromBuffer()
    {
        /**
         * The consumer Semaphore must aquire before the item can be consumed
         */
        try {
            consumerSemaphore.acquire();
        }
        catch (InterruptedException e) {
            System.out.println("Exception handled");
        }

        System.out.println("Consumer has consumed item : " + item);
        producerSemaphore.release();
    }
}

