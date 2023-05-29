package com.example.employee.service;

import com.example.employee.dto.EmployeeDTO;
import com.example.employee.entity.Employee;
import com.example.employee.exception.AadharAlreadyExistsException;
import com.example.employee.exception.EmployeeDoesNotExistException;
import com.example.employee.exception.EmployeeTableEmptyException;
import com.example.employee.repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.employee.utility.Constants.*;

@Service
@Transactional
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    private Integer calculateAgeBasedOnBirthDate(LocalDate birthDate) {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    private Optional<Employee> isEmployeeExists(Integer aadhar) {
        Optional<Employee> employee = Optional.ofNullable(employeeRepository.findEmployeeByAadhar(aadhar));
        return employee;
    }

    private static Employee getEmployeeIfExists(Optional<Employee> employee) throws EmployeeDoesNotExistException {
        Employee employeeFetchedByAadhar = employee.orElseThrow(() ->
                new EmployeeDoesNotExistException(EMPLOYEE_DOES_NOT_EXIST_MESSAGE));
        return employeeFetchedByAadhar;
    }

    public Integer addEmployee(EmployeeDTO employeeDTO) throws AadharAlreadyExistsException {
        Optional<Employee> employee = isEmployeeExists(employeeDTO.getAadhar());
        if (employee.isPresent()) {
            throw new AadharAlreadyExistsException(AADHAR_ALREADY_EXISTS_ERROR_MESSAGE);
        }

        LocalDate birthDate = employeeDTO.getDate_of_birth();
        Integer age = calculateAgeBasedOnBirthDate(birthDate);
        Employee newEmployee = Employee.builder()
                .id(employeeDTO.getId())
                .name(employeeDTO.getName())
                .aadhar(employeeDTO.getAadhar())
                .age(age)
                .department(employeeDTO.getDepartment())
                .city(employeeDTO.getCity())
                .date_of_birth(employeeDTO.getDate_of_birth())
                .build();
        employeeRepository.save(newEmployee);
        return newEmployee.getId();
    }

    public List<EmployeeDTO> fetchAllEmployees() throws EmployeeTableEmptyException {
        List<Employee> employees = employeeRepository.findAll();
        List<EmployeeDTO> employeeDTOList = new ArrayList<>();
        employees.forEach(employee -> {
            EmployeeDTO employeeDTO = EmployeeDTO.builder()
                    .id(employee.getId())
                    .name(employee.getName())
                    .aadhar(employee.getAadhar())
                    .age(employee.getAge())
                    .department(employee.getDepartment())
                    .city(employee.getCity())
                    .date_of_birth(employee.getDate_of_birth())
                    .build();

            employeeDTOList.add(employeeDTO);
        });

        if (employeeDTOList.isEmpty()) {
            throw new EmployeeTableEmptyException(EMPLOYEES_DOES_NOT_EXIST);
        }
        return employeeDTOList;
    }

    public EmployeeDTO fetchEmployeeByAadhar(Integer aadhar) throws EmployeeDoesNotExistException {
        Optional<Employee> employee = isEmployeeExists(aadhar);
        Employee employeeFetchedByAadhar = getEmployeeIfExists(employee);
        EmployeeDTO employeeDTOFetchedByAadhar = EmployeeDTO.builder()
                .id(employeeFetchedByAadhar.getId())
                .name(employeeFetchedByAadhar.getName())
                .aadhar(employeeFetchedByAadhar.getAadhar())
                .age(employeeFetchedByAadhar.getAge())
                .department(employeeFetchedByAadhar.getDepartment())
                .city(employeeFetchedByAadhar.getCity())
                .date_of_birth(employeeFetchedByAadhar.getDate_of_birth())
                .build();
        return employeeDTOFetchedByAadhar;
    }

    public Integer updateEmployee(Integer aadhar, String department) throws EmployeeDoesNotExistException {
        Optional<Employee> employee = isEmployeeExists(aadhar);
        Employee employeeFetchedByAadhar = getEmployeeIfExists(employee);
        employeeFetchedByAadhar.setDepartment(department);
        return employeeFetchedByAadhar.getId();
    }

    public void deleteEmployee(Integer aadhar) throws EmployeeDoesNotExistException {
        Optional<Employee> employee = isEmployeeExists(aadhar);
        Employee employeeToBeDeleted = getEmployeeIfExists(employee);
        employeeRepository.delete(employeeToBeDeleted);
    }
}
