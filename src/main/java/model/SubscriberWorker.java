package model;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SubscriberWorker implements Runnable {
    private final Subscriber subscriber;
    private final TopicSubscriber topicSubscriber;

    private void consume(Message message) {
        System.out.println(subscriber.getName() + " " + message.toString());
    }

    @Override
    public void run() {
        do {
            synchronized (topicSubscriber) {
                int offset = topicSubscriber.getOffset().get();
                if (offset >= topicSubscriber.getTopic().getMessageList().size()) {
                    break;
                }
                Message message = topicSubscriber.getTopic().getMessageList().get(offset);
                consume(message);
                topicSubscriber.getOffset().set(offset + 1);
            }
        } while (true);
    }

}
