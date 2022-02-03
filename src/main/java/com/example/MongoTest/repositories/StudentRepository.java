package com.example.MongoTest.repositories;

import com.example.MongoTest.models.Student;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends MongoRepository<Student,Long> {

    public List<Student> findStudentsByFirstName(String firstName);

    public List<Student> findByFirstName(String name, TextCriteria criteria);

    @Query(
            value = "{ firstName: ?0 } ",
            fields = "{email: 1, _id: 0}"
    )
    public List<String> findEmailFromName(String name);
}
