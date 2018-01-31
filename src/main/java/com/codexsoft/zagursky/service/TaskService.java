package com.codexsoft.zagursky.service;

import com.codexsoft.zagursky.entity.Task;
import com.codexsoft.zagursky.entity.User;
import com.codexsoft.zagursky.entity.status.TaskStatus;
import com.codexsoft.zagursky.exception.CustomException;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Dzenyaa on 26.01.2018.
 */
public interface TaskService {
    void saveTask(Long idProject,String name, String description) throws CustomException;

    List<Task> getTasksByProject(Long idProject);

    void addDeveloperToTask(Long idTask, String username)  throws CustomException;

    void setStatusToTask(Long id, TaskStatus status);

    List<HashMap<String,String>> getTasksByUser();
}
