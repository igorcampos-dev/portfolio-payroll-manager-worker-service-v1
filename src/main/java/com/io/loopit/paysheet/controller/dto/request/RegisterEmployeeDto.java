package com.io.loopit.paysheet.controller.dto.request;

import com.io.loopit.paysheet.model.payroll.EmployeeEntity;
import com.io.loopit.paysheet.model.rh.EmployeeRhEntity;
import com.io.loopit.paysheet.model.enums.Profession;
import com.io.loopit.paysheet.model.enums.UserRole;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterEmployeeDto {

    @NotNull(message = ": O cpf não pode ser nulo")
    @NotEmpty(message = ": O cpf não pode ser vazio")
    @CPF(message = ": Cpf inválido")
    private String cpf;

    @NotNull(message = ": A senha não pode ser nulo")
    @NotEmpty(message = ": A senha não pode ser vazio")
    private String password;

    public EmployeeEntity toEntity(EmployeeRhEntity employeeProperties) {
        return EmployeeEntity.builder()
                .role(UserRole.USER)
                .name(employeeProperties.getName())
                .cpf(this.cpf)
                .password(new BCryptPasswordEncoder().encode(this.password))
                .profession(Profession.valueOf(employeeProperties.getProfession()))
                .build();
    }

}
