package org.mija.elbuensaborback.application.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.mija.elbuensaborback.application.dto.request.empleado.EmpleadoBasicUpdateRequest;
import org.mija.elbuensaborback.application.dto.request.empleado.EmpleadoCreatedRequest;
import org.mija.elbuensaborback.application.dto.request.empleado.EmpleadoUpdateRequest;
import org.mija.elbuensaborback.application.dto.response.EmpleadoResponse;
import org.mija.elbuensaborback.application.mapper.EmpleadoMapper;
import org.mija.elbuensaborback.application.service.contratos.EmpleadoService;
import org.mija.elbuensaborback.domain.enums.AuthProviderEnum;
import org.mija.elbuensaborback.infrastructure.persistence.entity.ClienteEntity;
import org.mija.elbuensaborback.infrastructure.persistence.entity.EmpleadoEntity;
import org.mija.elbuensaborback.infrastructure.persistence.entity.RoleEntity;
import org.mija.elbuensaborback.infrastructure.persistence.entity.SucursalEntity;
import org.mija.elbuensaborback.infrastructure.persistence.repository.adapter.EmpleadoRepositoryImpl;
import org.mija.elbuensaborback.infrastructure.persistence.repository.adapter.RoleRepositoryImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class EmpleadoServiceImpl implements EmpleadoService {

    private final EmpleadoRepositoryImpl empleadoRepository;
    private final EmpleadoMapper empleadoMapper;
    private final RoleRepositoryImpl roleRepository;

    @Override
    public EmpleadoResponse crearEmpleado(EmpleadoCreatedRequest request) {
        EmpleadoEntity empleado = empleadoMapper.toEntity(request);
        RoleEntity rol = roleRepository.findById(request.rolId()).orElseThrow(()-> new EntityNotFoundException("Rol no encontrado"));

        //Creo el usuario por defecto
        empleado.setSucursal(SucursalEntity.builder().id(1L).build());
        empleado.setActivo(true);
        empleado.getUsuario().setRol(rol);
        empleado.getUsuario().setAuthProviderEnum(AuthProviderEnum.LOCAL);
        empleado.getUsuario().setDisabled(false);
        empleado.getUsuario().setAccountExpired(false);
        empleado.getUsuario().setAccountLocked(false);
        empleado.getUsuario().setCredentialsExpired(false);

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
    public EmpleadoResponse actualizarEmpleadoCompleto(Long id, EmpleadoUpdateRequest request) {
        EmpleadoEntity empleado = empleadoRepository.findById(id).orElseThrow(()->new EntityNotFoundException("No se pudo encontrar el empleado"));
        RoleEntity rol = roleRepository.findById(request.rolId()).orElseThrow(()-> new EntityNotFoundException("No se pudo encontrar el rol"));
        empleadoMapper.actualizarDesdeDto(request, empleado);

        empleado.getUsuario().setRol(rol);

        return empleadoMapper.toResponse(empleadoRepository.save(empleado));
    }

    @Override
    public EmpleadoResponse actualizarEmpleadoBasico(Long id, EmpleadoBasicUpdateRequest request) {
        EmpleadoEntity empleado = empleadoRepository.findById(id).orElseThrow(()->new EntityNotFoundException("No se pudo encontrar el empleado"));

        empleadoMapper.actualizarDesdeDto(request, empleado);


        return empleadoMapper.toResponse(empleadoRepository.save(empleado));
    }

    @Override
    public void eliminarEmpleado(Long id) {
        EmpleadoEntity empleado = empleadoRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("No se encontró el empleado"));
        empleado.setActivo(false);
        empleadoRepository.save(empleado);
    }
}
