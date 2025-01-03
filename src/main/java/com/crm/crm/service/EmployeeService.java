package com.crm.crm.service;

import com.crm.crm.entity.Employee;
import com.crm.crm.exception.ResourceNotFound;
import com.crm.crm.payload.EmployeeDto;
import com.crm.crm.repository.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    public EmployeeService(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

    public EmployeeDto addEmployee(EmployeeDto dto) {
        Employee employee = mapToEntity(dto);
        Employee emp = employeeRepository.save(employee);
        EmployeeDto employeeDto = mapToDto(emp);

        return employeeDto;
    }

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    public void updateEmployee(Long id, EmployeeDto dto) {
        Optional<Employee> opEmp = employeeRepository.findById(id);
        if (opEmp.isPresent()) {
            Employee employee = opEmp.get();
            employee.setName(dto.getName());
            employee.setEmailId(dto.getEmailId());
            employee.setMobile(dto.getMobile());
            employeeRepository.save(employee);
        }
    }

    public List<EmployeeDto> getEmployee(int pageSize, int pageNo, String sortBy) {
        Pageable page = PageRequest.of(pageNo,pageSize, Sort.by(sortBy));
        Page<Employee> all = employeeRepository.findAll(page);
        List<Employee> employees = all.getContent();

        List<EmployeeDto> dto = employees.stream()
                .map(e->mapToDto(e))
                .collect(Collectors.toList());
        return dto;
    }

    private EmployeeDto mapToDto(Employee employee) {
        return modelMapper.map(employee, EmployeeDto.class);
    }

    private Employee mapToEntity(EmployeeDto dto) {
        return modelMapper.map(dto, Employee.class);
    }
    public EmployeeDto getEmployeesById(long empId) {
        Employee employee = employeeRepository.findById(empId).orElseThrow(
                () -> new ResourceNotFound("Employee not found with id " + empId)
        );
        EmployeeDto dto = mapToDto(employee);
        return dto;
    }
}



