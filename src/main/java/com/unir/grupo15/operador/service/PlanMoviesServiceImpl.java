package com.unir.grupo15.operador.service;

import com.unir.grupo15.operador.data.PlanMoviesRepository;
import com.unir.grupo15.operador.data.PlanRepository;
import com.unir.grupo15.operador.facade.MoviesFacade;
import com.unir.grupo15.operador.model.Movie;
import com.unir.grupo15.operador.model.pojo.Plan;
import com.unir.grupo15.operador.model.pojo.PlanMovies;
import com.unir.grupo15.operador.model.request.PlanMoviesRequest;
import com.unir.grupo15.operador.service.interfaces.PlanMoviesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PlanMoviesServiceImpl implements PlanMoviesService {

    @Autowired //Inyeccion por campo (field injection). Es la menos recomendada.
    private MoviesFacade moviesFacade;

    @Autowired //Inyeccion por campo (field injection). Es la menos recomendada.
    private PlanMoviesRepository planMoviesRepository;

    @Autowired //Inyeccion por campo (field injection). Es la menos recomendada.
    private PlanRepository planRepository;

    @Override
    public PlanMovies createPlanMovies(PlanMoviesRequest request) {

        List<Movie> products = request.getMovies().stream().map(moviesFacade::getMovie).filter(Objects::nonNull).toList();

        Plan plan = planRepository.getById(Long.valueOf(request.getPlanId()));

        if(plan == null){
            return null;
        }

        if(products.size() != request.getMovies().size()) {
            return null;
        }

            PlanMovies planMovies =
                    PlanMovies.
                            builder().
                            movies(products.stream().map(Movie::getId).collect(Collectors.toList()))
                            .plan(plan).build();
            planMoviesRepository.save(planMovies);
            return planMovies;

    }

    @Override
    public PlanMovies getPlanMovies(String id) {
        return planMoviesRepository.getById(Long.valueOf(id));
    }

    @Override
    public List<PlanMovies> getAllPlanMovies() {
        List<PlanMovies> planMovies = planMoviesRepository.getPlanMovies();
        return planMovies.isEmpty() ? null : planMovies;
    }
}
