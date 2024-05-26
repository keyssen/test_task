package com.task.mediasoft.interview.CORE.I38;

public class I38 {
    public static void main(String[] args) {
        String str1 = "Hello";
        String str2 = new String("      Hello         ");

        // str1 и str2 ссылаются на разные объекты
        System.out.println(str1 == str2); // Выведет: false

        // intern() вернет ссылку на строку из пула строк
        String str3 = str2.trim();
        System.out.println(str3); // Выведет: true
        // Теперь str1 и str3 ссылаются на одну и ту же строку в пуле строк
        System.out.println(str1 == str3); // Выведет: true
    }
}

