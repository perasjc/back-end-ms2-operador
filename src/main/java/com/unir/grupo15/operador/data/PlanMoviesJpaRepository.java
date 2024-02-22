package com.unir.grupo15.operador.data;

import com.unir.grupo15.operador.model.pojo.PlanMovies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PlanMoviesJpaRepository  extends JpaRepository<PlanMovies, Long>, JpaSpecificationExecutor<PlanMovies> {
}
