package com.codexsoft.zagursky.service;

import com.codexsoft.zagursky.entity.Comment;
import com.codexsoft.zagursky.exception.CustomException;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Dzenyaa on 26.01.2018.
 */

public interface CommentService {

    void saveComment(Long id,String description);

    void deleteComment(Long id) throws CustomException;

    void changeComment(Long id, String description) throws CustomException;

    List<Comment> getCommentByTask(Long id);

    HashMap<String,String> getNameAndLastNameByComment(Long id);
}
