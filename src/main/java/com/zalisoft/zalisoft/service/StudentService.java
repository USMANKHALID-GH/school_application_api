package com.zalisoft.zalisoft.service;

import com.uploadcare.upload.UploadFailureException;
import com.zalisoft.zalisoft.dto.RoleDto;
import com.zalisoft.zalisoft.dto.StudentAppyDto;

import com.zalisoft.zalisoft.dto.UserInformationDto;
import com.zalisoft.zalisoft.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface StudentService {

    void apply(StudentAppyDto dto, List<Map<String , MultipartFile>> file,long departmentId) throws UploadFailureException, IOException;

    void approve(long id);

    void reject(long id);


    void delete(long id);

    Student findById(long id);

    void setToPending(long id);

    void updateByStudent(UserInformationDto dto );

    void updateByAdmin(long id , UserInformationDto dto);

    Page<Student> getAppliedStudents(Pageable pageable);

    Student findByStudentNo(String no);

    Page<Student> findStudentByDepartment(Pageable pageable,String search);

    Page<Student> search(Pageable pageable,String search);

    Student  checkApplicationStatus();
}
