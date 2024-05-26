package com.task.mediasoft.interview.I59;

import java.util.function.Consumer;

public class AsyncOperation {
    public void performAsyncOperation(Consumer<String> callback) {
        // Выполнение асинхронной операции
        // По завершению вызываем callback
        String result = "Operation completed";
        callback.accept(result);
    }
}

