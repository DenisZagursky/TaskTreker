package com.codexsoft.zagursky.controller;

import com.codexsoft.zagursky.entity.User;
import com.codexsoft.zagursky.entity.VerificationToken;
import com.codexsoft.zagursky.event.OnRegistrationCompleteEvent;
import com.codexsoft.zagursky.exception.CustomException;
import com.codexsoft.zagursky.repository.UserRepository;
import com.codexsoft.zagursky.service.UserService;
import com.codexsoft.zagursky.validator.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;

/**
 * Created by Dzenyaa on 27.01.2018.
 */
@Controller
public class LoginController {

    @Autowired
    ApplicationEventPublisher eventPublisher;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/auth", method = RequestMethod.GET)
    ResponseEntity getAuth() {
        HashMap<String, String> result = new HashMap<>();
        try {


            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userRepository.findByUsername(username);
            result.put("status", user.getAuthority().getRole());
            result.put("name", user.getName());
            result.put("lastname", user.getLastName());
        } catch (Exception ex) {
            return new ResponseEntity(HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    String getSinglePage() {
        return "index";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    String getRegistration() {
        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    ResponseEntity registration(@ModelAttribute User user,
                                @RequestParam(defaultValue = "user") String role,
                                BindingResult result,
                                WebRequest request,
                                Errors errors) throws Exception {
        if (!EmailValidator.isValidEmailAddress(user.getUsername())) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        try {

            user = userService.createUser(user, role);
        } catch (CustomException ex) {
            return new ResponseEntity(HttpStatus.BAD_GATEWAY);
        }
        String appUrl = request.getContextPath();
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent
                (user, request.getLocale(), appUrl));


        return new ResponseEntity(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/regitrationConfirm", method = RequestMethod.GET)
    public String confirmRegistration
            (WebRequest request, Model model, @RequestParam("token") String token) {

        VerificationToken verificationToken = userService.getVerificationToken(token);
        if (verificationToken == null) {
            return null;
        }
        User user = verificationToken.getUser();
        user.setEnabled(true);
        userRepository.save(user);
        return "redirect:/";
    }

}
