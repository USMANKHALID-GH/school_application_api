package com.usman.service.impl;

import com.usman.dto.FacultyDto;
import com.usman.exception.BusinessException;
import com.usman.model.Faculty;
import com.usman.repository.FacultyRepository;
import com.usman.service.FacultyService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class FacultyServiceImpl implements FacultyService {

    @Autowired
    private FacultyRepository repository;

    @Override
    public Faculty save(FacultyDto facultyDto) {

        if(StringUtils.isEmpty(facultyDto.getName())){
            throw new BusinessException("Faculty name cannot be empty");
        }
        if(StringUtils.isEmpty(facultyDto.getCode())){
            throw new   BusinessException("Faculty code cannot be empty");
        }

        var faculty = new Faculty();
        faculty.setName(facultyDto.getName());
        faculty.setCode(facultyDto.getCode());
        faculty.setDescription(facultyDto.getDescription());
        return repository.save(faculty);
    }

    @Override
    public void delete(Long id) {
        repository.delete(findById(id));
    }

    @Override
    public void update(Long id, FacultyDto facultyDto) {

    }

    @Override
    public Faculty findById(Long id) {
        return repository.findById(id)
                .orElseThrow(()->new BusinessException("Faculty not found"));
    }

    @Override
    public Page<Faculty> search(String search, Pageable pageable) {
        return repository.search(search, pageable);
    }
}
