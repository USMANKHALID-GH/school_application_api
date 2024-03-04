package com.zalisoft.zalisoft.service.impl;

import com.uploadcare.upload.UploadFailureException;
import com.zalisoft.zalisoft.dto.*;

import com.zalisoft.zalisoft.enums.ApplicationStatus;
import com.zalisoft.zalisoft.exception.BusinessException;
import com.zalisoft.zalisoft.model.*;
import com.zalisoft.zalisoft.repository.StudentRepository;
import com.zalisoft.zalisoft.service.*;
import com.zalisoft.zalisoft.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.zalisoft.zalisoft.util.RandomUtil.generateStudentNumber;

@Service
@Slf4j
public class StudentServiceImpl implements StudentService {


    @Autowired
    private StudentRepository repository;

    @Autowired
    private UserService userService;

    @Autowired
    private PersonalService personalService;

    @Autowired
    private FileUploadService fileUploadService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private ParameterService parameterService;

    @Autowired
    private ScoreServiceImpl scoreService;

    private boolean checkDateRange(Parameter parameter){
        var startDate= DateUtil.getLocalDateTime(parameter.getValue());
        var endDate= DateUtil.getLocalDateTime(parameter.getValue2());
        LocalDateTime today=LocalDateTime.now();
        return (today.isAfter(startDate) && today.isBefore(endDate));
    }

    @Override
    public void apply(StudentAppyDto dto, List<Map<String, MultipartFile>> file,long departmentId) throws UploadFailureException, IOException {
        var dateRange= parameterService.findRegistrationDate();

        Documents documents = new Documents();
        try {
            if(checkDateRange(dateRange)) {

                Department department = departmentService.findById(departmentId);
                file.stream()
                        .flatMap(map -> map.entrySet().stream())
                        .forEach(entry ->
                        {
                            var name = entry.getKey();
                            try {
                                switch (name) {
                                    case "diploma" -> documents.setCertificateUrl(fileUploadService.uploadFile(entry.getValue()));
                                    case "english" -> documents.setEnglishUrl(fileUploadService.uploadFile(entry.getValue()));
                                    case "transcript" -> documents.setTranscriptUrl(fileUploadService.uploadFile(entry.getValue()));
                                    case "image" -> documents.setUserImageUrl(fileUploadService.uploadFile(entry.getValue()));
                                    default -> throw new BusinessException("");
                                }

                            } catch (UploadFailureException e) {
                                throw new RuntimeException(e);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        });
                var student = new Student();
                student.setScore(scoreService.getScore(dto.getScore()));
                student.setUserInformation(userService.userValidation(dto.getUserInformation()));
                student.setDocuments(documents);
                student.setDepartment(department);
                student.setUser(userService.registerStudent(dto.getUser()));
                repository.save(student);
            }
            else {
                log.info("else inside: {}",checkDateRange(dateRange));
                throw new BusinessException("Application is not within the specified date");
            }
        }catch (HibernateError e){
            userService.deleteByEmail(dto.getUser().getEmail());
            fileUploadService.deleteFile(documents.getEnglishUrl());
            fileUploadService.deleteFile(documents.getCertificateUrl());
            fileUploadService.deleteFile(documents.getCertificateUrl());
            fileUploadService.deleteFile(documents.getUserImageUrl());
            throw new BusinessException("An unexpected thing happened while applying: "+e.getMessage());
        }

    }


    @Override
    public void approve(long id) {
      Student student=findById(id);
      var roleDto=new RoleDto();
        roleDto.setName("STUDENT");
       var prvil= new PrivilegeDto();
       prvil.setName("STUDENT");
        roleDto.setPrivileges(Set.of(prvil));

        String studentNo=generateStudentNumber();
        if(!repository.checkIfStudentNumberExist(studentNo).isPresent()){
            student.setStudentNumber(studentNo);
        }
        else {
            student.setStudentNumber(studentNo.substring(0,5)+String.format("%05d", +student.getId()));
        }
        student.getScore().setDeleted(true);
        var userDto=new UserDto();
        userDto.setRoles(new HashSet<>(List.of(roleDto)));
        userService.updateUserByAdmin(student.getUser().getId(),userDto);
         student.setApplicationStatus(ApplicationStatus.APPROVED);
        repository.save(student);
    }

    @Override
    public void reject(long id) {
        var student=findById(id);
        student.setApplicationStatus(ApplicationStatus.REJECTED);
        repository.delete(student);

    }

    @Override
    public void setToPending(long id) {
        var student=findById(id);
        student.setApplicationStatus(ApplicationStatus.PENDING);
        repository.delete(student);
    }


    @Override
    public void delete(long id) {
        var student=findById(id);
        student.getDocuments().setDeleted(true);
        student.getScore().setDeleted(true);
        userService.deactivateUser(student.getUser().getId());
        delete(id);

    }


    @Override
    public Student findById(long id) {
        return repository.findById(id).orElseThrow(()->new BusinessException("Student Not Found: " + id));
    }

    @Override
    public void updateByStudent(UserInformationDto dto) {
        var student= repository.findByUser(userService.getCurrentUser()).get();
        student.setUserInformation(userService.updateUserInformation(dto));
        repository.save(student);
    }

    @Override
    public void updateByAdmin(long id, UserInformationDto dto) {
        var person= personalService.findByCurrentUser();
        var student= repository.findStudentByDepartmentPersonal(person.getId(),id);
        if(student.isPresent()){
            student.get().setUserInformation(userService.updateUserInformation(dto));
            repository.save(student.get());
        }
    else
        throw new BusinessException("");

    }

    @Override
    public Page<Student> getAppliedStudents(Pageable pageable) {
        var person= personalService.findByCurrentUser();
        log.info("person: {}",person.toString());
        return  repository.findStudentByTheirScores(pageable,person.getDepartment().getId());
    }

    @Override
    public Student findByStudentNo(String no) {
        return repository.findUserByStudentNo(no).orElseThrow(()-> new BusinessException("Student not found with: "+no));
    }

    @Override
    public Page<Student> findStudentByDepartment(Pageable pageable, String search) {
        var person= personalService.findByCurrentUser();
        return repository.findStudentByDepartment(pageable,person.getDepartment().getId(), search);
    }

    @Override
    public Page<Student> search(Pageable pageable, String search) {
        return repository.search(pageable,search);
    }

    @Override
    public Student checkApplicationStatus() {
        Student student  =repository.findByUser(userService.getCurrentUser()).get();
        return findById(student.getId());
    }


}
