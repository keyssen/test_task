package com.task.mediasoft.interview.I84;

import java.util.List;

public class I84 {
    public static void main(String[] args) {
        // Создание неизменяемого списка с тремя элементами
        List<String> list = List.of("a", "b", "c");
        System.out.println(list); // Выведет: [a, b, c]

//         Попытка добавить элемент в неизменяемый список
//         list.add("d"); // Вызовет UnsupportedOperationException

        // Попытка изменить элемент в неизменяемом списке
        // list.set(0, "x"); // Вызовет UnsupportedOperationException

        // Попытка передать null в качестве элемента
//         List<String> nullList = List.of("a", null, "c"); // Вызовет NullPointerException
    }
}
