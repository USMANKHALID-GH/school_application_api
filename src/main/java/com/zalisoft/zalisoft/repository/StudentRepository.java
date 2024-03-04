package com.zalisoft.zalisoft.repository;

import com.zalisoft.zalisoft.model.Student;
import com.zalisoft.zalisoft.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByUser(User user);

    @Query("select s from Student s where s.studentNumber=:no")
    Optional<Student> findUserByStudentNo(String no);

    @Query("select s from Student s where s.studentNumber=:studentNumber")
    Optional<Student> checkIfStudentNumberExist(String studentNumber);

    @Query("""
       SELECT s FROM Student  s
       inner join Personal p on p.department.id=s.department.id
       inner join Documents  d on d.id=s.documents.id
       WHERE  d.certificateUrl !=null 
       and d.englishUrl !=null
       and d.transcriptUrl !=null
       and d.deleted= false 
       and s.deleted=false 
       and s.applicationStatus='WAITING'  
       and p.department.id=:departmentId
       order by s.score.totalScore desc 
   """)
    Page<Student>  findStudentByTheirScores(Pageable pageable,long departmentId );



    @Query("""
            select s from Student s 
            inner join Personal p on p.department.id=s.department.id
            where s.id=:id and p.id=:personId 
            """)
    Optional<Student>  findStudentByDepartmentPersonal(@Param("personId") Long personId, @Param("id") Long id);

    @Query("""
       SELECT s FROM Student  s 
       WHERE (''= :search or :search= null 
       or lower(s.studentNumber) like lower(concat('%', :search,'%'))
       or lower(s.userInformation.firstName) like lower(concat('%', :search,'%'))
       or lower(s.userInformation.lastName) like lower(concat('%', :search,'%'))
       or lower(s.userInformation.tcNumber) like lower(concat('%', :search,'%'))
       or lower(s.userInformation.phone) like lower(concat('%', :search,'%')))
       and s.department.id=:departmentId and s.studentNumber <>null 
         """)
    Page<Student>  findStudentByDepartment(Pageable pageable,Long departmentId,String search);



    @Query("""
       SELECT s FROM Student  s 
       WHERE ('' = :search or :search is null 
       or lower(s.studentNumber) like lower(concat('%', :search,'%'))
       or lower(s.userInformation.firstName) like lower(concat('%', :search,'%'))
       or lower(s.userInformation.lastName) like lower(concat('%', :search,'%'))
       or lower(s.userInformation.tcNumber) like lower(concat('%', :search,'%'))
       or lower(s.userInformation.phone) like lower(concat('%', :search,'%')))
         """)
    Page<Student>  search(Pageable pageable,String search);






}