package com.codexsoft.zagursky.repository;

import com.codexsoft.zagursky.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Dzenyaa on 26.01.2018.
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> getTasksByProject_Id(Long id);

    List<Task> getTasksByUsers_Id(Long id);
}
