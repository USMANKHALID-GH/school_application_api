package com.zalisoft.zalisoft.service.impl;

import com.zalisoft.zalisoft.dto.PersonalDto;
import com.zalisoft.zalisoft.dto.UserInformationDto;
import com.zalisoft.zalisoft.exception.BusinessException;
import com.zalisoft.zalisoft.mapper.UserInformationMapper;
import com.zalisoft.zalisoft.model.Personal;
import com.zalisoft.zalisoft.model.User;
import com.zalisoft.zalisoft.repository.PersonalRepository;
import com.zalisoft.zalisoft.service.DepartmentService;
import com.zalisoft.zalisoft.service.PersonalService;
import com.zalisoft.zalisoft.service.UserGeneralService;
import com.zalisoft.zalisoft.service.UserService;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public  class PersonalServiceImpl implements PersonalService {

    @Autowired
    private PersonalRepository repository;

    @Autowired
    private UserService userService;

    @Autowired
    private DepartmentService departmentService;


    @Override
    public void savePersonal(PersonalDto personalDto,long departmentId) {
        var personal= new Personal();
        var infor=userService.userValidation(personalDto.getUserInformation());
        findByPhoneNumber(infor.getPhone());
        personal.setUserInformation(infor);
        personal.setDepartment(departmentService.findById(departmentId));
        personal.setUser(userService.registerPersonal(personalDto.getUser()));
        repository.save(personal);
    }

    @Override
    public void updatepersonal(UserInformationDto dto) {
        var personal= findPersonalByUser(userService.getCurrentUser());
        var infor=userService.updateUserInformation(personal.getUserInformation(),dto);
        findByPhoneNumber(infor.getPhone());
        personal.setUserInformation(infor);
        repository.save(personal);
    }

    @Override
    public Page<Personal> getPersonalsByFaculty(Pageable pageable,Long facilityId)
    {
        return repository.getPersonalsByFaculty(pageable,facilityId);
    }

    @Override
    public Page<Personal> search(String search, Pageable pageable) {
        return repository.search(search, pageable);
    }

    @Override
    public void delete(long id) {
        repository.delete(findById(id));

    }

    @Override
    public Personal findById(long id) {
        return repository.findById(id).orElseThrow(()->new BusinessException("Personal not found"));
    }

    @Override
    public void updateByAdmin(long id, UserInformationDto dto) {
        if(StringUtils.isNotEmpty(dto.getPhone()) &&repository.findPersonalByPhone(dto.getPhone()).isPresent()){
        var oldPersonal=findById(id);
        oldPersonal.setUserInformation(userService.updateUserInformation(oldPersonal.getUserInformation(),dto));
        repository.save(oldPersonal);}
        else
            throw new BusinessException("Phone number already exists");


    }

    @Override
    public Personal findByEmailOrPhoneOrTc(String search) {
        return repository.findByEmailOrPhoneorTc(search)
                .orElseThrow(()->new BusinessException("Personal not found "));
    }

    @Override
    public Personal findByCurrentUser() {
        return repository.findByCurrentUser(userService.getCurrentUser())
                .orElseThrow(()->new BusinessException("Personal not found"));

    }

    @Override
    public Page<Personal> findAllDepartmentPersonal(Pageable pageable) {
        var personal=findByCurrentUser();
        return repository.findAllDepartmentPersonal(personal.getDepartment().getId(), pageable);
    }

    private Personal findPersonalByUser(User user) {
        return repository.findByUser(user).orElseThrow(()->new BusinessException("Personal not found"));

    }

    private void findByPhoneNumber(String phoneNumber) {
        repository.findPersonalByPhone(phoneNumber).orElseThrow(()->new BusinessException("Phone Number already exists"));
    }

}
