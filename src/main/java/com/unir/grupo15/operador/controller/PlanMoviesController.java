package com.unir.grupo15.operador.controller;

import com.unir.grupo15.operador.model.pojo.PlanMovies;
import com.unir.grupo15.operador.model.request.PlanMoviesRequest;
import com.unir.grupo15.operador.service.interfaces.PlanMoviesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@Slf4j
public class PlanMoviesController {

    private final PlanMoviesService service; //Inyeccion por constructor mediante @RequiredArgsConstructor. Y, también es inyección por interfaz.

    @PostMapping("/planMovies")
    public ResponseEntity<PlanMovies> createOrder(@RequestBody @Valid PlanMoviesRequest request) { //Se valida con Jakarta Validation API

        log.info("Creating order...");
        PlanMovies created = service.createPlanMovies(request);

        if (created != null) {
            return ResponseEntity.ok(created);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/planMovies")
    public ResponseEntity<List<PlanMovies>> getOrders() {

        List<PlanMovies> allPlanMovies = service.getAllPlanMovies();
        return ResponseEntity.ok(Objects.requireNonNullElse(allPlanMovies, Collections.emptyList()));
    }

    @GetMapping("/planMovies/{id}")
    public ResponseEntity<PlanMovies> getOrder(@PathVariable String id) {

        PlanMovies planMovies = service.getPlanMovies(id);
        if (planMovies != null) {
            return ResponseEntity.ok(planMovies);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
