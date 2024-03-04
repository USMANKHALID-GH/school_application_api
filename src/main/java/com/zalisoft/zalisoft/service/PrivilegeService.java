package com.zalisoft.zalisoft.service;

import com.zalisoft.zalisoft.dto.PrivilegeDto;
import com.zalisoft.zalisoft.model.Privilege;

import java.util.List;

public interface PrivilegeService {
    List<Privilege> findAll();

    Privilege findById(Long id);

    List<Privilege> findAllById(List<Long> ids);

    Privilege create(Privilege privilege);

    Privilege update(Long id, PrivilegeDto privilegeDto);

    void deleteById(Long id);
}
