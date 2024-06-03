package com.io.loopit.paysheet.controller.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdministratorLoginDto {

    @CPF(message = "Cpf inválido")
    @NotNull(message = "O cpf não pode ser nulo")
    @NotEmpty(message = "O cpf não pode ser vazio")
    private String cpf;

    @NotNull(message = "A senha não pode ser nulo")
    @NotEmpty(message = "A senha não pode ser vazio")
    private String password;

}
