package org.mija.elbuensaborback.application.service.contratos;

import org.mija.elbuensaborback.application.dto.request.cliente.ClienteUpdateRequest;
import org.mija.elbuensaborback.application.dto.response.ClienteBasicResponse;
import org.mija.elbuensaborback.application.dto.response.ClienteResponse;
import org.mija.elbuensaborback.application.dto.response.EmpleadoResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ClienteService {


    ClienteBasicResponse traerCliente(Long id);
    ClienteBasicResponse actualizarCliente(Long id, ClienteUpdateRequest clienteUpdateRequest);
    ClienteBasicResponse subirFoto(Long id, MultipartFile foto);
    List<ClienteResponse> listarClientes();
}
