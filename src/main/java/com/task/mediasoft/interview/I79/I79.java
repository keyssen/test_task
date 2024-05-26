package com.task.mediasoft.interview.I79;

import java.util.LinkedHashMap;
import java.util.Map;

public class I79 {
    public static void main(String[] args) {
        Map<Integer, String> linkedHashMap = new LinkedHashMap<>(16, 0.75f, true);

        // Добавление элементов
        linkedHashMap.put(1, "One");
        linkedHashMap.put(2, "Two");
        linkedHashMap.put(3, "Three");

        // Вывод элементов в исходном порядке
        System.out.println("Original order: " + linkedHashMap);

        // Получение элемента по ключу 2 (он становится последним)
        linkedHashMap.get(2);
        // Вывод элементов после получения элемента по ключу 2
        System.out.println("After accessing key 2: " + linkedHashMap);

        // Добавление нового элемента
        linkedHashMap.put(4, "Four");
        // Вывод элементов после добавления нового элемента
        System.out.println("After adding key 4: " + linkedHashMap);
    }
}