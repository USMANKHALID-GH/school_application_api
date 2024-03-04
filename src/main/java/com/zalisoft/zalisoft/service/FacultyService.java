package com.zalisoft.zalisoft.service;

import com.zalisoft.zalisoft.dto.FacultyDto;
import com.zalisoft.zalisoft.model.Faculty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FacultyService {

    Faculty save(FacultyDto facultyDto);

    void delete(Long id);

    void update(Long id, FacultyDto facultyDto);

    Faculty findById(Long id);


    Page<Faculty> search(String search , Pageable pageable);
}
