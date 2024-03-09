package com.usman.service.impl;

import com.usman.dto.DepartmentDto;
import com.usman.exception.BusinessException;
import com.usman.model.Department;
import com.usman.repository.DepartmentRepository;
import com.usman.service.DepartmentService;

import com.usman.service.FacultyService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public  class DepartmentSerivceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository repository;

    @Autowired
    private FacultyService facultyService;
    @Override
    public Department save(DepartmentDto dto, long facultyId) {

        var faculty = facultyService.findById(facultyId);
        if(StringUtils.isEmpty(dto.getName())){
            throw  new BusinessException("Department name cannot be empty");
        }
        findUniqueName(dto.getName());
        var department = new Department();
        department.setCode(dto.getCode());
        department.setName(dto.getName());
        department.setFaculty(faculty);
        return repository.save(department);
    }

    @Override
    public Department findById(Long id) {
        return repository.findById(id).orElseThrow(()-> new BusinessException("Department not found"));
    }

    @Override
    public Page<Department> search(String search, Pageable pageable) {
        return repository.search(search, pageable);
    }

    @Override
    public void delete(Long id) {
        var department = findById(id);
        if(repository.departmentHasUser(id).isPresent()){
           throw  new BusinessException("Department cannot be deleted there are still students and personal");
        }
        repository.delete(department);
    }

    @Override
    public void update(Long id, DepartmentDto dto) {
    var department=findById(id);
    if(!StringUtils.isEmpty(dto.getName())){
        findUniqueName(dto.getName());
        department.setName(dto.getName());
    }


     repository.save(department);
    }

    private  void findUniqueName(String name){
        if(repository.findDepartmentByNameContainingIgnoreCase(name).isPresent()){
            throw  new BusinessException("Department already exists");
        }

    }
}
