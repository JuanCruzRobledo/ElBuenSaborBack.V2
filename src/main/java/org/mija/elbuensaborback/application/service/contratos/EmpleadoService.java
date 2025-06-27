package org.mija.elbuensaborback.application.service.contratos;

import org.mija.elbuensaborback.application.dto.request.empleado.EmpleadoBasicUpdateRequest;
import org.mija.elbuensaborback.application.dto.request.empleado.EmpleadoCreatedRequest;
import org.mija.elbuensaborback.application.dto.request.empleado.EmpleadoUpdateRequest;
import org.mija.elbuensaborback.application.dto.response.EmpleadoResponse;

import java.util.List;

public interface EmpleadoService {
    EmpleadoResponse crearEmpleado(EmpleadoCreatedRequest request);
    EmpleadoResponse buscarEmpleadoPorId(Long id);
    EmpleadoResponse buscarEmpleadoPorEmail(String email);
    List<EmpleadoResponse> listarEmpleados();
    EmpleadoResponse actualizarEmpleadoCompleto(Long id, EmpleadoUpdateRequest request);
    EmpleadoResponse actualizarEmpleadoBasico(Long id, EmpleadoBasicUpdateRequest request);
    void eliminarEmpleado(Long id);
}
