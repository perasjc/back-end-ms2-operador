package com.unir.grupo15.operador.service.interfaces;

import com.unir.grupo15.operador.model.pojo.Plan;
import com.unir.grupo15.operador.model.pojo.PlanDto;
import com.unir.grupo15.operador.model.request.PlanRequest;

import java.util.List;

public interface PlanService {
    List<Plan> getPlans();
    Plan getPlan(String planId);

    Boolean removePlan(String planId);

    Plan createPlan(PlanRequest request);

    Plan updatePlan(String planId, String updateRequest);

    Plan updatePlan(String planId, PlanRequest updateRequest);
}
