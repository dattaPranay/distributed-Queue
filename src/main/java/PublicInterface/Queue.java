package PublicInterface;

import handler.SubscriberHandler;
import handler.ThreadHandler;
import lombok.Data;
import lombok.NonNull;
import model.*;
import Exception.UserNotSubscribed;


import java.util.*;


import static java.lang.Thread.sleep;

@Data
public class Queue {

    private final DistributedQueue distributedQueue;
    private final Map<String, SubscriberHandler> subscribeProcessors;
    private final Map<String, List<String>> topicSubscriberMapping;
    private final ThreadHandler threadHandler;

    private void consume(String subscriberId, String topicId) {
        subscribeProcessors.get(subscriberId).consume(topicId, threadHandler);
    }

    public void publish(Topic topic, Message message) {
        topic.getMessageList().add(message);
        System.out.println("message published: " + message.getText());
        for (String subscriberId : topicSubscriberMapping.get(topic.getId())) {
            //DO something
            consume(subscriberId, topic.getId());

        }
    }

    public void subscribe(Topic topic, Subscriber subscriber) {
        Map<String, TopicSubscriber> subscriberMap = subscriber.getSubscribed();
        if (subscriberMap.containsKey(topic.getId())) return;
        subscriberMap.put(topic.getId(), new TopicSubscriber(topic));
        topicSubscriberMapping.get(topic.getId()).add(subscriber.getId());
        System.out.println(subscriber.getName() + " successfully subscribed to " + topic.getName());
        consume(subscriber.getId(), topic.getId());
    }

    public Topic createTopic(@NonNull String topicName) {
        Topic topic = new Topic(new LinkedList<>(), topicName, UUID.randomUUID().toString());
        distributedQueue.getTopicList().add(topic);
        topicSubscriberMapping.put(topic.getId(), new LinkedList<>());
        System.out.println("topic created :" + topic.getName());
        return topic;
    }


    public Subscriber createSubscriber(@NonNull String name) {
        String uuid = UUID.randomUUID().toString();
        Subscriber subscriber = new Subscriber(new HashMap<>(), uuid, name);
        subscribeProcessors.put(uuid, new SubscriberHandler(subscriber, new HashMap<>()));
        System.out.println("subscriber created: " + subscriber.getName());
        return subscriber;
    }

    public void resetOffset(Subscriber subscriber, String topicId, @NonNull Integer newOffset) throws UserNotSubscribed {
        if (!subscriber.getSubscribed().containsKey(topicId)) {
            throw new UserNotSubscribed();
        }

        TopicSubscriber topicSubscriber = subscriber.getSubscribed().get(topicId);
        threadHandler.getExecutorService().execute(() -> {
            try {
                changeTheOffset(topicSubscriber, newOffset);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println("reset successful for subid " + subscriber.getName() + " " + topicId);
        consume(subscriber.getId(), topicId);
    }

    private void changeTheOffset(@NonNull TopicSubscriber topicSubscriber, int newOffset) throws InterruptedException {
        synchronized (topicSubscriber) {
            System.out.println("sleeping starts");
            sleep(10000);
            System.out.println("sleeping ends");
            topicSubscriber.getOffset().set(newOffset);
        }
    }
}
