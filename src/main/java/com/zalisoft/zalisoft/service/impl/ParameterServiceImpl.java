package com.zalisoft.zalisoft.service.impl;

import com.zalisoft.zalisoft.config.CacheConfig;
import com.zalisoft.zalisoft.constant.ParameterConstant;
import com.zalisoft.zalisoft.dto.ParameterDto;
import com.zalisoft.zalisoft.enums.ResponseMessageEnum;
import com.zalisoft.zalisoft.exception.BusinessException;
import com.zalisoft.zalisoft.mapper.ParameterMapper;
import com.zalisoft.zalisoft.model.Parameter;
import com.zalisoft.zalisoft.repository.ParameterRepository;
import com.zalisoft.zalisoft.service.ParameterService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Slf4j
@Transactional
@Service("parameterService")
public class ParameterServiceImpl implements ParameterService {

    @Autowired
    private ParameterRepository repository;

    @Autowired
    private ParameterMapper mapper;

    @Override
    public List<Parameter> getAllParameters() {
        return repository.findAll();
    }

    @Override
    public Page<Parameter> search(String searchKey, Pageable pageable) {
        return repository.search(searchKey, pageable);
    }

    @Override
    public List<Parameter> findParameterByKeyContains(String searchKey) {
        return repository.findParameterByKeyContainsOrderByIdAsc(searchKey);
    }

    @Override
    public Parameter create(ParameterDto parameterDto) {
        Parameter parameter = repository.findByKey(parameterDto.getKey());
        if (parameter != null) {
            throw new BusinessException(ResponseMessageEnum.BACK_PARAMETER_MSG_005);
        }
        parameter = repository.save(mapper.toEntity(parameterDto));
        return parameter;
    }

    @Override
    public Parameter findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new BusinessException(ResponseMessageEnum.BACK_PARAMETER_MSG_001));
    }

    @Override
    @Cacheable(cacheNames = CacheConfig.CACHE_ONE_MINUTE, keyGenerator = "keyGenerator", unless = CacheConfig.UNLESS_RESULT_NULL)
    public String getValueByKey(String key) {
        Parameter parameter = repository.findByKey(key);
        return parameter != null ? parameter.getValue() : null;
    }

    @Override
    public Parameter update(Long id, ParameterDto parameterDto) {
        Parameter parameter = findById(id);
        if (StringUtils.isNotEmpty(parameterDto.getKey())) {
            parameter.setKey(parameterDto.getKey());
        }
        if (StringUtils.isNotEmpty(parameterDto.getValue())) {
            parameter.setValue(parameterDto.getValue());
        }
        if (StringUtils.isNotEmpty(parameterDto.getDescription())) {
            parameter.setDescription(parameterDto.getDescription());
        }
        repository.save(parameter);
        return parameter;
    }

    @Override
    public void delete(Long id) {
        Parameter parameter = findById(id);
        repository.deleteById(parameter.getId());
    }

    @Override
    public Parameter findRegistrationDate() {
        Parameter parameter =repository.findByKey(ParameterConstant.REGISTRATION_START_DATE);
        if(parameter==null && StringUtils.isNotEmpty(parameter.getValue2()) && StringUtils.isNotEmpty(parameter.getValue())){
            throw new BusinessException("Both value1 and value2 must be specified");
        }
        return parameter;
    }

    @Override
    public Parameter getGpa() {
        return repository.findByKey(ParameterConstant.GPA_PARAMETER);
    }

    @Override
    public Parameter getLANGUAGE() {
        return repository.findByKey(ParameterConstant.LANGUAGE_PARAMETER);
    }

    @Override
    public Parameter getAlex() {
        return repository.findByKey(ParameterConstant.ALEX_PARAMETER);
    }
}