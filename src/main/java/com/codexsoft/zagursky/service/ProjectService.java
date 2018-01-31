package com.codexsoft.zagursky.service;

import com.codexsoft.zagursky.entity.Project;
import com.codexsoft.zagursky.exception.CustomException;

import java.util.List;

/**
 * Created by Dzenyaa on 26.01.2018.
 */
public interface ProjectService {

    void createProject(Project project);

    void addProjectToUser(Long id, String username) throws CustomException;

    List<Project> getProjectsByUser(Long id);

    Project geProjectById(Long id);

    List<Project> findProjectsByUser();

    void saveProject(String name, String description);
}
