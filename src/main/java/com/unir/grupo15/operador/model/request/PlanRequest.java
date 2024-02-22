package com.unir.grupo15.operador.model.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class PlanRequest {
    private Long id;
    private String name;
    private String description;
}
