package com.codexsoft.zagursky.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(exclude = {"tasks", "users"})
@ToString(exclude = {"tasks", "users"})
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "project_t")
public class Project {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String name;

    @Column
    private String description;


    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Task> tasks=new ArrayList<>();


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_project",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @JsonIgnore
    private List<User> users=new ArrayList<>();

    public Project(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
