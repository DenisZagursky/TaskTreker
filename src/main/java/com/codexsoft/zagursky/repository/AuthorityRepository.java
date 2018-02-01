package com.codexsoft.zagursky.repository;

import com.codexsoft.zagursky.entity.Authority;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Dzenyaa on 31.01.2018.
 */
@Repository
public interface AuthorityRepository extends CrudRepository<Authority, Long> {
}
