package tasks.taskthree;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

class MessageBus {
    private final Map<String, Queue<Message>> topicQueues = new HashMap<>();

    public synchronized void postMessage(String topic, Message message) {
        if (!topicQueues.containsKey(topic)) {
            topicQueues.put(topic, new LinkedList<>());
        }
        topicQueues.get(topic).add(message);
        notifyAll();
    }

    public synchronized Message consumeMessage(String topic) {
        while (!topicQueues.containsKey(topic) || topicQueues.get(topic).isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return null;
            }
        }
        return topicQueues.get(topic).poll();
    }
}
