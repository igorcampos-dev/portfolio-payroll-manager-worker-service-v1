package com.io.loopit.paysheet.service.component;

import com.io.loopit.paysheet.repository.payroll.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@SuppressWarnings("unused")
public class LoadUserComponent implements UserDetailsService {

    private final EmployeeRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String cpf){
        return this.findByEmail(cpf);
    }

    public UserDetails findByEmail(String cpf) {
        var user = this.userRepository.findByCpfOrElseThrow(cpf);
        return new User(user.getCpf(), user.getPassword(), user.getAuthorities());
    }
}