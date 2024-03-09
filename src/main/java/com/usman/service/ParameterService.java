package com.usman.service;


import com.usman.dto.ParameterDto;
import com.usman.model.Parameter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ParameterService {

    List<Parameter> getAllParameters();

    Page<Parameter> search(String searchKey, Pageable pageable);

    List<Parameter> findParameterByKeyContains(String searchKey);

    Parameter create(ParameterDto parameterDto);

    Parameter findById(Long id);

    String getValueByKey(String key);

    Parameter update(Long id, ParameterDto parameterDto);

    void delete(Long id);

    Parameter findRegistrationDate();

    Parameter getGpa();

    Parameter getLANGUAGE();

    Parameter getAlex();
}
