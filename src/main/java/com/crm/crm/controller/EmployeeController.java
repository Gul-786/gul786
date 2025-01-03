package com.crm.crm.controller;

import com.crm.crm.entity.Employee;
import com.crm.crm.payload.EmployeeDto;
import com.crm.crm.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {

    private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addEmployee(
           @Valid @RequestBody EmployeeDto dto, BindingResult result
    ) {

        if(result.hasErrors()){
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        EmployeeDto employeeDto = employeeService.addEmployee(dto);
        return new ResponseEntity<>(employeeDto,HttpStatus.CREATED);

    }

    // http://localhost:8080/api/v1/employee?id=1
    @DeleteMapping
    public String deleteEmployee(@RequestParam Long id) {
        employeeService.deleteEmployee(id);
        return "deleted";
    }

    @PutMapping
    public String updateEmployee(@RequestParam Long id, @RequestBody EmployeeDto dto) {
        employeeService.updateEmployee(id, dto);
        return "updated";


    }

    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getEmployees(
            @RequestParam(name="pageSize",required = false,defaultValue = "5")int pageSize,
             @RequestParam(name="pageNo",required = false,defaultValue = "0")int pageNo,
            @RequestParam(name="sortBy",required = false,defaultValue = "0")String sortBy )
    {
        List<EmployeeDto> employeesDto = employeeService.getEmployee(pageSize,pageNo,sortBy);
        return new ResponseEntity<>(employeesDto, HttpStatus.OK);
    }





    @GetMapping("/employeeId/{empId}")
    public ResponseEntity<EmployeeDto> getEmployeesById(
            @PathVariable long empId){

        EmployeeDto dto = employeeService.getEmployeesById(empId);

        return new ResponseEntity<>(dto, HttpStatus.OK);

    }

}
