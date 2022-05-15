package handler;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import model.Subscriber;
import model.SubscriberWorker;

import java.util.Map;

@RequiredArgsConstructor
public class SubscriberHandler {

    private final Subscriber subscriber;
    @Getter
    private final Map<String, SubscriberWorker> workers;

    public void consume(String topicId, ThreadHandler threadHandler) {
        if (!workers.containsKey(topicId)){
            workers.put(topicId, new SubscriberWorker(subscriber, subscriber.getSubscribed().get(topicId)));
        }
       //new Thread(workers.get(topicId)).start();
        threadHandler.getExecutorService().execute(workers.get(topicId));
    }

}
