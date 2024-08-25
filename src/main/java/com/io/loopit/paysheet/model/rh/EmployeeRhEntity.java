package com.io.loopit.paysheet.model.rh;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@Table(name = "employees_rh")
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRhEntity {

    @Id
    @Column(name = "id", length = 36, nullable = false)
    private String id;

    @Column(name = "phone_number", length = 20, unique = true, nullable = false)
    private String phoneNumber;

    @Column(name = "name", length = 100, unique = true, nullable = false)
    private String name;

    @Column(name = "profession", length = 100, nullable = false)
    private String profession;

    @Column(name = "cpf", length = 14, unique = true, nullable = false)
    private String cpf;

    @Column(name = "email", length = 100, unique = true, nullable = false)
    private String email;

    @Column(name = "age")
    private Integer age;

    @Column(name = "code", nullable = false)
    private Integer code;

    @Column(name = "hire_year")
    private Integer hireYear;

}
