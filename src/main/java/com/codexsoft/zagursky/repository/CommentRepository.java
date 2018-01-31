package com.codexsoft.zagursky.repository;

import com.codexsoft.zagursky.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Dzenyaa on 26.01.2018.
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> getCommentsByTask_Id(Long id);
}
