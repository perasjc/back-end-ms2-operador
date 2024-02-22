package com.unir.grupo15.operador.model.pojo;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class PlanDetailDto {
    private Long id;
    private String description;
    private Long planId;
}
