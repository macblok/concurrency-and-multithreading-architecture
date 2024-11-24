package tasks.taskthree;

import java.util.Random;

class Producer implements Runnable {
    private MessageBus messageBus;
    private String topic;
    private int counter;

    public Producer(MessageBus messageBus, String topic) {
        this.messageBus = messageBus;
        this.topic = topic;
    }

    @Override
    public void run() {
        while (true) {
            String payload = topic +" Message_" + counter;
            counter++;
            Message message = new Message(topic, payload);
            messageBus.postMessage(topic, message);
            try {
                Thread.sleep(new Random().nextInt(10000) + 1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
