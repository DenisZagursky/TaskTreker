package com.codexsoft.zagursky.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dzenyaa on 26.01.2018.
 */
@Getter
@Setter
@EqualsAndHashCode(exclude = {"userStatus"})
@ToString(exclude = {"userStatus"})
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "role")
public class Authority implements GrantedAuthority {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String role;

    @OneToMany(mappedBy = "authority", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<User> users=new ArrayList<>();

    @Override
    public String getAuthority() {
        return getRole();
    }
}
