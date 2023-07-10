package com.example.amigojpa;

import com.example.amigojpa.entity.*;
import com.example.amigojpa.repository.BookRepository;
import com.example.amigojpa.repository.StudentIdCardRepository;
import com.example.amigojpa.repository.StudentRepository;
import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class AmigoJpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(AmigoJpaApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository,
                                        StudentIdCardRepository studentIdCardRepository,
                                        BookRepository bookRepository) {
        return args -> {
            Faker faker = new Faker();

            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            String email = String.format("%s.%s@mail.com", firstName,lastName);

            Student student = new Student(
                    firstName,
                    lastName,
                    email,
                    faker.number().numberBetween(20, 55));

            System.out.println("*************Add books!*************");

            student.addBook(
                    new Book("Clean Code", LocalDateTime.now().minusDays(4)));

            student.addBook(
                    new Book("Think and Grow Rich", LocalDateTime.now()));

            student.addBook(
                    new Book("Spring Data JPA", LocalDateTime.now().minusYears(1)));


            System.out.println("*************Add Student Card!*************");
            StudentIdCard studentIdCard = new StudentIdCard(
                    "13456789"
                    , student);

            student.setStudentIdCard(studentIdCard);




                //not needed because we save the studentCard via the student
//            studentIdCardRepository.save(studentIdCard);


            System.out.println("**********Enroll in course**********");
//
            student.addEnrolment(
                    new Enrolment(
                            new EnrolmentId(1L, 1L),
                            student,
                            new Course("Computer science","IT"),
                            LocalDateTime.now()
                    )
            );

            student.addEnrolment(
                    new Enrolment(
                            new EnrolmentId(1L, 1L),
                            student,
                            new Course("Mathematics", "Science"),
                            LocalDateTime.now()
                    )
            );



//            student.addEnrolment(
//                    new Enrolment(student, new Course("Computer science","IT")
//                    )
//            );
//
//            student.addEnrolment(
//                    new Enrolment(student, new Course("Mathematics","Science")
//                    )
//            );


            studentRepository.save(student);







             // 4 / 4
//
//            student.enrollToCourse(
//                    new Course("Computer science","IT")
//            );
//
//            student.enrollToCourse(
//                    new Course("Mathematics","Science")
//            );
//
//            studentRepository.save(student);
//
//
//            student.unEnrollToCourse(student.getCourses().get(1));

//            studentRepository.save(student);







//            studentIdCardRepository.findById(1L)
//                    .ifPresent(
//                    System.out::println
//            );

//            studentRepository.deleteById(1L);
        };
    }


    private static void sorting(StudentRepository studentRepository) {
        // "firstName" is the java object
//            Sort sort = Sort.by(Sort.Direction.DESC, "firstName");
        Sort sort = Sort.by( "firstName").ascending().and(Sort.by("age").descending());
        studentRepository.findAll(sort)
                .forEach(student -> System.out.println(student.getFirstName() + " " + student.getAge()));
    }

    private static void generateRandomStudents(StudentRepository studentRepository) {
        Faker faker = new Faker();

        for (int i = 0; i < 20; i++) {
            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            String email = String.format("%s.%s@mail.com", firstName,lastName);

            Student student = new Student(firstName, lastName, email, faker.number().numberBetween(20, 55));

            studentRepository.save(student);

        }
    }


}
