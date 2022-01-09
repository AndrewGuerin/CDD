package Lab6.ProducerConsumer;

class Main {
    public static void main(String args[]) {
        task task = new task();
        new Producer(task);
        new Consumer(task);
    }
}