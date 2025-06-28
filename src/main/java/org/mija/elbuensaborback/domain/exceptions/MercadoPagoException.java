package org.mija.elbuensaborback.domain.exceptions;

public class MercadoPagoException extends RuntimeException {
    public MercadoPagoException(String message, String error) {
        super(message +" "+ error);
    }
}
