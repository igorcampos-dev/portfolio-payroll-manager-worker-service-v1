package com.io.loopit.paysheet.repository.rh;

import com.io.loopit.paysheet.model.rh.EmployeeRhEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RhRepository extends JpaRepository<EmployeeRhEntity, String> {
  Optional<EmployeeRhEntity> findByCpf(String cpf);

  default EmployeeRhEntity findByCpfOrElseThrow(String cpf){
    return this.findByCpf(cpf).orElseThrow(() -> new NullPointerException("Funcionário não encontrado na base de dados principal"));
  }
}