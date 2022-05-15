package model;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class Subscriber {
    private final Map<String, TopicSubscriber> subscribed;
    private final String id;
    private final String name;
}
