package com.codexsoft.zagursky.entity;

import com.codexsoft.zagursky.entity.status.TaskStatus;
import com.codexsoft.zagursky.service.TaskService;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(exclude = {"Project", "comments", "users"})
@ToString(exclude = {"Project", "comments", "users"})
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "task_t")
public class Task {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private TaskStatus taskStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_project")
    @JsonIgnore
    private Project project;


    @OneToMany(mappedBy = "task")
    @JsonIgnore
    private List<Comment> comments=new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_task",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @JsonIgnore
    private List<User> users=new ArrayList<>();


    public void addUserToTask(User user) {
        users.add(user);
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }

    public Task(String name, String description, Project project) {
        taskStatus= TaskStatus.WAITING;
        this.name = name;
        this.description = description;
        this.project = project;
    }
}
