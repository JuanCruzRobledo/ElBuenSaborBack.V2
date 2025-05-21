package org.mija.elbuensaborback.application.dto.response;

import java.math.BigDecimal;

public record PreferenceResponseDto(
        String preferenceId,
        Long pedidoId,
        BigDecimal totalPedido
) {}