package org.mija.elbuensaborback.domain.exceptions;

public class StockInsuficienteException extends RuntimeException {
  public StockInsuficienteException(String message) {
    super(message);
  }
}
