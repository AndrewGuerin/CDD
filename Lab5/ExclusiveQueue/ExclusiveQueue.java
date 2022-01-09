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

    private void dance(String type) {
        System.out.println("Generated " + type);
    }

    public static void main(String[] args) {
        ExclusiveQueue queue = new ExclusiveQueue();
        queue.leaderFollowerExecution();

    }
}