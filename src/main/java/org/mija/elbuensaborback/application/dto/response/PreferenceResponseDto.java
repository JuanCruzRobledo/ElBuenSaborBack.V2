package org.mija.elbuensaborback.application.dto.response;

public record PreferenceResponseDto(
        String preferenceId,
        Long pedidoId,
        Double totalPedido
) {}