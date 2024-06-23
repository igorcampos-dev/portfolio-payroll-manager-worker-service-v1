package com.io.loopit.paysheet.model;

import com.io.loopit.paysheet.controller.dto.response.RegisterEmployeeResponse;
import com.io.loopit.paysheet.model.enums.Profession;
import com.io.loopit.paysheet.model.enums.UserRole;
import lombok.*;
import org.hibernate.annotations.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(name = "employee")
public class EmployeeEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(unique = true)
    private String id;

    @Column(
            name = "role",
            nullable = false,
            length = 5,
            updatable = false
    )
    @Enumerated(EnumType.STRING)
    @Comment("Tipo do funcionário")
    private UserRole role;

    @Column(
            name = "profession",
            nullable = false,
            length = 30,
            updatable = false
    )
    @Enumerated(EnumType.STRING)
    @Comment("Profissão do funcionário")
    private Profession profession;

    @Column(
            name = "cpf",
            nullable = false,
            length = 11,
            updatable = false,
            unique = true
    )
    @Comment("Cpf do funcionário")
    private String cpf;

    @Column(
            name = "name",
            nullable = false,
            updatable = false
    )
    @Comment("Nome do funcionário")
    private String name;

    @Column(
            name = "code",
            nullable = false,
            updatable = false,
            length = 15
    )
    @Comment("Código do funcionário")
    private Integer code;

    @Column(
            name = "password",
            nullable = false,
            updatable = false
    )
    @Comment("Senha do funcionário")
    private String password;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        if (this.role == UserRole.ADMIN) { authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));}
        return authorities;
    }

    @Override
    public String getUsername() {
        return cpf;
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

    @Override
    public boolean isEnabled() {
        return true;
    }

    public RegisterEmployeeResponse toRegisterResponse() {
        return RegisterEmployeeResponse.builder()
                .name(this.name)
                .profession(this.profession)
                .code(this.code)
                .cpf(this.cpf)
                .build();
    }
}
