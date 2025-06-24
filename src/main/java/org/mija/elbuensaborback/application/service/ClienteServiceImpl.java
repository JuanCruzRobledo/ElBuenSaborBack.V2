package org.mija.elbuensaborback.application.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.mija.elbuensaborback.application.dto.request.cliente.ClienteUpdateRequest;
import org.mija.elbuensaborback.application.dto.response.ClienteBasicResponse;
import org.mija.elbuensaborback.application.dto.response.ClienteResponse;
import org.mija.elbuensaborback.application.mapper.ClienteMapper;
import org.mija.elbuensaborback.application.service.contratos.ClienteService;
import org.mija.elbuensaborback.infrastructure.persistence.entity.ClienteEntity;
import org.mija.elbuensaborback.infrastructure.persistence.entity.ImagenClienteEntity;
import org.mija.elbuensaborback.infrastructure.persistence.repository.adapter.ClienteRepositoryImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private final PedidoServiceImpl pedidoService;
    private final ClienteRepositoryImpl clienteRepository;
    private final ClienteMapper clienteMapper;
    private final CloudinaryService cloudinaryService;

    @Override
    public ClienteBasicResponse traerCliente(Long id) {
        ClienteEntity cliente = clienteRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("No se pudo encontrar el cliente"));

        return  clienteMapper.toBasicResponse(cliente);
    }

    @Override
    public ClienteBasicResponse actualizarCliente(Long id, ClienteUpdateRequest clienteUpdateRequest) {
        ClienteEntity cliente = clienteRepository.findById(id).orElseThrow(()->new EntityNotFoundException("No se pudo encontrar el cliente"));
        clienteMapper.actualizarDesdeDto(clienteUpdateRequest, cliente);

        cliente.setId(id);

        return clienteMapper.toBasicResponse(clienteRepository.save(cliente));
    }

    @Override
    public ClienteBasicResponse subirFoto(Long id, MultipartFile foto) {
        ClienteEntity cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se pudo encontrar el cliente"));

        try {
            // Subir a Cloudinary y obtener la URL
            String url = cloudinaryService.subirArchivo(foto);

            if (cliente.getImagen() == null) {
                ImagenClienteEntity imagen = ImagenClienteEntity.builder()
                        .url(url) // Usamos la URL obtenida
                        .build();
                cliente.setImagen(imagen);
            } else {
                cliente.getImagen().setUrl(url); // Actualizamos la URL
            }

            cliente = clienteRepository.save(cliente);
            return clienteMapper.toBasicResponse(cliente);

        } catch (IOException e) {
            throw new RuntimeException("Error al subir la imagen: " + e.getMessage(), e);
        }
    }

    public ClienteBasicResponse subirFotoString(Long id, String foto) {
        ClienteEntity cliente = clienteRepository.findById(id).orElseThrow(()->new EntityNotFoundException("No se pudo encontrar el cliente"));
        if (cliente.getImagen() == null) {
            ImagenClienteEntity imagen = ImagenClienteEntity.builder().url(foto).build();
            cliente.setImagen(imagen);
            imagen.setCliente(cliente);
        } else {
            cliente.getImagen().setUrl(foto);
            cliente.getImagen().setCliente(cliente);
        }

        cliente =clienteRepository.save(cliente);
        return clienteMapper.toBasicResponse(cliente);
    }

    @Override
    public List<ClienteResponse> listarClientes() {
        List<ClienteEntity> listaEmpleados = clienteRepository.findAll();
        return listaEmpleados.stream().map(clienteMapper::toResponse).toList();
    }

    @Override
    public void eliminarCliente(Long id) {
        ClienteEntity cliente = clienteRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("No se pudo encontrar el Empleado"));
        cliente.setActivo(false);
        clienteRepository.save(cliente);
    }
}
