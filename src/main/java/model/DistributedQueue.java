package model;

import lombok.Data;
import lombok.Setter;

import java.util.List;

@Data
public class DistributedQueue {
    private final List<Topic> topicList;

}
