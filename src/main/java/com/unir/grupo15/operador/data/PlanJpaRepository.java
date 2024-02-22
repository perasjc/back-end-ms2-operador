package com.unir.grupo15.operador.data;

import com.unir.grupo15.operador.model.pojo.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PlanJpaRepository extends JpaRepository<Plan, Long>, JpaSpecificationExecutor<Plan> {
}
