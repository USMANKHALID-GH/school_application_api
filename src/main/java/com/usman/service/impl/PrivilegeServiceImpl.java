package com.usman.service.impl;

import com.usman.dto.PrivilegeDto;
import com.usman.enums.ResponseMessageEnum;
import com.usman.exception.BusinessException;
import com.usman.model.Privilege;
import com.usman.model.Role;
import com.usman.repository.PrivilegeRepository;
import com.usman.repository.RoleRepository;
import com.usman.service.PrivilegeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Slf4j
@Service
@Transactional
public class PrivilegeServiceImpl implements PrivilegeService {

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private RoleRepository roleRepository;


    @Override
    public List<Privilege> findAll() {
        return privilegeRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    @Override
    public Privilege findById(Long id) {
        return privilegeRepository.findById(id).orElseThrow(() -> new BusinessException(ResponseMessageEnum.BACK_PRIVILEGE_MSG_001));
    }

    @Override
    public List<Privilege> findAllById(List<Long> ids) {
        return privilegeRepository.findAllById(ids);
    }

    @Override
    public Privilege create(Privilege privilege) {
        controlUniqueName(privilege.getName(), -1l);
        return privilegeRepository.save(privilege);
    }

    @Override
    public Privilege update(Long id, PrivilegeDto privilegeDto) {
        Privilege privilege = findById(id);
        if (StringUtils.isNotEmpty(privilegeDto.getName())) {
            controlUniqueName(privilegeDto.getName(), id);
            privilege.setName(privilegeDto.getName());
        }
        return privilegeRepository.save(privilege);
    }

    @Override
    public void deleteById(Long id) {
        Privilege privilege = findById(id);
        List<Role> assignedRoles = roleRepository.findAllByPrivileges(privilege);
        if (!CollectionUtils.isEmpty(assignedRoles)) {
            throw new BusinessException(ResponseMessageEnum.BACK_PRIVILEGE_MSG_002);
        }
        privilegeRepository.deleteById(id);
    }

    private void controlUniqueName(String name, Long id) {
        if (privilegeRepository.existsByNameAndIdNot(name, id)) {
            throw new BusinessException(ResponseMessageEnum.BACK_PRIVILEGE_MSG_003);
        }
    }
}