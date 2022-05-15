import PublicInterface.Queue;
import handler.ThreadHandler;
import model.DistributedQueue;
import model.Message;
import model.Subscriber;
import model.Topic;

import java.util.HashMap;
import java.util.LinkedList;

public class Main {


    public static void main(String[] args) throws Exception{
        DistributedQueue distributedQueue = new DistributedQueue(new LinkedList<>());
        Queue queue = new Queue(distributedQueue, new HashMap<>(), new HashMap<>(), new ThreadHandler(2,10000));

        Subscriber subscriber1 = queue.createSubscriber("ram");
        Subscriber subscriber2 = queue.createSubscriber("sham");
        Subscriber subscriber3 = queue.createSubscriber("jadu");
        Subscriber subscriber4 = queue.createSubscriber("ram");

        Topic topic1 = queue.createTopic("politics");
        Topic topic2 = queue.createTopic("science");
        Topic topic3 = queue.createTopic("literature");
        Topic topic4 = queue.createTopic("politics");

        queue.publish(topic1, new Message("hail hitubhai"));
        queue.publish(topic1, new Message("hail hitubhai1"));

        queue.subscribe(topic2, subscriber1);
        queue.subscribe(topic1, subscriber1);
        queue.publish(topic1, new Message("hail hitubhai3"));
        queue.resetOffset(subscriber1,topic1.getId(), 1);
        queue.publish(topic1, new Message("hail hitubhai4"));
        queue.publish(topic1, new Message("hail hitubhai5"));
        queue.publish(topic1, new Message("hail hitubhai6"));
        queue.publish(topic1, new Message("hail hitubhai7"));

        queue.getThreadHandler().getExecutorService().shutdown();
    }
}
