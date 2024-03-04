package com.zalisoft.zalisoft.repository;

import com.zalisoft.zalisoft.model.Privilege;
import com.zalisoft.zalisoft.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    boolean existsRolesByIdIn(List<Long> ids);

    List<Role> findAllByPrivileges(Privilege privilege);

    @Query(value = "select r.privileges from Role r where r.id = :roleId")
    List<Privilege> findPrivilegeByRoleId(Long roleId);

    boolean existsByNameAndIdNot(String name, Long id);

    Role findByName(String name);
}
