package com.codexsoft.zagursky.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

/**
 * Created by Dzenyaa on 25.01.2018.
 */

@Getter
@Setter
@EqualsAndHashCode(exclude = {"task", "user"})
@ToString(exclude = {"task", "user"})
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comment_t")
public class Comment {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String Description;

    @ManyToOne
    @JoinColumn(name = "id_task")
    @JsonIgnore
    private Task task;

    @ManyToOne()
    @JoinColumn(name = "id_user")
    @JsonIgnore
    private User user;

}
