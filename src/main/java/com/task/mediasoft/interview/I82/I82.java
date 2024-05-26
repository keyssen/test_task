package com.task.mediasoft.interview.I82;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class I82 {
    public static void main(String[] args) {
        // Создаем BlockingQueue
        BlockingQueue<Integer> queue = new LinkedBlockingQueue<>();

        // Создаем поток-производитель
        Thread producer = new Thread(() -> {
            try {
                for (int i = 1; i <= 5; i++) {
                    System.out.println("Producer produced: " + i);
                    queue.put(i); // Добавляем элемент в очередь
                    Thread.sleep(1000); // Имитируем задержку
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        // Создаем поток-потребитель
        Thread consumer = new Thread(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    int num = queue.take(); // Получаем элемент из очереди
                    System.out.println("Consumer consumed: " + num);
                    Thread.sleep(2000); // Имитируем обработку
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        // Запускаем потоки
        producer.start();
        consumer.start();
    }
}