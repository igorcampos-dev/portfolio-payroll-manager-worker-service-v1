package com.nexus.java.api.infrastructure.adapter;

import com.nexus.java.api.domain.persistence.PaycheckEmployeePersistence;
import com.nexus.java.api.infrastructure.validation.repository.PaycheckEmployeeValidationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaycheckEmployeeAdapter implements PaycheckEmployeePersistence {
    private final PaycheckEmployeeValidationRepository employeeValidationPersistence;
}
