package org.mija.elbuensaborback.application.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.mija.elbuensaborback.application.dto.request.empleado.EmpleadoCreatedRequest;
import org.mija.elbuensaborback.application.dto.response.EmpleadoResponse;
import org.mija.elbuensaborback.application.mapper.EmpleadoMapper;
import org.mija.elbuensaborback.application.service.contratos.EmpleadoService;
import org.mija.elbuensaborback.infrastructure.persistence.entity.EmpleadoEntity;
import org.mija.elbuensaborback.infrastructure.persistence.entity.SucursalEntity;
import org.mija.elbuensaborback.infrastructure.persistence.repository.adapter.EmpleadoRepositoryImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class EmpleadoServiceImpl implements EmpleadoService {

    private final EmpleadoRepositoryImpl empleadoRepository;
    private final EmpleadoMapper empleadoMapper;

    @Override
    public EmpleadoResponse crearEmpleado(EmpleadoCreatedRequest request) {
        EmpleadoEntity empleado = empleadoMapper.toEntity(request);
        empleado.setSucursal(SucursalEntity.builder().id(1L).build());
        empleado.setActivo(true);
        empleado = empleadoRepository.save(empleado);
        return empleadoMapper.toResponse(empleado);
    }

    @Override
    public EmpleadoResponse buscarEmpleadoPorId(Long id) {
        return empleadoMapper.toResponse(empleadoRepository.findById(id).orElse(null));
    }

    @Override
    public EmpleadoResponse buscarEmpleadoPorEmail(String email) {
        return empleadoMapper.toResponse(empleadoRepository.findByEmail(email));
    }

    @Override
    public List<EmpleadoResponse> listarEmpleados() {
        List<EmpleadoEntity> listaEmpleados = empleadoRepository.findAll();
        return listaEmpleados.stream().map(empleadoMapper::toResponse).toList();
    }

    @Override
    public void eliminarEmpleado(Long id) {
        EmpleadoEntity empleado = empleadoRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("No se encontró el empleado"));
        empleado.setActivo(false);
        empleadoRepository.save(empleado);
    }
}
