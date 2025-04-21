package org.mija.elbuensaborback.application.dto.response;

public record AuthResponse(String username,
                           String menssage,
                           String jwt ,
                           boolean status) {

}