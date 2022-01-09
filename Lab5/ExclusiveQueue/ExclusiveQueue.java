package Lab5.ExclusiveQueue;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/** @author Andrew Guerin  */

public class ExclusiveQueue {
        private final int runtime = 50;
        private int leaders = followers = 0;
        private int followers = 0;
        private final Semaphore mutex = new Semaphore(1);
        private final Semaphore leaderQueue = new Semaphore(0);
        private final Semaphore followerQueue = new Semaphore(0);
        private final Semaphore rendezvous = new Semaphore(0);

        public void leaderFollowerExecution() {
            for (int index = 0; index < runtime; index++) {
                final int CurrentIndex = index;

                /** leader section */
                    public void run() {
                        mutex.acquireUninterruptibly();
                        if (followers > 0) {
                            followers--;
                            followerQueue.release();
                        } else {
                            leaders--;
                            mutex.release();
                        }

                        dance("leader " + CurrentIndex);
                        mutex.release();
                    }

                });

                /** leader section */
                //follower

                    public void run() {
                        if (leaders > 0) {
                            leaders--;
                            leaderQueue.release();
                        } else {
                            followers++;
                            mutex.release();
                        }

                        dance("follower " + CurrentIndex);
                        rendezvous.release();
                    }

                });
            }

        }

        private void dance(String type) {
            System.out.println("Generated " + type);
        }
    }

