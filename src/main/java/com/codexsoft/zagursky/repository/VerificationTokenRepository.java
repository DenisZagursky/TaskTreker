package com.codexsoft.zagursky.repository;

import com.codexsoft.zagursky.entity.VerificationToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Dzenyaa on 31.01.2018.
 */
@Repository
public interface VerificationTokenRepository extends CrudRepository<VerificationToken, Long> {

    VerificationToken getVerificationTokenByToken(String token);
}
