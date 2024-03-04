package com.zalisoft.zalisoft.controller;


import com.zalisoft.zalisoft.dto.*;
import com.zalisoft.zalisoft.mapper.StudentApplyMapper;
import com.zalisoft.zalisoft.mapper.StudentApplyShortMapper;
import com.zalisoft.zalisoft.mapper.StudentMapper;
import com.zalisoft.zalisoft.model.Student;
import com.zalisoft.zalisoft.service.StudentService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class StudentController {

    @Autowired
    private StudentService service;

    @Autowired
    private StudentMapper mapper;

    @Autowired
    private StudentApplyMapper applyMapper;

    @Autowired
    private StudentApplyShortMapper shortMapper;


    @PostMapping("/admin/student/{id}/approved")
    public ResponseEntity<BaseResponseDto> approve(@PathVariable Long id) {
        service.approve(id);
        return ResponseEntity.ok(BaseResponseDto.builder().message("Student approved successfully").build());
    }
    @PostMapping("/admin/student/{id}/rejected")
    public ResponseEntity<BaseResponseDto> reject(@PathVariable Long id) {
        service.reject(id);
        return ResponseEntity.ok(BaseResponseDto.builder().message("Student rejected successfully").build());
    }

    @DeleteMapping("/admin/student/{id}")
    public ResponseEntity<BaseResponseDto> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(BaseResponseDto.builder().message("Student deleted successfully").build());
    }

    @PostMapping("/admin/student/{id}/pending")
    public ResponseEntity<BaseResponseDto> setPending(@PathVariable Long id) {
        service.setToPending(id);
        return ResponseEntity.ok(BaseResponseDto.builder().message("Student set to pending successfully").build());
    }

    @GetMapping("/student/{id}")
    public ResponseEntity<StudentDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(mapper.toDto(service.findById(id)));
    }

    @GetMapping("/passive/student/application-status")
    public ResponseEntity<StudentApplyShortDto> checkApplicationStatus() {
        return ResponseEntity.ok(shortMapper.toDto(service.checkApplicationStatus()));
    }


    @PutMapping("/student")
    public ResponseEntity<BaseResponseDto> update(@RequestBody UserInformationDto userInformation) {
        service. updateByStudent(userInformation);
        return ResponseEntity.ok(BaseResponseDto.builder().message("Student updated successfully").build());
    }

    @PutMapping("/admin/student/{id}")
    public ResponseEntity<BaseResponseDto> updateByAdmin(@PathVariable Long id, @RequestBody UserInformationDto userInformationDto) {
        service.updateByAdmin(id, userInformationDto);
        return ResponseEntity.ok(BaseResponseDto.builder().message("Student updated successfully").build());
    }


    @GetMapping("/admin/student/applied")
    public ResponseEntity<Page<StudentApplyShortDto>> getAppliedStudents(Pageable pageable) {
        Page<Student> searchResult = service.getAppliedStudents(pageable);
        return ResponseEntity.ok(new PageImpl<>(shortMapper.toDto(searchResult.getContent()), pageable, searchResult.getTotalElements()));
    }


    @GetMapping("/admin/student-no/{no}")
    public ResponseEntity<StudentDto> findByStudentNo(@PathVariable String no) {
        return ResponseEntity.ok(mapper.toDto(service.findByStudentNo(no)));
    }

    @GetMapping("/admin/student/department")
    public ResponseEntity<Page<StudentDto>> findStudentByDepartment(Pageable pageable,@RequestParam(name = "search", required = false) String search) {
        Page<Student> searchResult = service.findStudentByDepartment(pageable,search);
        return ResponseEntity.ok(new PageImpl<>(mapper.toDto(searchResult.getContent()), pageable, searchResult.getTotalElements()));
    }


    @GetMapping("/super/student")
    public ResponseEntity<Page<StudentDto>> search(Pageable pageable,@RequestParam(name = "search", required = false) String search) {
        Page<Student> searchResult = service.search(pageable,search);
        return ResponseEntity.ok(new PageImpl<>(mapper.toDto(searchResult.getContent()), pageable, searchResult.getTotalElements()));
    }


}
