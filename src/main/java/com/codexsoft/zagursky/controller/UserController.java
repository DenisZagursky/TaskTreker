package com.codexsoft.zagursky.controller;

import com.codexsoft.zagursky.entity.status.TaskStatus;
import com.codexsoft.zagursky.exception.CustomException;
import com.codexsoft.zagursky.service.CommentService;
import com.codexsoft.zagursky.service.ProjectService;
import com.codexsoft.zagursky.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashMap;

/**
 * Created by Dzenyaa on 29.01.2018.
 */
@Controller
public class UserController {
    @Autowired
    private TaskService taskService;
    @Autowired
    private ProjectService projectService;

    @Autowired
    private CommentService commentService;

    @RequestMapping(value = "/tasks/{id}", method = RequestMethod.GET)
    ResponseEntity getTasks(@PathVariable Long id) {
        return new ResponseEntity(taskService.getTasksByProject(id), HttpStatus.OK);
    }

    @RequestMapping(value = "/projects", method = RequestMethod.GET)
    ResponseEntity getProjects() {
        return new ResponseEntity(projectService.findProjectsByUser(), HttpStatus.OK);
    }

    @RequestMapping(value = "/comments/{id}", method = RequestMethod.GET)
    ResponseEntity getComments(@PathVariable Long id) {
        return new ResponseEntity(commentService.getCommentByTask(id), HttpStatus.OK);
    }

    @RequestMapping(value = "/comments/user/{id}", method = RequestMethod.GET)
    ResponseEntity getUserByComment(@PathVariable Long id) {
        HashMap<String, String> result = commentService.getNameAndLastNameByComment(id);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/comments/{id}/{description}", method = RequestMethod.PUT)
    ResponseEntity updateComment(@PathVariable Long id, @PathVariable String description) throws CustomException {
        commentService.changeComment(id, description);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/comments/{id}", method = RequestMethod.DELETE)
    ResponseEntity deleteComment(@PathVariable Long id) throws CustomException {
        commentService.deleteComment(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/comments/{id}/{description}", method = RequestMethod.POST)
    ResponseEntity createComment(@PathVariable Long id, @PathVariable String description) {
        commentService.saveComment(id, description);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/tasks/{id}/{name}/{description}", method = RequestMethod.POST)
    ResponseEntity createTask(@PathVariable Long id, @PathVariable String name, @PathVariable String description) throws CustomException {
        taskService.saveTask(id, name, description);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/tasks/status/{id}/{status}", method = RequestMethod.POST)
    ResponseEntity updateStatus(@PathVariable Long id, @PathVariable TaskStatus status) {
        taskService.setStatusToTask(id, status);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/tasks/filter", method = RequestMethod.GET)
    ResponseEntity getFilter() {
        return new ResponseEntity(taskService.getTasksByUser(), HttpStatus.OK);
    }
}
