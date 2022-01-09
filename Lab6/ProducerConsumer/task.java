package Lab6.ProducerConsumer;

/**
 * Author: Andrew Guerin
 */

import java.util.concurrent.Semaphore;

public class task {

    int item;
    static Semaphore producerSemaphore = new Semaphore(1);
    static Semaphore consumerSemaphore = new Semaphore(0);

    void putItemInBuffer(int item)
    {
        try {
            producerSemaphore.acquire();
        }
        catch (InterruptedException e) {
            System.out.println("Exception handled");
        }

        this.item = item;
        System.out.println("Producer produced item : " + item);
        consumerSemaphore.release();
    }

    void getItemFromBuffer()
    {
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

class Consumer implements Runnable {
    task task;
    Consumer(task task)
    {
        this.task = task;
        new Thread(this, "Consumer").start();
    }

    public void run()
    {
        for (int index = 0; index < 3; index++)
            task.getItemFromBuffer();
    }
}

class Producer implements Runnable {
    task task;
    Producer(task task)
    {
        this.task = task;
        new Thread(this, "Producer").start();
    }

    public void run()
    {
        for (int index = 0; index < 3; index++)
            task.putItemInBuffer(index);
    }
}

class Main {
    public static void main(String args[])
    {
        task task = new task();
        new Producer(task);
        new Consumer(task);
    }
}