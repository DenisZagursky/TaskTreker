package com.codexsoft.zagursky.controller;

import com.codexsoft.zagursky.exception.CustomException;
import com.codexsoft.zagursky.repository.ProjectRepository;
import com.codexsoft.zagursky.service.ProjectService;
import com.codexsoft.zagursky.service.TaskService;
import com.codexsoft.zagursky.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Dzenyaa on 28.01.2018.
 */
@Controller
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private TaskService taskService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "admin/projects/{name}/{description}", method = RequestMethod.POST)
    ResponseEntity addProject(@PathVariable String name, @PathVariable String description) {
        projectService.saveProject(name, description);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/users/", method = RequestMethod.GET)
    ResponseEntity getUsersByName(@RequestParam String name, @RequestParam String lastName) {
        return new ResponseEntity(userService.getDevelopersbyfilter(name, lastName), HttpStatus.OK);

    }

    @RequestMapping(value = "/projects/{id}/{username}", method = RequestMethod.POST)
    ResponseEntity addProjectToUser(@PathVariable Long id, @PathVariable String username) throws CustomException {

        projectService.addProjectToUser(id, username);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/tasks/{id}/{username}", method = RequestMethod.POST)
    ResponseEntity addTaskToUser(@PathVariable Long id, @PathVariable String username) throws CustomException {
        taskService.addDeveloperToTask(id, username);
        return new ResponseEntity(HttpStatus.OK);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity error(CustomException ex) {
        return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
