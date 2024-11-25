package com.example.dto;

import com.example.entity.Employee;
import com.example.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDetails implements UserDetails {

    private Integer id;
    private String phoneNumber;
    private String password;
    private List<GrantedAuthority> authorities;

    public EmployeeDetails(Employee employee, List<Role> roles) {
        this.id = employee.getId();
        this.phoneNumber = employee.getPhoneNumber();
        this.password = employee.getPassword();
        this.authorities = roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getRole()))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return phoneNumber;
    }

}

