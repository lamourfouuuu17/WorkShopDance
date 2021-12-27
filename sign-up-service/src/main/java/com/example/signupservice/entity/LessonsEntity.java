package com.example.signupservice.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Data
@Table(schema = "workshop", name = "lessons_full_view")
public class LessonsEntity {
    @Id
    private Long id;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "name")
    private String studentName;

    @Column(name = "surname")
    private String studentSurname;

    @Column(name = "email")
    private String studentEmail;

    @Column(name = "teacher_email")
    private String teacherEmail;
}
