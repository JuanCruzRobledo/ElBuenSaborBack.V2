package org.mija.elbuensaborback.application.dto.response;

public record RankingClientesResponse(
        ClienteResponse cliente,
        int cantidadPedidos,
        Double importeTotal
) {
}
