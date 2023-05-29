package com.example.employee.controller;

import com.example.employee.dto.EmployeeDTO;
import com.example.employee.exception.AadharAlreadyExistsException;
import com.example.employee.exception.EmployeeDoesNotExistException;
import com.example.employee.exception.EmployeeTableEmptyException;
import com.example.employee.service.EmployeeService;
import com.example.employee.utility.ResponseHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.employee.utility.Constants.EMPLOYEE_SUCCESSFULLY_DELETED;

@RestController
@Validated
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @PostMapping("/employees")
    public ResponseEntity<Object> createEmployee(@Valid @RequestBody EmployeeDTO employeeDTO)
            throws AadharAlreadyExistsException {
        Integer employeeId = employeeService.addEmployee(employeeDTO);
        String successMessage = "Employee with ID - " + employeeId + " successfully created";
        return new ResponseHandler().generateResponse(successMessage, HttpStatus.CREATED);
    }

    @GetMapping("/employees")
    public ResponseEntity<List<EmployeeDTO>> fetchAllEmployees() throws EmployeeTableEmptyException {
        List<EmployeeDTO> employeeList = employeeService.fetchAllEmployees();
        return new ResponseEntity<>(employeeList, HttpStatus.OK);
    }

    @GetMapping("/employee/{aadhar}")
    public ResponseEntity<EmployeeDTO> fetchEmployeeByAadhar(@PathVariable Integer aadhar)
            throws EmployeeDoesNotExistException {
        EmployeeDTO employeeFetchedByAadhar = employeeService.fetchEmployeeByAadhar(aadhar);
        return new ResponseEntity<>(employeeFetchedByAadhar, HttpStatus.OK);
    }

    @PutMapping("/employee/{aadhar}")
    public ResponseEntity<Object> updateEmplyeeByAadhar(@PathVariable Integer aadhar,
                                                        @Valid @RequestBody EmployeeDTO employee)
            throws EmployeeDoesNotExistException {
        Integer employeeId = employeeService.updateEmployee(aadhar, employee.getDepartment());
        String updateMessage = "Department successfully updated for employee ID - " + employeeId;
        return new ResponseHandler().generateResponse(updateMessage, HttpStatus.OK);
    }

    @DeleteMapping("/employee/{aadhar}")
    public ResponseEntity<Object> deleteEmployeeByAadhar(@PathVariable Integer aadhar) throws EmployeeDoesNotExistException {
        employeeService.deleteEmployee(aadhar);
        return new ResponseHandler().generateResponse(EMPLOYEE_SUCCESSFULLY_DELETED, HttpStatus.OK);
    }

}
