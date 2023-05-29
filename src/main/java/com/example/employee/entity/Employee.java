package com.example.employee.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "employee")
public class Employee {

    @Id
    private Integer id;

    private String name;

    @Column(unique = true)
    private Integer aadhar;

    private Integer age;

    private String department;

    private String city;

    private LocalDate date_of_birth;
}
