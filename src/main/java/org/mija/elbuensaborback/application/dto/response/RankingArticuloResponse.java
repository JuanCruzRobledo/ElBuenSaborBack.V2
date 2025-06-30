package org.mija.elbuensaborback.application.dto.response;

import org.mija.elbuensaborback.domain.enums.TipoEnvioEnum;

import java.math.BigDecimal;

public record RankingArticuloResponse(
        String denominacion,
        Long cantidadTotal,
        BigDecimal totalRecaudado,
        TipoEnvioEnum tipoEnvioEnum
) {
}
