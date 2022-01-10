package Lab7.sevenDiningPhilosophers;

import java.util.concurrent.ThreadLocalRandom;
/**
 * Author: Andrew Guerin
 */

public class Philosopher extends Thread
{
    /**
     *  Instantiate amount of Philosophers and forks
     */
    public int philosopherCount;
    public sevenDiningPhilosophers.Fork leftFork;
    public sevenDiningPhilosophers.Fork rightFork;
    Philosopher(int philosNumber, sevenDiningPhilosophers.Fork left, sevenDiningPhilosophers.Fork right)
    {
        philosopherCount = philosNumber;
        leftFork = left;
        rightFork = right;
    }

    /**
     * Grab fork method
     */
    void useFork()
    {
        try
        {
            /**
             * determines the number between 0 to 500 that represents the sleep time in ms
             */
            int waitTime = ThreadLocalRandom.current().nextInt(0, 500);
            System.out.println("Philosopher " + (philosopherCount +1) + " eats for " + waitTime +"ms");
            Thread.sleep(waitTime);
        }
        catch (Exception e)
        {
            e.printStackTrace(System.out);
        }
    }

    /**
     * Run Method
     */
    public void run()
    {
        while (true)
        {
            /**
             *  Philosopher picks up fork
             */
            leftFork.pickUpFork();
            System.out.println("Philosopher " + (philosopherCount +1) + " picks up left fork.");
            rightFork.pickUpFork();
            System.out.println("Philosopher " + (philosopherCount +1) + " picks up right fork.");
            useFork();

            /**
             *  Releases fork when Philosopher is finished
             */
            leftFork.release();
            System.out.println("Philosopher " + (philosopherCount +1) + " puts down left fork.");
            rightFork.release();
            System.out.println("Philosopher " + (philosopherCount +1) + " puts down right fork.");
        }
    }

}