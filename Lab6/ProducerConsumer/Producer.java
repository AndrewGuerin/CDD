package Lab6.ProducerConsumer;

class Producer implements Runnable {
    task task;

    Producer(task task) {
        this.task = task;
        new Thread(this, "Producer").start();
    }

    public void run() {
        for (int index = 0; index < 3; index++)
            task.putItemInBuffer(index);
    }
}