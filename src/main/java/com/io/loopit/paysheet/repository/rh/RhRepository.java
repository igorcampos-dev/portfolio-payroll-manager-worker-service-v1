package com.io.loopit.paysheet.repository.rh;

import com.io.loopit.paysheet.model.rh.EmployeeRhEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RhRepository extends JpaRepository<EmployeeRhEntity, String> {
  EmployeeRhEntity findDescriptionByCpf(String cpf);
}