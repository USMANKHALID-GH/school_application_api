package com.usman.controller;

import com.usman.dto.BaseResponseDto;
import com.usman.dto.DepartmentDto;
import com.usman.mapper.DepartmentMapper;
import com.usman.model.Department;
import com.usman.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class DepartmentController {

    @Autowired
    private DepartmentService service;

    @Autowired
    private DepartmentMapper mapper;


    @GetMapping("/public/department")
    public ResponseEntity<Page<DepartmentDto>> search(@RequestParam(value = "searchKey", required = false) String searchKey, Pageable pageable) {
        Page<Department> searchResult = service.search(searchKey, pageable);
        return ResponseEntity.ok(new PageImpl<>(mapper.toDto(searchResult.getContent()), pageable, searchResult.getTotalElements()));
    }

    @PostMapping("/super/department/faculty/{facultyId}")
    public ResponseEntity<DepartmentDto> create(@RequestBody DepartmentDto departmentDto,@PathVariable  Long facultyId ) {
        return ResponseEntity.ok(mapper.toDto(service.save(departmentDto,facultyId)));
    }

    @GetMapping("/all/department/{id}")
    public ResponseEntity<DepartmentDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(mapper.toDto(service.findById(id)));
    }

    @PutMapping("/super/department/{id}")
    public ResponseEntity<BaseResponseDto> update(@PathVariable Long id, @RequestBody DepartmentDto departmentDto) {
        service.update(id, departmentDto);
        return ResponseEntity.ok(BaseResponseDto.builder().message("Department update successfully").build());
    }

    @DeleteMapping("/super/department/{id}")
    public ResponseEntity<BaseResponseDto> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(BaseResponseDto.builder().message("Department deleted successfully").build());
    }
}
