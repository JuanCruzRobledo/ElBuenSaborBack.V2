package org.mija.elbuensaborback.application.service.contratos;

import org.mija.elbuensaborback.application.dto.request.cliente.ClienteBasicUpdateRequest;
import org.mija.elbuensaborback.application.dto.request.cliente.ClienteCompleteUpdateRequest;
import org.mija.elbuensaborback.application.dto.response.ClienteBasicResponse;
import org.mija.elbuensaborback.application.dto.response.ClienteResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ClienteService {


    ClienteBasicResponse traerCliente(Long id);
    ClienteBasicResponse actualizarCliente(Long id, ClienteBasicUpdateRequest clienteBasicUpdateRequest);
    ClienteResponse actualizarClienteCompleto(Long id, ClienteCompleteUpdateRequest clienteBasicUpdateRequest);
    ClienteBasicResponse subirFoto(Long id, MultipartFile foto);
    List<ClienteResponse> listarClientes();
    void eliminarCliente(Long id);
}
