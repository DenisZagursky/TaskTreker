package com.codexsoft.zagursky.service;

import com.codexsoft.zagursky.entity.User;
import com.codexsoft.zagursky.entity.VerificationToken;
import com.codexsoft.zagursky.exception.CustomException;

import java.util.List;

/**
 * Created by Dzenyaa on 26.01.2018.
 */
public interface UserService {
    List<User> getDevelopersbyfilter(String name, String lastName);

    User createUser(User user, String role) throws CustomException;

    void createVerificationToken(User user, String token);

    VerificationToken getVerificationToken(String token);
}

