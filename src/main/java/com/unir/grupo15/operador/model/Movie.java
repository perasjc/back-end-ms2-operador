package com.unir.grupo15.operador.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Movie {
    private Long id;
    private int releaseYear;
    private String title;
    private String synopsis;
    private String numSeasonsLabel;
}
