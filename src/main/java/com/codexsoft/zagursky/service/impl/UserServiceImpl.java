package com.codexsoft.zagursky.service.impl;

import com.codexsoft.zagursky.entity.Authority;
import com.codexsoft.zagursky.entity.User;
import com.codexsoft.zagursky.entity.VerificationToken;
import com.codexsoft.zagursky.exception.CustomException;
import com.codexsoft.zagursky.repository.AuthorityRepository;
import com.codexsoft.zagursky.repository.UserRepository;
import com.codexsoft.zagursky.repository.VerificationTokenRepository;
import com.codexsoft.zagursky.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Dzenyaa on 29.01.2018.
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private VerificationTokenRepository tokenRepository;

    @Transactional(readOnly = true)
    @Override
    public List<User> getDevelopersbyfilter(String name, String lastName) {
        return userRepository.findByNameIsContainingAndLastNameIsContainingAndAuthority_RoleAndEnabledIsTrue(name, lastName, "ROLE_USER");
    }

    @Override
    public User createUser(User user, String role) throws CustomException{
        if (role.equals("admin"))
        {
            Authority authority=authorityRepository.findOne((long)1);
            user.setAuthority(authority);
        } else {
            Authority authority=authorityRepository.findOne((long)2);
            user.setAuthority(authority);
        }
        user.setEnabled(false);
        if (userRepository.findByUsername(user.getUsername())!=null){
            throw new CustomException("такой пользователь уже есть");
        }
        userRepository.save(user);
        return user;
    }

    @Override
    public void createVerificationToken(User user, String token) {
        VerificationToken verificationToken=new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        tokenRepository.save(verificationToken);
    }

    @Override
    public VerificationToken getVerificationToken(String token) {
        return tokenRepository.getVerificationTokenByToken(token);
    }
}
