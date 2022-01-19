package com.soydasm.taskmanagement.model;

import com.soydasm.taskmanagement.model.grant.Grant;
import com.soydasm.taskmanagement.model.grant.Role;
import com.soydasm.taskmanagement.model.grant.UserRole;
import com.soydasm.taskmanagement.payload.UserDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.Where;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.*;

import static java.util.stream.Collectors.toList;

@Entity
@Table(name = "T_USER", uniqueConstraints = @UniqueConstraint(columnNames = {"id", "userName"}))
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@EqualsAndHashCode(callSuper = true, of = "")
@ToString(callSuper = true, of = "")
@Where(clause = "DELETED = '0'")
public class User extends BaseEntity implements UserDetails
{
    @Column(unique=true)
    @NotBlank
    @Size(max = 40, message = "Username length can not be bigger than 40 characters!")
    private String userName;

    @NotBlank
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "T_USER_USER_ROLE",
            joinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "USER_ROLE_ID", referencedColumnName = "ID")})
    private Set<UserRole> userRoles = new HashSet<>();


    public static User convertUserDTOtoUser(UserDTO userDTO)
    {
        User user = new User();
        if(userDTO.getDeleted() != null && !"".equals(userDTO.getDeleted()))
        {
            user.setDeleted(userDTO.getDeleted());
        }
        if(userDTO.getId() != null)
        {
            user.setId(userDTO.getId());
        }
        user.setVersion(userDTO.getVersion());
        user.setCreateUser((userDTO.getCreateUser() != null && !"".equals(userDTO.getCreateUser())) ? userDTO.getCreateUser() :"SYSTEM");
        user.setCreatedTime(userDTO.getCreatedTime() != null ? userDTO.getCreatedTime() : new Date());
        user.setUserName(userDTO.getUserName());
        user.setPassword(userDTO.getPassword());

        return user;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getGrantedAuthorities(getPrivileges(userRoles));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }


    public static List<String> getPrivileges(Collection<UserRole> userRoles) {

        List<String> privileges = new ArrayList<>();
        for (UserRole userRole : userRoles)
        {
            userRole.getRoles().forEach(role ->
            {
                privileges.add(role.getName());
            });
        }
        return privileges;
    }

    public static List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }

}
