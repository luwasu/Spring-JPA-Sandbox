package com.example.amigojpa.repository;

import com.example.amigojpa.entity.StudentIdCard;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentIdCardRepository
        extends CrudRepository<StudentIdCard, Long> {

}
