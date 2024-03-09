package com.usman.repository;


import com.usman.model.Parameter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ParameterRepository extends JpaRepository<Parameter, Long> {

    @Query(value = """ 
                   select p from Parameter p 
                   where '' = :searchText or :searchText is null 
                   or lower(p.key) like lower(concat('%', :searchText,'%')) 
                   or lower(p.value) like lower(concat('%', :searchText,'%')) 
                   or lower(p.description) like lower(concat('%', :searchText,'%')) """)
    Page<Parameter> search(String searchText, Pageable pageable);

    Parameter findByKey(String key);

    List<Parameter> findParameterByKeyContainsOrderByIdAsc(String key);
}
