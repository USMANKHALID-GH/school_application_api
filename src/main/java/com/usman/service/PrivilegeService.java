package com.usman.service;

import com.usman.dto.PrivilegeDto;
import com.usman.model.Privilege;

import java.util.List;

public interface PrivilegeService {
    List<Privilege> findAll();

    Privilege findById(Long id);

    List<Privilege> findAllById(List<Long> ids);

    Privilege create(Privilege privilege);

    Privilege update(Long id, PrivilegeDto privilegeDto);

    void deleteById(Long id);
}
