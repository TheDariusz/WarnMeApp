package com.thedariusz.warnme.user.repository.entity;

import com.thedariusz.warnme.user.UserDto;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.Set;

@Entity(name = "user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private int enabled;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name="role_id"))
    private Set<RoleEntity> roleEntities;

    public UserEntity() {
    }

    public UserEntity(String username, String password, int enabled, Set<RoleEntity> roleEntities) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.roleEntities = roleEntities;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    public Set<RoleEntity> getRoles() {
        return roleEntities;
    }

    public void setRoles(Set<RoleEntity> roleEntities) {
        this.roleEntities = roleEntities;
    }

    public static UserEntity toEntity(UserDto userDto) {
        return new UserEntity(
                userDto.getUsername(),
                userDto.getPassword(),
                userDto.getEnabled(),
                userDto.getRoles()
        );
    }

    public static UserDto toUserDto(UserEntity user) {
        return new UserDto(
                user.getUsername(),
                user.getPassword(),
                user.getEnabled(),
                user.getRoles()
        );
    }
}
