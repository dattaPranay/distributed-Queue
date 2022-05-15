package handler;

import lombok.Getter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadHandler {
    @Getter
    private final ExecutorService executorService;
    public ThreadHandler(int size, int ttl) {
        executorService = new ThreadPoolExecutor(10,100,1000, TimeUnit.MILLISECONDS,new LinkedBlockingQueue<>());
    }

}
