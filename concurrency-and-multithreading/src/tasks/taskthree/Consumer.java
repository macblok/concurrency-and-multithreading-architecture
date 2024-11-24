package tasks.taskthree;

class Consumer implements Runnable {
    private MessageBus messageBus;
    private String topic;

    public Consumer(MessageBus messageBus, String topic) {
        this.messageBus = messageBus;
        this.topic = topic;
    }

    @Override
    public void run() {
        while (true) {
            Message message = messageBus.consumeMessage(topic);
            if (message != null) {
                System.out.println("Consumed: " + message.getPayload());
            }
        }
    }
}
