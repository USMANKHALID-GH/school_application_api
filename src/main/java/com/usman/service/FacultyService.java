package com.usman.service;

import com.usman.dto.FacultyDto;
import com.usman.model.Faculty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FacultyService {

    Faculty save(FacultyDto facultyDto);

    void delete(Long id);

    void update(Long id, FacultyDto facultyDto);

    Faculty findById(Long id);


    Page<Faculty> search(String search , Pageable pageable);
}
