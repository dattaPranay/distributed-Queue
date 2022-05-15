package model;

import lombok.Data;
import lombok.Getter;

import java.util.concurrent.atomic.AtomicInteger;


public class TopicSubscriber {
    @Getter
    private final AtomicInteger offset;
    @Getter
    private final Topic topic;

    public TopicSubscriber(Topic topic) {
        this.topic = topic;
        offset = new AtomicInteger(0);
    }
}
