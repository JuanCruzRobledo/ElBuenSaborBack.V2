package org.mija.elbuensaborback.application.service.contratos;

import org.mija.elbuensaborback.application.dto.request.empleado.EmpleadoCreatedRequest;
import org.mija.elbuensaborback.application.dto.response.EmpleadoResponse;

public interface EmpleadoService {
    public EmpleadoResponse crearEmpleado(EmpleadoCreatedRequest request);
    public EmpleadoResponse buscarEmpleadoPorId(Long id);
    public EmpleadoResponse buscarEmpleadoPorEmail(String email);
}
