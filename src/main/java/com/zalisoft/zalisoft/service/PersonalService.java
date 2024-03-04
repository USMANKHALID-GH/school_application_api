package com.zalisoft.zalisoft.service;

import com.zalisoft.zalisoft.dto.PersonalDto;
import com.zalisoft.zalisoft.dto.UserInformationDto;
import com.zalisoft.zalisoft.model.Personal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface PersonalService {


    void savePersonal(PersonalDto personalDto,long departmentId);

    void updatepersonal(UserInformationDto dto);

    Page<Personal>  getPersonalsByFaculty(Pageable pageable,Long facultyId);

    Page<Personal>  search(String search, Pageable pageable);

    void delete(long id);

    Personal findById(long id);

    void updateByAdmin(long id, UserInformationDto dto);

    Personal findByEmailOrPhoneOrTc(String search);

    Personal findByCurrentUser();

    Page<Personal>  findAllDepartmentPersonal(Pageable pageable);


}
