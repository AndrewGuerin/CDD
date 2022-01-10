package Lab7.sevenDiningPhilosophers;

/**
 * Author: Andrew Guerin
 */

import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;


public class sevenDiningPhilosophers
{
    /**
     * Sets the number of Philosophers
     */
    static int philosopher = 7;
    static Philosopher philosophers[] = new Philosopher[philosopher];
    static Fork forks[] = new Fork[philosopher];
    static class Fork
    {
        public Semaphore mutex = new Semaphore(1);

        /**
         *  Get method for fork
         */
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

        /**
         * Release method
         */
        void release()
        {
            mutex.release();
        }
        boolean isAvailable()
        {
            return mutex.availablePermits() > 0;
        }
    }

    /**
     *  Driver class
     */
    public static void main(String args[])
    {
        /**
         *  Iterates through forks
         */
        for (int index = 0; index < philosopher; index++)
        {
            forks[index] = new Fork();
        }

        /**
         *  Iterates through philosophers
         */
        for (int index = 0; index < philosopher; index++)
        {
            philosophers[index] = new Philosopher(index, forks[index], forks[(index + 1) % philosopher]);
            philosophers[index].start();
        }
        while (true)
        {
            try
            {
                /**
                 *  Force sleep
                 */
                Thread.sleep(500);
                boolean deadlock = true;

                /**
                 *  Checks if a fork is in use
                 */
                for (Fork f : forks)
                {
                    if (f.isAvailable())
                    {
                        deadlock = false;
                    }
                }
                /**
                 *  Deadlock happens if sleep time is equal to 500ms
                 */
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