package com.usman.repository;

import com.usman.model.Courses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CoursesRepository extends JpaRepository<Courses, Long> {

    @Query(value = """
                 select  dp from Courses  dp 
                 inner join Personal p on dp.department.id=p.department.id
                 where  p.id=:userId and (
                 '' = :searchText or :searchText is null
                 or  lower(dp.name) like lower(concat('%', :search,'%'))
                 or  lower(dp.code) like lower(concat('%', :search,'%')))
                 """)
    Page<Courses> search(String search, Long userId, Pageable pageable);




    @Query("SELECT c FROM Courses c  inner join Personal p on c.department.id=p.department.id WHERE c.id = :id AND p.id = :userId " +
            "AND c.deleted = false AND c.deleted = false")
    Optional<Courses> checkIfUserPartOfDepartment(@Param("userId") Long userId, @Param("id") Long id);

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END " +
            "FROM Courses c  inner join Personal p on c.department.id=p.department.id WHERE p.id = :userId")
    boolean checkIfUserPartOfDepartment( long userId);

    @Query(value = """
                 select  dp from Courses  dp where 
                  lower(dp.department.name) like lower(concat('%', :department,'%'))
                 or  lower(dp.department.code) like lower(concat('%', :department,'%'))
                 """)
    Page<Courses> findCourseByDepartment(String department,Pageable pageable);


}