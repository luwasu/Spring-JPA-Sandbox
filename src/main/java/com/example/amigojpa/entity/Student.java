package com.example.amigojpa.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.SEQUENCE;


@Data
@NoArgsConstructor
@Entity(name = "Student")
@Table(
        name = "student",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "student_email_unique",
                        columnNames = "email")
        }
)
public class Student {

    @Id
    @SequenceGenerator(
            name = "student_sequence",
            sequenceName = "student_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "student_sequence"
    )
    @Column(name = "id",
            updatable = false)
    private Long id;

    @Column(name = "first_name",
            nullable = false,
            columnDefinition = "TEXT")
    private String firstName;

    @Column(name = "last_name",
            nullable = false,
            columnDefinition = "TEXT")
    private String lastName;

    @Column(name = "email",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String email;

    @Column(name = "age",
            nullable = false)
    private Integer age;

    // This forms a Bi-directional (<->) relationship
    // when you load the student you will load the card
    @OneToOne(
            mappedBy = "student",
            cascade = CascadeType.ALL,
            // This will delete the card when the student is deleted
            // always be careful when you delete data!!!
            orphanRemoval = true)
    @ToString.Exclude
    private StudentIdCard studentIdCard;

    // This forms a Bi-directional (<->) relationship
    // when you load the student you will load the book
    @OneToMany(
            mappedBy = "student",
            // This will delete the card when the student is deleted
            // always be careful when you delete data!!!
            orphanRemoval = true,
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE}
    )
    @ToString.Exclude
    private final List<Book> books = new ArrayList<>();

// 1 / 4 Only needed if you make the join table automatically
//    @ManyToMany(
//            cascade = {CascadeType.PERSIST, CascadeType.REMOVE}
//    )
//    @JoinTable(
//            name = "enrolment",
//            joinColumns = @JoinColumn(
//                    name = "student_id",
//                    foreignKey = @ForeignKey(name = "enrolment_student_id_fk")
//            ),
//            inverseJoinColumns = @JoinColumn(
//                    name = "course_id",
//                    foreignKey = @ForeignKey(name = "enrolment_course_id_fk")
//            )
//    )
//    @ToString.Exclude
//    private List<Course> courses = new ArrayList<>();


    // Manually making the joinTable with Enrolment.class

    @OneToMany(
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            mappedBy = "student", // refers to the student inside of Enrolment.
            orphanRemoval = true
    )
    @ToString.Exclude
    private List<Enrolment> enrolments = new ArrayList<>();



    public Student(String firstName, String lastName, String email, Integer age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.age = age;
    }

    // This is needed to make sure the Book table stays in-sync
    public void addBook(Book book) {
        if (!books.contains(book)) {
            books.add(book);
            book.setStudent(this);
        }
    }

    // This is needed to make sure the Book table stays in-sync
    public void removeBook(Book book) {
        if (this.books.contains(book)) {
            this.books.remove(book);
            book.setStudent(null);
        }
    }


    // 2 / 4
//    public void enrollToCourse(Course course) {
//        courses.add(course);
//        course.getStudents().add(this);
//    }
//
//    public void unEnrollToCourse(Course course) {
//        courses.remove(course);
//        course.getStudents().remove(this);
//    }

    public void addEnrolment(Enrolment enrolment) {
        if (!enrolments.contains(enrolment)) {
            enrolments.add(enrolment);
        }
    }

    public void removeEnrolment (Enrolment enrolment) {
        enrolments.remove(enrolment);
    }


}






