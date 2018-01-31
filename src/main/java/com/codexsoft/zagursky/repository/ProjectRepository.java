package com.codexsoft.zagursky.repository;

import com.codexsoft.zagursky.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Dzenyaa on 26.01.2018.
 */
@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> getProjectsByUsers_Id(Long id);
}
