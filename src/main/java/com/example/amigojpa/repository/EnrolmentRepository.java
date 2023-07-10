package com.example.amigojpa.repository;

import com.example.amigojpa.entity.Enrolment;
import com.example.amigojpa.entity.EnrolmentId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnrolmentRepository extends JpaRepository<Enrolment, EnrolmentId> {
}
