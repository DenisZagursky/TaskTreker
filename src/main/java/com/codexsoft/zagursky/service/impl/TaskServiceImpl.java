package com.codexsoft.zagursky.service.impl;

import com.codexsoft.zagursky.entity.Project;
import com.codexsoft.zagursky.entity.Task;
import com.codexsoft.zagursky.entity.User;
import com.codexsoft.zagursky.entity.status.TaskStatus;
import com.codexsoft.zagursky.exception.CustomException;
import com.codexsoft.zagursky.repository.ProjectRepository;
import com.codexsoft.zagursky.repository.TaskRepository;
import com.codexsoft.zagursky.repository.UserRepository;
import com.codexsoft.zagursky.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Dzenyaa on 26.01.2018.
 */
@Service
@Transactional
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public void saveTask(Long idProject, String name, String description) throws CustomException{
        Project project=projectRepository.findOne(idProject);
        if (project==null)
        {
            throw new CustomException("проект не найден");
        }
        Task task = new Task(name, description,project);
        taskRepository.save(task);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Task> getTasksByProject(Long idProject) {
        return taskRepository.getTasksByProject_Id(idProject);
    }

    @Override
    public void addDeveloperToTask(Long idTask, String username) throws  CustomException {
        Task task = taskRepository.getOne(idTask);
        User developer=userRepository.findByUsername(username);

        if ((task==null) || (developer.getEnabled()!=true)|| (developer==null) ||(task.getUsers().contains(developer))|| (developer.getAuthority().getRole()=="ROLE_ADMIN"))
        {
            throw new CustomException("Такого пользователя не существует, либо он уже работает над проектом");
        }
        task.addUserToTask(developer);
        taskRepository.save(task);

    }

    @Override
    public void setStatusToTask(Long id, TaskStatus status) {
        Task task = taskRepository.getOne(id);
        task.setTaskStatus(status);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HashMap<String,String>> getTasksByUser() {
        String username= SecurityContextHolder.getContext().getAuthentication().getName();
        List<HashMap<String,String>> result=new ArrayList<HashMap<String, String>>();
      List<Task> tasks= taskRepository.getTasksByUsers_Id(userRepository.findByUsername(username).getId());
      for (Task task:tasks)
      {
          HashMap<String,String> information=new HashMap<>();
          information.put("projectid",task.getProject().getId().toString());
          information.put("project",task.getProject().getName());
          information.put("task",task.getName());
          information.put("taskid",task.getId().toString());
          information.put("status",task.getTaskStatus().toString());
          result.add(information);
      }
      return result;
    }
}
