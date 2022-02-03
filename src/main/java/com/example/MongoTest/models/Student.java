package com.example.MongoTest.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document
public class Student {
    @Id
    private Long studentId;
    @Field(name = "first_name")
    @TextIndexed
    private String firstName;
    private String lastName;
    @Indexed(unique = true)
    private String email;
    private Address address;
    @TextIndexed
    private String note;
}
