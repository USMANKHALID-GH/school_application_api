package com.usman.repository;

import com.usman.model.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

                @Query(value = """
                 select  dp from Department  dp where 
                 '' = :search or :search is null
                 or  lower(dp.name) like lower(concat('%', :search,'%'))
                 or  lower(dp.code) like lower(concat('%', :search,'%'))
                 """)
        Page<Department> search(String search, Pageable pageable);

       Optional<Department>  findDepartmentByNameContainingIgnoreCase(String name);

    @Query("""
    SELECT dp FROM Department dp
    JOIN Personal pd ON dp.id = pd.department.id
    JOIN Student s ON s.department.id = dp.id
    WHERE dp.id = :id AND pd.deleted = false AND s.deleted = false
""")
    Optional<Department> departmentHasUser(@Param("id") Long id);

}