package com.task.mediasoft.kafka.hadler;

import com.task.mediasoft.kafka.request.EventSource;

public interface EventHandler <T extends EventSource> {

    boolean canHandle(EventSource eventSource);

    void handleEvent(T eventSource);
}
