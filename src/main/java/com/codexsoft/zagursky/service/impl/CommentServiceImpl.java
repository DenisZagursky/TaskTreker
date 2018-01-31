package com.codexsoft.zagursky.service.impl;

import com.codexsoft.zagursky.entity.Comment;
import com.codexsoft.zagursky.entity.Task;
import com.codexsoft.zagursky.exception.CustomException;
import com.codexsoft.zagursky.repository.CommentRepository;
import com.codexsoft.zagursky.repository.TaskRepository;
import com.codexsoft.zagursky.repository.UserRepository;
import com.codexsoft.zagursky.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Dzenyaa on 26.01.2018.
 */
@Service
@Transactional
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public void saveComment(Long id, String description) {
        Comment comment = new Comment();
        comment.setDescription(description);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        comment.setUser(userRepository.findByUsername(username));
        Task task = taskRepository.getOne(id);
        comment.setTask(task);
        commentRepository.save(comment);
    }

    @Override
    public void deleteComment(Long id) throws CustomException {
        Comment comment = commentRepository.findOne(id);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        String commentUsername = comment.getUser().getUsername();
        if ((comment == null) || (commentUsername.equals(username) == false)) {
            throw new CustomException("не нашел комента");
        }

        commentRepository.delete(id);
    }

    @Override
    public void changeComment(Long id, String description) throws CustomException {
        Comment comment = commentRepository.findOne(id);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        String commentUsername = comment.getUser().getUsername();
        if ((comment == null) || (commentUsername.equals(username) == false)) {
            throw new CustomException("не нашел комента");
        }
        comment.setDescription(description);
        commentRepository.save(comment);
    }

    @Override
    public List<Comment> getCommentByTask(Long id) {
        return commentRepository.getCommentsByTask_Id(id);
    }

    @Override
    public HashMap<String, String> getNameAndLastNameByComment(Long id) {
        Comment comment = commentRepository.findOne(id);
        HashMap<String, String> result = new HashMap<>();
        result.put("name", comment.getUser().getName());
        result.put("lastname", comment.getUser().getLastName());
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (username.equals(comment.getUser().getUsername())) {
            result.put("canchange", "true");
        } else {
            result.put("canchange", "false");
        }
        return result;
    }
}
