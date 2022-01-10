package Lab5.ExclusiveQueue;
/**
 * Author: Andrew Guerin
 */
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class ExclusiveQueue {
    private final int runtime = 50;
    private int leaders = followers = 0;
    private int followers = 0;
    private final Semaphore mutex = new Semaphore(1);
    private final Semaphore leaderQueue = new Semaphore(0);
    private final Semaphore followerQueue = new Semaphore(0);
    private final Semaphore rendezvous = new Semaphore(0);
    private ExecutorService executor = Executors.newCachedThreadPool();



    public void leaderFollowerExecution() {
        for (int index = 0; index < runtime; index++) {
            final int CurrentIndex = index;
            /**
             * Leader Section
             */

            /**
             * When leader arrives it gets the mutex that protects leaders and
             * followers. If there is a follower waiting, the leader decrements followers,
             * signals a follower, and then invokes dance, all before releasing mutex
             *
             * If there are no followers waiting, the leader has to give up the mutex before
             * waiting on leaderQueue.
             */
            executor.submit(() -> {
                mutex.acquireUninterruptibly();
                if (followers > 0) {
                    followers--;
                    followerQueue.release();
                } else {
                    leaders--;
                    mutex.release();
                    leaderQueue.acquireUninterruptibly();
                }

                dance("leader " + CurrentIndex);
                rendezvous.acquireUninterruptibly();
                mutex.release();
            });

            /**
             * Follower Section
             */

            /**
             * When a follower arrives, it checks for a waiting leader. If there is one, the
             * follower decrements leaders, signals a leader, and executes dance, all without
             * releasing mutex.
             */
            executor.submit(() -> {
                mutex.acquireUninterruptibly();
                if (leaders > 0) {
                    leaders--;
                    leaderQueue.release();
                } else {
                    followers++;
                    mutex.release();
                    followerQueue.acquireUninterruptibly();
                }

                dance("follower " + CurrentIndex);
                rendezvous.release();
            });
        }

    }

    /**
     *  Dance method
     */
    private void dance(String type) {
        System.out.println("Generated " + type);
    }

    public static void main(String[] args) {
        ExclusiveQueue queue = new ExclusiveQueue();
        queue.leaderFollowerExecution();

    }
}