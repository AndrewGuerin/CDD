package Lab6.ProducerConsumer;

public class Consumer implements Runnable {
    task task;

    Consumer(task task) {
        this.task = task;
        new Thread(this, "Consumer").start();
    }

    public void run() {
        for (int index = 0; index < 3; index++)
            task.getItemFromBuffer();
    }
}
