package com.nexus.java.api.secure.authentication;

import com.nexus.java.api.infrastructure.validation.repository.EmployeeValidationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationLoginUser implements UserDetailsService {

    private final EmployeeValidationRepository employeeValidationRepository;

    @Override
    public UserDetails loadUserByUsername(String cpf) throws UsernameNotFoundException {
        return employeeValidationRepository.findByLoginAuth(cpf);
    }
}