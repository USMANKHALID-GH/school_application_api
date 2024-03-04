package com.zalisoft.zalisoft.service;

import com.zalisoft.zalisoft.dto.CoursesDto;
import com.zalisoft.zalisoft.model.Courses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CourseService {



    Courses save(Long departmentId,CoursesDto coursesDto);

    void delete(Long id);
    Courses findById(Long id);

    void update(CoursesDto courses, Long id);

    void deleteByAdmin(Long id);

    Page<Courses> search(String search, Pageable pageable);

    Page<Courses> findCourseByDepartment(String department,Pageable pageable);
}
