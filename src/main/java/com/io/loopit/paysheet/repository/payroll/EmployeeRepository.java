package com.io.loopit.paysheet.repository.payroll;

import com.io.loopit.paysheet.model.payroll.EmployeeEntity;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Repository
@Qualifier("employeeRepository")
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

    default List<EmployeeEntity> findAllOrElseThrow(){
        List<EmployeeEntity> employeeEntities = this.findAll();
        if (employeeEntities.isEmpty()){
            throw new NullPointerException("Não existe usuários.");
        }
        return employeeEntities;
    }
}
