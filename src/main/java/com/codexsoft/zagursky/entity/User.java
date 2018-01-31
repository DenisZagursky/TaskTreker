package com.codexsoft.zagursky.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dzenyaa on 26.01.2018.
 */
@Getter
@Setter
@EqualsAndHashCode(exclude = {"projects", "tasks", "comments","authority"})
@ToString(exclude = {"projects", "tasks", "comments","authority"})
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_t")
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Email
    @Column(unique = true)
    private String username;

    @Column
    private String password;

    @Column
    private String name;

    @Column
    private String lastName;

    @Column
    private Boolean enabled;

    @ManyToOne
    @JoinColumn(name = "id_userStatus")
    @JsonIgnore
    private Authority authority;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_project",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id"))
    @JsonIgnore
    List<Project> projects=new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_task",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "task_id"))
    @JsonIgnore
    List<Task> tasks=new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Comment> comments=new ArrayList<>();

}
