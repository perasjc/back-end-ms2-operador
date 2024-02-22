package com.unir.grupo15.operador.data;

import com.unir.grupo15.operador.model.pojo.PlanMovies;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PlanMoviesRepository {

    private final PlanMoviesJpaRepository repository;

    public List<PlanMovies> getPlanMovies() {
        return repository.findAll();
    }

    public PlanMovies getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public PlanMovies save(PlanMovies planMovies) {
        return repository.save(planMovies);
    }

    public void delete(PlanMovies planMovies) {
        repository.delete(planMovies);
    }
}
