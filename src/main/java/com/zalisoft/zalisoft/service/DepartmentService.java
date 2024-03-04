package com.zalisoft.zalisoft.service;

import com.zalisoft.zalisoft.dto.DepartmentDto;
import com.zalisoft.zalisoft.model.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DepartmentService {

    Department  save(DepartmentDto dto, long facultyId);

    Department findById(Long id);


    Page<Department> search(String search, Pageable pageable);

    void delete(Long id);

    void update(Long id, DepartmentDto dto);
}
