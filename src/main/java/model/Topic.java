package model;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class Topic {
    private final List<Message> messageList;
    private final String name;
    private final String id;
}
