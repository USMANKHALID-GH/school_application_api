package com.zalisoft.zalisoft.repository;

import com.zalisoft.zalisoft.model.Faculty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty, Long> {

    @Query(value = """
                 select  dp from Faculty  dp where (
                 '' = :search or :search is null
                 or  lower(dp.name) like lower(concat('%', :search,'%'))
                 or  lower(dp.code) like lower(concat('%', :search,'%')))
                 """)
    Page<Faculty> search(String search, Pageable pageable);
}