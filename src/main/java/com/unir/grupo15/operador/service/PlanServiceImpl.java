package com.unir.grupo15.operador.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
import com.unir.grupo15.operador.data.PlanRepository;
import com.unir.grupo15.operador.model.pojo.Plan;
import com.unir.grupo15.operador.model.request.PlanRequest;
import com.unir.grupo15.operador.service.interfaces.PlanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
@Service
@Slf4j
public class PlanServiceImpl implements PlanService {
    @Autowired
    private PlanRepository planRepositoryrepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public List<Plan> getPlans() {

        List<Plan> products = planRepositoryrepository.getPlans();
        return products.isEmpty() ? null : products;
    }

    @Override
    public Plan getPlan(String planId) {
        return planRepositoryrepository.getById(Long.valueOf(planId));
    }

    @Override
    public Boolean removePlan(String planId) {

        Plan plan = planRepositoryrepository.getById(Long.valueOf(planId));

        if (plan != null) {
            planRepositoryrepository.delete(plan);
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }

    @Override
    public Plan createPlan(PlanRequest request) {

        //Otra opcion: Jakarta Validation: https://www.baeldung.com/java-validation
        if (request != null && StringUtils.hasLength(request.getName().trim())
                && StringUtils.hasLength(request.getDescription().trim())) {

            Plan plan = Plan.builder().name(request.getName()).description(request.getDescription()).build();

            return planRepositoryrepository.save(plan);
        } else {
            return null;
        }
    }

    @Override
    public Plan updatePlan(String planId, String request) {

        //PATCH se implementa en este caso mediante Merge Patch: https://datatracker.ietf.org/doc/html/rfc7386
        Plan plan = planRepositoryrepository.getById(Long.valueOf(planId));
        if (plan != null) {
            try {
                JsonMergePatch jsonMergePatch = JsonMergePatch.fromJson(objectMapper.readTree(request));
                JsonNode target = jsonMergePatch.apply(objectMapper.readTree(objectMapper.writeValueAsString(plan)));
                Plan patched = objectMapper.treeToValue(target, Plan.class);
                planRepositoryrepository.save(patched);
                return patched;
            } catch (JsonProcessingException | JsonPatchException e) {
                log.error("Error updating plan {}", planId, e);
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public Plan updatePlan(String planId, PlanRequest updateRequest) {
        Plan plan = planRepositoryrepository.getById(Long.valueOf(planId));
        if (plan != null) {
            plan.setName(updateRequest.getName());
            plan.setDescription(updateRequest.getDescription());
            planRepositoryrepository.save(plan);
            return plan;
        } else {
            return null;
        }
    }

}
