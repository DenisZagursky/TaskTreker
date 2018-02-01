package com.codexsoft.zagursky.service.impl;

import com.codexsoft.zagursky.entity.Project;
import com.codexsoft.zagursky.entity.User;
import com.codexsoft.zagursky.exception.CustomException;
import com.codexsoft.zagursky.repository.ProjectRepository;
import com.codexsoft.zagursky.repository.UserRepository;
import com.codexsoft.zagursky.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Dzenyaa on 26.01.2018.
 */
@Transactional
@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void createProject(Project project) {
        projectRepository.save(project);

    }

    @Override
    public void addProjectToUser(Long id, String username) throws CustomException {
        Project project = projectRepository.findOne(id);
        User user = userRepository.findByUsername(username);
        if ((project == null) || (user.getEnabled() != true) || (user == null) || (project.getUsers().contains(user)) || (user.getAuthority().getRole().equals("ROLE_ADMIN"))) {
            throw new CustomException("Такого пользователя не существует, либо он уже работает над проектом");
        } else {
            project.getUsers().add(user);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Project> getProjectsByUser(Long id) {
        return projectRepository.getProjectsByUsers_Id(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Project geProjectById(Long id) {
        return projectRepository.findOne(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Project> findProjectsByUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username);
        return user.getProjects();
    }

    @Override
    public void saveProject(String name, String description) {
        Project project = new Project();
        project.setName(name);
        project.setDescription(description);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username);
        project.getUsers().add(user);
        projectRepository.save(project);
    }
}
