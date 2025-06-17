package org.mija.elbuensaborback.application.dto.response;

import java.math.BigDecimal;

public record RankingArticuloResponse(
        String denominacion,
        Long cantidadTotal,
        BigDecimal totalRecaudado
) {
}
