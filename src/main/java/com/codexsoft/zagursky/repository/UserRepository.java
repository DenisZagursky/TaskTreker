package com.codexsoft.zagursky.repository;

import com.codexsoft.zagursky.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Dzenyaa on 26.01.2018.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    List<User> findByNameIsContainingAndLastNameIsContainingAndAuthority_RoleAndEnabledIsTrue(String name, String lastName, String role);
}
