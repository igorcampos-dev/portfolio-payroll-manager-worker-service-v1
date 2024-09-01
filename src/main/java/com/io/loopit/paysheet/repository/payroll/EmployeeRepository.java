package com.io.loopit.paysheet.repository.payroll;

import com.io.loopit.paysheet.model.payroll.EmployeeEntity;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import java.util.Optional;
import org.springframework.data.domain.Pageable;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Service> {
    Optional<EmployeeEntity> findByCpf(String cpf);
    Optional<EmployeeEntity> findById(String userId);

    default EmployeeEntity findByCpfOrElseThrow(String cpf){
        return this.findByCpf(cpf).orElseThrow(() -> new NullPointerException("Usuário não existe com esse cpf."));
    }

    default void ifUserExistsThrow(String cpf){
        if (findByCpf(cpf).isPresent()){
            throw new DuplicateKeyException("Já existe um usuário com esse cpf no banco de dados.");
        }
    }

    default EmployeeEntity findByIdOrElseThrow(String userId){
        return this.findById(userId).orElseThrow(() -> new NullPointerException("Usuário não existe com esse cpf."));
    }

    default Page<EmployeeEntity> findAllOrElseThrow(int page){
        Pageable pageable = PageRequest.of(page,6);
        Page<EmployeeEntity> employeeEntities = this.findAll(pageable);
        if (employeeEntities.isEmpty()){
            throw new NullPointerException("Não existe usuários.");
        }
        return employeeEntities;
    }
}
