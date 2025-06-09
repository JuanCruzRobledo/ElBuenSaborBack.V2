package org.mija.elbuensaborback.application.service.contratos;

import org.mija.elbuensaborback.application.dto.request.empleado.EmpleadoCreatedRequest;
import org.mija.elbuensaborback.application.dto.response.EmpleadoResponse;

import java.util.List;

public interface EmpleadoService {
    EmpleadoResponse crearEmpleado(EmpleadoCreatedRequest request);
    EmpleadoResponse buscarEmpleadoPorId(Long id);
    EmpleadoResponse buscarEmpleadoPorEmail(String email);
    List<EmpleadoResponse> listarEmpleados();
}
