package tasks.taskthree;

public class Main {
    public static void main(String[] args) {
        MessageBus bus = new MessageBus();
        String topic = "Sport";

        Thread producerOneThread = new Thread(new Producer(bus, "Sport"));
        Thread consumerOneThread = new Thread(new Consumer(bus, "Sport"));

        Thread producerTwoThread = new Thread(new Producer(bus, "News"));
        Thread consumerTwoThread = new Thread(new Consumer(bus, "News"));

        Thread producerThreeThread = new Thread(new Producer(bus, "Finances"));
        Thread consumerThreeThread = new Thread(new Consumer(bus, "Finances"));

        Thread producerFourThread = new Thread(new Producer(bus, "Kids"));
        Thread consumerFourThread = new Thread(new Consumer(bus, "Kids"));

        producerOneThread.start();
        consumerOneThread.start();

        producerTwoThread.start();
        consumerTwoThread.start();

        producerThreeThread.start();
        consumerThreeThread.start();

        producerFourThread.start();
        consumerFourThread.start();
    }
}
