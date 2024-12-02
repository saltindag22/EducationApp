package com.example.demo2.models;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.UuidGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
@Entity
@Getter
@Setter
@Table(name="user")
public class User implements UserDetails {
    
    @Id
    @UuidGenerator
    protected String id;

    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Roles role;


    @OneToMany(mappedBy = "user")
    private Set<Question> questions;
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
       return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired(){
        return true;
    }
    @Override
    public boolean isAccountNonLocked(){
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired(){
        return true;
    }
    
    @Override
    public boolean isEnabled(){
        return true;
    }
    

}
