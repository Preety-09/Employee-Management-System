package com.example.employee.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class EmployeeDTO {

    private Integer id;

    @Pattern(regexp = "[A-Za-z]+( [A-Za-z]+)*", message = "Name should be alphabets only")
    private String name;

    @Column(unique = true)
    private Integer aadhar;

    private Integer age;

    private String department;

    private String city;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @PastOrPresent(message = "Date should not be of future")
    private LocalDate date_of_birth;
}
