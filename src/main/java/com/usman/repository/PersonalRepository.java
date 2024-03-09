package com.usman.repository;

import com.usman.model.Personal;
import com.usman.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonalRepository extends JpaRepository<Personal, Long> {

    Optional<Personal>  findByUser(User user);

    @Query("SELECT p FROM Personal p where p.userInformation.phone=:phone")
    Optional<Personal> findPersonalByPhone(String phone);

        @Query(value = """
            select u from Personal u
            where ('' = :searchText or :searchText is null
                   or lower(concat(u.userInformation.firstName, ' ', u.userInformation.lastName)) 
                   like lower(concat('%', :searchText,'%'))
                   or lower(u.userInformation.qualification) like lower(concat('%', :searchText,'%'))
                   or lower(u.userInformation.birthDate) like lower(concat('%', :searchText,'%')))
           """)
        Page<Personal> search(String searchText , Pageable pageable);

    @Query(value = """
            select u from Personal u
            where ('' = :searchText or :searchText is null
                   or lower(u.userInformation.phone) like lower(concat('%', :searchText,'%'))
                    or lower(u.user.email) like lower(concat('%', :searchText,'%'))
                   or lower(u.userInformation.tcNumber) like lower(concat('%', :searchText,'%')))
           """)
        Optional<Personal> findByEmailOrPhoneorTc(String searchText);

    @Query("""
        select p from Personal  p where p.department.faculty.id=:fId and p.deleted=false 
        """)
    Page<Personal> getPersonalsByFaculty(Pageable pageable, Long fId);


    @Query(value = """
    select p from Personal p  where p.user=:userId and p.deleted=false 
    """)
    Optional<Personal> findByCurrentUser(@Param("userId") User userId);



    @Query("""
       select p from Personal p where p.department.id=:dId and p.deleted=false 
       """)
    Page<Personal> findAllDepartmentPersonal(@Param("dId") Long dId, Pageable pageable);

}