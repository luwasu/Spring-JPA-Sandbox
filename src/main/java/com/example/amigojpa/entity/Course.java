package com.example.amigojpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.SEQUENCE;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name = "Course")
@Table(name = "course")
public class Course {

    @Id
    @SequenceGenerator(
            name = "course_sequence",
            sequenceName = "course_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "course_sequence"
    )
    @Column(name = "id",
            updatable = false)
    private Long id;

    @Column(name = "name",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String name;

    @Column(name = "department",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String department;

// 3 / 4
//    @ManyToMany(
//            mappedBy = "courses"
//    )
//    @ToString.Exclude
//    private List<Student> students = new ArrayList<>();


    @OneToMany(
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            mappedBy = "course", // refers to the student inside of Enrolment.
            orphanRemoval = true
    )
    @ToString.Exclude
    private List<Enrolment> enrolments = new ArrayList<>();


    public Course(String name, String department) {
        this.name = name;
        this.department = department;
    }

    public void addEnrolment(Enrolment enrolment) {
        if (!enrolments.contains(enrolment)) {
            enrolments.add(enrolment);
        }
    }

    public void removeEnrolment (Enrolment enrolment) {
        enrolments.remove(enrolment);
    }
}
