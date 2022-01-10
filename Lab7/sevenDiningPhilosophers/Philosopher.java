package Lab7.sevenDiningPhilosophers;

import java.util.concurrent.ThreadLocalRandom;
/**
 * Author: Andrew Guerin
 */

public class Philosopher extends Thread
{
    public int philosopherCount;
    public sevenDiningPhilosophers.Fork leftFork;
    public sevenDiningPhilosophers.Fork rightFork;
    Philosopher(int philosNumber, sevenDiningPhilosophers.Fork left, sevenDiningPhilosophers.Fork right)
    {
        philosopherCount = philosNumber;
        leftFork = left;
        rightFork = right;
    }

    void useFork()
    {
        try
        {
            int waitTime = ThreadLocalRandom.current().nextInt(0, 500);
            System.out.println("Philosopher " + (philosopherCount +1) + " eats for " + waitTime +"ms");
            Thread.sleep(waitTime);
        }
        catch (Exception e)
        {
            e.printStackTrace(System.out);
        }
    }

    public void run()
    {
        while (true)
        {
            leftFork.pickUpFork();
            System.out.println("Philosopher " + (philosopherCount +1) + " picks up left fork.");
            rightFork.pickUpFork();
            System.out.println("Philosopher " + (philosopherCount +1) + " picks up right fork.");
            useFork();
            leftFork.release();
            System.out.println("Philosopher " + (philosopherCount +1) + " puts down left fork.");
            rightFork.release();
            System.out.println("Philosopher " + (philosopherCount +1) + " puts down right fork.");
        }
    }

}