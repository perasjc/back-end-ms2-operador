package com.unir.grupo15.operador.service.interfaces;

import com.unir.grupo15.operador.model.pojo.PlanMovies;
import com.unir.grupo15.operador.model.request.PlanMoviesRequest;

import java.util.List;

public interface PlanMoviesService {

    PlanMovies createPlanMovies(PlanMoviesRequest request);

    PlanMovies getPlanMovies(String id);

    List<PlanMovies> getAllPlanMovies();
}
