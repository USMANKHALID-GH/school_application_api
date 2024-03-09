package com.usman.controller;

import com.usman.dto.BaseResponseDto;
import com.usman.dto.CoursesDto;
import com.usman.mapper.CourseMapper;
import com.usman.model.Courses;
import com.usman.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CourseController {

    @Autowired
    private CourseService service;

    @Autowired
    private CourseMapper mapper;


    @GetMapping("/admin/course")
    public ResponseEntity<Page<CoursesDto>> search(@RequestParam(value = "searchKey", required = false) String searchKey, Pageable pageable) {
        Page<Courses> searchResult = service.search(searchKey, pageable);
        return ResponseEntity.ok(new PageImpl<>(mapper.toDto(searchResult.getContent()), pageable, searchResult.getTotalElements()));
    }

    @GetMapping("/public/course")
    public ResponseEntity<Page<CoursesDto>> findCourseByDepartment(@RequestParam String searchKey, Pageable pageable) {
        Page<Courses> searchResult = service.findCourseByDepartment(searchKey, pageable);
        return ResponseEntity.ok(new PageImpl<>(mapper.toDto(searchResult.getContent()), pageable, searchResult.getTotalElements()));
    }

    @PostMapping("/admin/course/department/{departmentId}")
    public ResponseEntity<CoursesDto> create(@RequestBody CoursesDto coursesDto, @PathVariable long departmentId) {
        return ResponseEntity.ok(mapper.toDto(service.save(departmentId, coursesDto)));
    }

    @GetMapping("/all/course/{id}")
    public ResponseEntity<CoursesDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(mapper.toDto(service.findById(id)));
    }

    @PutMapping("/admin/course/{id}")
    public ResponseEntity<BaseResponseDto> update(@PathVariable Long id, @RequestBody CoursesDto coursesDto) {
        service.update(coursesDto,id);
        return ResponseEntity.ok(BaseResponseDto.builder().message("Course update successfully").build());
    }

    @DeleteMapping("/admin/course/{id}")
    public ResponseEntity<BaseResponseDto> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(BaseResponseDto.builder().message("Course deleted successfully").build());
    }

    @DeleteMapping("/super/course/{id}")
    public ResponseEntity<BaseResponseDto> deleteByAdmin(@PathVariable Long id) {
        service.deleteByAdmin(id);
        return ResponseEntity.ok(BaseResponseDto.builder().message("Course deleted successfully").build());
    }

}
