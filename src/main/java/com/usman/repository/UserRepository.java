package com.usman.repository;


import com.usman.model.Role;
import com.usman.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAllByRoles(Role role);

    Optional<User> findOneByEmail(String email);



    boolean existsByEmailIgnoreCase(String email);

    Page<User> findAll(Pageable pageable);



}
