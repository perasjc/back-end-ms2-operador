package com.unir.grupo15.operador.controller;

import com.unir.grupo15.operador.model.pojo.Plan;
import com.unir.grupo15.operador.model.pojo.PlanDto;
import com.unir.grupo15.operador.model.request.PlanRequest;
import com.unir.grupo15.operador.service.interfaces.PlanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Plans Controller", description = "Microservicio encargado de exponer operaciones CRUD sobre planes alojados en una base de datos en memoria.")
public class PlansController {

    private final PlanService planService;

    @GetMapping("/plans")
    @Operation(
            operationId = "Obtener planes",
            description = "Operacion de lectura",
            summary = "Se devuelve una lista de todos los planes almacenados en la base de datos.")
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Plan.class)))
    public ResponseEntity<List<Plan>> getPlans(
            @RequestHeader Map<String, String> headers) {

        log.info("headers: {}", headers);
        List<Plan> plans = planService.getPlans();

        return ResponseEntity.ok(Objects.requireNonNullElse(plans, Collections.emptyList()));
    }

    @GetMapping("/plans/{planId}")
    @Operation(
            operationId = "Obtener un plan",
            description = "Operacion de lectura",
            summary = "Se devuelve un plan a partir de su identificador.")
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Plan.class)))
    @ApiResponse(
            responseCode = "404",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class)),
            description = "No se ha encontrado el plan con el identificador indicado.")
    public ResponseEntity<Plan> getPlan(@PathVariable String planId) {

        log.info("Request received for plan {}", planId);
        Plan plan = planService.getPlan(planId);

        if (plan != null) {
            return ResponseEntity.ok(plan);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/plans/{planId}")
    @Operation(
            operationId = "Eliminar un plan",
            description = "Operacion de escritura",
            summary = "Se elimina un plan a partir de su identificador.")
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class)))
    @ApiResponse(
            responseCode = "404",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class)),
            description = "No se ha encontrado el plan con el identificador indicado.")
    public ResponseEntity<Void> deletePlan(@PathVariable String planId) {

        Boolean removed = planService.removePlan(planId);

        if (Boolean.TRUE.equals(removed)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @PostMapping("/plans")
    @Operation(
            operationId = "Insertar un plan",
            description = "Operacion de escritura",
            summary = "Se crea un plan a partir de sus datos.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del plan a crear.",
                    required = true,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PlanRequest.class))))
    @ApiResponse(
            responseCode = "201",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Plan.class)))
    @ApiResponse(
            responseCode = "400",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class)),
            description = "Datos incorrectos introducidos.")
    @ApiResponse(
            responseCode = "404",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class)),
            description = "No se ha encontrado el plan con el identificador indicado.")
    public ResponseEntity<Plan> addPlan(@RequestBody PlanRequest request) {

        Plan createdPlan = planService.createPlan(request);

        if (createdPlan != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(createdPlan);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }


    @PatchMapping("/plans/{planId}")
    @Operation(
            operationId = "Modificar parcialmente un plan",
            description = "RFC 7386. Operacion de escritura",
            summary = "RFC 7386. Se modifica parcialmente un plan.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del plan a crear.",
                    required = true,
                    content = @Content(mediaType = "application/merge-patch+json", schema = @Schema(implementation = String.class))))
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Plan.class)))
    @ApiResponse(
            responseCode = "400",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class)),
            description = "Plan inválido o datos incorrectos introducidos.")
    public ResponseEntity<Plan> patchPlan(@PathVariable String planId, @RequestBody String patchBody) {

        Plan patched = planService.updatePlan(planId, patchBody);
        if (patched != null) {
            return ResponseEntity.ok(patched);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }


    @PutMapping("/plans/{planId}")
    @Operation(
            operationId = "Modificar totalmente un plan",
            description = "Operacion de escritura",
            summary = "Se modifica totalmente un plan.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del plan a actualizar.",
                    required = true,
                    content = @Content(mediaType = "application/merge-patch+json", schema = @Schema(implementation = PlanDto.class))))
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Plan.class)))
    @ApiResponse(
            responseCode = "404",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class)),
            description = "Plan no encontrado.")
    public ResponseEntity<Plan> updatePlan(@PathVariable String planId, @RequestBody PlanRequest body) {

        Plan updated = planService.updatePlan(planId, body);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
