package com.usman.service;

import com.usman.dto.PersonalDto;
import com.usman.dto.UserInformationDto;
import com.usman.model.Personal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface PersonalService {


    void savePersonal(PersonalDto personalDto, long departmentId);

    void updatepersonal(UserInformationDto dto);

    Page<Personal>  getPersonalsByFaculty(Pageable pageable, Long facultyId);

    Page<Personal>  search(String search, Pageable pageable);

    void delete(long id);

    Personal findById(long id);

    void updateByAdmin(long id, UserInformationDto dto);

    Personal findByEmailOrPhoneOrTc(String search);

    Personal findByCurrentUser();

    Page<Personal>  findAllDepartmentPersonal(Pageable pageable);


}
