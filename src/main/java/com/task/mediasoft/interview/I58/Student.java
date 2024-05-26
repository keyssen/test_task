package com.task.mediasoft.interview.I58;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student  implements Comparable<Student>{
    private Integer id;
    private String name;
    private String department;

    @Override
    public int compareTo(Student student) {
        return this.name.compareTo(student.name) + this.id.compareTo(student.id);
    }
}