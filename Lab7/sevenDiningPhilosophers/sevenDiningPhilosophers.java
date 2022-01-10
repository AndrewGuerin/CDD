package Lab7.sevenDiningPhilosophers;

/**
 * Author: Andrew Guerin
 */

import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

public class sevenDiningPhilosophers
{

    static int philosopher = 7;
    static Philosopher philosophers[] = new Philosopher[philosopher];
    static Fork forks[] = new Fork[philosopher];
    static class Fork
    {
        public Semaphore mutex = new Semaphore(1);
        void pickUpFork()
        {
            try
            {
                mutex.acquire();
            }
            catch (Exception e)
            {
                e.printStackTrace(System.out);
            }
        }
        void release()
        {
            mutex.release();
        }
        boolean isAvailable()
        {
            return mutex.availablePermits() > 0;
        }
    }

    public static void main(String args[])
    {
        for (int index = 0; index < philosopher; index++)
        {
            forks[index] = new Fork();
        }

        for (int index = 0; index < philosopher; index++)
        {
            philosophers[index] = new Philosopher(index, forks[index], forks[(index + 1) % philosopher]);
            philosophers[index].start();
        }
        while (true)
        {
            try
            {
                Thread.sleep(500);
                boolean deadlock = true;
                for (Fork cs : forks)
                {
                    if (cs.isAvailable())
                    {
                        deadlock = false;
                    }
                }
                if (deadlock)
                {
                    Thread.sleep(500);
                    break;
                }
            }
            catch (Exception e)
            {
                e.printStackTrace(System.out);
            }
        }
        System.exit(0);
    }
}