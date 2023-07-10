package com.example.amigojpa.repository;

import com.example.amigojpa.entity.Student;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {


    // will generate a SQL query for this method
    Optional<Student> findStudentByEmail(String email);

    List<Student> findStudentsByFirstNameEqualsAndAgeIsGreaterThanEqual(String firstName, Integer age);


    // Uses the "Student" from the @Entity("Student) and uses JPQL
    @Query(value = "SELECT s FROM Student s WHERE s.email = ?1")
    Optional<Student> findStudentByEmailJPQL(String email);


    @Query(value = "SELECT s FROM Student s WHERE s.firstName = ?1 AND s.age >= ?2")
    List<Student> findStudentsByFirstNameEqualsAndAgeIsGreaterThanEqualJPQL(String firstName, Integer age);



    // If you use native SQL it can break if you change the DB to a different type. Ex. postgreSQL to MySQL
    @Query(value = "SELECT * FROM student WHERE first_name = :firstName AND age >= :age" , nativeQuery = true)
    // @Param can be used to map variables to the query.
    List<Student> findStudentsByFirstNameEqualsAndAgeIsGreaterThanEqualNativeSQL(
            @Param(value = "firstName") String firstName,
            @Param(value = "age") Integer age);


    // Indicates a query method should be considered as modifying query as that changes the way it
    // needs to be executed. This annotation is only considered if used on query methods defined
    // through a Query annotation. It's not applied on custom implementation methods or queries
    // derived from the method name as they already have control over the underlying data access
    // APIs or specify if they are modifying by their name.
    // Queries that require a `@Modifying` annotation include INSERT, UPDATE, DELETE, and DDL statements.
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Student u WHERE u.id = ?1")
    int deleteStudentById(Long id);
}
