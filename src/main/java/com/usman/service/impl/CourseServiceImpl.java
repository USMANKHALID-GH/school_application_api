package com.usman.service.impl;

import com.usman.dto.CoursesDto;
import com.usman.exception.BusinessException;
import com.usman.model.Courses;
import com.usman.repository.CoursesRepository;
import com.usman.service.CourseService;
import com.usman.service.DepartmentService;
import com.usman.service.PersonalService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service

public class CourseServiceImpl implements CourseService {

    @Autowired
    private PersonalService personalService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private CoursesRepository repository;




    @Override
    public Courses save(Long departmentId, CoursesDto coursesDto) {
        var personal= personalService.findByCurrentUser();
        var department = departmentService.findById(departmentId);
        if(repository.checkIfUserPartOfDepartment(personal.getId())){
            if(StringUtils.isEmpty(coursesDto.getName())){
                throw new BusinessException("Course name must not be empty");
            }
            if(StringUtils.isEmpty(coursesDto.getCode())){
                throw new BusinessException("Course Code must not be empty");
            }

            var courses =new Courses();
            courses.setCode(coursesDto.getCode());
            courses.setName(coursesDto.getName());
            courses.setDepartment(department);
            return repository.save(courses);
        }

      else
          throw new BusinessException("You are not eligible to do this operation");
    }

    @Override
    public void delete(Long id) {

       var personal= personalService.findByCurrentUser();
       var course= repository.checkIfUserPartOfDepartment(personal.getId(),id);
       if(course.isPresent()){
           repository.delete(course.get());
       }

    }

    @Override
    public Courses findById(Long id) {
        return repository.findById(id)
                .orElseThrow(()-> new BusinessException("Course not found"));
    }

    @Override
    public void update(CoursesDto courses, Long id) {

    }

    @Override
    public void deleteByAdmin(Long id) {
        repository.delete(findById(id));
    }

    @Override
    public Page<Courses> search(String search, Pageable pageable) {
        var personal= personalService.findByCurrentUser();
        return repository.search(search, personal.getId(), pageable);
    }

    @Override
    public Page<Courses> findCourseByDepartment(String department,Pageable pageable) {
        return repository.findCourseByDepartment(department,pageable);
    }

}
