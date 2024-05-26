package com.task.mediasoft.interview.I77;

import java.util.Map;
import java.util.WeakHashMap;

public class WeakHashMapExample {
    public static void main(String[] args) {
        Map<Object, String> weakMap = new WeakHashMap<>();

        Object key1 = new Object();
        Object key2 = new Object();

        weakMap.put(key1, "value1");
        weakMap.put(key2, "value2");

        System.out.println("Before GC: " + weakMap.size());  // Output: 2

        key1 = null;  // Теперь объект key1 может быть собран мусором
        System.gc();  // Вызов сборщика мусора

        try {
            Thread.sleep(1000);  // Подождем немного, чтобы сборщик мусора сработал
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("After GC: " + weakMap.size());  // Output: 1 или 2, зависит от работы GC
    }
}
